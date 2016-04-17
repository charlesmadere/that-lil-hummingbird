package com.charlesmadere.hummingbird.adapters;

import android.content.Context;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.models.AnimeEpisode;
import com.charlesmadere.hummingbird.models.Season;

import java.util.ArrayList;
import java.util.Collections;
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

        Collections.sort(episodes, AnimeEpisode.COMPARATOR);
        boolean showSeasons = false;

        if (episodes.get(0).getSeasonNumber() != null) {
            final int seasonNumber = episodes.get(0).getSeasonNumber();

            for (final AnimeEpisode episode : episodes) {
                if (episode.getSeasonNumber() != seasonNumber) {
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
        Season season = new Season(episodes.get(0).getSeasonNumber());
        list.add(season);

        for (final AnimeEpisode episode : episodes) {
            if (episode.getSeasonNumber() != season.getSeason()) {
                season = new Season(episode.getSeasonNumber());
                list.add(season);
            }

            list.add(episode);
        }

        super.set(list);
    }

}
