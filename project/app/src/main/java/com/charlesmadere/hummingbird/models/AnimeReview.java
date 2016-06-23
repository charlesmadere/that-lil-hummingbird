package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
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

    @SerializedName("content")
    private String mContent;

    @SerializedName("id")
    private String mId;

    @SerializedName("summary")
    private String mSummary;

    @SerializedName("user_id")
    private String mUserId;

    // hydrated fields
    private CharSequence mCompiledContent;
    private String mAnimeTitle;
    private User mUser;


    @Override
    public boolean equals(final Object o) {
        return o instanceof AnimeReview && mId.equalsIgnoreCase(((AnimeReview) o).getId());
    }

    public String getAnimeTitle() {
        return mAnimeTitle;
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

    public boolean hydrate(final AnimeDigest animeDigest) {
        mCompiledContent = JsoupUtils.parse(mContent);
        mAnimeTitle = animeDigest.getTitle();

        for (final User user : animeDigest.getUsers()) {
            if (mUserId.equalsIgnoreCase(user.getId())) {
                mUser = user;
                break;
            }
        }

        return mUser != null;
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
        dest.writeString(mContent);
        dest.writeString(mId);
        dest.writeString(mSummary);
        dest.writeString(mUserId);
        TextUtils.writeToParcel(mCompiledContent, dest, flags);
        dest.writeString(mAnimeTitle);
        dest.writeParcelable(mUser, flags);
    }

    public static final Creator<AnimeReview> CREATOR = new Creator<AnimeReview>() {
        @Override
        public AnimeReview createFromParcel(final Parcel source) {
            final AnimeReview r = new AnimeReview();
            r.mLiked = source.readInt() != 0;
            r.mRating = source.readFloat();
            r.mRatingAnimation = source.readFloat();
            r.mRatingCharacters = source.readFloat();
            r.mRatingEnjoyment = source.readFloat();
            r.mRatingSound = source.readFloat();
            r.mRatingStory = source.readFloat();
            r.mPositiveVotes = source.readInt();
            r.mTotalVotes = source.readInt();
            r.mContent = source.readString();
            r.mId = source.readString();
            r.mSummary = source.readString();
            r.mUserId = source.readString();
            r.mCompiledContent = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(source);
            r.mAnimeTitle = source.readString();
            r.mUser = source.readParcelable(User.class.getClassLoader());
            return r;
        }

        @Override
        public AnimeReview[] newArray(final int size) {
            return new AnimeReview[size];
        }
    };

}
