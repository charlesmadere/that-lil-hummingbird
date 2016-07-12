package com.charlesmadere.hummingbird.misc;

import com.charlesmadere.hummingbird.models.AbsAnime;
import com.charlesmadere.hummingbird.models.AbsNotification;
import com.charlesmadere.hummingbird.models.AbsStory;
import com.charlesmadere.hummingbird.models.AbsSubstory;
import com.charlesmadere.hummingbird.models.MediaStory;
import com.charlesmadere.hummingbird.models.Rating;
import com.charlesmadere.hummingbird.models.ReadingStatus;
import com.charlesmadere.hummingbird.models.SearchBundle;
import com.charlesmadere.hummingbird.models.SimpleDate;
import com.charlesmadere.hummingbird.models.User;
import com.charlesmadere.hummingbird.models.UserDigest;
import com.charlesmadere.hummingbird.models.WatchingStatus;
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
                    .registerTypeAdapter(AbsNotification.class, AbsNotification.JSON_DESERIALIZER)
                    .registerTypeAdapter(AbsNotification.AbsSource.class, AbsNotification.AbsSource.JSON_DESERIALIZER)
                    .registerTypeAdapter(AbsStory.class, AbsStory.JSON_DESERIALIZER)
                    .registerTypeAdapter(AbsSubstory.class, AbsSubstory.JSON_DESERIALIZER)
                    .registerTypeAdapter(MediaStory.AbsMedia.class, MediaStory.AbsMedia.JSON_DESERIALIZER)
                    .registerTypeAdapter(Rating.class, Rating.JSON_DESERIALIZER)
                    .registerTypeAdapter(ReadingStatus.class, ReadingStatus.JSON_DESERIALIZER)
                    .registerTypeAdapter(SearchBundle.AbsResult.class, SearchBundle.AbsResult.JSON_DESERIALIZER)
                    .registerTypeAdapter(SimpleDate.class, SimpleDate.JSON_DESERIALIZER)
                    .registerTypeAdapter(User.class, User.JSON_DESERIALIZER)
                    .registerTypeAdapter(UserDigest.Favorite.AbsItem.class, UserDigest.Favorite.AbsItem.JSON_DESERIALIZER)
                    .registerTypeAdapter(WatchingStatus.class, WatchingStatus.JSON_DESERIALIZER)
                    .serializeNulls()
                    .create();
        }

        return sGson;
    }

}
