package com.charlesmadere.hummingbird.misc;

import android.support.annotation.Nullable;

import com.charlesmadere.hummingbird.models.AppNews;
import com.charlesmadere.hummingbird.models.AppNewsStatus;
import com.charlesmadere.hummingbird.models.ErrorInfo;
import com.charlesmadere.hummingbird.networking.Api;
import com.charlesmadere.hummingbird.networking.ApiResponse;
import com.charlesmadere.hummingbird.preferences.Preferences;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public final class AppNewsChecker {

    private static final String TAG = "AppNewsChecker";
    private static final long REFRESH_INTERNAL = TimeUnit.HOURS.toMillis(8L);


    private static void checkForImportantNews(final AppNewsStatus appNewsStatus) {
        final long pollTime = appNewsStatus.getPollTime();
        appNewsStatus.updatePollTime();

        Timber.d(TAG, "Beginning poll for app news...");

        Api.getAppNews(new ApiResponse<ArrayList<AppNews>>() {
            @Override
            public void failure(@Nullable final ErrorInfo error) {
                Timber.e(TAG, "Poll for app news failed");
                Preferences.Misc.AppNewsAvailability.set(appNewsStatus);
            }

            @Override
            public void success(@Nullable final ArrayList<AppNews> appNews) {
                if (appNews == null || appNews.isEmpty()) {
                    Preferences.Misc.AppNewsAvailability.set(appNewsStatus);
                    return;
                }

                for (final AppNews an : appNews) {
                    if (an.getEpoch() < pollTime) {
                        break;
                    }

                    if (an.isImportant()) {
                        appNewsStatus.setImportantNewsAvailable(true);
                        break;
                    }
                }

                Timber.d(TAG, "Poll for app news completed (important news available: "
                        + appNewsStatus.isImportantNewsAvailable() + ')');

                Preferences.Misc.AppNewsAvailability.set(appNewsStatus);
            }
        });
    }

    public static void refresh() {
        AppNewsStatus appNewsStatus = Preferences.Misc.AppNewsAvailability.get();

        if (appNewsStatus == null) {
            appNewsStatus = new AppNewsStatus();
        }

        if (System.currentTimeMillis() - appNewsStatus.getPollTime() < REFRESH_INTERNAL) {
            return;
        }

        checkForImportantNews(appNewsStatus);
    }

}
