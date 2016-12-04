package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.charlesmadere.hummingbird.preferences.Preferences;
import com.google.gson.annotations.SerializedName;

public class AnimeV3 implements DataObject, Parcelable {

    @SerializedName("attributes")
    private Attributes mAttributes;

    @SerializedName("type")
    private DataType mDataType;

    @SerializedName("links")
    private Links mLinks;

    @Nullable
    @SerializedName("relationships")
    private Relationships mRelationships;

    @SerializedName("id")
    private String mId;


    @Override
    public boolean equals(final Object o) {
        return o instanceof AnimeV3 && mId.equals(((AnimeV3) o).getId());
    }

    @Nullable
    public String getAgeRating() {
        return mAttributes.mAgeRating;
    }

    @Nullable
    public String getAgeRatingGuide() {
        return mAttributes.mAgeRatingGuide;
    }

    @Nullable
    public Float getAverageRating() {
        return mAttributes.mAverageRating;
    }

    public String getCanonicalTitle() {
        return mAttributes.mCanonicalTitle;
    }

    @Nullable
    public Image getCoverImage() {
        return mAttributes.mCoverImage;
    }

    @Nullable
    public Integer getCoverImageTopOffset() {
        return mAttributes.mCoverImageTopOffset;
    }

    @Override
    public DataType getDataType() {
        return mDataType;
    }

    @Nullable
    public SimpleDate getEndDate() {
        return mAttributes.mEndDate;
    }

    @Nullable
    public Integer getEpisodeCount() {
        return mAttributes.mEpisodeCount;
    }

    @Nullable
    public Integer getEpisodeLength() {
        return mAttributes.mEpisodeLength;
    }

    @Override
    public String getId() {
        return mId;
    }

    public Links getLinks() {
        return mLinks;
    }

    @Nullable
    public Image getPosterImage() {
        return mAttributes.mPosterImage;
    }

    @Nullable
    public Relationships getRelationships() {
        return mRelationships;
    }

    public AnimeType getShowType() {
        return mAttributes.mShowType;
    }

    public String getSlug() {
        return mAttributes.mSlug;
    }

    @Nullable
    public SimpleDate getStartDate() {
        return mAttributes.mStartDate;
    }

    @Nullable
    public String getSynopsis() {
        return mAttributes.mSynopsis;
    }

    public String getTitle() {
        final TitleType titleType = Preferences.General.TitleLanguage.get();

        if (titleType == null || titleType == TitleType.CANONICAL) {
            return getCanonicalTitle();
        }

        String title = null;
        final Titles titles = getTitles();

        switch (titleType) {
            case ENGLISH:
                title = titles.getEn();
                break;

            case JAPANESE:
                title = titles.getJaJp();

                if (TextUtils.isEmpty(title)) {
                    title = titles.getEnJp();
                }
                break;
        }

        if (TextUtils.isEmpty(title)) {
            return getCanonicalTitle();
        } else {
            return title;
        }
    }

    public Titles getTitles() {
        return mAttributes.mTitles;
    }

    @Override
    public int hashCode() {
        return mId.hashCode();
    }

    public boolean isNsfw() {
        return mAttributes.mNsfw;
    }

    @Override
    public String toString() {
        return getTitle();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeParcelable(mAttributes, flags);
        dest.writeParcelable(mDataType, flags);
        dest.writeParcelable(mLinks, flags);
        dest.writeParcelable(mRelationships, flags);
        dest.writeString(mId);
    }

    public static final Creator<AnimeV3> CREATOR = new Creator<AnimeV3>() {
        @Override
        public AnimeV3 createFromParcel(final Parcel source) {
            final AnimeV3 a = new AnimeV3();
            a.mAttributes = source.readParcelable(Attributes.class.getClassLoader());
            a.mDataType = source.readParcelable(DataType.class.getClassLoader());
            a.mLinks = source.readParcelable(Links.class.getClassLoader());
            a.mRelationships = source.readParcelable(Relationships.class.getClassLoader());
            a.mId = source.readString();
            return a;
        }

        @Override
        public AnimeV3[] newArray(final int size) {
            return new AnimeV3[size];
        }
    };


