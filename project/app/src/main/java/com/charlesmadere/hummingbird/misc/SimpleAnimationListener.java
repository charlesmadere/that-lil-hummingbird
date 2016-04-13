package com.charlesmadere.hummingbird.misc;

import android.view.animation.Animation;

public abstract class SimpleAnimationListener implements Animation.AnimationListener {

    @Override
    public void onAnimationEnd(final Animation animation) {}

    @Override
    public void onAnimationRepeat(final Animation animation) {}

    @Override
    public void onAnimationStart(final Animation animation) {}

}
