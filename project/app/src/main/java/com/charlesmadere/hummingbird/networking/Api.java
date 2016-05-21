package com.charlesmadere.hummingbird.networking;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.charlesmadere.hummingbird.misc.Constants;
import com.charlesmadere.hummingbird.misc.CurrentUser;
import com.charlesmadere.hummingbird.misc.GsonUtils;
import com.charlesmadere.hummingbird.misc.RetrofitUtils;
import com.charlesmadere.hummingbird.misc.Threading;
import com.charlesmadere.hummingbird.misc.Timber;
import com.charlesmadere.hummingbird.models.AbsAnime;
import com.charlesmadere.hummingbird.models.AnimeDigest;
import com.charlesmadere.hummingbird.models.AnimeEpisode;
import com.charlesmadere.hummingbird.models.AnimeV1;
import com.charlesmadere.hummingbird.models.AnimeV2;
import com.charlesmadere.hummingbird.models.AuthInfo;
import com.charlesmadere.hummingbird.models.CommentPost;
import com.charlesmadere.hummingbird.models.CommentStory;
import com.charlesmadere.hummingbird.models.ErrorInfo;
import com.charlesmadere.hummingbird.models.Feed;
import com.charlesmadere.hummingbird.models.LibraryEntry;
import com.charlesmadere.hummingbird.models.LibraryUpdate;
import com.charlesmadere.hummingbird.models.MangaDigest;
import com.charlesmadere.hummingbird.models.SearchBundle;
import com.charlesmadere.hummingbird.models.SearchDepth;
import com.charlesmadere.hummingbird.models.SearchScope;
import com.charlesmadere.hummingbird.models.UserDigest;
import com.charlesmadere.hummingbird.models.UserV2;
import com.charlesmadere.hummingbird.models.WatchingStatus;
import com.charlesmadere.hummingbird.preferences.Preferences;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;

public final class Api {

    private static final String TAG = "Api";


    public static void addOrUpdateLibraryEntry(final String id, final LibraryUpdate libraryUpdate,
            final ApiResponse<LibraryEntry> listener) {
        getApi().addOrUpdateLibraryEntry(id, libraryUpdate).enqueue(new Callback<LibraryEntry>() {
            @Override
            public void onResponse(final Call<LibraryEntry> call,
                    final Response<LibraryEntry> response) {
                LibraryEntry body = null;

                if (response.isSuccessful()) {
                    body = response.body();
                }

                if (body == null) {
                    listener.failure(retrieveErrorInfo(response));
                } else {
                    listener.success(body);
                }
            }

            @Override
            public void onFailure(final Call<LibraryEntry> call, final Throwable t) {
                Timber.e(TAG, "add or update library entry (" + id + ") failed", t);
                listener.failure(null);
            }
        });
    }

    public static void authenticate(final AuthInfo authInfo, final ApiResponse<String> listener) {
        getApi().authenticate(authInfo).enqueue(new Callback<String>() {
            @Override
            public void onResponse(final Call<String> call, final Response<String> response) {
                String body = null;

                if (response.isSuccessful()) {
                    body = response.body();
                }

                if (TextUtils.isEmpty(body)) {
                    listener.failure(retrieveErrorInfo(response));
                } else {
                    Preferences.Account.AuthToken.set(body);
                    Preferences.Account.Username.set(authInfo.getUsername());
                    listener.success(body);
                }
            }

            @Override
            public void onFailure(final Call<String> call, final Throwable t) {
                Timber.e(TAG, "authenticate failed", t);
                listener.failure(null);
            }
        });
    }

    public static void getAnimeById(final AbsAnime anime, final ApiResponse<AnimeV2> listener) {
        if (anime.getVersion() == AbsAnime.Version.V2) {
            listener.success((AnimeV2) anime);
        } else {
            getAnimeById(anime.getId(), listener);
        }
    }

    public static void getAnimeById(final String id, final ApiResponse<AnimeV2> listener) {
        getApi().getAnimeById(Constants.API_KEY, id).enqueue(new Callback<AnimeV2>() {
            @Override
            public void onResponse(final Call<AnimeV2> call, final Response<AnimeV2> response) {
                AnimeV2 body = null;

                if (response.isSuccessful()) {
                    body = response.body();

                    if (body.hasAnimeEpisodes()) {
                        AnimeEpisode.sort(body.getAnimeEpisodes());
                    }
                }

                if (body == null) {
                    listener.failure(retrieveErrorInfo(response));
                } else {
                    listener.success(body);
                }
            }

            @Override
            public void onFailure(final Call<AnimeV2> call, final Throwable t) {
                Timber.e(TAG, "get anime (" + id + ") by id failed ", t);
                listener.failure(null);
            }
        });
    }

