package com.charlesmadere.hummingbird.misc;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.ThatLilHummingbird;
import com.charlesmadere.hummingbird.activities.NotificationsActivity;
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
        return Preferences.Account.AuthToken.exists() && Preferences.Account.Username.exists()
                && Preferences.NotificationPolling.IsEnabled.get();
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
        final Context context = getContext();
        return new NotificationCompat.Builder(context)
                .setAutoCancel(true)
                .setCategory(NotificationCompat.CATEGORY_SOCIAL)
                .setContentTitle(context.getString(R.string.that_lil_hummingbird))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setSmallIcon(R.drawable.notification)
                .setVisibility(NotificationCompat.VISIBILITY_PRIVATE);
    }

    private void buildNotification(final AbsNotification notification) {
        final NotificationCompat.Builder builder = buildNotification();

        // TODO
        final Context context = getContext();
        builder.setContentText(notification.getType().toString());

        // TODO
        final Intent intent = NotificationsActivity.getLaunchIntent(context);
        final PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        NotificationManager.show(builder);
    }

    private void buildNotification(final ArrayList<AbsNotification> notifications) {
        final NotificationCompat.Builder builder = buildNotification();

        final Context context = getContext();
        builder.setContentText(context.getString(R.string.x_new_notifications,
                NumberFormat.getInstance().format(notifications.size())));

        final NotificationCompat.InboxStyle style = new NotificationCompat.InboxStyle(builder);

        for (int i = 0; i < notifications.size() && i < 5; ++i) {
            // TODO
            final AbsNotification notification = notifications.get(i);
            style.addLine(notification.getType().toString());
        }

        final Intent intent = NotificationsActivity.getLaunchIntent(context);
        final PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

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

                if (newNotifications.size() == 1) {
                    buildNotification(newNotifications.get(0));
                } else {
                    buildNotification(newNotifications);
                }
            }
        });

        return GcmNetworkManager.RESULT_SUCCESS;
    }

}
