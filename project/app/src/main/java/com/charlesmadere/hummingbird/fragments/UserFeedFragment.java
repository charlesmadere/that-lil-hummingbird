package com.charlesmadere.hummingbird.fragments;

import android.os.Bundle;

import com.charlesmadere.hummingbird.networking.Api;

public class UserFeedFragment extends BaseUserFeedFragment {

    private static final String TAG = "UserFeedFragment";


    public static UserFeedFragment create(final String username) {
        final Bundle args = new Bundle(1);
        args.putString(KEY_USERNAME, username);

        final UserFeedFragment fragment = new UserFeedFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void fetchFeed() {
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
