package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.charlesmadere.hummingbird.misc.ParcelableUtils;
import com.google.gson.annotations.SerializedName;

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
    private AbsAnime mAnime;


    @Override
    public boolean equals(final Object o) {
        return o instanceof AnimeLibraryEntry && mId.equalsIgnoreCase(((AnimeLibraryEntry) o).getId());
    }

    public AbsAnime getAnime() {
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

    public void hydrate(final Feed feed) {
        for (final AbsAnime anime : feed.getAnime()) {
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

    @Override
    public String toString() {
        return mAnime.getTitle();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        ParcelableUtils.writeAbsAnimeToParcel(mAnime, dest, flags);
        dest.writeParcelable(mAnime, flags);
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
    }

    public static final Creator<AnimeLibraryEntry> CREATOR = new Creator<AnimeLibraryEntry>() {
        @Override
        public AnimeLibraryEntry createFromParcel(final Parcel source) {
            final AnimeLibraryEntry le = new AnimeLibraryEntry();
            le.mAnime = ParcelableUtils.readAbsAnimeFromParcel(source);
            le.mIsFavorite = source.readInt() != 0;
            le.mIsPrivate = source.readInt() != 0;
            le.mIsRewatching = source.readInt() != 0;
            le.mNotesPresent = source.readInt() != 0;
            le.mEpisodesWatched = source.readInt();
            le.mRewatchCount = source.readInt();
            le.mRating = source.readParcelable(Rating.class.getClassLoader());
            le.mLastWatched = source.readParcelable(SimpleDate.class.getClassLoader());
            le.mAnimeId = source.readString();
            le.mId = source.readString();
            le.mNotes = source.readString();
            le.mStatus = source.readParcelable(WatchingStatus.class.getClassLoader());
            return le;
        }

        @Override
        public AnimeLibraryEntry[] newArray(final int size) {
            return new AnimeLibraryEntry[size];
        }
    };

}
