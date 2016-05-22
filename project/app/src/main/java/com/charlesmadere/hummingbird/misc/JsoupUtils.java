package com.charlesmadere.hummingbird.misc;

import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

public final class JsoupUtils {

    private static final String RELATIVE_URL = "//hummingbird.me/";
    private static final Whitelist WHITELIST;


    static {
        WHITELIST = Whitelist.simpleText()
                .addTags("a")
                .addTags("img");
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
    public static CharSequence parse(@Nullable final String text) {
        if (TextUtils.isEmpty(text) || TextUtils.getTrimmedLength(text) == 0) {
            return text;
        }

        final Document document = Jsoup.parse(text, Constants.HUMMINGBIRD_URL);
        Elements as = document.select("a");

        if (as != null && !as.isEmpty()) {
            for (final Element a : as) {
                a.children().remove();
                String href = a.attr("href");

                if (!TextUtils.isEmpty(href) && href.startsWith(RELATIVE_URL)) {
                    href = "https:" + href;
                    a.attr("href", href);
                }

                if (TextUtils.isEmpty(a.html())) {
                    a.html(href);
                }
            }
        }

        Elements imgs = document.select("img");

        if (imgs != null && !imgs.isEmpty()) {
            imgs = imgs.tagName("a");

            for (final Element img : imgs) {
                final String src = img.attr("src");
                img.removeAttr("src");
                img.attr("href", src);
            }
        }

        return Html.fromHtml(document.body().toString());
    }

}
