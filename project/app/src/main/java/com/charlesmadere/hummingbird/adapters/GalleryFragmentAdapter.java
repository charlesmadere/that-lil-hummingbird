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


    public GalleryFragmentAdapter(final FragmentActivity activity,
            final ArrayList<GalleryImage> galleryImages) {
        this(activity.getSupportFragmentManager(), galleryImages);
    }

    public GalleryFragmentAdapter(final FragmentManager fm,
            final ArrayList<GalleryImage> galleryImages) {
        super(fm);
        mGalleryImages = galleryImages;
    }

    @Override
    public int getCount() {
        return mGalleryImages.size();
    }

    @Override
    public Fragment getItem(final int position) {
        return GalleryFragment.create(mGalleryImages.get(position));
    }

}
