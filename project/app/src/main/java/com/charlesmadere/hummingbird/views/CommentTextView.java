package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.URLSpan;
import android.util.AttributeSet;
import android.view.View;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.misc.MiscUtils;
import com.charlesmadere.hummingbird.models.CommentStory;
import com.charlesmadere.hummingbird.models.ReplySubstory;
import com.charlesmadere.hummingbird.preferences.Preferences;

public class CommentTextView extends KeyValueTextView {

    private ForegroundColorSpan mShowNsfwCommentSpan;


    public CommentTextView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public CommentTextView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private Spanned buildSpanned(CharSequence text) {
        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        URLSpan[] urls = builder.getSpans(0, builder.length(), URLSpan.class);

        if (urls != null && urls.length >= 1) {
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
    protected void onFinishInflate() {
        super.onFinishInflate();
        mShowNsfwCommentSpan = new ForegroundColorSpan(MiscUtils.getAttrColor(getContext(),
                R.attr.colorAccent));
    }

    public void setContent(final CommentStory content) {
        if (content.isAdult() && !Boolean.TRUE.equals(Preferences.General.ShowNsfwContent.get())) {
            final SpannableString spannable = new SpannableString(getResources().getText(
                    R.string.nsfw_content));
            spannable.setSpan(mShowNsfwCommentSpan, 0, spannable.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            setText(spannable);
        } else {
            setText(content.getComment());
        }
    }

    public void setContent(final ReplySubstory content) {
        setText(content.getUser().getId(), content.getReply());
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
