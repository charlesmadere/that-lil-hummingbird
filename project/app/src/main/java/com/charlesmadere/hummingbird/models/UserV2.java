package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

public class UserV2 extends AbsUser implements Parcelable {

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
    public String getAbout() {
        return mAbout;
    }

    @Nullable
    public String getAboutFormatted() {
        return mAboutFormatted;
    }

    @Override
    public String getAvatar() {
        return mAvatarTemplate;
    }

    @Override
    public String getAvatarSmall() {
        return mAvatarTemplate;
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

    @Override
    public String getName() {
        return mId;
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

    public boolean isAdmin() {
        return mIsAdmin;
    }

    public boolean isFollowed() {
        return mIsFollowed;
    }

    public boolean isPro() {
        return mIsPro;
    }

    @Override
    protected void readFromParcel(final Parcel source) {
        super.readFromParcel(source);
        mIsAdmin = source.readInt() != 0;
        mIsFollowed = source.readInt() != 0;
        mIsPro = source.readInt() != 0;
        mFollowerCount = source.readInt();
        mFollowingCount = source.readInt();
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

}
