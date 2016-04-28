package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.StringRes;

import com.charlesmadere.hummingbird.R;

public enum NightMode implements Parcelable {

    AUTO(R.string.auto),

    NIGHT_NO(R.string.day),

    NIGHT_YES(R.string.night),

    SYSTEM(R.string.system);

    private final int mTextResId;


    NightMode(@StringRes final int textResId) {
        mTextResId = textResId;
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

    public static final Creator<NightMode> CREATOR = new Creator<NightMode>() {
        @Override
        public NightMode createFromParcel(final Parcel source) {
            final int ordinal = source.readInt();
            return values()[ordinal];
        }

        @Override
        public NightMode[] newArray(final int size) {
            return new NightMode[size];
        }
    };

}
