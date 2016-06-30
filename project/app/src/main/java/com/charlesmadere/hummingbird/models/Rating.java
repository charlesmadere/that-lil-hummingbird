package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public enum Rating implements Parcelable {
    @SerializedName("0")
    ZERO(0f),

    @SerializedName("0.5")
    ZERO_POINT_FIVE(0.5f),

    @SerializedName("1")
    ONE(1f),

    @SerializedName("1.5")
    ONE_POINT_FIVE(1.5f),

    @SerializedName("2")
    TWO(2f),

    @SerializedName("2.5")
    TWO_POINT_FIVE(2.5f),

    @SerializedName("3")
    THREE(3f),

    @SerializedName("3.5")
    THREE_POINT_FIVE(3.5f),

    @SerializedName("4")
    FOUR(4f),

    @SerializedName("4.5")
    FOUR_POINT_FIVE(4.5f),

    @SerializedName("5")
    FIVE(5f);

    private final float mValue;


    public static Rating from(final LibraryEntry libraryEntry) {
        final LibraryEntry.Rating rating = libraryEntry.getRating();

        if (rating == null || !rating.hasValue()) {
            throw new IllegalArgumentException("rating is null or has no value");
        }

        final float value = Float.parseFloat(rating.getValue());

        if (value >= FIVE.mValue) {
            return FIVE;
        } else if (value >= FOUR_POINT_FIVE.mValue) {
            return FOUR_POINT_FIVE;
        } else if (value >= FOUR.mValue) {
            return FOUR;
        } else if (value >= THREE_POINT_FIVE.mValue) {
            return THREE_POINT_FIVE;
        } else if (value >= THREE.mValue) {
            return THREE;
        } else if (value >= TWO_POINT_FIVE.mValue) {
            return TWO_POINT_FIVE;
        } else if (value >= TWO.mValue) {
            return TWO;
        } else if (value >= ONE_POINT_FIVE.mValue) {
            return ONE_POINT_FIVE;
        } else if (value >= ONE.mValue) {
            return ONE;
        } else if (value >= ZERO_POINT_FIVE.mValue) {
            return ZERO_POINT_FIVE;
        } else {
            return ZERO;
        }
    }

    Rating(final float value) {
        mValue = value;
    }

    public float getValue() {
        return mValue;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeInt(ordinal());
    }

    public static final Creator<Rating> CREATOR = new Creator<Rating>() {
        @Override
        public Rating createFromParcel(final Parcel source) {
            return values()[source.readInt()];
        }

        @Override
        public Rating[] newArray(final int size) {
            return new Rating[size];
        }
    };
}
