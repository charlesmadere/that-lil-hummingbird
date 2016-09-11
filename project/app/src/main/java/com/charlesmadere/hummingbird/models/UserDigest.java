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
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;

public class UserDigest implements Hydratable, Parcelable {

    @Nullable
    @SerializedName("anime")
    private ArrayList<Anime> mAnime;

    @Nullable
    @SerializedName("favorites")
    private ArrayList<Favorite> mFavorites;

    @Nullable
    @SerializedName("manga")
    private ArrayList<Manga> mManga;

    @Nullable
    @SerializedName("users")
    private ArrayList<User> mUsers;

    @SerializedName("user_info")
    private Info mInfo;


    @Override
    public boolean equals(final Object o) {
        return o instanceof UserDigest && getUserId().equalsIgnoreCase(((UserDigest) o).getUserId());
    }

    @Nullable
    public ArrayList<Anime> getAnime() {
        return mAnime;
    }

    @Nullable
    public ArrayList<Favorite> getFavorites() {
        return mFavorites;
    }

    public Info getInfo() {
        return mInfo;
    }

    @Nullable
    public ArrayList<Manga> getManga() {
        return mManga;
    }

    public User getUser() {
        return mUsers.get(0);
    }

    public String getUserId() {
        return getUser().getId();
    }

    public boolean hasAnime() {
        return mAnime != null && !mAnime.isEmpty();
    }

    public boolean hasFavorites() {
        return mFavorites != null && !mFavorites.isEmpty();
    }

    @Override
    public int hashCode() {
        return getUserId().hashCode();
    }

    public boolean hasManga() {
        return mManga != null && !mManga.isEmpty();
    }

    @Override
    public void hydrate() {
        for (final User user : mUsers) {
            user.hydrate();
        }

        if (hasFavorites()) {
            for (final Favorite favorite : mFavorites) {
                favorite.hydrate(this);
            }

            Collections.sort(mFavorites, Favorite.COMPARATOR);
        }
    }

    public boolean isMissingUser() {
        return mUsers == null || mUsers.isEmpty();
    }

    public void setUser(final User user) {
        if (!isMissingUser()) {
            throw new RuntimeException("User already exists: (" + getUserId() + ')');
        } else if (user == null) {
            throw new IllegalArgumentException("user parameter can't be null");
        }

        if (mUsers == null) {
            mUsers = new ArrayList<>(1);
        }

        mUsers.add(user);
    }

    @Override
    public String toString() {
        return getUserId();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeTypedList(mAnime);
        dest.writeTypedList(mUsers);
        dest.writeTypedList(mFavorites);
        dest.writeTypedList(mManga);
        dest.writeParcelable(mInfo, flags);
    }

