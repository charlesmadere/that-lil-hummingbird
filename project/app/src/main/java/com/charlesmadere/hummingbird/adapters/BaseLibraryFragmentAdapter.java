package com.charlesmadere.hummingbird.adapters;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.util.SparseArrayCompat;
import android.view.ViewGroup;

import com.charlesmadere.hummingbird.fragments.BaseLibraryFragment;

import java.lang.ref.WeakReference;

public abstract class BaseLibraryFragmentAdapter extends FragmentStatePagerAdapter {

    protected final Context mContext;
    protected final SparseArrayCompat<WeakReference<BaseLibraryFragment>> mFragments;


    public BaseLibraryFragmentAdapter(final FragmentActivity activity) {
        this(activity, activity.getSupportFragmentManager());
    }

    public BaseLibraryFragmentAdapter(final Context context, final FragmentManager fm) {
        super(fm);
        mContext = context;
        mFragments = new SparseArrayCompat<>(getCount());
    }
    @Override
    public void destroyItem(final ViewGroup container, final int position, final Object object) {
        super.destroyItem(container, position, object);
        mFragments.removeAt(position);
    }

    @Override
    public abstract BaseLibraryFragment getItem(final int position);

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        final BaseLibraryFragment fragment =
                (BaseLibraryFragment) super.instantiateItem(container, position);
        mFragments.put(position, new WeakReference<>(fragment));
        return fragment;
    }

    public void updateLibrarySort() {
        for (int i = 0; i < mFragments.size(); ++i) {
            final WeakReference<BaseLibraryFragment> fragmentReference = mFragments.get(i);

            if (fragmentReference != null) {
                final BaseLibraryFragment fragment = fragmentReference.get();

                if (fragment != null && fragment.isAlive()) {
                    fragment.updateLibrarySort();
                }
            }
        }
    }

}
