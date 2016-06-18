package com.charlesmadere.hummingbird.preferences;

import android.content.Context;
import android.preference.PreferenceManager;

import com.charlesmadere.hummingbird.ThatLilHummingbird;
import com.charlesmadere.hummingbird.misc.Timber;
import com.charlesmadere.hummingbird.models.NightMode;
import com.charlesmadere.hummingbird.models.PollFrequency;
import com.charlesmadere.hummingbird.models.TitleType;

public final class Preferences {

    private static final String TAG = "Preferences";


    private static void erase(final String... tags) {
        if (tags == null || tags.length == 0) {
            return;
        }

        final Context context = ThatLilHummingbird.get();

        for (final String tag : tags) {
            context.getSharedPreferences(tag, Context.MODE_PRIVATE)
                    .edit()
                    .clear()
                    .apply();
        }
    }

    public static void eraseAll() {
        PreferenceManager.getDefaultSharedPreferences(ThatLilHummingbird.get())
                .edit()
                .clear()
                .apply();

        erase(Account.TAG, General.TAG);
        Timber.d(TAG, "All preferences have been erased");
    }

    public static final class Account {
        private static final String TAG = Preferences.TAG + ".Account";
        public static final StringPreference AuthToken;
        public static final StringPreference Username;

        static {
            AuthToken = new StringPreference(TAG, "AuthToken", null);
            Username = new StringPreference(TAG, "Username", null);
        }
    }

    public static final class General {
        private static final String TAG = Preferences.TAG + ".General";
        public static final BooleanPreference ShowNsfwContent;
        public static final GsonPreference<NightMode> Theme;
        public static final GsonPreference<TitleType> TitleLanguage;

        static {
            ShowNsfwContent = new BooleanPreference(TAG, "ShowNsfwContent", Boolean.FALSE);
            Theme = new GsonPreference<>(TAG, "NightMode", NightMode.class, NightMode.getDefault());
            TitleLanguage = new GsonPreference<>(TAG, "TitleLanguage", TitleType.class, TitleType.ENGLISH);
        }
    }

    public static final class Misc {
        private static final String TAG = Preferences.TAG + ".Misc";
        public static final BooleanPreference ImportantNewsAvailable;

        static {
            ImportantNewsAvailable = new BooleanPreference(TAG, "ImportantNewsAvailable", Boolean.FALSE);
        }
    }

    public static final class NotificationPolling {
        private static final String TAG = Preferences.TAG + ".Sync";
        public static final BooleanPreference IsEnabled;
        public static final BooleanPreference IsPowerRequired;
        public static final BooleanPreference IsWifiRequired;
        public static final GsonPreference<PollFrequency> Frequency;
        public static final LongPreference LastPoll;

        static {
            IsEnabled = new BooleanPreference(TAG, "IsEnabled", Boolean.TRUE);
            IsPowerRequired = new BooleanPreference(TAG, "IsPowerRequired", Boolean.FALSE);
            IsWifiRequired = new BooleanPreference(TAG, "IsWifiRequired", Boolean.TRUE);
            Frequency = new GsonPreference<>(TAG, "Frequency", PollFrequency.class, PollFrequency.DAILY);
            LastPoll = new LongPreference(TAG, "LastPoll", null);
        }
    }

}
