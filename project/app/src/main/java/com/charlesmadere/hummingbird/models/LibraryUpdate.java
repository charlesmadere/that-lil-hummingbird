package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.charlesmadere.hummingbird.misc.ParcelableUtils;
import com.charlesmadere.hummingbird.preferences.Preferences;
import com.google.gson.annotations.SerializedName;

public class LibraryUpdate implements Parcelable {

    @Nullable
    @SerializedName("rewatching")
    private Boolean mRewatching;

    @Nullable
    @SerializedName("episodes_watched")
    private Integer mEpisodesWatched;

    @Nullable
    @SerializedName("rewatched_times")
    private Integer mRewatchedTimes;

    @Nullable
    @SerializedName("privacy")
    private Privacy mPrivacy;

    @Nullable
    @SerializedName("rating")
    private Rating mRating;

    @Nullable
    @SerializedName("same_rating_update")
    private Rating mSameRatingUpdate;

    @SerializedName("auth_token")
    private final String mAuthToken;

    @Nullable
    @SerializedName("notes")
    private String mNotes;

    @Nullable
    @SerializedName("status")
    private WatchingStatus mWatchingStatus;


    public LibraryUpdate() {
        this(Preferences.Account.AuthToken.get());
    }

    private LibraryUpdate(final String authToken) {
        if (TextUtils.isEmpty(authToken)) {
            throw new IllegalArgumentException("authToken can't be null / empty");
        }

        mAuthToken = authToken;
    }

    public boolean containsModifications(final LibraryEntry libraryEntry) {
        // TODO
        return false;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final LibraryUpdate that = (LibraryUpdate) o;

        if (mRewatching != null ? !mRewatching.equals(that.mRewatching) :
                that.mRewatching != null) {
            return false;
        }

        if (mEpisodesWatched != null ? !mEpisodesWatched.equals(that.mEpisodesWatched) :
                that.mEpisodesWatched != null) {
            return false;
        }

        if (mRewatchedTimes != null ? !mRewatchedTimes.equals(that.mRewatchedTimes) :
                that.mRewatchedTimes != null) {
            return false;
        }

        if (mPrivacy != that.mPrivacy) {
            return false;
        }

        if (mRating != that.mRating) {
            return false;
        }

        if (mSameRatingUpdate != that.mSameRatingUpdate) {
            return false;
        }

        if (mAuthToken != null ? !mAuthToken.equals(that.mAuthToken) : that.mAuthToken != null) {
            return false;
        }

        if (mNotes != null ? !mNotes.equals(that.mNotes) : that.mNotes != null) {
            return false;
        }

        return mWatchingStatus == that.mWatchingStatus;
    }

    public String getAuthToken() {
        return mAuthToken;
    }

    @Nullable
    public Integer getEpisodesWatched() {
        return mEpisodesWatched;
    }

    @Nullable
    public String getNotes() {
        return mNotes;
    }

    @Nullable
    public Privacy getPrivacy() {
        return mPrivacy;
    }

    @Nullable
    public Rating getRating() {
        return mRating;
    }

    @Nullable
    public Integer getRewatchedTimes() {
        return mRewatchedTimes;
    }

    @Nullable
    public Boolean getRewatchingStatus() {
        return mRewatching;
    }

    @Nullable
    public Rating getSameRatingUpdate() {
        return mSameRatingUpdate;
    }

    @Override
    public int hashCode() {
        int result = mRewatching != null ? mRewatching.hashCode() : 0;
        result = 31 * result + (mEpisodesWatched != null ? mEpisodesWatched.hashCode() : 0);
        result = 31 * result + (mRewatchedTimes != null ? mRewatchedTimes.hashCode() : 0);
        result = 31 * result + (mPrivacy != null ? mPrivacy.hashCode() : 0);
        result = 31 * result + (mRating != null ? mRating.hashCode() : 0);
        result = 31 * result + (mSameRatingUpdate != null ? mSameRatingUpdate.hashCode() : 0);
        result = 31 * result + (mAuthToken != null ? mAuthToken.hashCode() : 0);
        result = 31 * result + (mNotes != null ? mNotes.hashCode() : 0);
        result = 31 * result + (mWatchingStatus != null ? mWatchingStatus.hashCode() : 0);
        return result;
    }

    public void setEpisodesWatched(@Nullable final Integer episodesWatched) {
        mEpisodesWatched = episodesWatched;
    }

    public void setNotes(@Nullable final String notes) {
        mNotes = notes;
    }

