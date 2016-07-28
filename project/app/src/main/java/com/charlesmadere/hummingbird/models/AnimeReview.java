package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.charlesmadere.hummingbird.misc.JsoupUtils;
import com.google.gson.annotations.SerializedName;

public class AnimeReview implements Parcelable {

    @SerializedName("liked")
    private boolean mLiked;

    @SerializedName("rating")
    private float mRating;

    @SerializedName("rating_animation")
    private float mRatingAnimation;

    @SerializedName("rating_characters")
    private float mRatingCharacters;

    @SerializedName("rating_enjoyment")
    private float mRatingEnjoyment;

    @SerializedName("rating_sound")
    private float mRatingSound;

    @SerializedName("rating_story")
    private float mRatingStory;

    @SerializedName("positive_votes")
    private int mPositiveVotes;

    @SerializedName("total_votes")
    private int mTotalVotes;

    @SerializedName("anime_id")
    private String mAnimeId;

    @SerializedName("content")
    private String mContent;

    @SerializedName("formatted_content")
    private String mFormattedContent;

    @SerializedName("id")
    private String mId;

    @SerializedName("summary")
    private String mSummary;

    @SerializedName("user_id")
    private String mUserId;

    // hydrated fields
    @Nullable
    private Anime mAnime;
    private CharSequence mCompiledContent;
    private String mAnimeTitle;
    private User mUser;


    private void compileContent() {
        if (TextUtils.isEmpty(mCompiledContent)) {
            mCompiledContent = JsoupUtils.parse(TextUtils.isEmpty(mFormattedContent)
                    ? mContent : mFormattedContent);
        }
    }

    @Override
    public boolean equals(final Object o) {
        return o instanceof AnimeReview && mId.equalsIgnoreCase(((AnimeReview) o).getId());
    }

    @Nullable
    public Anime getAnime() {
        return mAnime;
    }

    public String getAnimeId() {
        return mAnimeId;
    }

    public String getAnimeTitle() {
        return mAnime == null ? mAnimeTitle : mAnime.getTitle();
    }

    public CharSequence getContent() {
        return mCompiledContent;
    }

    public String getId() {
        return mId;
    }

    public int getPositiveVotes() {
        return mPositiveVotes;
    }

    public float getRating() {
        return mRating;
    }

    public float getRatingAnimation() {
        return mRatingAnimation;
    }

    public float getRatingCharacters() {
        return mRatingCharacters;
    }

    public float getRatingEnjoyment() {
        return mRatingEnjoyment;
    }

    public float getRatingSound() {
        return mRatingSound;
    }

    public float getRatingStory() {
        return mRatingStory;
    }

    public String getSummary() {
        return mSummary;
    }

    public int getTotalVotes() {
        return mTotalVotes;
    }

    public User getUser() {
        return mUser;
    }

    public String getUserId() {
        return mUserId;
    }

    @Override
    public int hashCode() {
        return mId.hashCode();
    }

    public void hydrate(final Anime anime) {
        compileContent();
        mAnime = anime;
    }

    public boolean hydrate(final AnimeDigest animeDigest) {
        if (!animeDigest.hasUsers()) {
            return false;
        }

        compileContent();
        mAnimeTitle = animeDigest.getTitle();

        for (final User user : animeDigest.getUsers()) {
            if (mUserId.equalsIgnoreCase(user.getId())) {
                mUser = user;
                return true;
            }
        }

        return false;
    }

    public boolean hydrate(final Feed feed) {
        if (!feed.hasAnime() || !feed.hasUsers()) {
            return false;
        }

        compileContent();

        for (final Anime anime : feed.getAnime()) {
            if (mAnimeId.equalsIgnoreCase(anime.getId())) {
                mAnime = anime;
                break;
            }
        }

        for (final User user : feed.getUsers()) {
            if (mUserId.equalsIgnoreCase(user.getId())) {
                mUser = user;
            }
        }

        return mAnime != null && mUser != null;
    }

    public boolean isLiked() {
        return mLiked;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeInt(mLiked ? 1 : 0);
        dest.writeFloat(mRating);
        dest.writeFloat(mRatingAnimation);
        dest.writeFloat(mRatingCharacters);
        dest.writeFloat(mRatingEnjoyment);
        dest.writeFloat(mRatingSound);
        dest.writeFloat(mRatingStory);
        dest.writeInt(mPositiveVotes);
        dest.writeInt(mTotalVotes);
        dest.writeString(mAnimeId);
        dest.writeString(mContent);
        dest.writeString(mFormattedContent);
        dest.writeString(mId);
        dest.writeString(mSummary);
        dest.writeString(mUserId);
        dest.writeParcelable(mAnime, flags);
        TextUtils.writeToParcel(mCompiledContent, dest, flags);
        dest.writeString(mAnimeTitle);
        dest.writeParcelable(mUser, flags);
    }

    public static final Creator<AnimeReview> CREATOR = new Creator<AnimeReview>() {
        @Override
        public AnimeReview createFromParcel(final Parcel source) {
            final AnimeReview ar = new AnimeReview();
            ar.mLiked = source.readInt() != 0;
            ar.mRating = source.readFloat();
            ar.mRatingAnimation = source.readFloat();
            ar.mRatingCharacters = source.readFloat();
            ar.mRatingEnjoyment = source.readFloat();
            ar.mRatingSound = source.readFloat();
            ar.mRatingStory = source.readFloat();
            ar.mPositiveVotes = source.readInt();
            ar.mTotalVotes = source.readInt();
            ar.mAnimeId = source.readString();
            ar.mContent = source.readString();
            ar.mFormattedContent = source.readString();
            ar.mId = source.readString();
            ar.mSummary = source.readString();
            ar.mUserId = source.readString();
            ar.mAnime = source.readParcelable(Anime.class.getClassLoader());
            ar.mCompiledContent = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(source);
            ar.mAnimeTitle = source.readString();
            ar.mUser = source.readParcelable(User.class.getClassLoader());
            return ar;
        }

        @Override
        public AnimeReview[] newArray(final int size) {
            return new AnimeReview[size];
        }
    };

}
