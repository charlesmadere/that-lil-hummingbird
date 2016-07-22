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
public class MangaLibraryUpdateTest {

    private MangaLibraryUpdate mLibraryUpdate;


    @Before
    public void setUp() throws Exception {
        mLibraryUpdate = new MangaLibraryUpdate(MangaLibraryEntryTest.get());
    }

    @Test
    public void testContainsModifications() throws Exception {
        assertFalse(mLibraryUpdate.containsModifications());
    }

    @Test
    public void testSetChaptersRead() throws Exception {
        assertFalse(mLibraryUpdate.containsModifications());

        mLibraryUpdate.setChaptersRead(mLibraryUpdate.getChaptersRead());
        assertFalse(mLibraryUpdate.containsModifications());

        mLibraryUpdate.setChaptersRead(mLibraryUpdate.getChaptersRead() + 1);
        assertTrue(mLibraryUpdate.containsModifications());

        mLibraryUpdate.setChaptersRead(mLibraryUpdate.getDefaults().getChaptersRead());
        assertFalse(mLibraryUpdate.containsModifications());

        mLibraryUpdate.setChaptersRead(mLibraryUpdate.getChaptersRead() + 2);
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
    public void testSetReadingStatus() throws Exception {
        assertFalse(mLibraryUpdate.containsModifications());

        mLibraryUpdate.setReadingStatus(mLibraryUpdate.getDefaults().getReadingStatus());
        assertFalse(mLibraryUpdate.containsModifications());

        mLibraryUpdate.setReadingStatus(ReadingStatus.COMPLETED);
        assertTrue(mLibraryUpdate.containsModifications());

        mLibraryUpdate.setReadingStatus(mLibraryUpdate.getDefaults().getReadingStatus());
        assertFalse(mLibraryUpdate.containsModifications());

        mLibraryUpdate.setReadingStatus(ReadingStatus.ON_HOLD);
        assertTrue(mLibraryUpdate.containsModifications());
    }

    @Test
    public void testSetReReadCount() throws Exception {
        assertFalse(mLibraryUpdate.containsModifications());

        mLibraryUpdate.setReReadCount(mLibraryUpdate.getDefaults().getReReadCount());
        assertFalse(mLibraryUpdate.containsModifications());

        mLibraryUpdate.setReReadCount(5);
        assertTrue(mLibraryUpdate.containsModifications());

        mLibraryUpdate.setReReadCount(mLibraryUpdate.getDefaults().getReReadCount());
        assertFalse(mLibraryUpdate.containsModifications());

        mLibraryUpdate.setReReadCount(20);
        assertTrue(mLibraryUpdate.containsModifications());
    }

    @Test
    public void testSetReReading() throws Exception {
        assertFalse(mLibraryUpdate.containsModifications());

        mLibraryUpdate.setReReading(mLibraryUpdate.isReReading());
        assertFalse(mLibraryUpdate.containsModifications());

        mLibraryUpdate.setReReading(!mLibraryUpdate.isReReading());
        assertTrue(mLibraryUpdate.containsModifications());

        mLibraryUpdate.setReReading(!mLibraryUpdate.isReReading());
        assertFalse(mLibraryUpdate.containsModifications());
    }

    @Test
    public void testSetVolumesRead() throws Exception {
        assertFalse(mLibraryUpdate.containsModifications());

        mLibraryUpdate.setVolumesRead(mLibraryUpdate.getVolumesRead());
        assertFalse(mLibraryUpdate.containsModifications());

        mLibraryUpdate.setVolumesRead(mLibraryUpdate.getVolumesRead() + 1);
        assertTrue(mLibraryUpdate.containsModifications());

        mLibraryUpdate.setVolumesRead(mLibraryUpdate.getDefaults().getVolumesRead());
        assertFalse(mLibraryUpdate.containsModifications());

        mLibraryUpdate.setVolumesRead(mLibraryUpdate.getChaptersRead() + 2);
        assertTrue(mLibraryUpdate.containsModifications());
    }

}
