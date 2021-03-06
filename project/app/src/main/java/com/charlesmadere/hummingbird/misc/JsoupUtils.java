package com.charlesmadere.hummingbird.misc;

import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
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

    private static final String TAG = "JsoupUtils";


    private static void fixA(final Document document) {
        final Elements as = document.select("a");

        if (as == null || as.isEmpty()) {
            return;
        }

        for (final Element a : as) {
            String href = a.attr("href");

            stripAttributes(a);
            stripChildren(a);

            if (!TextUtils.isEmpty(href) && href.startsWith(Constants.HUMMINGBIRD_URL_RELATIVE)) {
                href = "https:" + href;
            }

            a.attr("href", href);

            if (TextUtils.isEmpty(a.html())) {
                a.html(href);
            }
        }
    }

    private static void fixBlockquote(final Document document) {
        final Elements blockquotes = document.select("blockquote");
        removeElementsPreserveContents(blockquotes);
    }

    private static void fixH(final Document document) {
        fixH(document, "1");
        fixH(document, "2");
        fixH(document, "3");
        fixH(document, "4");
        fixH(document, "5");
        fixH(document, "6");
    }

    private static void fixH(final Document document, final String which) {
        if (TextUtils.isEmpty(which)) {
            return;
        }

        final Elements hs = document.select("h" + which);
        removeElementsPreserveContents(hs);
    }

    private static void fixIframe(final Document document) {
        Elements iframes = document.select("iframe");

        if (iframes == null || iframes.isEmpty()) {
            return;
        }

        iframes = iframes.tagName("a");

        for (final Element iframe : iframes) {
            String src = iframe.attr("src");

            stripAttributes(iframe);
            stripChildren(iframe);

            if (src.startsWith("//")) {
                src = "http:" + src;
            }

            iframe.attr("href", src);
            iframe.html(src);
        }
    }

    private static void fixImg(final Document document) {
        Elements imgs = document.select("img");

        if (imgs == null || imgs.isEmpty()) {
            return;
        }

        imgs = imgs.tagName("a");

        for (final Element img : imgs) {
            final String src = img.attr("src");
            final String alt = img.attr("alt");

            stripAttributes(img);
            stripChildren(img);

            img.attr("href", src);

            if (!TextUtils.isEmpty(alt) && TextUtils.isEmpty(img.html())) {
                img.html(alt);
            }
        }
    }

    @Nullable
    public static String getCsrfToken(@Nullable final String signInPage) {
        if (TextUtils.isEmpty(signInPage)) {
            return null;
        }

        final int indexOf = signInPage.indexOf("name=\"csrf-token\"");

        if (indexOf == -1) {
            return null;
        }

        int endOfToken = -1;

        for (int i = indexOf; i >= 0; --i) {
            if (signInPage.charAt(i) == '"') {
                endOfToken = i;
                break;
            }
        }

        if (endOfToken < 0) {
            return null;
        }

        int startOfToken = -1;

        for (int i = endOfToken - 1; i >= 0; --i) {
            if (signInPage.charAt(i) == '"') {
                startOfToken = i + 1;
                break;
            }
        }

        if (startOfToken < 0 || endOfToken - startOfToken < 1) {
            return null;
        } else {
            return signInPage.substring(startOfToken, endOfToken);
        }
    }

    @Nullable
    @WorkerThread
    public static CharSequence parse(@Nullable String text) {
        if (TextUtils.isEmpty(text)) {
            return null;
        }

        text = text.trim();

        if (TextUtils.isEmpty(text)) {
            return null;
        }

        final Document document;

        try {
            document = Jsoup.parse(text, Constants.HUMMINGBIRD_URL_HTTPS);
        } catch (final Exception e) {
            // This is a bit over cautious but probably a good thing. We really want any problems
            // with the library to be fully caught, logged, and understood.
            Timber.e(TAG, "parse resulted in an exception", e);
            throw e;
        }

        if (document == null) {
            Timber.e(TAG, "parse resulted in a null document");
            return null;
        }

        fixA(document);
        fixBlockquote(document);
        fixH(document);
        fixIframe(document);
        fixImg(document);
        stripScript(document);

        text = document.body().toString().trim();

        if (TextUtils.isEmpty(text)) {
            return null;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT);
        } else {
            // noinspection deprecation
            return Html.fromHtml(text);
        }
    }

    private static void removeElementsPreserveContents(@Nullable final Elements elements) {
        if (elements == null || elements.isEmpty()) {
            return;
        }

        for (final Element element : elements) {
            final String inner = element.html();

            if (!TextUtils.isEmpty(inner)) {
                element.before(inner);
            }

            element.remove();
        }
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
            final String key = attribute.getKey();

            if (!TextUtils.isEmpty(key)) {
                element.removeAttr(key);
            }
        }
    }

    private static void stripChildren(final Element element) {
        final Elements elements = element.children();

        if (elements == null || elements.isEmpty()) {
            return;
        }

        elements.remove();
    }

    private static void stripScript(final Document document) {
        final Elements scripts = document.select("script");

        if (scripts == null || scripts.isEmpty()) {
            return;
        }

        scripts.remove();
    }

}
