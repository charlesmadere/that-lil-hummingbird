package com.charlesmadere.hummingbird.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.charlesmadere.hummingbird.fragments.NewsFeedFragment;
import com.charlesmadere.hummingbird.fragments.UserGroupsFragment;
import com.charlesmadere.hummingbird.fragments.UserProfileFragment;

public class NewsFeedFragmentAdapter extends BaseUserFragmentAdapter {

    public NewsFeedFragmentAdapter(final FragmentActivity activity) {
        super(activity);
    }

    public NewsFeedFragmentAdapter(final Context context, final FragmentManager fm) {
        super(context, fm);
    }

    @Override
    protected NewsFeedFragment createUserFeedFragment() {
        return NewsFeedFragment.create();
    }

    @Override
    protected UserGroupsFragment createUserGroupsFragment() {
        return UserGroupsFragment.create();
    }

    @Override
    protected UserProfileFragment createUserProfileFragment() {
        return UserProfileFragment.create();
    }

    @Nullable
    @Override
    public NewsFeedFragment getFeedFragment() {
        return (NewsFeedFragment) super.getFeedFragment();
    }

}
