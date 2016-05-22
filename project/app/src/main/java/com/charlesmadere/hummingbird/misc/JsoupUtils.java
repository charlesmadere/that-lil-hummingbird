package com.charlesmadere.hummingbird.misc;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public final class JsoupUtils {

    @Nullable
    public static String parse(@Nullable final String text) {
        if (TextUtils.isEmpty(text) || TextUtils.getTrimmedLength(text) == 0) {
            return text;
        }

        final Document document = Jsoup.parse(text, Constants.HUMMINGBIRD_URL);
        return document.text();
    }

}
