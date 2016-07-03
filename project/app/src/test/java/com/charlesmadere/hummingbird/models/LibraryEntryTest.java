package com.charlesmadere.hummingbird.models;

import android.os.Build;

import com.charlesmadere.hummingbird.BuildConfig;
import com.charlesmadere.hummingbird.misc.GsonUtils;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.M)
public class LibraryEntryTest {

    private static final String JSON_LIBRARY_ENTRY = "{\"id\":13406029,\"episodes_watched\":0,\"last_watched\":\"2016-06-08T21:37:42.787Z\",\"updated_at\":\"2016-06-08T21:37:44.640Z\",\"rewatched_times\":0,\"notes\":null,\"notes_present\":null,\"status\":\"currently-watching\",\"private\":false,\"rewatching\":false,\"anime\":{\"id\":11818,\"mal_id\":32681,\"slug\":\"uchuu-patrol-luluco\",\"status\":\"Finished Airing\",\"url\":\"https://hummingbird.me/anime/uchuu-patrol-luluco\",\"title\":\"Uchuu Patrol Luluco\",\"alternate_title\":\"Space Patrol Luluco\",\"episode_count\":13,\"episode_length\":8,\"cover_image\":\"https://static.hummingbird.me/anime/poster_images/000/011/818/large/luluco-2nd-kv.jpg?1458325087\",\"synopsis\":\"\\\"My First Love, Big Bang.\\\"\\r\\n\\r\\nThe headquarters of the space patrol is said to be located in the center of this galactic system. The story is set in \\\"OGIKUBO,\\\" a special ward for remote space immigrants in the solar system, designated by the space patrol. 20 years after the immigration began, space slums have been created, which have the lowest level of the space deviation.\\r\\n\\r\\nLuluco is a normal junior high school girl whose father is a detective at the space patrol. One day, she visits the Ogikubo branch of the space patrol to save her father who is suddenly frozen. But she happens to be assigned as a space patrol by the chief director Overjustice. Wearing a space patrol suit with strange functions, will she able to save her father and keep the peace of Ogikubo from space criminals? And will she fulfill her life as a junior high school student, including love and study?\\r\\n\\r\\n(Source: Crunchyroll)\",\"show_type\":\"TV\",\"started_airing\":\"2016-04-01\",\"finished_airing\":\"2016-06-24\",\"community_rating\":4.02510471338673,\"age_rating\":\"PG13\",\"genres\":[{\"name\":\"Action\"},{\"name\":\"Adventure\"},{\"name\":\"Comedy\"},{\"name\":\"Sci-Fi\"},{\"name\":\"Space\"},{\"name\":\"Romance\"},{\"name\":\"Parody\"}]},\"rating\":{\"type\":\"simple\",\"value\":\"5.0\"}}";

    private static LibraryEntry sLibraryEntry;


    public static LibraryEntry get() {
        if (sLibraryEntry == null) {
            sLibraryEntry = GsonUtils.getGson().fromJson(JSON_LIBRARY_ENTRY, LibraryEntry.class);
        }

        return sLibraryEntry;
    }

}
