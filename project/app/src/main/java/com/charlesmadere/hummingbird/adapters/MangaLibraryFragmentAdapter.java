package com.charlesmadere.hummingbird.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.charlesmadere.hummingbird.fragments.MangaLibraryFragment;
import com.charlesmadere.hummingbird.models.ReadingStatus;

public class MangaLibraryFragmentAdapter extends FragmentStatePagerAdapter {

    private static ReadingStatus[] READING_STATUSES = { ReadingStatus.CURRENTLY_READING,
            ReadingStatus.COMPLETED, ReadingStatus.PLAN_TO_READ, ReadingStatus.ON_HOLD,
            ReadingStatus.DROPPED};

    private final boolean mEditableLibrary;
    private final Context mContext;
    private final String mUsername;


    public MangaLibraryFragmentAdapter(final FragmentActivity activity, final String username,
            final boolean editableLibrary) {
        this(activity, activity.getSupportFragmentManager(), username, editableLibrary);
    }

    public MangaLibraryFragmentAdapter(final Context context, final FragmentManager fm,
            final String username, final boolean editableLibrary) {
        super(fm);
        mContext = context;
        mUsername = username;
        mEditableLibrary = editableLibrary;
    }

    @Override
    public int getCount() {
        return READING_STATUSES.length;
    }

    @Override
    public Fragment getItem(final int position) {
        return MangaLibraryFragment.create(READING_STATUSES[position], mUsername, mEditableLibrary);
    }

    @Override
    public CharSequence getPageTitle(final int position) {
        return mContext.getText(READING_STATUSES[position].getTextResId());
    }

}
