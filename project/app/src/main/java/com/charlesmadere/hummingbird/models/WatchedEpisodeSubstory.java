package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class WatchedEpisodeSubstory extends AbsSubstory implements Parcelable {

    @SerializedName("episode_number")
    private int mEpisodeNumber;


    public int getEpisodeNumber() {
        return mEpisodeNumber;
    }

    @Override
    public Type getType() {
        return Type.WATCHED_EPISODE;
    }

    @Override
    protected void readFromParcel(final Parcel source) {
        super.readFromParcel(source);
        mEpisodeNumber = source.readInt();
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(mEpisodeNumber);
    }

    public static final Creator<WatchedEpisodeSubstory> CREATOR = new Creator<WatchedEpisodeSubstory>() {
        @Override
        public WatchedEpisodeSubstory createFromParcel(final Parcel source) {
            final WatchedEpisodeSubstory wes = new WatchedEpisodeSubstory();
            wes.readFromParcel(source);
            return wes;
        }

        @Override
        public WatchedEpisodeSubstory[] newArray(final int size) {
            return new WatchedEpisodeSubstory[size];
        }
    };

}
