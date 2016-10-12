package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AnimeLibraryEntryResponse implements Parcelable {

    @SerializedName("library_entry")
    private AnimeLibraryEntry mLibraryEntry;

    @SerializedName("anime")
    private ArrayList<Anime> mAnime;


    public AnimeLibraryEntry getLibraryEntry() {
        if (mLibraryEntry.getAnime() == null) {
            mLibraryEntry.setAnime(mAnime.get(0));
        }

        return mLibraryEntry;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeParcelable(mLibraryEntry, flags);
        dest.writeTypedList(mAnime);
    }

    public static final Creator<AnimeLibraryEntryResponse> CREATOR = new Creator<AnimeLibraryEntryResponse>() {
        @Override
        public AnimeLibraryEntryResponse createFromParcel(final Parcel source) {
            final AnimeLibraryEntryResponse aler = new AnimeLibraryEntryResponse();
            aler.mLibraryEntry = source.readParcelable(AnimeLibraryEntry.class.getClassLoader());
            aler.mAnime = source.createTypedArrayList(Anime.CREATOR);
            return aler;
        }

        @Override
        public AnimeLibraryEntryResponse[] newArray(final int size) {
            return new AnimeLibraryEntryResponse[size];
        }
    };

}
