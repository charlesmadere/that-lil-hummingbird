package com.charlesmadere.hummingbird.models;

import android.os.Build;

import com.charlesmadere.hummingbird.BuildConfig;
import com.charlesmadere.hummingbird.misc.GsonUtils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.M)
public class AbsAnimeTest {

    private static final String JSON_ANIME_V1 = "{\"id\":11818,\"mal_id\":32681,\"slug\":\"uchuu-patrol-luluco\",\"status\":\"Finished Airing\",\"url\":\"https://hummingbird.me/anime/uchuu-patrol-luluco\",\"title\":\"Uchuu Patrol Luluco\",\"alternate_title\":\"Space Patrol Luluco\",\"episode_count\":13,\"episode_length\":8,\"cover_image\":\"https://static.hummingbird.me/anime/poster_images/000/011/818/large/luluco-2nd-kv.jpg?1458325087\",\"synopsis\":\"\\\"My First Love, Big Bang.\\\"\\r\\n\\r\\nThe headquarters of the space patrol is said to be located in the center of this galactic system. The story is set in \\\"OGIKUBO,\\\" a special ward for remote space immigrants in the solar system, designated by the space patrol. 20 years after the immigration began, space slums have been created, which have the lowest level of the space deviation.\\r\\n\\r\\nLuluco is a normal junior high school girl whose father is a detective at the space patrol. One day, she visits the Ogikubo branch of the space patrol to save her father who is suddenly frozen. But she happens to be assigned as a space patrol by the chief director Overjustice. Wearing a space patrol suit with strange functions, will she able to save her father and keep the peace of Ogikubo from space criminals? And will she fulfill her life as a junior high school student, including love and study?\\r\\n\\r\\n(Source: Crunchyroll)\",\"show_type\":\"TV\",\"started_airing\":\"2016-04-01\",\"finished_airing\":\"2016-06-24\",\"community_rating\":4.02510471338673,\"age_rating\":\"PG13\",\"genres\":[{\"name\":\"Action\"},{\"name\":\"Adventure\"},{\"name\":\"Comedy\"},{\"name\":\"Sci-Fi\"},{\"name\":\"Space\"},{\"name\":\"Romance\"},{\"name\":\"Parody\"}]}";
    private static final String JSON_ANIME_V2 = "{\"id\":\"rwby\",\"canonical_title\":\"RWBY\",\"english_title\":\"\",\"romaji_title\":\"RWBY\",\"synopsis\":\"The story takes place in a world filled with monsters and supernatural forces. The series focuses on four girls, each with their own unique weapon and powers, who come together as a team at Beacon Academy in a place called Vale where they are trained to battle these forces alongside other similar teams. Prior to the events of the series, mankind waged a battle of survival against the shadowy creatures of the Grimm until they discovered the power of Dust, which allowed them to fight back the monsters. Dust is used to power magical abilities in the series.\",\"poster_image\":\"https://static.hummingbird.me/anime/poster_images/000/007/929/large/2e97c8a8938535beb4c86d2e4a50e0cb1376689737_full.jpg?1408462956\",\"poster_image_thumb\":\"https://static.hummingbird.me/anime/poster_images/000/007/929/medium/2e97c8a8938535beb4c86d2e4a50e0cb1376689737_full.jpg?1408462956\",\"show_type\":\"ONA\",\"age_rating\":\"PG13\",\"age_rating_guide\":\"Teens 13 or older\",\"episode_count\":16,\"episode_length\":9,\"started_airing\":\"2013-07-18\",\"started_airing_date_known\":true,\"finished_airing\":\"2013-11-07\",\"genres\":[\"Action\",\"Adventure\",\"Anime Influenced\",\"Fantasy\"],\"updated_at\":\"2016-07-01T00:07:03.464Z\"}";

    private static AnimeV1 sAnimeV1;
    private static AnimeV2 sAnimeV2;


    public static AnimeV1 getV1() {
        if (sAnimeV1 == null) {
            sAnimeV1 = GsonUtils.getGson().fromJson(JSON_ANIME_V1, AnimeV1.class);
        }

        return sAnimeV1;
    }

    public static AnimeV2 getV2() {
        if (sAnimeV2 == null) {
            sAnimeV2 = GsonUtils.getGson().fromJson(JSON_ANIME_V2, AnimeV2.class);
        }

        return sAnimeV2;
    }

    @Test
    public void testConstructV1() throws Exception {
        final AbsAnime animeV1 = GsonUtils.getGson().fromJson(JSON_ANIME_V1, AbsAnime.class);
        assertNotNull(animeV1);
        assertTrue(AbsAnime.Version.V1 == animeV1.getVersion());
    }

    @Test
    public void testConstructV2() throws Exception {
        final AbsAnime animeV2 = GsonUtils.getGson().fromJson(JSON_ANIME_V2, AbsAnime.class);
        assertNotNull(animeV2);
        assertTrue(AbsAnime.Version.V2 == animeV2.getVersion());
    }

}
