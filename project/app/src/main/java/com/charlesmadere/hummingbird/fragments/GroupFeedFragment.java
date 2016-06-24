package com.charlesmadere.hummingbird.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.FeedAdapter;
import com.charlesmadere.hummingbird.models.ErrorInfo;
import com.charlesmadere.hummingbird.models.Feed;
import com.charlesmadere.hummingbird.networking.Api;
import com.charlesmadere.hummingbird.networking.ApiResponse;
import com.charlesmadere.hummingbird.views.RecyclerViewPaginator;
import com.charlesmadere.hummingbird.views.RefreshLayout;
import com.charlesmadere.hummingbird.views.SpaceItemDecoration;

import java.lang.ref.WeakReference;

import butterknife.BindView;

public class GroupFeedFragment extends BaseFragment implements
        RecyclerViewPaginator.Listeners, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "GroupFeedFragment";
    private static final String KEY_FEED = "Feed";
    private static final String KEY_GROUP_ID = "GroupId";

    private Feed mFeed;
    private FeedAdapter mAdapter;
    private RecyclerViewPaginator mPaginator;
    private String mGroupId;

    @BindView(R.id.llEmpty)
    LinearLayout mEmpty;

    @BindView(R.id.llError)
    LinearLayout mError;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.refreshLayout)
    RefreshLayout mRefreshLayout;


    public static GroupFeedFragment create(final String groupId) {
        final Bundle args = new Bundle(1);
        args.putString(KEY_GROUP_ID, groupId);

        final GroupFeedFragment fragment = new GroupFeedFragment();
        fragment.setArguments(args);

        return fragment;
    }

    private void fetchGroupStories() {
        mRefreshLayout.setRefreshing(true);
        Api.getGroupStories(mGroupId, new GetGroupStoriesListener(this));
    }

    @Override
    public String getFragmentName() {
        return TAG;
    }

    @Override
    public boolean isLoading() {
        return mRefreshLayout.isRefreshing() || mAdapter.isPaginating();
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Bundle args = getArguments();
        mGroupId = args.getString(KEY_GROUP_ID);

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
        fetchGroupStories();
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
            fetchGroupStories();
        } else {
            showGroupStories(mFeed);
        }
    }

    @Override
    public void paginate() {
        mAdapter.setPaginating(true);
        Api.getGroupStories(mGroupId, mFeed, new PaginateGroupStoriesListener(this));
    }

    protected void paginationComplete() {
        mAdapter.set(mFeed);
        mAdapter.setPaginating(false);
    }

    protected void paginationNoMore() {
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
        mAdapter.set(feed);
        mEmpty.setVisibility(View.GONE);
        mError.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        mPaginator.setEnabled(feed.hasCursor());
        mRefreshLayout.setRefreshing(false);
    }


    private static class GetGroupStoriesListener implements ApiResponse<Feed> {
        private final WeakReference<GroupFeedFragment> mFragmentReference;

        private GetGroupStoriesListener(final GroupFeedFragment fragment) {
            mFragmentReference = new WeakReference<>(fragment);
        }

        @Override
        public void failure(@Nullable final ErrorInfo error) {
            final GroupFeedFragment fragment = mFragmentReference.get();

            if (fragment != null && !fragment.isDestroyed()) {
                fragment.showError();
            }
        }

        @Override
        public void success(final Feed feed) {
            final GroupFeedFragment fragment = mFragmentReference.get();

            if (fragment != null && !fragment.isDestroyed()) {
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
            mStoriesSize = fragment.mFeed.getStories().size();
        }

        @Override
        public void failure(@Nullable final ErrorInfo error) {
            final GroupFeedFragment fragment = mFragmentReference.get();

            if (fragment != null && !fragment.isDestroyed()) {
                fragment.paginationNoMore();
            }
        }

        @Override
        public void success(final Feed feed) {
            final GroupFeedFragment fragment = mFragmentReference.get();

            if (fragment != null && !fragment.isDestroyed()) {
                if (feed.hasCursor() && feed.getStories().size() > mStoriesSize) {
                    fragment.paginationComplete();
                } else {
                    fragment.paginationNoMore();
                }
            }
        }
    }

}
