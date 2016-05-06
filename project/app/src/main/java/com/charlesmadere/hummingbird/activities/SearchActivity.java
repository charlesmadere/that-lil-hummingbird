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
    private static final String KEY_EMPTY_STATE = "EmptyState";
    private static final String KEY_ERROR_STATE = "ErrorState";
    private static final String KEY_QUERY = "Query";
    private static final String KEY_SEARCH_BUNDLE = "SearchBundle";
    private static final long SEARCH_DELAY_MS = 400L;

    private Handler mHandler;
    private SearchBundle mSearchBundle;
    private SearchResultsAdapter mAdapter;
    private String mQuery;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.tvEmpty)
    TextView mEmpty;

    @BindView(R.id.tvError)
    TextView mError;

    @BindView(R.id.tvInitialMessage)
    TextView mInitialMessage;


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

        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_QUERY)) {
            mQuery = savedInstanceState.getString(KEY_QUERY);

            if (savedInstanceState.containsKey(KEY_SEARCH_BUNDLE)) {
                mSearchBundle = savedInstanceState.getParcelable(KEY_SEARCH_BUNDLE);
                showSearchResults(mSearchBundle);
            } else if (savedInstanceState.getBoolean(KEY_EMPTY_STATE, false)) {
                showEmpty();
            } else if (savedInstanceState.getBoolean(KEY_ERROR_STATE, false)) {
                showError();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.activity_search, menu);

        final MenuItem searchMenuItem = menu.findItem(R.id.miSearch);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchMenuItem);
        searchView.setQueryHint(getString(R.string.search_));
        MenuItemCompat.expandActionView(searchMenuItem);

        if (!TextUtils.isEmpty(mQuery)) {
            searchView.setQuery(mQuery, false);
        }

        MenuItemCompat.setOnActionExpandListener(searchMenuItem, this);
        searchView.setOnQueryTextListener(this);

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
    public boolean onQueryTextChange(String newText) {
        mSearchBundle = null;

        if (TextUtils.isEmpty(newText) || TextUtils.getTrimmedLength(newText) == 0) {
            showInitialMessage();
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

        if (!TextUtils.isEmpty(mQuery) && TextUtils.getTrimmedLength(mQuery) >= 1) {
            outState.putString(KEY_QUERY, mQuery);

            if (mSearchBundle != null) {
                outState.putParcelable(KEY_SEARCH_BUNDLE, mSearchBundle);
            } else if (mEmpty.getVisibility() == View.VISIBLE) {
                outState.putBoolean(KEY_EMPTY_STATE, true);
            } else if (mError.getVisibility() == View.VISIBLE) {
                outState.putBoolean(KEY_ERROR_STATE, true);
            }
        }
    }

    @Override
    protected void onViewsBound() {
        super.onViewsBound();
        SpaceItemDecoration.apply(mRecyclerView, false, R.dimen.root_padding);
        mAdapter = new SearchResultsAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void showError() {
        mRecyclerView.setVisibility(View.GONE);
        mInitialMessage.setVisibility(View.GONE);
        mEmpty.setVisibility(View.GONE);
        mError.setVisibility(View.VISIBLE);
    }

    private void showEmpty() {
        mRecyclerView.setVisibility(View.GONE);
        mInitialMessage.setVisibility(View.GONE);
        mError.setVisibility(View.GONE);
        mEmpty.setVisibility(View.VISIBLE);
    }

    private void showInitialMessage() {
        mRecyclerView.setVisibility(View.GONE);
        mEmpty.setVisibility(View.GONE);
        mError.setVisibility(View.GONE);
        mInitialMessage.setVisibility(View.VISIBLE);
    }

    private void showSearchResults(final SearchBundle searchBundle) {
        mSearchBundle = searchBundle;
        mAdapter.set(searchBundle);
        mInitialMessage.setVisibility(View.GONE);
        mEmpty.setVisibility(View.GONE);
        mError.setVisibility(View.GONE);
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

            if (activity != null && !activity.isDestroyed() && mQuery.equals(activity.mQuery)) {
                activity.showError();
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
                    activity.showEmpty();
                } else {
                    activity.showSearchResults(searchBundle);
                }
            }
        }
    }

}
