package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Type;

public enum Rating implements Parcelable {

    @SerializedName("5")
    FIVE(5f),

    @SerializedName("4.5")
    FOUR_POINT_FIVE(4.5f),

    @SerializedName("4")
    FOUR(4f),

    @SerializedName("3.5")
    THREE_POINT_FIVE(3.5f),

    @SerializedName("3")
    THREE(3f),

    @SerializedName("2.5")
    TWO_POINT_FIVE(2.5f),

    @SerializedName("2")
    TWO(2f),

    @SerializedName("1.5")
    ONE_POINT_FIVE(1.5f),

    @SerializedName("1")
    ONE(1f),

    @SerializedName("0.5")
    ZERO_POINT_FIVE(0.5f),

    @SerializedName("0")
    ZERO(0f),

    @SerializedName("unrated")
    UNRATED(Float.MIN_VALUE);

    private final float mValue;


    public static int compare(@Nullable final Rating l, @Nullable final Rating r) {
        final int lOrdinal = l == null ? UNRATED.ordinal() : l.ordinal();
        final int rOrdinal = r == null ? UNRATED.ordinal() : r.ordinal();
        return lOrdinal - rOrdinal;
    }

    public static boolean equals(@Nullable final Rating l, @Nullable final Rating r) {
        return l == r || (l == null && r == UNRATED) || (l == UNRATED && r == null);
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

    public static final JsonDeserializer<Rating> JSON_DESERIALIZER = new JsonDeserializer<Rating>() {
        @Override
        public Rating deserialize(final JsonElement json, final Type typeOfT,
                final JsonDeserializationContext context) throws JsonParseException {
            if (json == null || json.isJsonNull()) {
                return null;
            }

            final float value = json.getAsFloat();

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
    };

}
