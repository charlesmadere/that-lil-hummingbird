package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.AdapterView;
import com.charlesmadere.hummingbird.misc.Timber;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TimberEntryView extends LinearLayout implements AdapterView<Timber.BaseEntry> {

    @Bind(R.id.tvStackTrace)
    TextView mStackTrace;

    @Bind(R.id.tvTagAndMessage)
    TextView mTagAndMessage;

    private StyleSpan mBoldSpan;


    public TimberEntryView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public TimberEntryView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        if (isInEditMode()) {
            return;
        }

        ButterKnife.bind(this);
        mBoldSpan = new StyleSpan(Typeface.BOLD);
    }

    @Override
    public void setContent(final Timber.BaseEntry content) {
        final SpannableStringBuilder tagAndMessage = new SpannableStringBuilder(content.getTag());
        tagAndMessage.setSpan(mBoldSpan, 0, tagAndMessage.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tagAndMessage.append(" - ");
        tagAndMessage.append(content.getMessage());
        mTagAndMessage.setText(tagAndMessage);

        if (content.hasStackTrace()) {
            mStackTrace.setVisibility(VISIBLE);
        } else {
            mStackTrace.setVisibility(GONE);
        }

        final int color = ContextCompat.getColor(getContext(), content.getColor());
        mTagAndMessage.setTextColor(color);
        mStackTrace.setTextColor(color);
    }

}
