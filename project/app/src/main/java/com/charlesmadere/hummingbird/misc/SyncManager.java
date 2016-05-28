package com.charlesmadere.hummingbird.misc;

import com.charlesmadere.hummingbird.preferences.Preferences;

public final class SyncManager {

    private static final String TAG = "SyncManager";


    private static void disable() {
        Timber.d(TAG, "Sync has been disabled");

        // TODO
    }

    private static void enable() {
        Timber.d(TAG, "Sync has been enabled");

        // TODO
    }

    public static void enableOrDisable() {
        if (Boolean.TRUE.equals(Preferences.NotificationPolling.IsEnabled.get())) {
            enable();
        } else {
            disable();
        }
    }

}
