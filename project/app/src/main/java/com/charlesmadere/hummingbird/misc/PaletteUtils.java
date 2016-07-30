package com.charlesmadere.hummingbird.misc;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.graphics.Palette;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.charlesmadere.hummingbird.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.BasePostprocessor;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.lang.ref.WeakReference;

public final class PaletteUtils {

    public static void applyParallaxColors(final String url, final Activity activity,
            final AppBarLayout appBarLayout, final CollapsingToolbarLayout collapsingToolbarLayout,
            final SimpleDraweeView simpleDraweeView, final TabLayout tabLayout) {
        final Uri uri = Uri.parse(url);

        if (MiscUtils.isLowRamDevice()) {
            simpleDraweeView.setImageURI(uri);
        } else {
            final ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                    .setPostprocessor(new PalettePostprocessor(activity, appBarLayout,
                            collapsingToolbarLayout, tabLayout))
                    .build();

            final AbstractDraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setOldController(simpleDraweeView.getController())
                    .setImageRequest(request)
                    .build();

            simpleDraweeView.setController(controller);
        }
    }

    @ColorInt
    private static int getDrawableColor(final View view, @Nullable Drawable drawable) {
        if (drawable instanceof ColorDrawable) {
            return ((ColorDrawable) drawable).getColor();
        } else {
            return ContextCompat.getColor(view.getContext(), R.color.transparent);
        }
    }


    private static final class PalettePostprocessor extends BasePostprocessor implements
            Palette.PaletteAsyncListener {

        private final WeakReference<Activity> mActivity;
        private final WeakReference<AppBarLayout> mAppBarLayout;
        private final WeakReference<CollapsingToolbarLayout> mCollapsingToolbarLayout;
        private final WeakReference<TabLayout> mTabLayout;


        private PalettePostprocessor(final Activity activity, final AppBarLayout appBarLayout,
                final CollapsingToolbarLayout collapsingToolbarLayout, final TabLayout tabLayout) {
            mActivity = new WeakReference<>(activity);
            mAppBarLayout = new WeakReference<>(appBarLayout);
            mCollapsingToolbarLayout = new WeakReference<>(collapsingToolbarLayout);
            mTabLayout = new WeakReference<>(tabLayout);
        }

        private void applyColorsWithAnimation(final AppBarLayout appBarLayout,
                final CollapsingToolbarLayout collapsingToolbarLayout, final TabLayout tabLayout,
                @ColorInt final int darkVibrantColor, @ColorInt final int vibrantColor) {
            ArgbEvaluator argbEvaluator = new ArgbEvaluator();

            ValueAnimator appBarAnimator = ValueAnimator.ofObject(argbEvaluator,
                    getDrawableColor(appBarLayout, appBarLayout.getBackground()), darkVibrantColor);
            appBarAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(final ValueAnimator animation) {
                    appBarLayout.setBackgroundColor((int) animation.getAnimatedValue());
                }
            });

            int statusBarScrimColor = getDrawableColor(collapsingToolbarLayout,
                    collapsingToolbarLayout.getStatusBarScrim());
            final ColorDrawable statusBarScrim = new ColorDrawable(statusBarScrimColor);
            collapsingToolbarLayout.setStatusBarScrim(new LayerDrawable(new Drawable[] {
                    statusBarScrim, new ColorDrawable(ContextCompat.getColor(
                    collapsingToolbarLayout.getContext(), R.color.translucent)) } ));

            ValueAnimator collapsingToolbarStatusBarAnimator = ValueAnimator.ofObject(argbEvaluator,
                    statusBarScrimColor, darkVibrantColor);
            collapsingToolbarStatusBarAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(final ValueAnimator animation) {
                    statusBarScrim.setColor((int) animation.getAnimatedValue());
                }
            });

            ValueAnimator collapsingToolbarContentAnimator = ValueAnimator.ofObject(argbEvaluator,
                    getDrawableColor(collapsingToolbarLayout, collapsingToolbarLayout.getContentScrim()),
                    darkVibrantColor);
            collapsingToolbarContentAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(final ValueAnimator animation) {
                    collapsingToolbarLayout.setContentScrimColor((int) animation.getAnimatedValue());
                }
            });

            collapsingToolbarContentAnimator.addListener(new SimpleAnimatorListener() {
                @Override
                public void onAnimationStart(final Animator animation) {
                    tabLayout.setSelectedTabIndicatorColor(vibrantColor);
                }
            });

            final AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setDuration(appBarLayout.getResources().getInteger(R.integer.color_duration));
            animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
            animatorSet.playTogether(appBarAnimator, collapsingToolbarStatusBarAnimator,
                    collapsingToolbarContentAnimator);
            animatorSet.start();
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
            final int darkVibrantColor = palette.getDarkVibrantColor(MiscUtils.getAttrColor(context,
                    R.attr.colorPrimary));
            final int vibrantColor = palette.getVibrantColor(MiscUtils.getAttrColor(context,
                    R.attr.colorAccent));

            applyColorsWithAnimation(appBarLayout, collapsingToolbarLayout, tabLayout,
                    darkVibrantColor, vibrantColor);
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
}
