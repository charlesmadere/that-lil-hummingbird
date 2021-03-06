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
import com.charlesmadere.hummingbird.networking.Api;
import com.charlesmadere.hummingbird.networking.ApiResponse;
import com.charlesmadere.hummingbird.views.RecyclerViewPaginator;
import com.charlesmadere.hummingbird.views.RefreshLayout;
import com.charlesmadere.hummingbird.views.SpaceItemDecoration;

import java.lang.ref.WeakReference;

import butterknife.BindView;

public class UserFeedFragment extends BaseUserFragment implements ObjectCache.KeyProvider,
        RecyclerViewPaginator.Listeners, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "UserFeedFragment";

    private boolean mFetchingFeed;
    private Feed mFeed;
    private FeedAdapter mAdapter;
    private FeedListeners mFeedListeners;
    private RecyclerViewPaginator mPaginator;

    @BindView(R.id.llEmpty)
    LinearLayout mEmpty;

    @BindView(R.id.llError)
    LinearLayout mError;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.refreshLayout)
    RefreshLayout mRefreshLayout;


    public static UserFeedFragment create() {
        return new UserFeedFragment();
    }

    public void fetchFeed() {
        mFetchingFeed = true;
        mFeedListeners.onFeedBeganLoading();
        mRefreshLayout.setRefreshing(true);
        Api.getUserStories(getUserId(), new GetFeedListener(this));
    }

    @Override
    public String getFragmentName() {
        return TAG;
    }

    @Override
    public String[] getObjectCacheKeys() {
        return new String[] { getFragmentName(), getUserId() };
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
        if (fragment instanceof FeedListeners) {
            mFeedListeners = (FeedListeners) fragment;
        } else {
            final Activity activity = MiscUtils.optActivity(context);

            if (activity instanceof FeedListeners) {
                mFeedListeners = (FeedListeners) activity;
            }
        }

        if (mFeedListeners == null) {
            throw new IllegalStateException(getFragmentName() + " must attach to FeedListeners");
        }
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
    }

    @Override
    public void paginate() {
        mAdapter.setPaginating(true);
        Api.getUserStories(getUserId(), mFeed, new PaginateFeedListener(this));
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
    }

    protected void showError() {
        mRecyclerView.setVisibility(View.GONE);
        mEmpty.setVisibility(View.GONE);
        mError.setVisibility(View.VISIBLE);
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

    @Override
    protected void showUserDigest(final UserDigest userDigest) {
        mFeed = ObjectCache.get(this);

        if (mFeed == null) {
            fetchFeed();
        } else {
            showFeed(mFeed);
        }
    }


    private static class GetFeedListener implements ApiResponse<Feed> {
        private final WeakReference<UserFeedFragment> mFragmentReference;

        private GetFeedListener(final UserFeedFragment fragment) {
            mFragmentReference = new WeakReference<>(fragment);
        }

        @Override
        public void failure(@Nullable final ErrorInfo error) {
            final UserFeedFragment fragment = mFragmentReference.get();

            if (fragment != null && fragment.isAlive()) {
                fragment.showError();
            }
        }

        @Override
        public void success(final Feed feed) {
            final UserFeedFragment fragment = mFragmentReference.get();

            if (fragment != null && fragment.isAlive()) {
                if (feed.hasStories()) {
                    fragment.showFeed(feed);
                } else {
                    fragment.showEmpty();
                }
            }
        }
    }

    private static class PaginateFeedListener implements ApiResponse<Feed> {
        private final WeakReference<UserFeedFragment> mFragmentReference;
        private final int mStoriesSize;

        private PaginateFeedListener(final UserFeedFragment fragment) {
            mFragmentReference = new WeakReference<>(fragment);
            mStoriesSize = fragment.mFeed.getStoriesSize();
        }

        @Override
        public void failure(@Nullable final ErrorInfo error) {
            final UserFeedFragment fragment = mFragmentReference.get();

            if (fragment != null && fragment.isAlive()) {
                fragment.paginationNoMore();
            }
        }

        @Override
        public void success(final Feed feed) {
            final UserFeedFragment fragment = mFragmentReference.get();

            if (fragment != null && fragment.isAlive()) {
                if (feed.hasCursor() && feed.getStoriesSize() > mStoriesSize) {
                    fragment.paginationComplete();
                } else {
                    fragment.paginationNoMore();
                }
            }
        }
    }

}
