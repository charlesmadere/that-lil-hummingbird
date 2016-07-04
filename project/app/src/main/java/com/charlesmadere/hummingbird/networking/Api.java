package com.charlesmadere.hummingbird.networking;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.charlesmadere.hummingbird.misc.Constants;
import com.charlesmadere.hummingbird.misc.CurrentUser;
import com.charlesmadere.hummingbird.misc.RetrofitUtils;
import com.charlesmadere.hummingbird.misc.Threading;
import com.charlesmadere.hummingbird.misc.Timber;
import com.charlesmadere.hummingbird.models.AbsAnime;
import com.charlesmadere.hummingbird.models.AnimeDigest;
import com.charlesmadere.hummingbird.models.AnimeLibraryEntry;
import com.charlesmadere.hummingbird.models.AnimeLibraryUpdate;
import com.charlesmadere.hummingbird.models.AppNews;
import com.charlesmadere.hummingbird.models.AuthInfo;
import com.charlesmadere.hummingbird.models.CommentPost;
import com.charlesmadere.hummingbird.models.CommentStory;
import com.charlesmadere.hummingbird.models.ErrorInfo;
import com.charlesmadere.hummingbird.models.Feed;
import com.charlesmadere.hummingbird.models.FeedPost;
import com.charlesmadere.hummingbird.models.Franchise;
import com.charlesmadere.hummingbird.models.GroupDigest;
import com.charlesmadere.hummingbird.models.MangaDigest;
import com.charlesmadere.hummingbird.models.SearchBundle;
import com.charlesmadere.hummingbird.models.SearchDepth;
import com.charlesmadere.hummingbird.models.SearchScope;
import com.charlesmadere.hummingbird.models.User;
import com.charlesmadere.hummingbird.models.UserDigest;
import com.charlesmadere.hummingbird.models.WatchingStatus;
import com.charlesmadere.hummingbird.preferences.Preferences;

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


    public static void addLibraryEntry(final AnimeLibraryUpdate libraryUpdate,
            final ApiResponse<Void> listener) {
        hummingbird().addLibraryEntry(getAuthTokenCookieString(), libraryUpdate.toJson())
                .enqueue(new Callback<Void>() {
            @Override
            public void onResponse(final Call<Void> call, final Response<Void> response) {
                if (response.isSuccessful()) {
                    listener.success(response.body());
                } else {
                    listener.failure(retrieveErrorInfo(response));
                }
            }

            @Override
            public void onFailure(final Call<Void> call, final Throwable t) {
                Timber.e(TAG, "add library entry failed", t);
                listener.failure(null);
            }
        });
    }

    public static void authenticate(final AuthInfo authInfo, final ApiResponse<String> listener) {
        hummingbird().authenticate(authInfo).enqueue(new Callback<String>() {
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
                Timber.e(TAG, "authentication failed", t);
                listener.failure(null);
            }
        });
    }

    public static void deleteLibraryEntry(final String libraryEntryId,
            final ApiResponse<Void> listener) {
        hummingbird().deleteLibraryEntry(getAuthTokenCookieString(), libraryEntryId)
                .enqueue(new Callback<Void>() {
            @Override
            public void onResponse(final Call<Void> call, final Response<Void> response) {
                if (response.isSuccessful()) {
                    listener.success(response.body());
                } else {
                    listener.failure(retrieveErrorInfo(response));
                }
            }

            @Override
            public void onFailure(final Call<Void> call, final Throwable t) {
                Timber.e(TAG, "delete library entry (" + libraryEntryId + ") failed", t);
                listener.failure(null);
            }
        });
    }

    public static void deleteStory(final String storyId, final ApiResponse<Boolean> listener) {
        hummingbird().deleteStory(getAuthTokenCookieString(), storyId).enqueue(
                new Callback<Boolean>() {
            @Override
            public void onResponse(final Call<Boolean> call, final Response<Boolean> response) {
                Boolean body = null;

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
            public void onFailure(final Call<Boolean> call, final Throwable t) {
                Timber.e(TAG, "delete story (" + storyId + ") failed", t);
                listener.failure(null);
            }
        });
    }

    public static void deleteSubstory(final String substoryId, final ApiResponse<Boolean> listener) {
        hummingbird().deleteSubstory(getAuthTokenCookieString(), substoryId).enqueue(
                new Callback<Boolean>() {
            @Override
            public void onResponse(final Call<Boolean> call, final Response<Boolean> response) {
                Boolean body = null;

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
            public void onFailure(final Call<Boolean> call, final Throwable t) {
                Timber.e(TAG, "delete substory (" + substoryId + ") failed", t);
                listener.failure(null);
            }
        });
    }

    public static void getAnime(final String animeId, final ApiResponse<AbsAnime> listener) {
        hummingbird().getAnime(getAuthTokenCookieString(), Constants.MIMETYPE_JSON, animeId)
                .enqueue(new Callback<AbsAnime>() {
            @Override
            public void onResponse(final Call<AbsAnime> call, final Response<AbsAnime> response) {
                AbsAnime anime = null;

                if (response.isSuccessful()) {
                    anime = response.body();
                }

                if (anime == null) {
                    listener.failure(retrieveErrorInfo(response));
                } else {
                    listener.success(anime);
                }
            }

            @Override
            public void onFailure(final Call<AbsAnime> call, final Throwable t) {
                Timber.e(TAG, "get anime (" + animeId + ") failed", t);
                listener.failure(null);
            }
        });
    }

    public static void getAnimeDigest(final String animeId, final ApiResponse<AnimeDigest> listener) {
        hummingbird().getAnimeDigest(getAuthTokenCookieString(), animeId).enqueue(
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

    public static void getAppNews(final ApiResponse<ArrayList<AppNews>> listener) {
        website().getAppNews().enqueue(new Callback<ArrayList<AppNews>>() {
            @Override
            public void onResponse(final Call<ArrayList<AppNews>> call,
                    final Response<ArrayList<AppNews>> response) {
                if (response.isSuccessful()) {
                    listener.success(response.body());
                } else {
                    listener.failure(null);
                }
            }

            @Override
            public void onFailure(final Call<ArrayList<AppNews>> call, final Throwable t) {
                Timber.e(TAG, "get app news failed", t);
                listener.failure(null);
            }
        });
    }

    private static String getAuthTokenCookieString() {
        final String authToken = Preferences.Account.AuthToken.get();

        if (TextUtils.isEmpty(authToken)) {
            throw new IllegalStateException("authToken can't be null / empty");
        }

        return "token=" + authToken + ';';
    }

    public static void getCurrentUser(final ApiResponse<UserDigest> listener) {
        getUserDigest(Preferences.Account.Username.get(), new ApiResponse<UserDigest>() {
            @Override
            public void failure(@Nullable final ErrorInfo error) {
                listener.failure(error);
            }

            @Override
            public void success(final UserDigest user) {
                CurrentUser.set(user);
                listener.success(user);
            }
        });
    }

    public static void getFollowedUsers(final String username, final ApiResponse<Feed> listener) {
        getFollowedUsers(username, null, listener);
    }

    public static void getFollowedUsers(final String username, @Nullable final Feed feed,
            final ApiResponse<Feed> listener) {
        final int page = feed == null ? 1 : feed.getCursor();

        hummingbird().getFollowedUsers(getAuthTokenCookieString(), username, page).enqueue(
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
        final int page = feed == null ? 1 : feed.getCursor();

        hummingbird().getFollowingUsers(getAuthTokenCookieString(), username, page).enqueue(
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

    public static void getFranchise(final String franchiseId, final ApiResponse<Franchise> listener) {
        hummingbird().getFranchise(getAuthTokenCookieString(), Constants.MIMETYPE_JSON, franchiseId)
                .enqueue(new Callback<Franchise>() {
            @Override
            public void onResponse(final Call<Franchise> call, final Response<Franchise> response) {
                if (response.isSuccessful()) {
                    listener.success(response.body());
                } else {
                    listener.failure(retrieveErrorInfo(response));
                }
            }

            @Override
            public void onFailure(final Call<Franchise> call, final Throwable t) {
                Timber.e(TAG, "get franchise (" + franchiseId + ") failed", t);
                listener.failure(null);
            }
        });
    }

    public static void getGroup(final String groupId, final ApiResponse<GroupDigest> listener) {
        hummingbird().getGroup(getAuthTokenCookieString(), Constants.MIMETYPE_JSON, groupId)
                .enqueue(new Callback<GroupDigest>() {
            private GroupDigest mGroupDigest;

            @Override
            public void onResponse(final Call<GroupDigest> call,
                    final Response<GroupDigest> response) {
                if (response.isSuccessful()) {
                    mGroupDigest = response.body();
                }

                if (mGroupDigest == null) {
                    listener.failure(retrieveErrorInfo(response));
                } else {
                    Threading.runOnBackground(new Runnable() {
                        @Override
                        public void run() {
                            mGroupDigest.hydrate();

                            Threading.runOnUi(new Runnable() {
                                @Override
                                public void run() {
                                    listener.success(mGroupDigest);
                                }
                            });
                        }
                    });
                }
            }

            @Override
            public void onFailure(final Call<GroupDigest> call, final Throwable t) {
                Timber.e(TAG, "get group (" + groupId + ") failed", t);
                listener.failure(null);
            }
        });
    }

    public static void getGroupMembers(final String groupId, final ApiResponse<Feed> listener) {
        getGroupMembers(groupId, null, listener);
    }

    public static void getGroupMembers(final String groupId, @Nullable final Feed feed,
            final ApiResponse<Feed> listener) {
        final int page = feed == null ? 1 : feed.getCursor();

        hummingbird().getGroupMembers(getAuthTokenCookieString(), groupId, page).enqueue(
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
                Timber.e(TAG, "get group (" + groupId + ") (page " + page + ") members failed", t);
                listener.failure(null);
            }
        });
    }

    public static void getGroupStories(final String groupId, final ApiResponse<Feed> listener) {
        getGroupStories(groupId, null, listener);
    }

    public static void getGroupStories(final String groupId, @Nullable final Feed feed,
            final ApiResponse<Feed> listener) {
        final int page = feed == null ? 1 : feed.getCursor();

        hummingbird().getGroupStories(getAuthTokenCookieString(), groupId, page).enqueue(
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
                Timber.e(TAG, "get group (" + groupId + ") (page " + page + ") stories failed", t);
                listener.failure(null);
            }
        });
    }

    public static void getLibraryEntries(final String username,
            @Nullable final WatchingStatus watchingStatus,
            final ApiResponse<ArrayList<AnimeLibraryEntry>> listener) {
        hummingbird().getLibraryEntries(username, watchingStatus).enqueue(
                new Callback<ArrayList<AnimeLibraryEntry>>() {
            @Override
            public void onResponse(final Call<ArrayList<AnimeLibraryEntry>> call,
                    final Response<ArrayList<AnimeLibraryEntry>> response) {
                if (response.isSuccessful()) {
                    listener.success(response.body());
                } else {
                    listener.failure(retrieveErrorInfo(response));
                }
            }

            @Override
            public void onFailure(final Call<ArrayList<AnimeLibraryEntry>> call, final Throwable t) {
                Timber.e(TAG, "get library entries for user (" + username + ") failed", t);
                listener.failure(null);
            }
        });
    }

    public static void getMangaDigest(final String mangaId,
            final ApiResponse<MangaDigest> listener) {
        hummingbird().getMangaDigest(getAuthTokenCookieString(), mangaId).enqueue(
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
        final int page = feed == null ? 1 : feed.getCursor();

        hummingbird().getNewsFeed(getAuthTokenCookieString(), Boolean.TRUE, page).enqueue(
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
        hummingbird().getNotifications(getAuthTokenCookieString(), Constants.MIMETYPE_JSON)
                .enqueue(new Callback<Feed>() {
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
        getSubstories(storyId, null, listener);
    }

    public static void getSubstories(final String storyId, @Nullable final Feed feed,
            final ApiResponse<Feed> listener) {
        final int page = feed == null ? 1 : feed.getCursor();

        hummingbird().getSubstories(getAuthTokenCookieString(), storyId, page).enqueue(
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
                        Timber.e(TAG, "get substories (" + storyId + ") (page " + page + ") failed", t);
                        listener.failure(null);
                    }
                });
    }

    public static void getUser(final String username, final ApiResponse<User> listener) {
        hummingbird().getUser(getAuthTokenCookieString(), Constants.MIMETYPE_JSON, username)
                .enqueue(new Callback<User>() {
            @Override
            public void onResponse(final Call<User> call, final Response<User> response) {
                User body = null;

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
            public void onFailure(final Call<User> call, final Throwable t) {
                Timber.e(TAG, "get user (" + username + ") failed", t);
                listener.failure(null);
            }
        });
    }

    public static void getUserDigest(final String username, final ApiResponse<UserDigest> listener) {
        hummingbird().getUserDigest(getAuthTokenCookieString(), username).enqueue(
                new Callback<UserDigest>() {
            private UserDigest mBody;

            private void fetchUser() {
                getUser(mBody.getInfo().getId(), new ApiResponse<User>() {
                    @Override
                    public void failure(@Nullable final ErrorInfo error) {
                        listener.failure(error);
                    }

                    @Override
                    public void success(final User user) {
                        mBody.setUser(user);
                        hydrate();
                    }
                });
            }

            private void hydrate() {
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

            @Override
            public void onResponse(final Call<UserDigest> call,
                    final Response<UserDigest> response) {
                if (response.isSuccessful()) {
                    mBody = response.body();
                }

                if (mBody == null) {
                    listener.failure(retrieveErrorInfo(response));
                } else if (mBody.isMissingUser()) {
                    fetchUser();
                } else {
                    hydrate();
                }
            }

            @Override
            public void onFailure(final Call<UserDigest> call, final Throwable t) {
                Timber.e(TAG, "get user (" + username + ") digest failed", t);
                listener.failure(null);
            }
        });
    }

    public static void getUserGroups(final String userId, final ApiResponse<Feed> listener) {
        getUserGroups(userId, null, listener);
    }

    public static void getUserGroups(final String userId, @Nullable final Feed feed,
            final ApiResponse<Feed> listener) {
        final int page = feed == null ? 1 : feed.getCursor();

        hummingbird().getUserGroups(getAuthTokenCookieString(), Constants.MIMETYPE_JSON, userId,
                page).enqueue(new Callback<Feed>() {
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
                Timber.e(TAG, "get user (" + userId + ") groups failed", t);
                listener.failure(null);
            }
        });
    }

    public static void getUserReviews(final String userId, final ApiResponse<Feed> listener) {
        getUserReviews(userId, null, listener);
    }

    public static void getUserReviews(final String userId, @Nullable final Feed feed,
            final ApiResponse<Feed> listener) {
        final int page = feed == null ? 1 : feed.getCursor();

        hummingbird().getUserReviews(getAuthTokenCookieString(), Constants.MIMETYPE_JSON, userId,
                page).enqueue(new Callback<Feed>() {
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
                Timber.e(TAG, "get user (" + userId + ") reviews failed", t);
                listener.failure(null);
            }
        });
    }

    public static void getUserStories(final String username, final ApiResponse<Feed> listener) {
        getUserStories(username, null, listener);
    }

    public static void getUserStories(final String username, @Nullable final Feed feed,
            final ApiResponse<Feed> listener) {
        final int page = feed == null ? 1 : feed.getCursor();

        hummingbird().getUserStories(getAuthTokenCookieString(), username, page).enqueue(
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
                if (oldFeed == null) {
                    newFeed.hydrate();
                } else {
                    oldFeed.merge(newFeed);
                    oldFeed.hydrate();
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

    public static void favoriteQuote(final AnimeDigest.Quote quote) {
        hummingbird().favoriteQuote(getAuthTokenCookieString(), quote.getId(), quote.toJson())
                .enqueue(new Callback<Void>() {
            @Override
            public void onResponse(final Call<Void> call, final Response<Void> response) {
                // do nothing
            }

            @Override
            public void onFailure(final Call<Void> call, final Throwable t) {
                Timber.e(TAG, "like quote (" + quote.getId() + ") failed", t);
            }
        });
    }

    private static HummingbirdApi hummingbird() {
        return RetrofitUtils.getHummingbirdApi();
    }

    public static void likeStory(final CommentStory story) {
        hummingbird().likeStory(getAuthTokenCookieString(), story.getId(), story.getLikeJson())
                .enqueue(new Callback<Void>() {
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
        hummingbird().postComment(getAuthTokenCookieString(), commentPost.toJson()).enqueue(
                new Callback<Void>() {
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

    public static void postToFeed(final FeedPost feedPost, final ApiResponse<Void> listener) {
        hummingbird().postToFeed(getAuthTokenCookieString(), feedPost.toJson()).enqueue(
                new Callback<Void>() {
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
                Timber.e(TAG, "post to feed (" + feedPost.getUserId() + ") failed", t);
                listener.failure(null);
            }
        });
    }

    public static void removeLibraryEntry(final AnimeLibraryEntry libraryEntry,
            final ApiResponse<Void> listener) {
        removeLibraryEntry(libraryEntry.getId(), listener);
    }

    public static void removeLibraryEntry(final String libraryEntryId,
            final ApiResponse<Void> listener) {
        hummingbird().deleteLibraryEntry(getAuthTokenCookieString(), libraryEntryId).enqueue(
                new Callback<Void>() {
            @Override
            public void onResponse(final Call<Void> call, final Response<Void> response) {
                if (response.isSuccessful()) {
                    listener.success(response.body());
                } else {
                    listener.failure(retrieveErrorInfo(response));
                }
            }

            @Override
            public void onFailure(final Call<Void> call, final Throwable t) {
                Timber.e(TAG, "remove library entry (" + libraryEntryId + ") failed", t);
                listener.failure(null);
            }
        });
    }

    @Nullable
    private static ErrorInfo retrieveErrorInfo(@Nullable final Response response) {
        if (response == null) {
            Timber.e(TAG, "can't retrieve error info from null response");
            return null;
        }

        final Retrofit retrofit = RetrofitUtils.getHummingbirdRetrofit();
        final Converter<ResponseBody, ErrorInfo> converter = retrofit
                .responseBodyConverter(ErrorInfo.class, new Annotation[0]);

        ErrorInfo errorInfo = null;

        try {
            final ResponseBody errorBody = response.errorBody();
            errorInfo = converter.convert(errorBody);

            if (errorInfo != null) {
                Timber.e(TAG, "retrieved error info: \"" + errorInfo.getError() + '"');
            }
        } catch (final Exception e) {
            Timber.e(TAG, "couldn't retrieve error info from response", e);
        }

        return errorInfo;
    }

    public static void search(final SearchScope scope, final String query,
            final ApiResponse<SearchBundle> listener) {
        hummingbird().search(scope, SearchDepth.INSTANT, query).enqueue(
                new Callback<SearchBundle>() {
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

    public static void toggleFollowingOfUser(final String userId) {
        hummingbird().toggleFollowingOfUser(getAuthTokenCookieString(), userId).enqueue(
                new Callback<Void>() {
            @Override
            public void onResponse(final Call<Void> call, final Response<Void> response) {
                // do nothing
            }

            @Override
            public void onFailure(final Call<Void> call, final Throwable t) {
                Timber.e(TAG, "toggle following of user (" + userId + ") failed", t);
            }
        });
    }

    public static void updateLibraryEntry(final String libraryEntryId,
            final AnimeLibraryUpdate libraryUpdate, final ApiResponse<Void> listener) {
        hummingbird().updateLibraryEntry(getAuthTokenCookieString(), Constants.MIMETYPE_JSON,
                libraryEntryId, libraryUpdate.toJson()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(final Call<Void> call, final Response<Void> response) {
                if (response.isSuccessful()) {
                    listener.success(response.body());
                } else {
                    listener.failure(retrieveErrorInfo(response));
                }
            }

            @Override
            public void onFailure(final Call<Void> call, final Throwable t) {
                Timber.e(TAG, "update library entry (" + libraryEntryId + ") failed", t);
                listener.failure(null);
            }
        });
    }

    private static WebsiteApi website() {
        return RetrofitUtils.getWebsiteApi();
    }

}
