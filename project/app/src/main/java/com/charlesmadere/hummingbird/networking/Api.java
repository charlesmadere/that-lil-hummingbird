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
import com.charlesmadere.hummingbird.models.Hydratable;
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
        TaskCompletionSource<Void> tcs = new TaskCompletionSource<>();
        tcs.getTask().continueWithTask(new Continuation<Void, Task<AnimeDigest>>() {
            @Override
            public Task<AnimeDigest> then(final Task<Void> task) throws Exception {
                final Response<AnimeDigest> response = HUMMINGBIRD.getAnimeDigest(animeId).execute();
                final AnimeDigest body = response.isSuccessful() ? response.body() : null;

                if (body == null) {
                    throw new ServerException(retrieveErrorInfo(response));
                } else {
                    return hydrate(body);
                }
            }
        }, Task.BACKGROUND_EXECUTOR).continueWith(new Continuation<AnimeDigest, Void>() {
            @Override
            public Void then(final Task<AnimeDigest> task) throws Exception {
                if (task.isCancelled() || task.isFaulted()) {
                    final Exception exception = task.getError();
                    Timber.e(TAG, "get anime digest (" + animeId + ") failed", exception);
                    listener.failure(exception instanceof ServerException ?
                            ((ServerException) exception).getErrorInfo() : null);
                } else {
                    listener.success(task.getResult());
                }

                return null;
            }
        }, Task.UI_THREAD_EXECUTOR);

        tcs.setResult(null);
    }

    public static void getAnimeLibraryEntries(final String username,
            @Nullable final WatchingStatus watchingStatus, final ApiResponse<Feed> listener) {
        getAnimeLibraryEntries(username, watchingStatus, null, listener);
    }

    public static void getAnimeLibraryEntries(final String userId,
            @Nullable final WatchingStatus watchingStatus, @Nullable final Feed feed,
            final ApiResponse<Feed> listener) {
        final String ws = watchingStatus == null ? null : watchingStatus.getPostValue();

        TaskCompletionSource<Void> tcs = new TaskCompletionSource<>();
        tcs.getTask().continueWithTask(new Continuation<Void, Task<Feed>>() {
            @Override
            public Task<Feed> then(final Task<Void> task) throws Exception {
                final Response<Feed> response = HUMMINGBIRD.getAnimeLibraryEntries(userId, ws)
                        .execute();
                final Feed body = response.isSuccessful() ? response.body() : null;

                if (body == null) {
                    throw new ServerException(retrieveErrorInfo(response));
                } else {
                    return hydrate(body);
                }
            }
        }, Task.BACKGROUND_EXECUTOR).onSuccessTask(new Continuation<Feed, Task<Feed>>() {
            @Override
            public Task<Feed> then(final Task<Feed> task) throws Exception {
                return merge(task.getResult(), feed);
            }
        }, Task.BACKGROUND_EXECUTOR).continueWith(new Continuation<Feed, Void>() {
            @Override
            public Void then(final Task<Feed> task) throws Exception {
                if (task.isCancelled() || task.isFaulted()) {
                    final Exception exception = task.getError();
                    Timber.e(TAG, "get anime library entries (" + userId +
                            ") (watching status " + ws + ") failed", exception);
                    listener.failure(exception instanceof ServerException ?
                            ((ServerException) exception).getErrorInfo() : null);
                } else {
                    listener.success(task.getResult());
                }

                return null;
            }
        });

        tcs.setResult(null);
    }

    private static Task<Anime> getAnimeTask(final String animeId) {
        return new TaskCompletionSource<Void>().getTask().continueWith(
                new Continuation<Void, Anime>() {
            @Override
            public Anime then(final Task<Void> task) throws Exception {
                final Response<AnimeWrapper> response = HUMMINGBIRD.getAnime(animeId).execute();
                final AnimeWrapper body = response.isSuccessful() ? response.body() : null;

                if (body == null) {
                    throw new ServerException(retrieveErrorInfo(response));
                } else {
                    return body.getAnime();
                }
            }
        }, Task.BACKGROUND_EXECUTOR);
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

    public static void getFollowedUsers(final String username, final ApiResponse<Feed> listener) {
        getFollowedUsers(username, null, listener);
    }

    public static void getFollowedUsers(final String username, @Nullable final Feed feed,
            final ApiResponse<Feed> listener) {
        final int page = feed == null ? 1 : feed.getCursor();

        TaskCompletionSource<Void> tcs = new TaskCompletionSource<>();
        tcs.getTask().continueWithTask(new Continuation<Void, Task<Feed>>() {
            @Override
            public Task<Feed> then(final Task<Void> task) throws Exception {
                final Response<Feed> response = HUMMINGBIRD.getFollowedUsers(username, page)
                        .execute();
                final Feed body = response.isSuccessful() ? response.body() : null;

                if (body == null) {
                    throw new ServerException(retrieveErrorInfo(response));
                } else {
                    return hydrate(body);
                }
            }
        }, Task.BACKGROUND_EXECUTOR).onSuccessTask(new Continuation<Feed, Task<Feed>>() {
            @Override
            public Task<Feed> then(final Task<Feed> task) throws Exception {
                return merge(task.getResult(), feed);
            }
        }, Task.BACKGROUND_EXECUTOR).continueWith(new Continuation<Feed, Void>() {
            @Override
            public Void then(final Task<Feed> task) throws Exception {
                if (task.isCancelled() || task.isFaulted()) {
                    final Exception exception = task.getError();
                    Timber.e(TAG, "get followed users (" + username + ") (page "
                            + page + ") failed", exception);
                    listener.failure(exception instanceof ServerException ?
                            ((ServerException) exception).getErrorInfo() : null);
                } else {
                    listener.success(task.getResult());
                }

                return null;
            }
        }, Task.UI_THREAD_EXECUTOR);

        tcs.setResult(null);
    }

    public static void getFollowingUsers(final String username, final ApiResponse<Feed> listener) {
        getFollowingUsers(username, null, listener);
    }

    public static void getFollowingUsers(final String username, @Nullable final Feed feed,
            final ApiResponse<Feed> listener) {
        final int page = feed == null ? 1 : feed.getCursor();

        TaskCompletionSource<Void> tcs = new TaskCompletionSource<>();
        tcs.getTask().continueWithTask(new Continuation<Void, Task<Feed>>() {
            @Override
            public Task<Feed> then(final Task<Void> task) throws Exception {
                final Response<Feed> response = HUMMINGBIRD.getFollowingUsers(username, page)
                        .execute();
                final Feed body = response.isSuccessful() ? response.body() : null;

                if (body == null) {
                    throw new ServerException(retrieveErrorInfo(response));
                } else {
                    return hydrate(body);
                }
            }
        }, Task.BACKGROUND_EXECUTOR).onSuccessTask(new Continuation<Feed, Task<Feed>>() {
            @Override
            public Task<Feed> then(final Task<Feed> task) throws Exception {
                return merge(task.getResult(), feed);
            }
        }, Task.BACKGROUND_EXECUTOR).continueWith(new Continuation<Feed, Void>() {
            @Override
            public Void then(final Task<Feed> task) throws Exception {
                if (task.isCancelled() || task.isFaulted()) {
                    final Exception exception = task.getError();
                    Timber.e(TAG, "get following users (" + username + ") (page "
                            + page + ") failed", exception);
                    listener.failure(exception instanceof ServerException ?
                            ((ServerException) exception).getErrorInfo() : null);
                } else {
                    listener.success(task.getResult());
                }

                return null;
            }
        }, Task.UI_THREAD_EXECUTOR);

        tcs.setResult(null);
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
        TaskCompletionSource<Void> tcs = new TaskCompletionSource<>();
        tcs.getTask().continueWithTask(new Continuation<Void, Task<GroupDigest>>() {
            @Override
            public Task<GroupDigest> then(final Task<Void> task) throws Exception {
                final Response<GroupDigest> response = HUMMINGBIRD.getGroup(groupId).execute();
                final GroupDigest body = response.isSuccessful() ? response.body() : null;

                if (body == null) {
                    throw new ServerException(retrieveErrorInfo(response));
                } else {
                    return hydrate(body);
                }
            }
        }, Task.BACKGROUND_EXECUTOR).continueWith(new Continuation<GroupDigest, Void>() {
            @Override
            public Void then(final Task<GroupDigest> task) throws Exception {
                if (task.isCancelled() || task.isFaulted()) {
                    final Exception exception = task.getError();
                    Timber.e(TAG, "get group digest (" + groupId + ") failed", exception);
                    listener.failure(exception instanceof ServerException ?
                            ((ServerException) exception).getErrorInfo() : null);
                } else {
                    listener.success(task.getResult());
                }

                return null;
            }
        }, Task.UI_THREAD_EXECUTOR);

        tcs.setResult(null);
    }

    public static void getGroupMembers(final String groupId, final ApiResponse<Feed> listener) {
        getGroupMembers(groupId, null, listener);
    }

    public static void getGroupMembers(final String groupId, @Nullable final Feed feed,
            final ApiResponse<Feed> listener) {
        final int page = feed == null ? 1 : feed.getCursor();

        TaskCompletionSource<Void> tcs = new TaskCompletionSource<>();
        tcs.getTask().continueWithTask(new Continuation<Void, Task<Feed>>() {
            @Override
            public Task<Feed> then(final Task<Void> task) throws Exception {
                final Response<Feed> response = HUMMINGBIRD.getGroupMembers(groupId, page).execute();
                final Feed body = response.isSuccessful() ? response.body() : null;

                if (body == null) {
                    throw new ServerException(retrieveErrorInfo(response));
                } else {
                    return hydrate(body);
                }
            }
        }, Task.BACKGROUND_EXECUTOR).onSuccessTask(new Continuation<Feed, Task<Feed>>() {
            @Override
            public Task<Feed> then(final Task<Feed> task) throws Exception {
                return merge(task.getResult(), feed);
            }
        }, Task.BACKGROUND_EXECUTOR).continueWith(new Continuation<Feed, Void>() {
            @Override
            public Void then(final Task<Feed> task) throws Exception {
                if (task.isCancelled() || task.isFaulted()) {
                    final Exception exception = task.getError();
                    Timber.e(TAG, "get group members (" + groupId + ") (page "
                            + page + ") failed", exception);
                    listener.failure(exception instanceof ServerException ?
                            ((ServerException) exception).getErrorInfo() : null);
                } else {
                    listener.success(task.getResult());
                }

                return null;
            }
        }, Task.UI_THREAD_EXECUTOR);

        tcs.setResult(null);
    }

    public static void getGroupStories(final String groupId, final ApiResponse<Feed> listener) {
        getGroupStories(groupId, null, listener);
    }

    public static void getGroupStories(final String groupId, @Nullable final Feed feed,
            final ApiResponse<Feed> listener) {
        final int page = feed == null ? 1 : feed.getCursor();

        TaskCompletionSource<Void> tcs = new TaskCompletionSource<>();
        tcs.getTask().continueWithTask(new Continuation<Void, Task<Feed>>() {
            @Override
            public Task<Feed> then(final Task<Void> task) throws Exception {
                final Response<Feed> response = HUMMINGBIRD.getGroupStories(groupId, page).execute();
                final Feed body = response.isSuccessful() ? response.body() : null;

                if (body == null) {
                    throw new ServerException(retrieveErrorInfo(response));
                } else {
                    return hydrate(body);
                }
            }
        }, Task.BACKGROUND_EXECUTOR).onSuccessTask(new Continuation<Feed, Task<Feed>>() {
            @Override
            public Task<Feed> then(final Task<Feed> task) throws Exception {
                return merge(task.getResult(), feed);
            }
        }, Task.BACKGROUND_EXECUTOR).continueWith(new Continuation<Feed, Void>() {
            @Override
            public Void then(final Task<Feed> task) throws Exception {
                if (task.isCancelled() || task.isFaulted()) {
                    final Exception exception = task.getError();
                    Timber.e(TAG, "get group stories (" + groupId + ") (page "
                            + page + ") failed", exception);
                    listener.failure(exception instanceof ServerException ?
                            ((ServerException) exception).getErrorInfo() : null);
                } else {
                    listener.success(task.getResult());
                }

                return null;
            }
        }, Task.UI_THREAD_EXECUTOR);

        tcs.setResult(null);
    }

    public static void getMangaDigest(final String mangaId, final ApiResponse<MangaDigest> listener) {
        TaskCompletionSource<Void> tcs = new TaskCompletionSource<>();
        tcs.getTask().continueWithTask(new Continuation<Void, Task<MangaDigest>>() {
            @Override
            public Task<MangaDigest> then(final Task<Void> task) throws Exception {
                final Response<MangaDigest> response = HUMMINGBIRD.getMangaDigest(mangaId).execute();
                final MangaDigest body = response.isSuccessful() ? response.body() : null;

                if (body == null) {
                    throw new ServerException(retrieveErrorInfo(response));
                } else {
                    return hydrate(body);
                }
            }
        }, Task.BACKGROUND_EXECUTOR).continueWith(new Continuation<MangaDigest, Void>() {
            @Override
            public Void then(final Task<MangaDigest> task) throws Exception {
                if (task.isCancelled() || task.isFaulted()) {
                    final Exception exception = task.getError();
                    Timber.e(TAG, "get manga digest (" + mangaId + ") failed", exception);
                    listener.failure(exception instanceof ServerException ?
                            ((ServerException) exception).getErrorInfo() : null);
                } else {
                    listener.success(task.getResult());
                }

                return null;
            }
        }, Task.UI_THREAD_EXECUTOR);

        tcs.setResult(null);
    }

    public static void getMangaLibraryEntries(final String username,
            @Nullable final ReadingStatus readingStatus, final ApiResponse<Feed> listener) {
        getMangaLibraryEntries(username, readingStatus, null, listener);
    }

    public static void getMangaLibraryEntries(final String username,
            @Nullable final ReadingStatus readingStatus, @Nullable final Feed feed,
            final ApiResponse<Feed> listener) {
        final String rs = readingStatus == null ? null : readingStatus.getPostValue();

        TaskCompletionSource<Void> tcs = new TaskCompletionSource<>();
        tcs.getTask().continueWithTask(new Continuation<Void, Task<Feed>>() {
            @Override
            public Task<Feed> then(final Task<Void> task) throws Exception {
                final Response<Feed> response = HUMMINGBIRD.getMangaLibraryEntries(username, rs)
                        .execute();
                final Feed body = response.isSuccessful() ? response.body() : null;

                if (body == null) {
                    throw new ServerException(retrieveErrorInfo(response));
                } else {
                    return hydrate(body);
                }
            }
        }, Task.BACKGROUND_EXECUTOR).continueWithTask(new Continuation<Feed, Task<Feed>>() {
            @Override
            public Task<Feed> then(final Task<Feed> task) throws Exception {
                return merge(task.getResult(), feed);
            }
        }, Task.BACKGROUND_EXECUTOR).continueWith(new Continuation<Feed, Void>() {
            @Override
            public Void then(final Task<Feed> task) throws Exception {
                if (task.isCancelled() || task.isFaulted()) {
                    final Exception exception = task.getError();
                    Timber.e(TAG, "get manga library entries (" + username +
                            ") (reading status " + rs + ") failed", exception);
                    listener.failure(exception instanceof ServerException ?
                            ((ServerException) exception).getErrorInfo() : null);
                } else {
                    listener.success(task.getResult());
                }

                return null;
            }
        }, Task.UI_THREAD_EXECUTOR);

        tcs.setResult(null);
    }

    public static void getNewsFeed(final ApiResponse<Feed> listener) {
        getNewsFeed(null, listener);
    }

    public static void getNewsFeed(@Nullable final Feed feed, final ApiResponse<Feed> listener) {
        final int page = feed == null ? 1 : feed.getCursor();

        TaskCompletionSource<Void> tcs = new TaskCompletionSource<>();
        tcs.getTask().continueWithTask(new Continuation<Void, Task<Feed>>() {
            @Override
            public Task<Feed> then(final Task<Void> task) throws Exception {
                final Response<Feed> response = HUMMINGBIRD.getNewsFeed(true, page).execute();
                final Feed body = response.isSuccessful() ? response.body() : null;

                if (body == null) {
                    throw new ServerException(retrieveErrorInfo(response));
                } else {
                    return hydrate(body);
                }
            }
        }, Task.BACKGROUND_EXECUTOR).continueWithTask(new Continuation<Feed, Task<Feed>>() {
            @Override
            public Task<Feed> then(final Task<Feed> task) throws Exception {
                return merge(task.getResult(), feed);
            }
        }, Task.BACKGROUND_EXECUTOR).continueWith(new Continuation<Feed, Void>() {
            @Override
            public Void then(final Task<Feed> task) throws Exception {
                if (task.isCancelled() || task.isFaulted()) {
                    final Exception exception = task.getError();
                    Timber.e(TAG, "get news feed (page " + page + ") failed", exception);
                    listener.failure(exception instanceof ServerException ?
                            ((ServerException) exception).getErrorInfo() : null);
                } else {
                    listener.success(task.getResult());
                }

                return null;
            }
        }, Task.UI_THREAD_EXECUTOR);

        tcs.setResult(null);
    }

    public static void getNotifications(final ApiResponse<Feed> listener) {
        getNotifications(null, listener);
    }

    public static void getNotifications(@Nullable final Feed feed, final ApiResponse<Feed> listener) {
        final int page = feed == null ? 1 : feed.getCursor();

        TaskCompletionSource<Void> tcs = new TaskCompletionSource<>();
        tcs.getTask().continueWithTask(new Continuation<Void, Task<Feed>>() {
            @Override
            public Task<Feed> then(final Task<Void> task) throws Exception {
                final Response<Feed> response = HUMMINGBIRD.getNotifications(page).execute();
                final Feed body = response.isSuccessful() ? response.body() : null;

                if (body == null) {
                    throw new ServerException(retrieveErrorInfo(response));
                } else {
                    return hydrate(body);
                }
            }
        }, Task.BACKGROUND_EXECUTOR).continueWithTask(new Continuation<Feed, Task<Feed>>() {
            @Override
            public Task<Feed> then(final Task<Feed> task) throws Exception {
                return merge(task.getResult(), feed);
            }
        }, Task.BACKGROUND_EXECUTOR).continueWith(new Continuation<Feed, Void>() {
            @Override
            public Void then(final Task<Feed> task) throws Exception {
                if (task.isCancelled() || task.isFaulted()) {
                    final Exception exception = task.getError();
                    Timber.e(TAG, "get notifications (page " + page + ") failed", exception);
                    listener.failure(exception instanceof ServerException ?
                            ((ServerException) exception).getErrorInfo() : null);
                } else {
                    listener.success(task.getResult());
                }

                return null;
            }
        }, Task.UI_THREAD_EXECUTOR);

        tcs.setResult(null);
    }

    public static void getStory(final String storyId, final ApiResponse<Feed> listener) {
        TaskCompletionSource<Void> tcs = new TaskCompletionSource<>();
        tcs.getTask().continueWithTask(new Continuation<Void, Task<Feed>>() {
            @Override
            public Task<Feed> then(final Task<Void> task) throws Exception {
                final Response<Feed> response = HUMMINGBIRD.getStory(storyId).execute();
                final Feed body = response.isSuccessful() ? response.body() : null;

                if (body == null) {
                    throw new ServerException(retrieveErrorInfo(response));
                } else {
                    return hydrate(body);
                }
            }
        }, Task.BACKGROUND_EXECUTOR).continueWith(new Continuation<Feed, Void>() {
            @Override
            public Void then(final Task<Feed> task) throws Exception {
                if (task.isCancelled() || task.isFaulted()) {
                    final Exception exception = task.getError();
                    Timber.e(TAG, "get story (" + storyId + ") failed", exception);
                    listener.failure(exception instanceof ServerException ?
                            ((ServerException) exception).getErrorInfo() : null);
                } else {
                    listener.success(task.getResult());
                }

                return null;
            }
        }, Task.UI_THREAD_EXECUTOR);

        tcs.setResult(null);
    }

    public static void getStoryFromNotification(final String notificationId,
            final ApiResponse<Feed> listener) {
        TaskCompletionSource<Void> tcs = new TaskCompletionSource<>();
        tcs.getTask().continueWithTask(new Continuation<Void, Task<Feed>>() {
            @Override
            public Task<Feed> then(final Task<Void> task) throws Exception {
                final Response<Feed> response = HUMMINGBIRD.getStoryFromNotification(notificationId)
                        .execute();
                final Feed body = response.isSuccessful() ? response.body() : null;

                if (body == null) {
                    throw new ServerException(retrieveErrorInfo(response));
                } else {
                    return hydrate(body);
                }
            }
        }, Task.BACKGROUND_EXECUTOR).continueWith(new Continuation<Feed, Void>() {
            @Override
            public Void then(final Task<Feed> task) throws Exception {
                if (task.isCancelled() || task.isFaulted()) {
                    final Exception exception = task.getError();
                    Timber.e(TAG, "get story from notification (" +
                            notificationId + ") failed", exception);
                    listener.failure(exception instanceof ServerException ?
                            ((ServerException) exception).getErrorInfo() : null);
                } else {
                    listener.success(task.getResult());
                }

                return null;
            }
        }, Task.UI_THREAD_EXECUTOR);

        tcs.setResult(null);
    }

    public static void getSubstories(final String storyId, final ApiResponse<Feed> listener) {
        getSubstories(storyId, null, listener);
    }

    public static void getSubstories(final String storyId, @Nullable final Feed feed,
            final ApiResponse<Feed> listener) {
        final int page = feed == null ? 1 : feed.getCursor();

        TaskCompletionSource<Void> tcs = new TaskCompletionSource<>();
        tcs.getTask().continueWithTask(new Continuation<Void, Task<Feed>>() {
            @Override
            public Task<Feed> then(final Task<Void> task) throws Exception {
                final Response<Feed> response = HUMMINGBIRD.getSubstories(storyId, page).execute();
                final Feed body = response.isSuccessful() ? response.body() : null;

                if (body == null) {
                    throw new ServerException(retrieveErrorInfo(response));
                } else {
                    return hydrate(body);
                }
            }
        }, Task.BACKGROUND_EXECUTOR).continueWithTask(new Continuation<Feed, Task<Feed>>() {
            @Override
            public Task<Feed> then(final Task<Feed> task) throws Exception {
                return merge(task.getResult(), feed);
            }
        }, Task.BACKGROUND_EXECUTOR).continueWith(new Continuation<Feed, Void>() {
            @Override
            public Void then(final Task<Feed> task) throws Exception {
                if (task.isCancelled() || task.isFaulted()) {
                    final Exception exception = task.getError();
                    Timber.e(TAG, "get substories (" + storyId + ") (page "
                            + page + ") failed", exception);
                    listener.failure(exception instanceof ServerException ?
                            ((ServerException) exception).getErrorInfo() : null);
                } else {
                    listener.success(task.getResult());
                }

                return null;
            }
        }, Task.UI_THREAD_EXECUTOR);

        tcs.setResult(null);
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
        TaskCompletionSource<Void> tcs = new TaskCompletionSource<>();
        tcs.getTask().continueWithTask(new Continuation<Void, Task<UserDigest>>() {
            @Override
            public Task<UserDigest> then(final Task<Void> task) throws Exception {
                final Response<UserDigest> response = HUMMINGBIRD.getUserDigest(username).execute();
                final UserDigest body = response.isSuccessful() ? response.body() : null;

                if (body == null) {
                    throw new ServerException(retrieveErrorInfo(response));
                } else {
                    return Task.forResult(body);
                }
            }
        }, Task.BACKGROUND_EXECUTOR).onSuccessTask(new Continuation<UserDigest, Task<UserDigest>>() {
            @Override
            public Task<UserDigest> then(final Task<UserDigest> task) throws Exception {
                final UserDigest digest = task.getResult();

                if (digest.isMissingUser()) {
                    Timber.d(TAG, "user digest for \"" + username + "\" is missing user");

                    final Response<User> response = HUMMINGBIRD.getUser(digest.getInfo().getId())
                            .execute();
                    final User user = response.isSuccessful() ? response.body() : null;

                    if (user == null) {
                        throw new ServerException(retrieveErrorInfo(response));
                    } else {
                        digest.setUser(user);
                    }
                }

                return hydrate(digest);
            }
        }, Task.BACKGROUND_EXECUTOR).continueWith(new Continuation<UserDigest, Void>() {
            @Override
            public Void then(final Task<UserDigest> task) throws Exception {
                if (task.isCancelled() || task.isFaulted()) {
                    final Exception exception = task.getError();
                    Timber.e(TAG, "get user digest (" + username + ") failed", exception);
                    listener.failure(exception instanceof ServerException ?
                            ((ServerException) exception).getErrorInfo() : null);
                } else {
                    listener.success(task.getResult());
                }

                return null;
            }
        });

        tcs.setResult(null);
    }

    public static void getUserGroups(final String userId, final ApiResponse<Feed> listener) {
        getUserGroups(userId, null, listener);
    }

    public static void getUserGroups(final String userId, @Nullable final Feed feed,
            final ApiResponse<Feed> listener) {
        final int page = feed == null ? 1 : feed.getCursor();

        TaskCompletionSource<Void> tcs = new TaskCompletionSource<>();
        tcs.getTask().continueWithTask(new Continuation<Void, Task<Feed>>() {
            @Override
            public Task<Feed> then(final Task<Void> task) throws Exception {
                final Response<Feed> response = HUMMINGBIRD.getUserGroups(userId, page).execute();
                final Feed body = response.isSuccessful() ? response.body() : null;

                if (body == null) {
                    throw new ServerException(retrieveErrorInfo(response));
                } else {
                    return hydrate(body);
                }
            }
        }, Task.BACKGROUND_EXECUTOR).continueWithTask(new Continuation<Feed, Task<Feed>>() {
            @Override
            public Task<Feed> then(final Task<Feed> task) throws Exception {
                return merge(task.getResult(), feed);
            }
        }, Task.BACKGROUND_EXECUTOR).continueWith(new Continuation<Feed, Void>() {
            @Override
            public Void then(final Task<Feed> task) throws Exception {
                if (task.isCancelled() || task.isFaulted()) {
                    final Exception exception = task.getError();
                    Timber.e(TAG, "get user groups (" + userId + ") (page " +
                            page + ") failed", exception);
                    listener.failure(exception instanceof ServerException ?
                            ((ServerException) exception).getErrorInfo() : null);
                } else {
                    listener.success(task.getResult());
                }

                return null;
            }
        }, Task.UI_THREAD_EXECUTOR);

        tcs.setResult(null);
    }

    public static void getUserReviews(final String userId, final ApiResponse<Feed> listener) {
        getUserReviews(userId, null, listener);
    }

    public static void getUserReviews(final String userId, @Nullable final Feed feed,
            final ApiResponse<Feed> listener) {
        final int page = feed == null ? 1 : feed.getCursor();

        TaskCompletionSource<Void> tcs = new TaskCompletionSource<>();
        tcs.getTask().continueWithTask(new Continuation<Void, Task<Feed>>() {
            @Override
            public Task<Feed> then(final Task<Void> task) throws Exception {
                final Response<Feed> response = HUMMINGBIRD.getUserReviews(userId, page).execute();
                final Feed body = response.isSuccessful() ? response.body() : null;

                if (body == null) {
                    throw new ServerException(retrieveErrorInfo(response));
                } else {
                    return Task.forResult(body);
                }
            }
        }, Task.BACKGROUND_EXECUTOR).onSuccessTask(new Continuation<Feed, Task<Feed>>() {
            @Override
            public Task<Feed> then(final Task<Feed> task) throws Exception {
                final Feed body = task.getResult();

                if (!body.hasAnimeReviews()) {
                    return merge(body, feed);
                }

                final ArrayList<String> animeIds = body.getAnimeIdsNeededForAnimeReviews();

                if (animeIds == null || animeIds.isEmpty()) {
                    return merge(body, feed);
                }

                final ArrayList<Task<Anime>> tasks = new ArrayList<>(animeIds.size());

                for (final String animeId : animeIds) {
                    tasks.add(getAnimeTask(animeId));
                }

                return Task.whenAll(tasks).continueWithTask(new Continuation<Void, Task<Feed>>() {
                    @Override
                    public Task<Feed> then(final Task<Void> task) throws Exception {
                        final ArrayList<Anime> animeList = new ArrayList<>(tasks.size());

                        for (final Task<Anime> animeTask : tasks) {
                            if (!task.isCancelled() && !task.isFaulted()) {
                                animeList.add(animeTask.getResult());
                            }
                        }

                        body.addAnime(animeList);
                        return merge(body, feed);
                    }
                });
            }
        }, Task.BACKGROUND_EXECUTOR).continueWith(new Continuation<Feed, Void>() {
            @Override
            public Void then(final Task<Feed> task) throws Exception {
                return null;
            }
        }, Task.UI_THREAD_EXECUTOR);

        tcs.setResult(null);
    }

    public static void getUserStories(final String userId, final ApiResponse<Feed> listener) {
        getUserStories(userId, null, listener);
    }

    public static void getUserStories(final String userId, @Nullable final Feed feed,
            final ApiResponse<Feed> listener) {
        final int page = feed == null ? 1 : feed.getCursor();

        TaskCompletionSource<Void> tcs = new TaskCompletionSource<>();
        tcs.getTask().continueWithTask(new Continuation<Void, Task<Feed>>() {
            @Override
            public Task<Feed> then(final Task<Void> task) throws Exception {
                final Response<Feed> response = HUMMINGBIRD.getUserStories(userId, page).execute();
                final Feed body = response.isSuccessful() ? response.body() : null;

                if (body == null) {
                    throw new ServerException(retrieveErrorInfo(response));
                } else {
                    return hydrate(body);
                }
            }
        }, Task.BACKGROUND_EXECUTOR).continueWithTask(new Continuation<Feed, Task<Feed>>() {
            @Override
            public Task<Feed> then(final Task<Feed> task) throws Exception {
                return merge(task.getResult(), feed);
            }
        }, Task.BACKGROUND_EXECUTOR).continueWith(new Continuation<Feed, Void>() {
            @Override
            public Void then(final Task<Feed> task) throws Exception {
                if (task.isCancelled() || task.isFaulted()) {
                    final Exception exception = task.getError();
                    Timber.e(TAG, "get user stories (" + userId + ") failed", exception);
                    listener.failure(exception instanceof ServerException ?
                            ((ServerException) exception).getErrorInfo() : null);
                } else {
                    listener.success(task.getResult());
                }

                return null;
            }
        }, Task.UI_THREAD_EXECUTOR);

        tcs.setResult(null);
    }

    private static <T extends Hydratable> Task<T> hydrate(final T hydratable) {
        return new TaskCompletionSource<Void>().getTask().continueWith(new Continuation<Void, T>() {
            @Override
            public T then(final Task<Void> task) throws Exception {
                hydratable.hydrate();
                return hydratable;
            }
        }, Task.BACKGROUND_EXECUTOR);
    }

    private static Task<Feed> merge(@NonNull final Feed newFeed, @Nullable final Feed oldFeed) {
        return new TaskCompletionSource<Void>().getTask().continueWith(new Continuation<Void, Feed>() {
            @Override
            public Feed then(final Task<Void> task) throws Exception {
                if (oldFeed == null) {
                    newFeed.hydrate();
                    return newFeed;
                } else {
                    oldFeed.merge(newFeed);
                    oldFeed.hydrate();
                    return oldFeed;
                }
            }
        }, Task.BACKGROUND_EXECUTOR);
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
