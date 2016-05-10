package com.charlesmadere.hummingbird.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.fragments.HomeFeedFragment;
import com.charlesmadere.hummingbird.fragments.UserDigestFragment;

public class HomeFragmentAdapter extends FragmentStatePagerAdapter {

    private final Context mContext;


    public HomeFragmentAdapter(final FragmentActivity activity) {
        this(activity, activity.getSupportFragmentManager());
    }

    public HomeFragmentAdapter(final Context context, final FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Fragment getItem(final int position) {
        final Fragment fragment;

        switch (position) {
            case 0:
                fragment = HomeFeedFragment.create();
                break;

            case 1:
                fragment = UserDigestFragment.create();
                break;

            default:
                throw new IllegalArgumentException("illegal position: " + position);
        }

        return fragment;
    }

    @Override
    public CharSequence getPageTitle(final int position) {
        final CharSequence title;

        switch (position) {
            case 0:
                title = mContext.getString(R.string.feed);
                break;

            case 1:
                title = mContext.getString(R.string.profile);
                break;

            default:
                throw new IllegalArgumentException("illegal position: " + position);
        }

        return title;
    }

}
