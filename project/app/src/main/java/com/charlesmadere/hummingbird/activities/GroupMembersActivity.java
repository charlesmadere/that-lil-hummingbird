package com.charlesmadere.hummingbird.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.models.ErrorInfo;
import com.charlesmadere.hummingbird.models.Feed;
import com.charlesmadere.hummingbird.networking.Api;
import com.charlesmadere.hummingbird.networking.ApiResponse;
import com.charlesmadere.hummingbird.views.RefreshLayout;

import java.lang.ref.WeakReference;

import butterknife.BindView;

public class GroupMembersActivity extends BaseDrawerActivity implements
        SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "GroupMembersActivity";
    private static final String CNAME = GroupMembersActivity.class.getCanonicalName();
    private static final String EXTRA_GROUP_ID = CNAME + ".GroupId";
    private static final String EXTRA_GROUP_NAME = CNAME + ".GroupName";
    private static final String KEY_FEED = "Feed";

    private Feed mFeed;
    private String mGroupId;

    @BindView(R.id.refreshLayout)
    RefreshLayout mRefreshLayout;


    public static Intent getLaunchIntent(final Context context, final String groupId,
            final String groupName) {
        return new Intent(context, GroupMembersActivity.class)
                .putExtra(EXTRA_GROUP_ID, groupId)
                .putExtra(EXTRA_GROUP_NAME, groupName);
    }

    private void fetchFeed() {
        mRefreshLayout.setRefreshing(true);
        Api.getGroupMembers(mGroupId, new GetGroupMembersListener(this));
    }

    @Override
    public String getActivityName() {
        return TAG;
    }

    @Override
    protected boolean isUpNavigationEnabled() {
        return true;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_members);

        final Intent intent = getIntent();
        setTitle(intent.getStringExtra(EXTRA_GROUP_NAME));
        mGroupId = intent.getStringExtra(EXTRA_GROUP_ID);

        if (savedInstanceState != null && !savedInstanceState.isEmpty()) {
            mFeed = savedInstanceState.getParcelable(KEY_FEED);
        }

        if (mFeed == null || !mFeed.hasGroupMembers()) {
            fetchFeed();
        } else {
            showFeed(mFeed);
        }
    }

    @Override
    public void onRefresh() {
        fetchFeed();
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mFeed != null && mFeed.hasGroupMembers()) {
            outState.putParcelable(KEY_FEED, mFeed);
        }
    }

    private void showEmpty() {

    }

    private void showError() {

    }

    private void showFeed(final Feed feed) {
        mFeed = feed;


    }


    private static class GetGroupMembersListener implements ApiResponse<Feed> {
        private final WeakReference<GroupMembersActivity> mActivityReference;

        private GetGroupMembersListener(final GroupMembersActivity activity) {
            mActivityReference = new WeakReference<>(activity);
        }

        @Override
        public void failure(@Nullable final ErrorInfo error) {
            final GroupMembersActivity activity = mActivityReference.get();

            if (activity != null && !activity.isDestroyed()) {
                activity.showError();
            }
        }

        @Override
        public void success(final Feed feed) {
            final GroupMembersActivity activity = mActivityReference.get();

            if (activity != null && !activity.isDestroyed()) {
                if (feed.hasGroupMembers()) {
                    activity.showFeed(feed);
                } else {
                    activity.showEmpty();
                }
            }
        }
    }

}