    public static void getAnimeByMyAnimeListId(final AbsAnime anime,
            final ApiResponse<AnimeV2> listener) {
        if (anime.getVersion() == AbsAnime.Version.V1) {
            final AnimeV1 animeV1 = (AnimeV1) anime;
            getAnimeByMyAnimeListId(animeV1.getMyAnimeListId(), listener);
        } else {
            listener.success((AnimeV2) anime);
        }
    }

    public static void getAnimeByMyAnimeListId(final String id,
            final ApiResponse<AnimeV2> listener) {
        getApi().getAnimeByMyAnimeListId(Constants.API_KEY, id).enqueue(new Callback<AnimeV2>() {
            @Override
            public void onResponse(final Call<AnimeV2> call, final Response<AnimeV2> response) {
                AnimeV2 body = null;

                if (response.isSuccessful()) {
                    body = response.body();
                }

                if (body == null) {
                    listener.failure(retrieveErrorInfo(response));
                } else {
                    listener.success(body);
                }
            }

            @Override
            public void onFailure(final Call<AnimeV2> call, final Throwable t) {
                Timber.e(TAG, "get anime (" + id + ") by My Anime List id failed", t);
                listener.failure(null);
            }
        });
    }

    public static void getAnimeDigest(final String animeId, final ApiResponse<AnimeDigest> listener) {
        getApi().getAnimeDigest(getAuthTokenCookieString(), animeId).enqueue(
                new Callback<AnimeDigest>() {
            private AnimeDigest mBody;

            @Override
            public void onResponse(final Call<AnimeDigest> call,
                    final Response<AnimeDigest> response) {
                if (response.isSuccessful()) {
                    mBody = response.body();
                }

                if (mBody == null) {
                    listener.failure(retrieveErrorInfo(response));
                } else {
                    Threading.runOnBackground(new Runnable() {
                        @Override
                        public void run() {
                            mBody.hydrate();

                            Threading.runOnUi(new Runnable() {
                                @Override
                                public void run() {
                                    listener.success(mBody);
                                }
                            });
                        }
                    });
                }
            }

            @Override
            public void onFailure(final Call<AnimeDigest> call, final Throwable t) {
                Timber.e(TAG, "get anime (" + animeId + ") digest failed", t);
                listener.failure(null);
            }
        });
    }

    private static HummingbirdApi getApi() {
        return RetrofitUtils.getHummingbirdApi();
    }

    private static String getAuthTokenCookieString() {
        final String authToken = Preferences.Account.AuthToken.get();
        return "token=" + authToken + ';';
    }

    public static void getCurrentUser(final ApiResponse<UserV2> listener) {
        getUser(Preferences.Account.Username.get(), new ApiResponse<UserV2>() {
            @Override
            public void failure(@Nullable final ErrorInfo error) {
                listener.failure(error);
            }

            @Override
            public void success(final UserV2 user) {
                CurrentUser.set(user);
                listener.success(user);
            }
        });
    }

    public static void getCurrentUserLibraryEntries(@Nullable final WatchingStatus watchingStatus,
            final ApiResponse<ArrayList<LibraryEntry>> listener) {
        getLibraryEntries(Preferences.Account.Username.get(), watchingStatus, listener);
    }

    public static void getFavoriteAnime(final String username,
            final ApiResponse<ArrayList<AbsAnime>> listener) {
        getApi().getFavoriteAnime(username).enqueue(new Callback<ArrayList<AbsAnime>>() {
            @Override
            public void onResponse(final Call<ArrayList<AbsAnime>> call,
                    final Response<ArrayList<AbsAnime>> response) {
                if (response.isSuccessful()) {
                    listener.success(response.body());
                } else {
                    listener.failure(retrieveErrorInfo(response));
                }
            }

            @Override
            public void onFailure(final Call<ArrayList<AbsAnime>> call, final Throwable t) {
                Timber.e(TAG, "get favorite anime for user (" + username + ") failed", t);
                listener.failure(null);
            }
        });
    }

    public static void getFollowedUsers(final String username, final ApiResponse<Feed> listener) {
        getFollowedUsers(username, null, listener);
    }

