package com.charlesmadere.hummingbird.adapters;

import android.content.Context;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.models.SearchBundle;
import com.charlesmadere.hummingbird.models.SearchScope;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchResultsAdapter extends BaseMultiAdapter {

    public SearchResultsAdapter(final Context context) {
        super(context);
    }

    private void buildSectionedList(final SearchBundle searchBundle) {
        if (searchBundle == null || !searchBundle.hasResults()) {
            super.set(null);
            return;
        }

        final ArrayList<Object> list = new ArrayList<>();

        final ArrayList<SearchBundle.AnimeResult> anime = searchBundle.getAnimeResults();
        if (anime != null && !anime.isEmpty()) {
            list.add(getContext().getString(SearchBundle.AbsResult.Type.ANIME.getTextResId()));
            list.addAll(anime);
        }

        final ArrayList<SearchBundle.GroupResult> groups = searchBundle.getGroupResults();
        if (groups != null && !groups.isEmpty()) {
            list.add(getContext().getString(SearchBundle.AbsResult.Type.GROUP.getTextResId()));
            list.addAll(groups);
        }

        final ArrayList<SearchBundle.MangaResult> manga = searchBundle.getMangaResults();
        if (manga != null && !manga.isEmpty()) {
            list.add(getContext().getString(SearchBundle.AbsResult.Type.MANGA.getTextResId()));
            list.addAll(manga);
        }

        final ArrayList<SearchBundle.UserResult> users = searchBundle.getUserResults();
        if (users != null && !users.isEmpty()) {
            list.add(getContext().getString(SearchBundle.AbsResult.Type.USER.getTextResId()));
            list.addAll(users);
        }

        super.set(list);
    }

    private void buildUnsectionedList(final SearchBundle searchBundle) {
        if (searchBundle == null || !searchBundle.hasResults()) {
            super.set(null);
        } else {
            super.set(new ArrayList<Object>(searchBundle.getResults()));
        }
    }

    @Override
    protected HashMap<Class, Integer> getItemViewKeyMap() {
        final HashMap<Class, Integer> map = new HashMap<>(5);
        map.put(String.class, R.layout.item_charsequence_card);
        map.put(SearchBundle.AnimeResult.class, R.layout.item_anime_result);
        map.put(SearchBundle.GroupResult.class, R.layout.item_group_result);
        map.put(SearchBundle.MangaResult.class, R.layout.item_manga_result);
        map.put(SearchBundle.UserResult.class, R.layout.item_user_result);
        return map;
    }

    public void set(final SearchBundle searchBundle, final SearchScope searchScope) {
        if (searchScope == SearchScope.ALL) {
            buildSectionedList(searchBundle);
        } else {
            buildUnsectionedList(searchBundle);
        }
    }

}
