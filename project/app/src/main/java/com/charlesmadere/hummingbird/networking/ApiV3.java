package com.charlesmadere.hummingbird.networking;

import com.charlesmadere.hummingbird.misc.RetrofitUtils;
import com.charlesmadere.hummingbird.models.ActionGroup;
import com.charlesmadere.hummingbird.models.ArrayResponse;

import bolts.Continuation;
import bolts.Task;
import bolts.TaskCompletionSource;

public final class ApiV3 {

    private static final String TAG = "ApiV3";

    private static final KitsuApi KITSU = RetrofitUtils.getKitsuApi();


    public static void getGlobalFeed(final ApiResponse<ArrayResponse<ActionGroup>> listener) {
        TaskCompletionSource<Void> tcs = new TaskCompletionSource<>();
        tcs.getTask().continueWith(new Continuation<Void, ArrayResponse<ActionGroup>>() {
            @Override
            public ArrayResponse<ActionGroup> then(final Task<Void> task) throws Exception {


                return null;
            }
        });
    }

}
