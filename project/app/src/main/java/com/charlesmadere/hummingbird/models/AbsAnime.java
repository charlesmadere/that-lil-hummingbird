package com.charlesmadere.hummingbird.models;

import android.content.res.Resources;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.text.TextUtils;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.misc.ParcelableUtils;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Type;

public abstract class AbsAnime implements Parcelable {

    @SerializedName("age_rating")
    protected AgeRating mAgeRating;

    @SerializedName("community_rating")
    protected float mCommunityRating;

    @Nullable
    @SerializedName("episode_count")
    protected Integer mEpisodeCount;

    @SerializedName("episode_length")
    protected int mEpisodeLength;

    @SerializedName("show_type")
    protected ShowType mShowType;

    @SerializedName("cover_image")
    protected String mCoverImage;

    @SerializedName("id")
    protected String mId;

    @Nullable
    @SerializedName("synopsis")
    protected String mSynopsis;


    @Nullable
    public AgeRating getAgeRating() {
        return mAgeRating;
    }

    public float getCommunityRating() {
        return mCommunityRating;
    }

    public String getCoverImage() {
        return mCoverImage;
    }

    @Nullable
    public Integer getEpisodeCount() {
        return mEpisodeCount;
    }

    public int getEpisodeLength() {
        return mEpisodeLength;
    }

    @Nullable
    public abstract SimpleDate getFinishedAiringDate();

    public abstract String getGenresString(final Resources res);

    public String getId() {
        return mId;
    }

    public ShowType getShowType() {
        return mShowType;
    }

    @Nullable
    public abstract SimpleDate getStartedAiringDate();

    @Nullable
    public String getSynopsis() {
        return mSynopsis;
    }

    public abstract String getTitle();

    public abstract Version getVersion();

    public boolean hasAgeRating() {
        return mAgeRating != null;
    }

    public boolean hasEpisodeCount() {
        return mEpisodeCount != null;
    }

    public abstract boolean hasGenres();

    public boolean hasFinishedAiringDate() {
        return getFinishedAiringDate() != null;
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
        mEpisodeLength = source.readInt();
        mShowType = source.readParcelable(ShowType.class.getClassLoader());
        mCoverImage = source.readString();
        mId = source.readString();
        mSynopsis = source.readString();
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeParcelable(mAgeRating, flags);
        dest.writeFloat(mCommunityRating);
        ParcelableUtils.writeInteger(mEpisodeCount, dest);
        dest.writeInt(mEpisodeLength);
        dest.writeParcelable(mShowType, flags);
        dest.writeString(mCoverImage);
        dest.writeString(mId);
        dest.writeString(mSynopsis);
    }


    public enum ShowType implements Parcelable {
        @SerializedName("Movie")
        MOVIE(R.string.movie),

        @SerializedName("Music")
        MUSIC(R.string.music),

        @SerializedName("ONA")
        ONA(R.string.ona),

        @SerializedName("OVA")
        OVA(R.string.ova),

        @SerializedName("Special")
        SPECIAL(R.string.special),

        @SerializedName("TV")
        TV(R.string.tv);

        private final int mTextResId;


        ShowType(@StringRes final int textResId) {
            mTextResId = textResId;
        }

        @StringRes
        public int getTextResId() {
            return mTextResId;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(final Parcel dest, final int flags) {
            dest.writeInt(ordinal());
        }

        public static final Creator<ShowType> CREATOR = new Creator<ShowType>() {
            @Override
            public ShowType createFromParcel(final Parcel source) {
                final int ordinal = source.readInt();
                return ShowType.values()[ordinal];
            }

            @Override
            public ShowType[] newArray(final int size) {
                return new ShowType[size];
            }
        };
    }


    public enum Version implements Parcelable {
        V1, V2, V3;

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

            if (jsonObject.has("anime")) {
                return context.deserialize(jsonObject, AnimeV2.class);
            } else {
                return context.deserialize(json, AnimeV1.class);
            }
        }
    };

}
