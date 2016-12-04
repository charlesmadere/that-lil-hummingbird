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
import com.charlesmadere.hummingbird.adapters.FeedV3Adapter;
import com.charlesmadere.hummingbird.models.ActionGroup;
import com.charlesmadere.hummingbird.models.ArrayResponse;
import com.charlesmadere.hummingbird.models.ErrorInfo;
import com.charlesmadere.hummingbird.networking.ApiV3;
import com.charlesmadere.hummingbird.networking.PaginationApiCall;
import com.charlesmadere.hummingbird.networking.PaginationApiListener;
import com.charlesmadere.hummingbird.views.RecyclerViewPaginator;
import com.charlesmadere.hummingbird.views.RefreshLayout;
import com.charlesmadere.hummingbird.views.SpaceItemDecoration;

import butterknife.BindView;

public class GlobalFeedFragment extends BaseFragment implements
        PaginationApiListener<ArrayResponse<ActionGroup>>, RecyclerViewPaginator.Listeners,
        SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "GlobalFeedFragment";

    private FeedV3Adapter mAdapter;
    private RecyclerViewPaginator mPaginator;

    @BindView(R.id.llEmpty)
    LinearLayout mEmpty;

    @BindView(R.id.llError)
    LinearLayout mError;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.refreshLayout)
    RefreshLayout mRefreshLayout;


    public static GlobalFeedFragment create() {
        return new GlobalFeedFragment();
    }

    @Override
    public void failure(@Nullable final ErrorInfo error) {
        showError();
    }

    private void fetchFeed() {
        mRefreshLayout.setRefreshing(true);
        ApiV3.getGlobalFeed(new PaginationApiCall<>(this));
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
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        fetchFeed();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            final Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_global_feed, container, false);
    }

    @Override
    public void onRefresh() {
        fetchFeed();
    }

    @Override
    public void paginate() {
        mAdapter.setPaginating(true);

    }

    @Override
    public void paginationComplete() {
        mAdapter.setPaginating(false);

    }

    @Override
    public void paginationNoMore() {
        mPaginator.setEnabled(false);
        mAdapter.setPaginating(false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRefreshLayout.setOnRefreshListener(this);
        mRecyclerView.setHasFixedSize(true);
        SpaceItemDecoration.apply(mRecyclerView, true, R.dimen.root_padding_half);
        mAdapter = new FeedV3Adapter(getContext());
        mRecyclerView.setAdapter(mAdapter);
        mPaginator = new RecyclerViewPaginator(mRecyclerView, this);
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

    @Override
    public void success(@Nullable final ArrayResponse<ActionGroup> actionGroups) {
        if (actionGroups != null && actionGroups.hasData()) {

        } else {
            showEmpty();
        }
    }

}
