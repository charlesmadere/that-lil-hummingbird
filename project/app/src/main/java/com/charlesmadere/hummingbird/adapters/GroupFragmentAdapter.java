package com.charlesmadere.hummingbird.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.util.SparseArrayCompat;
import android.view.ViewGroup;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.fragments.BaseFragment;
import com.charlesmadere.hummingbird.fragments.GroupDetailsFragment;
import com.charlesmadere.hummingbird.fragments.GroupFeedFragment;

import java.lang.ref.WeakReference;

public class GroupFragmentAdapter extends FragmentStatePagerAdapter {

    public static final int POSITION_FEED = 0;
    public static final int POSITION_DETAILS = 1;

    private final Context mContext;
    private final SparseArrayCompat<WeakReference<BaseFragment>> mFragments;


    public GroupFragmentAdapter(final FragmentActivity activity) {
        this(activity, activity.getSupportFragmentManager());
    }

    public GroupFragmentAdapter(final Context context, final FragmentManager fm) {
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
    public int getCount() {
        return 2;
    }

    @Override
    public Fragment getItem(final int position) {
        switch (position) {
            case POSITION_FEED: return GroupFeedFragment.create();
            case POSITION_DETAILS: return GroupDetailsFragment.create();
            default: throw new IllegalArgumentException("illegal position: " + position);
        }
    }

    @Override
    public CharSequence getPageTitle(final int position) {
        switch (position) {
            case POSITION_FEED: return mContext.getText(R.string.feed);
            case POSITION_DETAILS: return mContext.getText(R.string.details);
            default: throw new IllegalArgumentException("illegal position: " + position);
        }
    }

    @Override
    public BaseFragment instantiateItem(final ViewGroup container, final int position) {
        final BaseFragment fragment = (BaseFragment) super.instantiateItem(container, position);
        mFragments.put(position, new WeakReference<>(fragment));
        return fragment;
    }

}