    public static void getFollowedUsers(final String username, @Nullable final Feed feed,
            final ApiResponse<Feed> listener) {
        final int page = feed == null ? 1 : feed.getMetadata().getCursor();

        getApi().getFollowedUsers(getAuthTokenCookieString(), username, page).enqueue(
                new Callback<Feed>() {
            @Override
            public void onResponse(final Call<Feed> call, final Response<Feed> response) {
                Feed body = null;

                if (response.isSuccessful()) {
                    body = response.body();
                }

                if (body == null) {
                    listener.failure(retrieveErrorInfo(response));
                } else {
                    hydrateFeed(body, feed, listener);
                }
            }

            @Override
            public void onFailure(final Call<Feed> call, final Throwable t) {
                Timber.e(TAG, "get followed users (" + username + ") (page " + page + ") failed", t);
                listener.failure(null);
            }
        });
    }

    public static void getFollowingUsers(final String username, final ApiResponse<Feed> listener) {
        getFollowingUsers(username, null, listener);
    }

    public static void getFollowingUsers(final String username, @Nullable final Feed feed,
            final ApiResponse<Feed> listener) {
        final int page = feed == null ? 1 : feed.getMetadata().getCursor();

        getApi().getFollowingUsers(getAuthTokenCookieString(), username, page).enqueue(
                new Callback<Feed>() {
            @Override
            public void onResponse(final Call<Feed> call, final Response<Feed> response) {
                Feed body = null;

                if (response.isSuccessful()) {
                    body = response.body();
                }

                if (body == null) {
                    listener.failure(retrieveErrorInfo(response));
                } else {
                    hydrateFeed(body, feed, listener);
                }
            }

            @Override
            public void onFailure(final Call<Feed> call, final Throwable t) {
                Timber.e(TAG, "get following users (" + username + ") (page " + page + ") failed", t);
                listener.failure(null);
            }
        });
    }

    public static void getGroup(final String groupId, final ApiResponse<Feed> listener) {
        getGroup(groupId, null, listener);
    }

    public static void getGroup(final String groupId, @Nullable final Feed feed,
            final ApiResponse<Feed> listener) {
        final int page = feed == null ? 1 : feed.getMetadata().getCursor();

        getApi().getGroup(getAuthTokenCookieString(), groupId, page).enqueue(new Callback<Feed>() {
            @Override
            public void onResponse(final Call<Feed> call, final Response<Feed> response) {
                Feed body = null;

                if (response.isSuccessful()) {
                    body = response.body();
                }

                if (body == null) {
                    listener.failure(retrieveErrorInfo(response));
                } else {
                    hydrateFeed(body, feed, listener);
                }
            }

            @Override
            public void onFailure(final Call<Feed> call, final Throwable t) {
                Timber.e(TAG, "get group (" + groupId + ") (page " + page + ") failed", t);
                listener.failure(null);
            }
        });
    }

    public static void getGroupMembers(final String groupId, final ApiResponse<Feed> listener) {
        getGroupMembers(groupId, null, listener);
    }

    public static void getGroupMembers(final String groupId, @Nullable final Feed feed,
            final ApiResponse<Feed> listener) {
        final int page = feed == null ? 1 : feed.getMetadata().getCursor();

        getApi().getGroupMembers(getAuthTokenCookieString(), groupId, page).enqueue(
                new Callback<Feed>() {
            @Override
            public void onResponse(final Call<Feed> call, final Response<Feed> response) {
                Feed body = null;

                if (response.isSuccessful()) {
                    body = response.body();
                }

                if (body == null) {
                    listener.failure(retrieveErrorInfo(response));
                } else {
                    hydrateFeed(body, null, listener);
                }
            }

            @Override
            public void onFailure(final Call<Feed> call, final Throwable t) {
                Timber.e(TAG, "get group (" + groupId + ") (page " + page + ") members failed", t);
                listener.failure(null);
            }
        });
    }

    public static void getLibraryEntries(final String username,
            @Nullable final WatchingStatus watchingStatus,
            final ApiResponse<ArrayList<LibraryEntry>> listener) {
        getApi().getLibraryEntries(username, watchingStatus).enqueue(
                new Callback<ArrayList<LibraryEntry>>() {
            @Override
            public void onResponse(final Call<ArrayList<LibraryEntry>> call,
                    final Response<ArrayList<LibraryEntry>> response) {
                if (response.isSuccessful()) {
                    listener.success(response.body());
                } else {
                    listener.failure(retrieveErrorInfo(response));
                }
            }

            @Override
            public void onFailure(final Call<ArrayList<LibraryEntry>> call, final Throwable t) {
                Timber.e(TAG, "get library entries for user (" + username + ") failed", t);
                listener.failure(null);
            }
        });
    }

