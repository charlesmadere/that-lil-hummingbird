package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.misc.MiscUtils;
import com.charlesmadere.hummingbird.misc.Timber;
import com.charlesmadere.hummingbird.models.ReplySubstory;

public class ReplyTextView extends LinkTextView {

    private static final String TAG = "ReplyTextView";

    private ForegroundColorSpan mUserSpan;


    public ReplyTextView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public ReplyTextView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mUserSpan = new ForegroundColorSpan(MiscUtils.getAttrColor(getContext(),
                R.attr.colorAccent));
    }

    public void setContent(final ReplySubstory content) {
        final SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(content.getUserId());
        builder.setSpan(mUserSpan, 0, builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.append(' ');

        try {
            builder.append(content.getReply());
        } catch (final RuntimeException e) {
            Timber.e(TAG, "error appending reply (" + content.getStoryId() +
                    ") (" + content.getId() + "): " + content.getPlainTextReply(), e);
            throw new RuntimeException("error appending reply (" + content.getStoryId() +
                    ") (" + content.getId() + ")", e);
        }

        setText(builder);
    }

}
