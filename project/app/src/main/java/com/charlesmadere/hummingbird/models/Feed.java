package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Feed implements Parcelable {

    @SerializedName("meta")
    private Metadata mMetadata;


    public Metadata getMetadata() {
        return mMetadata;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        // TODO
    }

    public static final Creator<Feed> CREATOR = new Creator<Feed>() {
        @Override
        public Feed createFromParcel(final Parcel source) {
            final Feed f = new Feed();
            // TODO
            return f;
        }

        @Override
        public Feed[] newArray(final int size) {
            return new Feed[size];
        }
    };


    private static class Metadata implements Parcelable {
        @SerializedName("cursor")
        private int mCursor;


        public int getCursor() {
            return mCursor;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(final Parcel dest, final int flags) {
            dest.writeInt(mCursor);
        }

        public static final Creator<Metadata> CREATOR = new Creator<Metadata>() {
            @Override
            public Metadata createFromParcel(final Parcel source) {
                final Metadata m = new Metadata();
                m.mCursor = source.readInt();
                return m;
            }

            @Override
            public Metadata[] newArray(final int size) {
                return new Metadata[size];
            }
        };
    }

}
