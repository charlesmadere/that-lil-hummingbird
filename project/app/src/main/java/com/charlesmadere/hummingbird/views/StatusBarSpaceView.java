package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.os.Build;
import android.support.v4.widget.Space;
import android.util.AttributeSet;

import com.charlesmadere.hummingbird.misc.MiscUtils;

/**
 * A View that will apply the height of the Android status bar to itself on versions of the
 * platform greater than or equal to {@link Build.VERSION_CODES#LOLLIPOP}. On all Android
 * versions older than that, this view simply won't appear at all.
 */
public class StatusBarSpaceView extends Space {

    public StatusBarSpaceView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public StatusBarSpaceView(final Context context, final AttributeSet attrs,
            final int defStyle) {
        super(context, attrs, defStyle);
        initialize();
    }

    private void initialize() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setVisibility(INVISIBLE);
        } else {
            setVisibility(GONE);
        }
    }

    @Override
    protected void onMeasure(final int widthMeasureSpec, int heightMeasureSpec) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            heightMeasureSpec = MiscUtils.getStatusBarHeight(getResources());
        } else {
            heightMeasureSpec = 0;
        }

        heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightMeasureSpec, MeasureSpec.EXACTLY);
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

}
