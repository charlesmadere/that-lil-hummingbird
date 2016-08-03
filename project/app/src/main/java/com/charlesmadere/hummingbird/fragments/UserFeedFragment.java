package com.charlesmadere.hummingbird.fragments;

import com.charlesmadere.hummingbird.networking.Api;

public class UserFeedFragment extends BaseUserFeedFragment {

    private static final String TAG = "UserFeedFragment";


    public static UserFeedFragment create() {
        return new UserFeedFragment();
    }

    @Override
    protected void fetchFeed() {
        super.fetchFeed();
        Api.getUserStories(getUserDigest().getUserId(), new GetFeedListener(this));
    }

    @Override
    public String getFragmentName() {
        return TAG;
    }

    @Override
    public void paginate() {
        super.paginate();
        Api.getUserStories(getUserDigest().getUserId(), mFeed, new PaginateFeedListener(this));
    }

}
