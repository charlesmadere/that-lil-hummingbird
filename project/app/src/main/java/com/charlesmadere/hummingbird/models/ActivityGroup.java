package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class ActivityGroup implements DataObject, Parcelable {

    @SerializedName("attributes")
    private Attributes mAttributes;

    @SerializedName("dataType")
    private DataType mDataType;

    @SerializedName("relationships")
    private Relationships mRelationships;

    @SerializedName("id")
    private String mId;


    @Override
    public boolean equals(final Object o) {
        return o instanceof ActivityGroup && mId.equals(((ActivityGroup) o).getId());
    }

    @Override
    public DataType getDataType() {
        return mDataType;
    }

    public String getGroup() {
        return mAttributes.mGroup;
    }

    @Override
    public String getId() {
        return mId;
    }

    public Relationships getRelationships() {
        return mRelationships;
    }

    @Override
    public int hashCode() {
        return mId.hashCode();
    }

    @Nullable
    public Boolean isRead() {
        return mAttributes.mIsRead;
    }

    @Nullable
    public Boolean isSeen() {
        return mAttributes.mIsSeen;
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

    public static final Creator<ActivityGroup> CREATOR = new Creator<ActivityGroup>() {
        @Override
        public ActivityGroup createFromParcel(final Parcel source) {
            final ActivityGroup ag = new ActivityGroup();
            ag.mAttributes = source.readParcelable(Attributes.class.getClassLoader());
            ag.mDataType = source.readParcelable(DataType.class.getClassLoader());
            ag.mRelationships = source.readParcelable(Relationships.class.getClassLoader());
            ag.mId = source.readString();
            return ag;
        }

        @Override
        public ActivityGroup[] newArray(final int size) {
            return new ActivityGroup[size];
        }
    };


    private static class Attributes implements Parcelable {
        @Nullable
        @SerializedName("isRead")
        private Boolean mIsRead;

        @Nullable
        @SerializedName("isSeen")
        private Boolean mIsSeen;

        @SerializedName("group")
        private String mGroup;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(final Parcel dest, final int flags) {
            dest.writeValue(mIsRead);
            dest.writeValue(mIsSeen);
            dest.writeString(mGroup);
        }

        public static final Creator<Attributes> CREATOR = new Creator<Attributes>() {
            @Override
            public Attributes createFromParcel(final Parcel source) {
                final Attributes a = new Attributes();
                a.mIsRead = (Boolean) source.readValue(Boolean.class.getClassLoader());
                a.mIsSeen = (Boolean) source.readValue(Boolean.class.getClassLoader());
                a.mGroup = source.readString();
                return a;
            }

            @Override
            public Attributes[] newArray(final int size) {
                return new Attributes[size];
            }
        };
    }

}
