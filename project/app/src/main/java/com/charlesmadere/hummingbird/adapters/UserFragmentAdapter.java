package com.charlesmadere.hummingbird.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.charlesmadere.hummingbird.fragments.UserFeedFragment;

public class UserFragmentAdapter extends BaseUserFragmentAdapter {

    public UserFragmentAdapter(final FragmentActivity activity) {
        super(activity);
    }

    public UserFragmentAdapter(final Context context, final FragmentManager fm) {
        super(context, fm);
    }

    @Override
    protected UserFeedFragment createUserFeedFragment() {
        return UserFeedFragment.create();
    }

    @Nullable
    @Override
    public UserFeedFragment getFeedFragment() {
        return (UserFeedFragment) super.getFeedFragment();
    }

}
