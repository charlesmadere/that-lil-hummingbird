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
        final ArrayList<Object> animeList = new ArrayList<>();
        final ArrayList<SearchBundle.AnimeResult> anime = searchBundle.getAnimeResults();
        if (anime != null && !anime.isEmpty()) {
            animeList.add(SearchBundle.AbsResult.Type.ANIME);
            animeList.addAll(anime);
        }

        final ArrayList<Object> groupsList = new ArrayList<>();
        final ArrayList<SearchBundle.GroupResult> groups = searchBundle.getGroupResults();
        if (groups != null && !groups.isEmpty()) {
            groupsList.add(SearchBundle.AbsResult.Type.GROUP);
            groupsList.addAll(groups);
        }

        final ArrayList<Object> mangaList = new ArrayList<>();
        final ArrayList<SearchBundle.MangaResult> manga = searchBundle.getMangaResults();
        if (manga != null && !manga.isEmpty()) {
            mangaList.add(SearchBundle.AbsResult.Type.MANGA);
            mangaList.addAll(manga);
        }

        final ArrayList<Object> usersList = new ArrayList<>();
        final ArrayList<SearchBundle.UserResult> users = searchBundle.getUserResults();
        if (users != null && !users.isEmpty()) {
            usersList.add(SearchBundle.AbsResult.Type.USER);
            usersList.addAll(users);
        }

        final ArrayList<Object> list = new ArrayList<>();

        if (!animeList.isEmpty()) {
            list.addAll(animeList);
        }

        if (groupsList.isEmpty()) {
            list.addAll(groupsList);
        }

        if (!mangaList.isEmpty()) {
            list.addAll(mangaList);
        }

        if (!usersList.isEmpty()) {
            list.addAll(usersList);
        }

        list.trimToSize();
        super.set(list);
    }

    private void buildUnsectionedList(final SearchBundle searchBundle) {
        final ArrayList<Object> list;

        if (searchBundle.hasResults()) {
            list = new ArrayList<Object>(searchBundle.getResults());
        } else {
            list = null;
        }

        super.set(list);
    }

    @Override
    protected HashMap<Class, Integer> getItemViewKeyMap() {
        final HashMap<Class, Integer> map = new HashMap<>(5);
        map.put(SearchBundle.AbsResult.Type.class, R.layout.item_search_result_type);
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
