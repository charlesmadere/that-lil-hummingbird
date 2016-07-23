package com.charlesmadere.hummingbird.misc;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.ArrayList;

import static com.charlesmadere.hummingbird.misc.Constants.HUMMINGBIRD_URL;

public final class DeepLinkUtils {

    private static final String TAG = "DeepLinkUtils";


    @Nullable
    public static ArrayList<Intent> buildActivityStack(final Activity activity,
            @Nullable final Intent intent) {
        if (activity == null) {
            throw new IllegalArgumentException("activity parameter must not be null");
        }

        if (intent == null) {
            Timber.d(TAG, "Nothing to deep link to, Intent is null");
            return null;
        } else {
            return buildActivityStack(activity, intent.getData());
        }
    }

    @Nullable
    public static ArrayList<Intent> buildActivityStack(final Activity activity,
            @Nullable final String uri) {
        if (activity == null) {
            throw new IllegalArgumentException("activity parameter must not be null");
        }

        if (TextUtils.isEmpty(uri) || TextUtils.getTrimmedLength(uri) == 0) {
            Timber.d(TAG, "Nothing to deep link to - Intent's URI is null, empty, or whitespace");
            return null;
        }

        Timber.d(TAG, "Attempting to deep link to URI: \"" + uri + '"');

        if (!uri.startsWith(HUMMINGBIRD_URL)) {
            Timber.w(TAG, "Deep link URI isn't for Hummingbird");
            return null;
        }

        final String path = uri.substring(HUMMINGBIRD_URL.length(), uri.length());

        if (TextUtils.isEmpty(path)) {
            Timber.d(TAG, "Deep link URI's path is null or empty");
            return null;
        }

        final String[] paths = path.split("/");

        if (paths.length == 0) {
            Timber.d(TAG, "Deep link URI's path split is empty");
            return null;
        }

        if (paths[0].equalsIgnoreCase("dashboard")) {
            Timber.d(TAG, "Deep link URI is to the user's own dashboard");
            return null;
        }

        final ArrayList<Intent> activityStack = new ArrayList<>();

        // TODO

        if (activityStack.isEmpty()) {
            return null;
        } else {
            return activityStack;
        }
    }

    @Nullable
    public static ArrayList<Intent> buildActivityStack(final Activity activity,
            @Nullable final Uri data) {
        if (activity == null) {
            throw new IllegalArgumentException("activity parameter must not be null");
        }

        if (data == null) {
            Timber.d(TAG, "Nothing to deep link to, Intent's data is null");
            return null;
        } else {
            return buildActivityStack(activity, data.toString());
        }
    }

}
