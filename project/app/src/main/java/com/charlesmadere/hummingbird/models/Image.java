package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.charlesmadere.hummingbird.misc.MiscUtils;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.SerializedName;

import java.lang.*;
import java.lang.reflect.Type;

import static com.charlesmadere.hummingbird.misc.Constants.LARGE;
import static com.charlesmadere.hummingbird.misc.Constants.MEDIUM;
import static com.charlesmadere.hummingbird.misc.Constants.ORIGINAL;
import static com.charlesmadere.hummingbird.misc.Constants.SMALL;
import static com.charlesmadere.hummingbird.misc.Constants.TINY;

public class Image implements Parcelable {

    @Nullable
    @SerializedName(LARGE)
    private final String mLarge;

    @Nullable
    @SerializedName(MEDIUM)
    private final String mMedium;

    @Nullable
    @SerializedName(ORIGINAL)
    private final String mOriginal;

    @Nullable
    @SerializedName(SMALL)
    private final String mSmall;

    @Nullable
    @SerializedName(TINY)
    private final String mTiny;


    private Image(final String large, final String medium, final String original,
            final String small, final String tiny) {
        mLarge = large;
        mMedium = medium;
        mOriginal = original;
        mSmall = small;
        mTiny = tiny;
    }

    @Nullable
    public String getLarge() {
        return mLarge;
    }

    @Nullable
    public String getMedium() {
        return mMedium;
    }

    @Nullable
    public String getOriginal() {
        return mOriginal;
    }

    @Nullable
    public String getSmall() {
        return mSmall;
    }

    @Nullable
    public String getTiny() {
        return mTiny;
    }

    public boolean hasLarge() {
        return MiscUtils.isValidArtwork(mLarge);
    }

    public boolean hasMedium() {
        return MiscUtils.isValidArtwork(mMedium);
    }

    public boolean hasOriginal() {
        return MiscUtils.isValidArtwork(mOriginal);
    }

    public boolean hasSmall() {
        return MiscUtils.isValidArtwork(mSmall);
    }

    public boolean hasTiny() {
        return MiscUtils.isValidArtwork(mTiny);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeString(mLarge);
        dest.writeString(mMedium);
        dest.writeString(mOriginal);
        dest.writeString(mSmall);
        dest.writeString(mTiny);
    }

    public static final Creator<Image> CREATOR = new Creator<Image>() {
        @Override
        public Image createFromParcel(final Parcel source) {
            return new Image(source.readString(), source.readString(), source.readString(),
                    source.readString(), source.readString());
        }

        @Override
        public Image[] newArray(final int size) {
            return new Image[size];
        }
    };

    public static final JsonDeserializer<Image> JSON_DESERIALIZER = new JsonDeserializer<Image>() {
        @Override
        public Image deserialize(final JsonElement json, final Type typeOfT,
                final JsonDeserializationContext context) throws JsonParseException {
            if (json == null || json.isJsonNull()) {
                return null;
            }

            if (json.isJsonPrimitive()) {
                final String jsonString = json.getAsString();

                if (!MiscUtils.isValidArtwork(jsonString)) {
                    return null;
                }

                final int dot = jsonString.lastIndexOf('.');

                if (dot == -1) {
                    return null;
                }

                int slash = dot - 1;

                while (slash >= 0 && jsonString.charAt(slash) != '/') {
                    --slash;
                }

                final String size = jsonString.substring(slash + 1, dot);

                if (TextUtils.isEmpty(size)) {
                    return null;
                }

                String large = null, medium = null, original = null, small = null, tiny = null;

                if (size.equalsIgnoreCase(LARGE)) {
                    large = size;
                } else if (size.equalsIgnoreCase(MEDIUM)) {
                    medium = size;
                } else if (size.equalsIgnoreCase(SMALL)) {
                    small = size;
                } else if (size.equalsIgnoreCase(TINY)) {
                    tiny = size;
                } else {
                    original = size;
                }

                return new Image(large, medium, original, small, tiny);
            } else if (json.isJsonObject()) {
                final JsonObject jsonObject = json.getAsJsonObject();

                if (jsonObject.isJsonNull()) {
                    return null;
                }

                final String large = jsonObject.has(LARGE) ? jsonObject.get(LARGE).getAsString() : null;
                final String medium = jsonObject.has(MEDIUM) ? jsonObject.get(MEDIUM).getAsString() : null;
                final String original = jsonObject.has(ORIGINAL) ? jsonObject.get(ORIGINAL).getAsString() : null;
                final String small = jsonObject.has(SMALL) ? jsonObject.get(SMALL).getAsString() : null;
                final String tiny = jsonObject.has(TINY) ? jsonObject.get(TINY).getAsString() : null;
                return new Image(large, medium, original, small, tiny);
            }

            throw new JsonParseException("json is neither a primitive nor an object: " + json);
        }
    };

}
