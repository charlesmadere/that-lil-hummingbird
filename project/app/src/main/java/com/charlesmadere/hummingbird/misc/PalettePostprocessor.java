package com.charlesmadere.hummingbird.misc;

import android.animation.ArgbEvaluator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.graphics.Palette;

import com.charlesmadere.hummingbird.R;
import com.facebook.imagepipeline.request.BasePostprocessor;

import java.lang.ref.WeakReference;

public final class PalettePostprocessor extends BasePostprocessor implements
        Palette.PaletteAsyncListener {

    private static final long TRANSITION_TIME_MS = 250L;

    private final ArgbEvaluator mArgbEvaluator;
    private final WeakReference<Activity> mActivity;
    private final WeakReference<AppBarLayout> mAppBarLayout;
    private final WeakReference<CollapsingToolbarLayout> mCollapsingToolbarLayout;
    private final WeakReference<TabLayout> mTabLayout;


    public PalettePostprocessor(final Activity activity, final AppBarLayout appBarLayout,
            final CollapsingToolbarLayout collapsingToolbarLayout, final TabLayout tabLayout) {
        mActivity = new WeakReference<>(activity);
        mAppBarLayout = new WeakReference<>(appBarLayout);
        mCollapsingToolbarLayout = new WeakReference<>(collapsingToolbarLayout);
        mTabLayout = new WeakReference<>(tabLayout);
        mArgbEvaluator = new ArgbEvaluator();
    }

    private boolean isAlive() {
        final Activity activity = mActivity.get();
        return activity != null && !activity.isDestroyed() && mAppBarLayout.get() != null &&
                mCollapsingToolbarLayout.get() != null && mTabLayout.get() != null;
    }

    @Override
    public void onGenerated(final Palette palette) {
        if (!isAlive()) {
            return;
        }

        final AppBarLayout appBarLayout = mAppBarLayout.get();
        final CollapsingToolbarLayout collapsingToolbarLayout = mCollapsingToolbarLayout.get();
        final TabLayout tabLayout = mTabLayout.get();

        if (appBarLayout == null || collapsingToolbarLayout == null || tabLayout == null) {
            return;
        }

        final Context context = tabLayout.getContext();
        final int darkMutedColor = palette.getDarkMutedColor(ContextCompat.getColor(context,
                R.color.colorPrimaryDark));
        final int darkVibrantColor = palette.getDarkVibrantColor(ContextCompat.getColor(context,
                R.color.colorPrimary));
        final int vibrantColor = palette.getVibrantColor(ContextCompat.getColor(context,
                R.color.colorAccent));

        // TODO use ValueAnimator
        // https://stackoverflow.com/questions/2614545/animate-change-of-view-background-color-in-android

        appBarLayout.setBackgroundColor(darkVibrantColor);
        collapsingToolbarLayout.setStatusBarScrimColor(darkMutedColor);
        collapsingToolbarLayout.setContentScrimColor(darkVibrantColor);
        tabLayout.setSelectedTabIndicatorColor(vibrantColor);
    }

    @Override
    public void process(final Bitmap bitmap) {
        super.process(bitmap);

        if (!isAlive()) {
            return;
        }

        Palette.from(bitmap).generate(this);
    }

}
