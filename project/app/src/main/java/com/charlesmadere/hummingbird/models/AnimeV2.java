package com.charlesmadere.hummingbird.models;

import android.content.res.Resources;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.preferences.Preferences;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AnimeV2 extends AbsAnime implements Parcelable {

    @Nullable
    @SerializedName("genres")
    private ArrayList<String> mGenres;

    @SerializedName("started_airing_date_known")
    private boolean mStartedAiringDateKnown;

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

    @SerializedName("poster_image")
    private String mPosterImage;

    @SerializedName("poster_image_thumb")
    private String mPosterImageThumb;

    @Nullable
    @SerializedName("romaji_title")
    private String mRomajiTitle;


    @Nullable
    public String getAgeRatingGuide() {
        return mAgeRatingGuide;
    }

    public String getCanonicalTitle() {
        return mCanonicalTitle;
    }

    @Nullable
    public String getEnglishTitle() {
        return mEnglishTitle;
    }

    @Nullable
    @Override
    public SimpleDate getFinishedAiringDate() {
        return mFinishedAiring;
    }

    @Override
    public String getGenresString(final Resources res) {
        if (!hasGenres()) {
            return "";
        }

        return TextUtils.join(res.getText(R.string.delimiter), mGenres);
    }

    @Override
    public String getImage() {
        return getPosterImage();
    }

    public String getPosterImage() {
        return mPosterImage;
    }

    public String getPosterImageThumb() {
        return mPosterImageThumb;
    }

    @Nullable
    public String getRomajiTitle() {
        return mRomajiTitle;
    }

    @Nullable
    @Override
    public SimpleDate getStartedAiringDate() {
        return mStartedAiring;
    }

    @Override
    public String getThumb() {
        return mPosterImageThumb;
    }

    @Override
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

    @Override
    public Version getVersion() {
        return Version.V2;
    }

    public boolean hasAgeRatingGuide() {
        return !TextUtils.isEmpty(mAgeRatingGuide);
    }

    @Override
    public boolean hasGenres() {
        return mGenres != null && !mGenres.isEmpty();
    }

    public boolean hasPosterImage() {
        return !TextUtils.isEmpty(mPosterImage);
    }

    public boolean hasPosterImageThumb() {
        return !TextUtils.isEmpty(mPosterImageThumb);
    }

    @Override
    protected void readFromParcel(final Parcel source) {
        super.readFromParcel(source);
        mGenres = source.createStringArrayList();
        mStartedAiringDateKnown = source.readInt() != 0;
        mFinishedAiring = source.readParcelable(SimpleDate.class.getClassLoader());
        mStartedAiring = source.readParcelable(SimpleDate.class.getClassLoader());
        mAgeRatingGuide = source.readString();
        mCanonicalTitle = source.readString();
        mEnglishTitle = source.readString();
        mPosterImage = source.readString();
        mPosterImageThumb = source.readString();
        mRomajiTitle = source.readString();
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        super.writeToParcel(dest, flags);
        dest.writeStringList(mGenres);
        dest.writeInt(mStartedAiringDateKnown ? 1 : 0);
        dest.writeParcelable(mFinishedAiring, flags);
        dest.writeParcelable(mStartedAiring, flags);
        dest.writeString(mAgeRatingGuide);
        dest.writeString(mCanonicalTitle);
        dest.writeString(mEnglishTitle);
        dest.writeString(mPosterImage);
        dest.writeString(mPosterImageThumb);
        dest.writeString(mRomajiTitle);
    }

    public static final Creator<AnimeV2> CREATOR = new Creator<AnimeV2>() {
        @Override
        public AnimeV2 createFromParcel(final Parcel source) {
            final AnimeV2 a = new AnimeV2();
            a.readFromParcel(source);
            return a;
        }

        @Override
        public AnimeV2[] newArray(final int size) {
            return new AnimeV2[size];
        }
    };

}
