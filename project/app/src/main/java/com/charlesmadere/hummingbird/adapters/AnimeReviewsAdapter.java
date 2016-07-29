package com.charlesmadere.hummingbird.adapters;

import android.content.Context;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.models.AnimeReview;

public class AnimeReviewsAdapter extends BaseAdapter<AnimeReview> {

    public AnimeReviewsAdapter(final Context context) {
        super(context);
        setHasStableIds(true);
    }

    @Override
    public long getItemId(final int position) {
        return getItem(position).hashCode();
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.item_anime_review;
    }

}
