package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.google.gson.JsonObject;

public class AnimeLibraryUpdate implements Parcelable {

    private final Defaults mDefaults;

    private boolean mIsPrivate;
    private boolean mIsRewatching;
    private int mEpisodesWatched;
    private int mRewatchCount;

    @Nullable
    private Rating mRating;

    private String mAnimeId;
    private String mAnimeTitle;

    @Nullable
    private String mNotes;

    private WatchingStatus mWatchingStatus;


    public AnimeLibraryUpdate(final AnimeDigest digest) {
        mDefaults = new Defaults();
        mIsPrivate = mDefaults.isPrivate();
        mIsRewatching = mDefaults.isRewatching();
        mEpisodesWatched = mDefaults.getEpisodesWatched();
        mRewatchCount = mDefaults.getRewatchCount();
        mRating = mDefaults.getRating();
        mAnimeId = digest.getId();
        mAnimeTitle = digest.getTitle();
        mNotes = mDefaults.getNotes();
        mWatchingStatus = mDefaults.getWatchingStatus();
    }

    public AnimeLibraryUpdate(final AnimeLibraryEntry libraryEntry) {
        mDefaults = new Defaults(libraryEntry);
        mIsPrivate = mDefaults.isPrivate();
        mIsRewatching = mDefaults.isRewatching();
        mEpisodesWatched = mDefaults.getEpisodesWatched();
        mRewatchCount = mDefaults.getRewatchCount();
        mRating = mDefaults.getRating();
        mAnimeId = libraryEntry.getAnime().getId();
        mAnimeTitle = libraryEntry.getAnime().getTitle();
        mNotes = mDefaults.getNotes();
        mWatchingStatus = mDefaults.getWatchingStatus();
    }

    private AnimeLibraryUpdate(final Parcel source) {
        mDefaults = source.readParcelable(Defaults.class.getClassLoader());
        mIsPrivate = source.readInt() != 0;
        mIsRewatching = source.readInt() != 0;
        mEpisodesWatched = source.readInt();
        mRewatchCount = source.readInt();
        mRating = source.readParcelable(Rating.class.getClassLoader());
        mAnimeId = source.readString();
        mAnimeTitle = source.readString();
        mNotes = source.readString();
        mWatchingStatus = source.readParcelable(WatchingStatus.class.getClassLoader());
    }

    public boolean containsModifications() {
        return mIsPrivate != mDefaults.isPrivate() ||
                mIsRewatching != mDefaults.isRewatching() ||
                mEpisodesWatched != mDefaults.getEpisodesWatched() ||
                mRewatchCount != mDefaults.getRewatchCount() ||
                mRating != mDefaults.getRating() ||
                !TextUtils.equals(mNotes, mDefaults.getNotes()) ||
                mWatchingStatus != mDefaults.getWatchingStatus();
    }

    public String getAnimeId() {
        return mAnimeId;
    }

    public String getAnimeTitle() {
        return mAnimeTitle;
    }

    public Defaults getDefaults() {
        return mDefaults;
    }

    public int getEpisodesWatched() {
        return mEpisodesWatched;
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
        inner.addProperty("anime_id", mAnimeId);
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
        dest.writeParcelable(mDefaults, flags);

        dest.writeInt(mIsPrivate ? 1 : 0);
        dest.writeInt(mIsRewatching ? 1 : 0);
        dest.writeInt(mEpisodesWatched);
        dest.writeInt(mRewatchCount);
        dest.writeParcelable(mRating, flags);
        dest.writeString(mAnimeId);
        dest.writeString(mAnimeTitle);
        dest.writeString(mNotes);
        dest.writeParcelable(mWatchingStatus, flags);
    }

    public static final Creator<AnimeLibraryUpdate> CREATOR = new Creator<AnimeLibraryUpdate>() {
        @Override
        public AnimeLibraryUpdate createFromParcel(final Parcel source) {
            return new AnimeLibraryUpdate(source);
        }

        @Override
        public AnimeLibraryUpdate[] newArray(final int size) {
            return new AnimeLibraryUpdate[size];
        }
    };


    public static class Defaults implements Parcelable {
        private final boolean mIsPrivate;
        private final boolean mIsRewatching;
        private final int mEpisodesWatched;
        private final int mRewatchCount;

        @Nullable
        private final Rating mRating;

        @Nullable
        private final String mNotes;

        @Nullable
        private final WatchingStatus mWatchingStatus;


        private Defaults() {
            mIsPrivate = false;
            mIsRewatching = false;
            mEpisodesWatched = 0;
            mRewatchCount = 0;
            mRating = null;
            mNotes = null;
            mWatchingStatus = null;
        }

        private Defaults(final AnimeLibraryEntry libraryEntry) {
            mIsPrivate = libraryEntry.isPrivate();
            mIsRewatching = libraryEntry.isRewatching();
            mEpisodesWatched = libraryEntry.getEpisodesWatched();
            mRewatchCount = libraryEntry.getRewatchedTimes();
            mRating = Rating.from(libraryEntry);
            mNotes = libraryEntry.getNotes();
            mWatchingStatus = libraryEntry.getStatus();
        }

        private Defaults(final Parcel source) {
            mIsPrivate = source.readInt() != 0;
            mIsRewatching = source.readInt() != 0;
            mEpisodesWatched = source.readInt();
            mRewatchCount = source.readInt();
            mRating = source.readParcelable(Rating.class.getClassLoader());
            mNotes = source.readString();
            mWatchingStatus = source.readParcelable(WatchingStatus.class.getClassLoader());
        }

        public int getEpisodesWatched() {
            return mEpisodesWatched;
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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(final Parcel source, final int flags) {
            source.writeInt(mIsPrivate ? 1 : 0);
            source.writeInt(mIsRewatching ? 1 : 0);
            source.writeInt(mEpisodesWatched);
            source.writeInt(mRewatchCount);
            source.writeParcelable(mRating, flags);
            source.writeString(mNotes);
            source.writeParcelable(mWatchingStatus, flags);
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
