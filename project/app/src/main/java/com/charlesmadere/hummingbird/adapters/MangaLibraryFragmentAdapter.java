package com.charlesmadere.hummingbird.adapters;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.util.SparseArrayCompat;
import android.view.ViewGroup;

import com.charlesmadere.hummingbird.fragments.MangaLibraryFragment;
import com.charlesmadere.hummingbird.models.ReadingStatus;

import java.lang.ref.WeakReference;

public class MangaLibraryFragmentAdapter extends FragmentStatePagerAdapter {

    private static ReadingStatus[] READING_STATUSES = { ReadingStatus.CURRENTLY_READING,
            ReadingStatus.COMPLETED, ReadingStatus.PLAN_TO_READ, ReadingStatus.ON_HOLD,
            ReadingStatus.DROPPED};

    private final boolean mEditableLibrary;
    private final Context mContext;
    private final SparseArrayCompat<WeakReference<MangaLibraryFragment>> mFragments;
    private final String mUsername;


    public MangaLibraryFragmentAdapter(final FragmentActivity activity, final String username,
            final boolean editableLibrary) {
        this(activity, activity.getSupportFragmentManager(), username, editableLibrary);
    }

    public MangaLibraryFragmentAdapter(final Context context, final FragmentManager fm,
            final String username, final boolean editableLibrary) {
        super(fm);
        mEditableLibrary = editableLibrary;
        mContext = context;
        mUsername = username;
        mFragments = new SparseArrayCompat<>(getCount());
    }

    @Override
    public void destroyItem(final ViewGroup container, final int position, final Object object) {
        super.destroyItem(container, position, object);
        mFragments.removeAt(position);
    }

    @Override
    public int getCount() {
        return READING_STATUSES.length;
    }

    @Override
    public MangaLibraryFragment getItem(final int position) {
        return MangaLibraryFragment.create(READING_STATUSES[position], mUsername, mEditableLibrary);
    }

    @Override
    public CharSequence getPageTitle(final int position) {
        return mContext.getText(READING_STATUSES[position].getTextResId());
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        final MangaLibraryFragment fragment = (MangaLibraryFragment) super.instantiateItem(container, position);
        mFragments.put(position, new WeakReference<>(fragment));
        return fragment;
    }

    public void updateLibrarySort() {
        for (int i = 0; i < mFragments.size(); ++i) {
            final WeakReference<MangaLibraryFragment> fragmentReference = mFragments.get(i);

            if (fragmentReference != null) {
                final MangaLibraryFragment fragment = fragmentReference.get();

                if (fragment != null && !fragment.isDestroyed()) {
                    fragment.updateLibrarySort();
                }
            }
        }
    }

}
