package com.charlesmadere.hummingbird.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.charlesmadere.hummingbird.fragments.GalleryFragment;

import java.util.ArrayList;

public class GalleryFragmentAdapter extends FragmentStatePagerAdapter {

    private final ArrayList<String> mUrls;


    public GalleryFragmentAdapter(final FragmentActivity activity, final ArrayList<String> urls) {
        this(activity.getSupportFragmentManager(), urls);
    }

    public GalleryFragmentAdapter(final FragmentManager fm, final ArrayList<String> urls) {
        super(fm);
        mUrls = urls;
    }

    @Override
    public int getCount() {
        return mUrls.size();
    }

    @Override
    public Fragment getItem(final int position) {
        return GalleryFragment.create(mUrls.get(position));
    }

}
