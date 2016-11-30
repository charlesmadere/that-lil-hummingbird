package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class Episode implements DataObject, Parcelable {

    @SerializedName("attributes")
    private Attributes mAttributes;

    @SerializedName("type")
    private DataType mDataType;

    @SerializedName("relationships")
    private Relationships mRelationships;

    @SerializedName("id")
    private String mId;


    @Override
    public boolean equals(final Object o) {
        return o instanceof Episode && mId.equals(((Episode) o).getId());
    }

    @Nullable
    public SimpleDate getAirdate() {
        return mAttributes.mAirdate;
    }

    @Nullable
    public String getCanonicalTitle() {
        return mAttributes.mCanonicalTitle;
    }

    @Override
    public DataType getDataType() {
        return mDataType;
    }

    @Override
    public String getId() {
        return mId;
    }

    public int getNumber() {
        return mAttributes.mNumber;
    }

    public Relationships getRelationships() {
        return mRelationships;
    }

    @Nullable
    public Integer getSeasonNumber() {
        return mAttributes.mSeasonNumber;
    }

    @Nullable
    public String getSynopsis() {
        return mAttributes.mSynopsis;
    }

    @Nullable
    public Image getThumbnail() {
        return mAttributes.mThumbnail;
    }

    public Titles getTitles() {
        return mAttributes.mTitles;
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
        dest.writeParcelable(mRelationships, flags);
        dest.writeString(mId);
    }

    public static final Creator<Episode> CREATOR = new Creator<Episode>() {
        @Override
        public Episode createFromParcel(final Parcel source) {
            final Episode e = new Episode();
            e.mAttributes = source.readParcelable(Attributes.class.getClassLoader());
            e.mDataType = source.readParcelable(DataType.class.getClassLoader());
            e.mRelationships = source.readParcelable(Relationships.class.getClassLoader());
            e.mId = source.readString();
            return e;
        }

        @Override
        public Episode[] newArray(final int size) {
            return new Episode[size];
        }
    };


    private static class Attributes implements Parcelable {
        @Nullable
        @SerializedName("thumbnail")
        private Image mThumbnail;

        @SerializedName("number")
        private int mNumber;

        @Nullable
        @SerializedName("seasonNumber")
        private Integer mSeasonNumber;

        @Nullable
        @SerializedName("airdate")
        private SimpleDate mAirdate;

        @Nullable
        @SerializedName("canonicalTitle")
        private String mCanonicalTitle;

        @Nullable
        @SerializedName("synopsis")
        private String mSynopsis;

        @SerializedName("titles")
        private Titles mTitles;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(final Parcel dest, final int flags) {
            dest.writeParcelable(mThumbnail, flags);
            dest.writeInt(mNumber);
            dest.writeValue(mSeasonNumber);
            dest.writeParcelable(mAirdate, flags);
            dest.writeString(mCanonicalTitle);
            dest.writeString(mSynopsis);
            dest.writeParcelable(mTitles, flags);
        }

        public static final Creator<Attributes> CREATOR = new Creator<Attributes>() {
            @Override
            public Attributes createFromParcel(final Parcel source) {
                final Attributes a = new Attributes();
                a.mThumbnail = source.readParcelable(Image.class.getClassLoader());
                a.mNumber = source.readInt();
                a.mSeasonNumber = (Integer) source.readValue(Integer.class.getClassLoader());
                a.mAirdate = source.readParcelable(SimpleDate.class.getClassLoader());
                a.mCanonicalTitle = source.readString();
                a.mSynopsis = source.readString();
                a.mTitles = source.readParcelable(Titles.class.getClassLoader());
                return a;
            }

            @Override
            public Attributes[] newArray(final int size) {
                return new Attributes[size];
            }
        };
    }

}
