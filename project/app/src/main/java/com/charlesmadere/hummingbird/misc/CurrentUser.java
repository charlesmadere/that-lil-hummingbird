package com.charlesmadere.hummingbird.misc;

import android.app.Activity;

import com.charlesmadere.hummingbird.activities.LoginActivity;
import com.charlesmadere.hummingbird.models.UserDigest;
import com.charlesmadere.hummingbird.preferences.Preferences;
import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;

public final class CurrentUser {

    private static final String TAG = "CurrentUser";

    private static UserDigest sCurrentUserDigest;


    public static synchronized boolean exists() {
        return Preferences.Account.CsrfToken.exists() && Preferences.Account.Username.exists()
                && sCurrentUserDigest != null;
    }

    public static synchronized UserDigest get() {
        if (sCurrentUserDigest == null) {
            Timber.w(TAG, "reading in persisted UserDigest");
            sCurrentUserDigest = Preferences.Account.CurrentUserDigest.get();
        }

        return sCurrentUserDigest;
    }

    public static synchronized void set(final UserDigest userDigest) {
        if (userDigest == null) {
            throw new IllegalArgumentException("userDigest parameter can't be null");
        }

        if (sCurrentUserDigest == null) {
            Timber.d(TAG, "current user set to \"" + userDigest.getUserId() + '"');
        } else if (sCurrentUserDigest.equals(userDigest)) {
            Timber.d(TAG, "current user (" + userDigest.getUserId() + ") refreshed");
        } else {
            Timber.w(TAG, "current user was \"" + sCurrentUserDigest.getUserId() +
                    "\", is now being set to \"" + userDigest.getUserId() + '"');
        }

        sCurrentUserDigest = userDigest;
        Crashlytics.setUserName(sCurrentUserDigest.getUserId());
        Preferences.Account.CurrentUserDigest.set(userDigest);
    }

    public static synchronized boolean shouldBeFetched() {
        return Preferences.Account.CsrfToken.exists() && Preferences.Account.Username.exists()
                && (sCurrentUserDigest == null || sCurrentUserDigest.getUser() == null);
    }

    public static synchronized void signOut() {
        Timber.d(TAG, "current user is signing out");
        OkHttpUtils.getCookieJar().clear();
        Preferences.eraseAll();
        sCurrentUserDigest = null;

        final ArrayList<Activity> activities = ActivityRegister.get();

        if (activities == null || activities.isEmpty()) {
            Timber.e(TAG, "unable to restart the app, no Activity is available");
            return;
        }

        final Activity activity = activities.get(0);
        Timber.d(TAG, "restarting the app using: " + activity.toString());

        activity.startActivity(LoginActivity.getNewTaskLaunchIntent(activity));

        for (final Activity a : activities) {
            a.finish();
        }

        activities.clear();
    }

}
