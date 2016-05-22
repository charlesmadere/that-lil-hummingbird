package com.charlesmadere.hummingbird.misc;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.text.TextUtils;

public final class CustomTabsUtils {

    public static void openUrl(final Context context, @Nullable final String url) {
        if (context == null) {
            throw new IllegalArgumentException("context can't be null");
        }

        if (TextUtils.isEmpty(url) || TextUtils.getTrimmedLength(url) == 0) {
            return;
        }

        final CustomTabsIntent intent = new CustomTabsIntent.Builder()
                .build();

        intent.launchUrl((Activity) context, Uri.parse(url));
    }

}
