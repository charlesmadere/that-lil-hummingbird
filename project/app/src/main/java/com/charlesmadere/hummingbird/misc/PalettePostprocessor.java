package com.charlesmadere.hummingbird.misc;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.graphics.Palette;

import com.charlesmadere.hummingbird.R;
import com.facebook.imagepipeline.request.BasePostprocessor;

import java.lang.ref.WeakReference;

public final class PalettePostprocessor extends BasePostprocessor implements
        Palette.PaletteAsyncListener {

    private final WeakReference<Activity> mActivityReference;
    private final WeakReference<TabLayout> mTabLayoutReference;


    public PalettePostprocessor(final Activity activity, final TabLayout tabLayout) {
        mActivityReference = new WeakReference<>(activity);
        mTabLayoutReference = new WeakReference<>(tabLayout);
    }

    private boolean isAlive() {
        final Activity activity = mActivityReference.get();
        return activity != null && !activity.isDestroyed() && mTabLayoutReference.get() != null;
    }

    @Override
    public void onGenerated(final Palette palette) {
        if (!isAlive()) {
            return;
        }

        final TabLayout tabLayout = mTabLayoutReference.get();

        if (tabLayout == null) {
            return;
        }

        final Context context = tabLayout.getContext();
        final int darkMutedColor = palette.getDarkMutedColor(ContextCompat.getColor(context,
                R.color.colorPrimaryDark));
        final int darkVibrantColor = palette.getDarkVibrantColor(ContextCompat.getColor(context,
                R.color.colorPrimary));
        final int vibrantColor = palette.getVibrantColor(ContextCompat.getColor(context,
                R.color.colorAccent));

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
