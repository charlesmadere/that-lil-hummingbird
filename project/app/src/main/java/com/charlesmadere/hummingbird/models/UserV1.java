package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.charlesmadere.hummingbird.misc.ParcelableUtils;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UserV1 extends AbsUser implements Parcelable {

    @Nullable
    @SerializedName("favorites")
    private ArrayList<Favorite> mFavorites;

    @Nullable
    @SerializedName("online")
    private Boolean mOnline;

    @Nullable
    @SerializedName("show_adult_content")
    private Boolean mShowAdultContent;

    @SerializedName("life_spent_on_anime")
    private long mLifeSpentOnAnime;

    @SerializedName("last_library_update")
    private SimpleDate mLastLibraryUpdate;

    @SerializedName("avatar")
    private String mAvatar;

    @SerializedName("avatar_small")
    private String mAvatarSmall;

    @SerializedName("cover_image")
    private String mCoverImage;

    @SerializedName("name")
    private String mName;

    @SerializedName("url")
    private String mUrl;

    @Nullable
    @SerializedName("title_language_preference")
    private TitleLanguagePreference mTitleLanguagePreference;


    @Override
    public String getAvatar() {
        return mAvatar;
    }

    public String getAvatarSmall() {
        return mAvatarSmall;
    }

    @Override
    public String getCover() {
        return mCoverImage;
    }

    @Nullable
    public ArrayList<Favorite> getFavorites() {
        return mFavorites;
    }

    @Override
    public String getId() {
        return mName;
    }

    public SimpleDate getLastLibraryUpdate() {
        return mLastLibraryUpdate;
    }

    public long getLifeSpentOnAnimeMinutes() {
        return mLifeSpentOnAnime;
    }

    public long getLifeSpentOnAnimeSeconds() {
        return getLifeSpentOnAnimeMinutes() * 60L;
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

    @Override
    public Version getVersion() {
        return Version.V1;
    }

    public boolean hasFavorites() {
        return mFavorites != null && !mFavorites.isEmpty();
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
        dest.writeLong(mLifeSpentOnAnime);
        dest.writeParcelable(mLastLibraryUpdate, flags);
        dest.writeString(mAvatar);
        dest.writeString(mAvatarSmall);
        dest.writeString(mCoverImage);
        dest.writeString(mName);
        dest.writeString(mUrl);
        dest.writeParcelable(mTitleLanguagePreference, flags);
    }

    public static final Creator<UserV1> CREATOR = new Creator<UserV1>() {
        @Override
        public UserV1 createFromParcel(final Parcel source) {
            final UserV1 u = new UserV1();
            u.mFavorites = source.createTypedArrayList(Favorite.CREATOR);
            u.mOnline = ParcelableUtils.readBoolean(source);
            u.mShowAdultContent = ParcelableUtils.readBoolean(source);
            u.mLifeSpentOnAnime = source.readLong();
            u.mLastLibraryUpdate = source.readParcelable(SimpleDate.class.getClassLoader());
            u.mAvatar = source.readString();
            u.mAvatarSmall = source.readString();
            u.mCoverImage = source.readString();
            u.mName = source.readString();
            u.mUrl = source.readString();
            u.mTitleLanguagePreference = source.readParcelable(SimpleDate.class.getClassLoader());
            return u;
        }

        @Override
        public UserV1[] newArray(final int size) {
            return new UserV1[size];
        }
    };

}
