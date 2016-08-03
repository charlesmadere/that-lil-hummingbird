package com.charlesmadere.hummingbird.fragments;

import com.charlesmadere.hummingbird.networking.Api;

public class FeedFragment extends BaseUserFeedFragment {

    private static final String TAG = "CurrentUserFeedFragment";


    public static FeedFragment create() {
        return new FeedFragment();
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
        Api.getNewsFeed(mFeed, new PaginateFeedListener(this));
    }

}
