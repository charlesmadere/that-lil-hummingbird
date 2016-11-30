package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class LibraryEntry implements DataObject, Parcelable {

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
        return o instanceof LibraryEntry && mId.equals(((LibraryEntry) o).getId());
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
    public String getNotes() {
        return mAttributes.mNotes;
    }

    public int getProgress() {
        return mAttributes.mProgress;
    }

    @Nullable
    public Rating getRating() {
        return mAttributes.mRating;
    }

    public int getReconsumeCount() {
        return mAttributes.mReconsumeCount;
    }

    public Relationships getRelationships() {
        return mRelationships;
    }

    public Status getStatus() {
        return mAttributes.mStatus;
    }

    public SimpleDate getUpdatedAt() {
        return mAttributes.mUpdatedAt;
    }

    @Override
    public int hashCode() {
        return mId.hashCode();
    }

    public boolean isPrivate() {
        return mAttributes.mPrivate;
    }

    public boolean isReconsuming() {
        return mAttributes.mReconsuming;
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

    public static final Creator<LibraryEntry> CREATOR = new Creator<LibraryEntry>() {
        @Override
        public LibraryEntry createFromParcel(final Parcel source) {
            final LibraryEntry le = new LibraryEntry();
            le.mAttributes = source.readParcelable(Attributes.class.getClassLoader());
            le.mDataType = source.readParcelable(DataType.class.getClassLoader());
            le.mLinks = source.readParcelable(Links.class.getClassLoader());
            le.mRelationships = source.readParcelable(Relationships.class.getClassLoader());
            le.mId = source.readString();
            return le;
        }

        @Override
        public LibraryEntry[] newArray(final int size) {
            return new LibraryEntry[size];
        }
    };


    private static class Attributes implements Parcelable {
        @SerializedName("private")
        private boolean mPrivate;

        @SerializedName("reconsuming")
        private boolean mReconsuming;

        @SerializedName("progress")
        private int mProgress;

        @SerializedName("reconsumeCount")
        private int mReconsumeCount;

        @Nullable
        @SerializedName("rating")
        private Rating mRating;

        @SerializedName("updatedAt")
        private SimpleDate mUpdatedAt;

        @SerializedName("status")
        private Status mStatus;

        @Nullable
        @SerializedName("notes")
        private String mNotes;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(final Parcel dest, final int flags) {
            dest.writeInt(mPrivate ? 1 : 0);
            dest.writeInt(mReconsuming ? 1 : 0);
            dest.writeInt(mProgress);
            dest.writeInt(mReconsumeCount);
            dest.writeParcelable(mRating, flags);
            dest.writeParcelable(mUpdatedAt, flags);
            dest.writeParcelable(mStatus, flags);
            dest.writeString(mNotes);
        }

        public static final Creator<Attributes> CREATOR = new Creator<Attributes>() {
            @Override
            public Attributes createFromParcel(final Parcel source) {
                final Attributes a = new Attributes();
                a.mPrivate = source.readInt() != 0;
                a.mReconsuming = source.readInt() != 0;
                a.mProgress = source.readInt();
                a.mReconsumeCount = source.readInt();
                a.mRating = source.readParcelable(Rating.class.getClassLoader());
                a.mUpdatedAt = source.readParcelable(SimpleDate.class.getClassLoader());
                a.mStatus = source.readParcelable(Status.class.getClassLoader());
                a.mNotes = source.readString();
                return a;
            }

            @Override
            public Attributes[] newArray(final int size) {
                return new Attributes[size];
            }
        };
    }

}
