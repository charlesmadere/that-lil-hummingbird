package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.charlesmadere.hummingbird.misc.ParcelableUtils;
import com.google.gson.annotations.SerializedName;

public class LibraryEntry implements Parcelable {

    @SerializedName("anime")
    private AbsAnime mAnime;

    @SerializedName("notes_present")
    private boolean mNotesPresent;

    @SerializedName("private")
    private boolean mPrivate;

    @SerializedName("rewatching")
    private boolean mRewatching;

    @SerializedName("episodes_watched")
    private int mEpisodesWatched;

    @SerializedName("rewatched_times")
    private int mRewatchedTimes;

    @SerializedName("rating")
    private Rating mRating;

    @SerializedName("last_watched")
    private SimpleDate mLastWatched;

    @SerializedName("updated_at")
    private SimpleDate mUpdatedAt;

    @Nullable
    @SerializedName("notes")
    private String mNotes;

    @SerializedName("status")
    private WatchingStatus mStatus;


    public boolean areNotesPresent() {
        return mNotesPresent;
    }

    public AbsAnime getAnime() {
        return mAnime;
    }

    public int getEpisodesWatched() {
        return mEpisodesWatched;
    }

    public SimpleDate getLastWatched() {
        return mLastWatched;
    }

    @Nullable
    public String getNotes() {
        return mNotes;
    }

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
        return !TextUtils.isEmpty(mRating.getValue());
    }

    public boolean isPrivate() {
        return mPrivate;
    }

    public boolean isRewatching() {
        return mRewatching;
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
        dest.writeInt(mNotesPresent ? 1 : 0);
        dest.writeInt(mPrivate ? 1 : 0);
        dest.writeInt(mRewatching ? 1 : 0);
        dest.writeInt(mEpisodesWatched);
        dest.writeInt(mRewatchedTimes);
        dest.writeParcelable(mRating, flags);
        dest.writeParcelable(mLastWatched, flags);
        dest.writeParcelable(mUpdatedAt, flags);
        dest.writeString(mNotes);
        dest.writeParcelable(mStatus, flags);
    }

    public static final Creator<LibraryEntry> CREATOR = new Creator<LibraryEntry>() {
        @Override
        public LibraryEntry createFromParcel(final Parcel source) {
            final LibraryEntry le = new LibraryEntry();
            le.mAnime = ParcelableUtils.readAbsAnimeFromParcel(source);
            le.mNotesPresent = source.readInt() != 0;
            le.mPrivate = source.readInt() != 0;
            le.mRewatching = source.readInt() != 0;
            le.mEpisodesWatched = source.readInt();
            le.mRewatchedTimes = source.readInt();
            le.mRating = source.readParcelable(Rating.class.getClassLoader());
            le.mLastWatched = source.readParcelable(SimpleDate.class.getClassLoader());
            le.mUpdatedAt = source.readParcelable(SimpleDate.class.getClassLoader());
            le.mNotes = source.readString();
            le.mStatus = source.readParcelable(WatchingStatus.class.getClassLoader());
            return le;
        }

        @Override
        public LibraryEntry[] newArray(final int size) {
            return new LibraryEntry[size];
        }
    };


    public static class Rating implements Parcelable {
        @SerializedName("value")
        private String mValue;

        @SerializedName("type")
        private Type mType;


        public Type getType() {
            return mType;
        }

        public String getValue() {
            return mValue;
        }

        public boolean isRatingTypeAdvanced() {
            return mType == Type.ADVANCED;
        }

        public boolean isRatingTypeSimple() {
            return mType == Type.SIMPLE;
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
                r.mType = source.readParcelable(Type.class.getClassLoader());
                return r;
            }

            @Override
            public Rating[] newArray(final int size) {
                return new Rating[size];
            }
        };

        public enum Type implements Parcelable {
            @SerializedName("advanced")
            ADVANCED,

            @SerializedName("simple")
            SIMPLE;

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(final Parcel dest, final int flags) {
                dest.writeInt(ordinal());
            }

            public static final Creator<Type> CREATOR = new Creator<Type>() {
                @Override
                public Type createFromParcel(final Parcel source) {
                    final int ordinal = source.readInt();
                    return values()[ordinal];
                }

                @Override
                public Type[] newArray(final int size) {
                    return new Type[size];
                }
            };
        }
    }

}
