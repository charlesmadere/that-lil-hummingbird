package com.charlesmadere.hummingbird.models;

import android.content.res.Resources;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.misc.MiscUtils;
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

    @Nullable
    @SerializedName("manga_library_entry_id")
    private String mLibraryEntryId;

    @Nullable
    @SerializedName("poster_image")
    private String mPosterImage;

    @Nullable
    @SerializedName("poster_image_thumb")
    private String mPosterImageThumb;

    @SerializedName("romaji_title")
    private String mRomajiTitle;

    @Nullable
    @SerializedName("synopsis")
    private String mSynopsis;


    @Override
    public boolean equals(final Object o) {
        return o instanceof Manga && mId.equalsIgnoreCase(((Manga) o).getId());
    }

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

    @Nullable
    public String getLibraryEntryId() {
        return mLibraryEntryId;
    }

    @Nullable
    public String getPosterImage() {
        return mPosterImage;
    }

    @Nullable
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
        return MiscUtils.isValidArtwork(mCoverImage);
    }

    public boolean hasCoverImageTopOffset() {
        return mCoverImageTopOffset != null && mCoverImageTopOffset >= 1;
    }

    @Override
    public int hashCode() {
        return mId.hashCode();
    }

    public boolean hasGenres() {
        return mGenres != null && !mGenres.isEmpty();
    }

    public boolean hasLibraryEntryId() {
        return !TextUtils.isEmpty(mLibraryEntryId);
    }

    public boolean hasPosterImage() {
        return MiscUtils.isValidArtwork(mPosterImage);
    }

    public boolean hasPosterImageThumb() {
        return MiscUtils.isValidArtwork(mPosterImageThumb);
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
        dest.writeParcelable(mMangaType, flags);
        dest.writeParcelable(mUpdatedAt, flags);
        dest.writeString(mCoverImage);
        dest.writeString(mId);
        dest.writeString(mLibraryEntryId);
        dest.writeString(mPosterImage);
        dest.writeString(mPosterImageThumb);
        dest.writeString(mRomajiTitle);
        dest.writeString(mSynopsis);
    }

    public static final Creator<Manga> CREATOR = new Creator<Manga>() {
        @Override
        public Manga createFromParcel(final Parcel source) {
            final Manga m = new Manga();
            m.mGenres = source.createStringArrayList();
            m.mChapterCount = ParcelableUtils.readInteger(source);
            m.mCoverImageTopOffset = ParcelableUtils.readInteger(source);
            m.mVolumeCount = ParcelableUtils.readInteger(source);
            m.mMangaType = source.readParcelable(MangaType.class.getClassLoader());
            m.mUpdatedAt = source.readParcelable(SimpleDate.class.getClassLoader());
            m.mCoverImage = source.readString();
            m.mId = source.readString();
            m.mLibraryEntryId = source.readString();
            m.mPosterImage = source.readString();
            m.mPosterImageThumb = source.readString();
            m.mRomajiTitle = source.readString();
            m.mSynopsis = source.readString();
            return m;
        }

        @Override
        public Manga[] newArray(final int size) {
            return new Manga[size];
        }
    };

}
