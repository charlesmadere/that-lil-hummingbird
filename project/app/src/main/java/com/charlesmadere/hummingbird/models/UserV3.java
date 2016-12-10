package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.charlesmadere.hummingbird.misc.JsoupUtils;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UserV3 implements DataObject, Hydratable, Parcelable {

    @SerializedName("attributes")
    private Attributes mAttributes;

    @SerializedName("type")
    private DataType mDataType;

    @SerializedName("links")
    private Links mLinks;

    @Nullable
    @SerializedName("relationships")
    private Relationships mRelationships;

    @SerializedName("id")
    private String mId;


    @Override
    public boolean equals(final Object o) {
        return o instanceof UserV3 && mId.equals(((UserV3) o).getId());
    }

    @Nullable
    public CharSequence getAbout() {
        return mAttributes.mAboutCompiled;
    }

    @Nullable
    public Image getAvatar() {
        return mAttributes.mAvatar;
    }

    @Nullable
    public String getBio() {
        return mAttributes.mBio;
    }

    @Nullable
    public SimpleDate getBirthday() {
        return mAttributes.mBirthday;
    }

    public int getCommentsCount() {
        return mAttributes.mCommentsCount;
    }

    @Nullable
    public String getCountry() {
        return mAttributes.mCountry;
    }

    @Nullable
    public Image getCoverImage() {
        return mAttributes.mCoverImage;
    }

    @Nullable
    public SimpleDate getCreatedAt() {
        return mAttributes.mCreatedAt;
    }

    @Override
    public DataType getDataType() {
        return mDataType;
    }

    @Nullable
    public String getFacebookId() {
        return mAttributes.mFacebookId;
    }

    public int getFavoritesCount() {
        return mAttributes.mFavoritesCount;
    }

    public int getFollowersCount() {
        return mAttributes.mFollowersCount;
    }

    public int getFollowingCount() {
        return mAttributes.mFollowingCount;
    }

    @Nullable
    public String getGender() {
        return mAttributes.mGender;
    }

    @Override
    public String getId() {
        return mId;
    }

    public int getLifeSpentOnAnime() {
        return mAttributes.mLifeSpentOnAnime;
    }

    public int getLikesGivenCount() {
        return mAttributes.mLikesGivenCount;
    }

    public int getLikesReceivedCount() {
        return mAttributes.mLikesReceivedCount;
    }

    public Links getLinks() {
        return mLinks;
    }

    @Nullable
    public String getLocation() {
        return mAttributes.mLocation;
    }

    public String getName() {
        return mAttributes.mName;
    }

    @Nullable
    public ArrayList<String> getPastNames() {
        return mAttributes.mPastNames;
    }

    public int getPostsCount() {
        return mAttributes.mPostsCount;
    }

    @Nullable
    public SimpleDate getProExpiresAt() {
        return mAttributes.mProExpiresAt;
    }

    public int getRatingsCount() {
        return mAttributes.mRatingsCount;
    }

    @Nullable
    public Relationships getRelationships() {
        return mRelationships;
    }

    public int getReviewsCount() {
        return mAttributes.mReviewsCount;
    }

    @Nullable
    public SimpleDate getUpdatedAt() {
        return mAttributes.mUpdatedAt;
    }

    @Nullable
    public WaifuOrHusbando getWaifuOrHusbando() {
        return mAttributes.mWaifuOrHusbando;
    }

    @Nullable
    public String getWebsite() {
        return mAttributes.mWebsite;
    }

    @Override
    public int hashCode() {
        return mId.hashCode();
    }

    @Override
    public void hydrate() {
        if (!TextUtils.isEmpty(mAttributes.mAboutFormatted)) {
            mAttributes.mAboutCompiled = JsoupUtils.parse(mAttributes.mAboutFormatted);
        } else if (!TextUtils.isEmpty(mAttributes.mAbout)) {
            mAttributes.mAboutCompiled = JsoupUtils.parse(mAttributes.mAbout);
        }
    }

    public boolean isPro() {
        return mAttributes.mProExpiresAt != null && mAttributes.mProExpiresAt.isInTheFuture();
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
        dest.writeParcelable(mAttributes, flags);
        dest.writeParcelable(mDataType, flags);
        dest.writeParcelable(mLinks, flags);
        dest.writeParcelable(mRelationships, flags);
        dest.writeString(mId);
    }

    public static final Creator<UserV3> CREATOR = new Creator<UserV3>() {
        @Override
        public UserV3 createFromParcel(final Parcel source) {
            final UserV3 u = new UserV3();
            u.mAttributes = source.readParcelable(Attributes.class.getClassLoader());
            u.mDataType = source.readParcelable(DataType.class.getClassLoader());
            u.mLinks = source.readParcelable(Links.class.getClassLoader());
            u.mRelationships = source.readParcelable(Relationships.class.getClassLoader());
            u.mId = source.readString();
            return u;
        }

        @Override
        public UserV3[] newArray(final int size) {
            return new UserV3[size];
        }
    };


    private static class Attributes implements Parcelable {
        @Nullable
        @SerializedName("pastNames")
        private ArrayList<String> mPastNames;

        @Nullable
        @SerializedName("avatar")
        private Image mAvatar;

        @Nullable
        @SerializedName("coverImage")
        private Image mCoverImage;

        @SerializedName("commentsCount")
        private int mCommentsCount;

        @SerializedName("favoritesCount")
        private int mFavoritesCount;

        @SerializedName("followersCount")
        private int mFollowersCount;

        @SerializedName("followingCount")
        private int mFollowingCount;

        @SerializedName("lifeSpentOnAnime")
        private int mLifeSpentOnAnime;

        @SerializedName("likesGivenCount")
        private int mLikesGivenCount;

        @SerializedName("likesReceivedCount")
        private int mLikesReceivedCount;

        @SerializedName("postsCount")
        private int mPostsCount;

        @SerializedName("ratingsCount")
        private int mRatingsCount;

        @SerializedName("reviewsCount")
        private int mReviewsCount;

        @Nullable
        @SerializedName("birthday")
        private SimpleDate mBirthday;

        @Nullable
        @SerializedName("createdAt")
        private SimpleDate mCreatedAt;

        @Nullable
        @SerializedName("proExpiresAt")
        private SimpleDate mProExpiresAt;

        @Nullable
        @SerializedName("updatedAt")
        private SimpleDate mUpdatedAt;

        @Nullable
        @SerializedName("about")
        private String mAbout;

        @Nullable
        @SerializedName("aboutFormatted")
        private String mAboutFormatted;

        @Nullable
        @SerializedName("bio")
        private String mBio;

        @Nullable
        @SerializedName("country")
        private String mCountry;

        @Nullable
        @SerializedName("facebookId")
        private String mFacebookId;

        @Nullable
        @SerializedName("gender")
        private String mGender;

        @Nullable
        @SerializedName("location")
        private String mLocation;

        @SerializedName("name")
        private String mName;

        @Nullable
        @SerializedName("website")
        private String mWebsite;

        @Nullable
        @SerializedName("waifuOrHusbando")
        private WaifuOrHusbando mWaifuOrHusbando;

        // hydrated fields
        private CharSequence mAboutCompiled;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(final Parcel dest, final int flags) {
            dest.writeStringList(mPastNames);
            dest.writeParcelable(mAvatar, flags);
            dest.writeParcelable(mCoverImage, flags);
            dest.writeInt(mCommentsCount);
            dest.writeInt(mFavoritesCount);
            dest.writeInt(mFollowersCount);
            dest.writeInt(mFollowingCount);
            dest.writeInt(mLifeSpentOnAnime);
            dest.writeInt(mLikesGivenCount);
            dest.writeInt(mLikesReceivedCount);
            dest.writeInt(mPostsCount);
            dest.writeInt(mRatingsCount);
            dest.writeInt(mReviewsCount);
            dest.writeParcelable(mBirthday, flags);
            dest.writeParcelable(mCreatedAt, flags);
            dest.writeParcelable(mProExpiresAt, flags);
            dest.writeParcelable(mUpdatedAt, flags);
            dest.writeString(mAbout);
            dest.writeString(mAboutFormatted);
            dest.writeString(mBio);
            dest.writeString(mCountry);
            dest.writeString(mFacebookId);
            dest.writeString(mGender);
            dest.writeString(mLocation);
            dest.writeString(mName);
            dest.writeString(mWebsite);
            dest.writeParcelable(mWaifuOrHusbando, flags);
            TextUtils.writeToParcel(mAboutCompiled, dest, flags);
        }

        public static final Creator<Attributes> CREATOR = new Creator<Attributes>() {
            @Override
            public Attributes createFromParcel(final Parcel source) {
                final Attributes a = new Attributes();
                a.mPastNames = source.createStringArrayList();
                a.mAvatar = source.readParcelable(Image.class.getClassLoader());
                a.mCoverImage = source.readParcelable(Image.class.getClassLoader());
                a.mCommentsCount = source.readInt();
                a.mFavoritesCount = source.readInt();
                a.mFollowersCount = source.readInt();
                a.mFollowingCount = source.readInt();
                a.mLifeSpentOnAnime = source.readInt();
                a.mLikesGivenCount = source.readInt();
                a.mLikesReceivedCount = source.readInt();
                a.mPostsCount = source.readInt();
                a.mRatingsCount = source.readInt();
                a.mReviewsCount = source.readInt();
                a.mBirthday = source.readParcelable(SimpleDate.class.getClassLoader());
                a.mCreatedAt = source.readParcelable(SimpleDate.class.getClassLoader());
                a.mProExpiresAt = source.readParcelable(SimpleDate.class.getClassLoader());
                a.mUpdatedAt = source.readParcelable(SimpleDate.class.getClassLoader());
                a.mAbout = source.readString();
                a.mAboutFormatted = source.readString();
                a.mBio = source.readString();
                a.mCountry = source.readString();
                a.mFacebookId = source.readString();
                a.mGender = source.readString();
                a.mLocation = source.readString();
                a.mName = source.readString();
                a.mWebsite = source.readString();
                a.mWaifuOrHusbando = source.readParcelable(WaifuOrHusbando.class.getClassLoader());
                a.mAboutCompiled = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(source);
                return a;
            }

            @Override
            public Attributes[] newArray(final int size) {
                return new Attributes[size];
            }
        };
    }

}
