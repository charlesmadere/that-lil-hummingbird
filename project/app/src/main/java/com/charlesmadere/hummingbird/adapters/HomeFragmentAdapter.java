package com.charlesmadere.hummingbird.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.charlesmadere.hummingbird.fragments.HomeFeedFragment;
import com.charlesmadere.hummingbird.misc.CurrentUser;

public class HomeFragmentAdapter extends BaseUserFragmentAdapter {

    public HomeFragmentAdapter(final FragmentActivity activity) {
        super(activity, CurrentUser.get());
    }

    public HomeFragmentAdapter(final Context context, final FragmentManager fm) {
        super(context, fm, CurrentUser.get());
    }

    @Override
    protected HomeFeedFragment createFeedFragment() {
        return HomeFeedFragment.create();
    }

    @Nullable
    @Override
    public HomeFeedFragment getFeedFragment() {
        return (HomeFeedFragment) super.getFeedFragment();
    }

}
