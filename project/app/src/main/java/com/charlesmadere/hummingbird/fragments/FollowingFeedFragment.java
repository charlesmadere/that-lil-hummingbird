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
import com.charlesmadere.hummingbird.models.ActionGroup;
import com.charlesmadere.hummingbird.models.ArrayResponse;
import com.charlesmadere.hummingbird.models.ErrorInfo;
import com.charlesmadere.hummingbird.networking.PaginationApiListener;
import com.charlesmadere.hummingbird.views.RecyclerViewPaginator;
import com.charlesmadere.hummingbird.views.RefreshLayout;

import butterknife.BindView;

public class FollowingFeedFragment extends BaseFragment implements
        PaginationApiListener<ArrayResponse<ActionGroup>>, RecyclerViewPaginator.Listeners,
        SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "FollowingFeedFragment";

    private RecyclerViewPaginator mPaginator;

    @BindView(R.id.llEmpty)
    LinearLayout mEmpty;

    @BindView(R.id.llError)
    LinearLayout mError;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.refreshLayout)
    RefreshLayout mRefreshLayout;


    @Override
    public void failure(@Nullable final ErrorInfo error) {

    }

    @Override
    public String getFragmentName() {
        return TAG;
    }

    @Override
    public boolean isLoading() {
        return false;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            final Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_following_feed, container, false);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void paginate() {

    }

    @Override
    public void paginationComplete() {

    }

    @Override
    public void paginationNoMore() {

    }

    @Override
    public void success(@Nullable final ArrayResponse<ActionGroup> actionGroups) {

    }

}
