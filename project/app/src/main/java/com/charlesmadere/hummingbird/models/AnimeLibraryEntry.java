package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.charlesmadere.hummingbird.misc.ParcelableUtils;
import com.google.gson.annotations.SerializedName;

public class AnimeLibraryEntry implements Parcelable {

    @SerializedName("anime")
    private AbsAnime mAnime;

    @SerializedName("private")
    private boolean mIsPrivate;

    @SerializedName("rewatching")
    private boolean mIsRewatching;

    @SerializedName("notes_present")
    private boolean mNotesPresent;

    @SerializedName("episodes_watched")
    private int mEpisodesWatched;

    @SerializedName("rewatched_times")
    private int mRewatchedTimes;

    @Nullable
    @SerializedName("rating")
    private Rating mRating;

    @SerializedName("last_watched")
    private SimpleDate mLastWatched;

    @SerializedName("updated_at")
    private SimpleDate mUpdatedAt;

    @SerializedName("id")
    private String mId;

    @Nullable
    @SerializedName("notes")
    private String mNotes;

    @SerializedName("status")
    private WatchingStatus mStatus;


    public boolean areNotesPresent() {
        return mNotesPresent && !TextUtils.isEmpty(mNotes);
    }

    public AbsAnime getAnime() {
        return mAnime;
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

    public int getRewatchedTimes() {
        return mRewatchedTimes;
    }

    public WatchingStatus getStatus() {
        return mStatus;
    }

    public SimpleDate getUpdatedAt() {
        return mUpdatedAt;
    }

    public boolean hasRating() {
        return mRating != null && mRating.hasValue();
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
        dest.writeInt(mIsPrivate ? 1 : 0);
        dest.writeInt(mIsRewatching ? 1 : 0);
        dest.writeInt(mNotesPresent ? 1 : 0);
        dest.writeInt(mEpisodesWatched);
        dest.writeInt(mRewatchedTimes);
        dest.writeParcelable(mRating, flags);
        dest.writeParcelable(mLastWatched, flags);
        dest.writeParcelable(mUpdatedAt, flags);
        dest.writeString(mId);
        dest.writeString(mNotes);
        dest.writeParcelable(mStatus, flags);
    }

    public static final Creator<AnimeLibraryEntry> CREATOR = new Creator<AnimeLibraryEntry>() {
        @Override
        public AnimeLibraryEntry createFromParcel(final Parcel source) {
            final AnimeLibraryEntry le = new AnimeLibraryEntry();
            le.mAnime = ParcelableUtils.readAbsAnimeFromParcel(source);
            le.mIsPrivate = source.readInt() != 0;
            le.mIsRewatching = source.readInt() != 0;
            le.mNotesPresent = source.readInt() != 0;
            le.mEpisodesWatched = source.readInt();
            le.mRewatchedTimes = source.readInt();
            le.mRating = source.readParcelable(Rating.class.getClassLoader());
            le.mLastWatched = source.readParcelable(SimpleDate.class.getClassLoader());
            le.mUpdatedAt = source.readParcelable(SimpleDate.class.getClassLoader());
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


    public static class Rating implements Parcelable {
        @SerializedName("type")
        private RatingType mType;

        @Nullable
        @SerializedName("value")
        private String mValue;


        public RatingType getType() {
            return mType;
        }

        @Nullable
        public String getValue() {
            return mValue;
        }

        public boolean hasValue() {
            return !TextUtils.isEmpty(mValue);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(final Parcel dest, final int flags) {
            dest.writeString(mValue);
            dest.writeParcelable(mType, flags);
        }

        public static final Creator<Rating> CREATOR = new Creator<Rating>() {
            @Override
            public Rating createFromParcel(final Parcel source) {
                final Rating r = new Rating();
                r.mValue = source.readString();
                r.mType = source.readParcelable(RatingType.class.getClassLoader());
                return r;
            }

            @Override
            public Rating[] newArray(final int size) {
                return new Rating[size];
            }
        };
    }

}
