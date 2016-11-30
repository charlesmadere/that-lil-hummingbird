package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class Streamer implements DataObject, Parcelable {

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
        return o instanceof Streamer && mId.equals(((Streamer) o).getId());
    }

    @Override
    public DataType getDataType() {
        return mDataType;
    }

    @Override
    public String getId() {
        return mId;
    }

    public Links getLinks() {
        return mLinks;
    }

    @Nullable
    public Image getLogo() {
        return mAttributes.mLogo;
    }

    public Relationships getRelationships() {
        return mRelationships;
    }

    public String getSiteName() {
        return mAttributes.mSiteName;
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

    public static final Creator<Streamer> CREATOR = new Creator<Streamer>() {
        @Override
        public Streamer createFromParcel(final Parcel source) {
            final Streamer s = new Streamer();
            s.mAttributes = source.readParcelable(Attributes.class.getClassLoader());
            s.mDataType = source.readParcelable(DataType.class.getClassLoader());
            s.mLinks = source.readParcelable(Links.class.getClassLoader());
            s.mRelationships = source.readParcelable(Relationships.class.getClassLoader());
            s.mId = source.readString();
            return s;
        }

        @Override
        public Streamer[] newArray(final int size) {
            return new Streamer[size];
        }
    };


    private static class Attributes implements Parcelable {
        @Nullable
        @SerializedName("logo")
        private Image mLogo;

        @SerializedName("siteName")
        private String mSiteName;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(final Parcel dest, final int flags) {
            dest.writeParcelable(mLogo, flags);
            dest.writeString(mSiteName);
        }

        public static final Creator<Attributes> CREATOR = new Creator<Attributes>() {
            @Override
            public Attributes createFromParcel(final Parcel source) {
                final Attributes a = new Attributes();
                a.mLogo = source.readParcelable(Image.class.getClassLoader());
                a.mSiteName = source.readString();
                return a;
            }

            @Override
            public Attributes[] newArray(final int size) {
                return new Attributes[size];
            }
        };
    }

}
