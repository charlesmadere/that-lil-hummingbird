package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.util.Comparator;

public class AnimeLibraryEntry implements Parcelable {

    @SerializedName("is_favorite")
    private boolean mIsFavorite;

    @SerializedName("private")
    private boolean mIsPrivate;

    @SerializedName("rewatching")
    private boolean mIsRewatching;

    @SerializedName("notes_present")
    private boolean mNotesPresent;

    @SerializedName("episodes_watched")
    private int mEpisodesWatched;

    @SerializedName("rewatch_count")
    private int mRewatchCount;

    @Nullable
    @SerializedName("rating")
    private Rating mRating;

    @SerializedName("last_watched")
    private SimpleDate mLastWatched;

    @SerializedName("anime_id")
    private String mAnimeId;

    @SerializedName("id")
    private String mId;

    @Nullable
    @SerializedName("notes")
    private String mNotes;

    @SerializedName("status")
    private WatchingStatus mStatus;

    // hydrated fields
    private Anime mAnime;


    public boolean canBeIncremented() {
        return !mAnime.hasEpisodeCount() || mEpisodesWatched < mAnime.getEpisodeCount();
    }

    @Override
    public boolean equals(final Object o) {
        return o instanceof AnimeLibraryEntry && mId.equalsIgnoreCase(((AnimeLibraryEntry) o).getId());
    }

    public Anime getAnime() {
        return mAnime;
    }

    public String getAnimeId() {
        return mAnimeId;
    }

    public int getEpisodesWatched() {
        return mEpisodesWatched;
    }

    public String getId() {
        return mId;
    }

    public SimpleDate getLastWatched() {
        return mLastWatched;
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

    public WatchingStatus getStatus() {
        return mStatus;
    }

    @Override
    public int hashCode() {
        return mId.hashCode();
    }

    public boolean hasNotes() {
        return mNotesPresent && !TextUtils.isEmpty(mNotes);
    }

    public boolean hasRating() {
        return mRating != null;
    }

    public boolean hydrate(final Anime anime) {
        if (mAnimeId.equalsIgnoreCase(anime.getId())) {
            mAnime = anime;
            return true;
        } else {
            return false;
        }
    }

    public boolean hydrate(final AnimeDigest digest) {
        if (!digest.hasAnime()) {
            return false;
        }

        for (final Anime anime : digest.getAnime()) {
            if (hydrate(anime)) {
                return true;
            }
        }

        return false;
    }

    public void hydrate(final Feed feed) {
        for (final Anime anime : feed.getAnime()) {
            if (mAnimeId.equalsIgnoreCase(anime.getId())) {
                mAnime = anime;
                return;
            }
        }
    }

    public boolean isFavorite() {
        return mIsFavorite;
    }

    public boolean isPrivate() {
        return mIsPrivate;
    }

    public boolean isRewatching() {
        return mIsRewatching;
    }

    public void setAnime(final Anime anime) {
        if (anime == null) {
            throw new IllegalArgumentException("anime parameter can't be null");
        } else if (!mAnimeId.equalsIgnoreCase(anime.getId())) {
            throw new IllegalArgumentException("anime IDs don't match (" + mAnimeId +
                    ") (" + anime.getId() + ')');
        }

        mAnime = anime;
    }

    @Override
    public String toString() {
        return mAnime.toString();
    }

    public void update(final AnimeLibraryEntry libraryEntry) {
        mIsFavorite = libraryEntry.isFavorite();
        mIsPrivate = libraryEntry.isPrivate();
        mIsRewatching = libraryEntry.isRewatching();
        mNotesPresent = libraryEntry.hasNotes();
        mEpisodesWatched = libraryEntry.getEpisodesWatched();
        mRewatchCount = libraryEntry.getRewatchCount();
        mRating = libraryEntry.getRating();
        mLastWatched = libraryEntry.getLastWatched();
        mNotes = libraryEntry.getNotes();
        mStatus = libraryEntry.getStatus();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeInt(mIsFavorite ? 1 : 0);
        dest.writeInt(mIsPrivate ? 1 : 0);
        dest.writeInt(mIsRewatching ? 1 : 0);
        dest.writeInt(mNotesPresent ? 1 : 0);
        dest.writeInt(mEpisodesWatched);
        dest.writeInt(mRewatchCount);
        dest.writeParcelable(mRating, flags);
        dest.writeParcelable(mLastWatched, flags);
        dest.writeString(mAnimeId);
        dest.writeString(mId);
        dest.writeString(mNotes);
        dest.writeParcelable(mStatus, flags);
        dest.writeParcelable(mAnime, flags);
    }

    public static final Creator<AnimeLibraryEntry> CREATOR = new Creator<AnimeLibraryEntry>() {
        @Override
        public AnimeLibraryEntry createFromParcel(final Parcel source) {
            final AnimeLibraryEntry ale = new AnimeLibraryEntry();
            ale.mIsFavorite = source.readInt() != 0;
            ale.mIsPrivate = source.readInt() != 0;
            ale.mIsRewatching = source.readInt() != 0;
            ale.mNotesPresent = source.readInt() != 0;
            ale.mEpisodesWatched = source.readInt();
            ale.mRewatchCount = source.readInt();
            ale.mRating = source.readParcelable(Rating.class.getClassLoader());
            ale.mLastWatched = source.readParcelable(SimpleDate.class.getClassLoader());
            ale.mAnimeId = source.readString();
            ale.mId = source.readString();
            ale.mNotes = source.readString();
            ale.mStatus = source.readParcelable(WatchingStatus.class.getClassLoader());
            ale.mAnime = source.readParcelable(Anime.class.getClassLoader());
            return ale;
        }

        @Override
        public AnimeLibraryEntry[] newArray(final int size) {
            return new AnimeLibraryEntry[size];
        }
    };

    public static final Comparator<AnimeLibraryEntry> DATE = new Comparator<AnimeLibraryEntry>() {
        @Override
        public int compare(final AnimeLibraryEntry lhs, final AnimeLibraryEntry rhs) {
            return SimpleDate.REVERSE_CHRONOLOGICAL_ORDER.compare(lhs.getLastWatched(),
                    rhs.getLastWatched());
        }
    };

    public static final Comparator<AnimeLibraryEntry> RATING = new Comparator<AnimeLibraryEntry>() {
        @Override
        public int compare(final AnimeLibraryEntry lhs, final AnimeLibraryEntry rhs) {
            return Rating.compare(lhs.getRating(), rhs.getRating());
        }
    };

    public static final Comparator<AnimeLibraryEntry> TITLE = new Comparator<AnimeLibraryEntry>() {
        @Override
        public int compare(final AnimeLibraryEntry lhs, final AnimeLibraryEntry rhs) {
            return lhs.getAnime().getTitle().compareToIgnoreCase(rhs.getAnime().getTitle());
        }
    };

}
