package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.text.TextUtils;

import com.charlesmadere.hummingbird.misc.Constants;
import com.charlesmadere.hummingbird.misc.JsoupUtils;
import com.charlesmadere.hummingbird.misc.MiscUtils;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Type;

public class User implements Hydratable, Parcelable {

    private Data mData;

    // hydrated fields
    private CharSequence mCompiledAbout;


    @Override
    public boolean equals(final Object o) {
        return o instanceof User && getId().equalsIgnoreCase(((User) o).getId());
    }

    @Nullable
    public CharSequence getAbout() {
        return mCompiledAbout;
    }

    @Nullable
    private String getAvatar(final String template) {
        if (TextUtils.isEmpty(mData.mAvatarTemplate)) {
            return null;
        } else {
            return mData.mAvatarTemplate.replaceFirst(Constants.IMAGE_TEMPLATE_STUB, template);
        }
    }

    @Nullable
    public String[] getAvatars() {
        return new String[] { getAvatarThumb(), getAvatarThumbSmall(), getAvatarSmall(),
                getAvatarMedium(), getAvatarOriginal() };
    }

    @Nullable
    public String getAvatarMedium() {
        return getAvatar(Constants.IMAGE_TEMPLATE_MEDIUM);
    }

    @Nullable
    public String getAvatarOriginal() {
        return getAvatar(Constants.IMAGE_TEMPLATE_ORIGINAL);
    }

    @Nullable
    public String getAvatarSmall() {
        return getAvatar(Constants.IMAGE_TEMPLATE_SMALL);
    }

    @Nullable
    public String getAvatarThumb() {
        return getAvatar(Constants.IMAGE_TEMPLATE_THUMB);
    }

    @Nullable
    public String getAvatarThumbSmall() {
        return getAvatar(Constants.IMAGE_TEMPLATE_THUMB_SMALL);
    }

    @Nullable
    public String getBio() {
        return mData.mBio;
    }

    public String getCoverImage() {
        return mData.mCoverImageUrl;
    }

    public String getId() {
        return mData.mId;
    }

    public int getFollowerCount() {
        return mData.mFollowerCount;
    }

    public int getFollowingCount() {
        return mData.mFollowingCount;
    }

    @Nullable
    public String getLocation() {
        return mData.mLocation;
    }

    public RatingType getRatingType() {
        return mData.mRatingType;
    }

    @Nullable
    public String getWaifu() {
        return mData.mWaifu;
    }

    @Nullable
    public String getWaifuCharId() {
        return mData.mWaifuCharId;
    }

    @Nullable
    public WaifuOrHusbando getWaifuOrHusbando() {
        return mData.mWaifuOrHusbando;
    }

    @Nullable
    public String getWaifuSlug() {
        return mData.mWaifuSlug;
    }

    @Nullable
    public String getWebsite() {
        return mData.mWebsite;
    }

    @Nullable
    public String[] getWebsites() {
        if (hasWebsite()) {
            // noinspection ConstantConditions
            return mData.mWebsite.split("\\s+");
        } else {
            return null;
        }
    }

    public boolean hasAbout() {
        return !TextUtils.isEmpty(mCompiledAbout);
    }

    public boolean hasBio() {
        return !TextUtils.isEmpty(mData.mBio);
    }

    public boolean hasCoverImage() {
        return MiscUtils.isValidArtwork(mData.mCoverImageUrl);
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

    public boolean hasLocation() {
        return !TextUtils.isEmpty(mData.mLocation);
    }

    public boolean hasWaifuOrHusbando() {
        return !TextUtils.isEmpty(mData.mWaifu) && !TextUtils.isEmpty(mData.mWaifuCharId) &&
                !TextUtils.isEmpty(mData.mWaifuSlug) && mData.mWaifuOrHusbando != null;
    }

    public boolean hasWebsite() {
        return !TextUtils.isEmpty(mData.mWebsite);
    }

    public boolean hasWebsites() {
        final String[] websites = getWebsites();
        return websites != null && websites.length >= 2;
    }

    @Override
    @WorkerThread
    public void hydrate() {
        if (!TextUtils.isEmpty(mData.mAboutFormatted)) {
            mCompiledAbout = JsoupUtils.parse(mData.mAboutFormatted);
        } else if (!TextUtils.isEmpty(mData.mAbout)) {
            mCompiledAbout = JsoupUtils.parse(mData.mAbout);
        }
    }

    public boolean isAdmin() {
        return mData.mIsAdmin;
    }

    public boolean isFollowed() {
        return mData.mIsFollowed;
    }

    public boolean isPro() {
        return mData.mIsPro;
    }

    public boolean isRatingTypeAdvanced() {
        return mData.mRatingType == RatingType.ADVANCED;
    }

    public boolean isRatingTypeSimple() {
        return mData.mRatingType == RatingType.SIMPLE;
    }

    public void toggleFollowed() {
        mData.mIsFollowed = !mData.mIsFollowed;
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
        dest.writeParcelable(mData, flags);
        TextUtils.writeToParcel(mCompiledAbout, dest, flags);
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(final Parcel source) {
            final User u = new User();
            u.mData = source.readParcelable(Data.class.getClassLoader());
            u.mCompiledAbout = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(source);
            return u;
        }

        @Override
        public User[] newArray(final int size) {
            return new User[size];
        }
    };

    public static final JsonDeserializer<User> JSON_DESERIALIZER = new JsonDeserializer<User>() {
        @Override
        public User deserialize(final JsonElement json, final Type typeOfT,
                final JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();

            if (jsonObject.has("user")) {
                jsonObject = jsonObject.get("user").getAsJsonObject();
            }

            final User user = new User();
            user.mData = context.deserialize(jsonObject, Data.class);
            return user;
        }
    };


    private static class Data implements Parcelable {
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

        @Nullable
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

        public static final Creator<Data> CREATOR = new Creator<Data>() {
            @Override
            public Data createFromParcel(final Parcel source) {
                final Data d = new Data();
                d.mIsAdmin = source.readInt() != 0;
                d.mIsFollowed = source.readInt() != 0;
                d.mIsPro = source.readInt() != 0;
                d.mFollowerCount = source.readInt();
                d.mFollowingCount = source.readInt();
                d.mRatingType = source.readParcelable(RatingType.class.getClassLoader());
                d.mAbout = source.readString();
                d.mAboutFormatted = source.readString();
                d.mAvatarTemplate = source.readString();
                d.mBio = source.readString();
                d.mCoverImageUrl = source.readString();
                d.mId = source.readString();
                d.mLocation = source.readString();
                d.mWaifu = source.readString();
                d.mWaifuCharId = source.readString();
                d.mWaifuSlug = source.readString();
                d.mWebsite = source.readString();
                d.mWaifuOrHusbando = source.readParcelable(WaifuOrHusbando.class.getClassLoader());
                return d;
            }

            @Override
            public Data[] newArray(final int size) {
                return new Data[size];
            }
        };
    }

}
