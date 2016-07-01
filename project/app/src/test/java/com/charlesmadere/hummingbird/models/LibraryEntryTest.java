package com.charlesmadere.hummingbird.models;

public class LibraryEntryTest {

    private static LibraryEntry sLibraryEntry;


    public static LibraryEntry get() {
        if (sLibraryEntry == null) {
            sLibraryEntry = new LibraryEntry();
            // TODO
        }

        return sLibraryEntry;
    }

}
