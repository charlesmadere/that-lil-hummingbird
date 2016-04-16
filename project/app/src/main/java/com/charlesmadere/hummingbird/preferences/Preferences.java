package com.charlesmadere.hummingbird.preferences;

import android.content.Context;

import com.charlesmadere.hummingbird.Hummingbird;
import com.charlesmadere.hummingbird.models.AnimeV2;

public final class Preferences {

    private static final String TAG = "Preferences";


    private static void erase(final String... tags) {
        if (tags == null || tags.length == 0) {
            return;
        }

        final Context context = Hummingbird.get();

        for (final String tag : tags) {
            context.getSharedPreferences(tag, Context.MODE_PRIVATE)
                    .edit()
                    .clear()
                    .apply();
        }
    }

    public static void eraseAll() {
        erase(Account.TAG, General.TAG);
    }

    public static final class Account {
        private static final String TAG = Preferences.TAG + ".Account";
        public static final StringPreference AuthToken;
        public static final StringPreference Username;

        static {
            AuthToken = new StringPreference(TAG, "AuthToken", null);
            Username = new StringPreference(TAG, "Username", null);
        }

        public static void eraseAll() {
            erase(Account.TAG);
        }
    }

    public static final class General {
        private static final String TAG = Preferences.TAG + ".General";
        public static final GsonPreference<AnimeV2.Titles.Type> TitleLanguage;

        static {
            TitleLanguage = new GsonPreference<>(TAG, "TitleLanguage", AnimeV2.Titles.Type.ENGLISH,
                    AnimeV2.Titles.Type.class);
        }
    }

}
