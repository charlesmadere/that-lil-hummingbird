package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MangaLibraryEntryResponse implements Parcelable {

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

    public static final Creator<MangaLibraryEntryResponse> CREATOR = new Creator<MangaLibraryEntryResponse>() {
        @Override
        public MangaLibraryEntryResponse createFromParcel(final Parcel source) {
            final MangaLibraryEntryResponse mler = new MangaLibraryEntryResponse();
            mler.mManga = source.readParcelable(Manga.class.getClassLoader());
            mler.mLibraryEntry = source.readParcelable(MangaLibraryEntry.class.getClassLoader());
            return mler;
        }

        @Override
        public MangaLibraryEntryResponse[] newArray(final int size) {
            return new MangaLibraryEntryResponse[size];
        }
    };

}
