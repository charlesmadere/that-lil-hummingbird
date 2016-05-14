package com.charlesmadere.hummingbird.misc;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class Threading {

    private static final ExecutorService EXECUTOR_SERVICE;
    private static final Handler MAIN_HANDLER;


    static {
        EXECUTOR_SERVICE = Executors.newFixedThreadPool(2);
        MAIN_HANDLER = new Handler(Looper.getMainLooper());
    }

    public static void runOnBackground(final Runnable task) {
        EXECUTOR_SERVICE.submit(task);
    }

    public static void runOnUi(final Runnable task) {
        MAIN_HANDLER.post(task);
    }

}
