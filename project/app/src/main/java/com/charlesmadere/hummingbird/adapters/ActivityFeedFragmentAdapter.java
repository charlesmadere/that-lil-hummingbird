package com.charlesmadere.hummingbird.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.fragments.FollowingFeedFragment;
import com.charlesmadere.hummingbird.fragments.GlobalFeedFragment;

public class ActivityFeedFragmentAdapter extends FragmentStatePagerAdapter {

    private final Context mContext;


    public ActivityFeedFragmentAdapter(final FragmentActivity activity) {
        this(activity, activity.getSupportFragmentManager());
    }

    public ActivityFeedFragmentAdapter(final Context context, final FragmentManager fm) {
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
            case 0: return FollowingFeedFragment.create();
            case 1: return GlobalFeedFragment.create();
            default: throw new IllegalArgumentException("illegal position: " + position);
        }
    }

    @Override
    public CharSequence getPageTitle(final int position) {
        switch (position) {
            case 0: return mContext.getText(R.string.following);
            case 1: return mContext.getText(R.string.global);
            default: throw new IllegalArgumentException("illegal position: " + position);
        }
    }

}
