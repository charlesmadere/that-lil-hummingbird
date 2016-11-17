package com.charlesmadere.hummingbird.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.models.Feed;
import com.charlesmadere.hummingbird.models.LibrarySort;
import com.charlesmadere.hummingbird.models.MangaLibraryEntry;
import com.charlesmadere.hummingbird.views.MangaLibraryEntryItemView;

import java.util.ArrayList;
import java.util.Collections;

public class MangaLibraryEntriesAdapter extends BaseAdapter<MangaLibraryEntry> {

    private final MangaLibraryEntryItemView.Listeners mListeners;


    public MangaLibraryEntriesAdapter(final Context context) {
        this(context, null);
    }

    public MangaLibraryEntriesAdapter(final Context context,
            @Nullable final MangaLibraryEntryItemView.Listeners listeners) {
        super(context);
        mListeners = listeners;
        setHasStableIds(true);
    }

    @Override
    public long getItemId(final int position) {
        return getItem(position).hashCode();
    }

    @LayoutRes
    @Override
    public int getItemViewType(final int position) {
        return R.layout.item_manga_library_entry;
    }

    @Override
    public AdapterView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final AdapterView.ViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);
        final MangaLibraryEntryItemView view = (MangaLibraryEntryItemView) viewHolder.itemView;
        view.setListeners(mListeners);
        return viewHolder;
    }

    public void set(@Nullable final Feed feed, final LibrarySort sort) {
        if (feed == null || !feed.hasMangaLibraryEntries()) {
            super.set(null);
            return;
        }

        // noinspection ConstantConditions
        final ArrayList<MangaLibraryEntry> entries = new ArrayList<>(feed.getMangaLibraryEntries());

        switch (sort) {
            case DATE:
                Collections.sort(entries, MangaLibraryEntry.DATE);
                break;

            case RATING:
                Collections.sort(entries, MangaLibraryEntry.RATING);
                break;

            case TITLE:
                Collections.sort(entries, MangaLibraryEntry.TITLE);
                break;

            default:
                throw new IllegalArgumentException("encountered illegal " +
                        LibrarySort.class.getName() + ": \"" + sort + '"');
        }

        super.set(entries);
    }

}
