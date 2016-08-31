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
import com.charlesmadere.hummingbird.networking.Api;
import com.charlesmadere.hummingbird.networking.ApiResponse;
import com.charlesmadere.hummingbird.views.RecyclerViewPaginator;
import com.charlesmadere.hummingbird.views.RefreshLayout;
import com.charlesmadere.hummingbird.views.SpaceItemDecoration;

import java.lang.ref.WeakReference;

import butterknife.BindView;

public class GroupFeedFragment extends BaseGroupFragment implements ObjectCache.KeyProvider,
        RecyclerViewPaginator.Listeners, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "GroupFeedFragment";

    private boolean mFetchingGroupStories;
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


    public static GroupFeedFragment create() {
        return new GroupFeedFragment();
    }

    public void fetchGroupStories() {
        mFetchingGroupStories = true;
        mFeedListeners.onFeedBeganLoading();
        mRefreshLayout.setRefreshing(true);
        Api.getGroupStories(getGroupDigest().getId(), new GetGroupStoriesListener(this));
    }

    @Override
    public String[] getObjectCacheKeys() {
        return new String[] { getFragmentName(), getGroupDigest().getId() };
    }

    @Override
    public String getFragmentName() {
        return TAG;
    }

    public boolean isFetchingGroupStories() {
        return mFetchingGroupStories;
    }

    @Override
    public boolean isLoading() {
        return mFetchingGroupStories || mRefreshLayout.isRefreshing() || mAdapter.isPaginating();
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mRefreshLayout.setOnRefreshListener(this);
        mRecyclerView.setHasFixedSize(true);
        SpaceItemDecoration.apply(mRecyclerView, true, R.dimen.root_padding_half);
        mAdapter = new FeedAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);
        mPaginator = new RecyclerViewPaginator(mRecyclerView, this);

        mFeed = ObjectCache.get(this);

        if (mFeed == null) {
            fetchGroupStories();
        } else {
            showGroupStories(mFeed);
        }
    }

    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);

        final Fragment fragment = getParentFragment();
        final Activity activity = MiscUtils.optActivity(context);

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
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            final Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_user_feed, container, false);
    }

    @Override
    public void onRefresh() {
        fetchGroupStories();
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mFeed != null) {
            ObjectCache.put(mFeed, this);
        }
    }

    @Override
    public void paginate() {
        mAdapter.setPaginating(true);
        Api.getGroupStories(getGroupDigest().getId(), mFeed,
                new PaginateGroupStoriesListener(this));
    }

    private void paginationComplete() {
        mAdapter.set(mFeed);
        mAdapter.setPaginating(false);
    }

    private void paginationNoMore() {
        mPaginator.setEnabled(false);
        mAdapter.setPaginating(false);
    }

    private void showError() {
        mRecyclerView.setVisibility(View.GONE);
        mEmpty.setVisibility(View.GONE);
        mError.setVisibility(View.VISIBLE);
        mRefreshLayout.setRefreshing(false);
    }

    private void showEmpty() {
        mRecyclerView.setVisibility(View.GONE);
        mError.setVisibility(View.GONE);
        mEmpty.setVisibility(View.VISIBLE);
        mRefreshLayout.setRefreshing(false);
    }

    private void showGroupStories(final Feed feed) {
        mFeed = feed;
        mAdapter.set(mFeed);
        mEmpty.setVisibility(View.GONE);
        mError.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        mPaginator.setEnabled(mFeed.hasCursor());
        mRefreshLayout.setRefreshing(false);
        mFetchingGroupStories = false;
        mFeedListeners.onFeedFinishedLoading();
    }


    private static class GetGroupStoriesListener implements ApiResponse<Feed> {
        private final WeakReference<GroupFeedFragment> mFragmentReference;

        private GetGroupStoriesListener(final GroupFeedFragment fragment) {
            mFragmentReference = new WeakReference<>(fragment);
        }

        @Override
        public void failure(@Nullable final ErrorInfo error) {
            final GroupFeedFragment fragment = mFragmentReference.get();

            if (fragment != null && fragment.isAlive()) {
                fragment.showError();
            }
        }

        @Override
        public void success(final Feed feed) {
            final GroupFeedFragment fragment = mFragmentReference.get();

            if (fragment != null && fragment.isAlive()) {
                if (feed.hasStories()) {
                    fragment.showGroupStories(feed);
                } else {
                    fragment.showEmpty();
                }
            }
        }
    }

    private static class PaginateGroupStoriesListener implements ApiResponse<Feed> {
        private final WeakReference<GroupFeedFragment> mFragmentReference;
        private final int mStoriesSize;

        private PaginateGroupStoriesListener(final GroupFeedFragment fragment) {
            mFragmentReference = new WeakReference<>(fragment);
            mStoriesSize = fragment.mFeed.getStoriesSize();
        }

        @Override
        public void failure(@Nullable final ErrorInfo error) {
            final GroupFeedFragment fragment = mFragmentReference.get();

            if (fragment != null && fragment.isAlive()) {
                fragment.paginationNoMore();
            }
        }

        @Override
        public void success(final Feed feed) {
            final GroupFeedFragment fragment = mFragmentReference.get();

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
