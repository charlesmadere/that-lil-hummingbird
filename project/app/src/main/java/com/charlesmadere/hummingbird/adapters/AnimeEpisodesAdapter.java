package com.charlesmadere.hummingbird.adapters;

import android.content.Context;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.models.AnimeDigest;

public class AnimeEpisodesAdapter extends BaseAdapter<AnimeDigest.Episode> {

    public AnimeEpisodesAdapter(final Context context) {
        super(context);
    }

    @Override
    public int getItemViewType(final int position) {
        if (getItem(position).hasThumbnail()) {
            return R.layout.item_anime_episode_artsy;
        } else {
            return R.layout.item_anime_episode_plain;
        }
    }

}
