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
import com.charlesmadere.hummingbird.adapters.UsersAdapter;
import com.charlesmadere.hummingbird.models.ErrorInfo;
import com.charlesmadere.hummingbird.models.Feed;
import com.charlesmadere.hummingbird.networking.Api;
import com.charlesmadere.hummingbird.networking.ApiResponse;
import com.charlesmadere.hummingbird.views.RecyclerViewPaginator;
import com.charlesmadere.hummingbird.views.RefreshLayout;
import com.charlesmadere.hummingbird.views.SpaceItemDecoration;

import java.lang.ref.WeakReference;

import butterknife.BindView;

public class FollowingActivity extends BaseDrawerActivity implements
        RecyclerViewPaginator.Listeners, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "FollowingActivity";
    private static final String CNAME = FollowingActivity.class.getCanonicalName();
    private static final String EXTRA_USERNAME = CNAME + ".Username";
    private static final String KEY_FEED = "Feed";

    private Feed mFeed;
    private RecyclerViewPaginator mPaginator;
    private String mUsername;
    private UsersAdapter mAdapter;

    @BindView(R.id.llEmpty)
    LinearLayout mEmpty;

    @BindView(R.id.llError)
    LinearLayout mError;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.refreshLayout)
    RefreshLayout mRefreshLayout;


    public static Intent getLaunchIntent(final Context context, final String username) {
        return new Intent(context, FollowingActivity.class)
                .putExtra(EXTRA_USERNAME, username);
    }

    private void fetchFollowing() {
        mRefreshLayout.setRefreshing(true);
        Api.getFollowedUsers(mUsername, new GetFollowingListener(this));
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
        setContentView(R.layout.activity_following);

        final Intent intent = getIntent();
        mUsername = intent.getStringExtra(EXTRA_USERNAME);
        setSubtitle(mUsername);

        if (savedInstanceState != null && !savedInstanceState.isEmpty()) {
            mFeed = savedInstanceState.getParcelable(KEY_FEED);
        }

        if (mFeed == null) {
            fetchFollowing();
        } else {
            showFollowing(mFeed);
        }
    }

    @Override
    public void onRefresh() {
        fetchFollowing();
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mFeed != null) {
            outState.putParcelable(KEY_FEED, mFeed);
        }
    }

    @Override
    protected void onViewsBound() {
        super.onViewsBound();
        mRefreshLayout.setOnRefreshListener(this);
        SpaceItemDecoration.apply(mRecyclerView, false, R.dimen.root_padding_half);
        mAdapter = new UsersAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mPaginator = new RecyclerViewPaginator(mRecyclerView, this);
    }

    @Override
    public void paginate() {
        mAdapter.setPaginating(true);
        Api.getFollowedUsers(mUsername, mFeed, new PaginateFollowingListener(this));
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

    private void showFollowing(final Feed feed) {
        mFeed = feed;
        mAdapter.set(mFeed);
        mEmpty.setVisibility(View.GONE);
        mError.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        mPaginator.setEnabled(feed.hasCursor());
        mRefreshLayout.setRefreshing(false);
    }


    private static class GetFollowingListener implements ApiResponse<Feed> {
        private final WeakReference<FollowingActivity> mActivityReference;

        private GetFollowingListener(final FollowingActivity activity) {
            mActivityReference = new WeakReference<>(activity);
        }

        @Override
        public void failure(@Nullable final ErrorInfo error) {
            final FollowingActivity activity = mActivityReference.get();

            if (activity != null && !activity.isDestroyed()) {
                activity.showError();
            }
        }

        @Override
        public void success(final Feed feed) {
            final FollowingActivity activity = mActivityReference.get();

            if (activity != null && !activity.isDestroyed()) {
                if (feed.hasUsers()) {
                    activity.showFollowing(feed);
                } else {
                    activity.showEmpty();
                }
            }
        }
    }

    private static class PaginateFollowingListener implements ApiResponse<Feed> {
        private final WeakReference<FollowingActivity> mActivityReference;
        private final int mUsersSize;

        private PaginateFollowingListener(final FollowingActivity activity) {
            mActivityReference = new WeakReference<>(activity);
            mUsersSize = activity.mFeed.getUsersSize();
        }

        @Override
        public void failure(@Nullable final ErrorInfo error) {
            final FollowingActivity activity = mActivityReference.get();

            if (activity != null && !activity.isDestroyed()) {
                activity.paginationNoMore();
            }
        }

        @Override
        public void success(final Feed feed) {
            final FollowingActivity activity = mActivityReference.get();

            if (activity != null && !activity.isDestroyed()) {
                if (feed.hasCursor() && feed.getUsersSize() > mUsersSize) {
                    activity.paginationComplete();
                } else {
                    activity.paginationNoMore();
                }
            }
        }
    }

}
