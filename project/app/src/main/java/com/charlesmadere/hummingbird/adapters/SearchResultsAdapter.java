package com.charlesmadere.hummingbird.adapters;

import android.content.Context;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.models.SearchBundle;

import java.util.HashMap;

public class SearchResultsAdapter extends BaseMultiAdapter {

    public SearchResultsAdapter(final Context context) {
        super(context);
    }

    @Override
    protected HashMap<Class, Integer> getViewKeyMap() {
        final HashMap<Class, Integer> map = new HashMap<>(4);
        map.put(SearchBundle.AnimeResult.class, R.layout.item_anime_result);
        map.put(SearchBundle.GroupResult.class, R.layout.item_group_result);
        map.put(SearchBundle.MangaResult.class, R.layout.item_manga_result);
        map.put(SearchBundle.UserResult.class, R.layout.item_user_result);
        return map;
    }

}
