package com.charlesmadere.hummingbird.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.TimberEntriesAdapter;
import com.charlesmadere.hummingbird.misc.Timber;
import com.charlesmadere.hummingbird.views.RefreshLayout;

import butterknife.BindView;

public class LogViewerActivity extends BaseDrawerActivity implements
        SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "LogViewerActivity";

    @BindView(R.id.llEmpty)
    LinearLayout mEmpty;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.refreshLayout)
    RefreshLayout mRefreshLayout;

    private TimberEntriesAdapter mAdapter;


    public static Intent getLaunchIntent(final Context context) {
        return new Intent(context, LogViewerActivity.class);
    }

    private void fetchTimberEntries() {
        mRefreshLayout.setRefreshing(true);
        mAdapter.set(Timber.getEntries());

        if (mAdapter.getItemCount() >= 1) {
            mEmpty.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        } else {
            mRecyclerView.setVisibility(View.GONE);
            mEmpty.setVisibility(View.VISIBLE);
        }

        supportInvalidateOptionsMenu();
        mRefreshLayout.setRefreshing(false);
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
        setContentView(R.layout.activity_log_viewer);
        fetchTimberEntries();
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.activity_log_viewer, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.miClear:
                Timber.clearEntries();
                fetchTimberEntries();
                supportInvalidateOptionsMenu();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        menu.findItem(R.id.miClear).setEnabled(mAdapter.getItemCount() >= 1);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onRefresh() {
        fetchTimberEntries();
    }

    @Override
    protected void onViewsBound() {
        super.onViewsBound();

        mRefreshLayout.setOnRefreshListener(this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.HORIZONTAL));
        mAdapter = new TimberEntriesAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
    }

}
