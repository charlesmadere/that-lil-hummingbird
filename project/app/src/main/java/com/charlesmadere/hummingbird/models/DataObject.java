package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public interface DataObject {

    DataType getDataType();

    String getId();


    class Stub implements DataObject, Parcelable {
        @SerializedName("type")
        private DataType mDataType;

        @SerializedName("id")
        private String mId;

        @Override
        public boolean equals(final Object o) {
            return o instanceof Stub && mId.equals(((Stub) o).getId());
        }

        @Override
        public DataType getDataType() {
            return mDataType;
        }

        @Override
        public String getId() {
            return mId;
        }

        @Override
        public int hashCode() {
            return mId.hashCode();
        }

        @Override
        public String toString() {
            return mDataType + ":" + mId;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(final Parcel dest, final int flags) {
            dest.writeParcelable(mDataType, flags);
            dest.writeString(mId);
        }

        public static final Creator<Stub> CREATOR = new Creator<Stub>() {
            @Override
            public Stub createFromParcel(final Parcel source) {
                final Stub s = new Stub();
                s.mDataType = source.readParcelable(DataType.class.getClassLoader());
                s.mId = source.readString();
                return s;
            }

            @Override
            public Stub[] newArray(final int size) {
                return new Stub[size];
            }
        };
    }

}
