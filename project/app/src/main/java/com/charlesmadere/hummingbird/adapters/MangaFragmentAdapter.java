package com.charlesmadere.hummingbird.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.fragments.MangaCharactersFragment;
import com.charlesmadere.hummingbird.fragments.MangaDetailsFragment;
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
        return 2;
    }

    @Override
    public Fragment getItem(final int position) {
        switch (position) {
            case 0: return MangaDetailsFragment.create(mMangaDigest);
            case 1: return MangaCharactersFragment.create(mMangaDigest.getCharacters());
            default: throw new IllegalArgumentException("illegal position: " + position);
        }
    }

    @Override
    public CharSequence getPageTitle(final int position) {
        switch (position) {
            case 0: return mContext.getText(R.string.details);
            case 1: return mContext.getText(R.string.characters);
            default: throw new IllegalArgumentException("illegal position: " + position);
        }
    }

}
