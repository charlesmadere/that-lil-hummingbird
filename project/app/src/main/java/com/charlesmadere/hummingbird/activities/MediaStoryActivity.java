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
import com.charlesmadere.hummingbird.adapters.MediaStoryAdapter;
import com.charlesmadere.hummingbird.models.ErrorInfo;
import com.charlesmadere.hummingbird.models.Feed;
import com.charlesmadere.hummingbird.models.MediaStory;
import com.charlesmadere.hummingbird.networking.Api;
import com.charlesmadere.hummingbird.networking.ApiResponse;
import com.charlesmadere.hummingbird.views.RecyclerViewPaginator;
import com.charlesmadere.hummingbird.views.RefreshLayout;
import com.charlesmadere.hummingbird.views.SpaceItemDecoration;

import java.lang.ref.WeakReference;

import butterknife.BindView;

public class MediaStoryActivity extends BaseDrawerActivity implements
        RecyclerViewPaginator.Listeners, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "MediaStoryActivity";
    private static final String CNAME = MediaStoryActivity.class.getCanonicalName();
    private static final String EXTRA_MEDIA_STORY = CNAME + ".MediaStory";
    private static final String KEY_FEED = "Feed";

    private Feed mFeed;
    private MediaStory mMediaStory;
    private MediaStoryAdapter mAdapter;
    private RecyclerViewPaginator mPaginator;

    @BindView(R.id.llError)
    LinearLayout mError;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.refreshLayout)
    RefreshLayout mRefreshLayout;


    public static Intent getLaunchIntent(final Context context, final MediaStory mediaStory) {
        return new Intent(context, MediaStoryActivity.class)
                .putExtra(EXTRA_MEDIA_STORY, mediaStory);
    }

    private void fetchFeed() {
        mRefreshLayout.setRefreshing(true);
        Api.getSubstories(mMediaStory.getId(), new GetSubstoriesListener(this));
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
        setContentView(R.layout.activity_media_story);

        final Intent intent = getIntent();
        mMediaStory = intent.getParcelableExtra(EXTRA_MEDIA_STORY);

        if (savedInstanceState != null && !savedInstanceState.isEmpty()) {
            mFeed = savedInstanceState.getParcelable(KEY_FEED);
        }

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
            outState.putParcelable(KEY_FEED, mFeed);
        }
    }

    @Override
    protected void onViewsBound() {
        super.onViewsBound();
        mRefreshLayout.setOnRefreshListener(this);
        SpaceItemDecoration.apply(mRecyclerView, false, R.dimen.root_padding_half);
        mAdapter = new MediaStoryAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mPaginator = new RecyclerViewPaginator(mRecyclerView, this);
    }

    @Override
    public void paginate() {
        mAdapter.setPaginating(true);
        Api.getSubstories(mMediaStory.getId(), mFeed, new PaginateFeedListener(this));
    }

    private void paginationComplete() {
        mAdapter.set(mMediaStory, mFeed);
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
        mAdapter.set(mMediaStory, mFeed);
        mError.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        mPaginator.setEnabled(feed.hasCursor());
        mRefreshLayout.setRefreshing(false);
    }


    private static class GetSubstoriesListener implements ApiResponse<Feed> {
        private final WeakReference<MediaStoryActivity> mActivityReference;

        private GetSubstoriesListener(final MediaStoryActivity activity) {
            mActivityReference = new WeakReference<>(activity);
        }

        @Override
        public void failure(@Nullable final ErrorInfo error) {
            final MediaStoryActivity activity = mActivityReference.get();

            if (activity != null && !activity.isDestroyed()) {
                activity.showError();
            }
        }

        @Override
        public void success(final Feed feed) {
            final MediaStoryActivity activity = mActivityReference.get();

            if (activity != null && !activity.isDestroyed()) {
                activity.showFeed(feed);
            }
        }
    }

    private static class PaginateFeedListener implements ApiResponse<Feed> {
        private final WeakReference<MediaStoryActivity> mActivityReference;
        private final int mSubstoriesSize;

        private PaginateFeedListener(final MediaStoryActivity activity) {
            mActivityReference = new WeakReference<>(activity);
            mSubstoriesSize = activity.mFeed.getSubstoriesSize();
        }

        @Override
        public void failure(@Nullable final ErrorInfo error) {
            final MediaStoryActivity activity = mActivityReference.get();

            if (activity != null && !activity.isDestroyed()) {
                activity.paginationNoMore();
            }
        }

        @Override
        public void success(final Feed feed) {
            final MediaStoryActivity activity = mActivityReference.get();

            if (activity != null && !activity.isDestroyed()) {
                if (feed.getSubstoriesSize() > mSubstoriesSize) {
                    activity.paginationComplete();
                } else {
                    activity.paginationNoMore();
                }
            }
        }
    }

}
