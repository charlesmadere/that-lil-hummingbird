package com.charlesmadere.hummingbird.misc;

import android.app.Activity;

import com.charlesmadere.hummingbird.activities.LoginActivity;
import com.charlesmadere.hummingbird.models.UserV2;
import com.charlesmadere.hummingbird.preferences.Preferences;

import java.util.ArrayList;

public final class CurrentUser {

    private static final String TAG = "CurrentUser";

    private static UserV2 sCurrentUser;


    public static boolean exists() {
        return Preferences.Account.AuthToken.exists() && Preferences.Account.Username.exists()
                && get() != null;
    }

    public static synchronized UserV2 get() {
        return sCurrentUser;
    }

    public static synchronized void set(final UserV2 user) {
        Timber.d(TAG, "current user set to \"" + user.getName() + '"');
        sCurrentUser = user;
    }

    public static synchronized boolean shouldBeFetched() {
        return Preferences.Account.AuthToken.exists() && Preferences.Account.Username.exists()
                && get() == null;
    }

    public static synchronized void signOut() {
        Preferences.Account.eraseAll();
        sCurrentUser = null;
        Timber.d(TAG, "current user signed out");

        final ArrayList<Activity> activities = ActivityRegister.get();

        if (activities == null || activities.isEmpty()) {
            return;
        }

        final Activity activity = activities.get(0);
        activity.startActivity(LoginActivity.getNewTaskLaunchIntent(activity));

        for (final Activity a : activities) {
            a.finish();
        }

        activities.clear();
    }

}
