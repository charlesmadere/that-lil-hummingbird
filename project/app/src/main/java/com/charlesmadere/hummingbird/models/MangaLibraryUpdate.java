package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.google.gson.JsonObject;

public class MangaLibraryUpdate implements Parcelable {

    private final Defaults mDefaults;

    private boolean mIsPrivate;
    private boolean mIsReReading;
    private int mChaptersRead;
    private int mReReadCount;
    private int mVolumesRead;

    @Nullable
    private Rating mRating;

    @Nullable
    private ReadingStatus mReadingStatus;

    private String mMangaId;
    private String mMangaTitle;

    @Nullable
    private String mNotes;


    public MangaLibraryUpdate(final MangaDigest digest) {
        mDefaults = new Defaults();
        mIsPrivate = mDefaults.isPrivate();
        mIsReReading = mDefaults.isReReading();
        mChaptersRead = mDefaults.getChaptersRead();
        mReReadCount = mDefaults.getReReadCount();
        mVolumesRead = mDefaults.getVolumesRead();
        mRating = mDefaults.getRating();
        mReadingStatus = mDefaults.getReadingStatus();
        mMangaId = digest.getId();
        mMangaTitle = digest.getTitle();
        mNotes = mDefaults.getNotes();
    }

    public MangaLibraryUpdate(final MangaLibraryEntry libraryEntry) {
        mDefaults = new Defaults(libraryEntry);
        mIsPrivate = mDefaults.isPrivate();
        mIsReReading = mDefaults.isReReading();
        mChaptersRead = mDefaults.getChaptersRead();
        mReReadCount = mDefaults.getReReadCount();
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
        mReReadCount = source.readInt();
        mVolumesRead = source.readInt();
        mRating = source.readParcelable(Rating.class.getClassLoader());
        mReadingStatus = source.readParcelable(ReadingStatus.class.getClassLoader());
        mMangaId = source.readString();
        mMangaTitle = source.readString();
        mNotes = source.readString();
    }

    public boolean containsModifications() {
        return mIsPrivate != mDefaults.isPrivate() ||
                mIsReReading != mDefaults.isReReading() ||
                mChaptersRead != mDefaults.getChaptersRead() ||
                mReReadCount != mDefaults.getReReadCount() ||
                mVolumesRead != mDefaults.getVolumesRead() ||
                mRating != mDefaults.getRating() ||
                mReadingStatus != mDefaults.getReadingStatus() ||
                !TextUtils.equals(mNotes, mDefaults.getNotes());
    }

    public int getChaptersRead() {
        return mChaptersRead;
    }

    public Defaults getDefaults() {
        return mDefaults;
    }

    public String getMangaId() {
        return mMangaId;
    }

    public String getMangaTitle() {
        return mMangaTitle;
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

    public int getReReadCount() {
        return mReReadCount;
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

    public void setChaptersRead(final int chaptersRead) {
        if (chaptersRead < 0) {
            throw new IllegalArgumentException("chaptersRead is negative: " + chaptersRead);
        }

        mChaptersRead = chaptersRead;
    }

    public void setNotes(@Nullable String notes) {
        if (TextUtils.isEmpty(notes) || TextUtils.getTrimmedLength(notes) == 0) {
            notes = null;
        } else {
            notes = notes.trim();
        }

        mNotes = notes;
    }

    public void setPrivacy(final Privacy privacy) {
        if (Privacy.PRIVATE == privacy) {
            mIsPrivate = true;
        } else if (Privacy.PUBLIC == privacy) {
            mIsPrivate = false;
        } else {
            throw new IllegalArgumentException("privacy is an illegal value: " + privacy);
        }
    }

    public void setRating(@Nullable final Rating rating) {
        mRating = rating;
    }

    public void setReadingStatus(@Nullable final ReadingStatus readingStatus) {
        mReadingStatus = readingStatus;
    }

    public void setReReadCount(final int reReadCount) {
        if (reReadCount < 0) {
            throw new IllegalArgumentException("reReadCount is negative: " + reReadCount);
        }

        mReReadCount = reReadCount;
    }

    public void setReReading(final boolean reReading) {
        mIsReReading = reReading;
    }

    public void setVolumesRead(final int volumesRead) {
        if (volumesRead < 0) {
            throw new IllegalArgumentException("volumesRead is negative: " + volumesRead);
        }

        mVolumesRead = volumesRead;
    }

    public JsonObject toJson() {
        final JsonObject inner = new JsonObject();
        inner.addProperty("private", mIsPrivate);
        inner.addProperty("rereading", mIsReReading);
        inner.addProperty("chapters_read", mChaptersRead);
        inner.addProperty("reread_count", mReReadCount);
        inner.addProperty("volumes_read", mVolumesRead);

        if (mRating == null || mRating == Rating.UNRATED) {
            inner.add("rating", null);
        } else {
            inner.addProperty("rating", mRating.mValue);
        }

        if (mReadingStatus == null) {
            inner.add("status", null);
        } else {
            inner.addProperty("status", mReadingStatus.getPostValue());
        }

        inner.addProperty("manga_id", mMangaId);

        if (TextUtils.isEmpty(mNotes) || TextUtils.getTrimmedLength(mNotes) == 0) {
            inner.add("notes", null);
        } else {
            inner.addProperty("notes", mNotes);
        }

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
        dest.writeInt(mReReadCount);
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
        private final int mReReadCount;
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
            mReReadCount = 0;
            mVolumesRead = 0;
            mRating = null;
            mNotes = null;
            mReadingStatus = null;
        }

        private Defaults(final MangaLibraryEntry libraryEntry) {
            mIsPrivate = libraryEntry.isPrivate();
            mIsReReading = libraryEntry.isReReading();
            mChaptersRead = libraryEntry.getChaptersRead();
            mReReadCount = libraryEntry.getReReadCount();
            mVolumesRead = libraryEntry.getVolumesRead();
            mRating = libraryEntry.getRating();
            mNotes = libraryEntry.getNotes();
            mReadingStatus = libraryEntry.getStatus();
        }

        private Defaults(final Parcel source) {
            mIsPrivate = source.readInt() != 0;
            mIsReReading = source.readInt() != 0;
            mChaptersRead = source.readInt();
            mReReadCount = source.readInt();
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

        public int getReReadCount() {
            return mReReadCount;
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
            dest.writeInt(mReReadCount);
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
