package com.charlesmadere.hummingbird.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.views.NavigationDrawerItemView;

public class NotificationsActivity extends BaseDrawerActivity {

    private static final String TAG = "NotificationsActivity";


    public static Intent getLaunchIntent(final Context context) {
        return new Intent(context, NotificationsActivity.class);
    }

    @Override
    public String getActivityName() {
        return TAG;
    }

    @Override
    protected NavigationDrawerItemView.Entry getSelectedNavigationDrawerItemViewEntry() {
        return NavigationDrawerItemView.Entry.NOTIFICATIONS;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
    }

}
