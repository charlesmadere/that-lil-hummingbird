package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UserInfo implements Parcelable {

    @Nullable
    @SerializedName("top_genres")
    private ArrayList<Genre> mTopGenres;

    @Nullable
    @SerializedName("favorite_ids")
    private ArrayList<String> mFavoriteIds;

    @SerializedName("anime_watched")
    private int mAnimeWatched;

    @SerializedName("life_spent_on_anime")
    private long mLifeSpentOnAnime;

    @SerializedName("id")
    private String mId;


    public int getAnimeWatched() {
        return mAnimeWatched;
    }

    @Nullable
    public ArrayList<String> getFavoriteIds() {
        return mFavoriteIds;
    }

    public String getId() {
        return mId;
    }

    public long getLifeSpentOnAnime() {
        return mLifeSpentOnAnime;
    }

    @Nullable
    public ArrayList<Genre> getTopGenres() {
        return mTopGenres;
    }

    public boolean hasFavoriteIds() {
        return mFavoriteIds != null && !mFavoriteIds.isEmpty();
    }

    public boolean hasTopGenres() {
        return mTopGenres != null && !mTopGenres.isEmpty();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeTypedList(mTopGenres);
        dest.writeStringList(mFavoriteIds);
        dest.writeInt(mAnimeWatched);
        dest.writeLong(mLifeSpentOnAnime);
        dest.writeString(mId);
    }

    public static final Creator<UserInfo> CREATOR = new Creator<UserInfo>() {
        @Override
        public UserInfo createFromParcel(final Parcel source) {
            final UserInfo ui = new UserInfo();
            ui.mTopGenres = source.createTypedArrayList(Genre.CREATOR);
            ui.mFavoriteIds = source.createStringArrayList();
            ui.mAnimeWatched = source.readInt();
            ui.mLifeSpentOnAnime = source.readLong();
            ui.mId = source.readString();
            return ui;
        }

        @Override
        public UserInfo[] newArray(final int size) {
            return new UserInfo[size];
        }
    };


    public static class Genre implements Parcelable {
        @SerializedName("genre")
        private Data mData;

        @SerializedName("num")
        private int mNum;

        public Data getData() {
            return mData;
        }

        public int getNum() {
            return mNum;
        }

        @Override
        public String toString() {
            return mData.toString();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(final Parcel dest, final int flags) {
            dest.writeParcelable(mData, flags);
            dest.writeInt(mNum);
        }

        public static final Creator<Genre> CREATOR = new Creator<Genre>() {
            @Override
            public Genre createFromParcel(final Parcel source) {
                final Genre g = new Genre();
                g.mData = source.readParcelable(Data.class.getClassLoader());
                g.mNum = source.readInt();
                return g;
            }

            @Override
            public Genre[] newArray(final int size) {
                return new Genre[size];
            }
        };

        public static class Data implements Parcelable {
            @Nullable
            @SerializedName("description")
            private String mDescription;

            @SerializedName("id")
            private String mId;

            @SerializedName("name")
            private String mName;

            @SerializedName("slug")
            private String mSlug;

            @Nullable
            public String getDescription() {
                return mDescription;
            }

            public String getId() {
                return mId;
            }

            public String getName() {
                return mName;
            }

            public String getSlug() {
                return mSlug;
            }

            public boolean hasDescription() {
                return !TextUtils.isEmpty(mDescription);
            }

            @Override
            public String toString() {
                return getName();
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(final Parcel dest, final int flags) {
                dest.writeString(mDescription);
                dest.writeString(mId);
                dest.writeString(mName);
                dest.writeString(mSlug);
            }

            public static final Creator<Data> CREATOR = new Creator<Data>() {
                @Override
                public Data createFromParcel(final Parcel source) {
                    final Data d = new Data();
                    d.mDescription = source.readString();
                    d.mId = source.readString();
                    d.mName = source.readString();
                    d.mSlug = source.readString();
                    return d;
                }

                @Override
                public Data[] newArray(final int size) {
                    return new Data[size];
                }
            };
        }
    }

}
