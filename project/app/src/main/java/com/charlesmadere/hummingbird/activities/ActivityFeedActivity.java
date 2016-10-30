package com.charlesmadere.hummingbird.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.FeedAdapter;
import com.charlesmadere.hummingbird.fragments.FeedPostFragment;
import com.charlesmadere.hummingbird.misc.CurrentUser;
import com.charlesmadere.hummingbird.misc.ObjectCache;
import com.charlesmadere.hummingbird.misc.SyncManager;
import com.charlesmadere.hummingbird.models.ErrorInfo;
import com.charlesmadere.hummingbird.models.Feed;
import com.charlesmadere.hummingbird.models.FeedPost;
import com.charlesmadere.hummingbird.models.LaunchScreen;
import com.charlesmadere.hummingbird.networking.Api;
import com.charlesmadere.hummingbird.networking.ApiResponse;
import com.charlesmadere.hummingbird.preferences.Preferences;
import com.charlesmadere.hummingbird.views.NavigationDrawerItemView;
import com.charlesmadere.hummingbird.views.RecyclerViewPaginator;
import com.charlesmadere.hummingbird.views.RefreshLayout;
import com.charlesmadere.hummingbird.views.SpaceItemDecoration;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.OnClick;

public class ActivityFeedActivity extends BaseDrawerActivity implements FeedPostFragment.Listener,
        ObjectCache.KeyProvider, RecyclerViewPaginator.Listeners, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "ActivityFeedActivity";

    private Feed mFeed;
    private FeedAdapter mAdapter;
    private RecyclerViewPaginator mPaginator;

    @BindView(R.id.floatingActionButton)
    FloatingActionButton mPostToFeed;

    @BindView(R.id.llEmpty)
    LinearLayout mEmpty;

    @BindView(R.id.llError)
    LinearLayout mError;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.refreshLayout)
    RefreshLayout mRefreshLayout;


    static {
        SyncManager.enableOrDisable();
    }

    public static Intent getLaunchIntent(final Context context) {
        final Intent intent = createDrawerActivityIntent(context, ActivityFeedActivity.class);

        if (Preferences.General.DefaultLaunchScreen.get() == LaunchScreen.ACTIVITY_FEED) {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }

        return intent;
    }

    private void feedPostFailure() {
        new AlertDialog.Builder(this)
                .setMessage(R.string.error_posting_to_feed)
                .setNeutralButton(R.string.ok, null)
                .show();
    }

    private void fetchFeed() {
        mRefreshLayout.setRefreshing(true);
        Api.getNewsFeed(new GetFeedListener(this));
        mPostToFeed.hide();
    }

    @Override
    public String getActivityName() {
        return TAG;
    }

    @Override
    public String[] getObjectCacheKeys() {
        return new String[] { getActivityName() };
    }

    @Override
    protected NavigationDrawerItemView.Entry getSelectedNavigationDrawerItemViewEntry() {
        return NavigationDrawerItemView.Entry.ACTIVITY_FEED;
    }

    @Override
    public boolean isLoading() {
        return mRefreshLayout.isRefreshing() || mAdapter.isPaginating();
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_activity);

        mFeed = ObjectCache.get(this);

        if (mFeed == null) {
            fetchFeed();
        } else {
            showFeed(mFeed);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.activity_feed_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onFeedPostSubmit() {
        final FeedPostFragment fragment = (FeedPostFragment) getSupportFragmentManager()
                .findFragmentByTag(FeedPostFragment.TAG);
        final FeedPost post = fragment.getFeedPost(CurrentUser.get().getUserId());

        if (post == null) {
            return;
        }

        Api.postToFeed(post, new FeedPostListener(this));
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.miAnimeLibrary:
                startActivity(CurrentUserAnimeLibraryActivity.getLaunchIntent(this));
                return true;

            case R.id.miMangaLibrary:
                startActivity(CurrentUserMangaLibraryActivity.getLaunchIntent(this));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.floatingActionButton)
    void onPostToFeedClick() {
        FeedPostFragment.create().show(getSupportFragmentManager(), FeedPostFragment.TAG);
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
        SpaceItemDecoration.apply(mRecyclerView, true, R.dimen.root_padding_half);
        mAdapter = new FeedAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mPaginator = new RecyclerViewPaginator(mRecyclerView, this);
    }

    @Override
    public void paginate() {
        mAdapter.setPaginating(true);
        Api.getNewsFeed(mFeed, new PaginateFeedListener(this));
    }

    protected void paginationComplete() {
        mAdapter.set(mFeed);
        mAdapter.setPaginating(false);
    }

    protected void paginationNoMore() {
        mPaginator.setEnabled(false);
        mAdapter.setPaginating(false);
    }

    protected void showEmpty() {
        mRecyclerView.setVisibility(View.GONE);
        mError.setVisibility(View.GONE);
        mEmpty.setVisibility(View.VISIBLE);
        mRefreshLayout.setRefreshing(false);
        mPostToFeed.hide();
    }

    protected void showError() {
        mRecyclerView.setVisibility(View.GONE);
        mEmpty.setVisibility(View.GONE);
        mError.setVisibility(View.VISIBLE);
        mRefreshLayout.setRefreshing(false);
        mPostToFeed.hide();
    }

    private void showFeed(final Feed feed) {
        mFeed = feed;
        mAdapter.set(mFeed);
        mEmpty.setVisibility(View.GONE);
        mError.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        mPaginator.setEnabled(mFeed.hasCursor());
        mRefreshLayout.setRefreshing(false);
        mPostToFeed.show();
    }


    private static class FeedPostListener implements ApiResponse<Void> {
        private final WeakReference<ActivityFeedActivity> mActivityReference;

        private FeedPostListener(final ActivityFeedActivity activity) {
            mActivityReference = new WeakReference<>(activity);
        }

        @Override
        public void failure(@Nullable final ErrorInfo error) {
            final ActivityFeedActivity activity = mActivityReference.get();

            if (activity != null && activity.isAlive()) {
                activity.feedPostFailure();
            }
        }

        @Override
        public void success(@Nullable final Void object) {
            final ActivityFeedActivity activity = mActivityReference.get();

            if (activity != null && activity.isAlive()) {
                activity.fetchFeed();
            }
        }
    }

    private static class GetFeedListener implements ApiResponse<Feed> {
        private final WeakReference<ActivityFeedActivity> mActivityReference;

        private GetFeedListener(final ActivityFeedActivity activity) {
            mActivityReference = new WeakReference<>(activity);
        }

        @Override
        public void failure(@Nullable final ErrorInfo error) {
            final ActivityFeedActivity activity = mActivityReference.get();

            if (activity != null && activity.isAlive()) {
                activity.showError();
            }
        }

        @Override
        public void success(final Feed feed) {
            final ActivityFeedActivity activity = mActivityReference.get();

            if (activity != null && activity.isAlive()) {
                if (feed.hasStories()) {
                    activity.showFeed(feed);
                } else {
                    activity.showEmpty();
                }
            }
        }
    }

    private static class PaginateFeedListener implements ApiResponse<Feed> {
        private final WeakReference<ActivityFeedActivity> mActivityReference;
        private final int mStoriesSize;

        private PaginateFeedListener(final ActivityFeedActivity activity) {
            mActivityReference = new WeakReference<>(activity);
            mStoriesSize = activity.mFeed.getStoriesSize();
        }

        @Override
        public void failure(@Nullable final ErrorInfo error) {
            final ActivityFeedActivity activity = mActivityReference.get();

            if (activity != null && activity.isAlive()) {
                activity.paginationNoMore();
            }
        }

        @Override
        public void success(final Feed feed) {
            final ActivityFeedActivity activity = mActivityReference.get();

            if (activity != null && activity.isAlive()) {
                if (feed.hasCursor() && feed.getStoriesSize() > mStoriesSize) {
                    activity.paginationComplete();
                } else {
                    activity.paginationNoMore();
                }
            }
        }
    }

}
