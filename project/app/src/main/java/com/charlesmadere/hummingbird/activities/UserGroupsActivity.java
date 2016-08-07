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
import com.charlesmadere.hummingbird.adapters.GroupsAdapter;
import com.charlesmadere.hummingbird.misc.ObjectCache;
import com.charlesmadere.hummingbird.models.ErrorInfo;
import com.charlesmadere.hummingbird.models.Feed;
import com.charlesmadere.hummingbird.models.UiColorSet;
import com.charlesmadere.hummingbird.networking.Api;
import com.charlesmadere.hummingbird.networking.ApiResponse;
import com.charlesmadere.hummingbird.views.RecyclerViewPaginator;
import com.charlesmadere.hummingbird.views.RefreshLayout;
import com.charlesmadere.hummingbird.views.SpaceItemDecoration;

import java.lang.ref.WeakReference;

import butterknife.BindView;

public class UserGroupsActivity extends BaseDrawerActivity implements ObjectCache.KeyProvider,
        RecyclerViewPaginator.Listeners, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "UserGroupsActivity";
    private static final String CNAME = UserGroupsActivity.class.getCanonicalName();
    private static final String EXTRA_USERNAME = CNAME + ".Username";

    private Feed mFeed;
    private GroupsAdapter mAdapter;
    private RecyclerViewPaginator mPaginator;
    private String mUsername;

    @BindView(R.id.llEmpty)
    LinearLayout mEmpty;

    @BindView(R.id.llError)
    LinearLayout mError;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.refreshLayout)
    RefreshLayout mRefreshLayout;


    public static Intent getLaunchIntent(final Context context, final String username) {
        return getLaunchIntent(context, username, null);
    }

    public static Intent getLaunchIntent(final Context context, final String username,
            @Nullable final UiColorSet uiColorSet) {
        final Intent intent = new Intent(context, UserGroupsActivity.class)
                .putExtra(EXTRA_USERNAME, username);

        if (uiColorSet != null) {
            intent.putExtra(EXTRA_UI_COLOR_SET, uiColorSet);
        }

        return intent;
    }

    private void fetchUserGroups() {
        mRefreshLayout.setRefreshing(true);
        Api.getUserGroups(mUsername, new GetUserGroupsListener(this));
    }

    @Override
    public String getActivityName() {
        return TAG;
    }

    @Override
    public String[] getObjectCacheKeys() {
        return new String[] { getActivityName(), mUsername };
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
        setContentView(R.layout.activity_user_groups);

        final Intent intent = getIntent();
        mUsername = intent.getStringExtra(EXTRA_USERNAME);
        setSubtitle(mUsername);

        mFeed = ObjectCache.get(this);

        if (mFeed == null || !mFeed.hasGroups()) {
            fetchUserGroups();
        } else {
            showUserGroups(mFeed);
        }
    }

    @Override
    public void onRefresh() {
        fetchUserGroups();
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
        SpaceItemDecoration.apply(mRecyclerView, true, R.dimen.root_padding_half);
        mAdapter = new GroupsAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mPaginator = new RecyclerViewPaginator(mRecyclerView, this);
    }

    @Override
    public void paginate() {
        mAdapter.setPaginating(true);
        Api.getUserGroups(mUsername, mFeed, new PaginateGroupsListener(this));
    }

    private void paginationComplete() {
        mAdapter.set(mFeed);
        mAdapter.setPaginating(false);
    }

    private void paginationNoMore() {
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

    private void showUserGroups(final Feed feed) {
        mFeed = feed;
        mAdapter.set(mFeed);
        mEmpty.setVisibility(View.GONE);
        mError.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        mPaginator.setEnabled(feed.hasCursor());
        mRefreshLayout.setRefreshing(false);
    }


    private static class GetUserGroupsListener implements ApiResponse<Feed> {
        private final WeakReference<UserGroupsActivity> mActivityReference;

        private GetUserGroupsListener(final UserGroupsActivity activity) {
            mActivityReference = new WeakReference<>(activity);
        }

        @Override
        public void failure(@Nullable final ErrorInfo error) {
            final UserGroupsActivity activity = mActivityReference.get();

            if (activity != null && !activity.isDestroyed()) {
                activity.showError();
            }
        }

        @Override
        public void success(final Feed feed) {
            final UserGroupsActivity activity = mActivityReference.get();

            if (activity != null && !activity.isDestroyed()) {
                if (feed.hasGroups()) {
                    activity.showUserGroups(feed);
                } else {
                    activity.showEmpty();
                }
            }
        }
    }

    private static class PaginateGroupsListener implements ApiResponse<Feed> {
        private final WeakReference<UserGroupsActivity> mActivityReference;
        private final int mGroupsSize;

        private PaginateGroupsListener(final UserGroupsActivity activity) {
            mActivityReference = new WeakReference<>(activity);
            mGroupsSize = activity.mFeed.getGroupsSize();
        }

        @Override
        public void failure(@Nullable final ErrorInfo error) {
            final UserGroupsActivity activity = mActivityReference.get();

            if (activity != null && !activity.isDestroyed()) {
                activity.paginationNoMore();
            }
        }

        @Override
        public void success(final Feed feed) {
            final UserGroupsActivity activity = mActivityReference.get();

            if (activity != null && !activity.isDestroyed()) {
                if (feed.hasCursor() && feed.getGroupsSize() > mGroupsSize) {
                    activity.paginationComplete();
                } else {
                    activity.paginationNoMore();
                }
            }
        }
    }

}
