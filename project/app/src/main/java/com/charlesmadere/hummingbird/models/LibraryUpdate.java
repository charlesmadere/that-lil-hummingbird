package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.charlesmadere.hummingbird.misc.MiscUtils;
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
    @SerializedName("sane_rating_update")
    private Rating mSaneRating;

    @SerializedName("id")
    private final String mAnimeId;

    @SerializedName("auth_token")
    private final String mAuthToken;

    @Nullable
    @SerializedName("notes")
    private String mNotes;

    @Nullable
    @SerializedName("status")
    private WatchingStatus mWatchingStatus;


    public LibraryUpdate(final LibraryEntry libraryEntry) {
        this(libraryEntry.getAnime().getId(), Preferences.Account.AuthToken.get());
        mSaneRating = Rating.from(libraryEntry);
    }

    private LibraryUpdate(final String animeId, final String authToken) {
        mAnimeId = animeId;
        mAuthToken = authToken;
    }

    public boolean containsModifications() {
        return mEpisodesWatched != null || mRewatching != null || mRewatchedTimes != null ||
                mPrivacy != null || mNotes != null || mWatchingStatus != null || mRating != null;
    }

    public String getAnimeId() {
        return mAnimeId;
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
    public Rating getSaneRating() {
        return mSaneRating;
    }

    @Nullable
    public WatchingStatus getWatchingStatus() {
        return mWatchingStatus;
    }

    @Nullable
    public Boolean isRewatching() {
        return mRewatching;
    }

    public void setEpisodesWatched(@Nullable final Integer episodesWatched,
            final LibraryEntry libraryEntry) {
        if (MiscUtils.integerEquals(episodesWatched, libraryEntry.getEpisodesWatched())) {
            mEpisodesWatched = null;
        } else {
            mEpisodesWatched = episodesWatched;
        }
    }

    public void setNotes(@Nullable final String notes, final LibraryEntry libraryEntry) {
        if (TextUtils.equals(notes, libraryEntry.getNotes())) {
            mNotes = null;
        } else {
            mNotes = notes;

            if (!TextUtils.isEmpty(mNotes)) {
                mNotes = mNotes.trim();
            }
        }
    }

    public void setPrivacy(@Nullable final Privacy privacy, final LibraryEntry libraryEntry) {
        if (privacy == null || (privacy == Privacy.PRIVATE && libraryEntry.isPrivate())
                || (privacy == Privacy.PUBLIC && !libraryEntry.isPrivate())) {
            mPrivacy = null;
        } else {
            mPrivacy = privacy;
        }
    }

    public void setRating(final Rating rating, final LibraryEntry libraryEntry) {
        if (rating == Rating.from(libraryEntry)) {
            mRating = null;
            mSaneRating = rating;
        } else {
            mRating = rating;
            mSaneRating = null;
        }
    }

    public void setRewatchedTimes(@Nullable final Integer rewatchedTimes,
            final LibraryEntry libraryEntry) {
        if (MiscUtils.integerEquals(rewatchedTimes, libraryEntry.getRewatchedTimes())) {
            mRewatchedTimes = null;
        } else {
            mRewatchedTimes = rewatchedTimes;
        }
    }

    public void setRewatching(final boolean rewatching, final LibraryEntry libraryEntry) {
        if (MiscUtils.booleanEquals(rewatching, libraryEntry.isRewatching())) {
            mRewatching = null;
        } else {
            mRewatching = rewatching;
        }
    }

    public void setWatchingStatus(@Nullable final WatchingStatus watchingStatus,
            final LibraryEntry libraryEntry) {
        if (WatchingStatus.equals(watchingStatus, libraryEntry.getStatus())) {
            mWatchingStatus = null;
        } else {
            mWatchingStatus = watchingStatus;
        }
    }

    @Override
    public String toString() {
        return getAnimeId();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        // intentionally at the top
        dest.writeString(mAnimeId);
        dest.writeString(mAuthToken);

        ParcelableUtils.writeBoolean(mRewatching, dest);
        ParcelableUtils.writeInteger(mEpisodesWatched, dest);
        ParcelableUtils.writeInteger(mRewatchedTimes, dest);
        dest.writeParcelable(mPrivacy, flags);
        dest.writeParcelable(mRating, flags);
        dest.writeParcelable(mSaneRating, flags);
        dest.writeString(mNotes);
        dest.writeParcelable(mWatchingStatus, flags);
    }

    public static final Creator<LibraryUpdate> CREATOR = new Creator<LibraryUpdate>() {
        @Override
        public LibraryUpdate createFromParcel(final Parcel source) {
            final LibraryUpdate lu = new LibraryUpdate(source.readString(), source.readString());
            lu.mRewatching = ParcelableUtils.readBoolean(source);
            lu.mEpisodesWatched = ParcelableUtils.readInteger(source);
            lu.mRewatchedTimes = ParcelableUtils.readInteger(source);
            lu.mPrivacy = source.readParcelable(Privacy.class.getClassLoader());
            lu.mRating = source.readParcelable(Rating.class.getClassLoader());
            lu.mSaneRating = source.readParcelable(Rating.class.getClassLoader());
            lu.mNotes = source.readString();
            lu.mWatchingStatus = source.readParcelable(WatchingStatus.class.getClassLoader());
            return lu;
        }

        @Override
        public LibraryUpdate[] newArray(final int size) {
            return new LibraryUpdate[size];
        }
    };


}
