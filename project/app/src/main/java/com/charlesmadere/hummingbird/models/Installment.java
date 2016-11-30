package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Installment implements DataObject, Parcelable {

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
        return o instanceof Installment && mId.equals(((Installment) o).getId());
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

    public int getPosition() {
        return mAttributes.mPosition;
    }

    public Relationships getRelationships() {
        return mRelationships;
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

    public static final Creator<Installment> CREATOR = new Creator<Installment>() {
        @Override
        public Installment createFromParcel(final Parcel source) {
            final Installment i = new Installment();
            i.mAttributes = source.readParcelable(Attributes.class.getClassLoader());
            i.mDataType = source.readParcelable(DataType.class.getClassLoader());
            i.mLinks = source.readParcelable(Links.class.getClassLoader());
            i.mRelationships = source.readParcelable(Relationships.class.getClassLoader());
            i.mId = source.readString();
            return i;
        }

        @Override
        public Installment[] newArray(final int size) {
            return new Installment[size];
        }
    };


    private static class Attributes implements Parcelable {
        @SerializedName("position")
        private int mPosition;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(final Parcel dest, final int flags) {
            dest.writeInt(mPosition);
        }

        public static final Creator<Attributes> CREATOR = new Creator<Attributes>() {
            @Override
            public Attributes createFromParcel(final Parcel source) {
                final Attributes a = new Attributes();
                a.mPosition = source.readInt();
                return a;
            }

            @Override
            public Attributes[] newArray(final int size) {
                return new Attributes[size];
            }
        };
    }

}
