package com.charlesmadere.hummingbird.models;

import android.content.res.Resources;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.misc.MiscUtils;
import com.charlesmadere.hummingbird.misc.ParcelableUtils;
import com.charlesmadere.hummingbird.preferences.Preferences;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Anime implements Parcelable {

    @Nullable
    @SerializedName("age_rating")
    private AgeRating mAgeRating;

    @Nullable
    @SerializedName("show_type")
    private AnimeType mAnimeType;

    @Nullable
    @SerializedName("genres")
    private ArrayList<String> mGenres;

    @SerializedName("started_airing_date_known")
    private boolean mStartedAiringDateKnown;

    @SerializedName("community_rating")
    private float mCommunityRating;

    @Nullable
    @SerializedName("episode_count")
    private Integer mEpisodeCount;

    @Nullable
    @SerializedName("episode_length")
    private Integer mEpisodeLength;

    @Nullable
    @SerializedName("finished_airing")
    private SimpleDate mFinishedAiring;

    @Nullable
    @SerializedName("started_airing")
    private SimpleDate mStartedAiring;

    @Nullable
    @SerializedName("age_rating_guide")
    private String mAgeRatingGuide;

    @SerializedName("canonical_title")
    private String mCanonicalTitle;

    @Nullable
    @SerializedName("english_title")
    private String mEnglishTitle;

    @SerializedName("id")
    private String mId;

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


    @Override
    public boolean equals(final Object o) {
        return o instanceof Anime && mId.equalsIgnoreCase(((Anime) o).getId());
    }

    @Nullable
    public AgeRating getAgeRating() {
        return mAgeRating;
    }

    @Nullable
    public String getAgeRatingGuide() {
        return mAgeRatingGuide;
    }

    public String getCanonicalTitle() {
        return mCanonicalTitle;
    }

    public float getCommunityRating() {
        return mCommunityRating;
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
        return mFinishedAiring;
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
    public SimpleDate getStartedAiringDate() {
        return mStartedAiring;
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

    public boolean hasAgeRating() {
        return mAgeRating != null;
    }

    public boolean hasAgeRatingGuide() {
        return !TextUtils.isEmpty(mAgeRatingGuide);
    }

    public boolean hasEpisodeCount() {
        return mEpisodeCount != null && mEpisodeCount >= 1;
    }

    public boolean hasEpisodeLength() {
        return mEpisodeLength != null && mEpisodeLength >= 1;
    }

    public boolean hasFinishedAiringDate() {
        return mFinishedAiring != null;
    }

    public boolean hasGenres() {
        return mGenres != null && !mGenres.isEmpty();
    }

    @Override
    public int hashCode() {
        return mId.hashCode();
    }

    public boolean hasPosterImage() {
        return MiscUtils.isValidArtwork(mPosterImage);
    }

    public boolean hasPosterImageThumb() {
        return MiscUtils.isValidArtwork(mPosterImageThumb);
    }

    public boolean hasStartedAiringDate() {
        return mStartedAiringDateKnown && mStartedAiring != null;
    }

    public boolean hasSynopsis() {
        return !TextUtils.isEmpty(mSynopsis);
    }

    public boolean hasType() {
        return mAnimeType != null;
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
        dest.writeStringList(mGenres);
        dest.writeInt(mStartedAiringDateKnown ? 1 : 0);
        dest.writeFloat(mCommunityRating);
        ParcelableUtils.writeInteger(mEpisodeCount, dest);
        ParcelableUtils.writeInteger(mEpisodeLength, dest);
        dest.writeParcelable(mFinishedAiring, flags);
        dest.writeParcelable(mStartedAiring, flags);
        dest.writeString(mAgeRatingGuide);
        dest.writeString(mCanonicalTitle);
        dest.writeString(mEnglishTitle);
        dest.writeString(mPosterImage);
        dest.writeString(mPosterImageThumb);
        dest.writeString(mRomajiTitle);
        dest.writeString(mId);
        dest.writeString(mSynopsis);
    }

    public static final Creator<Anime> CREATOR = new Creator<Anime>() {
        @Override
        public Anime createFromParcel(final Parcel source) {
            final Anime a = new Anime();
            a.mAgeRating = source.readParcelable(AgeRating.class.getClassLoader());
            a.mAnimeType = source.readParcelable(AnimeType.class.getClassLoader());
            a.mGenres = source.createStringArrayList();
            a.mStartedAiringDateKnown = source.readInt() != 0;
            a.mCommunityRating = source.readFloat();
            a.mEpisodeCount = ParcelableUtils.readInteger(source);
            a.mEpisodeLength = ParcelableUtils.readInteger(source);
            a.mFinishedAiring = source.readParcelable(SimpleDate.class.getClassLoader());
            a.mStartedAiring = source.readParcelable(SimpleDate.class.getClassLoader());
            a.mAgeRatingGuide = source.readString();
            a.mCanonicalTitle = source.readString();
            a.mEnglishTitle = source.readString();
            a.mPosterImage = source.readString();
            a.mPosterImageThumb = source.readString();
            a.mRomajiTitle = source.readString();
            a.mId = source.readString();
            a.mSynopsis = source.readString();
            return a;
        }

        @Override
        public Anime[] newArray(final int size) {
            return new Anime[size];
        }
    };

}
