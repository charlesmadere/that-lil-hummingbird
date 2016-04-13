package com.charlesmadere.hummingbird.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.fragments.AnimeLibraryFragment;
import com.charlesmadere.hummingbird.fragments.FeedFragment;
import com.charlesmadere.hummingbird.models.User;
import com.charlesmadere.hummingbird.models.WatchingStatus;

public class UserFragmentAdapter extends FragmentStatePagerAdapter {

    private final Context mContext;
    private final User mUser;


    public UserFragmentAdapter(final FragmentActivity activity, final User user) {
        this(activity, activity.getSupportFragmentManager(), user);
    }

    public UserFragmentAdapter(final Context context, final FragmentManager fm, final User user) {
        super(fm);
        mContext = context;
        mUser = user;
    }

    @Override
    public int getCount() {
        return WatchingStatus.values().length + 1;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return FeedFragment.create(mUser);
        } else {
            --position;
            final WatchingStatus watchingStatus = WatchingStatus.values()[position];
            return AnimeLibraryFragment.create(mUser, watchingStatus);
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        final int pageTitleResId;

        if (position == 0) {
            pageTitleResId = R.string.feed;
        } else {
            --position;

            final WatchingStatus watchingStatus = WatchingStatus.values()[position];
            pageTitleResId = watchingStatus.getTextResId();
        }

        return mContext.getText(pageTitleResId);
    }

}
