package com.charlesmadere.hummingbird.adapters;

import android.content.Context;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.models.AnimeDigest;

public class AnimeCastingsAdapter extends BaseAdapter<AnimeDigest.Casting> {

    public AnimeCastingsAdapter(final Context context) {
        super(context);
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.item_anime_casting;
    }

}
