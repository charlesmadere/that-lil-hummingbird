package com.charlesmadere.hummingbird.models;

import android.content.res.Resources;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.charlesmadere.hummingbird.R;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AnimeV2 extends AbsAnime implements Parcelable {

    @Nullable
    @SerializedName("genres")
    private ArrayList<String> mGenres;

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
        return mTitles.getCanonical();
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

    @Override
    protected void readFromParcel(final Parcel source) {
        super.readFromParcel(source);
        mGenres = source.createStringArrayList();
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

        @SerializedName("english")
        private String mEnglish;

        @SerializedName("japanese")
        private String mJapanese;

        @SerializedName("romaji")
        private String mRomaji;


        public String getCanonical() {
            return mCanonical;
        }

        public String getEnglish() {
            return mEnglish;
        }

        public String getJapanese() {
            return mJapanese;
        }

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
    }

}
