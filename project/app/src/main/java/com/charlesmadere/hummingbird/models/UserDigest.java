package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.charlesmadere.hummingbird.misc.ParcelableUtils;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UserDigest implements Parcelable {

    @Nullable
    @SerializedName("anime")
    private ArrayList<AbsAnime> mAnime;

    @SerializedName("users")
    private ArrayList<AbsUser> mUsers;

    @Nullable
    @SerializedName("favorites")
    private ArrayList<Favorite> mFavorites;

    @SerializedName("user_info")
    private UserInfo mUserInfo;


    @Nullable
    public ArrayList<AbsAnime> getAnime() {
        return mAnime;
    }

    @Nullable
    public ArrayList<Favorite> getFavorites() {
        return mFavorites;
    }

    public AbsUser getUser() {
        return mUsers.get(0);
    }

    public UserInfo getUserInfo() {
        return mUserInfo;
    }

    public boolean hasAnime() {
        return mAnime != null && !mAnime.isEmpty();
    }

    public boolean hasFavorites() {
        return mFavorites != null && !mFavorites.isEmpty();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        ParcelableUtils.writeAbsAnimeListToParcel(mAnime, dest, flags);
        ParcelableUtils.writeAbsUserListToParcel(mUsers, dest, flags);
        dest.writeTypedList(mFavorites);
        dest.writeParcelable(mUserInfo, flags);
    }

    public static final Creator<UserDigest> CREATOR = new Creator<UserDigest>() {
        @Override
        public UserDigest createFromParcel(final Parcel source) {
            final UserDigest ud = new UserDigest();
            ud.mAnime = ParcelableUtils.readAbsAnimeListFromParcel(source);
            ud.mUsers = ParcelableUtils.readAbsUserListFromParcel(source);
            ud.mFavorites = source.createTypedArrayList(Favorite.CREATOR);
            ud.mUserInfo = source.readParcelable(UserInfo.class.getClassLoader());
            return ud;
        }

        @Override
        public UserDigest[] newArray(final int size) {
            return new UserDigest[size];
        }
    };


    public static class Favorite implements Parcelable {
        @SerializedName("fav_rank")
        private int mFavoriteRank;

        @SerializedName("item")
        private Item mItem;

        @SerializedName("id")
        private String mId;

        @SerializedName("user_id")
        private String mUserId;

        public int getFavoriteRank() {
            return mFavoriteRank;
        }

        public String getId() {
            return mId;
        }

        public Item getItem() {
            return mItem;
        }

        public String getUserId() {
            return mUserId;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(final Parcel dest, final int flags) {
            dest.writeInt(mFavoriteRank);
            dest.writeParcelable(mItem, flags);
            dest.writeString(mId);
            dest.writeString(mUserId);
        }

        public static final Creator<Favorite> CREATOR = new Creator<Favorite>() {
            @Override
            public Favorite createFromParcel(final Parcel source) {
                final Favorite f = new Favorite();
                f.mFavoriteRank = source.readInt();
                f.mItem = source.readParcelable(Item.class.getClassLoader());
                f.mId = source.readString();
                f.mUserId = source.readString();
                return f;
            }

            @Override
            public Favorite[] newArray(final int size) {
                return new Favorite[size];
            }
        };

        public static class Item implements Parcelable {
            @SerializedName("id")
            private String mId;

            @SerializedName("type")
            private Type mType;

            // hydrated fields
            private AbsAnime mAnime;

            public AbsAnime getAnime() {
                return mAnime;
            }

            public String getId() {
                return mId;
            }

            public Type getType() {
                return mType;
            }

            public void setAnime(final AbsAnime anime) {
                mAnime = anime;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(final Parcel dest, final int flags) {
                dest.writeString(mId);
                dest.writeParcelable(mType, flags);
                ParcelableUtils.writeAbsAnimeToParcel(mAnime, dest, flags);
            }

            public static final Creator<Item> CREATOR = new Creator<Item>() {
                @Override
                public Item createFromParcel(final Parcel source) {
                    final Item i = new Item();
                    i.mId = source.readString();
                    i.mType = source.readParcelable(Type.class.getClassLoader());
                    i.mAnime = ParcelableUtils.readAbsAnimeFromParcel(source);
                    return i;
                }

                @Override
                public Item[] newArray(final int size) {
                    return new Item[size];
                }
            };

            public enum Type implements Parcelable {
                @SerializedName("anime")
                ANIME;

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(final Parcel dest, final int flags) {
                    dest.writeInt(ordinal());
                }

                public static final Creator<Type> CREATOR = new Creator<Type>() {
                    @Override
                    public Type createFromParcel(final Parcel source) {
                        final int ordinal = source.readInt();
                        return values()[ordinal];
                    }

                    @Override
                    public Type[] newArray(final int size) {
                        return new Type[size];
                    }
                };
            }
        }
    }

}
