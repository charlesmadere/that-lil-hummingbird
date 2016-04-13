package com.charlesmadere.hummingbird.misc;

import com.charlesmadere.hummingbird.models.User;
import com.charlesmadere.hummingbird.preferences.Preferences;

public final class CurrentUser {

    private static final String TAG = "CurrentUser";

    private static User sCurrentUser;


    public static boolean exists() {
        return Preferences.Account.AuthToken.exists() && Preferences.Account.Username.exists()
                && get() != null;
    }

    public static synchronized User get() {
        return sCurrentUser;
    }

    public static synchronized void logout() {
        Preferences.Account.eraseAll();
        sCurrentUser = null;
    }

    public static synchronized void set(final User user) {
        Timber.d(TAG, "current user set to \"" + user.getName() + '"');
        sCurrentUser = user;
    }

    public static boolean shouldBeFetched() {
        return Preferences.Account.AuthToken.exists() && Preferences.Account.Username.exists()
                && get() == null;
    }

}
