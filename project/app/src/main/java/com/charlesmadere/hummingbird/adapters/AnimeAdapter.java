package com.charlesmadere.hummingbird.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

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
        // TODO
        return 0;
    }

    @Override
    public Fragment getItem(final int position) {
        // TODO
        return null;
    }

    @Override
    public CharSequence getPageTitle(final int position) {
        // TODO
        return super.getPageTitle(position);
    }

}
