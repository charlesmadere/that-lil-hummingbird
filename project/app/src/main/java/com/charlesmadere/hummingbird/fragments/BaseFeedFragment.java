package com.charlesmadere.hummingbird.fragments;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.FeedAdapter;
import com.charlesmadere.hummingbird.models.Feed;
import com.charlesmadere.hummingbird.models.UserV1;
import com.charlesmadere.hummingbird.views.RefreshLayout;
import com.charlesmadere.hummingbird.views.SpaceItemDecoration;

import butterknife.BindView;

public abstract class BaseFeedFragment extends BaseFragment implements
        SwipeRefreshLayout.OnRefreshListener {

    protected static final String KEY_FEED = "Feed";
    protected static final String KEY_USER = "User";

    @BindView(R.id.llEmpty)
    protected LinearLayout mEmpty;

    @BindView(R.id.llError)
    protected LinearLayout mError;

    @BindView(R.id.recyclerView)
    protected RecyclerView mRecyclerView;

    @BindView(R.id.refreshLayout)
    protected RefreshLayout mRefreshLayout;

    protected Feed mFeed;
    protected FeedAdapter mAdapter;
    protected UserV1 mUser;


    protected void fetchFeed() {
        mRefreshLayout.setRefreshing(true);
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Bundle args = getArguments();
        mUser = args.getParcelable(KEY_USER);

        if (savedInstanceState != null && !savedInstanceState.isEmpty()) {
            mFeed = savedInstanceState.getParcelable(KEY_FEED);
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            final Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_base_feed, container, false);
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
        mAdapter = new FeedAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);
        SpaceItemDecoration.apply(mRecyclerView, false, R.dimen.root_padding);

        if (mFeed == null) {
            fetchFeed();
        } else {
            showFeed(mFeed);
        }
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
        mRefreshLayout.setRefreshing(false);
    }

}
