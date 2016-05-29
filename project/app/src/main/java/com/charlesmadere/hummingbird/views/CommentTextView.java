package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.misc.MiscUtils;
import com.charlesmadere.hummingbird.models.CommentStory;
import com.charlesmadere.hummingbird.preferences.Preferences;

public class CommentTextView extends LinkTextView {

    private ForegroundColorSpan mShowNsfwCommentSpan;


    public CommentTextView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public CommentTextView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
            setText(buildLinkSpan(content.getComment()));
        }
    }

}
