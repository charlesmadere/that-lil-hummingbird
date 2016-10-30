package com.charlesmadere.hummingbird.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.models.Anime;

public class AnimeAdapter extends BaseAdapter<Anime> {

    public AnimeAdapter(final Context context) {
        super(context);
        setHasStableIds(true);
    }

    @Override
    public long getItemId(final int position) {
        return getItem(position).hashCode();
    }

    @LayoutRes
    @Override
    public int getItemViewType(final int position) {
        return R.layout.item_anime;
    }

}
