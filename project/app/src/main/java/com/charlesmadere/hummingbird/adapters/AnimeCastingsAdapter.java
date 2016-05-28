package com.charlesmadere.hummingbird.adapters;

import android.content.Context;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.models.AnimeDigest;

import java.util.ArrayList;
import java.util.HashMap;

public class AnimeCastingsAdapter extends BaseMultiAdapter {

    public AnimeCastingsAdapter(final Context context) {
        super(context);
    }

    @Override
    protected HashMap<Class, Integer> getViewKeyMap() {
        final HashMap<Class, Integer> map = new HashMap<>(2);
        map.put(AnimeDigest.Casting.class, R.layout.item_anime_casting);
        map.put(String.class, R.layout.item_string);
        return map;
    }

    public void set(final ArrayList<AnimeDigest.Casting> castings) {
        final HashMap<String, ArrayList<AnimeDigest.Casting>> map = new HashMap<>();

        for (final AnimeDigest.Casting casting : castings) {
            final String language = casting.getLanguage();

            if (!map.containsKey(language)) {
                map.put(language, new ArrayList<AnimeDigest.Casting>());
            }

            map.get(language).add(casting);
        }

        final ArrayList<Object> list = new ArrayList<>();

        for (final String language : map.keySet()) {
            list.add(language);
            list.addAll(map.get(language));
        }

        super.set(list);
    }

}