    public void setPrivacy(@Nullable final Privacy privacy) {
        mPrivacy = privacy;
    }

    public void setRating(@Nullable final Rating rating) {
        mRating = rating;
    }

    public void setRewatchedTimes(@Nullable final Integer rewatchedTimes) {
        mRewatchedTimes = rewatchedTimes;
    }

    public void setRewatchingStatus(@Nullable final Boolean rewatching) {
        mRewatching = rewatching;
    }

    public void setSameRatingUpdate(@Nullable final Rating sameRatingUpdate) {
        mSameRatingUpdate = sameRatingUpdate;
    }

    @Nullable
    public WatchingStatus getWatchingStatus() {
        return mWatchingStatus;
    }

    public void setWatchingStatus(@Nullable final WatchingStatus watchingStatus) {
        mWatchingStatus = watchingStatus;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        // intentionally at the top
        dest.writeString(mAuthToken);

        ParcelableUtils.writeBoolean(mRewatching, dest);
        ParcelableUtils.writeInteger(mEpisodesWatched, dest);
        ParcelableUtils.writeInteger(mRewatchedTimes, dest);
        dest.writeParcelable(mPrivacy, flags);
        dest.writeParcelable(mRating, flags);
        dest.writeParcelable(mSameRatingUpdate, flags);
        dest.writeString(mNotes);
        dest.writeParcelable(mWatchingStatus, flags);
    }

    public static final Creator<LibraryUpdate> CREATOR = new Creator<LibraryUpdate>() {
        @Override
        public LibraryUpdate createFromParcel(final Parcel source) {
            final LibraryUpdate lu = new LibraryUpdate(source.readString());
            lu.mRewatching = ParcelableUtils.readBoolean(source);
            lu.mEpisodesWatched = ParcelableUtils.readInteger(source);
            lu.mRewatchedTimes = ParcelableUtils.readInteger(source);
            lu.mPrivacy = source.readParcelable(Privacy.class.getClassLoader());
            lu.mRating = source.readParcelable(Rating.class.getClassLoader());
            lu.mSameRatingUpdate = source.readParcelable(Rating.class.getClassLoader());
            lu.mNotes = source.readString();
            lu.mWatchingStatus = source.readParcelable(WatchingStatus.class.getClassLoader());
            return lu;
        }

        @Override
        public LibraryUpdate[] newArray(final int size) {
            return new LibraryUpdate[size];
        }
    };


    public enum Privacy implements Parcelable {
        @SerializedName("private")
        PRIVATE,

        @SerializedName("public")
        PUBLIC;


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(final Parcel dest, final int flags) {
            dest.writeInt(ordinal());
        }

        public static final Creator<Privacy> CREATOR = new Creator<Privacy>() {
            @Override
            public Privacy createFromParcel(final Parcel source) {
                return values()[source.readInt()];
            }

            @Override
            public Privacy[] newArray(final int size) {
                return new Privacy[size];
            }
        };
    }


    public enum Rating implements Parcelable {
        @SerializedName("0")
        ZERO("0", 0f),

        @SerializedName("0.5")
        ZERO_POINT_FIVE("0.5", 0.5f),

        @SerializedName("1")
        ONE("1", 1f),

        @SerializedName("1.5")
        ONE_POINT_FIVE("1.5", 1.5f),

        @SerializedName("2")
        TWO("2", 2f),

        @SerializedName("2.5")
        TWO_POINT_FIVE("2.5", 2.5f),

        @SerializedName("3")
        THREE("3", 3f),

        @SerializedName("3.5")
        THREE_POINT_FIVE("3.5", 3.5f),

        @SerializedName("4")
        FOUR("4", 4f),

        @SerializedName("4.5")
        FOUR_POINT_FIVE("4.5", 4.5f),

        @SerializedName("5")
        FIVE("5", 5f);

        private final float mValue;
        private final String mText;


        Rating(final String text, final float value) {
            mText = text;
            mValue = value;
        }

        public String getText() {
            return mText;
        }

        public float getValue() {
            return mValue;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(final Parcel dest, final int flags) {
            dest.writeInt(ordinal());
        }

        public static final Creator<Rating> CREATOR = new Creator<Rating>() {
            @Override
            public Rating createFromParcel(final Parcel source) {
                return values()[source.readInt()];
            }

            @Override
            public Rating[] newArray(final int size) {
                return new Rating[size];
            }
        };
    }

}
