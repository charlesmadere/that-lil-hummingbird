package com.charlesmadere.hummingbird.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

public class GroupEnabledLinearLayout extends LinearLayout {

    public GroupEnabledLinearLayout(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public GroupEnabledLinearLayout(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public GroupEnabledLinearLayout(final Context context, final AttributeSet attrs,
            final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void setEnabled(final boolean enabled) {
        super.setEnabled(enabled);

        for (int i = 0; i < getChildCount(); ++i) {
            final View child = getChildAt(i);
            child.setEnabled(enabled);
        }
    }

}
