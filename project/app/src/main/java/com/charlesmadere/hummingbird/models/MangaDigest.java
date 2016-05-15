package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class MangaDigest implements Parcelable {

    @SerializedName("full_manga")
    private Manga mManga;


    public Manga getManga() {
        return mManga;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeParcelable(mManga, flags);
    }

    public static final Creator<MangaDigest> CREATOR = new Creator<MangaDigest>() {
        @Override
        public MangaDigest createFromParcel(final Parcel source) {
            final MangaDigest md = new MangaDigest();
            md.mManga = source.readParcelable(Manga.class.getClassLoader());
            return md;
        }

        @Override
        public MangaDigest[] newArray(final int size) {
            return new MangaDigest[size];
        }
    };

}