    private static class Attributes implements Parcelable {
        @Nullable
        @SerializedName("showType")
        private AnimeType mShowType;

        @SerializedName("nsfw")
        private boolean mNsfw;

        @Nullable
        @SerializedName("averageRating")
        private Float mAverageRating;

        @Nullable
        @SerializedName("coverImage")
        private Image mCoverImage;

        @Nullable
        @SerializedName("posterImage")
        private Image mPosterImage;

        @Nullable
        @SerializedName("coverImageTopOffset")
        private Integer mCoverImageTopOffset;

        @Nullable
        @SerializedName("episodeCount")
        private Integer mEpisodeCount;

        @Nullable
        @SerializedName("episodeLength")
        private Integer mEpisodeLength;

        @Nullable
        @SerializedName("endDate")
        private SimpleDate mEndDate;

        @Nullable
        @SerializedName("startDate")
        private SimpleDate mStartDate;

        @Nullable
        @SerializedName("ageRating")
        private String mAgeRating;

        @Nullable
        @SerializedName("ageRatingGuide")
        private String mAgeRatingGuide;

        @SerializedName("canonicalTitle")
        private String mCanonicalTitle;

        @SerializedName("slug")
        private String mSlug;

        @Nullable
        @SerializedName("synopsis")
        private String mSynopsis;

        @Nullable
        @SerializedName("youtubeVideoId")
        private String mYoutubeVideoId;

        @SerializedName("titles")
        private Titles mTitles;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(final Parcel dest, final int flags) {
            dest.writeParcelable(mShowType, flags);
            dest.writeInt(mNsfw ? 1 : 0);
            dest.writeValue(mAverageRating);
            dest.writeParcelable(mCoverImage, flags);
            dest.writeParcelable(mPosterImage, flags);
            dest.writeValue(mCoverImageTopOffset);
            dest.writeValue(mEpisodeCount);
            dest.writeValue(mEpisodeLength);
            dest.writeParcelable(mEndDate, flags);
            dest.writeParcelable(mStartDate, flags);
            dest.writeString(mAgeRating);
            dest.writeString(mAgeRatingGuide);
            dest.writeString(mCanonicalTitle);
            dest.writeString(mSlug);
            dest.writeString(mSynopsis);
            dest.writeString(mYoutubeVideoId);
            dest.writeParcelable(mTitles, flags);
        }

        public static final Creator<Attributes> CREATOR = new Creator<Attributes>() {
            @Override
            public Attributes createFromParcel(final Parcel source) {
                final Attributes a = new Attributes();
                a.mShowType = source.readParcelable(AnimeType.class.getClassLoader());
                a.mNsfw = source.readInt() != 0;
                a.mAverageRating = (Float) source.readValue(Float.class.getClassLoader());
                a.mCoverImage = source.readParcelable(Image.class.getClassLoader());
                a.mPosterImage = source.readParcelable(Image.class.getClassLoader());
                a.mCoverImageTopOffset = (Integer) source.readValue(Integer.class.getClassLoader());
                a.mEpisodeCount = (Integer) source.readValue(Integer.class.getClassLoader());
                a.mEpisodeLength = (Integer) source.readValue(Integer.class.getClassLoader());
                a.mEndDate = source.readParcelable(SimpleDate.class.getClassLoader());
                a.mStartDate = source.readParcelable(SimpleDate.class.getClassLoader());
                a.mAgeRating = source.readString();
                a.mAgeRatingGuide = source.readString();
                a.mCanonicalTitle = source.readString();
                a.mSlug = source.readString();
                a.mSynopsis = source.readString();
                a.mYoutubeVideoId = source.readString();
                a.mTitles = source.readParcelable(Titles.class.getClassLoader());
                return a;
            }

            @Override
            public Attributes[] newArray(final int size) {
                return new Attributes[size];
            }
        };
    }

}
