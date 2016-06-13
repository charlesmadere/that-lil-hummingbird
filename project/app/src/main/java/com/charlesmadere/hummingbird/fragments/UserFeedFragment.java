package com.charlesmadere.hummingbird.fragments;

import android.os.Bundle;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.models.Feed;
import com.charlesmadere.hummingbird.networking.Api;

import butterknife.OnClick;

public class UserFeedFragment extends BaseFeedFragment {

    private static final String TAG = "UserFeedFragment";


    public static UserFeedFragment create(final String username) {
        final Bundle args = new Bundle(1);
        args.putString(KEY_USERNAME, username);

        final UserFeedFragment fragment = new UserFeedFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    protected void fetchFeed() {
        super.fetchFeed();
        Api.getUserStories(mUsername, new GetFeedListener(this));
    }

    @Override
    public String getFragmentName() {
        return TAG;
    }

    @OnClick(R.id.floatingActionButton)
    void onFeedClick() {
        // TODO
    }

    @Override
    public void paginate() {
        super.paginate();
        Api.getUserStories(mUsername, mFeed, new PaginateFeedListener(this));
    }

    @Override
    protected void showFeed(final Feed feed) {
        super.showFeed(feed);
        mReply.show();
    }

}
