package com.charlesmadere.hummingbird.adapters;

import android.content.Context;
import android.text.TextUtils;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.models.AnimeDigest;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeMap;

public class AnimeCastingsAdapter extends BaseMultiAdapter implements Comparator<String> {

    public AnimeCastingsAdapter(final Context context) {
        super(context);
    }

    @Override
    public int compare(final String lhs, final String rhs) {
        if (TextUtils.isEmpty(lhs) && TextUtils.isEmpty(rhs)) {
            return 0;
        } else if (TextUtils.isEmpty(lhs)) {
            return Integer.MAX_VALUE;
        } else if (TextUtils.isEmpty(rhs)) {
            return Integer.MIN_VALUE;
        } else {
            return lhs.compareToIgnoreCase(rhs);
        }
    }

    @Override
    protected HashMap<Class, Integer> getItemViewKeyMap() {
        final HashMap<Class, Integer> map = new HashMap<>(2);
        map.put(AnimeDigest.Casting.class, R.layout.item_anime_casting);
        map.put(String.class, R.layout.item_charsequence_header);
        return map;
    }

    public void set(final ArrayList<AnimeDigest.Casting> castings) {
        final TreeMap<String, ArrayList<AnimeDigest.Casting>> map = new TreeMap<>(this);

        for (final AnimeDigest.Casting casting : castings) {
            final String language = casting.getLanguage();

            if (!map.containsKey(language)) {
                map.put(language, new ArrayList<AnimeDigest.Casting>());
            }

            map.get(language).add(casting);
        }

        final ArrayList<Object> list = new ArrayList<>();

        for (final String language : map.keySet()) {
            if (TextUtils.isEmpty(language)) {
                list.add(getContext().getString(R.string.other));
            } else {
                list.add(language);
            }

            list.addAll(map.get(language));
        }

        super.set(list);
    }

}
