package com.charlesmadere.hummingbird.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.MangaLibraryEntriesAdapter;
import com.charlesmadere.hummingbird.misc.ObjectCache;
import com.charlesmadere.hummingbird.models.ErrorInfo;
import com.charlesmadere.hummingbird.models.Feed;
import com.charlesmadere.hummingbird.models.MangaLibraryEntry;
import com.charlesmadere.hummingbird.models.MangaLibraryUpdate;
import com.charlesmadere.hummingbird.models.ReadingStatus;
import com.charlesmadere.hummingbird.networking.Api;
import com.charlesmadere.hummingbird.networking.ApiResponse;
import com.charlesmadere.hummingbird.views.MangaLibraryEntryItemView;

import java.lang.ref.WeakReference;

public class MangaLibraryFragment extends BaseLibraryFragment implements
        MangaLibraryEntryItemView.OnEditClickListener, MangaLibraryUpdateFragment.DeleteListener,
        MangaLibraryUpdateFragment.UpdateListener {

    private static final String TAG = "MangaLibraryFragment";
    private static final String KEY_READING_STATUS = "ReadingStatus";

    private MangaLibraryEntriesAdapter mAdapter;
    private ReadingStatus mReadingStatus;


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

    @Override
    protected void fetchLibraryEntries() {
        super.fetchLibraryEntries();
        Api.getMangaLibraryEntries(mUsername, mReadingStatus, new GetLibraryEntriesListener(this));
    }

    @Override
    public MangaLibraryEntriesAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    public String getFragmentName() {
        return TAG;
    }

    @Override
    public String[] getObjectCacheKeys() {
        return new String[] { getFragmentName(), mUsername, mReadingStatus.name() };
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Bundle args = getArguments();
        mReadingStatus = args.getParcelable(KEY_READING_STATUS);

        mFeed = ObjectCache.get(this);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            final Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_manga_library, container, false);
    }

    @Override
    public void onDeleteLibraryEntry() {
        final MangaLibraryUpdateFragment fragment = (MangaLibraryUpdateFragment)
                getChildFragmentManager().findFragmentByTag(MangaLibraryUpdateFragment.TAG);
        final MangaLibraryEntry libraryEntry = fragment.getLibraryEntry();

        mRefreshLayout.setRefreshing(true);
        Api.deleteMangaLibraryEntry(libraryEntry, new DeleteLibraryEntryListener(this));
    }

    @Override
    public void onEditClick(final MangaLibraryEntryItemView v) {
        MangaLibraryUpdateFragment.create(v.getLibraryEntry()).show(getChildFragmentManager(),
                MangaLibraryUpdateFragment.TAG);
    }

    @Override
    public void onUpdateLibraryEntry() {
        final MangaLibraryUpdateFragment fragment = (MangaLibraryUpdateFragment)
                getChildFragmentManager().findFragmentByTag(MangaLibraryUpdateFragment.TAG);
        final MangaLibraryUpdate libraryUpdate = fragment.getLibraryUpdate();
        final String libraryEntryId = fragment.getLibraryEntry().getId();

        mRefreshLayout.setRefreshing(true);
        Api.updateMangaLibraryEntry(libraryEntryId, libraryUpdate, new EditLibraryEntryListener(this));
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (mEditableLibrary) {
            mAdapter = new MangaLibraryEntriesAdapter(getContext(), this);
        } else {
            mAdapter = new MangaLibraryEntriesAdapter(getContext());
        }

        mRecyclerView.setAdapter(mAdapter);

        mEmptyText.setText(mReadingStatus.getEmptyTextResId());
        mErrorText.setText(mReadingStatus.getErrorTextResId());

        if (mFeed == null || !mFeed.hasMangaLibraryEntries()) {
            fetchLibraryEntries();
        } else {
            showLibraryEntries(mFeed);
        }
    }

    @Override
    public void paginate() {
        super.paginate();
        Api.getMangaLibraryEntries(mUsername, mReadingStatus, mFeed,
                new PaginateLibraryEntriesListener(this));
    }

    @Override
    protected void paginationComplete() {
        mAdapter.set(mFeed, mListener.getLibrarySort());
        mAdapter.setPaginating(false);
    }

    @Override
    protected void showLibraryEntries(final Feed feed) {
        mFeed = feed;
        mAdapter.set(mFeed, mListener.getLibrarySort());
        mEmpty.setVisibility(View.GONE);
        mError.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        mRefreshLayout.setRefreshing(false);
        mPaginator.setEnabled(feed.hasCursor());
    }

    @Override
    public void updateLibrarySort() {
        if (mFeed != null && mFeed.hasMangaLibraryEntries()) {
            showLibraryEntries(mFeed);
        }
    }


    private static class DeleteLibraryEntryListener implements ApiResponse<Void> {
        private final WeakReference<MangaLibraryFragment> mFragmentReference;

        private DeleteLibraryEntryListener(final MangaLibraryFragment fragment) {
            mFragmentReference = new WeakReference<>(fragment);
        }

        @Override
        public void failure(@Nullable final ErrorInfo error) {
            final MangaLibraryFragment fragment = mFragmentReference.get();

            if (fragment != null && !fragment.isDestroyed()) {
                fragment.showDeleteLibraryEntryError();
            }
        }

        @Override
        public void success(@Nullable final Void object) {
            final MangaLibraryFragment fragment = mFragmentReference.get();

            if (fragment != null && !fragment.isDestroyed()) {
                fragment.fetchLibraryEntries();
            }
        }
    }

    private static class EditLibraryEntryListener implements ApiResponse<Void> {
        private final WeakReference<MangaLibraryFragment> mFragmentReference;

        private EditLibraryEntryListener(final MangaLibraryFragment fragment) {
            mFragmentReference = new WeakReference<>(fragment);
        }

        @Override
        public void failure(@Nullable final ErrorInfo error) {
            final MangaLibraryFragment fragment = mFragmentReference.get();

            if (fragment != null && !fragment.isDestroyed()) {
                fragment.showEditLibraryEntryError();
            }
        }

        @Override
        public void success(@Nullable final Void object) {
            final MangaLibraryFragment fragment = mFragmentReference.get();

            if (fragment != null && !fragment.isDestroyed()) {
                fragment.fetchLibraryEntries();
            }
        }
    }

    private static class GetLibraryEntriesListener implements ApiResponse<Feed> {
        private final WeakReference<MangaLibraryFragment> mFragmentReference;

        private GetLibraryEntriesListener(final MangaLibraryFragment fragment) {
            mFragmentReference = new WeakReference<>(fragment);
        }

        @Override
        public void failure(@Nullable final ErrorInfo error) {
            final MangaLibraryFragment fragment = mFragmentReference.get();

            if (fragment != null && !fragment.isDestroyed()) {
                fragment.showError();
            }
        }

        @Override
        public void success(final Feed feed) {
            final MangaLibraryFragment fragment = mFragmentReference.get();

            if (fragment != null && !fragment.isDestroyed()) {
                if (feed.hasMangaLibraryEntries()) {
                    fragment.showLibraryEntries(feed);
                } else {
                    fragment.showEmpty();
                }
            }
        }
    }

    private static class PaginateLibraryEntriesListener implements ApiResponse<Feed> {
        private final WeakReference<MangaLibraryFragment> mFragmentReference;
        private final int mLibraryEntriesSize;

        private PaginateLibraryEntriesListener(final MangaLibraryFragment fragment) {
            mFragmentReference = new WeakReference<>(fragment);
            mLibraryEntriesSize = fragment.mFeed.getMangaLibraryEntriesSize();
        }

        @Override
        public void failure(@Nullable final ErrorInfo error) {
            final MangaLibraryFragment fragment = mFragmentReference.get();

            if (fragment != null && !fragment.isDestroyed()) {
                fragment.paginationNoMore();
            }
        }

        @Override
        public void success(final Feed feed) {
            final MangaLibraryFragment fragment = mFragmentReference.get();

            if (fragment != null && !fragment.isDestroyed()) {
                if (feed.hasCursor() && feed.getMangaLibraryEntriesSize() > mLibraryEntriesSize) {
                    fragment.paginationComplete();
                } else {
                    fragment.paginationNoMore();
                }
            }
        }
    }

}
