package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class Casting implements DataObject, Parcelable {

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
        return o instanceof Casting && mId.equals(((Casting) o).getId());
    }

    @Override
    public DataType getDataType() {
        return mDataType;
    }

    @Override
    public String getId() {
        return mId;
    }

    @Nullable
    public String getLanguage() {
        return mAttributes.mLanguage;
    }

    public Links getLinks() {
        return mLinks;
    }

    public Relationships getRelationships() {
        return mRelationships;
    }

    public String getRole() {
        return mAttributes.mRole;
    }

    @Override
    public int hashCode() {
        return mId.hashCode();
    }

    public boolean isFeatured() {
        return mAttributes.mFeatured;
    }

    public boolean isVoiceActor() {
        return mAttributes.mVoiceActor;
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

    public static final Creator<Casting> CREATOR = new Creator<Casting>() {
        @Override
        public Casting createFromParcel(final Parcel source) {
            final Casting c = new Casting();
            c.mAttributes = source.readParcelable(Attributes.class.getClassLoader());
            c.mDataType = source.readParcelable(DataType.class.getClassLoader());
            c.mLinks = source.readParcelable(Links.class.getClassLoader());
            c.mRelationships = source.readParcelable(Relationships.class.getClassLoader());
            c.mId = source.readString();
            return c;
        }

        @Override
        public Casting[] newArray(final int size) {
            return new Casting[size];
        }
    };


    private static class Attributes implements Parcelable {
        @SerializedName("featured")
        private boolean mFeatured;

        @SerializedName("voiceActor")
        private boolean mVoiceActor;

        @Nullable
        @SerializedName("language")
        private String mLanguage;

        @SerializedName("role")
        private String mRole;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(final Parcel dest, final int flags) {
            dest.writeInt(mFeatured ? 1 : 0);
            dest.writeInt(mVoiceActor ? 1 : 0);
            dest.writeString(mLanguage);
            dest.writeString(mRole);
        }

        public static final Creator<Attributes> CREATOR = new Creator<Attributes>() {
            @Override
            public Attributes createFromParcel(final Parcel source) {
                final Attributes a = new Attributes();
                a.mFeatured = source.readInt() != 0;
                a.mVoiceActor = source.readInt() != 0;
                a.mLanguage = source.readString();
                a.mRole = source.readString();
                return a;
            }

            @Override
            public Attributes[] newArray(final int size) {
                return new Attributes[size];
            }
        };
    }

}
