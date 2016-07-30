package com.charlesmadere.hummingbird.models;

import android.content.res.Resources;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.misc.Constants;
import com.charlesmadere.hummingbird.misc.MiscUtils;
import com.charlesmadere.hummingbird.misc.ParcelableUtils;
import com.charlesmadere.hummingbird.preferences.Preferences;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

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

    @Nullable
    @SerializedName("reviews")
    private ArrayList<AnimeReview> mReviews;

    @Nullable
    @SerializedName("users")
    private ArrayList<User> mUsers;

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

    public String getId() {
        return mInfo.getId();
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

    public int getProducersSize() {
        return mProducers == null ? 0 : mProducers.size();
    }

    public String getProducersString(final Resources res) {
        if (!hasProducers()) {
            return "";
        }

        return TextUtils.join(res.getString(R.string.delimiter), mProducers);
    }

    @Nullable
    public ArrayList<Quote> getQuotes() {
        return mQuotes;
    }

    @Nullable
    public ArrayList<AnimeReview> getReviews() {
        return mReviews;
    }

    public String getTitle() {
        return mInfo.getTitle();
    }

    @Nullable
    public ArrayList<User> getUsers() {
        return mUsers;
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

    public boolean hasReviews() {
        return mReviews != null && !mReviews.isEmpty();
    }

    public boolean hasUsers() {
        return mUsers != null && !mUsers.isEmpty();
    }

    public void hydrate() {
        if (hasCastings() && hasPeople()) {
            final Iterator<Casting> iterator = mCastings.iterator();

            do {
                final Casting casting = iterator.next();

                if (TextUtils.isEmpty(casting.getPersonId()) || !casting.hydrate(this)) {
                    iterator.remove();
                }
            } while (iterator.hasNext());

            if (mCastings.isEmpty()) {
                mCastings = null;
            }
        } else {
            mCastings = null;
        }

        if (hasEpisodes()) {
            Collections.sort(mEpisodes, Episode.COMPARATOR);
        }

        if (hasReviews() && hasUsers()) {
            final Iterator<AnimeReview> iterator = mReviews.iterator();

            do {
                final AnimeReview review = iterator.next();

                if (!review.hydrate(this)) {
                    iterator.remove();
                }
            } while (iterator.hasNext());
        } else {
            mReviews = null;
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
        dest.writeTypedList(mEpisodes);
        dest.writeTypedList(mPeople);
        dest.writeTypedList(mProducers);
        dest.writeTypedList(mQuotes);
        dest.writeTypedList(mReviews);
        dest.writeTypedList(mUsers);
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
            ad.mReviews = source.createTypedArrayList(AnimeReview.CREATOR);
            ad.mUsers = source.createTypedArrayList(User.CREATOR);
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

        // hydrated fields
        @Nullable
        private Character mCharacter;

        private Person mPerson;


        @Nullable
        public Character getCharacter() {
            return mCharacter;
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

        public Person getPerson() {
            return mPerson;
        }

        public String getPersonId() {
            return mPersonId;
        }

        public String getRole() {
            return mRole;
        }

        public boolean hasCharacter() {
            return mCharacter != null;
        }

        public boolean hasLanguage() {
            return !TextUtils.isEmpty(mLanguage);
        }

        public boolean hydrate(final AnimeDigest animeDigest) {
            if (!TextUtils.isEmpty(mCharacterId)) {
                for (final Character character : animeDigest.getCharacters()) {
                    if (mCharacterId.equalsIgnoreCase(character.getId())) {
                        mCharacter = character;
                        break;
                    }
                }
            }

            for (final Person person : animeDigest.getPeople()) {
                if (mPersonId.equalsIgnoreCase(person.getId())) {
                    mPerson = person;
                    break;
                }
            }

            return mPerson != null;
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
            dest.writeParcelable(mCharacter, flags);
            dest.writeParcelable(mPerson, flags);
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
                c.mCharacter = source.readParcelable(Character.class.getClassLoader());
                c.mPerson = source.readParcelable(Person.class.getClassLoader());
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

        public boolean hasImage() {
            return !TextUtils.isEmpty(mImage);
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


    public static class Episode implements Parcelable {
        @SerializedName("number")
        private int mNumber;

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
        public boolean equals(final Object o) {
            return o instanceof Episode && mId.equalsIgnoreCase(((Episode) o).getId());
        }

        public String getId() {
            return mId;
        }

        public int getNumber() {
            return mNumber;
        }

        @Nullable
        public String getSynopsis() {
            return mSynopsis;
        }

        @Nullable
        public String getThumbnail() {
            return mThumbnail;
        }

        public String getTitle() {
            return mTitle;
        }

        @Override
        public int hashCode() {
            return mId.hashCode();
        }

        public boolean hasSynopsis() {
            return !TextUtils.isEmpty(mSynopsis);
        }

        public boolean hasThumbnail() {
            return MiscUtils.isValidArtwork(mThumbnail);
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
            dest.writeInt(mNumber);
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

        public static final Comparator<Episode> COMPARATOR = new Comparator<Episode>() {
            @Override
            public int compare(final Episode lhs, final Episode rhs) {
                return MiscUtils.integerCompare(lhs.getNumber(), rhs.getNumber());
            }
        };
    }


    public static class Info implements Parcelable {
        @Nullable
        @SerializedName("age_rating")
        private AgeRating mAgeRating;

        @Nullable
        @SerializedName("show_type")
        private AnimeType mAnimeType;

        @Nullable
        @SerializedName("franchise_ids")
        private ArrayList<String> mFranchiseIds;

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

        @SerializedName("started_airing_date_known")
        private boolean mStartedAiringDateKnown;

        @Nullable
        @SerializedName("bayesian_rating")
        private Float mBayesianRating;

        @SerializedName("cover_image_top_offset")
        private int mCoverImageTopOffset;

        @Nullable
        @SerializedName("episode_count")
        private Integer mEpisodeCount;

        @Nullable
        @SerializedName("episode_length")
        private Integer mEpisodeLength;

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

        @Nullable
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

        @Nullable
        public Float getBayesianRating() {
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

        public String getFranchiseId() {
            return mFranchiseIds.get(0);
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

            return TextUtils.join(res.getString(R.string.delimiter), mGenres);
        }

        public String getId() {
            return mId;
        }

        @Nullable
        public ArrayList<String> getLanguages() {
            return mLanguages;
        }

        public int getLanguagesSize() {
            return mLanguages == null ? 0 : mLanguages.size();
        }

        public String getLanguagesString(final Resources res) {
            if (!hasLanguages()) {
                return "";
            }

            return TextUtils.join(res.getText(R.string.delimiter), mLanguages);
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

        @Nullable
        public String getRomajiTitle() {
            return mRomajiTitle;
        }

        @Nullable
        public ArrayList<String> getScreencaps() {
            return mScreencaps;
        }

        @Nullable
        public SimpleDate getStartedAiringDate() {
            return mStartedAiringDate;
        }

        @Nullable
        public String getSynopsis() {
            return mSynopsis;
        }

        public String getTitle() {
            final TitleType titleType = Preferences.General.TitleLanguage.get();

            if (titleType == null) {
                return getCanonicalTitle();
            }

            String title;

            switch (titleType) {
                case CANONICAL:
                    return getCanonicalTitle();

                case ENGLISH:
                    title = getEnglishTitle();
                    break;

                case JAPANESE:
                case ROMAJI:
                    title = getRomajiTitle();
                    break;

                default:
                    throw new RuntimeException("encountered unknown " +
                            TitleType.class.getSimpleName() + ": \"" + titleType + '"');
            }

            if (TextUtils.isEmpty(title)) {
                title = getCanonicalTitle();
            }

            return title;
        }

        @Nullable
        public AnimeType getType() {
            return mAnimeType;
        }

        @Nullable
        public SimpleDate getUpdatedAt() {
            return mUpdatedAt;
        }

        @Nullable
        public String getYouTubeVideoId() {
            return mYouTubeVideoId;
        }

        @Nullable
        public String getYouTubeVideoUrl() {
            if (!hasYouTubeVideoId()) {
                return null;
            }

            return Constants.YOUTUBE_BASE_URL + mYouTubeVideoId;
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

        public boolean hasBayesianRating() {
            return mBayesianRating != null && mBayesianRating != 0f;
        }

        public boolean hasCoverImage() {
            return !TextUtils.isEmpty(mCoverImage);
        }

        public boolean hasEnglishTitle() {
            return !TextUtils.isEmpty(mEnglishTitle);
        }

        public boolean hasEpisodeCount() {
            return mEpisodeCount != null && mEpisodeCount >= 1;
        }

        public boolean hasEpisodeLength() {
            return mEpisodeLength != null && mEpisodeLength >= 1;
        }

        public boolean hasFinishedAiringDate() {
            return mFinishedAiringDate != null;
        }

        public boolean hasFranchiseId() {
            return mFranchiseIds != null && !mFranchiseIds.isEmpty();
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

        public boolean hasPosterImage() {
            return MiscUtils.isValidArtwork(mPosterImage);
        }

        public boolean hasPosterImageThumb() {
            return MiscUtils.isValidArtwork(mPosterImageThumb);
        }

        public boolean hasReviewed() {
            return mHasReviewed;
        }

        public boolean hasRomajiTitle() {
            return !TextUtils.isEmpty(mRomajiTitle);
        }

        public boolean hasScreencaps() {
            return mScreencaps != null && !mScreencaps.isEmpty();
        }

        public boolean hasStartedAiringDate() {
            return mStartedAiringDateKnown && mStartedAiringDate != null;
        }

        public boolean hasSynopsis() {
            return !TextUtils.isEmpty(mSynopsis);
        }

        public boolean hasType() {
            return mAnimeType != null;
        }

        public boolean hasUpdatedAt() {
            return mUpdatedAt != null;
        }

        public boolean hasYouTubeVideoId() {
            return !TextUtils.isEmpty(mYouTubeVideoId);
        }

        public void setLibraryEntryId(@Nullable final String libraryEntryId) {
            mLibraryEntryId = libraryEntryId;
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
            dest.writeParcelable(mAgeRating, flags);
            dest.writeParcelable(mAnimeType, flags);
            dest.writeStringList(mFranchiseIds);
            dest.writeStringList(mGenres);
            dest.writeStringList(mLanguages);
            dest.writeStringList(mScreencaps);
            dest.writeInt(mHasReviewed ? 1 : 0);
            dest.writeInt(mStartedAiringDateKnown ? 1 : 0);
            ParcelableUtils.writeFloat(mBayesianRating, dest);
            dest.writeInt(mCoverImageTopOffset);
            ParcelableUtils.writeInteger(mEpisodeCount, dest);
            ParcelableUtils.writeInteger(mEpisodeLength, dest);
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
                i.mAnimeType = source.readParcelable(AnimeType.class.getClassLoader());
                i.mFranchiseIds = source.createStringArrayList();
                i.mGenres = source.createStringArrayList();
                i.mLanguages = source.createStringArrayList();
                i.mScreencaps = source.createStringArrayList();
                i.mHasReviewed = source.readInt() != 0;
                i.mStartedAiringDateKnown = source.readInt() != 0;
                i.mBayesianRating = ParcelableUtils.readFloat(source);
                i.mCoverImageTopOffset = source.readInt();
                i.mEpisodeCount = ParcelableUtils.readInteger(source);
                i.mEpisodeLength = ParcelableUtils.readInteger(source);
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

        public static final Creator<Person> CREATOR = new Creator<Person>() {
            @Override
            public Person createFromParcel(final Parcel source) {
                final Person p = new Person();
                p.mId = source.readString();
                p.mImage = source.readString();
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

        @Nullable
        @SerializedName("character_name")
        private String mCharacterName;

        @SerializedName("content")
        private String mContent;

        @SerializedName("id")
        private String mId;

        @SerializedName("username")
        private String mUsername;


        @Override
        public boolean equals(final Object o) {
            return o instanceof Quote && mId.equalsIgnoreCase(((Quote) o).getId());
        }

        public String getAnimeId() {
            return mAnimeId;
        }

        @Nullable
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

        public boolean hasCharacterName() {
            return !TextUtils.isEmpty(mCharacterName);
        }

        @Override
        public int hashCode() {
            return mId.hashCode();
        }

        public boolean isFavorite() {
            return mIsFavorite;
        }

        public void setFavorite(final boolean favorite) {
            if (favorite == mIsFavorite) {
                return;
            }

            mIsFavorite = favorite;

            if (mIsFavorite) {
                ++mFavoriteCount;
            } else {
                --mFavoriteCount;
            }
        }

        public void toggleFavorite() {
            setFavorite(!mIsFavorite);
        }

        public JsonObject toJson() {
            final JsonObject quote = new JsonObject();
            quote.addProperty("anime_id", mAnimeId);
            quote.addProperty("id", mId);
            quote.addProperty("is_favorite", mIsFavorite);

            final JsonObject json = new JsonObject();
            json.add("quote", quote);

            return json;
        }

        @Override
        public String toString() {
            return getContent();
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
