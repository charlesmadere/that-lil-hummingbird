package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.StringRes;

import com.charlesmadere.hummingbird.R;
import com.google.gson.annotations.SerializedName;

public enum AgeRating implements Parcelable {

    @SerializedName("G")
    G(R.string.age_rating_g),

    @SerializedName("PG")
    PG(R.string.age_rating_pg),

    @SerializedName("PG13")
    PG13(R.string.age_rating_pg13),

    @SerializedName("R17+")
    R17(R.string.age_rating_r17),

    @SerializedName("R18+")
    R18(R.string.age_rating_r18);

    private final int mTextResId;


    AgeRating(@StringRes final int textResId) {
        mTextResId = textResId;
    }

    public int getTextResId() {
        return mTextResId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeInt(ordinal());
    }

    public static final Creator<AgeRating> CREATOR = new Creator<AgeRating>() {
        @Override
        public AgeRating createFromParcel(final Parcel source) {
            final int ordinal = source.readInt();
            return values()[ordinal];
        }

        @Override
        public AgeRating[] newArray(final int size) {
            return new AgeRating[size];
        }
    };

}
