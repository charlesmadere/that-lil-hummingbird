package com.charlesmadere.hummingbird.models;

import android.content.res.Resources;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.charlesmadere.hummingbird.R;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AnimeV1 extends AbsAnime implements Parcelable {

    @SerializedName("status")
    private AiringStatus mStatus;

    @Nullable
    @SerializedName("genres")
    private ArrayList<Genre> mGenres;

    @Nullable
    @SerializedName("finished_airing")
    private SimpleDate mFinishedAiring;

    @Nullable
    @SerializedName("started_airing")
    private SimpleDate mStartedAiring;

    @Nullable
    @SerializedName("alternate_title")
    private String mAlternateTitle;

    @SerializedName("cover_image")
    private String mCoverImage;

    @SerializedName("title")
    private String mTitle;

    @SerializedName("url")
    private String mUrl;


    @Nullable
    public String getAlternateTitle() {
        return mAlternateTitle;
    }

    @Nullable
    public String getCoverImage() {
        return mCoverImage;
    }

    @Nullable
    @Override
    public SimpleDate getFinishedAiringDate() {
        return mFinishedAiring;
    }

    @Nullable
    public ArrayList<Genre> getGenres() {
        return mGenres;
    }

    @Override
    public String getGenresString(final Resources res) {
        if (!hasGenres()) {
            return "";
        }

        return TextUtils.join(res.getText(R.string.delimiter), mGenres);
    }

    @Override
    public String getImage() {
        return mCoverImage;
    }

    @Nullable
    @Override
    public SimpleDate getStartedAiringDate() {
        return mStartedAiring;
    }

    public AiringStatus getStatus() {
        return mStatus;
    }

    @Override
    public String getThumb() {
        return mCoverImage;
    }

    @Override
    public String getTitle() {
        return mTitle;
    }

    public String getUrl() {
        return mUrl;
    }

    @Override
    public Version getVersion() {
        return Version.V1;
    }

    @Override
    public boolean hasGenres() {
        return mGenres != null && !mGenres.isEmpty();
    }

    @Override
    protected void readFromParcel(final Parcel source) {
        super.readFromParcel(source);
        mStatus = source.readParcelable(AiringStatus.class.getClassLoader());
        mGenres = source.createTypedArrayList(Genre.CREATOR);
        mFinishedAiring = source.readParcelable(SimpleDate.class.getClassLoader());
        mStartedAiring = source.readParcelable(SimpleDate.class.getClassLoader());
        mAlternateTitle = source.readString();
        mCoverImage = source.readString();
        mTitle = source.readString();
        mUrl = source.readString();
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(mStatus, flags);
        dest.writeTypedList(mGenres);
        dest.writeParcelable(mFinishedAiring, flags);
        dest.writeParcelable(mStartedAiring, flags);
        dest.writeString(mAlternateTitle);
        dest.writeString(mCoverImage);
        dest.writeString(mTitle);
        dest.writeString(mUrl);
    }

    public static final Creator<AnimeV1> CREATOR = new Creator<AnimeV1>() {
        @Override
        public AnimeV1 createFromParcel(final Parcel source) {
            final AnimeV1 a = new AnimeV1();
            a.readFromParcel(source);
            return a;
        }

        @Override
        public AnimeV1[] newArray(final int size) {
            return new AnimeV1[size];
        }
    };


    public static class Genre implements Parcelable {
        @SerializedName("name")
        private String mName;


        public String getName() {
            return mName;
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
            dest.writeString(mName);
        }

        public static final Creator<Genre> CREATOR = new Creator<Genre>() {
            @Override
            public Genre createFromParcel(final Parcel source) {
                final Genre g = new Genre();
                g.mName = source.readString();
                return g;
            }

            @Override
            public Genre[] newArray(final int size) {
                return new Genre[size];
            }
        };
    }

}
