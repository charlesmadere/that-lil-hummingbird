package com.charlesmadere.hummingbird.fragments;

import android.os.Bundle;

import com.charlesmadere.hummingbird.misc.CurrentUser;
import com.charlesmadere.hummingbird.networking.Api;

public class NewsFeedFragment extends BaseUserFeedFragment {

    private static final String TAG = "NewsFeedFragment";


    public static NewsFeedFragment create() {
        final Bundle args = new Bundle(1);
        args.putString(KEY_USERNAME, CurrentUser.get().getUserId());

        final NewsFeedFragment fragment = new NewsFeedFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void fetchFeed() {
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
        Api.getNewsFeed(mFeed, new PaginateFeedListener(this));
    }

}
