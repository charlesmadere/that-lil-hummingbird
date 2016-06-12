package com.charlesmadere.hummingbird.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.GroupMembersAdapter;
import com.charlesmadere.hummingbird.models.ErrorInfo;
import com.charlesmadere.hummingbird.models.Feed;
import com.charlesmadere.hummingbird.networking.Api;
import com.charlesmadere.hummingbird.networking.ApiResponse;
import com.charlesmadere.hummingbird.views.RecyclerViewPaginator;
import com.charlesmadere.hummingbird.views.RefreshLayout;
import com.charlesmadere.hummingbird.views.SpaceItemDecoration;

import java.lang.ref.WeakReference;

import butterknife.BindView;

public class GroupMembersActivity extends BaseDrawerActivity implements
        RecyclerViewPaginator.Listeners, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "GroupMembersActivity";
    private static final String CNAME = GroupMembersActivity.class.getCanonicalName();
    private static final String EXTRA_GROUP_ID = CNAME + ".GroupId";
    private static final String EXTRA_GROUP_NAME = CNAME + ".GroupName";
    private static final String KEY_FEED = "Feed";

    private Feed mFeed;
    private GroupMembersAdapter mAdapter;
    private RecyclerViewPaginator mPaginator;
    private String mGroupId;

    @BindView(R.id.llEmpty)
    LinearLayout mEmpty;

    @BindView(R.id.llError)
    LinearLayout mError;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

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
    public boolean isLoading() {
        return mRefreshLayout.isRefreshing() || mAdapter.isPaginating();
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
        setTitle(getString(R.string.group_members_of_x, intent.getStringExtra(EXTRA_GROUP_NAME)));
        mGroupId = intent.getStringExtra(EXTRA_GROUP_ID);

        if (savedInstanceState != null && !savedInstanceState.isEmpty()) {
            mFeed = savedInstanceState.getParcelable(KEY_FEED);
        }

        if (mFeed == null || !mFeed.hasGroupMembers()) {
            fetchFeed();
        } else {
            showGroupMembers(mFeed);
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

    @Override
    protected void onViewsBound() {
        super.onViewsBound();
        mRefreshLayout.setOnRefreshListener(this);
        SpaceItemDecoration.apply(mRecyclerView, false, R.dimen.root_padding_half);
        mAdapter = new GroupMembersAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mPaginator = new RecyclerViewPaginator(mRecyclerView, this);
    }

    @Override
    public void paginate() {
        mAdapter.setPaginating(true);
        Api.getGroupMembers(mGroupId, mFeed, new PaginateGroupMembersListener(this));
    }

    private void showEmpty() {
        mRecyclerView.setVisibility(View.GONE);
        mError.setVisibility(View.GONE);
        mEmpty.setVisibility(View.VISIBLE);
        mRefreshLayout.setRefreshing(false);
    }

    private void showError() {
        mRecyclerView.setVisibility(View.GONE);
        mEmpty.setVisibility(View.GONE);
        mError.setVisibility(View.VISIBLE);
        mRefreshLayout.setRefreshing(false);
    }

    private void showGroupMembers(final Feed feed) {
        mFeed = feed;
        mAdapter.set(mFeed);
        mEmpty.setVisibility(View.GONE);
        mError.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        mPaginator.setEnabled(true);
        mRefreshLayout.setRefreshing(false);
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
                    activity.showGroupMembers(feed);
                } else {
                    activity.showEmpty();
                }
            }
        }
    }

    private static class PaginateGroupMembersListener implements ApiResponse<Feed> {
        private final WeakReference<GroupMembersActivity> mActivityReference;
        private final int mGroupMembersSize;

        private PaginateGroupMembersListener(final GroupMembersActivity activity) {
            mActivityReference = new WeakReference<>(activity);
            mGroupMembersSize = activity.mFeed.getGroupMembers().size();
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
                if (feed.getGroupMembers().size() <= mGroupMembersSize) {
                    activity.showGroupMembers(feed);
                } else {
                    activity.showEmpty();
                }
            }
        }
    }

}
