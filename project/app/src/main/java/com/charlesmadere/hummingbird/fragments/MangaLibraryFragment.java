package com.charlesmadere.hummingbird.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.MangaLibraryEntriesAdapter;
import com.charlesmadere.hummingbird.misc.ObjectCache;
import com.charlesmadere.hummingbird.models.ErrorInfo;
import com.charlesmadere.hummingbird.models.Feed;
import com.charlesmadere.hummingbird.models.MangaLibraryEntry;
import com.charlesmadere.hummingbird.models.MangaLibraryEntryResponse;
import com.charlesmadere.hummingbird.models.MangaLibraryUpdate;
import com.charlesmadere.hummingbird.models.ReadingStatus;
import com.charlesmadere.hummingbird.networking.Api;
import com.charlesmadere.hummingbird.networking.ApiResponse;
import com.charlesmadere.hummingbird.views.MangaLibraryEntryItemView;

import java.lang.ref.WeakReference;

public class MangaLibraryFragment extends BaseLibraryFragment implements
        MangaLibraryEntryItemView.Listeners, MangaLibraryUpdateFragment.LibraryEntryListener {

    private static final String TAG = "MangaLibraryFragment";
    private static final String KEY_READING_STATUS = "ReadingStatus";

    private MangaLibraryEntriesAdapter mAdapter;
    private ReadingStatus mReadingStatus;


    public static MangaLibraryFragment create(final String username,
            final ReadingStatus readingStatus, final boolean editableLibrary) {
        final Bundle args = new Bundle(3);
        args.putString(KEY_USERNAME, username);
        args.putParcelable(KEY_READING_STATUS, readingStatus);
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
    public MangaLibraryEntry getMangaLibraryEntry(final String libraryEntryId) {
        for (final MangaLibraryEntry libraryEntry : mFeed.getMangaLibraryEntries()) {
            if (libraryEntryId.equalsIgnoreCase(libraryEntry.getId())) {
                return libraryEntry;
            }
        }

        throw new RuntimeException("couldn't find MangaLibraryEntry: \"" + libraryEntryId + '"');
    }

    @Override
    public String[] getObjectCacheKeys() {
        return new String[] { getFragmentName(), mUsername, mReadingStatus.name() };
    }

    @Override
    public boolean isLibraryLoading() {
        return mRefreshLayout.isRefreshing();
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (mEditableLibrary) {
            mAdapter = new MangaLibraryEntriesAdapter(getContext(), this);
        } else {
            mAdapter = new MangaLibraryEntriesAdapter(getContext());
        }

        mRecyclerView.setAdapter(mAdapter);

        mEmptyText.setText(mReadingStatus.getEmptyTextResId());
        mErrorText.setText(mReadingStatus.getErrorTextResId());

        mFeed = ObjectCache.get(this);

        if (mFeed == null || !mFeed.hasMangaLibraryEntries()) {
            fetchLibraryEntries();
        } else {
            showLibraryEntries(mFeed);
        }
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Bundle args = getArguments();
        mReadingStatus = args.getParcelable(KEY_READING_STATUS);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            final Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_manga_library, container, false);
    }

    @Override
    public void onDeleteClick(final MangaLibraryEntryItemView v) {
        final MangaLibraryEntry libraryEntry = v.getLibraryEntry();

        new AlertDialog.Builder(getContext())
                .setMessage(R.string.are_you_sure_you_want_to_remove_this_from_your_library)
                .setNegativeButton(R.string.cancel, null)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        mRefreshLayout.setRefreshing(true);
                        Api.deleteMangaLibraryEntry(libraryEntry, new DeleteLibraryEntryListener(
                                MangaLibraryFragment.this));
                    }
                })
                .show();
    }

    @Override
    public void onEditClick(final MangaLibraryEntryItemView v) {
        MangaLibraryUpdateFragment.create(v.getLibraryEntry().getId()).show(
                getChildFragmentManager(), MangaLibraryUpdateFragment.TAG);
    }

    @Override
    public void onPlusOneClick(final MangaLibraryEntryItemView v) {
        final MangaLibraryEntry entry = v.getLibraryEntry();
        final MangaLibraryUpdate update = new MangaLibraryUpdate(entry);
        update.setChaptersRead(update.getChaptersRead() + 1);

        mRefreshLayout.setRefreshing(true);
        Api.updateMangaLibraryEntry(entry.getId(), update, new EditLibraryEntryListener(this));
    }

    @Override
    public void onUpdateLibraryEntry() {
        final MangaLibraryUpdateFragment fragment = (MangaLibraryUpdateFragment)
                getChildFragmentManager().findFragmentByTag(MangaLibraryUpdateFragment.TAG);
        final MangaLibraryUpdate update = fragment.getLibraryUpdate();
        final String entryId = fragment.getLibraryEntryId();

        mRefreshLayout.setRefreshing(true);
        Api.updateMangaLibraryEntry(entryId, update, new EditLibraryEntryListener(this));
    }

    @Override
    protected void showLibraryEntries(final Feed feed) {
        mFeed = feed;
        mAdapter.set(mFeed, mListener.getLibrarySort());
        mEmpty.setVisibility(View.GONE);
        mError.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        mRefreshLayout.setRefreshing(false);
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

            if (fragment != null && fragment.isAlive()) {
                fragment.showDeleteLibraryEntryError();
            }
        }

        @Override
        public void success(@Nullable final Void object) {
            final MangaLibraryFragment fragment = mFragmentReference.get();

            if (fragment != null && fragment.isAlive()) {
                fragment.fetchLibraryEntries();
            }
        }
    }

    private static class EditLibraryEntryListener implements ApiResponse<MangaLibraryEntryResponse> {
        private final WeakReference<MangaLibraryFragment> mFragmentReference;

        private EditLibraryEntryListener(final MangaLibraryFragment fragment) {
            mFragmentReference = new WeakReference<>(fragment);
        }

        @Override
        public void failure(@Nullable final ErrorInfo error) {
            final MangaLibraryFragment fragment = mFragmentReference.get();

            if (fragment != null && fragment.isAlive()) {
                fragment.showEditLibraryEntryError();
            }
        }

        @Override
        public void success(final MangaLibraryEntryResponse response) {
            final MangaLibraryFragment fragment = mFragmentReference.get();

            if (fragment != null && fragment.isAlive()) {
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

            if (fragment != null && fragment.isAlive()) {
                fragment.showError();
            }
        }

        @Override
        public void success(final Feed feed) {
            final MangaLibraryFragment fragment = mFragmentReference.get();

            if (fragment != null && fragment.isAlive()) {
                if (feed.hasMangaLibraryEntries()) {
                    fragment.showLibraryEntries(feed);
                } else {
                    fragment.showEmpty();
                }
            }
        }
    }

}
