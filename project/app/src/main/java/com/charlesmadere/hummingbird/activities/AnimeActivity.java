package com.charlesmadere.hummingbird.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.AnimeAdapter;
import com.charlesmadere.hummingbird.misc.PaletteUtils;
import com.charlesmadere.hummingbird.models.AbsAnime;
import com.charlesmadere.hummingbird.models.AnimeV2;
import com.charlesmadere.hummingbird.models.ErrorInfo;
import com.charlesmadere.hummingbird.networking.Api;
import com.charlesmadere.hummingbird.networking.ApiResponse;
import com.charlesmadere.hummingbird.views.SimpleProgressView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.lang.ref.WeakReference;

import butterknife.Bind;

public class AnimeActivity extends BaseDrawerActivity {

    private static final String CNAME = AnimeActivity.class.getCanonicalName();
    private static final String TAG = "AnimeActivity";
    private static final String EXTRA_ANIME = CNAME + ".Anime";
    private static final String KEY_ANIME_V2 = "AnimeV2";

    private AbsAnime mAnime;
    private AnimeV2 mAnimeV2;

    @Bind(R.id.appBarLayout)
    AppBarLayout mAppBarLayout;

    @Bind(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;

    @Bind(R.id.parallaxCoverImage)
    SimpleDraweeView mCoverImage;

    @Bind(R.id.simpleProgressView)
    SimpleProgressView mSimpleProgressView;

    @Bind(R.id.tabLayout)
    TabLayout mTabLayout;

    @Bind(R.id.viewPager)
    ViewPager mViewPager;


    public static Intent getLaunchIntent(final Context context, final AbsAnime anime) {
        return new Intent(context, AnimeActivity.class)
                .putExtra(EXTRA_ANIME, anime);
    }

    private void fetchAnimeV2() {
        mSimpleProgressView.fadeIn();
        Api.getAnimeById(mAnime, new GetAnimeByIdListener(this));
    }

    @Override
    public String getActivityName() {
        return TAG;
    }

    @Override
    protected boolean isUpNavigationEnabled() {
        return true;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime);

        final Intent intent = getIntent();
        mAnime = intent.getParcelableExtra(EXTRA_ANIME);

        if (savedInstanceState != null && !savedInstanceState.isEmpty()) {
            mAnimeV2 = savedInstanceState.getParcelable(KEY_ANIME_V2);
        }

        if (mAnimeV2 == null) {
            switch (mAnime.getVersion()) {
                case V1:
                    fetchAnimeV2();
                    break;

                case V2:
                    showAnimeV2((AnimeV2) mAnime);
                    break;

                default:
                    throw new RuntimeException("encountered illegal " +
                            AbsAnime.Version.class.getName() + ": " + mAnime.getVersion());
            }
        } else {
            showAnimeV2(mAnimeV2);
        }
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mAnimeV2 != null) {
            outState.putParcelable(KEY_ANIME_V2, mAnimeV2);
        }
    }

    private void showAnimeV2(final AnimeV2 animeV2) {
        mAnimeV2 = animeV2;
        PaletteUtils.applyParallaxColors(mAnimeV2.getCoverImage(), this, mAppBarLayout,
                mCollapsingToolbarLayout, mCoverImage, mTabLayout);
        mViewPager.setAdapter(new AnimeAdapter(this, mAnimeV2));
        mViewPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.root_padding));
        mViewPager.setOffscreenPageLimit(3);
        mTabLayout.setupWithViewPager(mViewPager);
        mSimpleProgressView.fadeOut();
    }

    private void showError() {
        mSimpleProgressView.fadeOut();

        new AlertDialog.Builder(this)
                .setMessage(R.string.error_loading_user)
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(final DialogInterface dialog) {
                        finish();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        finish();
                    }
                })
                .setPositiveButton(R.string.retry, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        fetchAnimeV2();
                    }
                })
                .show();
    }


    private static class GetAnimeByIdListener implements ApiResponse<AnimeV2> {
        private final WeakReference<AnimeActivity> mActivityReference;

        private GetAnimeByIdListener(final AnimeActivity activity) {
            mActivityReference = new WeakReference<>(activity);
        }

        @Override
        public void failure(@Nullable final ErrorInfo error) {
            final AnimeActivity activity = mActivityReference.get();

            if (activity != null && !activity.isDestroyed()) {
                activity.showError();
            }
        }

        @Override
        public void success(@Nullable final AnimeV2 animeV2) {
            final AnimeActivity activity = mActivityReference.get();

            if (activity != null && !activity.isDestroyed()) {
                activity.showAnimeV2(animeV2);
            }
        }
    }

}
