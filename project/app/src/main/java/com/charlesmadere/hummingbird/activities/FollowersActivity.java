package com.charlesmadere.hummingbird.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.views.RefreshLayout;
import com.charlesmadere.hummingbird.views.SpaceItemDecoration;

import butterknife.BindView;

public class FollowersActivity extends BaseDrawerActivity implements
        SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "FollowersActivity";
    private static final String CNAME = FollowersActivity.class.getCanonicalName();
    private static final String EXTRA_USERNAME = CNAME + ".Username";

    private String mUsername;

    @BindView(R.id.llEmpty)
    LinearLayout mEmpty;

    @BindView(R.id.llError)
    LinearLayout mError;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.refreshLayout)
    RefreshLayout mRefreshLayout;


    public static Intent getLaunchIntent(final Context context, final String username) {
        return new Intent(context, FollowersActivity.class)
                .putExtra(EXTRA_USERNAME, username);
    }

    private void fetchFollowers() {
        mRefreshLayout.setRefreshing(true);
        // TODO
    }

    @Override
    public String getActivityName() {
        return TAG;
    }

    @Override
    protected boolean isUpNavigationEnabled() {
        return true;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followers);

        final Intent intent = getIntent();
        mUsername = intent.getStringExtra(EXTRA_USERNAME);

        // TODO
    }

    @Override
    public void onRefresh() {
        fetchFollowers();
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);

        // TODO
    }

    @Override
    protected void onViewsBound() {
        super.onViewsBound();
        SpaceItemDecoration.apply(mRecyclerView, false, R.dimen.root_padding_half);
        mRefreshLayout.setOnRefreshListener(this);
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

}
