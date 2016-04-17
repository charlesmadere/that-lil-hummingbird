package com.charlesmadere.hummingbird.misc;

import android.support.annotation.Nullable;

import com.charlesmadere.hummingbird.models.AbsAnime;
import com.charlesmadere.hummingbird.models.AnimeV2;
import com.charlesmadere.hummingbird.models.SimpleDate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

public final class GsonUtils {

    private static final String TAG = "GsonUtils";

    private static Gson sGson;


    public static synchronized Gson getGson() {
        if (sGson == null) {
            Timber.d(TAG, "creating Gson instance");
            sGson = new GsonBuilder()
                    .registerTypeAdapter(AbsAnime.class, AbsAnime.JSON_DESERIALIZER)
                    .registerTypeAdapter(AnimeV2.class, AnimeV2.JSON_DESERIALIZER)
                    .registerTypeAdapter(SimpleDate.class, SimpleDate.JSON_DESERIALIZER)
                    .create();
        }

        return sGson;
    }

    @Nullable
    public static ArrayList<String> getStringArrayList(final JsonObject json, final String name) {
        if (json.has(name)) {
            final JsonArray array = json.getAsJsonArray(name);
            final ArrayList<String> strings = new ArrayList<>(array.size());

            for (int i = 0; i < array.size(); ++i) {
                strings.add(array.get(i).getAsString());
            }

            return strings;
        } else {
            return null;
        }
    }

}
