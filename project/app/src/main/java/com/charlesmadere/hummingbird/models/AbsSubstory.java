package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public abstract class AbsSubstory implements Parcelable {

    @SerializedName("created_at")
    private SimpleDate mCreatedAt;

    @SerializedName("id")
    private String mId;

    @SerializedName("story_id")
    private String mStoryId;


    public SimpleDate getCreatedAt() {
        return mCreatedAt;
    }

    public String getId() {
        return mId;
    }

    public String getStoryId() {
        return mStoryId;
    }

    public abstract Type getType();

    @Override
    public int describeContents() {
        return 0;
    }

    protected void readFromParcel(final Parcel source) {
        mCreatedAt = source.readParcelable(SimpleDate.class.getClassLoader());
        mId = source.readString();
        mStoryId = source.readString();
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeParcelable(mCreatedAt, flags);
        dest.writeString(mId);
        dest.writeString(mStoryId);
    }


    public enum Type implements Parcelable {
        @SerializedName("reply")
        REPLY,

        @SerializedName("watched_episode")
        WATCHED_EPISODE,

        @SerializedName("watchlist_status_update")
        WATCHLIST_STATUS_UPDATE;


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
