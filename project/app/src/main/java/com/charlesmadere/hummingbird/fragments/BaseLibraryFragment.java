package com.charlesmadere.hummingbird.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.BaseAdapter;
import com.charlesmadere.hummingbird.misc.MiscUtils;
import com.charlesmadere.hummingbird.misc.ObjectCache;
import com.charlesmadere.hummingbird.models.Feed;
import com.charlesmadere.hummingbird.models.LibrarySort;
import com.charlesmadere.hummingbird.views.RefreshLayout;
import com.charlesmadere.hummingbird.views.SpaceItemDecoration;

import butterknife.BindView;

public abstract class BaseLibraryFragment extends BaseFragment implements ObjectCache.KeyProvider,
        SwipeRefreshLayout.OnRefreshListener {

    protected Feed mFeed;
    protected Listeners mListeners;

    @BindView(R.id.llEmpty)
    protected LinearLayout mEmpty;

    @BindView(R.id.llError)
    protected LinearLayout mError;

    @BindView(R.id.recyclerView)
    protected RecyclerView mRecyclerView;

    @BindView(R.id.refreshLayout)
    protected RefreshLayout mRefreshLayout;

    @BindView(R.id.tvEmpty)
    protected TextView mEmptyText;

    @BindView(R.id.tvError)
    protected TextView mErrorText;


    protected void fetchLibraryEntries() {
        mRefreshLayout.setRefreshing(true);
    }

    protected abstract BaseAdapter getAdapter();

    protected LibrarySort getLibrarySort() {
        return mListeners.getLibrarySort();
    }

    protected String getUsername() {
        return mListeners.getUsername();
    }

    protected boolean isEditableLibrary() {
        return mListeners.isEditableLibrary();
    }

    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);

        final Fragment fragment = getParentFragment();
        if (fragment instanceof Listeners) {
            mListeners = (Listeners) fragment;
        } else {
            final Activity activity = MiscUtils.optActivity(context);

            if (activity instanceof Listeners) {
                mListeners = (Listeners) activity;
            }
        }

        if (mListeners == null) {
            throw new IllegalStateException(getFragmentName() + " must attach to Listeners");
        }
    }

    @Override
    public void onRefresh() {
        fetchLibraryEntries();
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mFeed != null) {
            ObjectCache.put(mFeed, this);
        }
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRefreshLayout.setOnRefreshListener(this);
        mRecyclerView.setHasFixedSize(true);
        SpaceItemDecoration.apply(mRecyclerView, true, R.dimen.root_padding_half);
    }

    protected void showDeleteLibraryEntryError() {
        mRefreshLayout.setRefreshing(false);
        Toast.makeText(getContext(), R.string.error_deleting_library_entry, Toast.LENGTH_LONG).show();
    }

    protected void showEditLibraryEntryError() {
        mRefreshLayout.setRefreshing(false);
        Toast.makeText(getContext(), R.string.error_editing_library_entry, Toast.LENGTH_LONG).show();
    }

    protected void showEmpty() {
        mRecyclerView.setVisibility(View.GONE);
        mError.setVisibility(View.GONE);
        mEmpty.setVisibility(View.VISIBLE);
        mRefreshLayout.setRefreshing(false);
    }

    protected void showError() {
        mRecyclerView.setVisibility(View.GONE);
        mEmpty.setVisibility(View.GONE);
        mError.setVisibility(View.VISIBLE);
        mRefreshLayout.setRefreshing(false);
    }

    protected abstract void showLibraryEntries(final Feed feed);

    public abstract void updateLibrarySort();


    public interface Listeners {
        LibrarySort getLibrarySort();
        String getUsername();
        boolean isEditableLibrary();
    }

}
