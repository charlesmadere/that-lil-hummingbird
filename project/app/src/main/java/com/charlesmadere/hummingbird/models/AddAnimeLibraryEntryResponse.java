package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.charlesmadere.hummingbird.misc.ParcelableUtils;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AddAnimeLibraryEntryResponse implements Parcelable {

    @SerializedName("anime")
    private ArrayList<AbsAnime> mAnime;

    @SerializedName("library_entry")
    private AnimeLibraryEntry mLibraryEntry;


    public AbsAnime getAnime() {
        return mAnime.get(0);
    }

    public AnimeLibraryEntry getLibraryEntry() {
        return mLibraryEntry;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        ParcelableUtils.writeAbsAnimeListToParcel(mAnime, dest, flags);
        dest.writeParcelable(mLibraryEntry, flags);
    }

    public static final Creator<AddAnimeLibraryEntryResponse> CREATOR = new Creator<AddAnimeLibraryEntryResponse>() {
        @Override
        public AddAnimeLibraryEntryResponse createFromParcel(final Parcel source) {
            final AddAnimeLibraryEntryResponse aaler = new AddAnimeLibraryEntryResponse();
            aaler.mAnime = ParcelableUtils.readAbsAnimeListFromParcel(source);
            aaler.mLibraryEntry = source.readParcelable(AnimeLibraryEntry.class.getClassLoader());
            return aaler;
        }

        @Override
        public AddAnimeLibraryEntryResponse[] newArray(final int size) {
            return new AddAnimeLibraryEntryResponse[size];
        }
    };

}
