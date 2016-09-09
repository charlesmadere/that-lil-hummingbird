package com.charlesmadere.hummingbird.misc;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.ThatLilHummingbird;
import com.charlesmadere.hummingbird.activities.LoginActivity;
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

import java.text.NumberFormat;
import java.util.ArrayList;

public final class SyncManager extends GcmTaskService {

    private static final String TAG = "SyncManager";


    private static boolean canEnable() {
        return Preferences.Account.Username.exists() &&
                Boolean.TRUE.equals(Preferences.NotificationPolling.IsEnabled.get());
    }

    private static void disable() {
        GcmNetworkManager.getInstance(getContext()).cancelAllTasks(SyncManager.class);
        Preferences.NotificationPolling.LastPoll.delete();
        Timber.d(TAG, "Sync has been disabled");
    }

    private static void enable() {
        final int connectionStatus = GoogleApiAvailability.getInstance()
                .isGooglePlayServicesAvailable(getContext());

        if (connectionStatus != ConnectionResult.SUCCESS) {
            Timber.w(TAG, "Failed to schedule because Google Play Services are unavailable "
                    + "(connectionStatus: " + connectionStatus + ")");
            return;
        }

        final PeriodicTask.Builder builder = new PeriodicTask.Builder()
                .setPeriod(Preferences.NotificationPolling.Frequency.get().getPeriod())
                .setPersisted(true)
                .setRequiresCharging(Boolean.TRUE.equals(
                        Preferences.NotificationPolling.IsPowerRequired.get()))
                .setService(SyncManager.class)
                .setTag(TAG)
                .setUpdateCurrent(true);

        if (Boolean.TRUE.equals(Preferences.NotificationPolling.IsWifiRequired.get())) {
            builder.setRequiredNetwork(Task.NETWORK_STATE_UNMETERED);
        } else {
            builder.setRequiredNetwork(Task.NETWORK_STATE_CONNECTED);
        }

        final PeriodicTask periodicTask = builder.build();
        GcmNetworkManager.getInstance(getContext()).schedule(periodicTask);
        Timber.d(TAG, "Sync has been enabled " + printConfigurationString());
    }

    public static void enableOrDisable() {
        if (canEnable()) {
            enable();
        } else {
            disable();
        }
    }

    private static Context getContext() {
        return ThatLilHummingbird.get();
    }

    private static String printConfigurationString() {
        return "(frequency: " + Preferences.NotificationPolling.Frequency.get().getPeriod() +
                ") (power required: " + Preferences.NotificationPolling.IsPowerRequired.get() +
                ") (wifi required: " + Preferences.NotificationPolling.IsWifiRequired.get() + ")";
    }

    private NotificationCompat.Builder buildNotification() {
        return new NotificationCompat.Builder(getContext())
                .setAutoCancel(true)
                .setCategory(NotificationCompat.CATEGORY_SOCIAL)
                .setContentTitle(getResources().getString(R.string.that_lil_hummingbird))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setSmallIcon(R.drawable.notification)
                .setVisibility(NotificationCompat.VISIBILITY_PRIVATE);
    }

    private void buildNotification(final AbsNotification notification) {
        final NotificationCompat.Builder builder = buildNotification();
        builder.setContentText(notification.getText(getResources()));
        setBuilderContentIntent(builder);
        NotificationManager.show(builder);
    }

    private void buildNotification(final ArrayList<AbsNotification> notifications) {
        final NotificationCompat.Builder builder = buildNotification();
        builder.setContentText(getResources().getString(R.string.x_notifications,
                NumberFormat.getInstance().format(notifications.size())));

        final NotificationCompat.InboxStyle style = new NotificationCompat.InboxStyle(builder);

        for (int i = 0; i < notifications.size() && i < 5; ++i) {
            final AbsNotification notification = notifications.get(i);
            style.addLine(notification.getText(getResources()));
        }

        setBuilderContentIntent(builder);
        NotificationManager.show(builder);
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
                if (error == null) {
                    Timber.e(TAG, "Sync failed");
                } else {
                    Timber.e(TAG, "Sync failed: " + error.getError());
                }
            }

            @Override
            public void success(final Feed feed) {
                if (!feed.hasNotifications()) {
                    Timber.d(TAG, "Sync finished: no notifications");
                    return;
                }

                final ArrayList<AbsNotification> unSeenNotifications = new ArrayList<>();

                // noinspection ConstantConditions
                for (final AbsNotification notification : feed.getNotifications()) {
                    if (!notification.isSeen()) {
                        unSeenNotifications.add(notification);
                    }
                }

                if (unSeenNotifications.isEmpty()) {
                    Timber.d(TAG, "Sync finished: no notifications");
                    return;
                }

                Timber.d(TAG, "Sync finished: " + unSeenNotifications.size() + " notification(s)");

                if (unSeenNotifications.size() == 1) {
                    buildNotification(unSeenNotifications.get(0));
                } else {
                    buildNotification(unSeenNotifications);
                }
            }
        });

        return GcmNetworkManager.RESULT_SUCCESS;
    }

    private void setBuilderContentIntent(final NotificationCompat.Builder builder) {
        final Intent intent = LoginActivity.getNotificationsLaunchIntent(getContext());
        final PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
    }

}
