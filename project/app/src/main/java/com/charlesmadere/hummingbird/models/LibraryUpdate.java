package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.charlesmadere.hummingbird.misc.GsonUtils;
import com.charlesmadere.hummingbird.misc.MiscUtils;
import com.charlesmadere.hummingbird.misc.ParcelableUtils;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

public class LibraryUpdate implements Parcelable {

    @SerializedName("private")
    private boolean mIsPrivate;

    @Nullable
    @SerializedName("rewatching")
    private Boolean mIsRewatching;

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

    @SerializedName("anime_id")
    private final String mAnimeId;

    @Nullable
    @SerializedName("notes")
    private String mNotes;

    @Nullable
    @SerializedName("status")
    private WatchingStatus mWatchingStatus;


    public LibraryUpdate(final LibraryEntry libraryEntry) {
        this(libraryEntry.getAnime().getId());
    }

    private LibraryUpdate(final String animeId) {
        mAnimeId = animeId;
    }

    public boolean containsModifications() {
        return mEpisodesWatched != null || mIsRewatching != null || mRewatchedTimes != null ||
                mPrivacy != null || mNotes != null || mWatchingStatus != null || mRating != null;
    }

    public String getAnimeId() {
        return mAnimeId;
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
    public WatchingStatus getWatchingStatus() {
        return mWatchingStatus;
    }

    public boolean isPrivate() {
        return mIsPrivate;
    }

    @Nullable
    public Boolean isRewatching() {
        return mIsRewatching;
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
        } else {
            mRating = rating;
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
            mIsRewatching = null;
        } else {
            mIsRewatching = rewatching;
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

    public JsonObject toJson() {
        final JsonElement libraryUpdate = GsonUtils.getGson().toJsonTree(this);
        final JsonObject json = new JsonObject();
        json.add("library_entry", libraryUpdate);

        return json;
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

        dest.writeInt(mIsPrivate ? 1 : 0);
        ParcelableUtils.writeBoolean(mIsRewatching, dest);
        ParcelableUtils.writeInteger(mEpisodesWatched, dest);
        ParcelableUtils.writeInteger(mRewatchedTimes, dest);
        dest.writeParcelable(mPrivacy, flags);
        dest.writeParcelable(mRating, flags);
        dest.writeString(mNotes);
        dest.writeParcelable(mWatchingStatus, flags);
    }

    public static final Creator<LibraryUpdate> CREATOR = new Creator<LibraryUpdate>() {
        @Override
        public LibraryUpdate createFromParcel(final Parcel source) {
            final LibraryUpdate lu = new LibraryUpdate(source.readString());
            lu.mIsPrivate = source.readInt() != 0;
            lu.mIsRewatching = ParcelableUtils.readBoolean(source);
            lu.mEpisodesWatched = ParcelableUtils.readInteger(source);
            lu.mRewatchedTimes = ParcelableUtils.readInteger(source);
            lu.mPrivacy = source.readParcelable(Privacy.class.getClassLoader());
            lu.mRating = source.readParcelable(Rating.class.getClassLoader());
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
