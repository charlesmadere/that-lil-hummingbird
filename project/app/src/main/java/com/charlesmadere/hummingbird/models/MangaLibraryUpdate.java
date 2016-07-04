package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.JsonObject;

public class MangaLibraryUpdate implements Parcelable {

    // TODO


    public JsonObject toJson() {
        // TODO

        final JsonObject outer = new JsonObject();
        outer.add("manga_library_entry", null);

        return outer;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        // TODO
    }

    public static final Creator<MangaLibraryUpdate> CREATOR = new Creator<MangaLibraryUpdate>() {
        @Override
        public MangaLibraryUpdate createFromParcel(final Parcel source) {
            final MangaLibraryUpdate mlu = new MangaLibraryUpdate();
            // TODO
            return mlu;
        }

        @Override
        public MangaLibraryUpdate[] newArray(final int size) {
            return new MangaLibraryUpdate[size];
        }
    };

}
