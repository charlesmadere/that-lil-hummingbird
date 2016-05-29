package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.util.AttributeSet;
import android.view.View;

import com.charlesmadere.hummingbird.misc.MiscUtils;

public class LinkTextView extends TypefaceTextView {

    public LinkTextView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public LinkTextView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected CharSequence buildLinkSpan(CharSequence text) {
        if (TextUtils.isEmpty(text) || TextUtils.getTrimmedLength(text) == 0) {
            return text;
        }

        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        URLSpan[] urls = builder.getSpans(0, builder.length(), URLSpan.class);

        if (urls == null || urls.length == 0) {
            return text;
        }

        for (final URLSpan url : urls) {
            ClickableSpan clickableSpan = new ClickableSpan() {
                @Override
                public void onClick(final View widget) {
                    MiscUtils.openUrl(getContext(), url.getURL());
                }
            };

            builder.setSpan(clickableSpan, builder.getSpanStart(url), builder.getSpanEnd(url),
                    builder.getSpanFlags(url));
            builder.removeSpan(url);
        }

        return builder;
    }

}
