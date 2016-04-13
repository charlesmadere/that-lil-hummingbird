package com.charlesmadere.hummingbird.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.misc.SimpleAnimationListener;

public class SimpleProgressView extends FrameLayout {

    public SimpleProgressView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public SimpleProgressView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SimpleProgressView(final Context context, final AttributeSet attrs,
            final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void fadeIn() {
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
        animation.setAnimationListener(new SimpleAnimationListener() {
            @Override
            public void onAnimationStart(final Animation animation) {
                setVisibility(VISIBLE);
            }
        });

        startAnimation(animation);
    }

    public void fadeOut() {
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.fade_out);
        animation.setAnimationListener(new SimpleAnimationListener() {
            @Override
            public void onAnimationEnd(final Animation animation) {
                setVisibility(GONE);
            }
        });

        startAnimation(animation);
    }

    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        return true;
    }

}
