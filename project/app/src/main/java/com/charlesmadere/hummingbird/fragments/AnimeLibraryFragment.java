package com.charlesmadere.hummingbird.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.AnimeLibraryEntriesAdapter;
import com.charlesmadere.hummingbird.misc.MiscUtils;
import com.charlesmadere.hummingbird.models.AnimeLibraryEntry;
import com.charlesmadere.hummingbird.models.AnimeLibraryUpdate;
import com.charlesmadere.hummingbird.models.ErrorInfo;
import com.charlesmadere.hummingbird.models.Feed;
import com.charlesmadere.hummingbird.models.LibrarySort;
import com.charlesmadere.hummingbird.models.WatchingStatus;
import com.charlesmadere.hummingbird.networking.Api;
import com.charlesmadere.hummingbird.networking.ApiResponse;
import com.charlesmadere.hummingbird.views.InternalAnimeItemView;
import com.charlesmadere.hummingbird.views.RecyclerViewPaginator;
import com.charlesmadere.hummingbird.views.RefreshLayout;
import com.charlesmadere.hummingbird.views.SpaceItemDecoration;

import java.lang.ref.WeakReference;

import butterknife.BindView;

public class AnimeLibraryFragment extends BaseFragment implements
        AnimeLibraryUpdateFragment.DeleteListener, AnimeLibraryUpdateFragment.UpdateListener,
        InternalAnimeItemView.OnEditClickListener, RecyclerViewPaginator.Listeners,
        SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "AnimeLibraryFragment";
    private static final String KEY_EDITABLE_LIBRARY = "EditableLibrary";
    private static final String KEY_FEED = "Feed";
    private static final String KEY_USERNAME = "Username";
    private static final String KEY_WATCHING_STATUS = "WatchingStatus";

    private AnimeLibraryEntriesAdapter mAdapter;
    private boolean mEditableLibrary;
    private Feed mFeed;
    private Listener mListener;
    private RecyclerViewPaginator mPaginator;
    private String mUsername;
    private WatchingStatus mWatchingStatus;

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


    public static AnimeLibraryFragment create(final String username,
            final WatchingStatus watchingStatus, final boolean editableLibrary) {
        final Bundle args = new Bundle(3);
        args.putString(KEY_USERNAME, username);
        args.putParcelable(KEY_WATCHING_STATUS, watchingStatus);
        args.putBoolean(KEY_EDITABLE_LIBRARY, editableLibrary);

        final AnimeLibraryFragment fragment = new AnimeLibraryFragment();
        fragment.setArguments(args);

        return fragment;
    }

    private void fetchLibraryEntries() {
        mRefreshLayout.setRefreshing(true);
        Api.getAnimeLibraryEntries(mUsername, mWatchingStatus, new GetLibraryEntriesListener(this));
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
    public void onAttach(final Context context) {
        super.onAttach(context);

        final Fragment fragment = getParentFragment();
        if (fragment instanceof Listener) {
            mListener = (Listener) fragment;
        } else {
            final Activity activity = MiscUtils.getActivity(context);

            if (activity instanceof Listener) {
                mListener = (Listener) activity;
            }
        }

        if (mListener == null) {
            throw new IllegalStateException(TAG + " must have a Listener");
        }
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Bundle args = getArguments();
        mUsername = args.getString(KEY_USERNAME);
        mWatchingStatus = args.getParcelable(KEY_WATCHING_STATUS);
        mEditableLibrary = args.getBoolean(KEY_EDITABLE_LIBRARY);

        if (savedInstanceState != null && !savedInstanceState.isEmpty()) {
            mFeed = savedInstanceState.getParcelable(KEY_FEED);
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            final Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_anime_library, container, false);
    }

    @Override
    public void onDeleteLibraryEntry() {
        final AnimeLibraryUpdateFragment fragment = (AnimeLibraryUpdateFragment)
                getChildFragmentManager().findFragmentByTag(AnimeLibraryUpdateFragment.TAG);
        final AnimeLibraryEntry libraryEntry = fragment.getLibraryEntry();

        mRefreshLayout.setRefreshing(true);
        Api.deleteAnimeLibraryEntry(libraryEntry, new DeleteLibraryEntryListener(this));
    }

    @Override
    public void onEditClick(final InternalAnimeItemView v) {
        AnimeLibraryUpdateFragment.create(v.getLibraryEntry()).show(getChildFragmentManager(),
                AnimeLibraryUpdateFragment.TAG);
    }

    @Override
    public void onRefresh() {
        fetchLibraryEntries();
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mFeed != null && mFeed.hasAnimeLibraryEntries()) {
            outState.putParcelable(KEY_FEED, mFeed);
        }
    }

    @Override
    public void onUpdateLibraryEntry() {
        final AnimeLibraryUpdateFragment fragment = (AnimeLibraryUpdateFragment)
                getChildFragmentManager().findFragmentByTag(AnimeLibraryUpdateFragment.TAG);
        final AnimeLibraryUpdate libraryUpdate = fragment.getLibraryUpdate();
        final String libraryEntryId = fragment.getLibraryEntry().getId();

        mRefreshLayout.setRefreshing(true);
        Api.updateAnimeLibraryEntry(libraryEntryId, libraryUpdate, new EditLibraryEntryListener(this));
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRefreshLayout.setOnRefreshListener(this);

        if (mEditableLibrary) {
            mAdapter = new AnimeLibraryEntriesAdapter(getContext(), this);
        } else {
            mAdapter = new AnimeLibraryEntriesAdapter(getContext());
        }

        mRecyclerView.setAdapter(mAdapter);
        SpaceItemDecoration.apply(mRecyclerView, false, R.dimen.root_padding);
        mPaginator = new RecyclerViewPaginator(mRecyclerView, this);

        mEmptyText.setText(mWatchingStatus.getEmptyTextResId());
        mErrorText.setText(mWatchingStatus.getErrorTextResId());

        if (mFeed != null && mFeed.hasAnimeLibraryEntries()) {
            showLibraryEntries(mFeed);
        } else {
            fetchLibraryEntries();
        }
    }

    @Override
    public void paginate() {
        mAdapter.setPaginating(true);
        Api.getAnimeLibraryEntries(mUsername, mWatchingStatus, mFeed,
                new PaginateLibraryEntriesListener(this));
    }

    private void paginationComplete() {
        mAdapter.set(mFeed, mListener.getLibrarySort());
        mAdapter.setPaginating(false);
    }

    private void paginationNoMore() {
        mPaginator.setEnabled(false);
        mAdapter.setPaginating(false);
    }

    private void showDeleteLibraryEntryError() {
        mRefreshLayout.setRefreshing(false);
        Toast.makeText(getContext(), R.string.error_deleting_library_entry, Toast.LENGTH_LONG).show();
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
        mAdapter.set(mFeed, mListener.getLibrarySort());
        mEmpty.setVisibility(View.GONE);
        mError.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        mRefreshLayout.setRefreshing(false);
        mPaginator.setEnabled(feed.hasCursor());
    }

    public void updateLibrarySort() {
        if (mFeed != null && mFeed.hasAnimeLibraryEntries()) {
            showLibraryEntries(mFeed);
        }
    }


    public interface Listener {
        LibrarySort getLibrarySort();
    }

    private static class DeleteLibraryEntryListener implements ApiResponse<Void> {
        private final WeakReference<AnimeLibraryFragment> mFragmentReference;

        private DeleteLibraryEntryListener(final AnimeLibraryFragment fragment) {
            mFragmentReference = new WeakReference<>(fragment);
        }

        @Override
        public void failure(@Nullable final ErrorInfo error) {
            final AnimeLibraryFragment fragment = mFragmentReference.get();

            if (fragment != null && !fragment.isDestroyed()) {
                fragment.showDeleteLibraryEntryError();
            }
        }

        @Override
        public void success(@Nullable final Void object) {
            final AnimeLibraryFragment fragment = mFragmentReference.get();

            if (fragment != null && !fragment.isDestroyed()) {
                fragment.fetchLibraryEntries();
            }
        }
    }

    private static class EditLibraryEntryListener implements ApiResponse<Void> {
        private final WeakReference<AnimeLibraryFragment> mFragmentReference;

        private EditLibraryEntryListener(final AnimeLibraryFragment fragment) {
            mFragmentReference = new WeakReference<>(fragment);
        }

        @Override
        public void failure(@Nullable final ErrorInfo error) {
            final AnimeLibraryFragment fragment = mFragmentReference.get();

            if (fragment != null && !fragment.isDestroyed()) {
                fragment.showEditLibraryEntryError();
            }
        }

        @Override
        public void success(@Nullable final Void object) {
            final AnimeLibraryFragment fragment = mFragmentReference.get();

            if (fragment != null && !fragment.isDestroyed()) {
                fragment.fetchLibraryEntries();
            }
        }
    }

    private static class GetLibraryEntriesListener implements ApiResponse<Feed> {
        private final WeakReference<AnimeLibraryFragment> mFragmentReference;

        private GetLibraryEntriesListener(final AnimeLibraryFragment fragment) {
            mFragmentReference = new WeakReference<>(fragment);
        }

        @Override
        public void failure(@Nullable final ErrorInfo error) {
            final AnimeLibraryFragment fragment = mFragmentReference.get();

            if (fragment != null && !fragment.isDestroyed()) {
                fragment.showError();
            }
        }

        @Override
        public void success(final Feed feed) {
            final AnimeLibraryFragment fragment = mFragmentReference.get();

            if (fragment != null && !fragment.isDestroyed()) {
                if (feed.hasAnimeLibraryEntries()) {
                    fragment.showLibraryEntries(feed);
                } else {
                    fragment.showEmpty();
                }
            }
        }
    }

    private static class PaginateLibraryEntriesListener implements ApiResponse<Feed> {
        private final WeakReference<AnimeLibraryFragment> mFragmentReference;
        private final int mLibraryEntriesSize;

        private PaginateLibraryEntriesListener(final AnimeLibraryFragment fragment) {
            mFragmentReference = new WeakReference<>(fragment);
            mLibraryEntriesSize = fragment.mFeed.getAnimeLibraryEntriesSize();
        }

        @Override
        public void failure(@Nullable final ErrorInfo error) {
            final AnimeLibraryFragment fragment = mFragmentReference.get();

            if (fragment != null && !fragment.isDestroyed()) {
                fragment.paginationNoMore();
            }
        }

        @Override
        public void success(final Feed feed) {
            final AnimeLibraryFragment fragment = mFragmentReference.get();

            if (fragment != null && !fragment.isDestroyed()) {
                if (feed.hasCursor() && feed.getAnimeLibraryEntriesSize() > mLibraryEntriesSize) {
                    fragment.paginationComplete();
                } else {
                    fragment.paginationNoMore();
                }
            }
        }
    }

}
