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
import java.util.Iterator;

public class MangaDigest implements Parcelable {

    @Nullable
    @SerializedName("castings")
    private ArrayList<Casting> mCastings;

    @Nullable
    @SerializedName("characters")
    private ArrayList<Character> mCharacters;

    @Nullable
    @SerializedName("manga")
    private ArrayList<Manga> mManga;

    @Nullable
    @SerializedName("manga_library_entries")
    private ArrayList<MangaLibraryEntry> mLibraryEntries;

    @SerializedName("full_manga")
    private Info mInfo;


    @Override
    public boolean equals(final Object o) {
        return o instanceof MangaDigest && getId().equalsIgnoreCase(((MangaDigest) o).getId());
    }

    @Nullable
    public ArrayList<Casting> getCastings() {
        return mCastings;
    }

    @Nullable
    public ArrayList<Character> getCharacters() {
        return mCharacters;
    }

    public String getId() {
        return mInfo.getId();
    }

    public Info getInfo() {
        return mInfo;
    }

    public MangaLibraryEntry getLibraryEntry() {
        return mLibraryEntries.get(0);
    }

    @Nullable
    public ArrayList<Manga> getManga() {
        return mManga;
    }

    public String getTitle() {
        return mInfo.getTitle();
    }

    public boolean hasCastings() {
        return mCastings != null && !mCastings.isEmpty();
    }

