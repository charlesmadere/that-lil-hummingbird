package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.charlesmadere.hummingbird.misc.ParcelableUtils;
import com.google.gson.annotations.SerializedName;

public class AnimeEpisode implements Parcelable {

    @SerializedName("number")
    private int mNumber;

    @Nullable
    @SerializedName("season_number")
    private Integer mSeasonNumber;

    @Nullable
    @SerializedName("airdate")
    private SimpleDate mAirDate;

    @SerializedName("id")
    private String mId;

    @Nullable
    @SerializedName("synopsis")
    private String mSynopsis;

    @SerializedName("title")
    private String mTitle;


    @Nullable
    public SimpleDate getAirDate() {
        return mAirDate;
    }

    public String getId() {
        return mId;
    }

    public int getNumber() {
        return mNumber;
    }

    @Nullable
    public Integer getSeasonNumber() {
        return mSeasonNumber;
    }

    @Nullable
    public String getSynopsis() {
        return mSynopsis;
    }

    public String getTitle() {
        return mTitle;
    }

    @Override
    public String toString() {
        return getTitle();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeInt(mNumber);
        ParcelableUtils.writeInteger(mSeasonNumber, dest);
        dest.writeParcelable(mAirDate, flags);
        dest.writeString(mId);
        dest.writeString(mSynopsis);
        dest.writeString(mTitle);
    }

    public static final Creator<AnimeEpisode> CREATOR = new Creator<AnimeEpisode>() {
        @Override
        public AnimeEpisode createFromParcel(final Parcel source) {
            final AnimeEpisode ae = new AnimeEpisode();
            ae.mNumber = source.readInt();
            ae.mSeasonNumber = ParcelableUtils.readInteger(source);
            ae.mAirDate = source.readParcelable(SimpleDate.class.getClassLoader());
            ae.mId = source.readString();
            ae.mSynopsis = source.readString();
            ae.mTitle = source.readString();
            return ae;
        }

        @Override
        public AnimeEpisode[] newArray(final int size) {
            return new AnimeEpisode[size];
        }
    };

}
