package com.charlesmadere.hummingbird.fragments;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.MangaLibraryEntriesAdapter;
import com.charlesmadere.hummingbird.models.Feed;
import com.charlesmadere.hummingbird.models.ReadingStatus;
import com.charlesmadere.hummingbird.views.RefreshLayout;
import com.charlesmadere.hummingbird.views.SpaceItemDecoration;

import butterknife.BindView;

public class MangaLibraryFragment extends BaseFragment implements
        MangaLibraryUpdateFragment.RemoveListener, MangaLibraryUpdateFragment.UpdateListener,
        SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "MangaLibraryFragment";
    private static final String KEY_EDITABLE_LIBRARY = "EditableLibrary";
    private static final String KEY_FEED = "Feed";
    private static final String KEY_READING_STATUS = "ReadingStatus";
    private static final String KEY_USERNAME = "Username";

    private boolean mEditableLibrary;
    private Feed mFeed;
    private MangaLibraryEntriesAdapter mAdapter;
    private ReadingStatus mReadingStatus;
    private String mUsername;

    @BindView(R.id.llEmpty)
    LinearLayout mEmpty;

    @BindView(R.id.llError)
    LinearLayout mError;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.refreshLayout)
    RefreshLayout mRefreshLayout;

    @BindView(R.id.tvEmpty)
    TextView mEmptyText;

    @BindView(R.id.tvError)
    TextView mErrorText;


    public static MangaLibraryFragment create(final ReadingStatus readingStatus,
            final String username, final boolean editableLibrary) {
        final Bundle args = new Bundle(3);
        args.putParcelable(KEY_READING_STATUS, readingStatus);
        args.putString(KEY_USERNAME, username);
        args.putBoolean(KEY_EDITABLE_LIBRARY, editableLibrary);

        final MangaLibraryFragment fragment = new MangaLibraryFragment();
        fragment.setArguments(args);

        return fragment;
    }

    private void fetchLibraryEntries() {
        mRefreshLayout.setRefreshing(true);
        // TODO
    }

    @Override
    public String getFragmentName() {
        return TAG;
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Bundle args = getArguments();
        mReadingStatus = args.getParcelable(KEY_READING_STATUS);
        mUsername = args.getString(KEY_USERNAME);
        mEditableLibrary = args.getBoolean(KEY_EDITABLE_LIBRARY);

        if (savedInstanceState != null && !savedInstanceState.isEmpty()) {
            mFeed = savedInstanceState.getParcelable(KEY_FEED);
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            final Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_manga_library, container, false);
    }

    @Override
    public void onRefresh() {
        fetchLibraryEntries();
    }

    @Override
    public void onRemoveLibraryEntry() {
        // TODO
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mFeed != null && mFeed.hasMangaLibraryEntries()) {
            outState.putParcelable(KEY_FEED, mFeed);
        }
    }

    @Override
    public void onUpdateLibraryEntry() {
        // TODO
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRefreshLayout.setOnRefreshListener(this);

        mRecyclerView.setAdapter(mAdapter);
        SpaceItemDecoration.apply(mRecyclerView, false, R.dimen.root_padding);

        mEmptyText.setText(mReadingStatus.getEmptyTextResId());
        mErrorText.setText(mReadingStatus.getErrorTextResId());

        if (mFeed != null && mFeed.hasMangaLibraryEntries()) {
            showLibraryEntries(mFeed);
        } else {
            fetchLibraryEntries();
        }
    }

    private void showEditLibraryEntryError() {
        mRefreshLayout.setRefreshing(false);
        Toast.makeText(getContext(), R.string.error_editing_library_entry, Toast.LENGTH_LONG).show();
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

    private void showLibraryEntries(final Feed feed) {
        mFeed = feed;
        mAdapter.set(mFeed);
        mEmpty.setVisibility(View.GONE);
        mError.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        mRefreshLayout.setRefreshing(false);
    }

}
