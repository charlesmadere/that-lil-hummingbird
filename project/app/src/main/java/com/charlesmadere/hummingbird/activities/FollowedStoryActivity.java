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
import com.charlesmadere.hummingbird.adapters.FollowedStoryAdapter;
import com.charlesmadere.hummingbird.misc.ObjectCache;
import com.charlesmadere.hummingbird.models.ErrorInfo;
import com.charlesmadere.hummingbird.models.Feed;
import com.charlesmadere.hummingbird.models.FollowedStory;
import com.charlesmadere.hummingbird.models.UiColorSet;
import com.charlesmadere.hummingbird.networking.Api;
import com.charlesmadere.hummingbird.networking.ApiResponse;
import com.charlesmadere.hummingbird.views.RecyclerViewPaginator;
import com.charlesmadere.hummingbird.views.RefreshLayout;

import java.lang.ref.WeakReference;

import butterknife.BindView;

public class FollowedStoryActivity extends BaseDrawerActivity implements ObjectCache.KeyProvider,
        RecyclerViewPaginator.Listeners, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "FollowedStoryActivity";
    private static final String CNAME = FollowedStoryActivity.class.getCanonicalName();
    private static final String EXTRA_FOLLOWED_STORY = CNAME + ".FollowedStory";

    private Feed mFeed;
    private FollowedStory mFollowedStory;
    private FollowedStoryAdapter mAdapter;
    private RecyclerViewPaginator mPaginator;

    @BindView(R.id.llError)
    LinearLayout mError;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.refreshLayout)
    RefreshLayout mRefreshLayout;


    public static Intent getLaunchIntent(final Context context, final FollowedStory followedStory) {
        return getLaunchIntent(context, followedStory, null);
    }

    public static Intent getLaunchIntent(final Context context, final FollowedStory followedStory,
            @Nullable final UiColorSet uiColorSet) {
        final Intent intent = new Intent(context, FollowedStoryActivity.class)
                .putExtra(EXTRA_FOLLOWED_STORY, followedStory);

        if (uiColorSet != null) {
            intent.putExtra(EXTRA_UI_COLOR_SET, uiColorSet);
        }

        return intent;
    }

    private void fetchFeed() {
        mRefreshLayout.setRefreshing(true);
        Api.getSubstories(mFollowedStory.getId(), new GetSubstoriesListener(this));
    }

    @Override
    public String getActivityName() {
        return TAG;
    }

    @Override
    public String[] getObjectCacheKeys() {
        return new String[] { getActivityName(), mFollowedStory.getId() };
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
        setContentView(R.layout.activity_followed_story);

        final Intent intent = getIntent();
        mFollowedStory = intent.getParcelableExtra(EXTRA_FOLLOWED_STORY);

        mFeed = ObjectCache.get(this);

        if (mFeed == null) {
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

        if (mFeed != null && mFeed.hasSubstories()) {
            ObjectCache.put(mFeed, this);
        }
    }

    @Override
    protected void onViewsBound() {
        super.onViewsBound();
        mRefreshLayout.setOnRefreshListener(this);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new FollowedStoryAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mPaginator = new RecyclerViewPaginator(mRecyclerView, this);
    }

    @Override
    public void paginate() {
        mAdapter.setPaginating(true);
        Api.getSubstories(mFollowedStory.getId(), mFeed, new PaginateFeedListener(this));
    }

    private void paginationComplete() {
        mAdapter.set(mFollowedStory, mFeed);
        mAdapter.setPaginating(false);
    }

    private void paginationNoMore() {
        mPaginator.setEnabled(false);
        mAdapter.setPaginating(false);
    }

    private void showError() {
        mRecyclerView.setVisibility(View.GONE);
        mError.setVisibility(View.VISIBLE);
        mRefreshLayout.setRefreshing(false);
    }

    private void showFeed(final Feed feed) {
        mFeed = feed;
        mAdapter.set(mFollowedStory, mFeed);
        mError.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        mPaginator.setEnabled(feed.hasCursor());
        mRefreshLayout.setRefreshing(false);
    }


    private static class GetSubstoriesListener implements ApiResponse<Feed> {
        private final WeakReference<FollowedStoryActivity> mActivityReference;

        private GetSubstoriesListener(final FollowedStoryActivity activity) {
            mActivityReference = new WeakReference<>(activity);
        }

        @Override
        public void failure(@Nullable final ErrorInfo error) {
            final FollowedStoryActivity activity = mActivityReference.get();

            if (activity != null && activity.isAlive()) {
                activity.showError();
            }
        }

        @Override
        public void success(final Feed feed) {
            final FollowedStoryActivity activity = mActivityReference.get();

            if (activity != null && activity.isAlive()) {
                activity.showFeed(feed);
            }
        }
    }

    private static class PaginateFeedListener implements ApiResponse<Feed> {
        private final WeakReference<FollowedStoryActivity> mActivityReference;
        private final int mSubstoriesSize;

        private PaginateFeedListener(final FollowedStoryActivity activity) {
            mActivityReference = new WeakReference<>(activity);
            mSubstoriesSize = activity.mFeed.getSubstoriesSize();
        }

        @Override
        public void failure(@Nullable final ErrorInfo error) {
            final FollowedStoryActivity activity = mActivityReference.get();

            if (activity != null && activity.isAlive()) {
                activity.paginationNoMore();
            }
        }

        @Override
        public void success(final Feed feed) {
            final FollowedStoryActivity activity = mActivityReference.get();

            if (activity != null && activity.isAlive()) {
                if (feed.hasCursor() && feed.getSubstoriesSize() > mSubstoriesSize) {
                    activity.paginationComplete();
                } else {
                    activity.paginationNoMore();
                }
            }
        }
    }

}
