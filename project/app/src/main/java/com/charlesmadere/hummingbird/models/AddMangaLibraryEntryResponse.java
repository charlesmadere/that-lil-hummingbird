package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AddMangaLibraryEntryResponse implements Parcelable {

    @SerializedName("manga")
    private ArrayList<Manga> mManga;

    @SerializedName("manga_library_entry")
    private MangaLibraryEntry mLibraryEntry;


    public MangaLibraryEntry getLibraryEntry() {
        return mLibraryEntry;
    }

    public Manga getManga() {
        return mManga.get(0);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeTypedList(mManga);
        dest.writeParcelable(mLibraryEntry, flags);
    }

    public static final Creator<AddMangaLibraryEntryResponse> CREATOR = new Creator<AddMangaLibraryEntryResponse>() {
        @Override
        public AddMangaLibraryEntryResponse createFromParcel(final Parcel source) {
            final AddMangaLibraryEntryResponse amler = new AddMangaLibraryEntryResponse();
            amler.mManga = source.readParcelable(Manga.class.getClassLoader());
            amler.mLibraryEntry = source.readParcelable(MangaLibraryEntry.class.getClassLoader());
            return amler;
        }

        @Override
        public AddMangaLibraryEntryResponse[] newArray(final int size) {
            return new AddMangaLibraryEntryResponse[size];
        }
    };

}
