package com.charlesmadere.hummingbird.misc;

import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;

public final class JsoupUtils {

    private static final String RELATIVE_URL = "//hummingbird.me/";


    private static void fixA(final Document document) {
        final Elements as = document.select("a");

        if (as != null && !as.isEmpty()) {
            for (final Element a : as) {
                String href = a.attr("href");

                stripAttributes(a);
                stripChildren(a);

                if (!TextUtils.isEmpty(href) && href.startsWith(RELATIVE_URL)) {
                    href = "https:" + href;
                }

                a.attr("href", href);

                if (TextUtils.isEmpty(a.html())) {
                    a.html(href);
                }
            }
        }
    }

    private static void fixIframe(final Document document) {
        Elements iframes = document.select("iframe");

        if (iframes != null && !iframes.isEmpty()) {
            iframes = iframes.tagName("a");

            for (final Element iframe : iframes) {
                final String src = iframe.attr("src");

                stripAttributes(iframe);
                stripChildren(iframe);

                iframe.attr("href", src);
                iframe.html(src);
            }
        }
    }

    private static void fixImg(final Document document) {
        Elements imgs = document.select("img");

        if (imgs != null && !imgs.isEmpty()) {
            imgs = imgs.tagName("a");

            for (final Element img : imgs) {
                final String src = img.attr("src");

                stripAttributes(img);
                stripChildren(img);

                img.attr("href", src);
            }
        }
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

        fixA(document);
        fixIframe(document);
        fixImg(document);

        return Html.fromHtml(document.body().toString().trim());
    }

    private static void stripAttributes(final Element element) {
        final Attributes attributes = element.attributes();

        if (attributes == null) {
            return;
        }

        final List<Attribute> attributesList = attributes.asList();

        if (attributesList == null || attributesList.isEmpty()) {
            return;
        }

        for (final Attribute attribute : attributesList) {
            element.removeAttr(attribute.getKey());
        }
    }

    private static void stripChildren(final Element element) {
        final Elements elements = element.children();

        if (elements == null || elements.isEmpty()) {
            return;
        }

        elements.remove();
    }

}
