package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Favorite implements Parcelable {

    @SerializedName("created_at")
    private SimpleDate mCreatedAt;

    @SerializedName("updated_at")
    private SimpleDate mUpdatedAt;

    @SerializedName("id")
    private String mId;

    @SerializedName("item_id")
    private String mItemId;

    @SerializedName("user_id")
    private String mUserId;

    @SerializedName("item_type")
    private Type mType;


    public SimpleDate getCreatedAt() {
        return mCreatedAt;
    }

    public SimpleDate getUpdatedAt() {
        return mUpdatedAt;
    }

    public String getId() {
        return mId;
    }

    public String getItemId() {
        return mItemId;
    }

    public Type getType() {
        return mType;
    }

    public String getUserId() {
        return mUserId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeParcelable(mCreatedAt, flags);
        dest.writeParcelable(mUpdatedAt, flags);
        dest.writeString(mId);
        dest.writeString(mItemId);
        dest.writeString(mUserId);
        dest.writeParcelable(mType, flags);
    }

    public static final Creator<Favorite> CREATOR = new Creator<Favorite>() {
        @Override
        public Favorite createFromParcel(final Parcel source) {
            final Favorite f = new Favorite();
            f.mCreatedAt = source.readParcelable(SimpleDate.class.getClassLoader());
            f.mUpdatedAt = source.readParcelable(SimpleDate.class.getClassLoader());
            f.mId = source.readString();
            f.mItemId = source.readString();
            f.mUserId = source.readString();
            f.mType = source.readParcelable(Type.class.getClassLoader());
            return f;
        }

        @Override
        public Favorite[] newArray(final int size) {
            return new Favorite[size];
        }
    };


    public enum Type implements Parcelable {
        @SerializedName("Anime")
        ANIME;


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
