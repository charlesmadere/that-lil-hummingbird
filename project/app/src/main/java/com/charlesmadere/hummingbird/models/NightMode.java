package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatDelegate;

import com.charlesmadere.hummingbird.R;
import com.google.gson.annotations.SerializedName;

public enum NightMode implements Parcelable {

    @SerializedName("auto")
    AUTO(R.string.auto, AppCompatDelegate.MODE_NIGHT_AUTO),

    @SerializedName("night_no")
    NIGHT_NO(R.string.day, AppCompatDelegate.MODE_NIGHT_NO),

    @SerializedName("night_yes")
    NIGHT_YES(R.string.night, AppCompatDelegate.MODE_NIGHT_YES),

    @SerializedName("system")
    SYSTEM(R.string.system, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);

    @StringRes
    private final int mTextResId;

    @AppCompatDelegate.NightMode
    private final int mThemeValue;


    public static NightMode getDefault() {
        return SYSTEM;
    }

    NightMode(@StringRes final int textResId, @AppCompatDelegate.NightMode final int themeValue) {
        mTextResId = textResId;
        mThemeValue = themeValue;
    }

    @StringRes
    public int getTextResId() {
        return mTextResId;
    }

    @AppCompatDelegate.NightMode
    public int getThemeValue() {
        return mThemeValue;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeInt(ordinal());
    }

    public static final Creator<NightMode> CREATOR = new Creator<NightMode>() {
        @Override
        public NightMode createFromParcel(final Parcel source) {
            return values()[source.readInt()];
        }

        @Override
        public NightMode[] newArray(final int size) {
            return new NightMode[size];
        }
    };

}
