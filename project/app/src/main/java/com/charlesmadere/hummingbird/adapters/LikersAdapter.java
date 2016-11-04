package com.charlesmadere.hummingbird.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.models.Liker;

public class LikersAdapter extends BasePaginationAdapter<Liker> {

    public LikersAdapter(final Context context) {
        super(context);
    }

    @LayoutRes
    @Override
    public int getItemViewTypeForPosition(final int position) {
        return R.layout.item_liker;
    }

}
