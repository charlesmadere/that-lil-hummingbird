package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Type;

public abstract class AbsUser implements Parcelable {

    @Nullable
    @SerializedName("bio")
    private String mBio;

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


    public abstract String getAvatar();

    public abstract String getAvatarSmall();

    @Nullable
    public String getBio() {
        return mBio;
    }

    public abstract String getCoverImage();

    public abstract String getId();

    public abstract String getName();

    public abstract Version getVersion();

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

    public boolean hasBio() {
        return !TextUtils.isEmpty(mBio);
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

    public boolean hasWebsite() {
        return !TextUtils.isEmpty(mWebsite);
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    protected void readFromParcel(final Parcel source) {
        mBio = source.readString();
        mWaifu = source.readString();
        mWaifuCharId = source.readString();
        mWaifuSlug = source.readString();
        mWebsite = source.readString();
        mWaifuOrHusbando = source.readParcelable(WaifuOrHusbando.class.getClassLoader());
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeString(mBio);
        dest.writeString(mWaifu);
        dest.writeString(mWaifuCharId);
        dest.writeString(mWaifuSlug);
        dest.writeString(mWebsite);
        dest.writeParcelable(mWaifuOrHusbando, flags);
    }


    public enum Version implements Parcelable {
        V1, V2;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(final Parcel dest, final int flags) {
            dest.writeInt(ordinal());
        }

        public static final Creator<Version> CREATOR = new Creator<Version>() {
            @Override
            public Version createFromParcel(final Parcel source) {
                final int ordinal = source.readInt();
                return values()[ordinal];
            }

            @Override
            public Version[] newArray(final int size) {
                return new Version[size];
            }
        };
    }

    public static final JsonDeserializer<AbsUser> JSON_DESERIALIZER = new JsonDeserializer<AbsUser>() {
        @Override
        public AbsUser deserialize(final JsonElement json, final Type typeOfT,
                final JsonDeserializationContext context) throws JsonParseException {
            final JsonObject jsonObject = json.getAsJsonObject();

            if (jsonObject.has("name")) {
                return context.deserialize(json, UserV1.class);
            } else if (jsonObject.has("id")) {
                return context.deserialize(json, UserV2.class);
            } else {
                throw new JsonParseException("unable to recognize AbsUser type");
            }
        }
    };

}
