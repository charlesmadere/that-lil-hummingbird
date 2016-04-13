package com.charlesmadere.hummingbird.misc;

import com.charlesmadere.hummingbird.models.AbsAnime;
import com.charlesmadere.hummingbird.models.SimpleDate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public final class GsonUtils {

    private static final String TAG = "GsonUtils";

    private static Gson sGson;


    public static synchronized Gson getGson() {
        if (sGson == null) {
            Timber.d(TAG, "creating Gson instance");
            sGson = new GsonBuilder()
                    .registerTypeAdapter(AbsAnime.class, AbsAnime.JSON_DESERIALIZER)
                    .registerTypeAdapter(SimpleDate.class, SimpleDate.JSON_DESERIALIZER)
                    .create();
        }

        return sGson;
    }

}
