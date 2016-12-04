package com.charlesmadere.hummingbird.fragments;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FollowingFeedFragment extends BaseFragment implements
        SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "FollowingFeedFragment";


    @Override
    public String getFragmentName() {
        return TAG;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            final Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onRefresh() {

    }

}
