package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public enum TitleLanguagePreference implements Parcelable {

    @SerializedName("canonical")
    CANONICAL,

    @SerializedName("english")
    ENGLISH,

    @SerializedName("romanized")
    ROMANIZED;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeInt(ordinal());
    }

    public static final Creator<TitleLanguagePreference> CREATOR = new Creator<TitleLanguagePreference>() {
        @Override
        public TitleLanguagePreference createFromParcel(final Parcel source) {
            final int ordinal = source.readInt();
            return values()[ordinal];
        }

        @Override
        public TitleLanguagePreference[] newArray(final int size) {
            return new TitleLanguagePreference[size];
        }
    };

}
