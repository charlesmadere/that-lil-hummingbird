package com.charlesmadere.hummingbird.misc;

import android.animation.Animator;

public abstract class SimpleAnimatorListener implements Animator.AnimatorListener {

    @Override
    public void onAnimationCancel(final Animator animation) {}

    @Override
    public void onAnimationEnd(final Animator animation) {}

    @Override
    public void onAnimationRepeat(final Animator animation) {}

    @Override
    public void onAnimationStart(final Animator animation) {}

}
