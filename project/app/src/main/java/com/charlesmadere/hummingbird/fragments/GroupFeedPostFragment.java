package com.charlesmadere.hummingbird.fragments;

import android.support.annotation.Nullable;

import com.charlesmadere.hummingbird.models.FeedPost;
import com.charlesmadere.hummingbird.models.GroupFeedPost;

public class GroupFeedPostFragment extends FeedPostFragment {

    public static final String TAG = "GroupFeedPostFragment";


    public static GroupFeedPostFragment create() {
        return new GroupFeedPostFragment();
    }

    @Override
    public String getFragmentName() {
        return TAG;
    }

    @Nullable
    public GroupFeedPost getGroupFeedPost(final String userId, final String groupId) {
        final FeedPost feedPost = super.getFeedPost(userId);
        return feedPost == null ? null : new GroupFeedPost(feedPost, groupId);
    }

}
