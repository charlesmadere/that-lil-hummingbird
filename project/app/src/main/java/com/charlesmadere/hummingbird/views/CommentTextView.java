package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.util.AttributeSet;
import android.view.View;

import com.charlesmadere.hummingbird.misc.MiscUtils;

public class CommentTextView extends KeyValueTextView {

    public CommentTextView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public CommentTextView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private Spanned buildSpanned(CharSequence text) {
        final SpannableStringBuilder builder = new SpannableStringBuilder(text);
        URLSpan[] urls = builder.getSpans(0, builder.length(), URLSpan.class);

        if (urls != null && urls.length >= 1) {
            setMovementMethod(LinkMovementMethod.getInstance());

            for (final URLSpan url : urls) {
                final ClickableSpan cs = new ClickableSpan() {
                    @Override
                    public void onClick(final View widget) {
                        MiscUtils.openUrl(getContext(), url.getURL());
                    }
                };

                final int start = builder.getSpanStart(url);
                final int end = builder.getSpanEnd(url);
                builder.setSpan(cs, start, end, builder.getSpanFlags(url));
                builder.removeSpan(url);
            }
        }

        return builder;
    }

    @Override
    public void setText(final CharSequence text, final BufferType type) {
        if (TextUtils.isEmpty(text)) {
            super.setText(text, type);
        } else {
            final Spanned spanned = buildSpanned(text);
            super.setText(spanned, type);
        }
    }

}
