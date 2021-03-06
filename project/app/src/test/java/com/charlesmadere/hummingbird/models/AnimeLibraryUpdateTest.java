package com.charlesmadere.hummingbird.models;

import android.os.Build;

import com.charlesmadere.hummingbird.BuildConfig;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.M)
public class AnimeLibraryUpdateTest {

    private AnimeLibraryUpdate mLibraryUpdate;


    @Before
    public void setUp() throws Exception {
        mLibraryUpdate = new AnimeLibraryUpdate(AnimeLibraryEntryTest.get());
    }

    @Test
    public void testContainsModifications() throws Exception {
        assertFalse(mLibraryUpdate.containsModifications());
    }

    @Test
    public void testSetEpisodesWatched() throws Exception {
        assertFalse(mLibraryUpdate.containsModifications());

        mLibraryUpdate.setEpisodesWatched(mLibraryUpdate.getEpisodesWatched());
        assertFalse(mLibraryUpdate.containsModifications());

        mLibraryUpdate.setEpisodesWatched(mLibraryUpdate.getEpisodesWatched() + 1);
        assertTrue(mLibraryUpdate.containsModifications());

        mLibraryUpdate.setEpisodesWatched(mLibraryUpdate.getDefaults().getEpisodesWatched());
        assertFalse(mLibraryUpdate.containsModifications());

        mLibraryUpdate.setEpisodesWatched(mLibraryUpdate.getEpisodesWatched() + 2);
        assertTrue(mLibraryUpdate.containsModifications());
    }

    @Test
    public void testSetNotes() throws Exception {
        assertFalse(mLibraryUpdate.containsModifications());

        mLibraryUpdate.setNotes(mLibraryUpdate.getNotes());
        assertFalse(mLibraryUpdate.containsModifications());

        mLibraryUpdate.setNotes("Hello, World!");
        assertTrue(mLibraryUpdate.containsModifications());

        mLibraryUpdate.setNotes(mLibraryUpdate.getDefaults().getNotes());
        assertFalse(mLibraryUpdate.containsModifications());
    }

    @Test
    public void testSetPrivacy() throws Exception {
        assertFalse(mLibraryUpdate.containsModifications());

        mLibraryUpdate.setPrivacy(mLibraryUpdate.getDefaults().isPrivate() ?
                Privacy.PRIVATE : Privacy.PUBLIC);
        assertFalse(mLibraryUpdate.containsModifications());

        mLibraryUpdate.setPrivacy(mLibraryUpdate.isPrivate() ? Privacy.PUBLIC : Privacy.PRIVATE);
        assertTrue(mLibraryUpdate.containsModifications());

        mLibraryUpdate.setPrivacy(mLibraryUpdate.getDefaults().isPrivate() ?
                Privacy.PRIVATE : Privacy.PUBLIC);
        assertFalse(mLibraryUpdate.containsModifications());
    }

    @Test
    public void testSetRating() throws Exception {
        assertFalse(mLibraryUpdate.containsModifications());

        mLibraryUpdate.setRating(mLibraryUpdate.getDefaults().getRating());
        assertFalse(mLibraryUpdate.containsModifications());

        mLibraryUpdate.setRating(Rating.FOUR);
        assertTrue(mLibraryUpdate.containsModifications());

        mLibraryUpdate.setRating(mLibraryUpdate.getDefaults().getRating());
        assertFalse(mLibraryUpdate.containsModifications());

        mLibraryUpdate.setRating(Rating.THREE_POINT_FIVE);
        assertTrue(mLibraryUpdate.containsModifications());
    }

    @Test
    public void testSetRewatchCount() throws Exception {
        assertFalse(mLibraryUpdate.containsModifications());

        mLibraryUpdate.setRewatchCount(mLibraryUpdate.getDefaults().getRewatchCount());
        assertFalse(mLibraryUpdate.containsModifications());

        mLibraryUpdate.setRewatchCount(5);
        assertTrue(mLibraryUpdate.containsModifications());

        mLibraryUpdate.setRewatchCount(mLibraryUpdate.getDefaults().getRewatchCount());
        assertFalse(mLibraryUpdate.containsModifications());

        mLibraryUpdate.setRewatchCount(20);
        assertTrue(mLibraryUpdate.containsModifications());
    }

    @Test
    public void testSetRewatching() throws Exception {
        assertFalse(mLibraryUpdate.containsModifications());

        mLibraryUpdate.setRewatching(mLibraryUpdate.isRewatching());
        assertFalse(mLibraryUpdate.containsModifications());

        mLibraryUpdate.setRewatching(!mLibraryUpdate.isRewatching());
        assertTrue(mLibraryUpdate.containsModifications());

        mLibraryUpdate.setRewatching(!mLibraryUpdate.isRewatching());
        assertFalse(mLibraryUpdate.containsModifications());
    }

    @Test
    public void testSetWatchingStatus() throws Exception {
        assertFalse(mLibraryUpdate.containsModifications());

        mLibraryUpdate.setWatchingStatus(mLibraryUpdate.getDefaults().getWatchingStatus());
        assertFalse(mLibraryUpdate.containsModifications());

        mLibraryUpdate.setWatchingStatus(WatchingStatus.COMPLETED);
        assertTrue(mLibraryUpdate.containsModifications());

        mLibraryUpdate.setWatchingStatus(mLibraryUpdate.getDefaults().getWatchingStatus());
        assertFalse(mLibraryUpdate.containsModifications());

        mLibraryUpdate.setWatchingStatus(WatchingStatus.ON_HOLD);
        assertTrue(mLibraryUpdate.containsModifications());
    }

}
