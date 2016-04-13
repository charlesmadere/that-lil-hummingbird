package com.charlesmadere.hummingbird.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class RatioFrameLayout extends FrameLayout {

    private RatioViewHelper mRatioViewHelper;


    public RatioFrameLayout(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        createRatioViewHelper(attrs);
    }

    public RatioFrameLayout(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        createRatioViewHelper(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RatioFrameLayout(final Context context, final AttributeSet attrs,
            final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        createRatioViewHelper(attrs);
    }

    private void createRatioViewHelper(final AttributeSet attrs) {
        mRatioViewHelper = RatioViewHelper.create(this, attrs);
    }

    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        final int[] dimens = mRatioViewHelper.measureSpecDimensions(widthMeasureSpec,
                heightMeasureSpec);
        super.onMeasure(dimens[0], dimens[1]);
    }

}
