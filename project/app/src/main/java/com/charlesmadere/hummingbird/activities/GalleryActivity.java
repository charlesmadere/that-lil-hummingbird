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

    private ArrayList<GalleryImage> mGalleryImages;

    @Bind(R.id.viewPager)
    ViewPager mViewPager;


    public static Intent getLaunchIntent(final Context context,
            final ArrayList<GalleryImage> galleryImages) {
        return new Intent(context, GalleryActivity.class)
                .putExtra(EXTRA_GALLERY_IMAGES, galleryImages);
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
    }

}
