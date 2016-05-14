package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.AttributeSet;

import com.charlesmadere.hummingbird.misc.CustomTypefaceSpan;

public class NotificationTitleTextView extends AppCompatTextView {

    private CustomTypefaceSpan mSecondaryTypefaceSpan;
    private CustomTypefaceSpan mUserNameSpan;
    private ForegroundColorSpan mSecondaryColorSpan;
    private RelativeSizeSpan mSecondarySizeSpan;


    public NotificationTitleTextView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        parseAttributes(attrs);
    }

    public NotificationTitleTextView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parseAttributes(attrs);
    }

    private void parseAttributes(final AttributeSet attrs) {
        if (isInEditMode()) {
            return;
        }


    }

}
