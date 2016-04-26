package com.charlesmadere.hummingbird.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.fragments.AnimeLibraryFragment;
import com.charlesmadere.hummingbird.fragments.FeedFragment;
import com.charlesmadere.hummingbird.fragments.UserBioFragment;
import com.charlesmadere.hummingbird.models.User;
import com.charlesmadere.hummingbird.models.WatchingStatus;

public class UserFragmentAdapter extends FragmentStatePagerAdapter {

    private static final WatchingStatus[] ORDER = { WatchingStatus.CURRENTLY_WATCHING,
            WatchingStatus.COMPLETED, WatchingStatus.PLAN_TO_WATCH,
            WatchingStatus.ON_HOLD, WatchingStatus.DROPPED };

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
        return WatchingStatus.values().length + 2;
    }

    @Override
    public Fragment getItem(final int position) {
        final Fragment fragment;

        switch (position) {
            case 0:
                fragment = UserBioFragment.create(mUser);
                break;

            case 1:
                fragment = FeedFragment.create(mUser);
                break;

            default:
                final WatchingStatus watchingStatus = ORDER[position - 2];
                fragment = AnimeLibraryFragment.create(mUser, watchingStatus);
                break;
        }

        return fragment;
    }

    @Override
    public CharSequence getPageTitle(final int position) {
        final int pageTitleResId;

        switch (position) {
            case 0:
                pageTitleResId = R.string.bio;
                break;

            case 1:
                pageTitleResId = R.string.feed;
                break;

            default:
                final WatchingStatus watchingStatus = ORDER[position - 2];
                pageTitleResId = watchingStatus.getTextResId();
                break;
        }

        return mContext.getText(pageTitleResId);
    }

}