    public static final Creator<UserDigest> CREATOR = new Creator<UserDigest>() {
        @Override
        public UserDigest createFromParcel(final Parcel source) {
            final UserDigest ud = new UserDigest();
            ud.mAnime = source.createTypedArrayList(Anime.CREATOR);
            ud.mUsers = source.createTypedArrayList(User.CREATOR);
            ud.mFavorites = source.createTypedArrayList(Favorite.CREATOR);
            ud.mManga = source.createTypedArrayList(Manga.CREATOR);
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


        @Override
        public boolean equals(final Object o) {
            return o instanceof Favorite && mId.equalsIgnoreCase(((Favorite) o).getId());
        }

        public int getFavoriteRank() {
            return mFavoriteRank;
        }

        public String getId() {
            return mId;
        }

        public AbsItem getItem() {
            return mItem;
        }

        public String getUserId() {
            return mUserId;
        }

        @Override
        public int hashCode() {
            return mId.hashCode();
        }

        public void hydrate(final UserDigest userDigest) {
            mItem.hydrate(userDigest);
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
        }

        public static final Creator<Favorite> CREATOR = new Creator<Favorite>() {
            @Override
            public Favorite createFromParcel(final Parcel source) {
                final Favorite f = new Favorite();
                f.mFavoriteRank = source.readInt();
                f.mItem = ParcelableUtils.readUserDigestFavoriteAbsItem(source);
                f.mId = source.readString();
                f.mUserId = source.readString();
                return f;
            }

            @Override
            public Favorite[] newArray(final int size) {
                return new Favorite[size];
            }
        };

        public static final Comparator<Favorite> COMPARATOR = new Comparator<Favorite>() {
            @Override
            public int compare(final Favorite lhs, final Favorite rhs) {
                return lhs.getFavoriteRank() - rhs.getFavoriteRank();
            }
        };

        public static abstract class AbsItem implements Parcelable {
            @SerializedName("id")
            private String mId;

            @SerializedName("type")
            private Type mType;

            @Override
            public boolean equals(final Object o) {
                return o instanceof AbsItem && mId.equalsIgnoreCase(((AbsItem) o).getId());
            }

            public String getId() {
                return mId;
            }

            public Type getType() {
                return mType;
            }

            @Override
            public int hashCode() {
                return mId.hashCode();
            }

            public abstract void hydrate(final UserDigest userDigest);

            @Override
            public String toString() {
                return getType().toString();
            }

            @Override
            public int describeContents() {
                return 0;
            }

            protected void readFromParcel(final Parcel source) {
                mId = source.readString();
                mType = source.readParcelable(Type.class.getClassLoader());
            }

            @Override
            public void writeToParcel(final Parcel dest, final int flags) {
                dest.writeString(mId);
                dest.writeParcelable(mType, flags);
            }

            public enum Type implements Parcelable {
                @SerializedName("anime")
                ANIME,

                @SerializedName("manga")
                MANGA;

                public static Type from(final String type) {
                    switch (type) {
                        case "anime":
                            return ANIME;

                        case "manga":
                            return MANGA;

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
                        return values()[source.readInt()];
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

                        case MANGA:
                            item = context.deserialize(json, MangaItem.class);
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
            private Anime mAnime;

            public Anime getAnime() {
                return mAnime;
            }

            @Override
            public void hydrate(final UserDigest userDigest) {
                for (final Anime anime : userDigest.getAnime()) {
                    if (getId().equalsIgnoreCase(anime.getId())) {
                        mAnime = anime;
                        return;
                    }
                }

                throw new RuntimeException("couldn't find Anime with ID \"" + getId() + '"');
            }

            @Override
            protected void readFromParcel(final Parcel source) {
                super.readFromParcel(source);
                mAnime = source.readParcelable(Anime.class.getClassLoader());
            }

            @Override
            public void writeToParcel(final Parcel dest, final int flags) {
                super.writeToParcel(dest, flags);
                dest.writeParcelable(mAnime, flags);
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

        public static class MangaItem extends AbsItem implements Parcelable {
            // hydrated fields
            private Manga mManga;

            public Manga getManga() {
                return mManga;
            }

            @Override
            public void hydrate(final UserDigest userDigest) {
                for (final Manga manga : userDigest.getManga()) {
                    if (getId().equalsIgnoreCase(manga.getId())) {
                        mManga = manga;
                        return;
                    }
                }

                throw new RuntimeException("couldn't find Manga with ID \"" + getId() + '"');
            }

            @Override
            protected void readFromParcel(final Parcel source) {
                super.readFromParcel(source);
                mManga = source.readParcelable(Manga.class.getClassLoader());
            }

            @Override
            public void writeToParcel(final Parcel dest, final int flags) {
                super.writeToParcel(dest, flags);
                dest.writeParcelable(mManga, flags);
            }

            public static final Creator<MangaItem> CREATOR = new Creator<MangaItem>() {
                @Override
                public MangaItem createFromParcel(final Parcel source) {
                    final MangaItem mi = new MangaItem();
                    mi.readFromParcel(source);
                    return mi;
                }

                @Override
                public MangaItem[] newArray(final int size) {
                    return new MangaItem[size];
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

        public long getLifeSpentOnAnimeMinutes() {
            return mLifeSpentOnAnime;
        }

        public long getLifeSpentOnAnimeSeconds() {
            return TimeUnit.MINUTES.toSeconds(mLifeSpentOnAnime);
        }

        public Genre getTopGenre() {
            if (!hasTopGenres()) {
                throw new RuntimeException("only use this method if there are top genres");
            }

            Genre topGenre = null;

            for (final Genre genre : mTopGenres) {
                if (topGenre == null || topGenre.getNum() < genre.getNum()) {
                    topGenre = genre;
                }
            }

            return topGenre;
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

                @Override
                public boolean equals(final Object o) {
                    return o instanceof Data && mId.equalsIgnoreCase(((Data) o).getId());
                }

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
                public int hashCode() {
                    return mId.hashCode();
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
