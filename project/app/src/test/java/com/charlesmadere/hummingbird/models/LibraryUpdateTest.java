package com.charlesmadere.hummingbird.models;

import org.junit.Before;
import org.junit.Test;

public class LibraryUpdateTest {

    private LibraryEntry mLibraryEntry;
    private LibraryUpdate mLibraryUpdate;

    @Before
    public void setUp() throws Exception {
        mLibraryEntry = LibraryEntryTest.get();
        mLibraryUpdate = new LibraryUpdate(mLibraryEntry);
    }

    @Test
    public void testContainsModifications() throws Exception {
        // TODO
    }

}
