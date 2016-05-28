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
import com.charlesmadere.hummingbird.fragments.AnimeGalleryFragment;
import com.charlesmadere.hummingbird.fragments.AnimeQuotesFragment;
import com.charlesmadere.hummingbird.models.AnimeDigest;
import com.charlesmadere.hummingbird.models.AnimeType;

public class AnimeFragmentAdapter extends FragmentStatePagerAdapter {

    private final AnimeDigest mAnimeDigest;
    private final Context mContext;
    private final Impl mImpl;


    public AnimeFragmentAdapter(final FragmentActivity activity, final AnimeDigest animeDigest) {
        this(activity, activity.getSupportFragmentManager(), animeDigest);
    }

    public AnimeFragmentAdapter(final Context context, final FragmentManager fm,
            final AnimeDigest animeDigest) {
        super(fm);
        mContext = context;
        mAnimeDigest = animeDigest;

        if (mAnimeDigest.getInfo().getType() == AnimeType.MOVIE) {
            mImpl = new MovieImpl();
        } else {
            mImpl = new ShowImpl();
        }
    }

    private AnimeCastingsFragment getAnimeCastingsFragment() {
        return AnimeCastingsFragment.create(mAnimeDigest.getCastings());
    }

    private CharSequence getAnimeCastingsTitle() {
        return mContext.getText(R.string.castings);
    }

    private AnimeDetailsFragment getAnimeDetailsFragment() {
        return AnimeDetailsFragment.create(mAnimeDigest);
    }

    private CharSequence getAnimeDetailsTitle() {
        return mContext.getText(R.string.details);
    }

    private AnimeEpisodesFragment getAnimeEpisodesFragment() {
        return AnimeEpisodesFragment.create(mAnimeDigest.getEpisodes());
    }

    private CharSequence getAnimeEpisodesTitle() {
        return mContext.getText(R.string.episodes);
    }

    private AnimeGalleryFragment getAnimeGalleryFragment() {
        return AnimeGalleryFragment.create(mAnimeDigest.getInfo());
    }

    private CharSequence getAnimeGalleryTitle() {
        return mContext.getText(R.string.gallery);
    }

    private AnimeQuotesFragment getAnimeQuotesFragment() {
        return AnimeQuotesFragment.create(mAnimeDigest.getQuotes());
    }

    private CharSequence getAnimeQuotesTitle() {
        return mContext.getText(R.string.quotes);
    }

    @Override
    public int getCount() {
        return mImpl.getCount();
    }

    @Override
    public Fragment getItem(final int position) {
        return mImpl.getItem(position);
    }

    @Override
    public CharSequence getPageTitle(final int position) {
        return mImpl.getPageTitle(position);
    }


    private interface Impl {
        int getCount();
        Fragment getItem(final int position);
        CharSequence getPageTitle(final int position);
    }


    private class MovieImpl implements Impl {
        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public Fragment getItem(final int position) {
            final Fragment fragment;

            switch (position) {
                case 0:
                    fragment = getAnimeDetailsFragment();
                    break;

                case 1:
                    fragment = getAnimeGalleryFragment();
                    break;

                case 2:
                    fragment = getAnimeCastingsFragment();
                    break;

                case 3:
                    fragment = getAnimeQuotesFragment();
                    break;

                default:
                    throw new IllegalArgumentException("illegal position: " + position);
            }

            return fragment;
        }

        @Override
        public CharSequence getPageTitle(final int position) {
            final CharSequence title;

            switch (position) {
                case 0:
                    title = getAnimeDetailsTitle();
                    break;

                case 1:
                    title = getAnimeGalleryTitle();
                    break;

                case 2:
                    title = getAnimeCastingsTitle();
                    break;

                case 3:
                    title = getAnimeQuotesTitle();
                    break;

                default:
                    throw new IllegalArgumentException("illegal position: " + position);
            }

            return title;
        }
    }


    private class ShowImpl implements Impl {
        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public Fragment getItem(final int position) {
            final Fragment fragment;

            switch (position) {
                case 0:
                    fragment = getAnimeDetailsFragment();
                    break;

                case 1:
                    fragment = getAnimeGalleryFragment();
                    break;

                case 2:
                    fragment = getAnimeCastingsFragment();
                    break;

                case 3:
                    fragment = getAnimeQuotesFragment();
                    break;

                case 4:
                    fragment = getAnimeEpisodesFragment();
                    break;

                default:
                    throw new IllegalArgumentException("illegal position: " + position);
            }

            return fragment;
        }

        @Override
        public CharSequence getPageTitle(final int position) {
            final CharSequence title;

            switch (position) {
                case 0:
                    title = getAnimeDetailsTitle();
                    break;

                case 1:
                    title = getAnimeGalleryTitle();
                    break;

                case 2:
                    title = getAnimeCastingsTitle();
                    break;

                case 3:
                    title = getAnimeQuotesTitle();
                    break;

                case 4:
                    title = getAnimeEpisodesTitle();
                    break;

                default:
                    throw new IllegalArgumentException("illegal position: " + position);
            }

            return title;
        }
    }

}
