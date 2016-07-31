package com.charlesmadere.hummingbird.networking;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.charlesmadere.hummingbird.misc.Constants;
import com.charlesmadere.hummingbird.misc.CurrentUser;
import com.charlesmadere.hummingbird.misc.JsoupUtils;
import com.charlesmadere.hummingbird.misc.MiscUtils;
import com.charlesmadere.hummingbird.misc.OkHttpUtils;
import com.charlesmadere.hummingbird.misc.RetrofitUtils;
import com.charlesmadere.hummingbird.misc.Threading;
import com.charlesmadere.hummingbird.misc.Timber;
import com.charlesmadere.hummingbird.models.AddAnimeLibraryEntryResponse;
import com.charlesmadere.hummingbird.models.AddMangaLibraryEntryResponse;
import com.charlesmadere.hummingbird.models.Anime;
import com.charlesmadere.hummingbird.models.AnimeDigest;
import com.charlesmadere.hummingbird.models.AnimeLibraryEntry;
import com.charlesmadere.hummingbird.models.AnimeLibraryUpdate;
import com.charlesmadere.hummingbird.models.AnimeWrapper;
import com.charlesmadere.hummingbird.models.AppNews;
import com.charlesmadere.hummingbird.models.CommentPost;
import com.charlesmadere.hummingbird.models.CommentStory;
import com.charlesmadere.hummingbird.models.ErrorInfo;
import com.charlesmadere.hummingbird.models.Feed;
import com.charlesmadere.hummingbird.models.FeedPost;
import com.charlesmadere.hummingbird.models.Franchise;
import com.charlesmadere.hummingbird.models.GroupDigest;
import com.charlesmadere.hummingbird.models.MangaDigest;
import com.charlesmadere.hummingbird.models.MangaLibraryEntry;
import com.charlesmadere.hummingbird.models.MangaLibraryUpdate;
import com.charlesmadere.hummingbird.models.ReadingStatus;
import com.charlesmadere.hummingbird.models.SearchBundle;
import com.charlesmadere.hummingbird.models.SearchDepth;
import com.charlesmadere.hummingbird.models.SearchScope;
import com.charlesmadere.hummingbird.models.User;
import com.charlesmadere.hummingbird.models.UserDigest;
import com.charlesmadere.hummingbird.models.WatchingStatus;
import com.charlesmadere.hummingbird.preferences.Preferences;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Cookie;
import okhttp3.HttpUrl;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;

public final class Api {

    private static final String TAG = "Api";

    private static final HummingbirdApi HUMMINGBIRD = RetrofitUtils.getHummingbirdApi();
    private static final WebsiteApi WEBSITE = RetrofitUtils.getWebsiteApi();


    public static void addAnimeLibraryEntry(final AnimeLibraryUpdate libraryUpdate,
            final ApiResponse<AddAnimeLibraryEntryResponse> listener) {
        HUMMINGBIRD.addAnimeLibraryEntry(libraryUpdate.toJson()).enqueue(
                new Callback<AddAnimeLibraryEntryResponse>() {
            @Override
            public void onResponse(final Call<AddAnimeLibraryEntryResponse> call,
                    final Response<AddAnimeLibraryEntryResponse> response) {
                final AddAnimeLibraryEntryResponse body = response.isSuccessful() ?
                        response.body() : null;

                if (body == null) {
                    listener.failure(retrieveErrorInfo(response));
                } else {
                    listener.success(body);
                }
            }

            @Override
            public void onFailure(final Call<AddAnimeLibraryEntryResponse> call,
                    final Throwable t) {
                Timber.e(TAG, "add anime library entry failed", t);
                listener.failure(null);
            }
        });
    }

    public static void addMangaLibraryEntry(final MangaLibraryUpdate libraryUpdate,
            final ApiResponse<AddMangaLibraryEntryResponse> listener) {
        HUMMINGBIRD.addMangaLibraryEntry(libraryUpdate.toJson()).enqueue(
                new Callback<AddMangaLibraryEntryResponse>() {
            @Override
            public void onResponse(final Call<AddMangaLibraryEntryResponse> call,
                    final Response<AddMangaLibraryEntryResponse> response) {
                final AddMangaLibraryEntryResponse body = response.isSuccessful() ?
                        response.body() : null;

                if (body == null) {
                    listener.failure(retrieveErrorInfo(response));
                } else {
                    listener.success(body);
                }
            }

            @Override
            public void onFailure(final Call<AddMangaLibraryEntryResponse> call,
                    final Throwable t) {
                Timber.e(TAG, "add manga library entry failed", t);
                listener.failure(null);
            }
        });
    }

