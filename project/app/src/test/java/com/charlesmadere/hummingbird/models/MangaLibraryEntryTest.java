package com.charlesmadere.hummingbird.models;

import android.os.Build;

import com.charlesmadere.hummingbird.BuildConfig;
import com.charlesmadere.hummingbird.misc.GsonUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertFalse;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.M)
public class MangaLibraryEntryTest {

    private static final String JSON_LIBRARY_ENTRY = "{\"id\":741276,\"status\":\"Plan to Read\",\"is_favorite\":false,\"rating\":null,\"notes\":null,\"chapters_read\":0,\"volumes_read\":0,\"rereading\":false,\"reread_count\":0,\"last_read\":\"2016-07-04T20:13:46.746Z\",\"manga_id\":\"sword-art-online-aincrad\",\"mManga\":{\"id\":\"sword-art-online-aincrad\",\"romaji_title\":\"Sword Art Online: Aincrad\",\"poster_image\":\"https://static.hummingbird.me/manga/poster_images/000/015/506/large/77727.jpg?1434283429\",\"poster_image_thumb\":\"https://static.hummingbird.me/manga/poster_images/000/015/506/large/77727.jpg?1434283429\",\"synopsis\":\"The only way to escape is to ‘clear’ the game. Game over means actual ‘death’ —-\\nThe ten thousand who have logged onto the as of yet mysterious game ‘Sword Art Online’ have been forced into this perilous death game.\\nProtagonist Kirito, one of the many gamers, has greeted this ‘truth’.\\nHe plays as a solo player in the giant castle that is the stage for this game —- ‘Aincrad’.\\nTo meet the conditions of clearing the game — getting through all 100 floors, Kirito fights his way through this quest alone.\\n(Source: Riceballicious)\",\"chapter_count\":12,\"volume_count\":2,\"genres\":[\"Action\",\"Adventure\",\"Fantasy\",\"Romance\",\"Sci-Fi\"],\"manga_type\":\"Manga\",\"updated_at\":\"2015-06-14T12:03:50.468Z\",\"cover_image\":\"/cover_images/original/missing.png\",\"cover_image_top_offset\":0}}";

    private MangaLibraryEntry mLibraryEntry;


    public static MangaLibraryEntry get() throws Exception {
        final MangaLibraryEntryTest test = new MangaLibraryEntryTest();
        test.setUp();
        return test.mLibraryEntry;
    }

    @Before
    public void setUp() throws Exception {
        mLibraryEntry = GsonUtils.getGson().fromJson(JSON_LIBRARY_ENTRY, MangaLibraryEntry.class);
    }

    @Test
    public void testHasNotes() throws Exception {
        assertFalse(mLibraryEntry.hasNotes());
    }

    @Test
    public void testHasRating() throws Exception {
        assertFalse(mLibraryEntry.hasRating());
    }

}
