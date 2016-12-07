package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class Action implements DataObject, Parcelable {

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
        return o instanceof Action && mId.equals(((Action) o).getId());
    }

    @Override
    public DataType getDataType() {
        return mDataType;
    }

    public String getForeignId() {
        return mAttributes.mForeignId;
    }

    @Override
    public String getId() {
        return mId;
    }

    @Nullable
    public Integer getProgress() {
        return mAttributes.mProgress;
    }

    @Nullable
    public Rating getRating() {
        return mAttributes.mRating;
    }

    public Relationships getRelationships() {
        return mRelationships;
    }

    @Nullable
    public Status getStatus() {
        return mAttributes.mStatus;
    }

    @Nullable
    public String getStreamId() {
        return mAttributes.mStreamId;
    }

    public SimpleDate getTime() {
        return mAttributes.mTime;
    }

    public Verb getVerb() {
        return mAttributes.mVerb;
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

    public static final Creator<Action> CREATOR = new Creator<Action>() {
        @Override
        public Action createFromParcel(final Parcel source) {
            final Action a = new Action();
            a.mAttributes = source.readParcelable(Attributes.class.getClassLoader());
            a.mDataType = source.readParcelable(DataType.class.getClassLoader());
            a.mRelationships = source.readParcelable(Relationships.class.getClassLoader());
            a.mId = source.readString();
            return a;
        }

        @Override
        public Action[] newArray(final int size) {
            return new Action[size];
        }
    };


    private static class Attributes implements Parcelable {
        @Nullable
        @SerializedName("progress")
        private Integer mProgress;

        @Nullable
        @SerializedName("rating")
        private Rating mRating;

        @SerializedName("time")
        private SimpleDate mTime;

        @Nullable
        @SerializedName("status")
        private Status mStatus;

        @SerializedName("foreignId")
        private String mForeignId;

        @Nullable
        @SerializedName("streamId")
        private String mStreamId;

        @SerializedName("verb")
        private Verb mVerb;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(final Parcel dest, final int flags) {
            dest.writeValue(mProgress);
            dest.writeParcelable(mRating, flags);
            dest.writeParcelable(mTime, flags);
            dest.writeParcelable(mStatus, flags);
            dest.writeString(mForeignId);
            dest.writeString(mStreamId);
            dest.writeParcelable(mVerb, flags);
        }

        public static final Creator<Attributes> CREATOR = new Creator<Attributes>() {
            @Override
            public Attributes createFromParcel(final Parcel source) {
                final Attributes a = new Attributes();
                a.mProgress = (Integer) source.readValue(Integer.class.getClassLoader());
                a.mRating = source.readParcelable(Rating.class.getClassLoader());
                a.mTime = source.readParcelable(SimpleDate.class.getClassLoader());
                a.mStatus = source.readParcelable(Status.class.getClassLoader());
                a.mForeignId = source.readString();
                a.mStreamId = source.readString();
                a.mVerb = source.readParcelable(Verb.class.getClassLoader());
                return a;
            }

            @Override
            public Attributes[] newArray(final int size) {
                return new Attributes[size];
            }
        };
    }

    public enum Verb implements Parcelable {
        @SerializedName("comment")
        COMMENT,

        @SerializedName("follow")
        FOLLOW,

        @SerializedName("post")
        POST,

        @SerializedName("progressed")
        PROGRESSED,

        @SerializedName("rated")
        RATED,

        @SerializedName("reviewed")
        REVIEWED,

        @SerializedName("updated")
        UPDATED;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(final Parcel dest, final int flags) {
            dest.writeInt(ordinal());
        }

        public static final Creator<Verb> CREATOR = new Creator<Verb>() {
            @Override
            public Verb createFromParcel(final Parcel source) {
                return values()[source.readInt()];
            }

            @Override
            public Verb[] newArray(final int size) {
                return new Verb[size];
            }
        };
    }

}
