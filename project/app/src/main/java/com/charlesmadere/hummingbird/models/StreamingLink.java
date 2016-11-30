package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class StreamingLink implements DataObject, Parcelable {

    @SerializedName("attributes")
    private Attributes mAttributes;

    @SerializedName("type")
    private DataType mDataType;

    @SerializedName("links")
    private Links mLinks;

    @SerializedName("relationships")
    private Relationships mRelationships;

    @SerializedName("id")
    private String mId;


    @Override
    public boolean equals(final Object o) {
        return o instanceof StreamingLink && mId.equals(((StreamingLink) o).getId());
    }

    @Override
    public DataType getDataType() {
        return mDataType;
    }

    public ArrayList<String> getDubs() {
        return mAttributes.mDubs;
    }

    @Override
    public String getId() {
        return mId;
    }

    public ArrayList<String> getSubs() {
        return mAttributes.mSubs;
    }

    public String getUrl() {
        return mAttributes.mUrl;
    }

    @Override
    public int hashCode() {
        return mId.hashCode();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeParcelable(mAttributes, flags);
        dest.writeParcelable(mDataType, flags);
        dest.writeParcelable(mLinks, flags);
        dest.writeParcelable(mRelationships, flags);
        dest.writeString(mId);
    }

    public static final Creator<StreamingLink> CREATOR = new Creator<StreamingLink>() {
        @Override
        public StreamingLink createFromParcel(final Parcel source) {
            final StreamingLink sl = new StreamingLink();
            sl.mAttributes = source.readParcelable(Attributes.class.getClassLoader());
            sl.mDataType = source.readParcelable(DataType.class.getClassLoader());
            sl.mLinks = source.readParcelable(Links.class.getClassLoader());
            sl.mRelationships = source.readParcelable(Relationships.class.getClassLoader());
            sl.mId = source.readString();
            return sl;
        }

        @Override
        public StreamingLink[] newArray(final int size) {
            return new StreamingLink[size];
        }
    };


    private static class Attributes implements Parcelable {
        @SerializedName("dubs")
        private ArrayList<String> mDubs;

        @SerializedName("subs")
        private ArrayList<String> mSubs;

        @SerializedName("url")
        private String mUrl;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(final Parcel dest, final int flags) {
            dest.writeStringList(mDubs);
            dest.writeStringList(mSubs);
            dest.writeString(mUrl);
        }

        public static final Creator<Attributes> CREATOR = new Creator<Attributes>() {
            @Override
            public Attributes createFromParcel(final Parcel source) {
                final Attributes a = new Attributes();
                a.mDubs = source.createStringArrayList();
                a.mSubs = source.createStringArrayList();
                a.mUrl = source.readString();
                return a;
            }

            @Override
            public Attributes[] newArray(final int size) {
                return new Attributes[size];
            }
        };
    }

}
