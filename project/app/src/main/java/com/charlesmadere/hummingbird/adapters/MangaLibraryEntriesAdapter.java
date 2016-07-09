package com.charlesmadere.hummingbird.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.models.Feed;
import com.charlesmadere.hummingbird.models.MangaLibraryEntry;
import com.charlesmadere.hummingbird.views.MangaLibraryEntryItemView;

public class MangaLibraryEntriesAdapter extends BaseAdapter<MangaLibraryEntry> {

    private final MangaLibraryEntryItemView.OnEditClickListener mEditClickListener;


    public MangaLibraryEntriesAdapter(final Context context) {
        this(context, null);
    }

    public MangaLibraryEntriesAdapter(final Context context, final
            @Nullable MangaLibraryEntryItemView.OnEditClickListener editClickListener) {
        super(context);
        mEditClickListener = editClickListener;
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.item_manga_library_entry;
    }

    @Override
    public AdapterView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final AdapterView.ViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);
        final MangaLibraryEntryItemView view = (MangaLibraryEntryItemView) viewHolder.itemView;
        view.setOnEditClickListener(mEditClickListener);
        return viewHolder;
    }

    public void set(final Feed feed) {
        super.set(feed.getMangaLibraryEntries());
    }

}
