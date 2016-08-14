package com.charlesmadere.hummingbird.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.models.AnimeLibraryEntry;
import com.charlesmadere.hummingbird.models.Feed;
import com.charlesmadere.hummingbird.models.LibrarySort;
import com.charlesmadere.hummingbird.views.AnimeLibraryEntryItemView;

import java.util.ArrayList;
import java.util.Collections;

public class AnimeLibraryEntriesAdapter extends BasePaginationAdapter<AnimeLibraryEntry> {

    private final AnimeLibraryEntryItemView.OnDeleteClickListener mDeleteClickListener;
    private final AnimeLibraryEntryItemView.OnEditClickListener mEditClickListener;


    public AnimeLibraryEntriesAdapter(final Context context) {
        this(context, null, null);
    }

    public AnimeLibraryEntriesAdapter(final Context context,
            @Nullable final AnimeLibraryEntryItemView.OnDeleteClickListener deleteClickListener,
            @Nullable final AnimeLibraryEntryItemView.OnEditClickListener editClickListener) {
        super(context);
        mDeleteClickListener = deleteClickListener;
        mEditClickListener = editClickListener;
    }

    @Override
    public int getItemViewTypeForPosition(final int position) {
        return R.layout.item_anime_library_entry;
    }

    @Override
    public AdapterView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final AdapterView.ViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);
        final AnimeLibraryEntryItemView view = (AnimeLibraryEntryItemView) viewHolder.itemView;
        view.setOnDeleteClickListener(mDeleteClickListener);
        view.setOnEditClickListener(mEditClickListener);
        return viewHolder;
    }

    public void set(final Feed feed, final LibrarySort sort) {
        if (feed == null || !feed.hasAnimeLibraryEntries()) {
            super.set(null);
            return;
        }

        final ArrayList<AnimeLibraryEntry> entries = new ArrayList<>(feed.getAnimeLibraryEntries());

        switch (sort) {
            case DATE:
                Collections.sort(entries, AnimeLibraryEntry.DATE);
                break;

            case RATING:
                Collections.sort(entries, AnimeLibraryEntry.RATING);
                break;

            case TITLE:
                Collections.sort(entries, AnimeLibraryEntry.TITLE);
                break;

            default:
                throw new IllegalArgumentException("encountered illegal " +
                        LibrarySort.class.getName() + ": \"" + sort + '"');
        }

        super.set(entries);
    }

}
