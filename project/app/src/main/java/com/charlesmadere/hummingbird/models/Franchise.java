package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Franchise {

    @Nullable
    @SerializedName("anime")
    private ArrayList<Anime> mAnime;

    @SerializedName("franchise")
    private Data mData;


    @Nullable
    public ArrayList<Anime> getAnime() {
        return mAnime;
    }

    public Data getData() {
        return mData;
    }

    public boolean hasAnime() {
        return mAnime != null && !mAnime.isEmpty();
    }

    @Override
    public String toString() {
        return getData().toString();
    }


    public static class Data implements Parcelable {
        @Nullable
        @SerializedName("anime_ids")
        private ArrayList<String> mAnimeIds;

        @SerializedName("id")
        private String mId;


        @Nullable
        public ArrayList<String> getAnimeIds() {
            return mAnimeIds;
        }

        public String getId() {
            return mId;
        }

        public boolean hasAnimeIds() {
            return mAnimeIds != null && !mAnimeIds.isEmpty();
        }

        @Override
        public String toString() {
            return getId();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(final Parcel dest, final int flags) {
            dest.writeStringList(mAnimeIds);
            dest.writeString(mId);
        }

        public static final Creator<Data> CREATOR = new Creator<Data>() {
            @Override
            public Data createFromParcel(final Parcel source) {
                final Data d = new Data();
                d.mAnimeIds = source.createStringArrayList();
                d.mId = source.readString();
                return d;
            }

            @Override
            public Data[] newArray(final int size) {
                return new Data[size];
            }
        };
    }

}
