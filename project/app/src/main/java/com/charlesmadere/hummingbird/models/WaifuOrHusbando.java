package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.StringRes;

import com.charlesmadere.hummingbird.R;
import com.google.gson.annotations.SerializedName;

public enum WaifuOrHusbando implements Parcelable {

    @SerializedName("Husbando")
    HUSBANDO(R.string.husbando_is, R.string.husbando),

    @SerializedName("Waifu")
    WAIFU(R.string.waifu_is, R.string.waifu);


    @StringRes
    private final int mExtendedTextResId;

    @StringRes
    private final int mTextResId;


    WaifuOrHusbando(@StringRes final int extendedTextResId, @StringRes final int textResId) {
        mExtendedTextResId = extendedTextResId;
        mTextResId = textResId;
    }

    @StringRes
    public int getExtendedTextResId() {
        return mExtendedTextResId;
    }

    @StringRes
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

    public static final Creator<WaifuOrHusbando> CREATOR = new Creator<WaifuOrHusbando>() {
        @Override
        public WaifuOrHusbando createFromParcel(final Parcel source) {
            final int ordinal = source.readInt();
            return values()[ordinal];
        }

        @Override
        public WaifuOrHusbando[] newArray(final int size) {
            return new WaifuOrHusbando[size];
        }
    };

}
