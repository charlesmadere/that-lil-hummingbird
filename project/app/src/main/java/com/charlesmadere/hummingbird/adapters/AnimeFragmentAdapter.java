package com.charlesmadere.hummingbird.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.fragments.AnimeCastingsFragment;
import com.charlesmadere.hummingbird.fragments.AnimeDetailsFragment;
import com.charlesmadere.hummingbird.fragments.AnimeEpisodesFragment;
import com.charlesmadere.hummingbird.fragments.AnimeFranchiseFragment;
import com.charlesmadere.hummingbird.fragments.AnimeGalleryFragment;
import com.charlesmadere.hummingbird.fragments.AnimeQuotesFragment;
import com.charlesmadere.hummingbird.fragments.AnimeReviewsFragment;
import com.charlesmadere.hummingbird.models.AnimeDigest;
import com.charlesmadere.hummingbird.models.AnimeType;

import java.util.ArrayList;

public class AnimeFragmentAdapter extends FragmentStatePagerAdapter {

    private final AnimeDigest mAnimeDigest;
    private final Context mContext;
    private final FragmentPage[] mFragmentPages;


    public AnimeFragmentAdapter(final FragmentActivity activity, final AnimeDigest animeDigest) {
        this(activity, activity.getSupportFragmentManager(), animeDigest);
    }

    public AnimeFragmentAdapter(final Context context, final FragmentManager fm,
            final AnimeDigest animeDigest) {
        super(fm);
        mContext = context;
        mAnimeDigest = animeDigest;

        final ArrayList<FragmentPage> fragmentPages = new ArrayList<>();
        fragmentPages.add(new AnimeDetailsFragmentPage());

        final AnimeDigest.Info info = mAnimeDigest.getInfo();

        if (info.hasScreencaps()) {
            fragmentPages.add(new AnimeGalleryFragmentPage());
        }

        if (mAnimeDigest.hasReviews()) {
            fragmentPages.add(new AnimeReviewsFragmentPage());
        }

        if (mAnimeDigest.hasCastings()) {
            fragmentPages.add(new AnimeCastingsFragmentPage());
        }

        if (mAnimeDigest.hasQuotes()) {
            fragmentPages.add(new AnimeQuotesFragmentPage());
        }

        if (mAnimeDigest.hasEpisodes() && info.getType() != AnimeType.MOVIE) {
            fragmentPages.add(new AnimeEpisodesFragmentPage());
        }

        if (info.hasFranchiseId()) {
            fragmentPages.add(new AnimeFranchiseFragmentPage());
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


    private class AnimeCastingsFragmentPage implements FragmentPage {
        @Override
        public Fragment getItem() {
            return AnimeCastingsFragment.create();
        }

        @Override
        public CharSequence getPageTitle() {
            return mContext.getText(R.string.casting);
        }
    }

    private class AnimeDetailsFragmentPage implements FragmentPage {
        @Override
        public Fragment getItem() {
            return AnimeDetailsFragment.create();
        }

        @Override
        public CharSequence getPageTitle() {
            return mContext.getText(R.string.details);
        }
    }

    private class AnimeEpisodesFragmentPage implements FragmentPage {
        @Override
        public Fragment getItem() {
            return AnimeEpisodesFragment.create();
        }

        @Override
        public CharSequence getPageTitle() {
            return mContext.getText(R.string.episodes);
        }
    }

    private class AnimeFranchiseFragmentPage implements FragmentPage {
        @Override
        public Fragment getItem() {
            return AnimeFranchiseFragment.create();
        }

        @Override
        public CharSequence getPageTitle() {
            return mContext.getText(R.string.franchise);
        }
    }

    private class AnimeGalleryFragmentPage implements FragmentPage {
        @Override
        public Fragment getItem() {
            return AnimeGalleryFragment.create();
        }

        @Override
        public CharSequence getPageTitle() {
            return mContext.getText(R.string.gallery);
        }
    }

    private class AnimeQuotesFragmentPage implements FragmentPage {
        @Override
        public Fragment getItem() {
            return AnimeQuotesFragment.create();
        }

        @Override
        public CharSequence getPageTitle() {
            return mContext.getText(R.string.quotes);
        }
    }

    private class AnimeReviewsFragmentPage implements FragmentPage {
        @Override
        public Fragment getItem() {
            return AnimeReviewsFragment.create();
        }

        @Override
        public CharSequence getPageTitle() {
            return mContext.getText(R.string.reviews);
        }
    }

}
