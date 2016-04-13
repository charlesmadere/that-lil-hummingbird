package com.charlesmadere.hummingbird.adapters;

import android.content.Context;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.models.LibraryEntry;

public class LibraryEntriesAdapter extends BaseAdapter<LibraryEntry> {

    public LibraryEntriesAdapter(final Context context) {
        super(context);
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.item_library_entry;
    }

}
