package com.charlesmadere.hummingbird.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.charlesmadere.hummingbird.models.ErrorInfo;
import com.charlesmadere.hummingbird.models.Feed;
import com.charlesmadere.hummingbird.models.UserV1;
import com.charlesmadere.hummingbird.networking.Api;
import com.charlesmadere.hummingbird.networking.ApiResponse;

import java.lang.ref.WeakReference;

public class HomeFeedFragment extends BaseFeedFragment {

    private static final String TAG = "HomeFeedFragment";


    public static HomeFeedFragment create(final UserV1 user) {
        final Bundle args = new Bundle(1);
        args.putParcelable(KEY_USER, user);

        final HomeFeedFragment fragment = new HomeFeedFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    protected void fetchFeed() {
        super.fetchFeed();
        Api.getUserStories(mUser.getName(), new GetNewsFeedListener(this));
    }

    @Override
    public String getFragmentName() {
        return TAG;
    }


    private static class GetNewsFeedListener implements ApiResponse<Feed> {
        private final WeakReference<HomeFeedFragment> mFragmentReference;

        private GetNewsFeedListener(final HomeFeedFragment fragment) {
            mFragmentReference = new WeakReference<>(fragment);
        }

        @Override
        public void failure(@Nullable final ErrorInfo error) {
            final HomeFeedFragment fragment = mFragmentReference.get();

            if (fragment != null && !fragment.isDestroyed()) {
                fragment.showError();
            }
        }

        @Override
        public void success(final Feed feed) {
            final HomeFeedFragment fragment = mFragmentReference.get();

            if (fragment != null && !fragment.isDestroyed()) {
                if (feed.hasStories()) {
                    fragment.showFeed(feed);
                } else {
                    fragment.showEmpty();
                }
            }
        }
    }

}
