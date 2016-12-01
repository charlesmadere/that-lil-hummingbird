package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.charlesmadere.hummingbird.misc.JsoupUtils;
import com.google.gson.annotations.SerializedName;

public class Comment implements DataObject, Hydratable, Parcelable {

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
        return o instanceof Comment && mId.equals(((Comment) o).getId());
    }

    @Nullable
    public CharSequence getContent() {
        return mAttributes.mContentCompiled;
    }

    public SimpleDate getCreatedAt() {
        return mAttributes.mCreatedAt;
    }

    @Override
    public DataType getDataType() {
        return mDataType;
    }

    @Nullable
    public SimpleDate getDeletedAt() {
        return mAttributes.mDeletedAt;
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

    public Relationships getRelationships() {
        return mRelationships;
    }

    public int getRepliesCount() {
        return mAttributes.mRepliesCount;
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

    public boolean isBlocked() {
        return mAttributes.mBlocked;
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

    public static final Creator<Comment> CREATOR = new Creator<Comment>() {
        @Override
        public Comment createFromParcel(final Parcel source) {
            final Comment c = new Comment();
            c.mAttributes = source.readParcelable(Attributes.class.getClassLoader());
            c.mDataType = source.readParcelable(DataType.class.getClassLoader());
            c.mLinks = source.readParcelable(Links.class.getClassLoader());
            c.mRelationships = source.readParcelable(Relationships.class.getClassLoader());
            c.mId = source.readString();
            return c;
        }

        @Override
        public Comment[] newArray(final int size) {
            return new Comment[size];
        }
    };


    private static class Attributes implements Parcelable {
        @SerializedName("blocked")
        private boolean mBlocked;

        @SerializedName("likesCount")
        private int mLikesCount;

        @SerializedName("repliesCount")
        private int mRepliesCount;

        @SerializedName("createdAt")
        private SimpleDate mCreatedAt;

        @Nullable
        @SerializedName("deletedAt")
        private SimpleDate mDeletedAt;

        @Nullable
        @SerializedName("content")
        private String mContent;

        @Nullable
        @SerializedName("contentFormatted")
        private String mContentFormatted;

        // hydrated fields
        private CharSequence mContentCompiled;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(final Parcel dest, final int flags) {
            dest.writeInt(mBlocked ? 1 : 0);
            dest.writeInt(mLikesCount);
            dest.writeInt(mRepliesCount);
            dest.writeParcelable(mCreatedAt, flags);
            dest.writeParcelable(mDeletedAt, flags);
            dest.writeString(mContent);
            dest.writeString(mContentFormatted);
            TextUtils.writeToParcel(mContentCompiled, dest, flags);
        }

        public static final Creator<Attributes> CREATOR = new Creator<Attributes>() {
            @Override
            public Attributes createFromParcel(final Parcel source) {
                final Attributes a = new Attributes();
                a.mBlocked = source.readInt() != 0;
                a.mLikesCount = source.readInt();
                a.mRepliesCount = source.readInt();
                a.mCreatedAt = source.readParcelable(SimpleDate.class.getClassLoader());
                a.mDeletedAt = source.readParcelable(SimpleDate.class.getClassLoader());
                a.mContent = source.readString();
                a.mContentFormatted = source.readString();
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
