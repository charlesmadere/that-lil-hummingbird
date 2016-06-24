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
import com.charlesmadere.hummingbird.adapters.UserAnimeReviewsAdapter;
import com.charlesmadere.hummingbird.models.ErrorInfo;
import com.charlesmadere.hummingbird.models.Feed;
import com.charlesmadere.hummingbird.networking.Api;
import com.charlesmadere.hummingbird.networking.ApiResponse;
import com.charlesmadere.hummingbird.views.RecyclerViewPaginator;
import com.charlesmadere.hummingbird.views.RefreshLayout;
import com.charlesmadere.hummingbird.views.SpaceItemDecoration;

import java.lang.ref.WeakReference;

import butterknife.BindView;

public class UserAnimeReviewsActivity extends BaseDrawerActivity implements
        RecyclerViewPaginator.Listeners, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "UserAnimeReviewsActivity";
    private static final String CNAME = UserAnimeReviewsActivity.class.getCanonicalName();
    private static final String EXTRA_USERNAME = CNAME + ".Username";
    private static final String KEY_FEED = "Feed";

    private Feed mFeed;
    private RecyclerViewPaginator mPaginator;
    private String mUsername;
    private UserAnimeReviewsAdapter mAdapter;

    @BindView(R.id.llEmpty)
    LinearLayout mEmpty;

    @BindView(R.id.llError)
    LinearLayout mError;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.refreshLayout)
    RefreshLayout mRefreshLayout;


    public static Intent getLaunchIntent(final Context context, final String username) {
        return new Intent(context, UserAnimeReviewsActivity.class)
                .putExtra(EXTRA_USERNAME, username);
    }

    private void fetchReviews() {
        mRefreshLayout.setRefreshing(true);
        Api.getUserReviews(mUsername, new GetUserReviewsListener(this));
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
        setContentView(R.layout.activity_user_anime_reviews);

        final Intent intent = getIntent();
        mUsername = intent.getStringExtra(EXTRA_USERNAME);
        getSupportActionBar().setSubtitle(mUsername);

        if (savedInstanceState != null && !savedInstanceState.isEmpty()) {
            mFeed = savedInstanceState.getParcelable(KEY_FEED);
        }

        if (mFeed == null) {
            fetchReviews();
        } else {
            showReviews(mFeed);
        }
    }

    @Override
    public void onRefresh() {
        fetchReviews();
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
        mAdapter = new UserAnimeReviewsAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mPaginator = new RecyclerViewPaginator(mRecyclerView, this);
    }

    @Override
    public void paginate() {
        mAdapter.setPaginating(true);
        Api.getUserReviews(mUsername, mFeed, new PaginateUserReviewsListener(this));
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

    private void showReviews(final Feed feed) {
        mFeed = feed;
        mAdapter.set(mFeed);
        mEmpty.setVisibility(View.GONE);
        mError.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        mPaginator.setEnabled(feed.hasCursor());
        mRefreshLayout.setRefreshing(false);
    }


    private static class GetUserReviewsListener implements ApiResponse<Feed> {
        private final WeakReference<UserAnimeReviewsActivity> mActivityReference;

        private GetUserReviewsListener(final UserAnimeReviewsActivity activity) {
            mActivityReference = new WeakReference<>(activity);
        }

        @Override
        public void failure(@Nullable final ErrorInfo error) {
            final UserAnimeReviewsActivity activity = mActivityReference.get();

            if (activity != null && !activity.isDestroyed()) {
                activity.showError();
            }
        }

        @Override
        public void success(final Feed feed) {
            final UserAnimeReviewsActivity activity = mActivityReference.get();

            if (activity != null && !activity.isDestroyed()) {
                if (feed.hasAnimeReviews()) {
                    activity.showReviews(feed);
                } else {
                    activity.showEmpty();
                }
            }
        }
    }

    private static class PaginateUserReviewsListener implements ApiResponse<Feed> {
        private final WeakReference<UserAnimeReviewsActivity> mActivityReference;
        private final int mReviewsSize;

        private PaginateUserReviewsListener(final UserAnimeReviewsActivity activity) {
            mActivityReference = new WeakReference<>(activity);
            mReviewsSize = activity.mFeed.getAnimeReviews().size();
        }

        @Override
        public void failure(@Nullable final ErrorInfo error) {
            final UserAnimeReviewsActivity activity = mActivityReference.get();

            if (activity != null && !activity.isDestroyed()) {
                activity.paginationNoMore();
            }
        }

        @Override
        public void success(final Feed feed) {
            final UserAnimeReviewsActivity activity = mActivityReference.get();

            if (activity != null && !activity.isDestroyed()) {
                if (feed.hasCursor() && feed.getAnimeReviews().size() > mReviewsSize) {
                    activity.paginationComplete();
                } else {
                    activity.paginationNoMore();
                }
            }
        }
    }

}
