package com.charlesmadere.hummingbird.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.SearchResultsAdapter;
import com.charlesmadere.hummingbird.misc.MiscUtils;
import com.charlesmadere.hummingbird.models.ErrorInfo;
import com.charlesmadere.hummingbird.models.SearchBundle;
import com.charlesmadere.hummingbird.models.SearchScope;
import com.charlesmadere.hummingbird.networking.Api;
import com.charlesmadere.hummingbird.networking.ApiResponse;
import com.charlesmadere.hummingbird.views.SearchScopeSpinner;
import com.charlesmadere.hummingbird.views.SpaceItemDecoration;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import butterknife.OnTextChanged;

public class SearchActivity extends BaseDrawerActivity implements
        SearchScopeSpinner.OnItemSelectedListener {

    private static final String TAG = "SearchActivity";
    private static final long SEARCH_DELAY_MS = 250L;

    private Handler mHandler;
    private SearchResultsAdapter mAdapter;

    @BindView(R.id.etSearch)
    EditText mSearchField;

    @BindView(R.id.ibClear)
    ImageButton mClear;

    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.searchScopeSpinner)
    SearchScopeSpinner mSearchScope;

    @BindView(R.id.tvEmpty)
    TextView mEmpty;

    @BindView(R.id.tvInitialMessage)
    TextView mInitialMessage;


    public static Intent getLaunchIntent(final Context context) {
        return createDrawerActivityIntent(context, SearchActivity.class);
    }

    @Override
    public String getActivityName() {
        return TAG;
    }

    private String getSearchQuery() {
        return mSearchField.getText().toString().trim();
    }

    @Override
    protected boolean isUpNavigationEnabled() {
        return true;
    }

    @OnClick(R.id.ibClear)
    void onClearClick() {
        mSearchField.setText("");
        MiscUtils.openKeyboard(this, mSearchField);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
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
    protected void onDrawerOpened() {
        super.onDrawerOpened();
        MiscUtils.closeKeyboard(this);
    }

    @OnEditorAction(R.id.etSearch)
    boolean onSearchEditorAction(final int actionId) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            MiscUtils.closeKeyboard(this);
        }

        return false;
    }

    @Override
    public void onItemSelected(final SearchScopeSpinner v) {
        onSearchTextChanged();
    }

    @OnTextChanged(R.id.etSearch)
    void onSearchTextChanged() {
        final String query = getSearchQuery();

        if (TextUtils.isEmpty(query)) {
            mClear.setVisibility(View.INVISIBLE);
            showInitialMessage();
            return;
        }

        mClear.setVisibility(View.VISIBLE);
        showProgressBar();

        if (mHandler == null) {
            mHandler = new Handler();
        } else {
            mHandler.removeCallbacksAndMessages(null);
        }

        mHandler.postDelayed(new Search(this, mSearchScope.getSelectedItem(), query),
                SEARCH_DELAY_MS);
    }

    @Override
    protected void onViewsBound() {
        super.onViewsBound();
        mRecyclerView.setHasFixedSize(true);
        SpaceItemDecoration.apply(mRecyclerView, true, R.dimen.root_padding_half);
        mAdapter = new SearchResultsAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mSearchScope.setOnItemSelectedListener(this);
    }

    @Override
    protected boolean showSearchIcon() {
        return false;
    }

    private void showEmpty() {
        mProgressBar.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.GONE);
        mInitialMessage.setVisibility(View.GONE);
        mEmpty.setVisibility(View.VISIBLE);
    }

    private void showInitialMessage() {
        mProgressBar.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.GONE);
        mEmpty.setVisibility(View.GONE);
        mInitialMessage.setVisibility(View.VISIBLE);
    }

    private void showProgressBar() {
        mRecyclerView.setVisibility(View.GONE);
        mInitialMessage.setVisibility(View.GONE);
        mEmpty.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void showSearchResults(final SearchBundle searchBundle) {
        mAdapter.set(searchBundle, mSearchScope.getSelectedItem());
        mProgressBar.setVisibility(View.GONE);
        mInitialMessage.setVisibility(View.GONE);
        mEmpty.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }


    private static class Search implements ApiResponse<SearchBundle>, Runnable {
        private final SearchScope mSearchScope;
        private final String mQuery;
        private final WeakReference<SearchActivity> mActivity;

        private Search(final SearchActivity activity, final SearchScope searchScope,
                final String query) {
            mSearchScope = searchScope;
            mQuery = query;
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void failure(@Nullable final ErrorInfo error) {
            final SearchActivity activity = mActivity.get();

            if (proceed(activity)) {
                activity.showEmpty();
            }
        }

        private boolean proceed(final SearchActivity activity) {
            return activity != null && !activity.isDestroyed() &&
                    mSearchScope == activity.mSearchScope.getSelectedItem() &&
                    mQuery.equals(activity.getSearchQuery());
        }

        @Override
        public void run() {
            Api.search(mSearchScope, mQuery, this);
        }

        @Override
        public void success(@Nullable final SearchBundle searchBundle) {
            final SearchActivity activity = mActivity.get();

            if (proceed(activity)) {
                if (searchBundle == null || !searchBundle.hasResults()) {
                    activity.showEmpty();
                } else {
                    activity.showSearchResults(searchBundle);
                }
            }
        }
    }

}
