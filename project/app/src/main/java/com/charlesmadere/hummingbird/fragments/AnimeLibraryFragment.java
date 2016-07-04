package com.charlesmadere.hummingbird.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.LibraryEntriesAdapter;
import com.charlesmadere.hummingbird.models.ErrorInfo;
import com.charlesmadere.hummingbird.models.LibraryEntry;
import com.charlesmadere.hummingbird.models.LibraryUpdate;
import com.charlesmadere.hummingbird.models.WatchingStatus;
import com.charlesmadere.hummingbird.networking.Api;
import com.charlesmadere.hummingbird.networking.ApiResponse;
import com.charlesmadere.hummingbird.views.InternalAnimeItemView;
import com.charlesmadere.hummingbird.views.RefreshLayout;
import com.charlesmadere.hummingbird.views.SpaceItemDecoration;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import butterknife.BindView;

public class AnimeLibraryFragment extends BaseFragment implements AnimeLibraryUpdateFragment.Listeners,
        InternalAnimeItemView.OnEditClickListener, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "AnimeLibraryFragment";
    private static final String KEY_EDITABLE_LIBRARY = "EditableLibrary";
    private static final String KEY_LIBRARY_ENTRIES = "LibraryEntries";
    private static final String KEY_USERNAME = "Username";
    private static final String KEY_WATCHING_STATUS = "WatchingStatus";

    private ArrayList<LibraryEntry> mLibraryEntries;
    private boolean mEditableLibrary;
    private LibraryEntriesAdapter mAdapter;
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
        Api.getLibraryEntries(mUsername, mWatchingStatus, new GetLibraryEntriesListener(this));
    }

    @Override
    public String getFragmentName() {
        return TAG;
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Bundle args = getArguments();
        mUsername = args.getString(KEY_USERNAME);
        mWatchingStatus = args.getParcelable(KEY_WATCHING_STATUS);
        mEditableLibrary = args.getBoolean(KEY_EDITABLE_LIBRARY);

        if (savedInstanceState != null && !savedInstanceState.isEmpty()) {
            mLibraryEntries = savedInstanceState.getParcelableArrayList(KEY_LIBRARY_ENTRIES);
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            final Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_anime_library, container, false);
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
    public void onRemoveLibraryEntry() {
        final AnimeLibraryUpdateFragment fragment = (AnimeLibraryUpdateFragment)
                getChildFragmentManager().findFragmentByTag(AnimeLibraryUpdateFragment.TAG);
        final LibraryEntry libraryEntry = fragment.getLibraryEntry();

        mRefreshLayout.setRefreshing(true);
        Api.removeLibraryEntry(libraryEntry, new EditLibraryEntryListener(this));
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mLibraryEntries != null && !mLibraryEntries.isEmpty()) {
            outState.putParcelableArrayList(KEY_WATCHING_STATUS, mLibraryEntries);
        }
    }

    @Override
    public void onUpdateLibraryEntry() {
        final AnimeLibraryUpdateFragment fragment = (AnimeLibraryUpdateFragment)
                getChildFragmentManager().findFragmentByTag(AnimeLibraryUpdateFragment.TAG);
        final LibraryUpdate libraryUpdate = fragment.getLibraryUpdate();
        final String libraryEntryId = fragment.getLibraryEntry().getId();

        mRefreshLayout.setRefreshing(true);
        Api.updateLibraryEntry(libraryEntryId, libraryUpdate, new EditLibraryEntryListener(this));
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRefreshLayout.setOnRefreshListener(this);

        if (mEditableLibrary) {
            mAdapter = new LibraryEntriesAdapter(getContext(), this);
        } else {
            mAdapter = new LibraryEntriesAdapter(getContext());
        }

        mRecyclerView.setAdapter(mAdapter);
        SpaceItemDecoration.apply(mRecyclerView, false, R.dimen.root_padding);

        mEmptyText.setText(mWatchingStatus.getEmptyTextResId());
        mErrorText.setText(mWatchingStatus.getErrorTextResId());

        if (mLibraryEntries == null || mLibraryEntries.isEmpty()) {
            fetchLibraryEntries();
        } else {
            showList(mLibraryEntries);
        }
    }

    private void showEditLibraryEntryError() {
        mRefreshLayout.setRefreshing(false);
        Toast.makeText(getContext(), R.string.error_editing_library_entry, Toast.LENGTH_LONG).show();
    }

    private void showError() {
        mRecyclerView.setVisibility(View.GONE);
        mEmpty.setVisibility(View.GONE);
        mError.setVisibility(View.VISIBLE);
        mRefreshLayout.setRefreshing(false);
    }

    private void showEmpty() {
        mRecyclerView.setVisibility(View.GONE);
        mError.setVisibility(View.GONE);
        mEmpty.setVisibility(View.VISIBLE);
        mRefreshLayout.setRefreshing(false);
    }

    private void showList(final ArrayList<LibraryEntry> libraryEntries) {
        mLibraryEntries = libraryEntries;
        mAdapter.set(libraryEntries);
        mEmpty.setVisibility(View.GONE);
        mError.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        mRefreshLayout.setRefreshing(false);
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

    private static class GetLibraryEntriesListener implements ApiResponse<ArrayList<LibraryEntry>> {
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
        public void success(@Nullable final ArrayList<LibraryEntry> stories) {
            final AnimeLibraryFragment fragment = mFragmentReference.get();

            if (fragment != null && !fragment.isDestroyed()) {
                if (stories == null || stories.isEmpty()) {
                    fragment.showEmpty();
                } else {
                    fragment.showList(stories);
                }
            }
        }
    }

}