    public static void deleteAnimeLibraryEntry(final AnimeLibraryEntry libraryEntry,
            final ApiResponse<Void> listener) {
        deleteAnimeLibraryEntry(libraryEntry.getId(), listener);
    }

    public static void deleteAnimeLibraryEntry(final String libraryEntryId,
            final ApiResponse<Void> listener) {
        HUMMINGBIRD.deleteAnimeLibraryEntry(libraryEntryId).enqueue(new Callback<Void>() {
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
                Timber.e(TAG, "delete anime library entry (" + libraryEntryId + ") failed", t);
                listener.failure(null);
            }
        });
    }

    public static void deleteMangaLibraryEntry(final MangaLibraryEntry libraryEntry,
            final ApiResponse<Void> listener) {
        deleteMangaLibraryEntry(libraryEntry.getId(), listener);
    }

    public static void deleteMangaLibraryEntry(final String libraryEntryId,
            final ApiResponse<Void> listener) {
        HUMMINGBIRD.deleteMangaLibraryEntry(libraryEntryId).enqueue(new Callback<Void>() {
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
                Timber.e(TAG, "delete manga library entry (" + libraryEntryId + ") failed", t);
                listener.failure(null);
            }
        });
    }

    public static void deleteStory(final String storyId, final ApiResponse<Boolean> listener) {
        HUMMINGBIRD.deleteStory(storyId).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(final Call<Boolean> call, final Response<Boolean> response) {
                final Boolean body = response.isSuccessful() ? response.body() : null;

                if (Boolean.TRUE.equals(body)) {
                    listener.success(body);
                } else {
                    listener.failure(retrieveErrorInfo(response));
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
        HUMMINGBIRD.deleteSubstory(substoryId).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(final Call<Boolean> call, final Response<Boolean> response) {
                final Boolean body = response.isSuccessful() ? response.body() : null;

                if (Boolean.TRUE.equals(body)) {
                    listener.success(body);
                } else {
                    listener.failure(retrieveErrorInfo(response));
                }
            }

            @Override
            public void onFailure(final Call<Boolean> call, final Throwable t) {
                Timber.e(TAG, "delete substory (" + substoryId + ") failed", t);
                listener.failure(null);
            }
        });
    }

    public static void getAnime(final ArrayList<String> animeIds,
            final ApiResponse<ArrayList<Anime>> listener) {
        final HashMap<String, Anime> animeMap = new HashMap<>(animeIds.size());

        for (final String animeId : animeIds) {
            animeMap.put(animeId, null);
        }

        for (final String animeId : animeIds) {
            getAnime(animeId, new ApiResponse<Anime>() {
                @Override
                public void failure(@Nullable final ErrorInfo error) {
                    animeMap.remove(animeId);
                    proceed(error);
                }

                private void proceed(@Nullable final ErrorInfo error) {
                    if (animeMap.isEmpty()) {
                        listener.failure(error);
                        return;
                    }

                    for (final Map.Entry<String, Anime> entry : animeMap.entrySet()) {
                        if (entry.getValue() == null) {
                            return;
                        }
                    }

                    listener.success(MiscUtils.toArrayList(animeMap.values()));
                }

                @Override
                public void success(final Anime anime) {
                    animeMap.put(animeId, anime);
                    proceed(null);
                }
            });
        }
    }

    public static void getAnime(final String animeId, final ApiResponse<Anime> listener) {
        HUMMINGBIRD.getAnime(animeId).enqueue(new Callback<AnimeWrapper>() {
            @Override
            public void onResponse(final Call<AnimeWrapper> call, final Response<AnimeWrapper> response) {
                final AnimeWrapper aw = response.isSuccessful() ? response.body() : null;

                if (aw == null) {
                    listener.failure(retrieveErrorInfo(response));
                } else {
                    listener.success(aw.getAnime());
                }
            }

            @Override
            public void onFailure(final Call<AnimeWrapper> call, final Throwable t) {
                Timber.e(TAG, "get anime (" + animeId + ") failed", t);
                listener.failure(null);
            }
        });
    }

    public static void getAnimeDigest(final String animeId, final ApiResponse<AnimeDigest> listener) {
        HUMMINGBIRD.getAnimeDigest(animeId).enqueue(new Callback<AnimeDigest>() {
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
                Timber.e(TAG, "get anime digest (" + animeId + ") failed", t);
                listener.failure(null);
            }
        });
    }

    public static void getAnimeLibraryEntries(final String username,
            @Nullable final WatchingStatus watchingStatus, final ApiResponse<Feed> listener) {
        getAnimeLibraryEntries(username, watchingStatus, null, listener);
    }

    public static void getAnimeLibraryEntries(final String username,
            @Nullable final WatchingStatus watchingStatus, @Nullable final Feed feed,
            final ApiResponse<Feed> listener) {
        final String ws = watchingStatus == null ? null : watchingStatus.getPostValue();

        HUMMINGBIRD.getAnimeLibraryEntries(username, ws).enqueue(new Callback<Feed>() {
            @Override
            public void onResponse(final Call<Feed> call, final Response<Feed> response) {
                final Feed body = response.isSuccessful() ? response.body() : null;

                if (body == null) {
                    listener.failure(retrieveErrorInfo(response));
                } else {
                    hydrateFeed(body, feed, listener);
                }
            }

            @Override
            public void onFailure(final Call<Feed> call, final Throwable t) {
                Timber.e(TAG, "get anime library entries (" + username + ") (watching status "
                        + ws + ") failed", t);
                listener.failure(null);
            }
        });
    }

    public static void getAppNews(final ApiResponse<ArrayList<AppNews>> listener) {
        WEBSITE.getAppNews().enqueue(new Callback<ArrayList<AppNews>>() {
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

    public static void getCsrfToken(final ApiResponse<Boolean> listener) {
        HUMMINGBIRD.getSignInPage().enqueue(new Callback<ResponseBody>() {
            private String mCsrfToken;

            @Override
            public void onResponse(final Call<ResponseBody> call, final Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Threading.runOnBackground(new Runnable() {
                        @Override
                        public void run() {
                            Timber.d(TAG, "attempting to retrieve CSRF token...");

                            try {
                                mCsrfToken = JsoupUtils.getCsrfToken(response.body().string());
                            } catch (final Exception e) {
                                Timber.e(TAG, "Exception when retrieving CSRF token", e);
                            }

                            Threading.runOnUi(new Runnable() {
                                @Override
                                public void run() {
                                    if (TextUtils.isEmpty(mCsrfToken)) {
                                        Timber.e(TAG, "CSRF token is empty");
                                        Preferences.Account.CsrfToken.delete();
                                        listener.failure(retrieveErrorInfo(response));
                                    } else {
                                        Timber.d(TAG, "CSRF token was retrieved");
                                        Preferences.Account.CsrfToken.set(mCsrfToken);
                                        listener.success(Boolean.TRUE);
                                    }
                                }
                            });
                        }
                    });
                } else {
                    Timber.w(TAG, "CSRF token response is unsuccessful");
                    listener.failure(retrieveErrorInfo(response));
                }
            }

            @Override
            public void onFailure(final Call<ResponseBody> call, final Throwable t) {
                Timber.e(TAG, "get CSRF token failed", t);
                listener.failure(null);
            }
        });
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

        HUMMINGBIRD.getFollowedUsers(username, page).enqueue(new Callback<Feed>() {
            @Override
            public void onResponse(final Call<Feed> call, final Response<Feed> response) {
                final Feed body = response.isSuccessful() ? response.body() : null;

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

        HUMMINGBIRD.getFollowingUsers(username, page).enqueue(new Callback<Feed>() {
            @Override
            public void onResponse(final Call<Feed> call, final Response<Feed> response) {
                final Feed body = response.isSuccessful() ? response.body() : null;

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
        HUMMINGBIRD.getFranchise(franchiseId).enqueue(new Callback<Franchise>() {
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
        HUMMINGBIRD.getGroup(groupId).enqueue(new Callback<GroupDigest>() {
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

        HUMMINGBIRD.getGroupMembers(groupId, page).enqueue(new Callback<Feed>() {
            @Override
            public void onResponse(final Call<Feed> call, final Response<Feed> response) {
                final Feed body = response.isSuccessful() ? response.body() : null;

                if (body == null) {
                    listener.failure(retrieveErrorInfo(response));
                } else {
                    hydrateFeed(body, feed, listener);
                }
            }

            @Override
            public void onFailure(final Call<Feed> call, final Throwable t) {
                Timber.e(TAG, "get group members (" + groupId + ") (page " + page + ") failed", t);
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

        HUMMINGBIRD.getGroupStories(groupId, page).enqueue(new Callback<Feed>() {
            @Override
            public void onResponse(final Call<Feed> call, final Response<Feed> response) {
                final Feed body = response.isSuccessful() ? response.body() : null;

                if (body == null) {
                    listener.failure(retrieveErrorInfo(response));
                } else {
                    hydrateFeed(body, feed, listener);
                }
            }

            @Override
            public void onFailure(final Call<Feed> call, final Throwable t) {
                Timber.e(TAG, "get group stories (" + groupId + ") (page " + page + ") failed", t);
                listener.failure(null);
            }
        });
    }

    public static void getMangaDigest(final String mangaId,
            final ApiResponse<MangaDigest> listener) {
        HUMMINGBIRD.getMangaDigest(mangaId).enqueue(new Callback<MangaDigest>() {
            @Override
            public void onResponse(final Call<MangaDigest> call,
                    final Response<MangaDigest> response) {
                final MangaDigest body = response.isSuccessful() ? response.body() : null;

                if (body == null) {
                    listener.failure(retrieveErrorInfo(response));
                } else {
                    listener.success(body);
                }
            }

            @Override
            public void onFailure(final Call<MangaDigest> call, final Throwable t) {
                Timber.e(TAG, "get manga digest (" + mangaId + ") failed", t);
                listener.failure(null);
            }
        });
    }

    public static void getMangaLibraryEntries(final String username,
            @Nullable final ReadingStatus readingStatus, final ApiResponse<Feed> listener) {
        getMangaLibraryEntries(username, readingStatus, null, listener);
    }

    public static void getMangaLibraryEntries(final String username,
            @Nullable final ReadingStatus readingStatus, @Nullable final Feed feed,
            final ApiResponse<Feed> listener) {
        final String rs = readingStatus == null ? null : readingStatus.getPostValue();

        HUMMINGBIRD.getMangaLibraryEntries(username, rs).enqueue(new Callback<Feed>() {
            @Override
            public void onResponse(final Call<Feed> call, final Response<Feed> response) {
                final Feed body = response.isSuccessful() ? response.body() : null;

                if (body == null) {
                    listener.failure(retrieveErrorInfo(response));
                } else {
                    hydrateFeed(body, feed, listener);
                }
            }

            @Override
            public void onFailure(final Call<Feed> call, final Throwable t) {
                Timber.e(TAG, "get manga library entries (" + username + ") (reading status "
                        + rs + ") failed", t);
                listener.failure(null);
            }
        });
    }

    public static void getNewsFeed(final ApiResponse<Feed> listener) {
        getNewsFeed(null, listener);
    }

    public static void getNewsFeed(@Nullable final Feed feed, final ApiResponse<Feed> listener) {
        final int page = feed == null ? 1 : feed.getCursor();

        HUMMINGBIRD.getNewsFeed(true, page).enqueue(new Callback<Feed>() {
            @Override
            public void onResponse(final Call<Feed> call, final Response<Feed> response) {
                final Feed body = response.isSuccessful() ? response.body() : null;

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
        getNotifications(null, listener);
    }

    public static void getNotifications(@Nullable final Feed feed, final ApiResponse<Feed> listener) {
        final int page = feed == null ? 1 : feed.getCursor();

        HUMMINGBIRD.getNotifications(page).enqueue(new Callback<Feed>() {
            @Override
            public void onResponse(final Call<Feed> call, final Response<Feed> response) {
                final Feed body = response.isSuccessful() ? response.body() : null;

                if (body == null) {
                    listener.failure(retrieveErrorInfo(response));
                } else {
                    hydrateFeed(body, feed, listener);
                }
            }

            @Override
            public void onFailure(final Call<Feed> call, final Throwable t) {
                Timber.e(TAG, "get notifications (page " + page + ") failed", t);
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

        HUMMINGBIRD.getSubstories(storyId, page).enqueue(new Callback<Feed>() {
            @Override
            public void onResponse(final Call<Feed> call, final Response<Feed> response) {
                final Feed body = response.isSuccessful() ? response.body() : null;

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
        HUMMINGBIRD.getUser(username).enqueue(new Callback<User>() {
            @Override
            public void onResponse(final Call<User> call, final Response<User> response) {
                final User body = response.isSuccessful() ? response.body() : null;

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
        HUMMINGBIRD.getUserDigest(username).enqueue(new Callback<UserDigest>() {
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
                Timber.e(TAG, "get user digest (" + username + ") failed", t);
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

        HUMMINGBIRD.getUserGroups(userId,page).enqueue(new Callback<Feed>() {
            @Override
            public void onResponse(final Call<Feed> call, final Response<Feed> response) {
                final Feed body = response.isSuccessful() ? response.body() : null;

                if (body == null) {
                    listener.failure(retrieveErrorInfo(response));
                } else {
                    hydrateFeed(body, feed, listener);
                }
            }

            @Override
            public void onFailure(final Call<Feed> call, final Throwable t) {
                Timber.e(TAG, "get user groups (" + userId + ") (page " + page + ") failed", t);
                listener.failure(null);
            }
        });
    }

    public static void getUserReviews(final String username, final ApiResponse<Feed> listener) {
        getUserReviews(username, null, listener);
    }

    public static void getUserReviews(final String username, @Nullable final Feed feed,
            final ApiResponse<Feed> listener) {
        final int page = feed == null ? 1 : feed.getCursor();

        HUMMINGBIRD.getUserReviews(username, page).enqueue(new Callback<Feed>() {
            @Override
            public void onResponse(final Call<Feed> call, final Response<Feed> response) {
                final Feed body = response.isSuccessful() ? response.body() : null;

                if (body == null) {
                    listener.failure(retrieveErrorInfo(response));
                    return;
                }

                if (!body.hasAnimeReviews()) {
                    hydrateFeed(body, feed, listener);
                    return;
                }

                final ArrayList<String> animeIds = body.getAnimeIdsNeededForAnimeReviews();

                if (animeIds == null || animeIds.isEmpty()) {
                    hydrateFeed(body, feed, listener);
                    return;
                }

                getAnime(animeIds, new ApiResponse<ArrayList<Anime>>() {
                    @Override
                    public void failure(@Nullable final ErrorInfo error) {
                        listener.failure(error);
                    }

                    @Override
                    public void success(@Nullable final ArrayList<Anime> anime) {
                        body.addAnime(anime);
                        hydrateFeed(body, feed, listener);
                    }
                });
            }

            @Override
            public void onFailure(final Call<Feed> call, final Throwable t) {
                Timber.e(TAG, "get user reviews (" + username + ") failed", t);
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

        HUMMINGBIRD.getUserStories(username, page).enqueue(new Callback<Feed>() {
            @Override
            public void onResponse(final Call<Feed> call, final Response<Feed> response) {
                final Feed body = response.isSuccessful() ? response.body() : null;

                if (body == null) {
                    listener.failure(retrieveErrorInfo(response));
                } else {
                    hydrateFeed(body, feed, listener);
                }
            }

            @Override
            public void onFailure(final Call<Feed> call, final Throwable t) {
                Timber.e(TAG, "get user stories (" + username + ") failed", t);
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

    public static void likeQuote(final AnimeDigest.Quote quote) {
        HUMMINGBIRD.likeQuote(quote.getId(), quote.toJson()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(final Call<Void> call, final Response<Void> response) {
                // do nothing
            }

            @Override
            public void onFailure(final Call<Void> call, final Throwable t) {
                Timber.e(TAG, "favorite quote (" + quote.getId() + ") failed", t);
            }
        });
    }

    public static void likeStory(final CommentStory story) {
        HUMMINGBIRD.likeStory(story.getId(), story.getLikeJson()).enqueue(new Callback<Void>() {
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
        HUMMINGBIRD.postComment(commentPost.toJson()).enqueue(new Callback<Void>() {
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
                Timber.e(TAG, "post comment (" + commentPost.getStoryId() + ") failed", t);
                listener.failure(null);
            }
        });
    }

    public static void postToFeed(final FeedPost feedPost, final ApiResponse<Void> listener) {
        HUMMINGBIRD.postToFeed(feedPost.toJson()).enqueue(new Callback<Void>() {
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

            if (errorInfo == null || TextUtils.isEmpty(errorInfo.getError())) {
                Timber.e(TAG, "retrieved error info that was null / empty");
            } else {
                Timber.e(TAG, "retrieved error info: \"" + errorInfo.getError() + '"');
            }
        } catch (final Exception e) {
            Timber.e(TAG, "couldn't retrieve error info from response", e);
        }

        return errorInfo;
    }

    public static void search(final SearchScope scope, final String query,
            final ApiResponse<SearchBundle> listener) {
        HUMMINGBIRD.search(scope, SearchDepth.INSTANT, query).enqueue(new Callback<SearchBundle>() {
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
                Timber.e(TAG, "search (scope " + scope + ") (query " + query + ") failed", t);
                listener.failure(null);
            }
        });
    }

    public static void signIn(final String username, final String password,
            final ApiResponse<Void> listener) {
        if (!Preferences.Account.CsrfToken.exists()) {
            throw new RuntimeException("CSRF token must exist before attempting sign in");
        }

        HUMMINGBIRD.signIn(username, password).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(final Call<Void> call, final Response<Void> response) {
                if (!response.isSuccessful()) {
                    listener.failure(retrieveErrorInfo(response));
                    return;
                }

                final HttpUrl url = new HttpUrl.Builder()
                        .scheme(Constants.HTTPS)
                        .host(Constants.HUMMINGBIRD_HOST)
                        .build();

                final List<Cookie> cookies = OkHttpUtils.getCookieJar().loadForRequest(url);

                if (cookies == null || cookies.isEmpty()) {
                    Timber.w(TAG, "successfully signed in but cookies are empty");
                    listener.failure(null);
                    return;
                }

                for (final Cookie cookie : cookies) {
                    if (Constants.TOKEN.equalsIgnoreCase(cookie.name())) {
                        Preferences.Account.Username.set(username);
                        listener.success(response.body());
                        return;
                    }
                }

                Timber.w(TAG, "failed logging in because no token cookie was found");
                listener.failure(null);
            }

            @Override
            public void onFailure(final Call<Void> call, final Throwable t) {
                Timber.e(TAG, "sign in failed", t);
                listener.failure(null);
            }
        });
    }

    public static void toggleFollowingOfUser(final String userId) {
        HUMMINGBIRD.toggleFollowingOfUser(userId).enqueue(new Callback<Void>() {
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

    public static void updateAnimeLibraryEntry(final String libraryEntryId,
            final AnimeLibraryUpdate libraryUpdate, final ApiResponse<Void> listener) {
        HUMMINGBIRD.updateAnimeLibraryEntry(libraryEntryId, libraryUpdate.toJson()).enqueue(
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
                Timber.e(TAG, "update anime library entry (" + libraryEntryId + ") failed", t);
                listener.failure(null);
            }
        });
    }

    public static void updateMangaLibraryEntry(final String libraryEntryId,
            final MangaLibraryUpdate libraryUpdate, final ApiResponse<Void> listener) {
        HUMMINGBIRD.updateMangaLibraryEntry(libraryEntryId, libraryUpdate.toJson()).enqueue(
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
                Timber.e(TAG, "update manga library entry (" + libraryEntryId + ") failed", t);
                listener.failure(null);
            }
        });
    }

}
