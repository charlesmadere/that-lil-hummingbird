package com.charlesmadere.hummingbird.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.View;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.GalleryFragmentAdapter;
import com.charlesmadere.hummingbird.models.AnimeDigest;
import com.charlesmadere.hummingbird.models.AnimeV2;
import com.charlesmadere.hummingbird.models.GalleryImage;

import java.text.NumberFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnPageChange;

public class GalleryActivity extends BaseActivity {

    private static final String TAG = "GalleryActivity";
    private static final String CNAME = GalleryActivity.class.getCanonicalName();
    private static final String EXTRA_STARTING_POSITION = CNAME + ".StartingPosition";
    private static final String EXTRA_URLS = CNAME + ".Urls";
    private static final String KEY_CURRENT_POSITION = "CurrentPosition";

    private ArrayList<String> mUrls;
    private int mStartingPosition;

    @BindView(R.id.viewPager)
    ViewPager mViewPager;


    public static Intent getLaunchIntent(final Context context, final ArrayList<String> urls) {
        return new Intent(context, GalleryActivity.class)
                .putExtra(EXTRA_STARTING_POSITION, 0)
                .putExtra(EXTRA_URLS, urls);
    }

    public static Intent getLaunchIntent(final Context context, final AnimeDigest.Info info,
            @Nullable final String url) {
        final ArrayList<String> urls = new ArrayList<>();

        if (info.hasCoverImage()) {
            urls.add(info.getCoverImage());
        }

        if (info.hasPosterImage()) {
            urls.add(info.getPosterImage());
        }

        if (info.hasScreencaps()) {
            urls.addAll(info.getScreencaps());
        }

        final int startingPosition = TextUtils.isEmpty(url) ? 0 : urls.indexOf(url);

        return new Intent(context, GalleryActivity.class)
                .putExtra(EXTRA_STARTING_POSITION, startingPosition)
                .putStringArrayListExtra(EXTRA_URLS, urls);
    }

    public static Intent getLaunchIntent(final Context context, final AnimeV2 anime,
            final GalleryImage galleryImage) {
        return getLaunchIntent(context, anime, galleryImage.getOriginal());
    }

    public static Intent getLaunchIntent(final Context context, final AnimeV2 anime,
            @Nullable final String url) {
        final ArrayList<String> urls = new ArrayList<>();

        if (anime.hasCoverImage()) {
            urls.add(anime.getCoverImage());
        }

        if (anime.hasPosterImage()) {
            urls.add(anime.getPosterImage());
        }

        if (anime.hasGalleryImages()) {
            for (final GalleryImage galleryImage : anime.getGalleryImages()) {
                urls.add(galleryImage.getOriginal());
            }
        }

        final int startingPosition = TextUtils.isEmpty(url) ? 0 : urls.indexOf(url);

        return new Intent(context, GalleryActivity.class)
                .putExtra(EXTRA_STARTING_POSITION, startingPosition)
                .putStringArrayListExtra(EXTRA_URLS, urls);
    }

    @Override
    public String getActivityName() {
        return TAG;
    }

    private void hideStatusBar() {
        final View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideStatusBar();
        setContentView(R.layout.activity_gallery);

        mToolbar.setNavigationIcon(R.drawable.ic_close_white_24dp);

        final Intent intent = getIntent();
        mStartingPosition = intent.getIntExtra(EXTRA_STARTING_POSITION, 0);
        mUrls = intent.getStringArrayListExtra(EXTRA_URLS);

        if (savedInstanceState != null && !savedInstanceState.isEmpty()) {
            mStartingPosition = savedInstanceState.getInt(KEY_CURRENT_POSITION, mStartingPosition);
        }

        prepareViewPager();
    }

    @OnPageChange(R.id.viewPager)
    void onViewPagerPageChange() {
        updateToolbarTitle();
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_CURRENT_POSITION, mViewPager.getCurrentItem());
    }

    private void prepareViewPager() {
        mViewPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.root_padding));
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(new GalleryFragmentAdapter(this, mUrls));
        mViewPager.setCurrentItem(mStartingPosition, false);
        updateToolbarTitle();
    }

    private void updateToolbarTitle() {
        final ActionBar actionBar = getSupportActionBar();
        final NumberFormat numberFormat = NumberFormat.getInstance();
        actionBar.setTitle(getString(R.string.x_of_y, numberFormat.format(
                mViewPager.getCurrentItem() + 1), numberFormat.format(mUrls.size())));
    }

}
