package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.charlesmadere.hummingbird.misc.ParcelableUtils;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AnimeDigest implements Parcelable {

    @Nullable
    @SerializedName("castings")
    private ArrayList<Casting> mCastings;

    @Nullable
    @SerializedName("characters")
    private ArrayList<Character> mCharacters;

    @Nullable
    @SerializedName("episodes")
    private ArrayList<Episode> mEpisodes;

    @Nullable
    @SerializedName("people")
    private ArrayList<Person> mPeople;

    @Nullable
    @SerializedName("producers")
    private ArrayList<Producer> mProducers;

    @Nullable
    @SerializedName("quotes")
    private ArrayList<Quote> mQuotes;

    @SerializedName("full_anime")
    private Info mInfo;


    @Nullable
    public ArrayList<Casting> getCastings() {
        return mCastings;
    }

    @Nullable
    public ArrayList<Character> getCharacters() {
        return mCharacters;
    }

    @Nullable
    public ArrayList<Episode> getEpisodes() {
        return mEpisodes;
    }

    public Info getInfo() {
        return mInfo;
    }

    @Nullable
    public ArrayList<Person> getPeople() {
        return mPeople;
    }

    @Nullable
    public ArrayList<Producer> getProducers() {
        return mProducers;
    }

    @Nullable
    public ArrayList<Quote> getQuotes() {
        return mQuotes;
    }

    public boolean hasCastings() {
        return mCastings != null && !mCastings.isEmpty();
    }

    public boolean hasCharacters() {
        return mCharacters != null && !mCharacters.isEmpty();
    }

    public boolean hasEpisodes() {
        return mEpisodes != null && !mEpisodes.isEmpty();
    }

    public boolean hasPeople() {
        return mPeople != null && !mPeople.isEmpty();
    }

    public boolean hasProducers() {
        return mProducers != null && !mProducers.isEmpty();
    }

    public boolean hasQuotes() {
        return mQuotes != null && !mQuotes.isEmpty();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeTypedList(mCastings);
        dest.writeTypedList(mCharacters);
        dest.writeTypedList(mEpisodes);
        dest.writeTypedList(mPeople);
        dest.writeTypedList(mProducers);
        dest.writeTypedList(mQuotes);
        dest.writeParcelable(mInfo, flags);
    }

    public static final Creator<AnimeDigest> CREATOR = new Creator<AnimeDigest>() {
        @Override
        public AnimeDigest createFromParcel(final Parcel source) {
            final AnimeDigest ad = new AnimeDigest();
            ad.mCastings = source.createTypedArrayList(Casting.CREATOR);
            ad.mCharacters = source.createTypedArrayList(Character.CREATOR);
            ad.mEpisodes = source.createTypedArrayList(Episode.CREATOR);
            ad.mPeople = source.createTypedArrayList(Person.CREATOR);
            ad.mProducers = source.createTypedArrayList(Producer.CREATOR);
            ad.mQuotes = source.createTypedArrayList(Quote.CREATOR);
            ad.mInfo = source.readParcelable(Info.class.getClassLoader());
            return ad;
        }

        @Override
        public AnimeDigest[] newArray(final int size) {
            return new AnimeDigest[size];
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

        @SerializedName("person_id")
        private String mPersonId;

        @SerializedName("role")
        private String mRole;


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

        public String getPersonId() {
            return mPersonId;
        }

        public String getRole() {
            return mRole;
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

        @Nullable
        @SerializedName("image")
        private String mImage;

        @SerializedName("name")
        private String mName;


        public String getId() {
            return mId;
        }

        @Nullable
        public String getImage() {
            return mImage;
        }

        public String getName() {
            return mName;
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


    public static class Episode implements Parcelable {
        @SerializedName("number")
        private int mNumber;

        @SerializedName("anime_id")
        private String mAnimeId;

        @SerializedName("id")
        private String mId;

        @Nullable
        @SerializedName("synopsis")
        private String mSynopsis;

        @Nullable
        @SerializedName("thumbnail")
        private String mThumbnail;

        @SerializedName("title")
        private String mTitle;


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(final Parcel dest, final int flags) {
            dest.writeInt(mNumber);
            dest.writeString(mAnimeId);
            dest.writeString(mId);
            dest.writeString(mSynopsis);
            dest.writeString(mThumbnail);
            dest.writeString(mTitle);
        }

        public static final Creator<Episode> CREATOR = new Creator<Episode>() {
            @Override
            public Episode createFromParcel(final Parcel source) {
                final Episode e = new Episode();
                e.mNumber = source.readInt();
                e.mAnimeId = source.readString();
                e.mId = source.readString();
                e.mSynopsis = source.readString();
                e.mThumbnail = source.readString();
                e.mTitle = source.readString();
                return e;
            }

            @Override
            public Episode[] newArray(final int size) {
                return new Episode[size];
            }
        };
    }


    public static class Info implements Parcelable {
        @Nullable
        @SerializedName("age_rating")
        private AgeRating mAgeRating;

        @Nullable
        @SerializedName("genres")
        private ArrayList<String> mGenres;

        @Nullable
        @SerializedName("languages")
        private ArrayList<String> mLanguages;

        @Nullable
        @SerializedName("screencaps")
        private ArrayList<String> mScreencaps;

        @SerializedName("has_reviewed")
        private boolean mHasReviewed;

        @SerializedName("pending_edits")
        private boolean mHasPendingEdits;

        @SerializedName("started_airing_date_known")
        private boolean mStartedAiringDateKnown;

        @SerializedName("bayesian_rating")
        private float mBayesianRating;

        @SerializedName("cover_image_top_offset")
        private int mCoverImageTopOffset;

        @Nullable
        @SerializedName("episode_count")
        private Integer mEpisodeCount;

        @Nullable
        @SerializedName("episode_length")
        private Integer mEpisodeLength;

        @Nullable
        @SerializedName("show_type")
        private ShowType mShowType;

        @Nullable
        @SerializedName("finished_airing")
        private SimpleDate mFinishedAiringDate;

        @Nullable
        @SerializedName("started_airing")
        private SimpleDate mStartedAiringDate;

        @Nullable
        @SerializedName("updated_at")
        private SimpleDate mUpdatedAt;

        @Nullable
        @SerializedName("age_rating_guide")
        private String mAgeRatingGuide;

        @Nullable
        @SerializedName("alternate_title")
        private String mAlternateTitle;

        @SerializedName("canonical_title")
        private String mCanonicalTitle;

        @Nullable
        @SerializedName("cover_image")
        private String mCoverImage;

        @Nullable
        @SerializedName("english_title")
        private String mEnglishTitle;

        @SerializedName("id")
        private String mId;

        @Nullable
        @SerializedName("library_entry_id")
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

        @Nullable
        @SerializedName("youtube_id")
        private String mYouTubeVideoId;


        @Nullable
        public AgeRating getAgeRating() {
            return mAgeRating;
        }

        @Nullable
        public String getAgeRatingGuide() {
            return mAgeRatingGuide;
        }

        @Nullable
        public String getAlternateTitle() {
            return mAlternateTitle;
        }

        public float getBayesianRating() {
            return mBayesianRating;
        }

        public String getCanonicalTitle() {
            return mCanonicalTitle;
        }

        @Nullable
        public String getCoverImage() {
            return mCoverImage;
        }

        public int getCoverImageTopOffset() {
            return mCoverImageTopOffset;
        }

        @Nullable
        public String getEnglishTitle() {
            return mEnglishTitle;
        }

        @Nullable
        public Integer getEpisodeCount() {
            return mEpisodeCount;
        }

        @Nullable
        public Integer getEpisodeLength() {
            return mEpisodeLength;
        }

        @Nullable
        public SimpleDate getFinishedAiringDate() {
            return mFinishedAiringDate;
        }

        @Nullable
        public ArrayList<String> getGenres() {
            return mGenres;
        }

        public String getId() {
            return mId;
        }

        @Nullable
        public ArrayList<String> getLanguages() {
            return mLanguages;
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
        public ArrayList<String> getScreencaps() {
            return mScreencaps;
        }

        @Nullable
        public ShowType getShowType() {
            return mShowType;
        }

        @Nullable
        public SimpleDate getStartedAiringDate() {
            return mStartedAiringDate;
        }

        @Nullable
        public String getSynopsis() {
            return mSynopsis;
        }

        @Nullable
        public SimpleDate getUpdatedAt() {
            return mUpdatedAt;
        }

        @Nullable
        public String getYouTubeVideoId() {
            return mYouTubeVideoId;
        }

        public boolean hasAgeRating() {
            return mAgeRating != null;
        }

        public boolean hasAgeRatingGuide() {
            return !TextUtils.isEmpty(mAgeRatingGuide);
        }

        public boolean hasAlternateTitle() {
            return !TextUtils.isEmpty(mAlternateTitle);
        }

        public boolean hasCoverImage() {
            return !TextUtils.isEmpty(mCoverImage);
        }

        public boolean hasEnglishTitle() {
            return !TextUtils.isEmpty(mEnglishTitle);
        }

        public boolean hasEpisodeCount() {
            return mEpisodeCount != null;
        }

        public boolean hasEpisodeLength() {
            return mEpisodeLength != null;
        }

        public boolean hasFinishedAiringDate() {
            return mFinishedAiringDate != null;
        }

        public boolean hasGenres() {
            return mGenres != null && !mGenres.isEmpty();
        }

        public boolean hasLanguages() {
            return mLanguages != null && !mLanguages.isEmpty();
        }

        public boolean hasLibraryEntryId() {
            return !TextUtils.isEmpty(mLibraryEntryId);
        }

        public boolean hasPendingEdits() {
            return mHasPendingEdits;
        }

        public boolean hasPosterImage() {
            return !TextUtils.isEmpty(mPosterImage);
        }

        public boolean hasPosterImageThumb() {
            return !TextUtils.isEmpty(mPosterImageThumb);
        }

        public boolean hasReviewed() {
            return mHasReviewed;
        }

        public boolean hasScreencaps() {
            return mScreencaps != null && !mScreencaps.isEmpty();
        }

        public boolean hasShowType() {
            return mShowType != null;
        }

        public boolean hasStartedAiringDate() {
            return mStartedAiringDateKnown && mStartedAiringDate != null;
        }

        public boolean hasSynopsis() {
            return !TextUtils.isEmpty(mSynopsis);
        }

        public boolean hasUpdatedAt() {
            return mUpdatedAt != null;
        }

        public boolean hasYouTubeVideoId() {
            return !TextUtils.isEmpty(mYouTubeVideoId);
        }

        public boolean isStartedAiringDateKnown() {
            return mStartedAiringDateKnown;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(final Parcel dest, final int flags) {
            dest.writeParcelable(mAgeRating, flags);
            dest.writeStringList(mGenres);
            dest.writeStringList(mLanguages);
            dest.writeStringList(mScreencaps);
            dest.writeInt(mHasReviewed ? 1 : 0);
            dest.writeInt(mHasPendingEdits ? 1 : 0);
            dest.writeInt(mStartedAiringDateKnown ? 1 : 0);
            dest.writeFloat(mBayesianRating);
            dest.writeInt(mCoverImageTopOffset);
            ParcelableUtils.writeInteger(mEpisodeCount, dest);
            ParcelableUtils.writeInteger(mEpisodeLength, dest);
            dest.writeParcelable(mShowType, flags);
            dest.writeParcelable(mFinishedAiringDate, flags);
            dest.writeParcelable(mStartedAiringDate, flags);
            dest.writeParcelable(mUpdatedAt, flags);
            dest.writeString(mAgeRatingGuide);
            dest.writeString(mAlternateTitle);
            dest.writeString(mCanonicalTitle);
            dest.writeString(mCoverImage);
            dest.writeString(mEnglishTitle);
            dest.writeString(mId);
            dest.writeString(mLibraryEntryId);
            dest.writeString(mPosterImage);
            dest.writeString(mPosterImageThumb);
            dest.writeString(mRomajiTitle);
            dest.writeString(mSynopsis);
            dest.writeString(mYouTubeVideoId);
        }

        public static final Creator<Info> CREATOR = new Creator<Info>() {
            @Override
            public Info createFromParcel(final Parcel source) {
                final Info i = new Info();
                i.mAgeRating = source.readParcelable(AgeRating.class.getClassLoader());
                i.mGenres = source.createStringArrayList();
                i.mLanguages = source.createStringArrayList();
                i.mScreencaps = source.createStringArrayList();
                i.mHasReviewed = source.readInt() != 0;
                i.mHasPendingEdits = source.readInt() != 0;
                i.mStartedAiringDateKnown = source.readInt() != 0;
                i.mBayesianRating = source.readFloat();
                i.mCoverImageTopOffset = source.readInt();
                i.mEpisodeCount = ParcelableUtils.readInteger(source);
                i.mEpisodeLength = ParcelableUtils.readInteger(source);
                i.mShowType = source.readParcelable(ShowType.class.getClassLoader());
                i.mFinishedAiringDate = source.readParcelable(SimpleDate.class.getClassLoader());
                i.mStartedAiringDate = source.readParcelable(SimpleDate.class.getClassLoader());
                i.mUpdatedAt = source.readParcelable(SimpleDate.class.getClassLoader());
                i.mAgeRatingGuide = source.readString();
                i.mAlternateTitle = source.readString();
                i.mCanonicalTitle = source.readString();
                i.mCoverImage = source.readString();
                i.mEnglishTitle = source.readString();
                i.mId = source.readString();
                i.mLibraryEntryId = source.readString();
                i.mPosterImage = source.readString();
                i.mPosterImageThumb = source.readString();
                i.mRomajiTitle = source.readString();
                i.mSynopsis = source.readString();
                i.mYouTubeVideoId = source.readString();
                return i;
            }

            @Override
            public Info[] newArray(final int size) {
                return new Info[size];
            }
        };
    }


    public static class Person implements Parcelable {
        @SerializedName("id")
        private String mId;

        @Nullable
        @SerializedName("image")
        private String mImage;

        @SerializedName("name")
        private String mName;


        public String getId() {
            return mId;
        }

        @Nullable
        public String getImage() {
            return mImage;
        }

        public String getName() {
            return mName;
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

        public static final Creator<Person> CREATOR = new Creator<Person>() {
            @Override
            public Person createFromParcel(final Parcel source) {
                final Person p = new Person();
                p.mId = source.readString();
                p.mId = source.readString();
                p.mName = source.readString();
                return p;
            }

            @Override
            public Person[] newArray(final int size) {
                return new Person[size];
            }
        };
    }


    public static class Producer implements Parcelable {
        @SerializedName("id")
        private String mId;

        @SerializedName("name")
        private String mName;


        public String getId() {
            return mId;
        }

        public String getName() {
            return mName;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(final Parcel dest, final int flags) {
            dest.writeString(mId);
            dest.writeString(mName);
        }

        public static final Creator<Producer> CREATOR = new Creator<Producer>() {
            @Override
            public Producer createFromParcel(final Parcel source) {
                final Producer p = new Producer();
                p.mId = source.readString();
                p.mName = source.readString();
                return p;
            }

            @Override
            public Producer[] newArray(final int size) {
                return new Producer[size];
            }
        };
    }


    public static class Quote implements Parcelable {
        @SerializedName("is_favorite")
        private boolean mIsFavorite;

        @SerializedName("favorite_count")
        private int mFavoriteCount;

        @SerializedName("anime_id")
        private String mAnimeId;

        @SerializedName("character_name")
        private String mCharacterName;

        @SerializedName("content")
        private String mContent;

        @SerializedName("id")
        private String mId;

        @SerializedName("username")
        private String mUsername;


        public String getAnimeId() {
            return mAnimeId;
        }

        public String getCharacterName() {
            return mCharacterName;
        }

        public String getContent() {
            return mContent;
        }

        public int getFavoriteCount() {
            return mFavoriteCount;
        }

        public String getId() {
            return mId;
        }

        public String getUsername() {
            return mUsername;
        }

        public boolean isFavorite() {
            return mIsFavorite;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(final Parcel dest, final int flags) {
            dest.writeInt(mIsFavorite ? 1 : 0);
            dest.writeInt(mFavoriteCount);
            dest.writeString(mAnimeId);
            dest.writeString(mCharacterName);
            dest.writeString(mContent);
            dest.writeString(mId);
            dest.writeString(mUsername);
        }

        public static final Creator<Quote> CREATOR = new Creator<Quote>() {
            @Override
            public Quote createFromParcel(final Parcel source) {
                final Quote q = new Quote();
                q.mIsFavorite = source.readInt() != 0;
                q.mFavoriteCount = source.readInt();
                q.mAnimeId = source.readString();
                q.mCharacterName = source.readString();
                q.mContent = source.readString();
                q.mId = source.readString();
                q.mUsername = source.readString();
                return q;
            }

            @Override
            public Quote[] newArray(final int size) {
                return new Quote[size];
            }
        };
    }

}
