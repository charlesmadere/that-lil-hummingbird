package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.charlesmadere.hummingbird.preferences.Preferences;
import com.google.gson.annotations.SerializedName;

public class MangaV3 implements DataObject, Parcelable {

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
        return o instanceof MangaV3 && mId.equals(((MangaV3) o).getId());
    }

    @Nullable
    public Float getAverageRating() {
        return mAttributes.mAverageRating;
    }

    public String getCanonicalTitle() {
        return mAttributes.mCanonicalTitle;
    }

    @Nullable
    public Integer getChapterCount() {
        return mAttributes.mChapterCount;
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

    @Override
    public String getId() {
        return mId;
    }

    public Links getLinks() {
        return mLinks;
    }

    @Nullable
    public Relationships getRelationships() {
        return mRelationships;
    }

    @Nullable
    public String getSerialization() {
        return mAttributes.mSerialization;
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

    @Nullable
    public Integer getVolumeCount() {
        return mAttributes.mVolumeCount;
    }

    @Override
    public int hashCode() {
        return mId.hashCode();
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

    public static final Creator<MangaV3> CREATOR = new Creator<MangaV3>() {
        @Override
        public MangaV3 createFromParcel(final Parcel source) {
            final MangaV3 m = new MangaV3();
            m.mAttributes = source.readParcelable(Attributes.class.getClassLoader());
            m.mDataType = source.readParcelable(DataType.class.getClassLoader());
            m.mLinks = source.readParcelable(Links.class.getClassLoader());
            m.mRelationships = source.readParcelable(Relationships.class.getClassLoader());
            m.mId = source.readString();
            return m;
        }

        @Override
        public MangaV3[] newArray(final int size) {
            return new MangaV3[size];
        }
    };


    private static class Attributes implements Parcelable {
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
        @SerializedName("chapterCount")
        private Integer mChapterCount;

        @Nullable
        @SerializedName("coverImageTopOffset")
        private Integer mCoverImageTopOffset;

        @Nullable
        @SerializedName("volumeCount")
        private Integer mVolumeCount;

        @Nullable
        @SerializedName("mangaType")
        private MangaType mMangaType;

        @Nullable
        @SerializedName("endDate")
        private SimpleDate mEndDate;

        @Nullable
        @SerializedName("startDate")
        private SimpleDate mStartDate;

        @SerializedName("canonicalTitle")
        private String mCanonicalTitle;

        @Nullable
        @SerializedName("serialization")
        private String mSerialization;

        @SerializedName("slug")
        private String mSlug;

        @Nullable
        @SerializedName("synopsis")
        private String mSynopsis;

        @SerializedName("titles")
        private Titles mTitles;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(final Parcel dest, final int flags) {
            dest.writeValue(mAverageRating);
            dest.writeParcelable(mCoverImage, flags);
            dest.writeParcelable(mPosterImage, flags);
            dest.writeValue(mChapterCount);
            dest.writeValue(mCoverImageTopOffset);
            dest.writeValue(mVolumeCount);
            dest.writeParcelable(mMangaType, flags);
            dest.writeParcelable(mEndDate, flags);
            dest.writeParcelable(mStartDate, flags);
            dest.writeString(mCanonicalTitle);
            dest.writeString(mSerialization);
            dest.writeString(mSlug);
            dest.writeString(mSynopsis);
            dest.writeParcelable(mTitles, flags);
        }

        public static final Creator<Attributes> CREATOR = new Creator<Attributes>() {
            @Override
            public Attributes createFromParcel(final Parcel source) {
                final Attributes a = new Attributes();
                a.mAverageRating = (Float) source.readValue(Float.class.getClassLoader());
                a.mCoverImage = source.readParcelable(Image.class.getClassLoader());
                a.mPosterImage = source.readParcelable(Image.class.getClassLoader());
                a.mChapterCount = (Integer) source.readValue(Integer.class.getClassLoader());
                a.mCoverImageTopOffset = (Integer) source.readValue(Integer.class.getClassLoader());
                a.mVolumeCount = (Integer) source.readValue(Integer.class.getClassLoader());
                a.mMangaType = source.readParcelable(MangaType.class.getClassLoader());
                a.mEndDate = source.readParcelable(SimpleDate.class.getClassLoader());
                a.mStartDate = source.readParcelable(SimpleDate.class.getClassLoader());
                a.mCanonicalTitle = source.readString();
                a.mSerialization = source.readString();
                a.mSynopsis = source.readString();
                a.mSlug = source.readString();
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
