package com.charlesmadere.hummingbird.fragments;

import android.os.Bundle;

import com.charlesmadere.hummingbird.misc.CurrentUser;
import com.charlesmadere.hummingbird.networking.Api;

public class HomeFeedFragment extends BaseFeedFragment {

    private static final String TAG = "HomeFeedFragment";


    public static HomeFeedFragment create() {
        final Bundle args = new Bundle(1);
        args.putString(KEY_USERNAME, CurrentUser.get().getUserId());

        final HomeFeedFragment fragment = new HomeFeedFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    protected void fetchFeed() {
        super.fetchFeed();
        Api.getNewsFeed(new GetFeedListener(this));
    }

    @Override
    public String getFragmentName() {
        return TAG;
    }

    @Override
    public void paginate() {
        super.paginate();

    }

}
