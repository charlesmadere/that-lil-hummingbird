package com.charlesmadere.hummingbird.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.fragments.UserGroupsFragment;
import com.charlesmadere.hummingbird.models.UiColorSet;

public class UserGroupsActivity extends BaseDrawerActivity {

    private static final String TAG = "UserGroupsActivity";
    private static final String CNAME = UserGroupsActivity.class.getCanonicalName();
    private static final String EXTRA_USERNAME = CNAME + ".Username";


    public static Intent getLaunchIntent(final Context context, final String username) {
        return getLaunchIntent(context, username, null);
    }

    public static Intent getLaunchIntent(final Context context, final String username,
            @Nullable final UiColorSet uiColorSet) {
        final Intent intent = new Intent(context, UserGroupsActivity.class)
                .putExtra(EXTRA_USERNAME, username);

        if (uiColorSet != null) {
            intent.putExtra(EXTRA_UI_COLOR_SET, uiColorSet);
        }

        return intent;
    }

    @Override
    public String getActivityName() {
        return TAG;
    }

    @Override
    protected boolean isUpNavigationEnabled() {
        return true;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_groups);

        final Intent intent = getIntent();
        final String username = intent.getStringExtra(EXTRA_USERNAME);
        setSubtitle(username);

        final FragmentManager manager = getSupportFragmentManager();
        UserGroupsFragment fragment = (UserGroupsFragment) manager.findFragmentById(R.id.flContent);

        if (fragment == null) {
            manager.beginTransaction()
                    .add(R.id.flContent, UserGroupsFragment.create(username))
                    .commit();
        }
    }

}
