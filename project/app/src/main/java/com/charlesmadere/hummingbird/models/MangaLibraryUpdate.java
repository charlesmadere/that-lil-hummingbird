package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.gson.JsonObject;

public class MangaLibraryUpdate implements Parcelable {

    private final MangaLibraryEntry mLibraryEntry;

    private boolean mIsPrivate;
    private boolean mIsReReading;
    private int mChaptersRead;
    private int mVolumesRead;

    @Nullable
    private Rating mRating;

    @Nullable
    private String mNotes;

    private ReadingStatus mReadingStatus;


    public MangaLibraryUpdate(final MangaLibraryEntry libraryEntry) {
        mLibraryEntry = libraryEntry;
        mIsReReading = libraryEntry.isReReading();
        mChaptersRead = libraryEntry.getChaptersRead();
        mVolumesRead = libraryEntry.getVolumesRead();
        mRating = libraryEntry.getRating();
        mNotes = libraryEntry.getNotes();
        mReadingStatus = libraryEntry.getStatus();
    }

    private MangaLibraryUpdate(final Parcel source) {
        mLibraryEntry = source.readParcelable(MangaLibraryEntry.class.getClassLoader());
        mIsPrivate = source.readInt() != 0;
        mIsReReading = source.readInt() != 0;
        mChaptersRead = source.readInt();
        mVolumesRead = source.readInt();
        mRating = source.readParcelable(Rating.class.getClassLoader());
        mNotes = source.readString();
        mReadingStatus = source.readParcelable(ReadingStatus.class.getClassLoader());
    }

    public JsonObject toJson() {
        // TODO

        final JsonObject outer = new JsonObject();
        outer.add("manga_library_entry", null);

        return outer;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        // TODO
    }

    public static final Creator<MangaLibraryUpdate> CREATOR = new Creator<MangaLibraryUpdate>() {
        @Override
        public MangaLibraryUpdate createFromParcel(final Parcel source) {
            return new MangaLibraryUpdate(source);
        }

        @Override
        public MangaLibraryUpdate[] newArray(final int size) {
            return new MangaLibraryUpdate[size];
        }
    };

}
