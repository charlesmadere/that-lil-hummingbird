package com.charlesmadere.hummingbird.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.charlesmadere.hummingbird.models.ReadingStatus;

public class MangaLibraryFragmentAdapter extends FragmentStatePagerAdapter {

    private static ReadingStatus[] READING_STATUSES = { ReadingStatus.CURRENTLY_READING,
            ReadingStatus.COMPLETED, ReadingStatus.PLAN_TO_READ, ReadingStatus.ON_HOLD,
            ReadingStatus.DROPPED};

    private final String mUsername;


    public MangaLibraryFragmentAdapter(final FragmentActivity activity, final String username) {
        this(activity.getSupportFragmentManager(), username);
    }

    public MangaLibraryFragmentAdapter(final FragmentManager fm, final String username) {
        super(fm);
        mUsername = username;
    }

    @Override
    public int getCount() {
        return READING_STATUSES.length;
    }

    @Override
    public Fragment getItem(final int position) {
        // TODO
        return null;
    }

}
