package com.charlesmadere.hummingbird.models;

import android.os.Build;

import com.charlesmadere.hummingbird.BuildConfig;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.M)
public class AnimeLibraryUpdateTest {

    private AnimeLibraryUpdate mLibraryUpdate;


    @Before
    public void setUp() throws Exception {
        mLibraryUpdate = new AnimeLibraryUpdate(AnimeLibraryEntryTest.get());
    }

    @Test
    public void testSetEpisodesWatched() throws Exception {
        assertFalse(mLibraryUpdate.containsModifications());

        mLibraryUpdate.setEpisodesWatched(mLibraryUpdate.getEpisodesWatched());
        assertFalse(mLibraryUpdate.containsModifications());

        mLibraryUpdate.setEpisodesWatched(mLibraryUpdate.getEpisodesWatched() + 1);
        assertTrue(mLibraryUpdate.containsModifications());

        mLibraryUpdate.setEpisodesWatched(mLibraryUpdate.getLibraryEntry().getEpisodesWatched());
        assertFalse(mLibraryUpdate.containsModifications());

        mLibraryUpdate.setEpisodesWatched(mLibraryUpdate.getEpisodesWatched() + 2);
        assertTrue(mLibraryUpdate.containsModifications());
    }

    @Test
    public void testSetNotesModifications() throws Exception {
        assertFalse(mLibraryUpdate.containsModifications());

        mLibraryUpdate.setNotes(mLibraryUpdate.getNotes());
        assertFalse(mLibraryUpdate.containsModifications());

        mLibraryUpdate.setNotes("Hello, World!");
        assertTrue(mLibraryUpdate.containsModifications());

        mLibraryUpdate.setNotes(mLibraryUpdate.getLibraryEntry().getNotes());
        assertFalse(mLibraryUpdate.containsModifications());
    }

    @Test
    public void testSetPrivacyModifications() throws Exception {
        assertFalse(mLibraryUpdate.containsModifications());

        mLibraryUpdate.setPrivacy(mLibraryUpdate.getLibraryEntry().isPrivate() ?
                Privacy.PRIVATE : Privacy.PUBLIC);
        assertFalse(mLibraryUpdate.containsModifications());

        mLibraryUpdate.setPrivacy(mLibraryUpdate.isPrivate() ? Privacy.PUBLIC : Privacy.PRIVATE);
        assertTrue(mLibraryUpdate.containsModifications());

        mLibraryUpdate.setPrivacy(mLibraryUpdate.getLibraryEntry().isPrivate() ?
                Privacy.PRIVATE : Privacy.PUBLIC);
        assertFalse(mLibraryUpdate.containsModifications());
    }

    @Test
    public void testSetRating() throws Exception {
        assertFalse(mLibraryUpdate.containsModifications());

        mLibraryUpdate.setRating(Rating.from(mLibraryUpdate.getLibraryEntry()));
        assertFalse(mLibraryUpdate.containsModifications());

        mLibraryUpdate.setRating(Rating.FOUR);
        assertTrue(mLibraryUpdate.containsModifications());

        mLibraryUpdate.setRating(Rating.from(mLibraryUpdate.getLibraryEntry()));
        assertFalse(mLibraryUpdate.containsModifications());

        mLibraryUpdate.setRating(Rating.UNRATED);
        assertTrue(mLibraryUpdate.containsModifications());
    }

    @Test
    public void testSetRewatchedTimes() throws Exception {
        assertFalse(mLibraryUpdate.containsModifications());

        mLibraryUpdate.setRewatchCount(mLibraryUpdate.getRewatchCount());
        assertFalse(mLibraryUpdate.containsModifications());

        mLibraryUpdate.setRewatchCount(5);
        assertTrue(mLibraryUpdate.containsModifications());

        mLibraryUpdate.setRewatchCount(mLibraryUpdate.getLibraryEntry().getRewatchedTimes());
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

        mLibraryUpdate.setWatchingStatus(mLibraryUpdate.getWatchingStatus());
        assertFalse(mLibraryUpdate.containsModifications());

        mLibraryUpdate.setWatchingStatus(WatchingStatus.PLAN_TO_WATCH);
        assertTrue(mLibraryUpdate.containsModifications());

        mLibraryUpdate.setWatchingStatus(mLibraryUpdate.getLibraryEntry().getStatus());
        assertFalse(mLibraryUpdate.containsModifications());

        mLibraryUpdate.setWatchingStatus(WatchingStatus.ON_HOLD);
        assertTrue(mLibraryUpdate.containsModifications());
    }

}
