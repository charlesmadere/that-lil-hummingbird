package com.charlesmadere.hummingbird.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.View;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.GalleryFragmentAdapter;
import com.charlesmadere.hummingbird.models.GalleryImage;

import java.text.NumberFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnPageChange;

public class GalleryActivity extends BaseActivity {

    private static final String TAG = "GalleryActivity";
    private static final String CNAME = GalleryActivity.class.getCanonicalName();
    private static final String EXTRA_GALLERY_IMAGES = CNAME + ".GalleryImages";
    private static final String EXTRA_STARTING_POSITION = CNAME + ".StartingPosition";
    private static final String EXTRA_URL = CNAME + ".Url";
    private static final String KEY_CURRENT_POSITION = "CurrentPosition";

    private ArrayList<GalleryImage> mGalleryImages;
    private int mStartingPosition;
    private String mUrl;

    @BindView(R.id.viewPager)
    ViewPager mViewPager;


    public static Intent getLaunchIntent(final Context context,
            final ArrayList<GalleryImage> galleryImages) {
        return getLaunchIntent(context, galleryImages, 0);
    }

    public static Intent getLaunchIntent(final Context context,
            final ArrayList<GalleryImage> galleryImages, final int startingPosition) {
        return new Intent(context, GalleryActivity.class)
                .putExtra(EXTRA_GALLERY_IMAGES, galleryImages)
                .putExtra(EXTRA_STARTING_POSITION, startingPosition);
    }

    public static Intent getLaunchIntent(final Context context, final String url) {
        return new Intent(context, GalleryActivity.class)
                .putExtra(EXTRA_URL, url);
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
        mGalleryImages = intent.getParcelableArrayListExtra(EXTRA_GALLERY_IMAGES);
        mStartingPosition = intent.getIntExtra(EXTRA_STARTING_POSITION, 0);
        mUrl = intent.getStringExtra(EXTRA_URL);

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

        if (mGalleryImages == null) {
            mViewPager.setAdapter(new GalleryFragmentAdapter(this, mUrl));
        } else {
            mViewPager.setAdapter(new GalleryFragmentAdapter(this, mGalleryImages));
            mViewPager.setCurrentItem(mStartingPosition, false);
        }

        updateToolbarTitle();
    }

    private void updateToolbarTitle() {
        final ActionBar actionBar = getSupportActionBar();
        final NumberFormat numberFormat = NumberFormat.getInstance();
        final int size = mGalleryImages == null ? 1 : mGalleryImages.size();
        actionBar.setTitle(getString(R.string.x_of_y, numberFormat.format(
                mViewPager.getCurrentItem() + 1), numberFormat.format(size)));
    }

}
