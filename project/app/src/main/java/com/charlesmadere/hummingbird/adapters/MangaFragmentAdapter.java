package com.charlesmadere.hummingbird.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.charlesmadere.hummingbird.models.MangaDigest;

public class MangaFragmentAdapter extends FragmentStatePagerAdapter {

    private final MangaDigest mMangaDigest;
    private final Context mContext;


    public MangaFragmentAdapter(final FragmentActivity activity, final MangaDigest mangaDigest) {
        this(activity, activity.getSupportFragmentManager(), mangaDigest);
    }

    public MangaFragmentAdapter(final Context context, final FragmentManager fm,
            final MangaDigest mangaDigest) {
        super(fm);
        mContext = context;
        mMangaDigest = mangaDigest;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Fragment getItem(final int position) {
        return null;
    }

    @Override
    public CharSequence getPageTitle(final int position) {
        return super.getPageTitle(position);
    }

}