    public static void getMangaDigest(final String mangaId,
            final ApiResponse<MangaDigest> listener) {
        getApi().getMangaDigest(getAuthTokenCookieString(), mangaId).enqueue(
                new Callback<MangaDigest>() {
            @Override
            public void onResponse(final Call<MangaDigest> call,
                    final Response<MangaDigest> response) {
                MangaDigest body = null;

                if (response.isSuccessful()) {
                    body = response.body();
                }

                if (body == null) {
                    listener.failure(retrieveErrorInfo(response));
                } else {
                    listener.success(body);
                }
            }

            @Override
            public void onFailure(final Call<MangaDigest> call, final Throwable t) {
                Timber.e(TAG, "get manga (" + mangaId + ") digest failed", t);
                listener.failure(null);
            }
        });
    }

    public static void getNewsFeed(final ApiResponse<Feed> listener) {
        getNewsFeed(null, listener);
    }

    public static void getNewsFeed(@Nullable final Feed feed, final ApiResponse<Feed> listener) {
        final int page = feed == null ? 1 : feed.getMetadata().getCursor();

        getApi().getNewsFeed(getAuthTokenCookieString(), Boolean.TRUE, page).enqueue(
                new Callback<Feed>() {
            @Override
            public void onResponse(final Call<Feed> call, final Response<Feed> response) {
                Feed body = null;

                if (response.isSuccessful()) {
                    body = response.body();
                }

                if (body == null) {
                    listener.failure(retrieveErrorInfo(response));
                } else {
                    hydrateFeed(body, feed, listener);
                }
            }

            @Override
            public void onFailure(final Call<Feed> call, final Throwable t) {
                Timber.e(TAG, "get news feed (page " + page + ") failed", t);
                listener.failure(null);
            }
        });
    }

    public static void getNotifications(final ApiResponse<Feed> listener) {
        getApi().getNotifications(getAuthTokenCookieString(), "application/json").enqueue(
                new Callback<Feed>() {
            @Override
            public void onResponse(final Call<Feed> call, final Response<Feed> response) {
                Feed body = null;

                if (response.isSuccessful()) {
                    body = response.body();
                }

                if (body == null) {
                    listener.failure(retrieveErrorInfo(response));
                } else {
                    hydrateFeed(body, null, listener);
                }
            }

            @Override
            public void onFailure(final Call<Feed> call, final Throwable t) {
                Timber.e(TAG, "get notifications failed", t);
                listener.failure(null);
            }
        });
    }

    public static void getSubstories(final String storyId, final ApiResponse<Feed> listener) {
        getApi().getSubstories(getAuthTokenCookieString(), storyId).enqueue(new Callback<Feed>() {
            @Override
            public void onResponse(final Call<Feed> call, final Response<Feed> response) {
                Feed body = null;

                if (response.isSuccessful()) {
                    body = response.body();
                }

                if (body == null) {
                    listener.failure(retrieveErrorInfo(response));
                } else {
                    hydrateFeed(body, null, listener);
                }
            }

            @Override
            public void onFailure(final Call<Feed> call, final Throwable t) {
                Timber.e(TAG, "get substories (" + storyId + ") failed", t);
                listener.failure(null);
            }
        });
    }

    public static void getUser(final String username, final ApiResponse<UserV2> listener) {
        getApi().getUser(getAuthTokenCookieString(), "application/json", username).enqueue(
                new Callback<UserV2>() {
            @Override
            public void onResponse(final Call<UserV2> call, final Response<UserV2> response) {
                UserV2 body = null;

                if (response.isSuccessful()) {
                    body = response.body();
                }

                if (body == null) {
                    listener.failure(retrieveErrorInfo(response));
                } else {
                    listener.success(body);
                }
            }

            @Override
            public void onFailure(final Call<UserV2> call, final Throwable t) {
                Timber.e(TAG, "get user (" + username + ") failed", t);
                listener.failure(null);
            }
        });
    }

    public static void getUserDigest(final String username, final ApiResponse<UserDigest> listener) {
        getApi().getUserDigest(getAuthTokenCookieString(), username).enqueue(
                new Callback<UserDigest>() {
            private UserDigest mBody;

            @Override
            public void onResponse(final Call<UserDigest> call,
                    final Response<UserDigest> response) {
                if (response.isSuccessful()) {
                    mBody = response.body();
                }

                if (mBody == null) {
                    listener.failure(retrieveErrorInfo(response));
                } else {
                    Threading.runOnBackground(new Runnable() {
                        @Override
                        public void run() {
                            mBody.hydrate();

                            Threading.runOnUi(new Runnable() {
                                @Override
                                public void run() {
                                    listener.success(mBody);
                                }
                            });
                        }
                    });
                }
            }

            @Override
            public void onFailure(final Call<UserDigest> call, final Throwable t) {
                Timber.e(TAG, "get user (" + username + ") digest failed", t);
                listener.failure(null);
            }
        });
    }

