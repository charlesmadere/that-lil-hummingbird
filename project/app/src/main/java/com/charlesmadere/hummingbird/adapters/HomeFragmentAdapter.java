package com.charlesmadere.hummingbird.adapters;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.charlesmadere.hummingbird.fragments.BaseFeedFragment;
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
    protected BaseFeedFragment createFeedFragment() {
        return HomeFeedFragment.create();
    }

}
