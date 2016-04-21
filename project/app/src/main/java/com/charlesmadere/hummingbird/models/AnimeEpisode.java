package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.charlesmadere.hummingbird.misc.MiscUtils;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AnimeEpisode implements Parcelable {

    @SerializedName("number")
    private int mNumber;

    @SerializedName("season_number")
    private int mSeason;

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

    public int getSeason() {
        return mSeason;
    }

    @Nullable
    public String getSynopsis() {
        return mSynopsis;
    }

    public String getTitle() {
        return mTitle;
    }

    public boolean hasAirDate() {
        return mAirDate != null;
    }

    public boolean hasSeason() {
        return mSeason >= 1;
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

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeInt(mNumber);
        dest.writeInt(mSeason);
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
            ae.mSeason = source.readInt();
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

    public static void sort(@Nullable final List<AnimeEpisode> list) {
        if (list == null || list.isEmpty()) {
            return;
        }

        final ArrayList<AnimeEpisode> episodesWithSeasons = new ArrayList<>();
        final ArrayList<AnimeEpisode> episodesWithoutSeasons = new ArrayList<>();

        for (final AnimeEpisode episode : list) {
            if (episode.hasSeason()) {
                episodesWithSeasons.add(episode);
            } else {
                episodesWithoutSeasons.add(episode);
            }
        }

        list.clear();

        if (!episodesWithSeasons.isEmpty()) {
            Collections.sort(episodesWithSeasons, COMPARATOR);
            list.addAll(episodesWithSeasons);
        }

        if (!episodesWithoutSeasons.isEmpty()) {
            Collections.sort(episodesWithoutSeasons, COMPARATOR);
            list.addAll(episodesWithoutSeasons);
        }
    }

    public static final Comparator<AnimeEpisode> COMPARATOR = new Comparator<AnimeEpisode>() {
        @Override
        public int compare(final AnimeEpisode lhs, final AnimeEpisode rhs) {
            int compare = MiscUtils.integerCompare(lhs.getSeason(), rhs.getSeason());
            if (compare != 0) {
                return compare;
            }

            compare = MiscUtils.integerCompare(lhs.getNumber(), rhs.getNumber());
            if (compare != 0) {
                return compare;
            }

            return lhs.getTitle().compareToIgnoreCase(rhs.getTitle());
        }
    };

}
