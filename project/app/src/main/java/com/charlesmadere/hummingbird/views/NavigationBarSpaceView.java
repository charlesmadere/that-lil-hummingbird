package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.support.v4.widget.Space;
import android.util.AttributeSet;

import com.charlesmadere.hummingbird.misc.MiscUtils;

public class NavigationBarSpaceView extends Space {

    public NavigationBarSpaceView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public NavigationBarSpaceView(final Context context, final AttributeSet attrs,
            final int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(final int widthMeasureSpec, int heightMeasureSpec) {
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(MiscUtils.getNavigationBarHeight(
                getResources()), MeasureSpec.EXACTLY);
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

}
