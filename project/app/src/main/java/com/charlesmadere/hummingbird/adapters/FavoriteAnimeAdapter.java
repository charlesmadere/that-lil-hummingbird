package com.charlesmadere.hummingbird.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;

import com.charlesmadere.hummingbird.models.UserDigest;

public class FavoriteAnimeAdapter extends BaseAdapter<UserDigest.Favorite.AnimeItem> {

    public FavoriteAnimeAdapter(final Context context) {
        super(context);
    }

    @LayoutRes
    @Override
    public int getItemViewType(final int position) {
        return 0;
    }

}
