package com.charlesmadere.hummingbird.models;

import android.content.res.Resources;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.text.TextUtils;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.misc.GsonUtils;
import com.charlesmadere.hummingbird.preferences.Preferences;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class AnimeV2 extends AbsAnime implements Parcelable {

    @Nullable
    @SerializedName("genres")
    private ArrayList<String> mGenres;

    @Nullable
    @SerializedName("producers")
    private ArrayList<String> mProducers;

    @SerializedName("bayesian_rating")
    private float mBayesianRating;

    @SerializedName("links")
    private Links mLinks;

    @Nullable
    @SerializedName("finished_airing_date")
    private SimpleDate mFinishedAiringDate;

    @Nullable
    @SerializedName("started_airing_Date")
    private SimpleDate mStartedAiringDate;

    @SerializedName("poster_image")
    private String mPosterImage;

    @SerializedName("slug")
    private String mSlug;

    @Nullable
    @SerializedName("youtube_video_id")
    private String mYoutubeVideoId;

    @SerializedName("titles")
    private Titles mTitles;


    public float getBayesianRating() {
        return mBayesianRating;
    }

    @Nullable
    @Override
    public SimpleDate getFinishedAiringDate() {
        return mFinishedAiringDate;
    }

    @Nullable
    public ArrayList<String> getGenres() {
        return mGenres;
    }

    @Override
    public String getGenresString(final Resources res) {
        if (!hasGenres()) {
            return "";
        }

        return TextUtils.join(res.getText(R.string.delimiter), mGenres);
    }

    public Links getLinks() {
        return mLinks;
    }

    public String getPosterImage() {
        return mPosterImage;
    }

    @Nullable
    public ArrayList<String> getProducers() {
        return mProducers;
    }

    public String getSlug() {
        return mSlug;
    }

    @Nullable
    @Override
    public SimpleDate getStartedAiringDate() {
        return mStartedAiringDate;
    }

    @Override
    public String getTitle() {
        return mTitles.get(Preferences.General.TitleLanguage.get());
    }

    public Titles getTitles() {
        return mTitles;
    }

    @Override
    public Version getVersion() {
        return Version.V2;
    }

    @Nullable
    public String getYoutubeVideoId() {
        return mYoutubeVideoId;
    }

    @Override
    public boolean hasGenres() {
        return mGenres != null && !mGenres.isEmpty();
    }

    public boolean hasProducers() {
        return mProducers != null && !mProducers.isEmpty();
    }

    public boolean hasYoutubeVideoId() {
        return !TextUtils.isEmpty(mYoutubeVideoId);
    }

    @Override
    protected void readFromParcel(final Parcel source) {
        super.readFromParcel(source);
        mGenres = source.createStringArrayList();
        mProducers = source.createStringArrayList();
        mBayesianRating = source.readFloat();
        mLinks = source.readParcelable(Links.class.getClassLoader());
        mFinishedAiringDate = source.readParcelable(SimpleDate.class.getClassLoader());
        mStartedAiringDate = source.readParcelable(SimpleDate.class.getClassLoader());
        mPosterImage = source.readString();
        mSlug = source.readString();
        mYoutubeVideoId = source.readString();
        mTitles = source.readParcelable(Titles.class.getClassLoader());
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        super.writeToParcel(dest, flags);
        dest.writeStringList(mGenres);
        dest.writeStringList(mProducers);
        dest.writeFloat(mBayesianRating);
        dest.writeParcelable(mLinks, flags);
        dest.writeParcelable(mFinishedAiringDate, flags);
        dest.writeParcelable(mStartedAiringDate, flags);
        dest.writeString(mPosterImage);
        dest.writeString(mSlug);
        dest.writeString(mYoutubeVideoId);
        dest.writeParcelable(mTitles, flags);
    }

    public static final Creator<AnimeV2> CREATOR = new Creator<AnimeV2>() {
        @Override
        public AnimeV2 createFromParcel(final Parcel source) {
            final AnimeV2 a = new AnimeV2();
            a.readFromParcel(source);
            return a;
        }

        @Override
        public AnimeV2[] newArray(final int size) {
            return new AnimeV2[size];
        }
    };


    public static class Links implements Parcelable {
        @SerializedName("episodes")
        private ArrayList<AnimeEpisode> mAnimeEpisodes;

        @SerializedName("gallery_images")
        private ArrayList<GalleryImage> mGalleryImages;


        public ArrayList<AnimeEpisode> getAnimeEpisodes() {
            return mAnimeEpisodes;
        }

        public ArrayList<GalleryImage> getGalleryImages() {
            return mGalleryImages;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(final Parcel dest, final int flags) {
            dest.writeTypedList(mAnimeEpisodes);
            dest.writeTypedList(mGalleryImages);
        }

        public static final Creator<Links> CREATOR = new Creator<Links>() {
            @Override
            public Links createFromParcel(final Parcel source) {
                final Links l = new Links();
                l.mAnimeEpisodes = source.createTypedArrayList(AnimeEpisode.CREATOR);
                l.mGalleryImages = source.createTypedArrayList(GalleryImage.CREATOR);
                return l;
            }

            @Override
            public Links[] newArray(final int size) {
                return new Links[size];
            }
        };
    }


    public static class Titles implements Parcelable {
        @SerializedName("canonical")
        private String mCanonical;

        @Nullable
        @SerializedName("english")
        private String mEnglish;

        @Nullable
        @SerializedName("japanese")
        private String mJapanese;

        @Nullable
        @SerializedName("romaji")
        private String mRomaji;


        public String get(@Nullable final Type type) {
            if (type == null) {
                return getCanonical();
            }

            String title;

            switch (type) {
                case CANONICAL:
                    return getCanonical();

                case ENGLISH:
                    title = getEnglish();
                    break;

                case JAPANESE:
                    title = getJapanese();
                    break;

                case ROMAJI:
                    title = getRomaji();
                    break;

                default:
                    throw new RuntimeException("encountered illegal " + Type.class.getName()
                            + ": " + type);
            }

            if (TextUtils.isEmpty(title)) {
                title = getCanonical();
            }

            return title;
        }

        public String getCanonical() {
            return mCanonical;
        }

        @Nullable
        public String getEnglish() {
            return mEnglish;
        }

        @Nullable
        public String getJapanese() {
            return mJapanese;
        }

        @Nullable
        public String getRomaji() {
            return mRomaji;
        }

        @Override
        public String toString() {
            return getCanonical();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(final Parcel dest, final int flags) {
            dest.writeString(mCanonical);
            dest.writeString(mEnglish);
            dest.writeString(mJapanese);
            dest.writeString(mRomaji);
        }

        public static final Creator<Titles> CREATOR = new Creator<Titles>() {
            @Override
            public Titles createFromParcel(final Parcel source) {
                final Titles t = new Titles();
                t.mCanonical = source.readString();
                t.mEnglish = source.readString();
                t.mJapanese = source.readString();
                t.mRomaji = source.readString();
                return t;
            }

            @Override
            public Titles[] newArray(final int size) {
                return new Titles[size];
            }
        };

        public enum Type implements Parcelable {
            @SerializedName("canonical")
            CANONICAL(R.string.canonical),

            @SerializedName("english")
            ENGLISH(R.string.english),

            @SerializedName("japanese")
            JAPANESE(R.string.japanese),

            @SerializedName("romaji")
            ROMAJI(R.string.romaji);


            private final int mTitleResId;

            Type(@StringRes final int titleResId) {
                mTitleResId = titleResId;
            }

            public int getTitleResId() {
                return mTitleResId;
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
    }


    public static final JsonDeserializer<AnimeV2> JSON_DESERIALIZER = new JsonDeserializer<AnimeV2>() {
        @Override
        public AnimeV2 deserialize(final JsonElement json, final Type typeOfT,
                final JsonDeserializationContext context) throws JsonParseException {
            final JsonObject jsonObject = json.getAsJsonObject();
            final AnimeV2 anime = new AnimeV2();

            final JsonObject animeJson = jsonObject.get("anime").getAsJsonObject();

            // AbsAnime fields
            anime.mAgeRating = context.deserialize(animeJson.get("age_rating"), AgeRating.class);
            anime.mCommunityRating = animeJson.get("community_rating").getAsFloat();
            anime.mEpisodeCount = GsonUtils.getInteger(animeJson, "episode_count");
            anime.mEpisodeLength = animeJson.get("episode_length").getAsInt();
            anime.mShowType = context.deserialize(animeJson.get("show_type"), ShowType.class);
            anime.mCoverImage = animeJson.get("cover_image").getAsString();
            anime.mId = animeJson.get("id").getAsString();
            anime.mSynopsis = animeJson.get("synopsis").getAsString();

            // AnimeV2 fields
            anime.mGenres = GsonUtils.getStringArrayList(animeJson, "genres");
            anime.mProducers = GsonUtils.getStringArrayList(animeJson, "producers");
            anime.mBayesianRating = animeJson.get("bayesian_rating").getAsFloat();
            anime.mLinks = context.deserialize(jsonObject.get("linked"), Links.class);
            anime.mFinishedAiringDate = context.deserialize(animeJson.get("finished_airing_date"),
                    SimpleDate.class);
            anime.mStartedAiringDate = context.deserialize(animeJson.get("started_airing_date"),
                    SimpleDate.class);
            anime.mPosterImage = animeJson.get("poster_image").getAsString();
            anime.mSlug = animeJson.get("slug").getAsString();
            anime.mYoutubeVideoId = GsonUtils.getString(animeJson, "youtube_video_id");
            anime.mTitles = context.deserialize(animeJson.get("titles"), Titles.class);

            return anime;
        }
    };

}
