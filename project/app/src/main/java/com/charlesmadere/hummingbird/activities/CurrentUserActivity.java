package com.charlesmadere.hummingbird.activities;

import android.content.Context;
import android.content.Intent;

import com.charlesmadere.hummingbird.misc.CurrentUser;
import com.charlesmadere.hummingbird.views.NavigationDrawerItemView;

public class CurrentUserActivity extends BaseUserActivity {

    private static final String TAG = "CurrentUserActivity";


    public static Intent getLaunchIntent(final Context context) {
        return BaseUserActivity.getLaunchIntent(context, CurrentUser.get());
    }

    @Override
    public String getActivityName() {
        return TAG;
    }

    @Override
    protected NavigationDrawerItemView.Entry getSelectedNavigationDrawerItemViewEntry() {
        return NavigationDrawerItemView.Entry.HOME;
    }

}
