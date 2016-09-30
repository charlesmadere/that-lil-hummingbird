package com.charlesmadere.hummingbird.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.AnimeLibraryEntriesAdapter;
import com.charlesmadere.hummingbird.misc.ObjectCache;
import com.charlesmadere.hummingbird.models.AnimeLibraryEntry;
import com.charlesmadere.hummingbird.models.AnimeLibraryEntryResponse;
import com.charlesmadere.hummingbird.models.AnimeLibraryUpdate;
import com.charlesmadere.hummingbird.models.ErrorInfo;
import com.charlesmadere.hummingbird.models.Feed;
import com.charlesmadere.hummingbird.models.WatchingStatus;
import com.charlesmadere.hummingbird.networking.Api;
import com.charlesmadere.hummingbird.networking.ApiResponse;
import com.charlesmadere.hummingbird.views.AnimeLibraryEntryItemView;

import java.lang.ref.WeakReference;

public class AnimeLibraryFragment extends BaseLibraryFragment implements
        AnimeLibraryEntryItemView.Listeners, AnimeLibraryUpdateFragment.LibraryEntryListener {

    private static final String TAG = "AnimeLibraryFragment";
    private static final String KEY_WATCHING_STATUS = "WatchingStatus";

    private AnimeLibraryEntriesAdapter mAdapter;
    private WatchingStatus mWatchingStatus;


    public static AnimeLibraryFragment create(final WatchingStatus watchingStatus) {
        final Bundle args = new Bundle(1);
        args.putParcelable(KEY_WATCHING_STATUS, watchingStatus);

        final AnimeLibraryFragment fragment = new AnimeLibraryFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    protected void fetchLibraryEntries() {
        super.fetchLibraryEntries();
        Api.getAnimeLibraryEntries(getUsername(), mWatchingStatus, new GetLibraryEntriesListener(this));
    }

    @Override
    public AnimeLibraryEntriesAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    public AnimeLibraryEntry getAnimeLibraryEntry(final String libraryEntryId) {
        for (final AnimeLibraryEntry libraryEntry : mFeed.getAnimeLibraryEntries()) {
            if (libraryEntryId.equalsIgnoreCase(libraryEntry.getId())) {
                return libraryEntry;
            }
        }

        throw new RuntimeException("couldn't find AnimeLibraryEntry: \"" + libraryEntryId + '"');
    }

    @Override
    public String getFragmentName() {
        return TAG;
    }

    @Override
    public String[] getObjectCacheKeys() {
        return new String[] { getFragmentName(), getUsername(), mWatchingStatus.name() };
    }

    @Override
    public boolean isLibraryLoading() {
        return mRefreshLayout.isRefreshing();
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (isEditableLibrary()) {
            mAdapter = new AnimeLibraryEntriesAdapter(getContext(), this);
        } else {
            mAdapter = new AnimeLibraryEntriesAdapter(getContext());
        }

        mRecyclerView.setAdapter(mAdapter);

        mEmptyText.setText(mWatchingStatus.getEmptyTextResId());
        mErrorText.setText(mWatchingStatus.getErrorTextResId());

        mFeed = ObjectCache.get(this);

        if (mFeed == null || !mFeed.hasAnimeLibraryEntries()) {
            fetchLibraryEntries();
        } else {
            showLibraryEntries(mFeed);
        }
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Bundle args = getArguments();
        mWatchingStatus = args.getParcelable(KEY_WATCHING_STATUS);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            final Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_anime_library, container, false);
    }

    @Override
    public void onDeleteClick(final AnimeLibraryEntryItemView v) {
        final AnimeLibraryEntry libraryEntry = v.getLibraryEntry();

        new AlertDialog.Builder(getContext())
                .setMessage(R.string.are_you_sure_you_want_to_remove_this_from_your_library)
                .setNegativeButton(R.string.cancel, null)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        mRefreshLayout.setRefreshing(true);
                        Api.deleteAnimeLibraryEntry(libraryEntry, new DeleteLibraryEntryListener(
                                AnimeLibraryFragment.this));
                    }
                })
                .show();
    }

    @Override
    public void onEditClick(final AnimeLibraryEntryItemView v) {
        AnimeLibraryUpdateFragment.create(v.getLibraryEntry().getId()).show(
                getChildFragmentManager(), AnimeLibraryUpdateFragment.TAG);
    }

    @Override
    public void onPlusOneClick(final AnimeLibraryEntryItemView v) {
        final AnimeLibraryEntry entry = v.getLibraryEntry();
        final AnimeLibraryUpdate update = new AnimeLibraryUpdate(entry);
        update.setEpisodesWatched(update.getEpisodesWatched() + 1);

        mRefreshLayout.setRefreshing(true);
        Api.updateAnimeLibraryEntry(entry.getId(), update, new EditLibraryEntryListener(this));
    }

    @Override
    public void onUpdateLibraryEntry() {
        final AnimeLibraryUpdateFragment fragment = (AnimeLibraryUpdateFragment)
                getChildFragmentManager().findFragmentByTag(AnimeLibraryUpdateFragment.TAG);
        final AnimeLibraryUpdate update = fragment.getLibraryUpdate();
        final String entryId = fragment.getLibraryEntryId();

        mRefreshLayout.setRefreshing(true);
        Api.updateAnimeLibraryEntry(entryId, update, new EditLibraryEntryListener(this));
    }

    @Override
    protected void showLibraryEntries(final Feed feed) {
        mFeed = feed;
        mAdapter.set(mFeed, getLibrarySort());
        mEmpty.setVisibility(View.GONE);
        mError.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void updateLibrarySort() {
        if (mFeed != null && mFeed.hasAnimeLibraryEntries()) {
            showLibraryEntries(mFeed);
        }
    }


    private static class DeleteLibraryEntryListener implements ApiResponse<Void> {
        private final WeakReference<AnimeLibraryFragment> mFragmentReference;

        private DeleteLibraryEntryListener(final AnimeLibraryFragment fragment) {
            mFragmentReference = new WeakReference<>(fragment);
        }

        @Override
        public void failure(@Nullable final ErrorInfo error) {
            final AnimeLibraryFragment fragment = mFragmentReference.get();

            if (fragment != null && fragment.isAlive()) {
                fragment.showDeleteLibraryEntryError();
            }
        }

        @Override
        public void success(@Nullable final Void object) {
            final AnimeLibraryFragment fragment = mFragmentReference.get();

            if (fragment != null && fragment.isAlive()) {
                fragment.fetchLibraryEntries();
            }
        }
    }

    private static class EditLibraryEntryListener implements ApiResponse<AnimeLibraryEntryResponse> {
        private final WeakReference<AnimeLibraryFragment> mFragmentReference;

        private EditLibraryEntryListener(final AnimeLibraryFragment fragment) {
            mFragmentReference = new WeakReference<>(fragment);
        }

        @Override
        public void failure(@Nullable final ErrorInfo error) {
            final AnimeLibraryFragment fragment = mFragmentReference.get();

            if (fragment != null && fragment.isAlive()) {
                fragment.showEditLibraryEntryError();
            }
        }

        @Override
        public void success(final AnimeLibraryEntryResponse response) {
            final AnimeLibraryFragment fragment = mFragmentReference.get();

            if (fragment != null && fragment.isAlive()) {
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

            if (fragment != null && fragment.isAlive()) {
                fragment.showError();
            }
        }

        @Override
        public void success(final Feed feed) {
            final AnimeLibraryFragment fragment = mFragmentReference.get();

            if (fragment != null && fragment.isAlive()) {
                if (feed.hasAnimeLibraryEntries()) {
                    fragment.showLibraryEntries(feed);
                } else {
                    fragment.showEmpty();
                }
            }
        }
    }

}
