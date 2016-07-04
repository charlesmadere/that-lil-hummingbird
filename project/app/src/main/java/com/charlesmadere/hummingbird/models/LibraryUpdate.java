package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.google.gson.JsonObject;

public class LibraryUpdate implements Parcelable {

    private final LibraryEntry mLibraryEntry;

    private boolean mIsPrivate;
    private boolean mIsRewatching;
    private int mEpisodesWatched;
    private int mRewatchCount;

    @Nullable
    private Rating mRating;

    @Nullable
    private String mNotes;

    private WatchingStatus mWatchingStatus;


    public LibraryUpdate(final LibraryEntry libraryEntry) {
        mLibraryEntry = libraryEntry;
        mIsPrivate = libraryEntry.isPrivate();
        mIsRewatching = libraryEntry.isRewatching();
        mEpisodesWatched = libraryEntry.getEpisodesWatched();
        mRewatchCount = libraryEntry.getRewatchedTimes();
        mRating = Rating.from(libraryEntry);
        mNotes = libraryEntry.getNotes();
        mWatchingStatus = libraryEntry.getStatus();
    }

    private LibraryUpdate(final Parcel source) {
        mLibraryEntry = source.readParcelable(LibraryEntry.class.getClassLoader());
        mIsPrivate = source.readInt() != 0;
        mIsRewatching = source.readInt() != 0;
        mEpisodesWatched = source.readInt();
        mRewatchCount = source.readInt();
        mRating = source.readParcelable(Rating.class.getClassLoader());
        mNotes = source.readString();
        mWatchingStatus = source.readParcelable(WatchingStatus.class.getClassLoader());
    }

    public boolean containsModifications() {
        return mIsPrivate != mLibraryEntry.isPrivate() ||
                mIsRewatching != mLibraryEntry.isRewatching() ||
                mEpisodesWatched != mLibraryEntry.getEpisodesWatched() ||
                mRewatchCount != mLibraryEntry.getRewatchedTimes() ||
                mRating != Rating.from(mLibraryEntry) ||
                !TextUtils.equals(mNotes, mLibraryEntry.getNotes()) ||
                mWatchingStatus != mLibraryEntry.getStatus();
    }

    public int getEpisodesWatched() {
        return mEpisodesWatched;
    }

    public LibraryEntry getLibraryEntry() {
        return mLibraryEntry;
    }

    @Nullable
    public String getNotes() {
        return mNotes;
    }

    @Nullable
    public Rating getRating() {
        return mRating;
    }

    public int getRewatchCount() {
        return mRewatchCount;
    }

    @Nullable
    public WatchingStatus getWatchingStatus() {
        return mWatchingStatus;
    }

    public boolean isPrivate() {
        return mIsPrivate;
    }

    public boolean isRewatching() {
        return mIsRewatching;
    }

    public void setEpisodesWatched(final int episodesWatched) {
        if (episodesWatched < 0) {
            throw new IllegalArgumentException("episodesWatched is negative: " + episodesWatched);
        }

        mEpisodesWatched = episodesWatched;
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

    public void setRewatchCount(final int rewatchCount) {
        if (rewatchCount < 0) {
            throw new IllegalArgumentException("rewatchCount is negative: " + rewatchCount);
        }

        mRewatchCount = rewatchCount;
    }

    public void setRewatching(final boolean rewatching) {
        mIsRewatching = rewatching;
    }

    public void setWatchingStatus(final WatchingStatus watchingStatus) {
        if (watchingStatus == null) {
            throw new IllegalArgumentException("watchingStatus can't be null");
        }

        mWatchingStatus = watchingStatus;
    }

    public JsonObject toJson() {
        final JsonObject inner = new JsonObject();
        inner.addProperty("anime_id", mLibraryEntry.getId());
        inner.addProperty("private", mIsPrivate);
        inner.addProperty("rewatching", mIsRewatching);
        inner.addProperty("episodes_watched", mEpisodesWatched);
        inner.addProperty("rewatch_count", mRewatchCount);

        if (mRating == null || mRating == Rating.UNRATED) {
            inner.add("rating", null);
        } else {
            inner.addProperty("rating", mRating.mValue);
        }

        if (TextUtils.isEmpty(mNotes) || TextUtils.getTrimmedLength(mNotes) == 0) {
            inner.add("notes", null);
        } else {
            inner.addProperty("notes", mNotes);
        }

        inner.addProperty("status", mWatchingStatus.getLibraryUpdateValue());

        final JsonObject outer = new JsonObject();
        outer.add("library_entry", inner);

        return outer;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        // intentionally at the top
        dest.writeParcelable(mLibraryEntry, flags);

        dest.writeInt(mIsPrivate ? 1 : 0);
        dest.writeInt(mIsRewatching ? 1 : 0);
        dest.writeInt(mEpisodesWatched);
        dest.writeInt(mRewatchCount);
        dest.writeParcelable(mRating, flags);
        dest.writeString(mNotes);
        dest.writeParcelable(mWatchingStatus, flags);
    }

    public static final Creator<LibraryUpdate> CREATOR = new Creator<LibraryUpdate>() {
        @Override
        public LibraryUpdate createFromParcel(final Parcel source) {
            return new LibraryUpdate(source);
        }

        @Override
        public LibraryUpdate[] newArray(final int size) {
            return new LibraryUpdate[size];
        }
    };

}
