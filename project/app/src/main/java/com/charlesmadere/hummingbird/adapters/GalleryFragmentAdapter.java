package com.charlesmadere.hummingbird.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.charlesmadere.hummingbird.fragments.GalleryFragment;
import com.charlesmadere.hummingbird.models.GalleryImage;

import java.util.ArrayList;

public class GalleryFragmentAdapter extends FragmentStatePagerAdapter {

    private final ArrayList<GalleryImage> mGalleryImages;
    private final String mUrl;


    public GalleryFragmentAdapter(final FragmentActivity activity,
            final ArrayList<GalleryImage> galleryImages) {
        this(activity.getSupportFragmentManager(), galleryImages);
    }

    public GalleryFragmentAdapter(final FragmentManager fm,
            final ArrayList<GalleryImage> galleryImages) {
        super(fm);
        mGalleryImages = galleryImages;
        mUrl = null;
    }

    public GalleryFragmentAdapter(final FragmentActivity activity, final String url) {
        this(activity.getSupportFragmentManager(), url);
    }

    public GalleryFragmentAdapter(final FragmentManager fm, final String url) {
        super(fm);
        mGalleryImages = null;
        mUrl = url;
    }

    @Override
    public int getCount() {
        if (mGalleryImages == null) {
            return 1;
        } else {
            return mGalleryImages.size();
        }
    }

    @Override
    public Fragment getItem(final int position) {
        if (mGalleryImages == null) {
            return GalleryFragment.create(mUrl);
        } else {
            return GalleryFragment.create(mGalleryImages.get(position));
        }
    }

}
