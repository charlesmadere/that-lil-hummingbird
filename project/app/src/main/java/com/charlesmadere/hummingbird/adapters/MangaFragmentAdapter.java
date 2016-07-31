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

import java.util.ArrayList;

public class MangaFragmentAdapter extends FragmentStatePagerAdapter {

    private final Context mContext;
    private final FragmentPage[] mFragmentPages;
    private final MangaDigest mMangaDigest;


    public MangaFragmentAdapter(final FragmentActivity activity, final MangaDigest mangaDigest) {
        this(activity, activity.getSupportFragmentManager(), mangaDigest);
    }

    public MangaFragmentAdapter(final Context context, final FragmentManager fm,
            final MangaDigest mangaDigest) {
        super(fm);
        mContext = context;
        mMangaDigest = mangaDigest;

        final ArrayList<FragmentPage> fragmentPages = new ArrayList<>();
        fragmentPages.add(new MangaDetailsFragmentPage());

        if (mMangaDigest.hasCharacters()) {
            fragmentPages.add(new MangaCharactersFragmentPage());
        }

        mFragmentPages = new FragmentPage[fragmentPages.size()];
        fragmentPages.toArray(mFragmentPages);
    }

    @Override
    public int getCount() {
        return mFragmentPages.length;
    }

    @Override
    public Fragment getItem(final int position) {
        return mFragmentPages[position].getItem();
    }

    @Override
    public CharSequence getPageTitle(final int position) {
        return mFragmentPages[position].getPageTitle();
    }


    private class MangaCharactersFragmentPage implements FragmentPage {
        @Override
        public Fragment getItem() {
            return MangaCharactersFragment.create();
        }

        @Override
        public CharSequence getPageTitle() {
            return mContext.getText(R.string.characters);
        }
    }

    private class MangaDetailsFragmentPage implements FragmentPage {
        @Override
        public Fragment getItem() {
            return MangaDetailsFragment.create();
        }

        @Override
        public CharSequence getPageTitle() {
            return mContext.getText(R.string.details);
        }
    }

}
