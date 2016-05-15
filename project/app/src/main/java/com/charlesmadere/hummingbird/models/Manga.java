package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.charlesmadere.hummingbird.misc.ParcelableUtils;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Manga implements Parcelable {

    @Nullable
    @SerializedName("genres")
    private ArrayList<String> mGenres;

    @SerializedName("cover_image_top_offset")
    private int mCoverImageTopOffset;

    @Nullable
    @SerializedName("chapter_count")
    private Integer mChapterCount;

    @Nullable
    @SerializedName("volume_count")
    private Integer mVolumeCount;

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

    public int getCoverImageTopOffset() {
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

    public String getId() {
        return mId;
    }

    public MangaType getMangaType() {
        return mMangaType;
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

    public SimpleDate getUpdatedAt() {
        return mUpdatedAt;
    }

    @Nullable
    public Integer getVolumeCount() {
        return mVolumeCount;
    }

    public boolean hasChapterCount() {
        return mChapterCount != null;
    }

    public boolean hasCoverImage() {
        return !TextUtils.isEmpty(mCoverImage);
    }

    public boolean hasGenres() {
        return mGenres != null && !mGenres.isEmpty();
    }

    public boolean hasSynopsis() {
        return !TextUtils.isEmpty(mSynopsis);
    }

    public boolean hasVolumeCount() {
        return mVolumeCount != null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    protected void readFromParcel(final Parcel source) {
        mGenres = source.createStringArrayList();
        mCoverImageTopOffset = source.readInt();
        mChapterCount = ParcelableUtils.readInteger(source);
        mVolumeCount = ParcelableUtils.readInteger(source);
        mUpdatedAt = source.readParcelable(SimpleDate.class.getClassLoader());
        mCoverImage = source.readString();
        mId = source.readString();
        mPosterImage = source.readString();
        mPosterImageThumb = source.readString();
        mRomajiTitle = source.readString();
        mSynopsis = source.readString();
        mMangaType = source.readParcelable(MangaType.class.getClassLoader());
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeStringList(mGenres);
        dest.writeInt(mCoverImageTopOffset);
        ParcelableUtils.writeInteger(mChapterCount, dest);
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
            m.readFromParcel(source);
            return m;
        }

        @Override
        public Manga[] newArray(final int size) {
            return new Manga[size];
        }
    };

}
