package com.charlesmadere.hummingbird.models;

import android.os.Build;

import com.charlesmadere.hummingbird.BuildConfig;
import com.charlesmadere.hummingbird.misc.GsonUtils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.M)
public class AnimeLibraryEntryTest {

    private static final String JSON_LIBRARY_ENTRY = "{\"id\":12956689,\"status\":\"Plan to Watch\",\"is_favorite\":false,\"rating\":\"5.0\",\"episodes_watched\":12,\"private\":true,\"rewatching\":false,\"rewatch_count\":0,\"last_watched\":\"2016-07-07T22:49:24.563Z\",\"anime_id\":\"non-non-biyori\",\"mAnime\":{\"id\":\"non-non-biyori\",\"canonical_title\":\"Non Non Biyori\",\"english_title\":\"\",\"romaji_title\":\"Non Non Biyori\",\"synopsis\":\"Elementary school student Ichijou Hotaru has moved with her parents from Tokyo to the middle of the country. Now she must adapt to her new school, where there are a total of 5 students in the same class who range through elementary and middle school ages. Join their everyday adventures in the countryside. \\r\\n(Source: MangaHelpers)\",\"poster_image\":\"https://static.hummingbird.me/anime/poster_images/000/007/711/large/9152138980_dbc9a427bf_b.jpg?1416279437\",\"poster_image_thumb\":\"https://static.hummingbird.me/anime/poster_images/000/007/711/medium/9152138980_dbc9a427bf_b.jpg?1416279437\",\"show_type\":\"TV\",\"age_rating\":\"PG13\",\"age_rating_guide\":\"Teens 13 or older\",\"episode_count\":12,\"episode_length\":24,\"started_airing\":\"2013-10-08\",\"started_airing_date_known\":true,\"finished_airing\":\"2013-12-24\",\"genres\":[\"Comedy\",\"School\",\"Slice of Life\"],\"updated_at\":\"2016-07-07T00:31:54.149Z\"}}";

    private static AnimeLibraryEntry sLibraryEntry;


    public static AnimeLibraryEntry get() {
        if (sLibraryEntry == null) {
            sLibraryEntry = GsonUtils.getGson().fromJson(JSON_LIBRARY_ENTRY, AnimeLibraryEntry.class);
        }

        return sLibraryEntry;
    }

    @Test
    public void testHasNotes() throws Exception {
        assertFalse(get().hasNotes());
    }

    @Test
    public void testHasRating() throws Exception {
        assertTrue(get().hasRating());
    }

}
