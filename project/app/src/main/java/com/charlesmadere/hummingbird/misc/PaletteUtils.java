package com.charlesmadere.hummingbird.misc;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
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
import android.text.TextUtils;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.models.UiColorSet;
import com.charlesmadere.hummingbird.views.ParallaxCoverImage;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.imagepipeline.request.BasePostprocessor;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.lang.ref.WeakReference;

public final class PaletteUtils {

    public static void applyParallaxColors(final String url, final Activity activity,
            final ParallaxCoverImage parallaxCoverImage, final AppBarLayout appBar,
            final CollapsingToolbarLayout collapsingToolbar, final TabLayout tabLayout) {
        applyParallaxColors(url, activity, null, parallaxCoverImage, appBar, collapsingToolbar,
                tabLayout);
    }

    public static void applyParallaxColors(final String url, final Activity activity,
            @Nullable final Listener listener, final ParallaxCoverImage parallaxCoverImage,
            final AppBarLayout appBar, final CollapsingToolbarLayout collapsingToolbar,
            final TabLayout tabLayout) {
        if (TextUtils.isEmpty(url)) {
            throw new IllegalArgumentException("url parameter can't be null / empty");
        }

        if (!parallaxCoverImage.hasDifferentImageThan(url)) {
            return;
        }

        final Uri uri = Uri.parse(url);

        if (MiscUtils.isLowRamDevice()) {
            parallaxCoverImage.setImageURI(uri);
        } else {
            final ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                    .setPostprocessor(new PalettePostprocessor(activity, listener, appBar,
                            collapsingToolbar, tabLayout))
                    .build();

            final AbstractDraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setOldController(parallaxCoverImage.getController())
                    .setImageRequest(request)
                    .build();

            parallaxCoverImage.setController(uri, controller);
        }
    }


    public interface Listener {
        @Nullable
        UiColorSet getUiColorSet();

        void onUiColorsBuilt(final UiColorSet uiColorSet);
    }

    private static final class PalettePostprocessor extends BasePostprocessor implements
            Palette.PaletteAsyncListener {

        private final WeakReference<Activity> mActivity;
        private final WeakReference<AppBarLayout> mAppBar;
        private final WeakReference<CollapsingToolbarLayout> mCollapsingToolbar;
        private final WeakReference<Listener> mListener;
        private final WeakReference<TabLayout> mTabLayout;


        private PalettePostprocessor(final Activity activity, @Nullable final Listener listener,
                final AppBarLayout appBar, final CollapsingToolbarLayout collapsingToolbar,
                final TabLayout tabLayout) {
            mActivity = new WeakReference<>(activity);
            mAppBar = new WeakReference<>(appBar);
            mCollapsingToolbar = new WeakReference<>(collapsingToolbar);
            mListener = new WeakReference<>(listener);
            mTabLayout = new WeakReference<>(tabLayout);
        }

        private void applyColorsWithAnimation(final AppBarLayout appBarLayout,
                final CollapsingToolbarLayout collapsingToolbarLayout, final TabLayout tabLayout,
                @ColorInt final int darkVibrantColor, @ColorInt final int vibrantColor) {
            ArgbEvaluator argbEvaluator = new ArgbEvaluator();

            ValueAnimator appBarAnimator = ValueAnimator.ofObject(argbEvaluator,
                    MiscUtils.getDrawableColor(appBarLayout, appBarLayout.getBackground()),
                    darkVibrantColor);
            appBarAnimator.addUpdateListener(new AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(final ValueAnimator animation) {
                    appBarLayout.setBackgroundColor((int) animation.getAnimatedValue());
                }
            });

            final ColorDrawable statusBarScrim = new ColorDrawable(MiscUtils.getDrawableColor(
                    collapsingToolbarLayout, collapsingToolbarLayout.getStatusBarScrim()));
            collapsingToolbarLayout.setStatusBarScrim(new LayerDrawable(new Drawable[] {
                    statusBarScrim, new ColorDrawable(ContextCompat.getColor(
                    collapsingToolbarLayout.getContext(), R.color.translucent)) } ));

            ValueAnimator collapsingToolbarStatusBarAnimator = ValueAnimator.ofObject(argbEvaluator,
                    statusBarScrim.getColor(), darkVibrantColor);
            collapsingToolbarStatusBarAnimator.addUpdateListener(new AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(final ValueAnimator animation) {
                    statusBarScrim.setColor((int) animation.getAnimatedValue());
                }
            });

            ValueAnimator collapsingToolbarContentAnimator = ValueAnimator.ofObject(argbEvaluator,
                    MiscUtils.getDrawableColor(collapsingToolbarLayout,
                            collapsingToolbarLayout.getContentScrim()), darkVibrantColor);
            collapsingToolbarContentAnimator.addUpdateListener(new AnimatorUpdateListener() {
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
            final Activity a = mActivity.get();
            return a != null && !a.isFinishing() && !a.isDestroyed() && mAppBar.get() != null
                    && mCollapsingToolbar.get() != null && mTabLayout.get() != null;
        }

        @Override
        public void onGenerated(final Palette palette) {
            if (!isAlive()) {
                return;
            }

            final AppBarLayout appBarLayout = mAppBar.get();
            final CollapsingToolbarLayout collapsingToolbarLayout = mCollapsingToolbar.get();
            final TabLayout tabLayout = mTabLayout.get();

            if (appBarLayout == null || collapsingToolbarLayout == null || tabLayout == null) {
                return;
            }

            final Context context = tabLayout.getContext();
            final int darkVibrantColor = palette.getDarkVibrantColor(MiscUtils.getAttrColor(context,
                    R.attr.colorPrimary));
            final int vibrantColor = palette.getVibrantColor(MiscUtils.getAttrColor(context,
                    R.attr.colorAccent));

            final Listener listener = mListener.get();
            if (listener != null) {
                listener.onUiColorsBuilt(new UiColorSet(darkVibrantColor, vibrantColor));
            }

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
