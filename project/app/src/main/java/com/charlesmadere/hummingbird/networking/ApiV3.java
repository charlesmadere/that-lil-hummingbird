package com.charlesmadere.hummingbird.networking;

import android.support.annotation.Nullable;

import com.charlesmadere.hummingbird.misc.RetrofitUtils;
import com.charlesmadere.hummingbird.models.ActionGroup;
import com.charlesmadere.hummingbird.models.ArrayResponse;
import com.charlesmadere.hummingbird.models.FeedV3;

import bolts.Continuation;
import bolts.Task;
import bolts.TaskCompletionSource;
import retrofit2.Response;

public final class ApiV3 {

    private static final String TAG = "ApiV3";

    private static final KitsuApi KITSU = RetrofitUtils.getKitsuApi();


    public static void getGlobalFeed(final ApiCall<FeedV3> listener, @Nullable final FeedV3 feed) {
        TaskCompletionSource<Void> tcs = new TaskCompletionSource<>();
        tcs.getTask().continueWith(new Continuation<Void, ArrayResponse<ActionGroup>>() {
            @Override
            public ArrayResponse<ActionGroup> then(final Task<Void> task) throws Exception {
                // TODO do a paginate call if feed isn't null
                final Response<ArrayResponse<ActionGroup>> response =
                        KITSU.getGlobalFeed().execute();

                final ArrayResponse<ActionGroup> body = response.isSuccessful() ?
                        response.body() : null;

                if (body == null) {
                    throw new Exception();
                } else {
                    return response.body();
                }
            }
        }, Task.BACKGROUND_EXECUTOR).onSuccess(new Continuation<ArrayResponse<ActionGroup>, FeedV3>() {
            @Override
            public FeedV3 then(final Task<ArrayResponse<ActionGroup>> task) throws Exception {
                final ArrayResponse<ActionGroup> response = task.getResult();
                return new FeedV3.Builder(response)
                        .setFeed(feed)
                        .build();
            }
        }, Task.BACKGROUND_EXECUTOR).continueWith(new Continuation<FeedV3, Void>() {
            @Override
            public Void then(final Task<FeedV3> task) throws Exception {
                if (listener.isAlive()) {
                    if (feed != null && listener instanceof PaginationApiListener) {
                        final PaginationApiListener pal = (PaginationApiListener) listener;

                        if (task.isFaulted()) {
                            pal.paginationNoMore();
                        } else {
                            pal.paginationComplete();
                        }
                    } else {
                        if (task.isFaulted()) {
                            listener.failure(null);
                        } else {
                            listener.success(task.getResult());
                        }
                    }
                }

                return null;
            }
        }, Task.UI_THREAD_EXECUTOR);

        tcs.setResult(null);
    }

}
