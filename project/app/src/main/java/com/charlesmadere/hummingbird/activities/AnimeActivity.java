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
import com.charlesmadere.hummingbird.adapters.AnimeFragmentAdapter;
import com.charlesmadere.hummingbird.misc.PaletteUtils;
import com.charlesmadere.hummingbird.models.AbsAnime;
import com.charlesmadere.hummingbird.models.AnimeV2;
import com.charlesmadere.hummingbird.models.ErrorInfo;
import com.charlesmadere.hummingbird.networking.Api;
import com.charlesmadere.hummingbird.networking.ApiResponse;
import com.charlesmadere.hummingbird.views.SimpleProgressView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.lang.ref.WeakReference;

import butterknife.BindView;

public class AnimeActivity extends BaseDrawerActivity {

    private static final String CNAME = AnimeActivity.class.getCanonicalName();
    private static final String TAG = "AnimeActivity";
    private static final String EXTRA_ABS_ANIME = CNAME + ".AbsAnime";
    private static final String EXTRA_ANIME_ID = CNAME + ".AnimeId";
    private static final String EXTRA_ANIME_NAME = CNAME + ".AnimeName";
    private static final String KEY_ANIME_V2 = "AnimeV2";

    private AbsAnime mAbsAnime;
    private AnimeV2 mAnimeV2;
    private String mAnimeId;

    @BindView(R.id.appBarLayout)
    AppBarLayout mAppBarLayout;

    @BindView(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;

    @BindView(R.id.parallaxCoverImage)
    SimpleDraweeView mCoverImage;

    @BindView(R.id.simpleProgressView)
    SimpleProgressView mSimpleProgressView;

    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;

    @BindView(R.id.viewPager)
    ViewPager mViewPager;


    public static Intent getLaunchIntent(final Context context, final AbsAnime anime) {
        return new Intent(context, AnimeActivity.class)
                .putExtra(EXTRA_ABS_ANIME, anime)
                .putExtra(EXTRA_ANIME_NAME, anime.getTitle());
    }

    public static Intent getLaunchIntent(final Context context, final String animeId,
            final String animeName) {
        return new Intent(context, AnimeActivity.class)
                .putExtra(EXTRA_ANIME_ID, animeId)
                .putExtra(EXTRA_ANIME_NAME, animeName);
    }

    private void fetchAnimeV2() {
        mSimpleProgressView.fadeIn();
        final String animeId = mAbsAnime == null ? mAnimeId : mAbsAnime.getId();
        Api.getAnimeById(animeId, new GetAnimeByIdListener(this));
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
        setTitle(intent.getStringExtra(EXTRA_ANIME_NAME));
        mAbsAnime = intent.getParcelableExtra(EXTRA_ABS_ANIME);
        mAnimeId = intent.getStringExtra(EXTRA_ANIME_ID);

        if (savedInstanceState != null && !savedInstanceState.isEmpty()) {
            mAnimeV2 = savedInstanceState.getParcelable(KEY_ANIME_V2);
        }

        if (mAnimeV2 == null) {
            if (mAbsAnime != null && mAbsAnime.getVersion() == AbsAnime.Version.V2) {
                showAnimeV2((AnimeV2) mAbsAnime);
            } else {
                fetchAnimeV2();
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
        mViewPager.setAdapter(new AnimeFragmentAdapter(this, mAnimeV2));
        mViewPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.root_padding));
        mViewPager.setOffscreenPageLimit(3);
        mTabLayout.setupWithViewPager(mViewPager);
        mSimpleProgressView.fadeOut();
    }

    private void showError() {
        mSimpleProgressView.fadeOut();

        new AlertDialog.Builder(this)
                .setMessage(R.string.error_loading_anime)
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
        public void success(final AnimeV2 animeV2) {
            final AnimeActivity activity = mActivityReference.get();

            if (activity != null && !activity.isDestroyed()) {
                activity.showAnimeV2(animeV2);
            }
        }
    }

}
