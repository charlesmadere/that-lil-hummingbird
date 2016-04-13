package com.charlesmadere.hummingbird.activities;

public class UserActivity extends BaseUserActivity {

    private static final String TAG = "UserActivity";


    @Override
    public String getActivityName() {
        return TAG;
    }

    @Override
    protected boolean isUpNavigationEnabled() {
        return true;
    }

}
