package com.charlesmadere.hummingbird.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.SearchResultsAdapter;
import com.charlesmadere.hummingbird.models.ErrorInfo;
import com.charlesmadere.hummingbird.models.SearchBundle;
import com.charlesmadere.hummingbird.models.SearchScope;
import com.charlesmadere.hummingbird.networking.Api;
import com.charlesmadere.hummingbird.networking.ApiResponse;
import com.charlesmadere.hummingbird.views.SpaceItemDecoration;

import java.lang.ref.WeakReference;

import butterknife.BindView;

public class SearchActivity extends BaseActivity implements MenuItemCompat.OnActionExpandListener,
        SearchView.OnQueryTextListener {

    private static final String TAG = "SearchActivity";
    private static final String KEY_QUERY = "Query";
    private static final String KEY_SEARCH_BUNDLE = "SearchBundle";
    private static final long SEARCH_DELAY_MS = 400L;

    private Handler mHandler;
    private SearchBundle mSearchBundle;
    private SearchResultsAdapter mAdapter;
    private String mQuery;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.tvInitialSearchMessage)
    TextView mInitialSearchMessage;


    public static Intent getLaunchIntent(final Context context) {
        return createDrawerActivityIntent(context, SearchActivity.class);
    }

    @Override
    public String getActivityName() {
        return TAG;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        if (savedInstanceState != null && !savedInstanceState.isEmpty()) {
            mQuery = savedInstanceState.getString(KEY_QUERY);
            mSearchBundle = savedInstanceState.getParcelable(KEY_SEARCH_BUNDLE);

            if (mSearchBundle != null && mSearchBundle.hasResults()) {
                showSearchResults(mSearchBundle);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.activity_search, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onDestroy() {
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }

        super.onDestroy();
    }

    @Override
    public boolean onMenuItemActionCollapse(final MenuItem item) {
        finish();
        return false;
    }

    @Override
    public boolean onMenuItemActionExpand(final MenuItem item) {
        // intentionally empty
        return false;
    }

    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        final MenuItem searchMenuItem = menu.findItem(R.id.miSearch);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchMenuItem);
        searchView.setQueryHint(getString(R.string.search_for_anime_));
        MenuItemCompat.expandActionView(searchMenuItem);

        if (!TextUtils.isEmpty(mQuery)) {
            searchView.setQuery(mQuery, false);
        }

        MenuItemCompat.setOnActionExpandListener(searchMenuItem, this);
        searchView.setOnQueryTextListener(this);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (TextUtils.isEmpty(newText) || TextUtils.getTrimmedLength(newText) == 0) {
            return false;
        }

        mQuery = newText.trim();

        if (mHandler == null) {
            mHandler = new Handler();
        } else {
            mHandler.removeCallbacksAndMessages(null);
        }

        mHandler.postDelayed(new Search(this, mQuery), SEARCH_DELAY_MS);
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(final String query) {
        // intentionally empty
        return false;
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_QUERY, mQuery);
        outState.putParcelable(KEY_SEARCH_BUNDLE, mSearchBundle);
    }

    @Override
    protected void onViewsBound() {
        super.onViewsBound();
        SpaceItemDecoration.apply(mRecyclerView, false, R.dimen.root_padding);
        mAdapter = new SearchResultsAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void showErrorMessage() {
        Toast.makeText(this, getString(R.string.search_for_x_failed, mQuery), Toast.LENGTH_LONG).show();
    }

    private void showEmptyMessage() {
        Toast.makeText(this, getString(R.string.no_results_for_x, mQuery), Toast.LENGTH_LONG).show();
    }

    private void showSearchResults(final SearchBundle searchBundle) {
        mSearchBundle = searchBundle;
        mAdapter.set(searchBundle);
        mInitialSearchMessage.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }


    private static class Search implements ApiResponse<SearchBundle>, Runnable {
        private final String mQuery;
        private final WeakReference<SearchActivity> mActivity;

        private Search(final SearchActivity activity, final String query) {
            mQuery = query;
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void failure(@Nullable final ErrorInfo error) {
            final SearchActivity activity = mActivity.get();

            if (activity != null && !activity.isDestroyed() &&
                    mQuery.equalsIgnoreCase(activity.mQuery)) {
                activity.showErrorMessage();
            }
        }

        @Override
        public void run() {
            Api.search(SearchScope.ALL, mQuery, this);
        }

        @Override
        public void success(@Nullable final SearchBundle searchBundle) {
            final SearchActivity activity = mActivity.get();

            if (activity != null && !activity.isDestroyed() && mQuery.equals(activity.mQuery)) {
                if (searchBundle == null || !searchBundle.hasResults()) {
                    activity.showEmptyMessage();
                } else {
                    activity.showSearchResults(searchBundle);
                }
            }
        }
    }

}
