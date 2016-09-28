package com.charlesmadere.hummingbird.preferences;

import android.content.Context;
import android.preference.PreferenceManager;

import com.charlesmadere.hummingbird.ThatLilHummingbird;
import com.charlesmadere.hummingbird.misc.Timber;
import com.charlesmadere.hummingbird.models.AppNewsStatus;
import com.charlesmadere.hummingbird.models.LaunchScreen;
import com.charlesmadere.hummingbird.models.LibrarySort;
import com.charlesmadere.hummingbird.models.NightMode;
import com.charlesmadere.hummingbird.models.PollFrequency;
import com.charlesmadere.hummingbird.models.TitleType;
import com.charlesmadere.hummingbird.models.UserDigest;

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

        erase(Account.TAG, General.TAG, Misc.TAG, NotificationPolling.TAG);
        Timber.d(TAG, "All preferences have been erased");
    }

    public static final class Account {
        private static final String TAG = Preferences.TAG + ".Account";
        private static final String CURRENT_USER_DIGEST_TAG = TAG + ".CurrentUserDigest";
        public static final GsonPreference<UserDigest> CurrentUserDigest;
        public static final StringPreference CsrfToken;
        public static final StringPreference Username;

        static {
            CurrentUserDigest = new GsonPreference<>(CURRENT_USER_DIGEST_TAG, "CurrentUserDigest", UserDigest.class, null);
            CsrfToken = new StringPreference(TAG, "CsrfToken", null);
            Username = new StringPreference(TAG, "Username", null);
        }

        public static void eraseAll() {
            erase(TAG, CURRENT_USER_DIGEST_TAG);
        }
    }

    public static final class General {
        private static final String TAG = Preferences.TAG + ".General";
        public static final BooleanPreference ShowNsfwContent;
        public static final GsonPreference<LaunchScreen> DefaultLaunchScreen;
        public static final GsonPreference<LibrarySort> DefaultLibrarySort;
        public static final GsonPreference<NightMode> Theme;
        public static final GsonPreference<TitleType> TitleLanguage;
        public static final IntegerPreference PreviousLaunchVersion;

        static {
            ShowNsfwContent = new BooleanPreference(TAG, "ShowNsfwContent", Boolean.FALSE);
            DefaultLaunchScreen = new GsonPreference<>(TAG, "DefaultLaunchScreen", LaunchScreen.class, LaunchScreen.ACTIVITY_FEED);
            DefaultLibrarySort = new GsonPreference<>(TAG, "DefaultLibrarySort", LibrarySort.class, LibrarySort.DATE);
            Theme = new GsonPreference<>(TAG, "NightMode", NightMode.class, NightMode.getDefault());
            TitleLanguage = new GsonPreference<>(TAG, "TitleLanguage", TitleType.class, TitleType.ENGLISH);
            PreviousLaunchVersion = new IntegerPreference(TAG, "PreviousLaunchVersion", null);
        }
    }

    public static final class Misc {
        private static final String TAG = Preferences.TAG + ".Misc";
        public static final GsonPreference<AppNewsStatus> AppNewsAvailability;

        static {
            AppNewsAvailability = new GsonPreference<>(TAG, "AppNewsAvailability", AppNewsStatus.class, null);
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
