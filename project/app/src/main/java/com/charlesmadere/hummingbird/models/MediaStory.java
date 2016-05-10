package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.charlesmadere.hummingbird.misc.ParcelableUtils;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.SerializedName;

public class MediaStory extends AbsStory implements Parcelable {

    @SerializedName("media")
    private AbsMedia mMedia;


    public AbsMedia getMedia() {
        return mMedia;
    }

    @Override
    public Type getType() {
        return Type.MEDIA_STORY;
    }

    @Override
    public void hydrate(final Feed feed) {
        super.hydrate(feed);
        mMedia.hydrate(feed);
    }

    @Override
    public String toString() {
        return getType().toString() + ':' + mMedia.toString();
    }

    @Override
    protected void readFromParcel(final Parcel source) {
        super.readFromParcel(source);
        mMedia = ParcelableUtils.readMediaStoryAbsMediaFromParcel(source);
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        super.writeToParcel(dest, flags);
        ParcelableUtils.writeMediaStoryAbsMediaToParcel(mMedia, dest, flags);
    }

    public static final Creator<MediaStory> CREATOR = new Creator<MediaStory>() {
        @Override
        public MediaStory createFromParcel(final Parcel source) {
            final MediaStory ms = new MediaStory();
            ms.readFromParcel(source);
            return ms;
        }

        @Override
        public MediaStory[] newArray(final int size) {
            return new MediaStory[size];
        }
    };


    public static abstract class AbsMedia implements Parcelable {
        @SerializedName("id")
        private String mId;

        public String getId() {
            return mId;
        }

        public abstract Type getType();

        public abstract void hydrate(final Feed feed);

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
                    final int ordinal = source.readInt();
                    return values()[ordinal];
                }

                @Override
                public Type[] newArray(final int size) {
                    return new Type[size];
                }
            };
        }

        public static final JsonDeserializer<AbsMedia> JSON_DESERIALIZER = new JsonDeserializer<AbsMedia>() {
            @Override
            public AbsMedia deserialize(final JsonElement json, final java.lang.reflect.Type typeOfT,
                    final JsonDeserializationContext context) throws JsonParseException {
                final JsonObject jsonObject = json.getAsJsonObject();
                final Type type = Type.from(jsonObject.get("type").getAsString());

                final AbsMedia media;

                switch (type) {
                    case ANIME:
                        media = context.deserialize(json, AnimeMedia.class);
                        break;

                    case MANGA:
                        media = context.deserialize(json, MangaMedia.class);
                        break;

                    default:
                        throw new RuntimeException("encountered unknown " + Type.class.getName()
                                + ": \"" + type + '"');
                }

                return media;
            }
        };
    }


    public static class AnimeMedia extends AbsMedia implements Parcelable {
        private AbsAnime mAnime;

        public AbsAnime getAnime() {
            return mAnime;
        }

        @Override
        public Type getType() {
            return Type.ANIME;
        }

        @Override
        public void hydrate(final Feed feed) {
            final String mediaId = getId();

            for (final AbsAnime anime : feed.getAnime()) {
                if (mediaId.equalsIgnoreCase(anime.getId())) {
                    mAnime = anime;
                    break;
                }
            }
        }

        @Override
        public String toString() {
            return getType().toString() + ':' + mAnime.getTitle();
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

        public static final Creator<AnimeMedia> CREATOR = new Creator<AnimeMedia>() {
            @Override
            public AnimeMedia createFromParcel(final Parcel source) {
                final AnimeMedia am = new AnimeMedia();
                am.readFromParcel(source);
                return am;
            }

            @Override
            public AnimeMedia[] newArray(final int size) {
                return new AnimeMedia[size];
            }
        };
    }


    public static class MangaMedia extends AbsMedia implements Parcelable {
        @Override
        public Type getType() {
            return Type.MANGA;
        }

        @Override
        public void hydrate(final Feed feed) {
            // TODO
        }

        @Override
        public String toString() {
            return getType().toString();
        }

        public static final Creator<MangaMedia> CREATOR = new Creator<MangaMedia>() {
            @Override
            public MangaMedia createFromParcel(final Parcel source) {
                final MangaMedia mm = new MangaMedia();
                mm.readFromParcel(source);
                return mm;
            }

            @Override
            public MangaMedia[] newArray(final int size) {
                return new MangaMedia[size];
            }
        };
    }

}
