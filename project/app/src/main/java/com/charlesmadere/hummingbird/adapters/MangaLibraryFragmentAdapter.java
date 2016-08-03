package com.charlesmadere.hummingbird.adapters;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.charlesmadere.hummingbird.fragments.MangaLibraryFragment;
import com.charlesmadere.hummingbird.models.ReadingStatus;

public class MangaLibraryFragmentAdapter extends BaseLibraryFragmentAdapter {

    private static ReadingStatus[] READING_STATUSES = { ReadingStatus.CURRENTLY_READING,
            ReadingStatus.COMPLETED, ReadingStatus.PLAN_TO_READ, ReadingStatus.ON_HOLD,
            ReadingStatus.DROPPED};


    public MangaLibraryFragmentAdapter(final FragmentActivity activity, final String username,
            final boolean editableLibrary) {
        super(activity, activity.getSupportFragmentManager(), username, editableLibrary);
    }

    public MangaLibraryFragmentAdapter(final Context context, final FragmentManager fm,
            final String username, final boolean editableLibrary) {
        super(context, fm, username, editableLibrary);
    }

    @Override
    public int getCount() {
        return READING_STATUSES.length;
    }

    @Override
    public MangaLibraryFragment getItem(final int position) {
        return MangaLibraryFragment.create(READING_STATUSES[position], mUsername,
                mEditableLibrary);
    }

    @Override
    public CharSequence getPageTitle(final int position) {
        return mContext.getText(READING_STATUSES[position].getTextResId());
    }

}
