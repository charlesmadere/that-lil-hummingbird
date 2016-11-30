package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.charlesmadere.hummingbird.misc.JsoupUtils;
import com.google.gson.annotations.SerializedName;

public class Post implements DataObject, Hydratable, Parcelable {

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


    public int getCommentsCount() {
        return mAttributes.mCommentsCount;
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

    public SimpleDate getDeletedAt() {
        return mAttributes.mDeletedAt;
    }

    @Override
    public String getId() {
        return mId;
    }

    public Links getLinks() {
        return mLinks;
    }

    public int getPostLikesCount() {
        return mAttributes.mPostLikesCount;
    }

    public Relationships getRelationships() {
        return mRelationships;
    }

    public int getTopLevelCommentsCount() {
        return mAttributes.mTopLevelCommentsCount;
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
            mAttributes.mContentCompiled = JsoupUtils.parse(mAttributes.mContentFormatted);
        }
    }

    public boolean isBlocked() {
        return mAttributes.mBlocked;
    }

    public boolean isNsfw() {
        return mAttributes.mNsfw;
    }

    public boolean isSpoiler() {
        return mAttributes.mSpoiler;
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

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(final Parcel source) {
            final Post p = new Post();
            p.mAttributes = source.readParcelable(Attributes.class.getClassLoader());
            p.mDataType = source.readParcelable(DataType.class.getClassLoader());
            p.mLinks = source.readParcelable(Links.class.getClassLoader());
            p.mRelationships = source.readParcelable(Relationships.class.getClassLoader());
            p.mId = source.readString();
            return p;
        }

        @Override
        public Post[] newArray(final int size) {
            return new Post[size];
        }
    };


    private static class Attributes implements Parcelable {
        @SerializedName("blocked")
        private boolean mBlocked;

        @SerializedName("nsfw")
        private boolean mNsfw;

        @SerializedName("spoiler")
        private boolean mSpoiler;

        @SerializedName("commentsCount")
        private int mCommentsCount;

        @SerializedName("postLikesCount")
        private int mPostLikesCount;

        @SerializedName("topLevelCommentsCount")
        private int mTopLevelCommentsCount;

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
            dest.writeInt(mNsfw ? 1 : 0);
            dest.writeInt(mSpoiler ? 1 : 0);
            dest.writeInt(mCommentsCount);
            dest.writeInt(mPostLikesCount);
            dest.writeInt(mTopLevelCommentsCount);
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
                a.mNsfw = source.readInt() != 0;
                a.mSpoiler = source.readInt() != 0;
                a.mCommentsCount = source.readInt();
                a.mPostLikesCount = source.readInt();
                a.mTopLevelCommentsCount = source.readInt();
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
