package com.charlesmadere.hummingbird.misc;

import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.charlesmadere.hummingbird.ThatLilHummingbird;

public final class NotificationManager {

    private static final int NOTIFICATION_ID = 1;


    public static void cancelAll() {
        NotificationManagerCompat.from(ThatLilHummingbird.get()).cancelAll();
    }

    public static void show(final NotificationCompat.Builder builder) {
        NotificationManagerCompat.from(ThatLilHummingbird.get()).notify(NOTIFICATION_ID,
                builder.build());
    }

}
