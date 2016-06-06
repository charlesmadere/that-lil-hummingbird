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
import com.charlesmadere.hummingbird.adapters.MangaFragmentAdapter;
import com.charlesmadere.hummingbird.misc.PaletteUtils;
import com.charlesmadere.hummingbird.models.ErrorInfo;
import com.charlesmadere.hummingbird.models.MangaDigest;
import com.charlesmadere.hummingbird.networking.Api;
import com.charlesmadere.hummingbird.networking.ApiResponse;
import com.charlesmadere.hummingbird.views.SimpleProgressView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.lang.ref.WeakReference;

import butterknife.BindView;

public class MangaActivity extends BaseDrawerActivity {

    private static final String TAG = "MangaActivity";
    private static final String CNAME = MangaActivity.class.getCanonicalName();
    private static final String EXTRA_MANGA_ID = CNAME + ".MangaId";
    private static final String EXTRA_MANGA_NAME = CNAME + ".MangaName";
    private static final String KEY_MANGA_DIGEST = "MangaDigest";
    private static final String KEY_STARTING_POSITION = "StartingPosition";

    private int mStartingPosition;
    private MangaDigest mMangaDigest;
    private String mMangaId;

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


    public static Intent getLaunchIntent(final Context context, final String mangaId,
            final String mangaName) {
        return new Intent(context, MangaActivity.class)
                .putExtra(EXTRA_MANGA_ID, mangaId)
                .putExtra(EXTRA_MANGA_NAME, mangaName);
    }

    private void fetchMangaDigest() {
        mSimpleProgressView.fadeIn();
        Api.getMangaDigest(mMangaId, new GetMangaDigestListener(this));
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
        setContentView(R.layout.activity_manga);

        final Intent intent = getIntent();
        setTitle(intent.getStringExtra(EXTRA_MANGA_NAME));
        mMangaId = intent.getStringExtra(EXTRA_MANGA_ID);

        mStartingPosition = MangaFragmentAdapter.POSITION_FEED;

        if (savedInstanceState != null && !savedInstanceState.isEmpty()) {
            mStartingPosition = savedInstanceState.getInt(KEY_STARTING_POSITION, mStartingPosition);
            mMangaDigest = savedInstanceState.getParcelable(KEY_MANGA_DIGEST);
        }

        if (mMangaDigest == null) {
            fetchMangaDigest();
        } else {
            showMangaDigest(mMangaDigest);
        }
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_STARTING_POSITION, mViewPager.getCurrentItem());

        if (mMangaDigest != null) {
            outState.putParcelable(KEY_MANGA_DIGEST, mMangaDigest);
        }
    }

    private void showError() {
        mSimpleProgressView.fadeOut();

        new AlertDialog.Builder(this)
                .setMessage(R.string.error_loading_manga)
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
                        fetchMangaDigest();
                    }
                })
                .show();
    }

    private void showMangaDigest(final MangaDigest mangaDigest) {
        mMangaDigest = mangaDigest;

        if (mangaDigest.getManga().hasCoverImage()) {
            PaletteUtils.applyParallaxColors(mangaDigest.getManga().getCoverImage(), this,
                    mAppBarLayout, mCollapsingToolbarLayout, mCoverImage, mTabLayout);
        }

        mViewPager.setAdapter(new MangaFragmentAdapter(this, mMangaDigest));
        mViewPager.setCurrentItem(mStartingPosition, false);
        mViewPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.root_padding));
        mViewPager.setOffscreenPageLimit(3);
        mTabLayout.setupWithViewPager(mViewPager);

        mSimpleProgressView.fadeOut();
    }


    private static class GetMangaDigestListener implements ApiResponse<MangaDigest> {
        private final WeakReference<MangaActivity> mActivityReference;

        private GetMangaDigestListener(final MangaActivity activity) {
            mActivityReference = new WeakReference<>(activity);
        }

        @Override
        public void failure(@Nullable final ErrorInfo error) {
            final MangaActivity activity = mActivityReference.get();

            if (activity != null && !activity.isDestroyed()) {
                activity.showError();
            }
        }

        @Override
        public void success(final MangaDigest mangaDigest) {
            final MangaActivity activity = mActivityReference.get();

            if (activity != null && !activity.isDestroyed()) {
                activity.showMangaDigest(mangaDigest);
            }
        }
    }

}
