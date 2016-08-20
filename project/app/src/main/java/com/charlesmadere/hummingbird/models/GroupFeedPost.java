package com.charlesmadere.hummingbird.models;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

public class GroupFeedPost extends FeedPost {

    @SerializedName("group_id")
    private final String mGroupId;


    public GroupFeedPost(final boolean adult, final String comment, final String userId,
            final String groupId) {
        super(adult, comment, userId);

        if (TextUtils.isEmpty(groupId) || TextUtils.getTrimmedLength(groupId) == 0) {
            throw new IllegalArgumentException("groupId can't be null / empty / whitespace");
        }

        mGroupId = groupId;
    }

    public GroupFeedPost(final FeedPost feedPost, final String groupId) {
        this(feedPost.isAdult(), feedPost.getComment(), feedPost.getUserId(), groupId);
    }

    public String getGroupId() {
        return mGroupId;
    }

}
