package com.charlesmadere.hummingbird.misc;

import android.animation.ArgbEvaluator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.graphics.Palette;

import com.charlesmadere.hummingbird.R;
import com.facebook.imagepipeline.request.BasePostprocessor;

import java.lang.ref.WeakReference;

public final class PalettePostprocessor extends BasePostprocessor implements
        Palette.PaletteAsyncListener {

    private static final long TRANSITION_TIME_MS = 250L;

    private final ArgbEvaluator mArgbEvaluator;
    private final WeakReference<Activity> mActivity;
    private final WeakReference<CollapsingToolbarLayout> mCollapsingToolbarLayout;
    private final WeakReference<DrawerLayout> mDrawerLayout;
    private final WeakReference<TabLayout> mTabLayout;


    public PalettePostprocessor(final Activity activity,
            final CollapsingToolbarLayout collapsingToolbarLayout, final DrawerLayout drawerLayout,
            final TabLayout tabLayout) {
        mActivity = new WeakReference<>(activity);
        mCollapsingToolbarLayout = new WeakReference<>(collapsingToolbarLayout);
        mDrawerLayout = new WeakReference<>(drawerLayout);
        mTabLayout = new WeakReference<>(tabLayout);
        mArgbEvaluator = new ArgbEvaluator();
    }

    private boolean isAlive() {
        final Activity activity = mActivity.get();
        return activity != null && !activity.isDestroyed() &&
                mCollapsingToolbarLayout.get() != null && mDrawerLayout.get() != null &&
                mTabLayout.get() != null;
    }

    @Override
    public void onGenerated(final Palette palette) {
        if (!isAlive()) {
            return;
        }

        final CollapsingToolbarLayout collapsingToolbarLayout = mCollapsingToolbarLayout.get();
        final DrawerLayout drawerLayout = mDrawerLayout.get();
        final TabLayout tabLayout = mTabLayout.get();

        if (collapsingToolbarLayout == null || drawerLayout == null || tabLayout == null) {
            return;
        }

        final Context context = tabLayout.getContext();
        final int darkMutedColor = palette.getDarkMutedColor(ContextCompat.getColor(context,
                R.color.colorPrimaryDark));
        final int darkVibrantColor = palette.getDarkVibrantColor(ContextCompat.getColor(context,
                R.color.colorPrimary));
        final int vibrantColor = palette.getVibrantColor(ContextCompat.getColor(context,
                R.color.colorAccent));

        collapsingToolbarLayout.setStatusBarScrimColor(darkMutedColor);
        collapsingToolbarLayout.setContentScrimColor(darkVibrantColor);
        tabLayout.setBackgroundColor(darkVibrantColor);
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
