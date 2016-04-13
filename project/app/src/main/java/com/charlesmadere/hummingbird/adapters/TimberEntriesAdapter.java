package com.charlesmadere.hummingbird.adapters;

import android.content.Context;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.misc.Timber;

public class TimberEntriesAdapter extends BaseAdapter<Timber.BaseEntry> {

    public TimberEntriesAdapter(final Context context) {
        super(context);
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.item_timber_entry;
    }

}
