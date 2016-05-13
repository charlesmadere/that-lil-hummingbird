package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public abstract class AbsNotification implements Parcelable {

    @SerializedName("seen")
    private boolean mSeen;

    @SerializedName("created_at")
    private SimpleDate mCreatedAt;

    @SerializedName("id")
    private String mId;


    public String getId() {
        return mId;
    }

    public abstract void hydrate(final Feed feed);

    @Override
    public int describeContents() {
        return 0;
    }

    protected void readFromParcel(final Parcel source) {
        // TODO
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        // TODO
    }


    public static abstract class AbsSource implements Parcelable {
        @SerializedName("id")
        private String mId;

        public abstract Type getType();

        @Override
        public int describeContents() {
            return 0;
        }

        protected void readFromParcel(final Parcel source) {
            mId = source.readString();
        }

        @Override
        public void writeToParcel(final Parcel dest, final int flags) {
            dest.writeString(mId);
        }

        public enum Type implements Parcelable {
            @SerializedName("story")
            STORY;

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
