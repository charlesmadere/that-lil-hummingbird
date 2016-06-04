package com.charlesmadere.hummingbird.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.fragments.MangaDetailsFragment;
import com.charlesmadere.hummingbird.fragments.MangaFeedFragment;
import com.charlesmadere.hummingbird.models.MangaDigest;

public class MangaFragmentAdapter extends FragmentStatePagerAdapter {

    public static final int POSITION_DETAILS = 0;
    public static final int POSITION_FEED = 1;

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
        return 2;
    }

    @Override
    public Fragment getItem(final int position) {
        switch (position) {
            case POSITION_DETAILS: return MangaDetailsFragment.create(mMangaDigest);
            case POSITION_FEED: return MangaFeedFragment.create(mMangaDigest.getId());
            default: throw new IllegalArgumentException("illegal position: " + position);
        }
    }

    @Override
    public CharSequence getPageTitle(final int position) {
        switch (position) {
            case POSITION_DETAILS: return mContext.getText(R.string.details);
            case POSITION_FEED: return mContext.getText(R.string.feed);
            default: throw new IllegalArgumentException("illegal position: " + position);
        }
    }

}
