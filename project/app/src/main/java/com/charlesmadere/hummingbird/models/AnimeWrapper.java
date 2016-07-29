package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class AnimeWrapper implements Parcelable {

    @SerializedName("anime")
    private Anime mAnime;


    public Anime getAnime() {
        return mAnime;
    }

    @Override
    public String toString() {
        return mAnime.toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeParcelable(mAnime, flags);
    }

    public static final Creator<AnimeWrapper> CREATOR = new Creator<AnimeWrapper>() {
        @Override
        public AnimeWrapper createFromParcel(final Parcel source) {
            final AnimeWrapper aw = new AnimeWrapper();
            aw.mAnime = source.readParcelable(Anime.class.getClassLoader());
            return aw;
        }

        @Override
        public AnimeWrapper[] newArray(final int size) {
            return new AnimeWrapper[size];
        }
    };

}
