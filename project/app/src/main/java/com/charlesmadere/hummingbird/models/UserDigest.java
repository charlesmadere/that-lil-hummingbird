package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.TextUtils;

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
    private Info mInfo;


    @Nullable
    public ArrayList<AbsAnime> getAnime() {
        return mAnime;
    }

    @Nullable
    public ArrayList<Favorite> getFavorites() {
        return mFavorites;
    }

    public Info getInfo() {
        return mInfo;
    }

    public AbsUser getUser() {
        return mUsers.get(0);
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
        dest.writeParcelable(mInfo, flags);
    }

    public static final Creator<UserDigest> CREATOR = new Creator<UserDigest>() {
        @Override
        public UserDigest createFromParcel(final Parcel source) {
            final UserDigest ud = new UserDigest();
            ud.mAnime = ParcelableUtils.readAbsAnimeListFromParcel(source);
            ud.mUsers = ParcelableUtils.readAbsUserListFromParcel(source);
            ud.mFavorites = source.createTypedArrayList(Favorite.CREATOR);
            ud.mInfo = source.readParcelable(Info.class.getClassLoader());
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


    public static class Info implements Parcelable {
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

        public static final Creator<Info> CREATOR = new Creator<Info>() {
            @Override
            public Info createFromParcel(final Parcel source) {
                final Info ui = new Info();
                ui.mTopGenres = source.createTypedArrayList(Genre.CREATOR);
                ui.mFavoriteIds = source.createStringArrayList();
                ui.mAnimeWatched = source.readInt();
                ui.mLifeSpentOnAnime = source.readLong();
                ui.mId = source.readString();
                return ui;
            }

            @Override
            public Info[] newArray(final int size) {
                return new Info[size];
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
                @SerializedName("created_at")
                private SimpleDate mCreatedAt;

                @Nullable
                @SerializedName("updated_at")
                private SimpleDate mUpdatedAt;

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
                public SimpleDate getCreatedAt() {
                    return mCreatedAt;
                }

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

                @Nullable
                public SimpleDate getUpdatedAt() {
                    return mUpdatedAt;
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
                    dest.writeParcelable(mCreatedAt, flags);
                    dest.writeParcelable(mUpdatedAt, flags);
                    dest.writeString(mDescription);
                    dest.writeString(mId);
                    dest.writeString(mName);
                    dest.writeString(mSlug);
                }

                public static final Creator<Data> CREATOR = new Creator<Data>() {
                    @Override
                    public Data createFromParcel(final Parcel source) {
                        final Data d = new Data();
                        d.mCreatedAt = source.readParcelable(SimpleDate.class.getClassLoader());
                        d.mUpdatedAt = source.readParcelable(SimpleDate.class.getClassLoader());
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

}
