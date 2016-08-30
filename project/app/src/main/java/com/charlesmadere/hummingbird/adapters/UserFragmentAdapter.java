package com.charlesmadere.hummingbird.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.charlesmadere.hummingbird.fragments.UserFeedFragment;
import com.charlesmadere.hummingbird.fragments.UserGroupsFragment;
import com.charlesmadere.hummingbird.fragments.UserProfileFragment;

public class UserFragmentAdapter extends BaseUserFragmentAdapter {

    private final String mUsername;


    public UserFragmentAdapter(final FragmentActivity activity, final String username) {
        super(activity);
        mUsername = username;
    }

    public UserFragmentAdapter(final Context context, final FragmentManager fm,
            final String username) {
        super(context, fm);
        mUsername = username;
    }

    @Override
    protected UserFeedFragment createUserFeedFragment() {
        return UserFeedFragment.create(mUsername);
    }

    @Override
    protected UserGroupsFragment createUserGroupsFragment() {
        return UserGroupsFragment.create(mUsername);
    }

    @Override
    protected UserProfileFragment createUserProfileFragment() {
        return UserProfileFragment.create(mUsername);
    }

    @Nullable
    @Override
    public UserFeedFragment getFeedFragment() {
        return (UserFeedFragment) super.getFeedFragment();
    }

}
