package com.charlesmadere.hummingbird.adapters;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.charlesmadere.hummingbird.fragments.AnimeLibraryFragment;
import com.charlesmadere.hummingbird.models.WatchingStatus;

public class AnimeLibraryFragmentAdapter extends BaseLibraryFragmentAdapter {

    private static final WatchingStatus[] WATCHING_STATUSES = { WatchingStatus.CURRENTLY_WATCHING,
            WatchingStatus.COMPLETED, WatchingStatus.PLAN_TO_WATCH, WatchingStatus.ON_HOLD,
            WatchingStatus.DROPPED };


    public AnimeLibraryFragmentAdapter(final FragmentActivity activity) {
        super(activity, activity.getSupportFragmentManager());
    }

    public AnimeLibraryFragmentAdapter(final Context context, final FragmentManager fm) {
        super(context, fm);
    }

    @Override
    public int getCount() {
        return WATCHING_STATUSES.length;
    }

    @Override
    public AnimeLibraryFragment getItem(final int position) {
        return AnimeLibraryFragment.create(WATCHING_STATUSES[position]);
    }

    @Override
    public CharSequence getPageTitle(final int position) {
        return mContext.getText(WATCHING_STATUSES[position].getTextResId());
    }

}
