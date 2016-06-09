package com.charlesmadere.hummingbird.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.charlesmadere.hummingbird.R;

public class GroupEnabledLinearLayout extends LinearLayout {

    private boolean mStartEnabled;


    public GroupEnabledLinearLayout(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        parseAttributes(attrs);
    }

    public GroupEnabledLinearLayout(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parseAttributes(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public GroupEnabledLinearLayout(final Context context, final AttributeSet attrs,
            final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        parseAttributes(attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setEnabled(mStartEnabled);
    }

    private void parseAttributes(@Nullable final AttributeSet attrs) {
        if (attrs == null) {
            return;
        }

        final TypedArray ta = getContext().obtainStyledAttributes(attrs,
                R.styleable.GroupEnabledLinearLayout);
        mStartEnabled = ta.getBoolean(R.styleable.GroupEnabledLinearLayout_start_enabled, true);
        ta.recycle();
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
