package com.charlesmadere.hummingbird.adapters;

import android.content.Context;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.models.AnimeEpisode;
import com.charlesmadere.hummingbird.models.Season;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AnimeEpisodesAdapter extends BaseMultiAdapter {

    public AnimeEpisodesAdapter(final Context context) {
        super(context);
    }

    @Override
    protected HashMap<Class, Integer> getViewKeyMap() {
        final HashMap<Class, Integer> viewKeyMap = new HashMap<>(2);
        viewKeyMap.put(AnimeEpisode.class, R.layout.item_anime_episode);
        viewKeyMap.put(Season.class, R.layout.item_season);
        return viewKeyMap;
    }

    public void setAnimeEpisodes(final List<AnimeEpisode> episodes) {
        // type hell

        boolean showSeasons = false;

        if (episodes.get(0).hasSeason()) {
            final int seasonNumber = episodes.get(0).getSeason();

            for (final AnimeEpisode episode : episodes) {
                if (seasonNumber != episode.getSeason()) {
                    showSeasons = true;
                    break;
                }
            }
        }

        if (!showSeasons) {
            super.set(new ArrayList<Object>(episodes));
            return;
        }

        final ArrayList list = new ArrayList();
        Season season = new Season(episodes.get(0).getSeason());
        list.add(season);

        for (final AnimeEpisode episode : episodes) {
            if (season.hasSeason() && season.getSeason() != episode.getSeason()) {
                season = new Season(episode.getSeason());
                list.add(season);
            }

            list.add(episode);
        }

        super.set(list);
    }

}
