package com.charlesmadere.hummingbird.fragments;

import com.charlesmadere.hummingbird.networking.Api;

public class NewsFeedFragment extends BaseUserFeedFragment {

    private static final String TAG = "NewsFeedFragment";


    public static NewsFeedFragment create() {
        return new NewsFeedFragment();
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
