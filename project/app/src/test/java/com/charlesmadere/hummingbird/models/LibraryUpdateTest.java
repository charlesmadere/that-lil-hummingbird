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
public class LibraryUpdateTest {

    private static final String NOTES = "Hello, World!";

    private LibraryEntry mLibraryEntry;
    private LibraryUpdate mLibraryUpdate;


    @Before
    public void setUp() throws Exception {
        mLibraryEntry = LibraryEntryTest.get();
        mLibraryUpdate = new LibraryUpdate(mLibraryEntry);
    }

    @Test
    public void testSetEpisodesWatched() throws Exception {
        assertFalse(mLibraryUpdate.containsModifications());

        mLibraryUpdate.setEpisodesWatched(mLibraryEntry.getEpisodesWatched(), mLibraryEntry);
        assertFalse(mLibraryUpdate.containsModifications());

        mLibraryUpdate.setEpisodesWatched(mLibraryEntry.getEpisodesWatched() + 1, mLibraryEntry);
        assertTrue(mLibraryUpdate.containsModifications());

        mLibraryUpdate.setEpisodesWatched(mLibraryEntry.getEpisodesWatched(), mLibraryEntry);
        assertFalse(mLibraryUpdate.containsModifications());

        mLibraryUpdate.setEpisodesWatched(mLibraryEntry.getEpisodesWatched() + 2, mLibraryEntry);
        assertTrue(mLibraryUpdate.containsModifications());
    }

    @Test
    public void testSetNotesModifications() throws Exception {
        assertFalse(mLibraryUpdate.containsModifications());

        mLibraryUpdate.setNotes(mLibraryEntry.getNotes(), mLibraryEntry);
        assertFalse(mLibraryUpdate.containsModifications());

        mLibraryUpdate.setNotes(NOTES, mLibraryEntry);
        assertTrue(mLibraryUpdate.containsModifications());

        mLibraryUpdate.setNotes(mLibraryEntry.getNotes(), mLibraryEntry);
        assertFalse(mLibraryUpdate.containsModifications());
    }

    @Test
    public void testSetPrivacyModifications() throws Exception {
        assertFalse(mLibraryUpdate.containsModifications());

        mLibraryUpdate.setPrivacy(mLibraryEntry.isPrivate() ? Privacy.PRIVATE : Privacy.PUBLIC,
                mLibraryEntry);
        assertFalse(mLibraryUpdate.containsModifications());

        mLibraryUpdate.setPrivacy(mLibraryEntry.isPrivate() ? Privacy.PUBLIC : Privacy.PRIVATE,
                mLibraryEntry);
        assertTrue(mLibraryUpdate.containsModifications());

        mLibraryUpdate.setPrivacy(mLibraryEntry.isPrivate() ? Privacy.PRIVATE : Privacy.PUBLIC,
                mLibraryEntry);
        assertFalse(mLibraryUpdate.containsModifications());
    }

    @Test
    public void testSetRewatching() throws Exception {
        assertFalse(mLibraryUpdate.containsModifications());

        mLibraryUpdate.setRewatching(mLibraryEntry.isRewatching(), mLibraryEntry);
        assertFalse(mLibraryUpdate.containsModifications());

        mLibraryUpdate.setRewatching(!mLibraryEntry.isRewatching(), mLibraryEntry);
        assertTrue(mLibraryUpdate.containsModifications());

        mLibraryUpdate.setRewatching(mLibraryEntry.isRewatching(), mLibraryEntry);
        assertFalse(mLibraryUpdate.containsModifications());
    }

    @Test
    public void testSetWatchingStatus() throws Exception {
        assertFalse(mLibraryUpdate.containsModifications());

        mLibraryUpdate.setWatchingStatus(mLibraryEntry.getStatus(), mLibraryEntry);
        assertFalse(mLibraryUpdate.containsModifications());

        mLibraryUpdate.setWatchingStatus(null, mLibraryEntry);
        assertTrue(mLibraryUpdate.containsModifications());

        mLibraryUpdate.setWatchingStatus(mLibraryEntry.getStatus(), mLibraryEntry);
        assertFalse(mLibraryUpdate.containsModifications());

        mLibraryUpdate.setWatchingStatus(WatchingStatus.PLAN_TO_WATCH, mLibraryEntry);
        assertTrue(mLibraryUpdate.containsModifications());

        mLibraryUpdate.setWatchingStatus(WatchingStatus.ON_HOLD, mLibraryEntry);
        assertTrue(mLibraryUpdate.containsModifications());

        mLibraryUpdate.setWatchingStatus(mLibraryEntry.getStatus(), mLibraryEntry);
        assertFalse(mLibraryUpdate.containsModifications());
    }

}
