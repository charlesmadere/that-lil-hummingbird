package com.charlesmadere.hummingbird.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.fragments.AnimeLibraryFragment;
import com.charlesmadere.hummingbird.fragments.FavoriteAnimeFragment;
import com.charlesmadere.hummingbird.fragments.UserBioFragment;
import com.charlesmadere.hummingbird.fragments.UserFeedFragment;
import com.charlesmadere.hummingbird.models.UserV1;
import com.charlesmadere.hummingbird.models.WatchingStatus;

public class UserFragmentAdapter extends FragmentStatePagerAdapter {

    public static final int POSITION_FAVORITES = 0;
    public static final int POSITION_BIO = 1;
    public static final int POSITION_FEED = 2;

    private static final WatchingStatus[] ORDER = { WatchingStatus.CURRENTLY_WATCHING,
            WatchingStatus.COMPLETED, WatchingStatus.PLAN_TO_WATCH,
            WatchingStatus.ON_HOLD, WatchingStatus.DROPPED };

    private final Context mContext;
    private final UserV1 mUser;


    public UserFragmentAdapter(final FragmentActivity activity, final UserV1 user) {
        this(activity, activity.getSupportFragmentManager(), user);
    }

    public UserFragmentAdapter(final Context context, final FragmentManager fm, final UserV1 user) {
        super(fm);
        mContext = context;
        mUser = user;
    }

    @Override
    public int getCount() {
        return WatchingStatus.values().length + 3;
    }

    @Override
    public Fragment getItem(final int position) {
        final Fragment fragment;

        switch (position) {
            case POSITION_FAVORITES:
                fragment = FavoriteAnimeFragment.create(mUser);
                break;

            case POSITION_BIO:
                fragment = UserBioFragment.create(mUser);
                break;

            case POSITION_FEED:
                fragment = UserFeedFragment.create(mUser);
                break;

            default:
                final WatchingStatus watchingStatus = ORDER[position - 3];
                fragment = AnimeLibraryFragment.create(mUser, watchingStatus);
                break;
        }

        return fragment;
    }

    @Override
    public CharSequence getPageTitle(final int position) {
        final int pageTitleResId;

        switch (position) {
            case POSITION_FAVORITES:
                pageTitleResId = R.string.favorites;
                break;

            case POSITION_BIO:
                pageTitleResId = R.string.bio;
                break;

            case POSITION_FEED:
                pageTitleResId = R.string.feed;
                break;

            default:
                final WatchingStatus watchingStatus = ORDER[position - 3];
                pageTitleResId = watchingStatus.getTextResId();
                break;
        }

        return mContext.getText(pageTitleResId);
    }

}
