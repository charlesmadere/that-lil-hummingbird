package com.charlesmadere.hummingbird.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.util.SparseArrayCompat;
import android.view.ViewGroup;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.fragments.BaseUserFragment;
import com.charlesmadere.hummingbird.fragments.UserFeedFragment;
import com.charlesmadere.hummingbird.fragments.UserGroupsFragment;
import com.charlesmadere.hummingbird.fragments.UserProfileFragment;

import java.lang.ref.WeakReference;

public class UserFragmentAdapter extends FragmentStatePagerAdapter {

    public static final int POSITION_FEED = 0;
    public static final int POSITION_PROFILE = 1;
    public static final int POSITION_GROUPS = 2;

    private final Context mContext;
    private final SparseArrayCompat<WeakReference<BaseUserFragment>> mFragments;


    public UserFragmentAdapter(final FragmentActivity activity) {
        this(activity, activity.getSupportFragmentManager());
    }

    public UserFragmentAdapter(final Context context, final FragmentManager fm) {
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
        return 3;
    }

    @Nullable
    public UserFeedFragment getFeedFragment() {
        final WeakReference<BaseUserFragment> fragmentReference = mFragments.get(POSITION_FEED);

        if (fragmentReference == null) {
            return null;
        } else {
            return (UserFeedFragment) fragmentReference.get();
        }
    }

    @Override
    public BaseUserFragment getItem(final int position) {
        final BaseUserFragment fragment;

        switch (position) {
            case POSITION_FEED: fragment = UserFeedFragment.create(); break;
            case POSITION_PROFILE: fragment = UserProfileFragment.create(); break;
            case POSITION_GROUPS: fragment = UserGroupsFragment.create(); break;
            default: throw new IllegalArgumentException("invalid position: " + position);
        }

        mFragments.put(position, new WeakReference<>(fragment));
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(final int position) {
        switch (position) {
            case POSITION_FEED: return mContext.getText(R.string.feed);
            case POSITION_PROFILE: return mContext.getText(R.string.profile);
            case POSITION_GROUPS: return mContext.getText(R.string.groups);
            default: throw new IllegalArgumentException("invalid position: " + position);
        }
    }

    @Override
    public BaseUserFragment instantiateItem(final ViewGroup container, final int position) {
        final BaseUserFragment fragment = (BaseUserFragment) super.instantiateItem(container,
                position);
        mFragments.put(position, new WeakReference<>(fragment));
        return fragment;
    }

    public void showUserDigest() {
        for (int i = 0; i < mFragments.size(); ++i) {
            final WeakReference<BaseUserFragment> fragmentReference = mFragments.get(i);

            if (fragmentReference != null) {
                final BaseUserFragment fragment = fragmentReference.get();

                if (fragment != null && fragment.isAlive()) {
                    fragment.showUserDigest();
                }
            }
        }
    }

}