    public static void getUserStories(final String username, final ApiResponse<Feed> listener) {
        getApi().getUserStories(getAuthTokenCookieString(), username).enqueue(new Callback<Feed>() {
            @Override
            public void onResponse(final Call<Feed> call, final Response<Feed> response) {
                Feed body = null;

                if (response.isSuccessful()) {
                    body = response.body();
                }

                if (body == null) {
                    listener.failure(retrieveErrorInfo(response));
                } else {
                    hydrateFeed(body, null, listener);
                }
            }

            @Override
            public void onFailure(final Call<Feed> call, final Throwable t) {
                Timber.e(TAG, "get user (" + username + ") stories failed", t);
                listener.failure(null);
            }
        });
    }

    private static void hydrateFeed(@NonNull final Feed newFeed, @Nullable final Feed oldFeed,
            @NonNull final ApiResponse<Feed> listener) {
        Threading.runOnBackground(new Runnable() {
            @Override
            public void run() {
                newFeed.hydrate();

                if (oldFeed != null) {
                    oldFeed.merge(newFeed);
                }

                Threading.runOnUi(new Runnable() {
                    @Override
                    public void run() {
                        if (oldFeed == null) {
                            listener.success(newFeed);
                        } else {
                            listener.success(oldFeed);
                        }
                    }
                });
            }
        });
    }

    public static void likeStory(final CommentStory story) {
        JsonObject json = new JsonObject();
        json.add("story", GsonUtils.getGson().toJsonTree(story));

        getApi().likeStory(getAuthTokenCookieString(), story.getId(), json).enqueue(
                new Callback<Void>() {
            @Override
            public void onResponse(final Call<Void> call, final Response<Void> response) {
                // do nothing
            }

            @Override
            public void onFailure(final Call<Void> call, final Throwable t) {
                Timber.e(TAG, "like story (" + story.getId() + ") failed", t);
            }
        });
    }

    public static void postComment(final CommentPost commentPost, final ApiResponse<Void> listener) {
        JsonObject json = new JsonObject();
        json.add("substory", GsonUtils.getGson().toJsonTree(commentPost));

        getApi().postComment(getAuthTokenCookieString(), json).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(final Call<Void> call, final Response<Void> response) {
                if (response.isSuccessful()) {
                    listener.success(null);
                } else {
                    listener.failure(retrieveErrorInfo(response));
                }
            }

            @Override
            public void onFailure(final Call<Void> call, final Throwable t) {
                Timber.e(TAG, "post comment (" + commentPost.getStoryId() + ") failed", t);
                listener.failure(null);
            }
        });
    }

    @Nullable
    private static ErrorInfo retrieveErrorInfo(final Response response) {
        final Retrofit retrofit = RetrofitUtils.getRetrofit();
        final Converter<ResponseBody, ErrorInfo> converter = retrofit
                .responseBodyConverter(ErrorInfo.class, new Annotation[0]);

        ErrorInfo errorInfo = null;

        try {
            final ResponseBody errorBody = response.errorBody();
            errorInfo = converter.convert(errorBody);

            if (errorInfo != null) {
                Timber.e(TAG, "Received server error: \"" + errorInfo.getError() + '"');
            }
        } catch (final IllegalStateException | IOException e) {
            Timber.w(TAG, "couldn't convert response's error body", e);
        }

        return errorInfo;
    }

    public static void search(final SearchScope scope, final String query,
            final ApiResponse<SearchBundle> listener) {
        getApi().search(scope, SearchDepth.INSTANT, query).enqueue(new Callback<SearchBundle>() {
            @Override
            public void onResponse(final Call<SearchBundle> call,
                    final Response<SearchBundle> response) {
                if (response.isSuccessful()) {
                    listener.success(response.body());
                } else {
                    listener.failure(retrieveErrorInfo(response));
                }
            }

            @Override
            public void onFailure(final Call<SearchBundle> call, final Throwable t) {
                Timber.e(TAG, "search (scope=" + scope + ") (query=" + query + ") failed", t);
                listener.failure(null);
            }
        });
    }

}
