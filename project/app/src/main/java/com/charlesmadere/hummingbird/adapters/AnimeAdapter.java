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
import com.charlesmadere.hummingbird.models.AnimeV2;

public class AnimeAdapter extends FragmentStatePagerAdapter {

    private final AnimeV2 mAnimeV2;
    private final Context mContext;


    public AnimeAdapter(final FragmentActivity activity, final AnimeV2 animeV2) {
        this(activity, activity.getSupportFragmentManager(), animeV2);
    }

    public AnimeAdapter(final Context context, final FragmentManager fm, final AnimeV2 animeV2) {
        super(fm);
        mContext = context;
        mAnimeV2 = animeV2;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Fragment getItem(final int position) {
        final Fragment fragment;

        switch (position) {
            case 0:
                fragment = AnimeDetailsFragment.create(mAnimeV2);
                break;

            case 1:
                fragment = AnimeEpisodesFragment.create(mAnimeV2.getLinks().getAnimeEpisodes());
                break;

            case 2:
                fragment = AnimeGalleryFragment.create(mAnimeV2.getLinks().getGalleryImages());
                break;

            default:
                throw new RuntimeException("illegal position: " + position);
        }

        return fragment;
    }

    @Override
    public CharSequence getPageTitle(final int position) {
        final int pageTitleResId;

        switch (position) {
            case 0:
                pageTitleResId = R.string.details;
                break;

            case 1:
                pageTitleResId = R.string.episodes;
                break;

            case 2:
                pageTitleResId = R.string.gallery;
                break;

            default:
                throw new RuntimeException("illegal position: " + position);
        }

        return mContext.getText(pageTitleResId);
    }

}
