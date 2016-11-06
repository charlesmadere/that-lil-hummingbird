package com.charlesmadere.hummingbird.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.models.Liker;

public class LikersAdapter extends BasePaginationAdapter<Liker> {

    public LikersAdapter(final Context context) {
        super(context);
        setHasStableIds(true);
    }

    @Override
    public long getItemId(final int position) {
        if (isPaginating() && position == getItemCount() - 1) {
            return Long.MIN_VALUE;
        } else {
            return getItem(position).hashCode();
        }
    }

    @LayoutRes
    @Override
    public int getItemViewTypeForPosition(final int position) {
        return R.layout.item_liker;
    }

}
