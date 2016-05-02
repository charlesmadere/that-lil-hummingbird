package com.charlesmadere.hummingbird.models;

import android.content.res.Resources;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

public class AnimeV3 extends AbsAnime implements Parcelable {

    @Nullable
    @Override
    public SimpleDate getFinishedAiringDate() {
        return null;
    }

    @Override
    public String getGenresString(final Resources res) {
        return null;
    }

    @Nullable
    @Override
    public SimpleDate getStartedAiringDate() {
        return null;
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public Version getVersion() {
        return Version.V3;
    }

    @Override
    public boolean hasGenres() {
        return false;
    }

    public static final Creator<AnimeV3> CREATOR = new Creator<AnimeV3>() {
        @Override
        public AnimeV3 createFromParcel(final Parcel source) {
            final AnimeV3 a = new AnimeV3();
            a.readFromParcel(source);
            return a;
        }

        @Override
        public AnimeV3[] newArray(final int size) {
            return new AnimeV3[size];
        }
    };

}
