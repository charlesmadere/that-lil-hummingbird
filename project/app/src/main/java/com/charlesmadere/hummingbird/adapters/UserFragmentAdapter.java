package com.charlesmadere.hummingbird.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.charlesmadere.hummingbird.fragments.UserFeedFragment;
import com.charlesmadere.hummingbird.models.UserDigest;

public class UserFragmentAdapter extends BaseUserFragmentAdapter {

    public UserFragmentAdapter(final FragmentActivity activity, final UserDigest digest) {
        super(activity, digest);
    }

    public UserFragmentAdapter(final Context context, final FragmentManager fm,
            final UserDigest digest) {
        super(context, fm, digest);
    }

    @Override
    protected UserFeedFragment createFeedFragment() {
        return UserFeedFragment.create(getUserDigest().getUserId());
    }

    @Nullable
    @Override
    public UserFeedFragment getFeedFragment() {
        return (UserFeedFragment) super.getFeedFragment();
    }

}
