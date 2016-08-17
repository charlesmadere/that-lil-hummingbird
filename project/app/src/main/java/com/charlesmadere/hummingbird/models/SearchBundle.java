package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.text.TextUtils;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.misc.MiscUtils;
import com.charlesmadere.hummingbird.misc.ParcelableUtils;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SearchBundle implements Parcelable {

    @Nullable
    @SerializedName("search")
    private ArrayList<AbsResult> mResults;


    @Nullable
    public ArrayList<AnimeResult> getAnimeResults() {
        return getResults(AbsResult.Type.ANIME);
    }

    @Nullable
    public ArrayList<GroupResult> getGroupResults() {
        return getResults(AbsResult.Type.GROUP);
    }

    @Nullable
    public ArrayList<MangaResult> getMangaResults() {
        return getResults(AbsResult.Type.MANGA);
    }

    @Nullable
    public ArrayList<AbsResult> getResults() {
        return mResults;
    }

    @Nullable
    @SuppressWarnings("unchecked")
    private <T extends AbsResult> ArrayList<T> getResults(final AbsResult.Type type) {
        if (!hasResults()) {
            return null;
        }

        final ArrayList<T> list = new ArrayList<>();

        for (final AbsResult result : mResults) {
            if (result.getType() == type) {
                list.add((T) result);
            }
        }

        if (list.isEmpty()) {
            return null;
        } else {
            return list;
        }
    }

    @Nullable
    public ArrayList<UserResult> getUserResults() {
        return getResults(AbsResult.Type.USER);
    }

    public boolean hasResults() {
        return mResults != null && !mResults.isEmpty();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        ParcelableUtils.writeSearchBundleAbsResultList(mResults, dest, flags);
    }

    public static final Creator<SearchBundle> CREATOR = new Creator<SearchBundle>() {
        @Override
        public SearchBundle createFromParcel(final Parcel source) {
            final SearchBundle sb = new SearchBundle();
            sb.mResults = ParcelableUtils.readSearchBundleAbsResultList(source);
            return sb;
        }

        @Override
        public SearchBundle[] newArray(final int size) {
            return new SearchBundle[size];
        }
    };


    public static abstract class AbsResult implements Parcelable {
        @SerializedName("badges")
        private ArrayList<Badge> mBadges;

        @SerializedName("rank")
        private float mRank;

        @Nullable
        @SerializedName("desc")
        private String mDescription;

        @SerializedName("image")
        private String mImage;

        @SerializedName("link")
        private String mLink;

        @SerializedName("title")
        private String mTitle;


        public ArrayList<Badge> getBadges() {
            return mBadges;
        }

        @Nullable
        public String getDescription() {
            return mDescription;
        }

        @Nullable
        public String getImage() {
            return mImage;
        }

        public String getLink() {
            return mLink;
        }

        public float getRank() {
            return mRank;
        }

        public String getTitle() {
            return mTitle;
        }

        public abstract Type getType();

        public boolean hasDescription() {
            return !TextUtils.isEmpty(mDescription);
        }

        @Override
        public String toString() {
            return getType().toString() + ':' + getTitle();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        protected void readFromParcel(final Parcel source) {
            mBadges = source.createTypedArrayList(Badge.CREATOR);
            mRank = source.readFloat();
            mDescription = source.readString();
            mImage = source.readString();
            mLink = source.readString();
            mTitle = source.readString();
        }

        @Override
        public void writeToParcel(final Parcel dest, final int flags) {
            dest.writeTypedList(mBadges);
            dest.writeFloat(mRank);
            dest.writeString(mDescription);
            dest.writeString(mImage);
            dest.writeString(mLink);
            dest.writeString(mTitle);
        }

        public static class Badge implements Parcelable {
            @SerializedName("class")
            private Clazz mClazz;

            @SerializedName("content")
            private String mContent;

            public Clazz getClazz() {
                return mClazz;
            }

            public String getContent() {
                return mContent;
            }

            @Override
            public String toString() {
                return mClazz.toString();
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(final Parcel dest, final int flags) {
                dest.writeParcelable(mClazz, flags);
                dest.writeString(mContent);
            }

            public static final Creator<Badge> CREATOR = new Creator<Badge>() {
                @Override
                public Badge createFromParcel(final Parcel source) {
                    final Badge b = new Badge();
                    b.mClazz = source.readParcelable(Clazz.class.getClassLoader());
                    b.mContent = source.readString();
                    return b;
                }

                @Override
                public Badge[] newArray(final int size) {
                    return new Badge[size];
                }
            };

            public enum Clazz implements Parcelable {
                @SerializedName("anime")
                ANIME,

                @SerializedName("episodes")
                EPISODES,

                @SerializedName("group")
                GROUP,

                @SerializedName("manga")
                MANGA,

                @SerializedName("user")
                USER;

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(final Parcel dest, final int flags) {
                    dest.writeInt(ordinal());
                }

                public static final Creator<Clazz> CREATOR = new Creator<Clazz>() {
                    @Override
                    public Clazz createFromParcel(final Parcel source) {
                        final int ordinal = source.readInt();
                        return values()[ordinal];
                    }

                    @Override
                    public Clazz[] newArray(final int size) {
                        return new Clazz[size];
                    }
                };
            }
        }

        public enum Type implements Parcelable {
            @SerializedName("anime")
            ANIME(R.string.anime),

            @SerializedName("group")
            GROUP(R.string.groups),

            @SerializedName("manga")
            MANGA(R.string.manga),

            @SerializedName("user")
            USER(R.string.users);

            @StringRes
            private final int mTextResId;

            public static Type from(final String type) {
                switch (type) {
                    case "anime":
                        return ANIME;

                    case "group":
                        return GROUP;

                    case "manga":
                        return MANGA;

                    case "user":
                        return USER;

                    default:
                        throw new IllegalArgumentException("encountered unknown " +
                                Type.class.getName() + ": \"" + type + '"');
                }
            }

            Type(@StringRes final int textResId) {
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

        public static final JsonDeserializer<AbsResult> JSON_DESERIALIZER = new JsonDeserializer<AbsResult>() {
            @Override
            public AbsResult deserialize(final JsonElement json,
                    final java.lang.reflect.Type typeOfT, final JsonDeserializationContext context)
                    throws JsonParseException {
                final JsonObject jsonObject = json.getAsJsonObject();
                final Type type = Type.from(jsonObject.get("type").getAsString());

                final AbsResult result;

                switch (type) {
                    case ANIME:
                        result = context.deserialize(json, AnimeResult.class);
                        break;

                    case GROUP:
                        result = context.deserialize(json, GroupResult.class);
                        break;

                    case MANGA:
                        result = context.deserialize(json, MangaResult.class);
                        break;

                    case USER:
                        result = context.deserialize(json, UserResult.class);
                        break;

                    default:
                        throw new RuntimeException("encountered unknown " + Type.class.getName()
                                + ": \"" + type + '"');
                }

                return result;
            }
        };
    }


    public static class AnimeResult extends AbsResult implements Parcelable {
        @Override
        public Type getType() {
            return Type.ANIME;
        }

        public static final Creator<AnimeResult> CREATOR = new Creator<AnimeResult>() {
            @Override
            public AnimeResult createFromParcel(final Parcel source) {
                final AnimeResult ar = new AnimeResult();
                ar.readFromParcel(source);
                return ar;
            }

            @Override
            public AnimeResult[] newArray(final int size) {
                return new AnimeResult[size];
            }
        };
    }


    public static class GroupResult extends AbsResult implements Parcelable {
        private String[] mImages;


        @Nullable
        public String[] getImages() {
            if (mImages == null && getImage() != null) {
                mImages = MiscUtils.getGroupLogos(getImage());
            }

            return mImages;
        }

        @Override
        public Type getType() {
            return Type.GROUP;
        }

        @Override
        protected void readFromParcel(final Parcel source) {
            super.readFromParcel(source);
            mImages = source.createStringArray();
        }

        @Override
        public void writeToParcel(final Parcel dest, final int flags) {
            super.writeToParcel(dest, flags);
            dest.writeStringArray(mImages);
        }

        public static final Creator<GroupResult> CREATOR = new Creator<GroupResult>() {
            @Override
            public GroupResult createFromParcel(final Parcel source) {
                final GroupResult gr = new GroupResult();
                gr.readFromParcel(source);
                return gr;
            }

            @Override
            public GroupResult[] newArray(final int size) {
                return new GroupResult[size];
            }
        };
    }


    public static class MangaResult extends AbsResult implements Parcelable {
        @Override
        public Type getType() {
            return Type.MANGA;
        }

        public static final Creator<MangaResult> CREATOR = new Creator<MangaResult>() {
            @Override
            public MangaResult createFromParcel(final Parcel source) {
                final MangaResult mr = new MangaResult();
                mr.readFromParcel(source);
                return mr;
            }

            @Override
            public MangaResult[] newArray(final int size) {
                return new MangaResult[size];
            }
        };
    }


    public static class UserResult extends AbsResult implements Parcelable {
        private String[] mImages;


        @Nullable
        public String[] getImages() {
            if (mImages == null && getImage() != null) {
                mImages = MiscUtils.getUserAvatars(getImage());
            }

            return mImages;
        }

        @Override
        public Type getType() {
            return Type.USER;
        }

        @Override
        protected void readFromParcel(final Parcel source) {
            super.readFromParcel(source);
            mImages = source.createStringArray();
        }

        @Override
        public void writeToParcel(final Parcel dest, final int flags) {
            super.writeToParcel(dest, flags);
            dest.writeStringArray(mImages);
        }

        public static final Creator<UserResult> CREATOR = new Creator<UserResult>() {
            @Override
            public UserResult createFromParcel(final Parcel source) {
                final UserResult ur = new UserResult();
                ur.readFromParcel(source);
                return ur;
            }

            @Override
            public UserResult[] newArray(final int size) {
                return new UserResult[size];
            }
        };
    }

}
