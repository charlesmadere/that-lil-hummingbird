package com.charlesmadere.hummingbird.networking;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.ThatLilHummingbird;
import com.charlesmadere.hummingbird.misc.Constants;
import com.charlesmadere.hummingbird.misc.CurrentUser;
import com.charlesmadere.hummingbird.misc.JsoupUtils;
import com.charlesmadere.hummingbird.misc.MiscUtils;
import com.charlesmadere.hummingbird.misc.OkHttpUtils;
import com.charlesmadere.hummingbird.misc.RetrofitUtils;
import com.charlesmadere.hummingbird.misc.ThreadUtils;
import com.charlesmadere.hummingbird.misc.Timber;
import com.charlesmadere.hummingbird.models.Anime;
import com.charlesmadere.hummingbird.models.AnimeDigest;
import com.charlesmadere.hummingbird.models.AnimeLibraryEntry;
import com.charlesmadere.hummingbird.models.AnimeLibraryEntryResponse;
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
import com.charlesmadere.hummingbird.models.Liker;
import com.charlesmadere.hummingbird.models.MangaDigest;
import com.charlesmadere.hummingbird.models.MangaLibraryEntry;
import com.charlesmadere.hummingbird.models.MangaLibraryEntryResponse;
import com.charlesmadere.hummingbird.models.MangaLibraryUpdate;
import com.charlesmadere.hummingbird.models.ReadingStatus;
import com.charlesmadere.hummingbird.models.SearchBundle;
import com.charlesmadere.hummingbird.models.SearchDepth;
import com.charlesmadere.hummingbird.models.SearchScope;
import com.charlesmadere.hummingbird.models.User;
import com.charlesmadere.hummingbird.models.UserDigest;
import com.charlesmadere.hummingbird.models.WatchingStatus;
import com.charlesmadere.hummingbird.preferences.Preferences;
import com.google.gson.JsonObject;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bolts.Continuation;
import bolts.Task;
import bolts.TaskCompletionSource;
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
            final ApiResponse<AnimeLibraryEntryResponse> listener) {
        HUMMINGBIRD.addAnimeLibraryEntry(libraryUpdate.toJson()).enqueue(
                new Callback<AnimeLibraryEntryResponse>() {
            @Override
            public void onResponse(final Call<AnimeLibraryEntryResponse> call,
                    final Response<AnimeLibraryEntryResponse> response) {
                final AnimeLibraryEntryResponse body = response.isSuccessful() ?
                        response.body() : null;

                if (body == null) {
                    listener.failure(retrieveErrorInfo(response));
                } else {
                    listener.success(body);
                }
            }

            @Override
            public void onFailure(final Call<AnimeLibraryEntryResponse> call, final Throwable t) {
                Timber.e(TAG, "add anime library entry failed", t);
                listener.failure(null);
            }
        });
    }

    public static void addMangaLibraryEntry(final MangaLibraryUpdate libraryUpdate,
            final ApiResponse<MangaLibraryEntryResponse> listener) {
        HUMMINGBIRD.addMangaLibraryEntry(libraryUpdate.toJson()).enqueue(
                new Callback<MangaLibraryEntryResponse>() {
            @Override
            public void onResponse(final Call<MangaLibraryEntryResponse> call,
                    final Response<MangaLibraryEntryResponse> response) {
                final MangaLibraryEntryResponse body = response.isSuccessful() ?
                        response.body() : null;

                if (body == null) {
                    listener.failure(retrieveErrorInfo(response));
                } else {
                    listener.success(body);
                }
            }

            @Override
            public void onFailure(final Call<MangaLibraryEntryResponse> call, final Throwable t) {
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
                    ThreadUtils.runOnBackground(new Runnable() {
                        @Override
                        public void run() {
                            mBody.hydrate();

                            ThreadUtils.runOnUi(new Runnable() {
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

    public static void getAnimeLibraryEntries(final String userId,
            @Nullable final WatchingStatus watchingStatus, final ApiResponse<Feed> listener) {
        getAnimeLibraryEntries(userId, watchingStatus, null, listener);
    }

    public static void getAnimeLibraryEntries(final String userId,
            @Nullable final WatchingStatus watchingStatus, @Nullable final Feed feed,
            final ApiResponse<Feed> listener) {
        final String ws = watchingStatus == null ? null : watchingStatus.getPostValue();

        HUMMINGBIRD.getAnimeLibraryEntries(userId, ws).enqueue(new Callback<Feed>() {
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
                Timber.e(TAG, "get anime library entries (" + userId + ") (watching status "
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

    public static void getFollowedUsers(final String userId, final ApiResponse<Feed> listener) {
        getFollowedUsers(userId, null, listener);
    }

    public static void getFollowedUsers(final String userId, @Nullable final Feed feed,
            final ApiResponse<Feed> listener) {
        final int page = feed == null ? 1 : feed.getCursor();

        HUMMINGBIRD.getFollowedUsers(userId, page).enqueue(new Callback<Feed>() {
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
                Timber.e(TAG, "get followed users (" + userId + ") (page " + page + ") failed", t);
                listener.failure(null);
            }
        });
    }

    public static void getFollowingUsers(final String userId, final ApiResponse<Feed> listener) {
        getFollowingUsers(userId, null, listener);
    }

    public static void getFollowingUsers(final String userId, @Nullable final Feed feed,
            final ApiResponse<Feed> listener) {
        final int page = feed == null ? 1 : feed.getCursor();

        HUMMINGBIRD.getFollowingUsers(userId, page).enqueue(new Callback<Feed>() {
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
                Timber.e(TAG, "get following users (" + userId + ") (page " + page + ") failed", t);
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

    public static void getGroupDigest(final String groupId, final ApiResponse<GroupDigest> listener) {
        HUMMINGBIRD.getGroupDigest(groupId).enqueue(new Callback<GroupDigest>() {
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
                    ThreadUtils.runOnBackground(new Runnable() {
                        @Override
                        public void run() {
                            mGroupDigest.hydrate();

                            ThreadUtils.runOnUi(new Runnable() {
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

    public static void getLikers(final String storyId, final ApiResponse<ArrayList<Liker>> listener) {
        getLikers(storyId, 1, listener);
    }

    public static void getLikers(final String storyId, final int page,
            final ApiResponse<ArrayList<Liker>> listener) {
        HUMMINGBIRD.getLikers(storyId, page).enqueue(new Callback<ArrayList<Liker>>() {
            @Override
            public void onResponse(final Call<ArrayList<Liker>> call,
                    final Response<ArrayList<Liker>> response) {
                if (response.isSuccessful()) {
                    listener.success(response.body());
                } else {
                    listener.failure(retrieveErrorInfo(response));
                }
            }

            @Override
            public void onFailure(final Call<ArrayList<Liker>> call, final Throwable t) {
                Timber.e(TAG, "get likers (" + storyId + ") (page " + page + ") failed", t);
                listener.failure(null);
            }
        });
    }

    public static void getMangaDigest(final String mangaId, final ApiResponse<MangaDigest> listener) {
        HUMMINGBIRD.getMangaDigest(mangaId).enqueue(new Callback<MangaDigest>() {
            private MangaDigest mBody;

            @Override
            public void onResponse(final Call<MangaDigest> call,
                    final Response<MangaDigest> response) {
                if (response.isSuccessful()) {
                    mBody = response.body();
                }

                if (mBody == null) {
                    listener.failure(retrieveErrorInfo(response));
                } else {
                    ThreadUtils.runOnBackground(new Runnable() {
                        @Override
                        public void run() {
                            mBody.hydrate();

                            ThreadUtils.runOnUi(new Runnable() {
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
            public void onFailure(final Call<MangaDigest> call, final Throwable t) {
                Timber.e(TAG, "get manga digest (" + mangaId + ") failed", t);
                listener.failure(null);
            }
        });
    }

    public static void getMangaLibraryEntries(final String userId,
            @Nullable final ReadingStatus readingStatus, final ApiResponse<Feed> listener) {
        getMangaLibraryEntries(userId, readingStatus, null, listener);
    }

    public static void getMangaLibraryEntries(final String userId,
            @Nullable final ReadingStatus readingStatus, @Nullable final Feed feed,
            final ApiResponse<Feed> listener) {
        final String rs = readingStatus == null ? null : readingStatus.getPostValue();

        HUMMINGBIRD.getMangaLibraryEntries(userId, rs).enqueue(new Callback<Feed>() {
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
                Timber.e(TAG, "get manga library entries (" + userId + ") (reading status "
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

    public static void getStory(final String storyId, final ApiResponse<Feed> listener) {
        HUMMINGBIRD.getStory(storyId).enqueue(new Callback<Feed>() {
            @Override
            public void onResponse(final Call<Feed> call, final Response<Feed> response) {
                final Feed body = response.isSuccessful() ? response.body() : null;

                if (body == null) {
                    listener.failure(retrieveErrorInfo(response));
                } else {
                    hydrateFeed(body, null, listener);
                }
            }

            @Override
            public void onFailure(final Call<Feed> call, final Throwable t) {
                Timber.e(TAG, "get story (" + storyId + ") failed", t);
                listener.failure(null);
            }
        });
    }

    public static void getStoryFromNotification(final String notificationId,
            final ApiResponse<Feed> listener) {
        HUMMINGBIRD.getStoryFromNotification(notificationId).enqueue(new Callback<Feed>() {
            @Override
            public void onResponse(final Call<Feed> call, final Response<Feed> response) {
                final Feed body = response.isSuccessful() ? response.body() : null;

                if (body == null) {
                    listener.failure(retrieveErrorInfo(response));
                } else {
                    hydrateFeed(body, null, listener);
                }
            }

            @Override
            public void onFailure(final Call<Feed> call, final Throwable t) {
                Timber.e(TAG, "get story from notification (" + notificationId + ") failed", t);
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

    public static void getUser(final String userId, final ApiResponse<User> listener) {
        HUMMINGBIRD.getUser(userId).enqueue(new Callback<User>() {
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
                Timber.e(TAG, "get user (" + userId + ") failed", t);
                listener.failure(null);
            }
        });
    }

    public static void getUserDigest(final String userId, final ApiResponse<UserDigest> listener) {
        HUMMINGBIRD.getUserDigest(userId).enqueue(new Callback<UserDigest>() {
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
                ThreadUtils.runOnBackground(new Runnable() {
                    @Override
                    public void run() {
                        mBody.hydrate();

                        ThreadUtils.runOnUi(new Runnable() {
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
                Timber.e(TAG, "get user digest (" + userId + ") failed", t);
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

    public static void getUserReviews(final String userId, final ApiResponse<Feed> listener) {
        getUserReviews(userId, null, listener);
    }

    public static void getUserReviews(final String userId, @Nullable final Feed feed,
            final ApiResponse<Feed> listener) {
        final int page = feed == null ? 1 : feed.getCursor();

        HUMMINGBIRD.getUserReviews(userId, page).enqueue(new Callback<Feed>() {
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
                Timber.e(TAG, "get user reviews (" + userId + ") failed", t);
                listener.failure(null);
            }
        });
    }

    public static void getUserStories(final String userId, final ApiResponse<Feed> listener) {
        getUserStories(userId, null, listener);
    }

    public static void getUserStories(final String userId, @Nullable final Feed feed,
            final ApiResponse<Feed> listener) {
        final int page = feed == null ? 1 : feed.getCursor();

        HUMMINGBIRD.getUserStories(userId, page).enqueue(new Callback<Feed>() {
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
                Timber.e(TAG, "get user stories (" + userId + ") failed", t);
                listener.failure(null);
            }
        });
    }

    private static void hydrateFeed(@NonNull final Feed newFeed, @Nullable final Feed oldFeed,
            @NonNull final ApiResponse<Feed> listener) {
        ThreadUtils.runOnBackground(new Runnable() {
            @Override
            public void run() {
                if (oldFeed == null) {
                    newFeed.hydrate();
                } else {
                    oldFeed.merge(newFeed);
                    oldFeed.hydrate();
                }

                ThreadUtils.runOnUi(new Runnable() {
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

    public static void joinGroup(final String groupId) {
        JsonObject groupMember = new JsonObject();
        groupMember.addProperty("group_id", groupId);
        groupMember.addProperty("user_id", CurrentUser.get().getUserId());

        JsonObject body = new JsonObject();
        body.add("group_member", groupMember);

        HUMMINGBIRD.joinGroup(body).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(final Call<Void> call, final Response<Void> response) {
                // do nothing
            }

            @Override
            public void onFailure(final Call<Void> call, final Throwable t) {
                Timber.e(TAG, "join group (" + groupId + ") failed", t);
            }
        });
    }

    public static void leaveGroup(final String currentGroupMemberId) {
        HUMMINGBIRD.leaveGroup(currentGroupMemberId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(final Call<Void> call, final Response<Void> response) {
                // do nothing
            }

            @Override
            public void onFailure(final Call<Void> call, final Throwable t) {
                Timber.e(TAG, "leave group failed", t);
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
        TaskCompletionSource<Void> tcs = new TaskCompletionSource<>();
        tcs.getTask().continueWithTask(new Continuation<Void, Task<String>>() {
            @Override
            public Task<String> then(final Task<Void> task) throws Exception {
                Timber.d(TAG, "attempting to retrieve sign in page...");

                final Response<ResponseBody> response = HUMMINGBIRD.getSignInPage().execute();

                if (response.isSuccessful()) {
                    Timber.d(TAG, "sign in page was retrieved");
                    return Task.forResult(response.body().string());
                } else {
                    Timber.e(TAG, "failed to retrieve sign in page");
                    throw new ServerException(retrieveErrorInfo(response));
                }
            }
        }, Task.BACKGROUND_EXECUTOR).onSuccessTask(new Continuation<String, Task<Void>>() {
            @Override
            public Task<Void> then(final Task<String> task) throws Exception {
                Timber.d(TAG, "attempting to retrieve CSRF token...");

                final String signInPage = task.getResult();
                final String csrfToken = JsoupUtils.getCsrfToken(signInPage);

                if (TextUtils.isEmpty(csrfToken)) {
                    Timber.e(TAG, "failed to retrieve CSRF token");
                    throw new ServerException();
                } else {
                    Timber.d(TAG, "CSRF token was retrieved");
                    Preferences.Account.CsrfToken.set(csrfToken);
                    return Task.forResult(null);
                }
            }
        }, Task.BACKGROUND_EXECUTOR).onSuccessTask(new Continuation<Void, Task<Void>>() {
            @Override
            public Task<Void> then(final Task<Void> task) throws Exception {
                Timber.d(TAG, "attempting to sign in...");

                final Response<Void> response = HUMMINGBIRD.signIn(username, password).execute();

                if (response.isSuccessful()) {
                    Timber.d(TAG, "successfully signed in");
                    return Task.forResult(null);
                } else {
                    Timber.e(TAG, "failed to sign in");
                    throw new SignInException(retrieveErrorInfo(response));
                }
            }
        }, Task.BACKGROUND_EXECUTOR).onSuccess(new Continuation<Void, Void>() {
            @Override
            public Void then(final Task<Void> task) throws Exception {
                Timber.d(TAG, "verifying that sign in succeeded by checking cookies...");

                final HttpUrl url = new HttpUrl.Builder()
                        .scheme(Constants.HTTPS)
                        .host(Constants.HUMMINGBIRD_HOST)
                        .build();

                final List<Cookie> cookies = OkHttpUtils.getCookieJar().loadForRequest(url);

                if (cookies == null || cookies.isEmpty()) {
                    Timber.e(TAG, "sign in was successful but no cookies were set");
                    throw new ServerException();
                }

                for (final Cookie cookie : cookies) {
                    if (Constants.TOKEN.equalsIgnoreCase(cookie.name())) {
                        Timber.d(TAG, "sign in was successful");
                        Preferences.Account.Username.set(username);
                        return null;
                    }
                }

                Timber.e(TAG, "sign in failed because no \"" + Constants.TOKEN + "\" cookie was found");
                throw new ServerException();
            }
        }, Task.BACKGROUND_EXECUTOR).continueWith(new Continuation<Void, Void>() {
            @Override
            public Void then(final Task<Void> task) throws Exception {
                if (task.isCancelled() || task.isFaulted()) {
                    final Exception exception = task.getError();
                    Timber.e(TAG, "clearing cookies and account preferences due to sign in error",
                            exception);

                    OkHttpUtils.getCookieJar().clear();
                    Preferences.Account.eraseAll();

                    if (exception instanceof ServerException) {
                        ErrorInfo errorInfo = ((ServerException) exception).getErrorInfo();

                        if (errorInfo == null) {
                            errorInfo = new ErrorInfo(ThatLilHummingbird.get().getString(
                                    R.string.error_logging_in_server));
                        }

                        listener.failure(errorInfo);
                    } else if (exception instanceof SignInException) {
                        listener.failure(((SignInException) exception).getErrorInfo());
                    } else {
                        listener.failure(null);
                    }
                } else {
                    listener.success(null);
                }

                return null;
            }
        }, Task.UI_THREAD_EXECUTOR);

        tcs.setResult(null);
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
            final AnimeLibraryUpdate libraryUpdate, final ApiResponse<AnimeLibraryEntryResponse> listener) {
        HUMMINGBIRD.updateAnimeLibraryEntry(libraryEntryId, libraryUpdate.toJson()).enqueue(
                new Callback<AnimeLibraryEntryResponse>() {
            @Override
            public void onResponse(final Call<AnimeLibraryEntryResponse> call,
                    final Response<AnimeLibraryEntryResponse> response) {
                final AnimeLibraryEntryResponse body = response.isSuccessful() ?
                        response.body() : null;

                if (body == null) {
                    listener.failure(retrieveErrorInfo(response));
                } else {
                    listener.success(body);
                }
            }

            @Override
            public void onFailure(final Call<AnimeLibraryEntryResponse> call, final Throwable t) {
                Timber.e(TAG, "update anime library entry (" + libraryEntryId + ") failed", t);
                listener.failure(null);
            }
        });
    }

    public static void updateMangaLibraryEntry(final String libraryEntryId,
            final MangaLibraryUpdate libraryUpdate, final ApiResponse<MangaLibraryEntryResponse> listener) {
        HUMMINGBIRD.updateMangaLibraryEntry(libraryEntryId, libraryUpdate.toJson()).enqueue(
                new Callback<MangaLibraryEntryResponse>() {
            @Override
            public void onResponse(final Call<MangaLibraryEntryResponse> call,
                    final Response<MangaLibraryEntryResponse> response) {
                final MangaLibraryEntryResponse body = response.isSuccessful() ?
                        response.body() : null;

                if (body == null) {
                    listener.failure(retrieveErrorInfo(response));
                } else {
                    listener.success(body);
                }
            }

            @Override
            public void onFailure(final Call<MangaLibraryEntryResponse> call, final Throwable t) {
                Timber.e(TAG, "update manga library entry (" + libraryEntryId + ") failed", t);
                listener.failure(null);
            }
        });
    }

}
