package com.charlesmadere.hummingbird.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.models.UserDigest;

public class FavoriteMangaAdapter extends BaseAdapter<UserDigest.Favorite.MangaItem> {

    public FavoriteMangaAdapter(final Context context) {
        super(context);
    }

    @LayoutRes
    @Override
    public int getItemViewType(final int position) {
        return R.layout.item_favorite_manga_view;
    }

}
