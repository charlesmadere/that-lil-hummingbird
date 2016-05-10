package com.charlesmadere.hummingbird.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.fragments.UserDigestFragment;

public class HomeAdapter extends FragmentStatePagerAdapter {

    private final Context mContext;
    private final String mUsername;


    public HomeAdapter(final FragmentActivity activity, final String username) {
        this(activity, activity.getSupportFragmentManager(), username);
    }

    public HomeAdapter(final Context context, final FragmentManager fm, final String username) {
        super(fm);
        mContext = context;
        mUsername = username;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Fragment getItem(final int position) {
        final Fragment fragment;

        switch (position) {
            case 0:
                fragment = null;
                break;

            case 1:
                fragment = UserDigestFragment.create(mUsername);
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
                title = mContext.getString(R.string.bio);
                break;

            default:
                throw new IllegalArgumentException("illegal position: " + position);
        }

        return title;
    }

}
