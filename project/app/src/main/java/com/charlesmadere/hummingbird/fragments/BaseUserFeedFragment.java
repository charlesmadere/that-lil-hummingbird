package com.charlesmadere.hummingbird.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.FeedAdapter;
import com.charlesmadere.hummingbird.misc.FeedListeners;
import com.charlesmadere.hummingbird.misc.MiscUtils;
import com.charlesmadere.hummingbird.misc.ObjectCache;
import com.charlesmadere.hummingbird.models.ErrorInfo;
import com.charlesmadere.hummingbird.models.Feed;
import com.charlesmadere.hummingbird.models.UserDigest;
import com.charlesmadere.hummingbird.networking.ApiResponse;
import com.charlesmadere.hummingbird.views.RecyclerViewPaginator;
import com.charlesmadere.hummingbird.views.RefreshLayout;
import com.charlesmadere.hummingbird.views.SpaceItemDecoration;

import java.lang.ref.WeakReference;

import butterknife.BindView;

public abstract class BaseUserFeedFragment extends BaseFragment implements ObjectCache.KeyProvider,
        RecyclerViewPaginator.Listeners, SwipeRefreshLayout.OnRefreshListener {

    protected boolean mFetchingFeed;
    protected Feed mFeed;
    protected FeedAdapter mAdapter;
    protected FeedListeners mFeedListeners;
    protected Listener mListener;
    protected RecyclerViewPaginator mPaginator;

    @BindView(R.id.llEmpty)
    protected LinearLayout mEmpty;

    @BindView(R.id.llError)
    protected LinearLayout mError;

    @BindView(R.id.recyclerView)
    protected RecyclerView mRecyclerView;

    @BindView(R.id.refreshLayout)
    protected RefreshLayout mRefreshLayout;


    public void fetchFeed() {
        mFetchingFeed = true;
        mRefreshLayout.setRefreshing(true);
        mFeedListeners.onFeedBeganLoading();
    }

    @Override
    public String[] getObjectCacheKeys() {
        return new String[] { getFragmentName(), getUserDigest().getUserId() };
    }

    protected UserDigest getUserDigest() {
        return mListener.getUserDigest();
    }

    public boolean isFetchingFeed() {
        return mFetchingFeed;
    }

    @Override
    public boolean isLoading() {
        return mFetchingFeed || mRefreshLayout.isRefreshing() || mAdapter.isPaginating();
    }

    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);

        final Fragment fragment = getParentFragment();
        final Activity activity = MiscUtils.optActivity(context);

        if (fragment instanceof Listener) {
            mListener = (Listener) fragment;
        } else if (activity instanceof Listener) {
            mListener = (Listener) activity;
        }

        if (mListener == null) {
            throw new IllegalStateException(getFragmentName() + " must attach to Listener");
        }

        if (fragment instanceof FeedListeners) {
            mFeedListeners = (FeedListeners) fragment;
        } else if (activity instanceof FeedListeners) {
            mFeedListeners = (FeedListeners) activity;
        }

        if (mFeedListeners == null) {
            throw new IllegalStateException(getFragmentName() + " must attach to FeedListeners");
        }
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFeed = ObjectCache.get(this);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            final Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_user_feed, container, false);
    }

    @Override
    public void onRefresh() {
        fetchFeed();
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mFeed != null) {
            ObjectCache.put(mFeed, this);
        }
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRefreshLayout.setOnRefreshListener(this);
        mRecyclerView.setHasFixedSize(true);
        SpaceItemDecoration.apply(mRecyclerView, true, R.dimen.root_padding_half);
        mAdapter = new FeedAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);
        mPaginator = new RecyclerViewPaginator(mRecyclerView, this);

        if (mFeed == null) {
            fetchFeed();
        } else {
            showFeed(mFeed);
        }
    }

    @Override
    public void paginate() {
        mAdapter.setPaginating(true);
    }

    protected void paginationComplete() {
        mAdapter.set(mFeed);
        mAdapter.setPaginating(false);
    }

    protected void paginationNoMore() {
        mPaginator.setEnabled(false);
        mAdapter.setPaginating(false);
    }

    protected void showError() {
        mRecyclerView.setVisibility(View.GONE);
        mEmpty.setVisibility(View.GONE);
        mError.setVisibility(View.VISIBLE);
        mRefreshLayout.setRefreshing(false);
    }

    protected void showEmpty() {
        mRecyclerView.setVisibility(View.GONE);
        mError.setVisibility(View.GONE);
        mEmpty.setVisibility(View.VISIBLE);
        mRefreshLayout.setRefreshing(false);
    }

    protected void showFeed(final Feed feed) {
        mFeed = feed;
        mAdapter.set(mFeed);
        mEmpty.setVisibility(View.GONE);
        mError.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        mPaginator.setEnabled(mFeed.hasCursor());
        mRefreshLayout.setRefreshing(false);
        mFetchingFeed = false;
        mFeedListeners.onFeedFinishedLoading();
    }


    public interface Listener {
        UserDigest getUserDigest();
    }

    protected static class GetFeedListener implements ApiResponse<Feed> {
        private final WeakReference<BaseUserFeedFragment> mFragmentReference;

        protected GetFeedListener(final BaseUserFeedFragment fragment) {
            mFragmentReference = new WeakReference<>(fragment);
        }

        @Override
        public void failure(@Nullable final ErrorInfo error) {
            final BaseUserFeedFragment fragment = mFragmentReference.get();

            if (fragment != null && !fragment.isDestroyed()) {
                fragment.showError();
            }
        }

        @Override
        public void success(final Feed feed) {
            final BaseUserFeedFragment fragment = mFragmentReference.get();

            if (fragment != null && !fragment.isDestroyed()) {
                if (feed.hasStories()) {
                    fragment.showFeed(feed);
                } else {
                    fragment.showEmpty();
                }
            }
        }
    }

    protected static class PaginateFeedListener implements ApiResponse<Feed> {
        private final WeakReference<BaseUserFeedFragment> mFragmentReference;
        private final int mStoriesSize;

        protected PaginateFeedListener(final BaseUserFeedFragment fragment) {
            mFragmentReference = new WeakReference<>(fragment);
            mStoriesSize = fragment.mFeed.getStoriesSize();
        }

        @Override
        public void failure(@Nullable final ErrorInfo error) {
            final BaseUserFeedFragment fragment = mFragmentReference.get();

            if (fragment != null && !fragment.isDestroyed()) {
                fragment.paginationNoMore();
            }
        }

        @Override
        public void success(final Feed feed) {
            final BaseUserFeedFragment fragment = mFragmentReference.get();

            if (fragment != null && !fragment.isDestroyed()) {
                if (feed.hasCursor() && feed.getStoriesSize() > mStoriesSize) {
                    fragment.paginationComplete();
                } else {
                    fragment.paginationNoMore();
                }
            }
        }
    }

}
