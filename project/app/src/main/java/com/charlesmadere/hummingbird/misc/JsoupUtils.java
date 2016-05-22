package com.charlesmadere.hummingbird.misc;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

public final class JsoupUtils {

    private static final Whitelist WHITELIST;


    static {
        WHITELIST = Whitelist.simpleText()
                .addTags("a");
    }

    @Nullable
    public static CharSequence parse(@Nullable final CharSequence text) {
        if (TextUtils.isEmpty(text) || TextUtils.getTrimmedLength(text) == 0) {
            return text;
        } else {
            return parse(text.toString());
        }
    }

    @Nullable
    public static CharSequence parse(@Nullable String text) {
        if (TextUtils.isEmpty(text) || TextUtils.getTrimmedLength(text) == 0) {
            return text;
        }

        text = Jsoup.clean(text, WHITELIST);
        final Document document = Jsoup.parse(text, Constants.HUMMINGBIRD_URL);
        return document.text();
    }

}
