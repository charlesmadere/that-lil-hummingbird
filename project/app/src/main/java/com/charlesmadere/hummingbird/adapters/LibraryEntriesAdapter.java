package com.charlesmadere.hummingbird.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.models.LibraryEntry;
import com.charlesmadere.hummingbird.views.InternalAnimeItemView;
import com.charlesmadere.hummingbird.views.LibraryEntryItemView;

public class LibraryEntriesAdapter extends BaseAdapter<LibraryEntry> {

    private final InternalAnimeItemView.OnEditClickListener mEditClickListener;


    public LibraryEntriesAdapter(final Context context) {
        this(context, null);
    }

    public LibraryEntriesAdapter(final Context context,
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
        final LibraryEntryItemView view = (LibraryEntryItemView) viewHolder.itemView;
        view.setOnEditClickListener(mEditClickListener);
        return viewHolder;
    }

}
