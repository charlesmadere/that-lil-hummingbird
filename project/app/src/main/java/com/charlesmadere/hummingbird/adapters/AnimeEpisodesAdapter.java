package com.charlesmadere.hummingbird.adapters;

import android.content.Context;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.models.AnimeEpisode;

public class AnimeEpisodesAdapter extends BaseAdapter<AnimeEpisode> {

    public AnimeEpisodesAdapter(final Context context) {
        super(context);
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.item_anime_episode;
    }

}
