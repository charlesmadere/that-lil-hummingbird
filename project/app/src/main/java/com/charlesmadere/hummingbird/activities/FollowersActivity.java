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
import com.charlesmadere.hummingbird.misc.ObjectCache;
import com.charlesmadere.hummingbird.models.ErrorInfo;
import com.charlesmadere.hummingbird.models.Feed;
import com.charlesmadere.hummingbird.models.UiColorSet;
import com.charlesmadere.hummingbird.networking.Api;
import com.charlesmadere.hummingbird.networking.ApiResponse;
import com.charlesmadere.hummingbird.views.DividerItemDecoration;
import com.charlesmadere.hummingbird.views.RecyclerViewPaginator;
import com.charlesmadere.hummingbird.views.RefreshLayout;

import java.lang.ref.WeakReference;

import butterknife.BindView;

public class FollowersActivity extends BaseDrawerActivity implements ObjectCache.KeyProvider,
        RecyclerViewPaginator.Listeners, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "FollowersActivity";
    private static final String CNAME = FollowersActivity.class.getCanonicalName();
    private static final String EXTRA_USERNAME = CNAME + ".Username";

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
        return getLaunchIntent(context, username, null);
    }

    public static Intent getLaunchIntent(final Context context, final String username,
            @Nullable final UiColorSet uiColorSet) {
        final Intent intent = new Intent(context, FollowersActivity.class)
                .putExtra(EXTRA_USERNAME, username);

        if (uiColorSet != null) {
            intent.putExtra(EXTRA_UI_COLOR_SET, uiColorSet);
        }

        return intent;
    }

    private void fetchFollowers() {
        mRefreshLayout.setRefreshing(true);
        Api.getFollowingUsers(mUsername, new GetFollowersListener(this));
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
        setContentView(R.layout.activity_followers);

        final Intent intent = getIntent();
        mUsername = intent.getStringExtra(EXTRA_USERNAME);
        setSubtitle(mUsername);

        mFeed = ObjectCache.get(this);

        if (mFeed == null) {
            fetchFollowers();
        } else {
            showFollowers(mFeed);
        }
    }

    @Override
    public void onRefresh() {
        fetchFollowers();
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
        mAdapter = new UsersAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mPaginator = new RecyclerViewPaginator(mRecyclerView, this);
    }

    @Override
    public void paginate() {
        mAdapter.setPaginating(true);
        Api.getFollowingUsers(mUsername, mFeed, new PaginateFollowersListener(this));
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

    private void showFollowers(final Feed feed) {
        mFeed = feed;
        mAdapter.set(mFeed);
        mEmpty.setVisibility(View.GONE);
        mError.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        mPaginator.setEnabled(feed.hasCursor());
        mRefreshLayout.setRefreshing(false);
    }


    private static class GetFollowersListener implements ApiResponse<Feed> {
        private final WeakReference<FollowersActivity> mActivityReference;

        private GetFollowersListener(final FollowersActivity activity) {
            mActivityReference = new WeakReference<>(activity);
        }

        @Override
        public void failure(@Nullable final ErrorInfo error) {
            final FollowersActivity activity = mActivityReference.get();

            if (activity != null && activity.isAlive()) {
                activity.showError();
            }
        }

        @Override
        public void success(final Feed feed) {
            final FollowersActivity activity = mActivityReference.get();

            if (activity != null && activity.isAlive()) {
                if (feed.hasUsers()) {
                    activity.showFollowers(feed);
                } else {
                    activity.showEmpty();
                }
            }
        }
    }

    private static class PaginateFollowersListener implements ApiResponse<Feed> {
        private final WeakReference<FollowersActivity> mActivityReference;
        private final int mUsersSize;

        private PaginateFollowersListener(final FollowersActivity activity) {
            mActivityReference = new WeakReference<>(activity);
            mUsersSize = activity.mFeed.getUsersSize();
        }

        @Override
        public void failure(@Nullable final ErrorInfo error) {
            final FollowersActivity activity = mActivityReference.get();

            if (activity != null && activity.isAlive()) {
                activity.paginationNoMore();
            }
        }

        @Override
        public void success(final Feed feed) {
            final FollowersActivity activity = mActivityReference.get();

            if (activity != null && activity.isAlive()) {
                if (feed.hasCursor() && feed.getUsersSize() > mUsersSize) {
                    activity.paginationComplete();
                } else {
                    activity.paginationNoMore();
                }
            }
        }
    }

}
