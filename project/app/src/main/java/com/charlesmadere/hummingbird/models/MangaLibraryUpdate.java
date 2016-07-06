package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.gson.JsonObject;

public class MangaLibraryUpdate implements Parcelable {

    private final Defaults mDefaults;

    private boolean mIsPrivate;
    private boolean mIsReReading;
    private int mChaptersRead;
    private int mVolumesRead;

    @Nullable
    private Rating mRating;

    @Nullable
    private ReadingStatus mReadingStatus;

    private String mMangaId;
    private String mMangaTitle;

    @Nullable
    private String mNotes;


    public MangaLibraryUpdate(final MangaLibraryEntry libraryEntry) {
        mDefaults = new Defaults(libraryEntry);
        mIsPrivate = mDefaults.isPrivate();
        mIsReReading = mDefaults.isReReading();
        mChaptersRead = mDefaults.getChaptersRead();
        mVolumesRead = mDefaults.getVolumesRead();
        mRating = mDefaults.getRating();
        mReadingStatus = mDefaults.getReadingStatus();
        mMangaId = libraryEntry.getManga().getId();
        mMangaTitle = libraryEntry.getManga().getTitle();
        mNotes = mDefaults.getNotes();
    }

    private MangaLibraryUpdate(final Parcel source) {
        mDefaults = source.readParcelable(Defaults.class.getClassLoader());
        mIsPrivate = source.readInt() != 0;
        mIsReReading = source.readInt() != 0;
        mChaptersRead = source.readInt();
        mVolumesRead = source.readInt();
        mRating = source.readParcelable(Rating.class.getClassLoader());
        mReadingStatus = source.readParcelable(ReadingStatus.class.getClassLoader());
        mMangaId = source.readString();
        mMangaTitle = source.readString();
        mNotes = source.readString();
    }

    public JsonObject toJson() {
        final JsonObject inner = new JsonObject();
        // TODO

        final JsonObject outer = new JsonObject();
        outer.add("manga_library_entry", inner);

        return outer;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeParcelable(mDefaults, flags);
        dest.writeInt(mIsPrivate ? 1 : 0);
        dest.writeInt(mIsReReading ? 1 : 0);
        dest.writeInt(mChaptersRead);
        dest.writeInt(mVolumesRead);
        dest.writeParcelable(mRating, flags);
        dest.writeParcelable(mReadingStatus, flags);
        dest.writeString(mMangaId);
        dest.writeString(mMangaTitle);
        dest.writeString(mNotes);
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


    public static class Defaults implements Parcelable {
        private final boolean mIsPrivate;
        private final boolean mIsReReading;
        private final int mChaptersRead;
        private final int mVolumesRead;

        @Nullable
        private final Rating mRating;

        @Nullable
        private final ReadingStatus mReadingStatus;

        @Nullable
        private final String mNotes;


        private Defaults() {
            mIsPrivate = false;
            mIsReReading = false;
            mChaptersRead = 0;
            mVolumesRead = 0;
            mRating = null;
            mNotes = null;
            mReadingStatus = null;
        }

        private Defaults(final MangaLibraryEntry libraryEntry) {
            mIsPrivate = libraryEntry.isPrivate();
            mIsReReading = libraryEntry.isReReading();
            mChaptersRead = libraryEntry.getChaptersRead();
            mVolumesRead = libraryEntry.getVolumesRead();
            mRating = libraryEntry.getRating();
            mNotes = libraryEntry.getNotes();
            mReadingStatus = libraryEntry.getStatus();
        }

        private Defaults(final Parcel source) {
            mIsPrivate = source.readInt() != 0;
            mIsReReading = source.readInt() != 0;
            mChaptersRead = source.readInt();
            mVolumesRead = source.readInt();
            mRating = source.readParcelable(Rating.class.getClassLoader());
            mNotes = source.readString();
            mReadingStatus = source.readParcelable(ReadingStatus.class.getClassLoader());
        }

        public int getChaptersRead() {
            return mChaptersRead;
        }

        @Nullable
        public String getNotes() {
            return mNotes;
        }

        @Nullable
        public Rating getRating() {
            return mRating;
        }

        @Nullable
        public ReadingStatus getReadingStatus() {
            return mReadingStatus;
        }

        public int getVolumesRead() {
            return mVolumesRead;
        }

        public boolean isPrivate() {
            return mIsPrivate;
        }

        public boolean isReReading() {
            return mIsReReading;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(final Parcel dest, final int flags) {
            dest.writeInt(mIsPrivate ? 1 : 0);
            dest.writeInt(mIsReReading ? 1 : 0);
            dest.writeInt(mChaptersRead);
            dest.writeInt(mVolumesRead);
            dest.writeParcelable(mRating, flags);
            dest.writeString(mNotes);
            dest.writeParcelable(mReadingStatus, flags);
        }

        public static final Creator<Defaults> CREATOR = new Creator<Defaults>() {
            @Override
            public Defaults createFromParcel(final Parcel source) {
                return new Defaults(source);
            }

            @Override
            public Defaults[] newArray(final int size) {
                return new Defaults[size];
            }
        };
    }

}
