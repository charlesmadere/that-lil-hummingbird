package com.charlesmadere.hummingbird.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.fragments.AnimeDetailsFragment;
import com.charlesmadere.hummingbird.fragments.AnimeEpisodesFragment;
import com.charlesmadere.hummingbird.fragments.AnimeGalleryFragment;
import com.charlesmadere.hummingbird.models.AbsAnime;
import com.charlesmadere.hummingbird.models.AnimeV2;

public class AnimeFragmentAdapter extends FragmentStatePagerAdapter {

    private final AnimeV2 mAnime;
    private final Context mContext;
    private final Impl mImpl;


    public AnimeFragmentAdapter(final FragmentActivity activity, final AnimeV2 anime) {
        this(activity, activity.getSupportFragmentManager(), anime);
    }

    public AnimeFragmentAdapter(final Context context, final FragmentManager fm, final AnimeV2 anime) {
        super(fm);
        mContext = context;
        mAnime = anime;

        if (mAnime.getShowType() == AbsAnime.ShowType.MOVIE) {
            mImpl = new MovieImpl();
        } else {
            mImpl = new ShowImpl();
        }
    }

    private AnimeDetailsFragment getAnimeDetailsFragment() {
        return AnimeDetailsFragment.create(mAnime);
    }

    private CharSequence getAnimeDetailsTitle() {
        return mContext.getString(R.string.details);
    }

    private AnimeEpisodesFragment getAnimeEpisodesFragment() {
        return AnimeEpisodesFragment.create(mAnime.getAnimeEpisodes());
    }

    private CharSequence getAnimeEpisodesTitle() {
        return mContext.getString(R.string.episodes);
    }

    private AnimeGalleryFragment getAnimeGalleryFragment() {
        return AnimeGalleryFragment.create(mAnime, mAnime.getGalleryImages());
    }

    private CharSequence getAnimeGalleryTitle() {
        return mContext.getString(R.string.gallery);
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
            return 2;
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

                default:
                    throw new RuntimeException("illegal position: " + position);
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

                default:
                    throw new RuntimeException("illegal position: " + position);
            }

            return title;
        }
    }

    private class ShowImpl implements Impl {
        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public Fragment getItem(final int position) {
            final Fragment fragment;

            switch (position) {
                case 0:
                    fragment = getAnimeDetailsFragment();
                    break;

                case 1:
                    fragment = getAnimeEpisodesFragment();
                    break;

                case 2:
                    fragment = getAnimeGalleryFragment();
                    break;

                default:
                    throw new RuntimeException("illegal position: " + position);
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
                    title = getAnimeEpisodesTitle();
                    break;

                case 2:
                    title = getAnimeGalleryTitle();
                    break;

                default:
                    throw new RuntimeException("illegal position: " + position);
            }

            return title;
        }
    }

}
