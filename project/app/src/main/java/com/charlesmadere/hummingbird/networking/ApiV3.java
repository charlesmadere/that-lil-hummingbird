package com.charlesmadere.hummingbird.networking;

import com.charlesmadere.hummingbird.misc.RetrofitUtils;
import com.charlesmadere.hummingbird.models.ActionGroup;
import com.charlesmadere.hummingbird.models.ArrayResponse;

import bolts.Continuation;
import bolts.Task;
import bolts.TaskCompletionSource;
import retrofit2.Response;

public final class ApiV3 {

    private static final String TAG = "ApiV3";

    private static final KitsuApi KITSU = RetrofitUtils.getKitsuApi();


    public static void getGlobalFeed(final ApiListener<ArrayResponse<ActionGroup>> listener) {
        TaskCompletionSource<Void> tcs = new TaskCompletionSource<>();
        tcs.getTask().continueWith(new Continuation<Void, ArrayResponse<ActionGroup>>() {
            @Override
            public ArrayResponse<ActionGroup> then(final Task<Void> task) throws Exception {
                final Response<ArrayResponse<ActionGroup>> response =
                        KITSU.getGlobalFeed().execute();

                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new Exception();
                }
            }
        }, Task.BACKGROUND_EXECUTOR).continueWith(new Continuation<ArrayResponse<ActionGroup>, Void>() {
            @Override
            public Void then(final Task<ArrayResponse<ActionGroup>> task) throws Exception {
                if (listener.isAlive()) {
                    if (task.isFaulted()) {
                        listener.failure(null);
                    } else {
                        listener.success(task.getResult());
                    }
                }

                return null;
            }
        }, Task.UI_THREAD_EXECUTOR);

        tcs.setResult(null);
    }

}
