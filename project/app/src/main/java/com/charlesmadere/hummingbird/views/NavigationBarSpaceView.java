package com.charlesmadere.hummingbird.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v4.widget.Space;
import android.util.AttributeSet;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.misc.MiscUtils;

/**
 * A View that will apply the height of the Android navigation bar to itself.
 */
public class NavigationBarSpaceView extends Space {

    public NavigationBarSpaceView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        parseAttributes(attrs);
    }

    public NavigationBarSpaceView(final Context context, final AttributeSet attrs,
            final int defStyle) {
        super(context, attrs, defStyle);
        parseAttributes(attrs);
    }

    @Override
    protected void onMeasure(final int widthMeasureSpec, int heightMeasureSpec) {
        final int navigationBarHeight = MiscUtils.getNavigationBarHeight(getResources());
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(navigationBarHeight, MeasureSpec.EXACTLY);
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    private void parseAttributes(final AttributeSet attrs) {
        final TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.View);
        final boolean alwaysShow = ta.getBoolean(R.styleable.View_always_show, false);
        ta.recycle();

        if (alwaysShow || Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setVisibility(INVISIBLE);
        } else {
            setVisibility(GONE);
        }
    }

}
