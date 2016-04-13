package com.charlesmadere.hummingbird.misc;

import android.os.Parcel;
import android.support.annotation.Nullable;

import com.charlesmadere.hummingbird.models.AbsAnime;
import com.charlesmadere.hummingbird.models.AnimeV1;
import com.charlesmadere.hummingbird.models.AnimeV2;

import java.util.ArrayList;
import java.util.List;

public final class ParcelableUtils {

    @Nullable
    @SuppressWarnings("unchecked")
    public static <T extends AbsAnime> T readAbsAnimeFromParcel(final Parcel source) {
        final AbsAnime.Version version = source.readParcelable(
                AbsAnime.Version.class.getClassLoader());

        if (version == null) {
            return null;
        }

        final AbsAnime anime;

        switch (version) {
            case V1:
                anime = source.readParcelable(AnimeV1.class.getClassLoader());
                break;

            case V2:
                anime = source.readParcelable(AnimeV2.class.getClassLoader());
                break;

            default:
                throw new RuntimeException("Encountered a " + AbsAnime.Version.class.getName()
                        + " (" + version + ") that hasn't been added as a possible value.");
        }

        return (T) anime;
    }

    @Nullable
    public static ArrayList<AbsAnime> readAbsAnimeListFromParcel(final Parcel source) {
        final int count = source.readInt();

        if (count == 0) {
            return null;
        }

        final ArrayList<AbsAnime> list = new ArrayList<>(count);

        for (int i = 0; i < count; ++i) {
            list.add(readAbsAnimeFromParcel(source));
        }

        return list;
    }

    public static void writeAbsAnimeToParcel(@Nullable final AbsAnime anime, final Parcel dest,
            final int flags) {
        if (anime == null) {
            dest.writeParcelable(null, flags);
        } else {
            dest.writeParcelable(anime.getVersion(), flags);
            dest.writeParcelable(anime, flags);
        }
    }

    public static void writeAbsAnimeListToParcel(@Nullable final List<AbsAnime> list,
            final Parcel dest, final int flags) {
        if (list == null || list.isEmpty()) {
            dest.writeInt(0);
            return;
        }

        dest.writeInt(list.size());

        for (final AbsAnime anime : list) {
            writeAbsAnimeToParcel(anime, dest, flags);
        }
    }

    @Nullable
    public static Boolean readBoolean(final Parcel source) {
        final int value = source.readInt();

        if (value == 1) {
            return Boolean.TRUE;
        } else if (value == 0) {
            return Boolean.FALSE;
        } else {
            return null;
        }
    }

    public static void writeBoolean(@Nullable final Boolean b, final Parcel dest) {
        if (Boolean.TRUE.equals(b)) {
            dest.writeInt(1);
        } else if (Boolean.FALSE.equals(b)) {
            dest.writeInt(0);
        } else {
            dest.writeInt(-1);
        }
    }

    @Nullable
    public static Integer readInteger(final Parcel source) {
        final String value = source.readString();

        if (value == null) {
            return null;
        } else {
            return Integer.parseInt(value);
        }
    }

    public static void writeInteger(@Nullable final Integer i, final Parcel dest) {
        String value = null;

        if (i != null) {
            value = i.toString();
        }

        dest.writeString(value);
    }

}
