package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;

public class MangaLibraryEntry implements Parcelable {

    // TODO


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        // TODO
    }

    public static final Creator<MangaLibraryEntry> CREATOR = new Creator<MangaLibraryEntry>() {
        @Override
        public MangaLibraryEntry createFromParcel(final Parcel source) {
            final MangaLibraryEntry mle = new MangaLibraryEntry();
            // TODO
            return mle;
        }

        @Override
        public MangaLibraryEntry[] newArray(final int size) {
            return new MangaLibraryEntry[size];
        }
    };

}