    public boolean hasCharacters() {
        return mCharacters != null && !mCharacters.isEmpty();
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

    public boolean hasLibraryEntries() {
        return mLibraryEntries != null && !mLibraryEntries.isEmpty();
    }

    public boolean hasManga() {
        return mManga != null && !mManga.isEmpty();
    }

    public void hydrate() {
        if (hasLibraryEntries()) {
            final Iterator<MangaLibraryEntry> iterator = mLibraryEntries.iterator();

            do {
                final MangaLibraryEntry libraryEntry = iterator.next();

                if (!libraryEntry.hydrate(this)) {
                    iterator.remove();
                }
            } while (iterator.hasNext());

            if (mLibraryEntries.isEmpty()) {
                mLibraryEntries = null;
            }
        } else {
            mLibraryEntries = null;
        }
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
        dest.writeTypedList(mCastings);
        dest.writeTypedList(mCharacters);
        dest.writeTypedList(mManga);
        dest.writeTypedList(mLibraryEntries);
        dest.writeParcelable(mInfo, flags);
    }

    public static final Creator<MangaDigest> CREATOR = new Creator<MangaDigest>() {
        @Override
        public MangaDigest createFromParcel(final Parcel source) {
            final MangaDigest md = new MangaDigest();
            md.mCastings = source.createTypedArrayList(Casting.CREATOR);
            md.mCharacters = source.createTypedArrayList(Character.CREATOR);
            md.mManga = source.createTypedArrayList(Manga.CREATOR);
            md.mLibraryEntries = source.createTypedArrayList(MangaLibraryEntry.CREATOR);
            md.mInfo = source.readParcelable(Info.class.getClassLoader());
            return md;
        }

        @Override
        public MangaDigest[] newArray(final int size) {
            return new MangaDigest[size];
        }
    };


    public static class Casting implements Parcelable {
        @SerializedName("character_id")
        private String mCharacterId;

        @SerializedName("id")
        private String mId;

        @Nullable
        @SerializedName("language")
        private String mLanguage;

        @Nullable
        @SerializedName("person_id")
        private String mPersonId;

        @Nullable
        @SerializedName("role")
        private String mRole;


        @Override
        public boolean equals(final Object o) {
            return o instanceof Casting && mId.equalsIgnoreCase(((Casting) o).getId());
        }

        public String getCharacterId() {
            return mCharacterId;
        }

        public String getId() {
            return mId;
        }

        @Nullable
        public String getLanguage() {
            return mLanguage;
        }

        @Nullable
        public String getPersonId() {
            return mPersonId;
        }

        @Nullable
        public String getRole() {
            return mRole;
        }

        @Override
        public int hashCode() {
            return mId.hashCode();
        }

        public boolean hasLanguage() {
            return !TextUtils.isEmpty(mLanguage);
        }

        public boolean hasPersonId() {
            return !TextUtils.isEmpty(mPersonId);
        }

        public boolean hasRole() {
            return !TextUtils.isEmpty(mRole);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(final Parcel dest, final int flags) {
            dest.writeString(mCharacterId);
            dest.writeString(mId);
            dest.writeString(mLanguage);
            dest.writeString(mPersonId);
            dest.writeString(mRole);
        }

        public static final Creator<Casting> CREATOR = new Creator<Casting>() {
            @Override
            public Casting createFromParcel(final Parcel source) {
                final Casting c = new Casting();
                c.mCharacterId = source.readString();
                c.mId = source.readString();
                c.mLanguage = source.readString();
                c.mPersonId = source.readString();
                c.mRole = source.readString();
                return c;
            }

            @Override
            public Casting[] newArray(final int size) {
                return new Casting[size];
            }
        };
    }


    public static class Character implements Parcelable {
        @SerializedName("id")
        private String mId;

        @SerializedName("image")
        private String mImage;

        @SerializedName("name")
        private String mName;


        @Override
        public boolean equals(final Object o) {
            return o instanceof Character && mId.equalsIgnoreCase(((Character) o).getId());
        }

        public String getId() {
            return mId;
        }

        public String getImage() {
            return mImage;
        }

        public String getName() {
            return mName;
        }

        @Override
        public int hashCode() {
            return mId.hashCode();
        }

        @Override
        public String toString() {
            return getName();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(final Parcel dest, final int flags) {
            dest.writeString(mId);
            dest.writeString(mImage);
            dest.writeString(mName);
        }

        public static final Creator<Character> CREATOR = new Creator<Character>() {
            @Override
            public Character createFromParcel(final Parcel source) {
                final Character c = new Character();
                c.mId = source.readString();
                c.mImage = source.readString();
                c.mName = source.readString();
                return c;
            }

            @Override
            public Character[] newArray(final int size) {
                return new Character[size];
            }
        };
    }


    public static class Info implements Parcelable {
        @Nullable
        @SerializedName("community_ratings")
        private ArrayList<Integer> mCommunityRatings;

        @Nullable
        @SerializedName("genres")
        private ArrayList<String> mGenres;

        @Nullable
        @SerializedName("bayesian_rating")
        private Float mBayesianRating;

        @Nullable
        @SerializedName("chapter_count")
        private Integer mChapterCount;

        @Nullable
        @SerializedName("cover_image_top_offset")
        private Integer mCoverImageTopOffset;

        @Nullable
        @SerializedName("pending_edits")
        private Integer mPendingEdits;

        @Nullable
        @SerializedName("volume_count")
        private Integer mVolumeCount;

        @Nullable
        @SerializedName("manga_type")
        private MangaType mType;

        @SerializedName("updated_at")
        private SimpleDate mUpdatedAt;

        @Nullable
        @SerializedName("cover_image")
        private String mCoverImage;

        @SerializedName("id")
        private String mId;

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
            return o instanceof Info && mId.equalsIgnoreCase(((Info) o).getId());
        }

        @Nullable
        public Float getBayesianRating() {
            return mBayesianRating;
        }

        @Nullable
        public Integer getChapterCount() {
            return mChapterCount;
        }

        @Nullable
        public ArrayList<Integer> getCommunityRatings() {
            return mCommunityRatings;
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
        public ArrayList<String> getGenres() {
            return mGenres;
        }

        public int getGenresSize() {
            return mGenres == null ? 0 : mGenres.size();
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

        public String getLibraryEntryId() {
            return mLibraryEntryId;
        }

        @Nullable
        public Integer getPendingEdits() {
            return mPendingEdits;
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
            return mType;
        }

        @Nullable
        public Integer getVolumeCount() {
            return mVolumeCount;
        }

        public boolean hasBayesianRating() {
            return mBayesianRating != null;
        }

        public boolean hasChapterCount() {
            return mChapterCount != null && mChapterCount >= 1;
        }

        public boolean hasCoverImage() {
            return MiscUtils.isValidArtwork(mCoverImage);
        }

        @Override
        public int hashCode() {
            return mId.hashCode();
        }

        public boolean hasGenres() {
            return mGenres != null && !mGenres.isEmpty();
        }

        public boolean hasPosterImage() {
            return MiscUtils.isValidArtwork(mPosterImage);
        }

        public boolean hasPosterImageThumb() {
            return MiscUtils.isValidArtwork(mPosterImageThumb);
        }

        public boolean hasType() {
            return mType != null;
        }

        public boolean hasSynopsis() {
            return !TextUtils.isEmpty(mSynopsis);
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
            ParcelableUtils.writeIntegerArrayList(mCommunityRatings, dest);
            dest.writeStringList(mGenres);
            ParcelableUtils.writeFloat(mBayesianRating, dest);
            ParcelableUtils.writeInteger(mChapterCount, dest);
            ParcelableUtils.writeInteger(mCoverImageTopOffset, dest);
            ParcelableUtils.writeInteger(mPendingEdits, dest);
            ParcelableUtils.writeInteger(mVolumeCount, dest);
            dest.writeParcelable(mType, flags);
            dest.writeParcelable(mUpdatedAt, flags);
            dest.writeString(mCoverImage);
            dest.writeString(mId);
            dest.writeString(mLibraryEntryId);
            dest.writeString(mPosterImage);
            dest.writeString(mPosterImageThumb);
            dest.writeString(mRomajiTitle);
            dest.writeString(mSynopsis);
        }

        public static final Creator<Info> CREATOR = new Creator<Info>() {
            @Override
            public Info createFromParcel(final Parcel source) {
                final Info i = new Info();
                i.mCommunityRatings = ParcelableUtils.readIntegerArrayList(source);
                i.mGenres = source.createStringArrayList();
                i.mBayesianRating = ParcelableUtils.readFloat(source);
                i.mChapterCount = ParcelableUtils.readInteger(source);
                i.mCoverImageTopOffset = ParcelableUtils.readInteger(source);
                i.mPendingEdits = ParcelableUtils.readInteger(source);
                i.mVolumeCount = ParcelableUtils.readInteger(source);
                i.mType = source.readParcelable(MangaType.class.getClassLoader());
                i.mUpdatedAt = source.readParcelable(SimpleDate.class.getClassLoader());
                i.mCoverImage = source.readString();
                i.mId = source.readString();
                i.mLibraryEntryId = source.readString();
                i.mPosterImage = source.readString();
                i.mPosterImageThumb = source.readString();
                i.mRomajiTitle = source.readString();
                i.mSynopsis = source.readString();
                return i;
            }

            @Override
            public Info[] newArray(final int size) {
                return new Info[size];
            }
        };
    }

}
