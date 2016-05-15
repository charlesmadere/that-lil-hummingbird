package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

public class UserV2 extends AbsUser implements Parcelable {

    private static final String AVATAR_TEMPLATE_STUB = "\\{size\\}";
    private static final String AVATAR_TEMPLATE_SMALL = "small";
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

    @SerializedName("cover_image_url")
    private String mCoverImageUrl;

    @SerializedName("id")
    private String mId;

    @Nullable
    @SerializedName("location")
    private String mLocation;


    @Nullable
    public String getAbout() {
        return mAbout;
    }

    @Nullable
    public String getAboutFormatted() {
        return mAboutFormatted;
    }

    @Override
    public String getAvatar() {
        return mAvatarTemplate.replaceFirst(AVATAR_TEMPLATE_STUB, AVATAR_TEMPLATE_THUMB_SMALL);
    }

    @Override
    public String getAvatarSmall() {
        return mAvatarTemplate.replaceFirst(AVATAR_TEMPLATE_STUB, AVATAR_TEMPLATE_SMALL);
    }

    @Override
    public String getCoverImage() {
        return mCoverImageUrl;
    }

    public int getFollowerCount() {
        return mFollowerCount;
    }

    public int getFollowingCount() {
        return mFollowingCount;
    }

    @Override
    public String getId() {
        return mId;
    }

    @Nullable
    public String getLocation() {
        return mLocation;
    }

    @Override
    public String getName() {
        return mId;
    }

    public RatingType getRatingType() {
        return mRatingType;
    }

    @Override
    public Version getVersion() {
        return Version.V2;
    }

    public boolean hasAbout() {
        return !TextUtils.isEmpty(mAbout);
    }

    public boolean hasAboutFormatted() {
        return !TextUtils.isEmpty(mAboutFormatted);
    }

    public boolean hasLocation() {
        return !TextUtils.isEmpty(mLocation);
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
    protected void readFromParcel(final Parcel source) {
        super.readFromParcel(source);
        mIsAdmin = source.readInt() != 0;
        mIsFollowed = source.readInt() != 0;
        mIsPro = source.readInt() != 0;
        mFollowerCount = source.readInt();
        mFollowingCount = source.readInt();
        mRatingType = source.readParcelable(RatingType.class.getClassLoader());
        mAbout = source.readString();
        mAboutFormatted = source.readString();
        mAvatarTemplate = source.readString();
        mCoverImageUrl = source.readString();
        mId = source.readString();
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(mIsAdmin ? 1 : 0);
        dest.writeInt(mIsFollowed ? 1 : 0);
        dest.writeInt(mIsPro ? 1 : 0);
        dest.writeInt(mFollowerCount);
        dest.writeInt(mFollowingCount);
        dest.writeParcelable(mRatingType, flags);
        dest.writeString(mAbout);
        dest.writeString(mAboutFormatted);
        dest.writeString(mAvatarTemplate);
        dest.writeString(mCoverImageUrl);
        dest.writeString(mId);
    }

    public static final Creator<UserV2> CREATOR = new Creator<UserV2>() {
        @Override
        public UserV2 createFromParcel(final Parcel source) {
            final UserV2 u = new UserV2();
            u.readFromParcel(source);
            return u;
        }

        @Override
        public UserV2[] newArray(final int size) {
            return new UserV2[size];
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
