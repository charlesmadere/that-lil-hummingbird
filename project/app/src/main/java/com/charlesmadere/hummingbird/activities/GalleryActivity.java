package com.charlesmadere.hummingbird.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.models.GalleryImage;

import java.util.ArrayList;

import butterknife.Bind;

public class GalleryActivity extends BaseActivity {

    private static final String TAG = "GalleryActivity";
    private static final String CNAME = GalleryActivity.class.getCanonicalName();
    private static final String EXTRA_GALLERY_IMAGES = CNAME + ".GalleryImages";
    private static final String EXTRA_STARTING_POSITION = CNAME + ".StartingPosition";

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

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        final Intent intent = getIntent();
        mGalleryImages = intent.getParcelableArrayListExtra(EXTRA_GALLERY_IMAGES);
        mStartingPosition = intent.getIntExtra(EXTRA_STARTING_POSITION, 0);
    }

    @Override
    protected void onViewsBound() {
        super.onViewsBound();
        // TODO
    }

}
