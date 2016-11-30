package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.charlesmadere.hummingbird.misc.JsoupUtils;
import com.google.gson.annotations.SerializedName;

public class Review implements DataObject, Hydratable, Parcelable {

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
        return o instanceof Review && mId.equals(((Review) o).getId());
    }

    @Nullable
    public CharSequence getContent() {
        return mAttributes.mContentCompiled;
    }

    @Override
    public DataType getDataType() {
        return mDataType;
    }

    @Override
    public String getId() {
        return mId;
    }

    public int getLikesCount() {
        return mAttributes.mLikesCount;
    }

    public Links getLinks() {
        return mLinks;
    }

    public int getRating() {
        return mAttributes.mRating;
    }

    public Relationships getRelationships() {
        return mRelationships;
    }

    @Nullable
    public String getSummary() {
        return mAttributes.mSummary;
    }

    @Override
    public int hashCode() {
        return mId.hashCode();
    }

    @Override
    public void hydrate() {
        if (!TextUtils.isEmpty(mAttributes.mContentFormatted)) {
            mAttributes.mContentCompiled = JsoupUtils.parse(mAttributes.mContentFormatted);
        } else if (!TextUtils.isEmpty(mAttributes.mContent)) {
            mAttributes.mContentCompiled = JsoupUtils.parse(mAttributes.mContent);
        }
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

    public static final Creator<Review> CREATOR = new Creator<Review>() {
        @Override
        public Review createFromParcel(final Parcel source) {
            final Review r = new Review();
            r.mAttributes = source.readParcelable(Attributes.class.getClassLoader());
            r.mDataType = source.readParcelable(DataType.class.getClassLoader());
            r.mLinks = source.readParcelable(Links.class.getClassLoader());
            r.mRelationships = source.readParcelable(Relationships.class.getClassLoader());
            r.mId = source.readString();
            return r;
        }

        @Override
        public Review[] newArray(final int size) {
            return new Review[size];
        }
    };


    private static class Attributes implements Parcelable {
        @SerializedName("likesCount")
        private int mLikesCount;

        @SerializedName("rating")
        private int mRating;

        @Nullable
        @SerializedName("content")
        private String mContent;

        @Nullable
        @SerializedName("contentFormatted")
        private String mContentFormatted;

        @Nullable
        @SerializedName("summary")
        private String mSummary;

        // hydrated fields
        private CharSequence mContentCompiled;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(final Parcel dest, final int flags) {
            dest.writeInt(mLikesCount);
            dest.writeInt(mRating);
            dest.writeString(mContent);
            dest.writeString(mContentFormatted);
            dest.writeString(mSummary);
            TextUtils.writeToParcel(mContentCompiled, dest, flags);
        }

        public static final Creator<Attributes> CREATOR = new Creator<Attributes>() {
            @Override
            public Attributes createFromParcel(final Parcel source) {
                final Attributes a = new Attributes();
                a.mLikesCount = source.readInt();
                a.mRating = source.readInt();
                a.mContent = source.readString();
                a.mContentFormatted = source.readString();
                a.mSummary = source.readString();
                a.mContentCompiled = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(source);
                return a;
            }

            @Override
            public Attributes[] newArray(final int size) {
                return new Attributes[size];
            }
        };
    }
}
