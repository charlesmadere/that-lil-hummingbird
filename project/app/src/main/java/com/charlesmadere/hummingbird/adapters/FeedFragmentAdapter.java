package com.charlesmadere.hummingbird.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.charlesmadere.hummingbird.fragments.FeedFragment;
import com.charlesmadere.hummingbird.fragments.UserProfileFragment;

public class FeedFragmentAdapter extends BaseUserFragmentAdapter {

    public FeedFragmentAdapter(final FragmentActivity activity) {
        super(activity);
    }

    public FeedFragmentAdapter(final Context context, final FragmentManager fm) {
        super(context, fm);
    }

    @Override
    protected FeedFragment createUserFeedFragment() {
        return FeedFragment.create();
    }

    @Override
    protected UserProfileFragment createUserProfileFragment() {
        return UserProfileFragment.create();
    }

    @Nullable
    @Override
    public FeedFragment getFeedFragment() {
        return (FeedFragment) super.getFeedFragment();
    }

}
