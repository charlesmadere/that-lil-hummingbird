package com.charlesmadere.hummingbird.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.models.AnimeLibraryEntry;
import com.charlesmadere.hummingbird.views.AnimeLibraryEntryItemView;
import com.charlesmadere.hummingbird.views.InternalAnimeItemView;

public class AnimeLibraryEntriesAdapter extends BaseAdapter<AnimeLibraryEntry> {

    private final InternalAnimeItemView.OnEditClickListener mEditClickListener;


    public AnimeLibraryEntriesAdapter(final Context context) {
        this(context, null);
    }

    public AnimeLibraryEntriesAdapter(final Context context,
            @Nullable final InternalAnimeItemView.OnEditClickListener editClickListener) {
        super(context);
        mEditClickListener = editClickListener;
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.item_library_entry;
    }

    @Override
    public AdapterView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final AdapterView.ViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);
        final AnimeLibraryEntryItemView view = (AnimeLibraryEntryItemView) viewHolder.itemView;
        view.setOnEditClickListener(mEditClickListener);
        return viewHolder;
    }

}
