package com.charlesmadere.hummingbird.models;

import android.content.res.Resources;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.charlesmadere.hummingbird.misc.ParcelableUtils;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Type;

public abstract class AbsAnime implements Parcelable {

    @Nullable
    @SerializedName("age_rating")
    private AgeRating mAgeRating;

    @SerializedName("community_rating")
    private float mCommunityRating;

    @Nullable
    @SerializedName("episode_count")
    private Integer mEpisodeCount;

    @Nullable
    @SerializedName("episode_length")
    private Integer mEpisodeLength;

    @Nullable
    @SerializedName("show_type")
    private ShowType mShowType;

    @SerializedName("id")
    private String mId;

    @Nullable
    @SerializedName("synopsis")
    private String mSynopsis;


    @Nullable
    public AgeRating getAgeRating() {
        return mAgeRating;
    }

    public float getCommunityRating() {
        return mCommunityRating;
    }

    @Nullable
    public Integer getEpisodeCount() {
        return mEpisodeCount;
    }

    @Nullable
    public Integer getEpisodeLength() {
        return mEpisodeLength;
    }

    @Nullable
    public abstract SimpleDate getFinishedAiringDate();

    public abstract String getGenresString(final Resources res);

    public String getId() {
        return mId;
    }

    public abstract String getImage();

    @Nullable
    public ShowType getShowType() {
        return mShowType;
    }

    @Nullable
    public abstract SimpleDate getStartedAiringDate();

    @Nullable
    public String getSynopsis() {
        return mSynopsis;
    }

    public abstract String getThumb();

    public abstract String getTitle();

    public abstract Version getVersion();

    public boolean hasAgeRating() {
        return mAgeRating != null;
    }

    public boolean hasEpisodeCount() {
        return mEpisodeCount != null;
    }

    public boolean hasFinishedAiringDate() {
        return getFinishedAiringDate() != null;
    }

    public abstract boolean hasGenres();

    public boolean hasShowType() {
        return mShowType != null;
    }

    public boolean hasStartedAiringDate() {
        return getStartedAiringDate() != null;
    }

    public boolean hasSynopsis() {
        return !TextUtils.isEmpty(mSynopsis);
    }

    @Override
    public String toString() {
        return getTitle();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    protected void readFromParcel(final Parcel source) {
        mAgeRating = source.readParcelable(AgeRating.class.getClassLoader());
        mCommunityRating = source.readFloat();
        mEpisodeCount = ParcelableUtils.readInteger(source);
        mEpisodeLength = ParcelableUtils.readInteger(source);
        mShowType = source.readParcelable(ShowType.class.getClassLoader());
        mId = source.readString();
        mSynopsis = source.readString();
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeParcelable(mAgeRating, flags);
        dest.writeFloat(mCommunityRating);
        ParcelableUtils.writeInteger(mEpisodeCount, dest);
        ParcelableUtils.writeInteger(mEpisodeLength, dest);
        dest.writeParcelable(mShowType, flags);
        dest.writeString(mId);
        dest.writeString(mSynopsis);
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


    public static final JsonDeserializer<AbsAnime> JSON_DESERIALIZER = new JsonDeserializer<AbsAnime>() {
        @Override
        public AbsAnime deserialize(final JsonElement json, final Type typeOfT,
                final JsonDeserializationContext context) throws JsonParseException {
            final JsonObject jsonObject = json.getAsJsonObject();

            if (jsonObject.has("title")) {
                return context.deserialize(jsonObject, AnimeV1.class);
            } else if (jsonObject.has("canonical_title")) {
                return context.deserialize(json, AnimeV2.class);
            } else {
                throw new JsonParseException("unable to recognize AbsAnime version");
            }
        }
    };

}
