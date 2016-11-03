package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.util.Comparator;

public class MangaLibraryEntry implements Parcelable {

    @SerializedName("is_favorite")
    private boolean mIsFavorite;

    @SerializedName("private")
    private boolean mIsPrivate;

    @SerializedName("rereading")
    private boolean mIsReReading;

    @SerializedName("chapters_read")
    private int mChaptersRead;

    @SerializedName("reread_count")
    private int mReReadCount;

    @SerializedName("volumes_read")
    private int mVolumesRead;

    @Nullable
    @SerializedName("rating")
    private Rating mRating;

    @SerializedName("status")
    private ReadingStatus mStatus;

    @SerializedName("last_read")
    private SimpleDate mLastRead;

    @SerializedName("id")
    private String mId;

    @SerializedName("manga_id")
    private String mMangaId;

    @Nullable
    @SerializedName("notes")
    private String mNotes;

    // hydrated fields
    private Manga mManga;


    public boolean canBeIncremented() {
        return !mManga.hasChapterCount() || mChaptersRead < mManga.getChapterCount();
    }

    @Override
    public boolean equals(final Object o) {
        return o instanceof MangaLibraryEntry && mId.equalsIgnoreCase(((MangaLibraryEntry) o).getId());
    }

    public int getChaptersRead() {
        return mChaptersRead;
    }

    public String getId() {
        return mId;
    }

    public SimpleDate getLastRead() {
        return mLastRead;
    }

    public Manga getManga() {
        return mManga;
    }

    public String getMangaId() {
        return mMangaId;
    }

    @Nullable
    public String getNotes() {
        return mNotes;
    }

    @Nullable
    public Rating getRating() {
        return mRating;
    }

    public int getReReadCount() {
        return mReReadCount;
    }

    public ReadingStatus getStatus() {
        return mStatus;
    }

    public int getVolumesRead() {
        return mVolumesRead;
    }

    @Override
    public int hashCode() {
        return mId.hashCode();
    }

    public boolean hasNotes() {
        return !TextUtils.isEmpty(mNotes);
    }

    public boolean hasRating() {
        return mRating != null;
    }

    @WorkerThread
    public void hydrate(final Feed feed) {
        for (final Manga manga : feed.getManga()) {
            if (mMangaId.equalsIgnoreCase(manga.getId())) {
                mManga = manga;
                return;
            }
        }
    }

    @WorkerThread
    public boolean hydrate(final Manga manga) {
        if (mMangaId.equalsIgnoreCase(manga.getId())) {
            mManga = manga;
            return true;
        } else {
            return false;
        }
    }

    @WorkerThread
    public boolean hydrate(final MangaDigest digest) {
        if (!digest.hasManga()) {
            return false;
        }

        // noinspection ConstantConditions
        for (final Manga manga : digest.getManga()) {
            if (hydrate(manga)) {
                return true;
            }
        }

        return false;
    }

    public boolean isFavorite() {
        return mIsFavorite;
    }

    public boolean isPrivate() {
        return mIsPrivate;
    }

    public boolean isReReading() {
        return mIsReReading;
    }

    public void setManga(final Manga manga) {
        if (manga == null) {
            throw new IllegalArgumentException("manga parameter can't be null");
        } else if (!mMangaId.equalsIgnoreCase(manga.getId())) {
            throw new IllegalArgumentException("manga IDs don't match (" + mMangaId +
                    ") (" + manga.getId() + ')');
        }

        mManga = manga;
    }

    @Override
    public String toString() {
        return mManga.toString();
    }

    public void update(final MangaLibraryEntry libraryEntry) {
        mIsFavorite = libraryEntry.isFavorite();
        mIsPrivate = libraryEntry.isPrivate();
        mIsReReading = libraryEntry.isReReading();
        mChaptersRead = libraryEntry.getChaptersRead();
        mReReadCount = libraryEntry.getReReadCount();
        mVolumesRead = libraryEntry.getVolumesRead();
        mRating = libraryEntry.getRating();
        mStatus = libraryEntry.getStatus();
        mLastRead = libraryEntry.getLastRead();
        mNotes = libraryEntry.getNotes();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeInt(mIsFavorite ? 1 : 0);
        dest.writeInt(mIsPrivate ? 1 : 0);
        dest.writeInt(mIsReReading ? 1 : 0);
        dest.writeInt(mChaptersRead);
        dest.writeInt(mReReadCount);
        dest.writeInt(mVolumesRead);
        dest.writeParcelable(mRating, flags);
        dest.writeParcelable(mStatus, flags);
        dest.writeParcelable(mLastRead, flags);
        dest.writeString(mId);
        dest.writeString(mMangaId);
        dest.writeString(mNotes);
        dest.writeParcelable(mManga, flags);
    }

    public static final Creator<MangaLibraryEntry> CREATOR = new Creator<MangaLibraryEntry>() {
        @Override
        public MangaLibraryEntry createFromParcel(final Parcel source) {
            final MangaLibraryEntry mle = new MangaLibraryEntry();
            mle.mIsFavorite = source.readInt() != 0;
            mle.mIsPrivate = source.readInt() != 0;
            mle.mIsReReading = source.readInt() != 0;
            mle.mChaptersRead = source.readInt();
            mle.mReReadCount = source.readInt();
            mle.mVolumesRead = source.readInt();
            mle.mRating = source.readParcelable(Rating.class.getClassLoader());
            mle.mStatus = source.readParcelable(ReadingStatus.class.getClassLoader());
            mle.mLastRead = source.readParcelable(SimpleDate.class.getClassLoader());
            mle.mId = source.readString();
            mle.mMangaId = source.readString();
            mle.mNotes = source.readString();
            mle.mManga = source.readParcelable(Manga.class.getClassLoader());
            return mle;
        }

        @Override
        public MangaLibraryEntry[] newArray(final int size) {
            return new MangaLibraryEntry[size];
        }
    };

    public static final Comparator<MangaLibraryEntry> DATE = new Comparator<MangaLibraryEntry>() {
        @Override
        public int compare(final MangaLibraryEntry lhs, final MangaLibraryEntry rhs) {
            return SimpleDate.REVERSE_CHRONOLOGICAL_ORDER.compare(lhs.getLastRead(),
                    rhs.getLastRead());
        }
    };

    public static final Comparator<MangaLibraryEntry> RATING = new Comparator<MangaLibraryEntry>() {
        @Override
        public int compare(final MangaLibraryEntry lhs, final MangaLibraryEntry rhs) {
            return Rating.compare(lhs.getRating(), rhs.getRating());
        }
    };

    public static final Comparator<MangaLibraryEntry> TITLE = new Comparator<MangaLibraryEntry>() {
        @Override
        public int compare(final MangaLibraryEntry lhs, final MangaLibraryEntry rhs) {
            return lhs.getManga().getTitle().compareToIgnoreCase(rhs.getManga().getTitle());
        }
    };

}
