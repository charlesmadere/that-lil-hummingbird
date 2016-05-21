package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

public class User implements Parcelable {

    private static final String AVATAR_TEMPLATE_STUB = "\\{size\\}";
    private static final String AVATAR_TEMPLATE_SMALL = "small";
    private static final String AVATAR_TEMPLATE_THUMB = "thumb";
    private static final String AVATAR_TEMPLATE_THUMB_SMALL = "thumb_small";

    @SerializedName("is_admin")
    private boolean mIsAdmin;

    @SerializedName("is_followed")
    private boolean mIsFollowed;

    @SerializedName("is_pro")
    private boolean mIsPro;

    @SerializedName("follower_count")
    private int mFollowerCount;

    @SerializedName("following_count")
    private int mFollowingCount;

    @SerializedName("rating_type")
    private RatingType mRatingType;

    @Nullable
    @SerializedName("about")
    private String mAbout;

    @Nullable
    @SerializedName("about_formatted")
    private String mAboutFormatted;

    @SerializedName("avatar_template")
    private String mAvatarTemplate;

    @Nullable
    @SerializedName("bio")
    private String mBio;

    @SerializedName("cover_image_url")
    private String mCoverImageUrl;

    @SerializedName("id")
    private String mId;

    @Nullable
    @SerializedName("location")
    private String mLocation;

    @Nullable
    @SerializedName("waifu")
    private String mWaifu;

    @Nullable
    @SerializedName("waifu_char_id")
    private String mWaifuCharId;

    @Nullable
    @SerializedName("waifu_slug")
    private String mWaifuSlug;

    @Nullable
    @SerializedName("website")
    private String mWebsite;

    @Nullable
    @SerializedName("waifu_or_husbando")
    private WaifuOrHusbando mWaifuOrHusbando;


    @Nullable
    public String getAbout() {
        return mAbout;
    }

    @Nullable
    public String getAboutFormatted() {
        return mAboutFormatted;
    }

    public String getAvatarSmallest() {
        return mAvatarTemplate.replaceFirst(AVATAR_TEMPLATE_STUB, AVATAR_TEMPLATE_THUMB_SMALL);
    }

    public String getAvatarLargest() {
        return mAvatarTemplate.replaceFirst(AVATAR_TEMPLATE_STUB, AVATAR_TEMPLATE_THUMB);
    }

    @Nullable
    public String getBio() {
        return mBio;
    }

    public String getCoverImageUrl() {
        return mCoverImageUrl;
    }

    public String getId() {
        return mId;
    }

    public int getFollowerCount() {
        return mFollowerCount;
    }

    public int getFollowingCount() {
        return mFollowingCount;
    }

    @Nullable
    public String getLocation() {
        return mLocation;
    }

    public RatingType getRatingType() {
        return mRatingType;
    }

    @Nullable
    public String getWaifu() {
        return mWaifu;
    }

    @Nullable
    public String getWaifuCharId() {
        return mWaifuCharId;
    }

    @Nullable
    public WaifuOrHusbando getWaifuOrHusbando() {
        return mWaifuOrHusbando;
    }

    @Nullable
    public String getWaifuSlug() {
        return mWaifuSlug;
    }

    @Nullable
    public String getWebsite() {
        return mWebsite;
    }

    public boolean hasAbout() {
        return !TextUtils.isEmpty(mAbout);
    }

    public boolean hasAboutFormatted() {
        return !TextUtils.isEmpty(mAboutFormatted);
    }

    public boolean hasBio() {
        return !TextUtils.isEmpty(mBio);
    }

    public boolean hasHusbando() {
        return hasWaifuOrHusbando() && mWaifuOrHusbando == WaifuOrHusbando.HUSBANDO;
    }

    public boolean hasLocation() {
        return !TextUtils.isEmpty(mLocation);
    }

    public boolean hasWaifu() {
        return hasWaifuOrHusbando() && mWaifuOrHusbando == WaifuOrHusbando.WAIFU;
    }

    public boolean hasWaifuOrHusbando() {
        return !TextUtils.isEmpty(mWaifu) && !TextUtils.isEmpty(mWaifuCharId) &&
                !TextUtils.isEmpty(mWaifuSlug) && mWaifuOrHusbando != null;
    }

    public boolean hasWebsite() {
        return !TextUtils.isEmpty(mWebsite);
    }

    public boolean isAdmin() {
        return mIsAdmin;
    }

    public boolean isFollowed() {
        return mIsFollowed;
    }

    public boolean isPro() {
        return mIsPro;
    }

    public boolean isRatingTypeAdvanced() {
        return mRatingType == RatingType.ADVANCED;
    }

    public boolean isRatingTypeSimple() {
        return mRatingType == RatingType.SIMPLE;
    }

    @Override
    public String toString() {
        return getId();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeInt(mIsAdmin ? 1 : 0);
        dest.writeInt(mIsFollowed ? 1 : 0);
        dest.writeInt(mIsPro ? 1 : 0);
        dest.writeInt(mFollowerCount);
        dest.writeInt(mFollowingCount);
        dest.writeParcelable(mRatingType, flags);
        dest.writeString(mAbout);
        dest.writeString(mAboutFormatted);
        dest.writeString(mAvatarTemplate);
        dest.writeString(mBio);
        dest.writeString(mCoverImageUrl);
        dest.writeString(mId);
        dest.writeString(mLocation);
        dest.writeString(mWaifu);
        dest.writeString(mWaifuCharId);
        dest.writeString(mWaifuSlug);
        dest.writeString(mWebsite);
        dest.writeParcelable(mWaifuOrHusbando, flags);
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(final Parcel source) {
            final User u = new User();
            u.mIsAdmin = source.readInt() != 0;
            u.mIsFollowed = source.readInt() != 0;
            u.mIsPro = source.readInt() != 0;
            u.mFollowerCount = source.readInt();
            u.mFollowingCount = source.readInt();
            u.mRatingType = source.readParcelable(RatingType.class.getClassLoader());
            u.mAbout = source.readString();
            u.mAboutFormatted = source.readString();
            u.mAvatarTemplate = source.readString();
            u.mBio = source.readString();
            u.mCoverImageUrl = source.readString();
            u.mId = source.readString();
            u.mLocation = source.readString();
            u.mWaifu = source.readString();
            u.mWaifuCharId = source.readString();
            u.mWaifuSlug = source.readString();
            u.mWebsite = source.readString();
            u.mWaifuOrHusbando = source.readParcelable(WaifuOrHusbando.class.getClassLoader());
            return u;
        }

        @Override
        public User[] newArray(final int size) {
            return new User[size];
        }
    };


    public enum RatingType implements Parcelable {
        @SerializedName("advanced")
        ADVANCED,

        @SerializedName("simple")
        SIMPLE;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(final Parcel dest, final int flags) {
            dest.writeInt(ordinal());
        }

        public static final Creator<RatingType> CREATOR = new Creator<RatingType>() {
            @Override
            public RatingType createFromParcel(final Parcel source) {
                final int ordinal = source.readInt();
                return values()[ordinal];
            }

            @Override
            public RatingType[] newArray(final int size) {
                return new RatingType[size];
            }
        };
    }

}
