package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.charlesmadere.hummingbird.misc.ParcelableUtils;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class User implements Parcelable {

    @Nullable
    @SerializedName("favorites")
    private ArrayList<Favorite> mFavorites;

    @Nullable
    @SerializedName("online")
    private Boolean mOnline;

    @Nullable
    @SerializedName("show_adult_content")
    private Boolean mShowAdultContent;

    @Nullable
    @SerializedName("life_spent_on_anime")
    private Integer mLifeSpentOnAnime;

    @Nullable
    @SerializedName("last_library_update")
    private SimpleDate mLastLibraryUpdate;

    @Nullable
    @SerializedName("avatar")
    private String mAvatar;

    @Nullable
    @SerializedName("avatar_small")
    private String mAvatarSmall;

    @Nullable
    @SerializedName("bio")
    private String mBio;

    @Nullable
    @SerializedName("cover_image")
    private String mCoverImage;

    @SerializedName("name")
    private String mName;

    @SerializedName("url")
    private String mUrl;

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
    @SerializedName("title_language_preference")
    private TitleLanguagePreference mTitleLanguagePreference;

    @Nullable
    @SerializedName("waifu_or_husbando")
    private WaifuOrHusbando mWaifuOrHusbando;


    @Nullable
    public String getAvatar() {
        return mAvatar;
    }

    @Nullable
    public String getAvatarSmall() {
        return mAvatarSmall;
    }

    @Nullable
    public String getBio() {
        return mBio;
    }

    @Nullable
    public String getCoverImage() {
        return mCoverImage;
    }

    @Nullable
    public ArrayList<Favorite> getFavorites() {
        return mFavorites;
    }

    @Nullable
    public SimpleDate getLastLibraryUpdate() {
        return mLastLibraryUpdate;
    }

    @Nullable
    public Integer getLifeSpentOnAnime() {
        return mLifeSpentOnAnime;
    }

    public String getName() {
        return mName;
    }

    @Nullable
    public Boolean getOnlineStatus() {
        return mOnline;
    }

    @Nullable
    public Boolean getShowAdultContentStatus() {
        return mShowAdultContent;
    }

    public String getUrl() {
        return mUrl;
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

    public boolean hasFavorites() {
        return mFavorites != null && !mFavorites.isEmpty();
    }

    public boolean hasHusbando() {
        return hasWaifuOrHusbando() && mWaifuOrHusbando == WaifuOrHusbando.HUSBANDO;
    }

    public boolean hasWaifu() {
        return hasWaifuOrHusbando() && mWaifuOrHusbando == WaifuOrHusbando.WAIFU;
    }

    public boolean hasWaifuOrHusbando() {
        return !TextUtils.isEmpty(mWaifu) && !TextUtils.isEmpty(mWaifuCharId) &&
                !TextUtils.isEmpty(mWaifuSlug) && mWaifuOrHusbando != null;
    }

    public boolean isNsfwContentAllowed() {
        return Boolean.TRUE.equals(mShowAdultContent);
    }

    public boolean isOnline() {
        return Boolean.TRUE.equals(mOnline);
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
        dest.writeTypedList(mFavorites);
        ParcelableUtils.writeBoolean(mOnline, dest);
        ParcelableUtils.writeBoolean(mShowAdultContent, dest);
        ParcelableUtils.writeInteger(mLifeSpentOnAnime, dest);
        dest.writeParcelable(mLastLibraryUpdate, flags);
        dest.writeString(mAvatar);
        dest.writeString(mAvatarSmall);
        dest.writeString(mBio);
        dest.writeString(mCoverImage);
        dest.writeString(mName);
        dest.writeString(mUrl);
        dest.writeString(mWaifu);
        dest.writeString(mWaifuCharId);
        dest.writeString(mWaifuSlug);
        dest.writeString(mWebsite);
        dest.writeParcelable(mTitleLanguagePreference, flags);
        dest.writeParcelable(mWaifuOrHusbando, flags);
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(final Parcel source) {
            final User u = new User();
            u.mFavorites = source.createTypedArrayList(Favorite.CREATOR);
            u.mOnline = ParcelableUtils.readBoolean(source);
            u.mShowAdultContent = ParcelableUtils.readBoolean(source);
            u.mLifeSpentOnAnime = ParcelableUtils.readInteger(source);
            u.mLastLibraryUpdate = source.readParcelable(SimpleDate.class.getClassLoader());
            u.mAvatar = source.readString();
            u.mAvatarSmall = source.readString();
            u.mBio = source.readString();
            u.mCoverImage = source.readString();
            u.mName = source.readString();
            u.mUrl = source.readString();
            u.mWaifu = source.readString();
            u.mWaifuCharId = source.readString();
            u.mWaifuSlug = source.readString();
            u.mWebsite = source.readString();
            u.mTitleLanguagePreference = source.readParcelable(SimpleDate.class.getClassLoader());
            u.mWaifuOrHusbando = source.readParcelable(WaifuOrHusbando.class.getClassLoader());
            return u;
        }

        @Override
        public User[] newArray(final int size) {
            return new User[size];
        }
    };

}
