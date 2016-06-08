package com.charlesmadere.hummingbird.misc;

import android.support.annotation.Nullable;

import com.charlesmadere.hummingbird.ThatLilHummingbird;
import com.charlesmadere.hummingbird.models.AbsNotification;
import com.charlesmadere.hummingbird.models.ErrorInfo;
import com.charlesmadere.hummingbird.models.Feed;
import com.charlesmadere.hummingbird.networking.Api;
import com.charlesmadere.hummingbird.networking.ApiResponse;
import com.charlesmadere.hummingbird.preferences.Preferences;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.PeriodicTask;
import com.google.android.gms.gcm.Task;
import com.google.android.gms.gcm.TaskParams;

import java.util.ArrayList;

public final class SyncManager extends GcmTaskService {

    private static final String TAG = "SyncManager";


    private static boolean canEnable() {
        return Preferences.Account.AuthToken.exists() && Preferences.Account.Username.exists()
                && Preferences.NotificationPolling.IsEnabled.get();
    }

    private static void disable() {
        GcmNetworkManager.getInstance(ThatLilHummingbird.get()).cancelAllTasks(SyncManager.class);
        Preferences.NotificationPolling.LastPoll.delete();
        Timber.d(TAG, "Sync has been disabled");
    }

    private static void enable() {
        final int connectionStatus = GoogleApiAvailability.getInstance()
                .isGooglePlayServicesAvailable(ThatLilHummingbird.get());

        if (connectionStatus != ConnectionResult.SUCCESS) {
            Timber.w(TAG, "Failed to schedule because Google Play Services are unavailable "
                    + "(connectionStatus: " + connectionStatus + ")");
            return;
        }

        final PeriodicTask.Builder builder = new PeriodicTask.Builder()
                .setPeriod(Preferences.NotificationPolling.Frequency.get().getPeriod())
                .setPersisted(true)
                .setRequiresCharging(Preferences.NotificationPolling.IsPowerRequired.get())
                .setService(SyncManager.class)
                .setTag(TAG)
                .setUpdateCurrent(true);

        if (Preferences.NotificationPolling.IsWifiRequired.get()) {
            builder.setRequiredNetwork(Task.NETWORK_STATE_UNMETERED);
        } else {
            builder.setRequiredNetwork(Task.NETWORK_STATE_CONNECTED);
        }

        final PeriodicTask periodicTask = builder.build();
        GcmNetworkManager.getInstance(ThatLilHummingbird.get()).schedule(periodicTask);
        Timber.d(TAG, "Sync has been enabled " + printConfigurationString());
    }

    public static void enableOrDisable() {
        if (canEnable()) {
            enable();
        } else {
            disable();
        }
    }

    private static String printConfigurationString() {
        return "(frequency: " + Preferences.NotificationPolling.Frequency.get().getPeriod() +
                ") (power required: " + Preferences.NotificationPolling.IsPowerRequired.get() +
                ") (wifi required: " + Preferences.NotificationPolling.IsWifiRequired.get() + ")";
    }

    @Override
    public void onInitializeTasks() {
        super.onInitializeTasks();
        enableOrDisable();
    }

    @Override
    public int onRunTask(final TaskParams taskParams) {
        Timber.d(TAG, "Syncing now... " + printConfigurationString());
        Preferences.NotificationPolling.LastPoll.set(System.currentTimeMillis());

        Api.getNotifications(new ApiResponse<Feed>() {
            @Override
            public void failure(@Nullable final ErrorInfo error) {
                // this can be safely ignored
            }

            @Override
            public void success(final Feed feed) {
                if (!feed.hasNotifications()) {
                    Timber.d(TAG, "No notifications");
                    return;
                }

                final ArrayList<AbsNotification> newNotifications = new ArrayList<>();

                for (final AbsNotification notification : feed.getNotifications()) {
                    if (!notification.isSeen()) {
                        newNotifications.add(notification);
                    }
                }

                if (newNotifications.isEmpty()) {
                    Timber.d(TAG, "No new notifications");
                    return;
                }

                Timber.d(TAG, "New notification(s): " + newNotifications.size());

                // TODO
            }
        });

        return GcmNetworkManager.RESULT_SUCCESS;
    }

}
