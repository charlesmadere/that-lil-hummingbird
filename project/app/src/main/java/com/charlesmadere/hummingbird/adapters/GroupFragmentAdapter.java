package com.charlesmadere.hummingbird.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.fragments.GroupDetailsFragment;
import com.charlesmadere.hummingbird.fragments.GroupFeedFragment;

public class GroupFragmentAdapter extends FragmentStatePagerAdapter {

    private final Context mContext;


    public GroupFragmentAdapter(final FragmentActivity activity) {
        this(activity, activity.getSupportFragmentManager());
    }

    public GroupFragmentAdapter(final Context context, final FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Fragment getItem(final int position) {
        switch (position) {
            case 0: return GroupFeedFragment.create();
            case 1: return GroupDetailsFragment.create();
            default: throw new IllegalArgumentException("illegal position: " + position);
        }
    }

    @Override
    public CharSequence getPageTitle(final int position) {
        switch (position) {
            case 0: return mContext.getText(R.string.feed);
            case 1: return mContext.getText(R.string.details);
            default: throw new IllegalArgumentException("illegal position: " + position);
        }
    }

}
