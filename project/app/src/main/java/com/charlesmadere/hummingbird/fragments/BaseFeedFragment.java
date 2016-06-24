package com.charlesmadere.hummingbird.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.FeedAdapter;
import com.charlesmadere.hummingbird.misc.MiscUtils;
import com.charlesmadere.hummingbird.models.ErrorInfo;
import com.charlesmadere.hummingbird.models.Feed;
import com.charlesmadere.hummingbird.models.FeedPost;
import com.charlesmadere.hummingbird.networking.Api;
import com.charlesmadere.hummingbird.networking.ApiResponse;
import com.charlesmadere.hummingbird.views.RecyclerViewPaginator;
import com.charlesmadere.hummingbird.views.RefreshLayout;
import com.charlesmadere.hummingbird.views.SpaceItemDecoration;

import java.lang.ref.WeakReference;

import butterknife.BindView;

public abstract class BaseFeedFragment extends BaseFragment implements
        RecyclerViewPaginator.Listeners, SwipeRefreshLayout.OnRefreshListener {

    protected static final String KEY_FEED = "Feed";
    protected static final String KEY_USERNAME = "Username";

    protected boolean mFetchingFeed;
    protected Feed mFeed;
    protected FeedAdapter mAdapter;
    protected Listener mListener;
    protected RecyclerViewPaginator mPaginator;
    protected String mUsername;

    @BindView(R.id.llEmpty)
    protected LinearLayout mEmpty;

    @BindView(R.id.llError)
    protected LinearLayout mError;

    @BindView(R.id.recyclerView)
    protected RecyclerView mRecyclerView;

    @BindView(R.id.refreshLayout)
    protected RefreshLayout mRefreshLayout;


    private void feedPostFailure() {
        new AlertDialog.Builder(getContext())
                .setMessage(R.string.error_posting_to_feed)
                .setNeutralButton(R.string.ok, null)
                .show();
    }

    protected void fetchFeed() {
        mFetchingFeed = true;
        mRefreshLayout.setRefreshing(true);
        mListener.onFeedBeganLoading();
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
        mListener = (Listener) MiscUtils.getActivity(context);
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Bundle args = getArguments();
        mUsername = args.getString(KEY_USERNAME);

        if (savedInstanceState != null && !savedInstanceState.isEmpty()) {
            mFeed = savedInstanceState.getParcelable(KEY_FEED);
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            final Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_feed, container, false);
    }

    @Override
    public void onRefresh() {
        fetchFeed();
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mFeed != null) {
            outState.putParcelable(KEY_FEED, mFeed);
        }
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRefreshLayout.setOnRefreshListener(this);
        SpaceItemDecoration.apply(mRecyclerView, false, R.dimen.root_padding);
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

    public void postToFeed(final FeedPost feedPost) {
        mRefreshLayout.setRefreshing(true);
        Api.postToFeed(feedPost, new FeedPostListener(this));
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
        mAdapter.set(feed);
        mEmpty.setVisibility(View.GONE);
        mError.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        mPaginator.setEnabled(feed.hasCursor());
        mRefreshLayout.setRefreshing(false);
        mFetchingFeed = false;
        mListener.onFeedFinishedLoading();
    }


    private static class FeedPostListener implements ApiResponse<Void> {
        private final WeakReference<BaseFeedFragment> mFragmentReference;

        private FeedPostListener(final BaseFeedFragment fragment) {
            mFragmentReference = new WeakReference<>(fragment);
        }

        @Override
        public void failure(@Nullable final ErrorInfo error) {
            final BaseFeedFragment fragment = mFragmentReference.get();

            if (fragment != null && !fragment.isDestroyed()) {
                fragment.feedPostFailure();
            }
        }

        @Override
        public void success(@Nullable final Void v) {
            final BaseFeedFragment fragment = mFragmentReference.get();

            if (fragment != null && !fragment.isDestroyed()) {
                fragment.fetchFeed();
            }
        }
    }

    protected static class GetFeedListener implements ApiResponse<Feed> {
        private final WeakReference<BaseFeedFragment> mFragmentReference;

        protected GetFeedListener(final BaseFeedFragment fragment) {
            mFragmentReference = new WeakReference<>(fragment);
        }

        @Override
        public void failure(@Nullable final ErrorInfo error) {
            final BaseFeedFragment fragment = mFragmentReference.get();

            if (fragment != null && !fragment.isDestroyed()) {
                fragment.showError();
            }
        }

        @Override
        public void success(final Feed feed) {
            final BaseFeedFragment fragment = mFragmentReference.get();

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
        private final WeakReference<BaseFeedFragment> mFragmentReference;
        private final int mStoriesSize;

        protected PaginateFeedListener(final BaseFeedFragment fragment) {
            mFragmentReference = new WeakReference<>(fragment);
            mStoriesSize = fragment.mFeed.getStories().size();
        }

        @Override
        public void failure(@Nullable final ErrorInfo error) {
            final BaseFeedFragment fragment = mFragmentReference.get();

            if (fragment != null && !fragment.isDestroyed()) {
                fragment.paginationNoMore();
            }
        }

        @Override
        public void success(final Feed feed) {
            final BaseFeedFragment fragment = mFragmentReference.get();

            if (fragment != null && !fragment.isDestroyed()) {
                if (feed.hasCursor() && feed.getStories().size() > mStoriesSize) {
                    fragment.paginationComplete();
                } else {
                    fragment.paginationNoMore();
                }
            }
        }
    }

    public interface Listener {
        void onFeedBeganLoading();
        void onFeedFinishedLoading();
    }

}
