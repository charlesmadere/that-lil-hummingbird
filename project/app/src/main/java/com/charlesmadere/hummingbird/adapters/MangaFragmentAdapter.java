package com.charlesmadere.hummingbird.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.util.SparseArrayCompat;
import android.view.ViewGroup;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.fragments.BaseMangaFragment;
import com.charlesmadere.hummingbird.fragments.MangaCharactersFragment;
import com.charlesmadere.hummingbird.fragments.MangaDetailsFragment;
import com.charlesmadere.hummingbird.models.MangaDigest;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class MangaFragmentAdapter extends FragmentStatePagerAdapter {

    private final Context mContext;
    private final FragmentPage[] mFragmentPages;
    private final SparseArrayCompat<WeakReference<BaseMangaFragment>> mFragments;


    public MangaFragmentAdapter(final FragmentActivity activity, final MangaDigest mangaDigest) {
        this(activity, activity.getSupportFragmentManager(), mangaDigest);
    }

    public MangaFragmentAdapter(final Context context, final FragmentManager fm,
            final MangaDigest mangaDigest) {
        super(fm);
        mContext = context;

        final ArrayList<FragmentPage> fragmentPages = new ArrayList<>();
        fragmentPages.add(new MangaDetailsFragmentPage());

        if (mangaDigest.hasCharacters()) {
            fragmentPages.add(new MangaCharactersFragmentPage());
        }

        mFragmentPages = new FragmentPage[fragmentPages.size()];
        fragmentPages.toArray(mFragmentPages);

        mFragments = new SparseArrayCompat<>(getCount());
    }

    @Override
    public void destroyItem(final ViewGroup container, final int position, final Object object) {
        super.destroyItem(container, position, object);
        mFragments.removeAt(position);
    }

    @Override
    public int getCount() {
        return mFragmentPages.length;
    }

    @Override
    public BaseMangaFragment getItem(final int position) {
        final BaseMangaFragment fragment = (BaseMangaFragment) mFragmentPages[position].getItem();
        mFragments.put(position, new WeakReference<>(fragment));
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(final int position) {
        return mFragmentPages[position].getPageTitle();
    }

    @Override
    public BaseMangaFragment instantiateItem(final ViewGroup container, final int position) {
        final BaseMangaFragment fragment = (BaseMangaFragment) super.instantiateItem(container,
                position);
        mFragments.put(position, new WeakReference<>(fragment));
        return fragment;
    }

    public void showMangaDigest() {
        for (int i = 0; i < mFragments.size(); ++i) {
            final WeakReference<BaseMangaFragment> fragmentReference = mFragments.get(i);

            if (fragmentReference != null) {
                final BaseMangaFragment fragment = fragmentReference.get();

                if (fragment != null && fragment.isAlive()) {
                    fragment.showMangaDigest();
                }
            }
        }
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
