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

    public static final int POSITION_DETAILS = 0;
    public static final int POSITION_FEED = 1;

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
            case POSITION_DETAILS: return GroupDetailsFragment.create();
            case POSITION_FEED: return GroupFeedFragment.create();
            default: throw new IllegalArgumentException("illegal position: " + position);
        }
    }

    @Override
    public CharSequence getPageTitle(final int position) {
        switch (position) {
            case POSITION_DETAILS: return mContext.getText(R.string.details);
            case POSITION_FEED: return mContext.getText(R.string.feed);
            default: throw new IllegalArgumentException("illegal position: " + position);
        }
    }

}
