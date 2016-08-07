package com.charlesmadere.hummingbird.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.GroupMembersAdapter;
import com.charlesmadere.hummingbird.misc.ObjectCache;
import com.charlesmadere.hummingbird.models.ErrorInfo;
import com.charlesmadere.hummingbird.models.Feed;
import com.charlesmadere.hummingbird.models.GroupDigest;
import com.charlesmadere.hummingbird.models.UiColorSet;
import com.charlesmadere.hummingbird.networking.Api;
import com.charlesmadere.hummingbird.networking.ApiResponse;
import com.charlesmadere.hummingbird.views.DividerItemDecoration;
import com.charlesmadere.hummingbird.views.RecyclerViewPaginator;
import com.charlesmadere.hummingbird.views.RefreshLayout;

import java.lang.ref.WeakReference;

import butterknife.BindView;

public class GroupMembersActivity extends BaseDrawerActivity implements ObjectCache.KeyProvider,
        RecyclerViewPaginator.Listeners, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "GroupMembersActivity";
    private static final String CNAME = GroupMembersActivity.class.getCanonicalName();
    private static final String EXTRA_GROUP_ID = CNAME + ".GroupId";
    private static final String EXTRA_GROUP_NAME = CNAME + ".GroupName";
    private static final String EXTRA_UI_COLOR_SET = CNAME + ".UiColorSet";

    private Feed mFeed;
    private GroupMembersAdapter mAdapter;
    private RecyclerViewPaginator mPaginator;
    private String mGroupId;
    private UiColorSet mUiColorSet;

    @BindView(R.id.llEmpty)
    LinearLayout mEmpty;

    @BindView(R.id.llError)
    LinearLayout mError;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.refreshLayout)
    RefreshLayout mRefreshLayout;


    public static Intent getLaunchIntent(final Context context, final String groupId) {
        return getLaunchIntent(context, groupId, null);
    }

    public static Intent getLaunchIntent(final Context context, final String groupId,
            @Nullable final String groupName) {
        return getLaunchIntent(context, groupId, groupName, null);
    }

    public static Intent getLaunchIntent(final Context context, final String groupId,
            @Nullable final String groupName, @Nullable final UiColorSet uiColorSet) {
        final Intent intent = new Intent(context, GroupMembersActivity.class)
                .putExtra(EXTRA_GROUP_ID, groupId);

        if (!TextUtils.isEmpty(groupName)) {
            intent.putExtra(EXTRA_GROUP_NAME, groupName);
        }

        if (uiColorSet != null) {
            intent.putExtra(EXTRA_UI_COLOR_SET, uiColorSet);
        }

        return intent;
    }

    private void applyUiColorSet() {
        mToolbar.setBackgroundColor(mUiColorSet.getDarkVibrantColor());
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
    public String[] getObjectCacheKeys() {
        return new String[] { getActivityName(), mGroupId };
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
        mGroupId = intent.getStringExtra(EXTRA_GROUP_ID);

        if (intent.hasExtra(EXTRA_GROUP_NAME)) {
            setSubtitle(intent.getStringExtra(EXTRA_GROUP_NAME));
        } else {
            Api.getGroup(mGroupId, new GetGroupDigestListener(this));
        }

        if (intent.hasExtra(EXTRA_UI_COLOR_SET)) {
            mUiColorSet = intent.getParcelableExtra(EXTRA_UI_COLOR_SET);
            applyUiColorSet();
        }

        mFeed = ObjectCache.get(this);

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

        if (mFeed != null) {
            ObjectCache.put(mFeed, this);
        }
    }

    @Override
    protected void onViewsBound() {
        super.onViewsBound();
        mRefreshLayout.setOnRefreshListener(this);
        mRecyclerView.setHasFixedSize(true);
        DividerItemDecoration.apply(mRecyclerView);
        mAdapter = new GroupMembersAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mPaginator = new RecyclerViewPaginator(mRecyclerView, this);
    }

    @Override
    public void paginate() {
        mAdapter.setPaginating(true);
        Api.getGroupMembers(mGroupId, mFeed, new PaginateGroupMembersListener(this));
    }

    protected void paginationComplete() {
        mAdapter.set(mFeed);
        mAdapter.setPaginating(false);
    }

    protected void paginationNoMore() {
        mPaginator.setEnabled(false);
        mAdapter.setPaginating(false);
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
        mPaginator.setEnabled(feed.hasCursor());
        mRefreshLayout.setRefreshing(false);
    }


    private static class GetGroupDigestListener implements ApiResponse<GroupDigest> {
        private final WeakReference<GroupMembersActivity> mActivityReference;

        private GetGroupDigestListener(final GroupMembersActivity activity) {
            mActivityReference = new WeakReference<>(activity);
        }

        @Override
        public void failure(@Nullable final ErrorInfo error) {
            // intentionally empty
        }

        @Override
        public void success(final GroupDigest groupDigest) {
            final GroupMembersActivity activity = mActivityReference.get();

            if (activity != null && !activity.isDestroyed()) {
                activity.setSubtitle(groupDigest.getName());
            }
        }
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
            mGroupMembersSize = activity.mFeed.getGroupMembersSize();
        }

        @Override
        public void failure(@Nullable final ErrorInfo error) {
            final GroupMembersActivity activity = mActivityReference.get();

            if (activity != null && !activity.isDestroyed()) {
                activity.paginationNoMore();
            }
        }

        @Override
        public void success(final Feed feed) {
            final GroupMembersActivity activity = mActivityReference.get();

            if (activity != null && !activity.isDestroyed()) {
                if (feed.hasCursor() && feed.getGroupMembersSize() > mGroupMembersSize) {
                    activity.paginationComplete();
                } else {
                    activity.paginationNoMore();
                }
            }
        }
    }

}
