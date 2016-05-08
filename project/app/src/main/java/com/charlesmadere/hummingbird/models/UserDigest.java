package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.charlesmadere.hummingbird.misc.ParcelableUtils;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
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

    public void hydrate() {
        if (hasFavorites()) {
            for (final Favorite favorite : mFavorites) {
                favorite.hydrate(this);
            }
        }
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
        @SerializedName("item")
        private AbsItem mItem;

        @SerializedName("fav_rank")
        private int mFavoriteRank;

        @SerializedName("id")
        private String mId;

        @SerializedName("user_id")
        private String mUserId;

        // hydrated fields
        private AbsUser mUser;

        public int getFavoriteRank() {
            return mFavoriteRank;
        }

        public String getId() {
            return mId;
        }

        public AbsItem getItem() {
            return mItem;
        }

        public AbsUser getUser() {
            return mUser;
        }

        public String getUserId() {
            return mUserId;
        }

        public void hydrate(final UserDigest userDigest) {

        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(final Parcel dest, final int flags) {
            dest.writeInt(mFavoriteRank);
            ParcelableUtils.writeUserDigestFavoriteAbsItemToParcel(mItem, dest, flags);
            dest.writeString(mId);
            dest.writeString(mUserId);
            ParcelableUtils.writeAbsUserToParcel(mUser, dest, flags);
        }

        public static final Creator<Favorite> CREATOR = new Creator<Favorite>() {
            @Override
            public Favorite createFromParcel(final Parcel source) {
                final Favorite f = new Favorite();
                f.mFavoriteRank = source.readInt();
                f.mItem = ParcelableUtils.readUserDigestFavoriteAbsItemFromParcel(source);
                f.mId = source.readString();
                f.mUserId = source.readString();
                f.mUser = ParcelableUtils.readAbsUserFromParcel(source);
                return f;
            }

            @Override
            public Favorite[] newArray(final int size) {
                return new Favorite[size];
            }
        };

        public static abstract class AbsItem implements Parcelable {
            @SerializedName("id")
            private String mId;

            public abstract Type getType();

            public abstract void hydrate(final UserDigest userDigest);

            @Override
            public int describeContents() {
                return 0;
            }

            protected void readFromParcel(final Parcel source) {
                mId = source.readString();
            }

            @Override
            public void writeToParcel(final Parcel dest, final int flags) {
                dest.writeString(mId);
            }

            public enum Type implements Parcelable {
                @SerializedName("anime")
                ANIME;

                public static Type from(final String type) {
                    switch (type) {
                        case "anime":
                            return ANIME;

                        default:
                            throw new IllegalArgumentException("encountered unknown " +
                                    Type.class.getName() + ": \"" + type + '"');
                    }
                }

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

            public static final JsonDeserializer<AbsItem> JSON_DESERIALIZER = new JsonDeserializer<AbsItem>() {
                @Override
                public AbsItem deserialize(final JsonElement json,
                        final java.lang.reflect.Type typeOfT,
                        final JsonDeserializationContext context) throws JsonParseException {
                    final JsonObject jsonObject = json.getAsJsonObject();
                    final Type type = Type.from(jsonObject.get("type").getAsString());

                    final AbsItem item;

                    switch (type) {
                        case ANIME:
                            item = context.deserialize(json, AnimeItem.class);
                            break;

                        default:
                            throw new RuntimeException("encountered unknown " +
                                    Type.class.getName() + ": \"" + type + '"');
                    }

                    return item;
                }
            };
        }

        public static class AnimeItem extends AbsItem implements Parcelable {
            // hydrated fields
            private AbsAnime mAnime;

            public AbsAnime getAnime() {
                return mAnime;
            }

            @Override
            public Type getType() {
                return Type.ANIME;
            }

            @Override
            public void hydrate(final UserDigest userDigest) {
                // TODO
            }

            @Override
            protected void readFromParcel(final Parcel source) {
                super.readFromParcel(source);
                mAnime = ParcelableUtils.readAbsAnimeFromParcel(source);
            }

            @Override
            public void writeToParcel(final Parcel dest, final int flags) {
                super.writeToParcel(dest, flags);
                ParcelableUtils.writeAbsAnimeToParcel(mAnime, dest, flags);
            }

            public static final Creator<AnimeItem> CREATOR = new Creator<AnimeItem>() {
                @Override
                public AnimeItem createFromParcel(final Parcel source) {
                    final AnimeItem ai = new AnimeItem();
                    ai.readFromParcel(source);
                    return ai;
                }

                @Override
                public AnimeItem[] newArray(final int size) {
                    return new AnimeItem[size];
                }
            };
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
