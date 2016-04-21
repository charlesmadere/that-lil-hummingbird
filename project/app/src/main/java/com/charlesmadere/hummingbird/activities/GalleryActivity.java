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

import butterknife.Bind;

public class GalleryActivity extends BaseActivity {

    private static final String TAG = "GalleryActivity";
    private static final String CNAME = GalleryActivity.class.getCanonicalName();
    private static final String EXTRA_GALLERY_IMAGES = CNAME + ".GalleryImages";
    private static final String EXTRA_STARTING_POSITION = CNAME + ".StartingPosition";
    private static final String KEY_CURRENT_POSITION = "CurrentPosition";

    private ArrayList<GalleryImage> mGalleryImages;
    private int mStartingPosition;

    @Bind(R.id.viewPager)
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

        if (savedInstanceState != null && !savedInstanceState.isEmpty()) {
            mStartingPosition = savedInstanceState.getInt(KEY_CURRENT_POSITION, mStartingPosition);
        }

        prepareViewPager();
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_CURRENT_POSITION, mViewPager.getCurrentItem());
    }

    private void prepareViewPager() {
        mViewPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.root_padding));
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(new GalleryFragmentAdapter(this, mGalleryImages));
        mViewPager.setCurrentItem(mStartingPosition, false);

        if (mGalleryImages.size() > 1) {
            updateToolbarTitle();

            mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
                @Override
                public void onPageSelected(final int position) {
                    updateToolbarTitle();
                }
            });
        }
    }

    private void updateToolbarTitle() {
        final ActionBar actionBar = getSupportActionBar();
        final NumberFormat numberFormat = NumberFormat.getInstance();
        actionBar.setTitle(getString(R.string.x_of_y, numberFormat.format(
                mViewPager.getCurrentItem() + 1), numberFormat.format(mGalleryImages.size())));
    }

}
