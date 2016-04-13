package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.charlesmadere.hummingbird.misc.ParcelableUtils;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Story implements Parcelable {

    @Nullable
    @SerializedName("media")
    private AbsAnime mMedia;

    @SerializedName("substories")
    private ArrayList<Substory> mSubstories;

    @Nullable
    @SerializedName("self_post")
    private Boolean mSelfPost;

    @SerializedName("substories_count")
    private int mSubstoriesCount;

    @SerializedName("updated_at")
    private SimpleDate mUpdatedAt;

    @SerializedName("id")
    private String mId;

    @SerializedName("story_type")
    private Type mType;

    @Nullable
    @SerializedName("poster")
    private User mPoster;

    @SerializedName("user")
    private User mUser;


    public Substory getCommentSubstory() {
        if (mType != Type.COMMENT) {
            throw new RuntimeException("this should only ever be used with a " +
                    Type.class.getName() + " of \"" + Type.COMMENT + "\", the current type is \""
                    + mType + '"');
        }

        for (final Substory substory : mSubstories) {
            if (substory.getType() == Substory.Type.COMMENT) {
                return substory;
            }
        }

        throw new IllegalStateException("couldn't find comment");
    }

    public String getId() {
        return mId;
    }

    @Nullable
    public AbsAnime getMedia() {
        return mMedia;
    }

    @Nullable
    public User getPoster() {
        return mPoster;
    }

    @Nullable
    public Boolean getSelfPostStatus() {
        return mSelfPost;
    }

    public ArrayList<Substory> getSubstories() {
        return mSubstories;
    }

    public int getSubstoriesCount() {
        return mSubstoriesCount;
    }

    public Type getType() {
        return mType;
    }

    public SimpleDate getUpdatedAt() {
        return mUpdatedAt;
    }

    public User getUser() {
        return mUser;
    }

    public boolean isSelfPost() {
        return Boolean.TRUE.equals(mSelfPost);
    }

    @Override
    public String toString() {
        return mType.toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeParcelable(mMedia, flags);
        dest.writeTypedList(mSubstories);
        ParcelableUtils.writeBoolean(mSelfPost, dest);
        dest.writeInt(mSubstoriesCount);
        dest.writeParcelable(mUpdatedAt, flags);
        dest.writeString(mId);
        dest.writeParcelable(mType, flags);
        dest.writeParcelable(mPoster, flags);
        dest.writeParcelable(mUser, flags);
    }

    public static final Creator<Story> CREATOR = new Creator<Story>() {
        @Override
        public Story createFromParcel(final Parcel source) {
            final Story s = new Story();
            s.mMedia = source.readParcelable(AnimeV1.class.getClassLoader());
            s.mSubstories = source.createTypedArrayList(Substory.CREATOR);
            s.mSelfPost = ParcelableUtils.readBoolean(source);
            s.mSubstoriesCount = source.readInt();
            s.mUpdatedAt = source.readParcelable(SimpleDate.class.getClassLoader());
            s.mId = source.readString();
            s.mType = source.readParcelable(Type.class.getClassLoader());
            s.mPoster = source.readParcelable(User.class.getClassLoader());
            s.mUser = source.readParcelable(User.class.getClassLoader());
            return s;
        }

        @Override
        public Story[] newArray(final int size) {
            return new Story[size];
        }
    };


    public enum Type implements Parcelable {
        @SerializedName("comment")
        COMMENT,

        @SerializedName("followed")
        FOLLOWED,

        @SerializedName("media_story")
        MEDIA_STORY;


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(final Parcel dest, final int flags) {
            dest.writeInt(ordinal());
        }

        public static final Creator<Type> CREATOR = new Creator<Type>() {
            @Override
            public Type createFromParcel(final Parcel source) {
                final int ordinal = source.readInt();
                return values()[ordinal];
            }

            @Override
            public Type[] newArray(final int size) {
                return new Type[size];
            }
        };
    }

}
