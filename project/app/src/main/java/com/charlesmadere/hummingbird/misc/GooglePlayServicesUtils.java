package com.charlesmadere.hummingbird.misc;

import com.charlesmadere.hummingbird.ThatLilHummingbird;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public final class GooglePlayServicesUtils {

    private static final String TAG = "GooglePlayServicesUtils";


    public static boolean areGooglePlayServicesAvailable() {
        final int connectionStatus = getConnectionStatus();
        final boolean isAvailable;

        if (connectionStatus == ConnectionResult.SUCCESS) {
            Timber.d(TAG, "Google Play Services are available (connectionStatus: " +
                    connectionStatus);
            isAvailable = true;
        } else {
            Timber.w(TAG, "Google Play Services are unavailable (connectionStatus: " +
                    connectionStatus + ")");
            isAvailable = false;
        }

        return isAvailable;
    }

    public static int getConnectionStatus() {
        final int connectionStatus = GoogleApiAvailability.getInstance()
                .isGooglePlayServicesAvailable(ThatLilHummingbird.get());
        Timber.d(TAG, "Google Play Services connection status: " + connectionStatus);
        return connectionStatus;
    }

    public static boolean isUserResolvableError(final int connectionStatus) {
        final boolean isUserResolvable = GoogleApiAvailability.getInstance().
                isUserResolvableError(connectionStatus);

        if (isUserResolvable) {
            Timber.d(TAG, "Google Play Services connection status (" + connectionStatus +
                    ") is user resolvable");
        } else {
            Timber.w(TAG, "Google Play Services connection status (" + connectionStatus +
                    ") is not user resolvable");
        }

        return isUserResolvable;
    }

}
