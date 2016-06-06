package com.charlesmadere.hummingbird.models;

import android.content.res.Resources;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.misc.ParcelableUtils;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Manga implements Parcelable {

    @Nullable
    @SerializedName("genres")
    private ArrayList<String> mGenres;

    @Nullable
    @SerializedName("chapter_count")
    private Integer mChapterCount;

    @Nullable
    @SerializedName("cover_image_top_offset")
    private Integer mCoverImageTopOffset;

    @Nullable
    @SerializedName("volume_count")
    private Integer mVolumeCount;

    @Nullable
    @SerializedName("manga_type")
    private MangaType mMangaType;

    @SerializedName("updated_at")
    private SimpleDate mUpdatedAt;

    @Nullable
    @SerializedName("cover_image")
    private String mCoverImage;

    @SerializedName("id")
    private String mId;

    @SerializedName("poster_image")
    private String mPosterImage;

    @SerializedName("poster_image_thumb")
    private String mPosterImageThumb;

    @SerializedName("romaji_title")
    private String mRomajiTitle;

    @Nullable
    @SerializedName("synopsis")
    private String mSynopsis;


    @Nullable
    public String getCoverImage() {
        return mCoverImage;
    }

    @Nullable
    public Integer getCoverImageTopOffset() {
        return mCoverImageTopOffset;
    }

    @Nullable
    public Integer getChapterCount() {
        return mChapterCount;
    }

    @Nullable
    public ArrayList<String> getGenres() {
        return mGenres;
    }

    public String getGenresString(final Resources res) {
        if (!hasGenres()) {
            return "";
        }

        return TextUtils.join(res.getText(R.string.delimiter), mGenres);
    }

    public String getId() {
        return mId;
    }

    public String getPosterImage() {
        return mPosterImage;
    }

    public String getPosterImageThumb() {
        return mPosterImageThumb;
    }

    public String getRomajiTitle() {
        return mRomajiTitle;
    }

    @Nullable
    public String getSynopsis() {
        return mSynopsis;
    }

    public String getTitle() {
        return getRomajiTitle();
    }

    @Nullable
    public MangaType getType() {
        return mMangaType;
    }

    public SimpleDate getUpdatedAt() {
        return mUpdatedAt;
    }

    @Nullable
    public Integer getVolumeCount() {
        return mVolumeCount;
    }

    public boolean hasChapterCount() {
        return mChapterCount != null && mChapterCount >= 1;
    }

    public boolean hasCoverImage() {
        return !TextUtils.isEmpty(mCoverImage);
    }

    public boolean hasCoverImageTopOffset() {
        return mCoverImageTopOffset != null && mCoverImageTopOffset >= 1;
    }

    public boolean hasGenres() {
        return mGenres != null && !mGenres.isEmpty();
    }

    public boolean hasSynopsis() {
        return !TextUtils.isEmpty(mSynopsis);
    }

    public boolean hasType() {
        return mMangaType != null;
    }

    public boolean hasVolumeCount() {
        return mVolumeCount != null && mVolumeCount >= 1;
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
        dest.writeStringList(mGenres);
        ParcelableUtils.writeInteger(mChapterCount, dest);
        ParcelableUtils.writeInteger(mCoverImageTopOffset, dest);
        ParcelableUtils.writeInteger(mVolumeCount, dest);
        dest.writeParcelable(mUpdatedAt, flags);
        dest.writeString(mCoverImage);
        dest.writeString(mId);
        dest.writeString(mPosterImage);
        dest.writeString(mPosterImageThumb);
        dest.writeString(mRomajiTitle);
        dest.writeString(mSynopsis);
        dest.writeParcelable(mMangaType, flags);
    }

    public static final Creator<Manga> CREATOR = new Creator<Manga>() {
        @Override
        public Manga createFromParcel(final Parcel source) {
            final Manga m = new Manga();
            m.mGenres = source.createStringArrayList();
            m.mChapterCount = ParcelableUtils.readInteger(source);
            m.mCoverImageTopOffset = ParcelableUtils.readInteger(source);
            m.mVolumeCount = ParcelableUtils.readInteger(source);
            m.mUpdatedAt = source.readParcelable(SimpleDate.class.getClassLoader());
            m.mCoverImage = source.readString();
            m.mId = source.readString();
            m.mPosterImage = source.readString();
            m.mPosterImageThumb = source.readString();
            m.mRomajiTitle = source.readString();
            m.mSynopsis = source.readString();
            m.mMangaType = source.readParcelable(MangaType.class.getClassLoader());
            return m;
        }

        @Override
        public Manga[] newArray(final int size) {
            return new Manga[size];
        }
    };

}
