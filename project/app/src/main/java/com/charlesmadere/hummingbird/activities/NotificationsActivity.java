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
import com.charlesmadere.hummingbird.adapters.NotificationsAdapter;
import com.charlesmadere.hummingbird.misc.NotificationManager;
import com.charlesmadere.hummingbird.misc.ObjectCache;
import com.charlesmadere.hummingbird.models.ErrorInfo;
import com.charlesmadere.hummingbird.models.Feed;
import com.charlesmadere.hummingbird.networking.Api;
import com.charlesmadere.hummingbird.networking.ApiResponse;
import com.charlesmadere.hummingbird.views.DividerItemDecoration;
import com.charlesmadere.hummingbird.views.NavigationDrawerItemView;
import com.charlesmadere.hummingbird.views.RecyclerViewPaginator;
import com.charlesmadere.hummingbird.views.RefreshLayout;

import java.lang.ref.WeakReference;

import butterknife.BindView;

public class NotificationsActivity extends BaseDrawerActivity implements ObjectCache.KeyProvider,
        RecyclerViewPaginator.Listeners, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "NotificationsActivity";

    private Feed mFeed;
    private NotificationsAdapter mAdapter;
    private RecyclerViewPaginator mPaginator;

    @BindView(R.id.llEmpty)
    LinearLayout mEmpty;

    @BindView(R.id.llError)
    LinearLayout mError;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.refreshLayout)
    RefreshLayout mRefreshLayout;


    public static Intent getLaunchIntent(final Context context) {
        return new Intent(context, NotificationsActivity.class);
    }

    private void fetchNotifications() {
        mRefreshLayout.setRefreshing(true);
        Api.getNotifications(new GetNotificationsListener(this));
        NotificationManager.cancelAll();
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
        return NavigationDrawerItemView.Entry.NOTIFICATIONS;
    }

    @Override
    public boolean isLoading() {
        return mRefreshLayout.isRefreshing() || mAdapter.isPaginating();
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        mFeed = ObjectCache.get(this);

        if (mFeed == null) {
            fetchNotifications();
        } else {
            showFeed(mFeed);
        }
    }

    @Override
    protected void onNewIntent(final Intent intent) {
        super.onNewIntent(intent);
        fetchNotifications();
    }

    @Override
    public void onRefresh() {
        fetchNotifications();
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
        mAdapter = new NotificationsAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mPaginator = new RecyclerViewPaginator(mRecyclerView, this);
    }

    @Override
    public void paginate() {
        mAdapter.setPaginating(true);
        Api.getNotifications(mFeed, new PaginateNotificationsListener(this));
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

    private void showFeed(final Feed feed) {
        mFeed = feed;
        mAdapter.set(mFeed);
        mEmpty.setVisibility(View.GONE);
        mError.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        mRefreshLayout.setRefreshing(false);
        mPaginator.setEnabled(mFeed.hasCursor());
    }


    private static class GetNotificationsListener implements ApiResponse<Feed> {
        private final WeakReference<NotificationsActivity> mActivityReference;

        private GetNotificationsListener(final NotificationsActivity activity) {
            mActivityReference = new WeakReference<>(activity);
        }

        @Override
        public void failure(@Nullable final ErrorInfo error) {
            final NotificationsActivity activity = mActivityReference.get();

            if (activity != null && !activity.isDestroyed()) {
                activity.showError();
            }
        }

        @Override
        public void success(final Feed feed) {
            final NotificationsActivity activity = mActivityReference.get();

            if (activity != null && !activity.isDestroyed()) {
                if (feed.hasStories()) {
                    activity.showFeed(feed);
                } else {
                    activity.showEmpty();
                }
            }
        }
    }

    private static class PaginateNotificationsListener implements ApiResponse<Feed> {
        private final WeakReference<NotificationsActivity> mActivityReference;
        private final int mNotificationsSize;

        private PaginateNotificationsListener(final NotificationsActivity activity) {
            mActivityReference = new WeakReference<>(activity);
            mNotificationsSize = activity.mFeed.getNotificationsSize();
        }

        @Override
        public void failure(@Nullable final ErrorInfo error) {
            final NotificationsActivity activity = mActivityReference.get();

            if (activity != null && !activity.isDestroyed()) {
                activity.paginationNoMore();
            }
        }

        @Override
        public void success(final Feed feed) {
            final NotificationsActivity activity = mActivityReference.get();

            if (activity != null && !activity.isDestroyed()) {
                if (feed.hasCursor() && feed.getNotificationsSize() > mNotificationsSize) {
                    activity.paginationComplete();
                } else {
                    activity.paginationNoMore();
                }
            }
        }
    }

}
