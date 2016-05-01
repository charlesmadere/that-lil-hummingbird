package com.charlesmadere.hummingbird.misc;

import android.os.Parcel;
import android.support.annotation.Nullable;

import com.charlesmadere.hummingbird.models.AbsAnime;
import com.charlesmadere.hummingbird.models.AbsStory;
import com.charlesmadere.hummingbird.models.AbsSubstory;
import com.charlesmadere.hummingbird.models.AnimeV1;
import com.charlesmadere.hummingbird.models.AnimeV2;
import com.charlesmadere.hummingbird.models.MediaStory;
import com.charlesmadere.hummingbird.models.ReplySubstory;
import com.charlesmadere.hummingbird.models.WatchedEpisodeSubstory;
import com.charlesmadere.hummingbird.models.WatchlistStatusUpdateSubstory;

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
    @SuppressWarnings("unchecked")
    public static <T extends AbsStory> T readAbsStoryFromParcel(final Parcel source) {
        final AbsStory.Type type = source.readParcelable(AbsStory.Type.class.getClassLoader());

        if (type == null) {
            return null;
        }

        final AbsStory story;

        switch (type) {
            case COMMENT:
                story = source.readParcelable(AnimeV1.class.getClassLoader());
                break;

            case MEDIA_STORY:
                story = source.readParcelable(AnimeV2.class.getClassLoader());
                break;

            default:
                throw new RuntimeException("Encountered a " + AbsStory.Type.class.getName()
                        + " (" + type + ") that hasn't been added as a possible value.");
        }

        return (T) story;
    }

    @Nullable
    public static ArrayList<AbsStory> readAbsStoryListFromParcel(final Parcel source) {
        final int count = source.readInt();

        if (count == 0) {
            return null;
        }

        final ArrayList<AbsStory> list = new ArrayList<>(count);

        for (int i = 0; i < count; ++i) {
            list.add(readAbsStoryFromParcel(source));
        }

        return list;
    }

    public static void writeAbsStoryToParcel(@Nullable final AbsStory story, final Parcel dest,
            final int flags) {
        if (story == null) {
            dest.writeParcelable(null, flags);
        } else {
            dest.writeParcelable(story.getType(), flags);
            dest.writeParcelable(story, flags);
        }
    }

    public static void writeAbsStoryListToParcel(@Nullable final List<AbsStory> list,
            final Parcel dest, final int flags) {
        if (list == null || list.isEmpty()) {
            dest.writeInt(0);
            return;
        }

        dest.writeInt(list.size());

        for (final AbsStory story : list) {
            writeAbsStoryToParcel(story, dest, flags);
        }
    }

    @Nullable
    @SuppressWarnings("unchecked")
    public static <T extends AbsSubstory> T readAbsSubstoryFromParcel(final Parcel source) {
        final AbsSubstory.Type type = source.readParcelable(AbsSubstory.Type.class.getClassLoader());

        if (type == null) {
            return null;
        }

        final AbsSubstory substory;

        switch (type) {
            case REPLY:
                substory = source.readParcelable(ReplySubstory.class.getClassLoader());
                break;

            case WATCHED_EPISODE:
                substory = source.readParcelable(WatchedEpisodeSubstory.class.getClassLoader());
                break;

            case WATCHLIST_STATUS_UPDATE:
                substory = source.readParcelable(WatchlistStatusUpdateSubstory.class.getClassLoader());
                break;

            default:
                throw new RuntimeException("Encountered a " + AbsSubstory.Type.class.getName()
                        + " (" + type + ") that hasn't been added as a possible value.");
        }

        return (T) substory;
    }

    @Nullable
    public static ArrayList<AbsSubstory> readAbsSubstoryListFromParcel(final Parcel source) {
        final int count = source.readInt();

        if (count == 0) {
            return null;
        }

        final ArrayList<AbsSubstory> list = new ArrayList<>(count);

        for (int i = 0; i < count; ++i) {
            list.add(readAbsSubstoryFromParcel(source));
        }

        return list;
    }

    public static void writeAbsSubstoryToParcel(@Nullable final AbsSubstory substory,
            final Parcel dest, final int flags) {
        if (substory == null) {
            dest.writeParcelable(null, flags);
        } else {
            dest.writeParcelable(substory.getType(), flags);
            dest.writeParcelable(substory, flags);
        }
    }

    public static void writeAbsSubstoryListToParcel(@Nullable final List<AbsSubstory> list,
            final Parcel dest, final int flags) {
        if (list == null || list.isEmpty()) {
            dest.writeInt(0);
            return;
        }

        dest.writeInt(list.size());

        for (final AbsSubstory substory : list) {
            writeAbsSubstoryToParcel(substory, dest, flags);
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

    @Nullable
    @SuppressWarnings("unchecked")
    public static <T extends MediaStory.AbsMedia> T readMediaStoryAbsMediaFromParcel(
            final Parcel source) {
        final MediaStory.AbsMedia.Type type = source.readParcelable(
                MediaStory.AbsMedia.Type.class.getClassLoader());

        if (type == null) {
            return null;
        }

        final MediaStory.AbsMedia media;

        switch (type) {
            case ANIME:
                media = source.readParcelable(MediaStory.AnimeMedia.class.getClassLoader());
                break;

            case MANGA:
                media = source.readParcelable(MediaStory.MangaMedia.class.getClassLoader());
                break;

            default:
                throw new RuntimeException("Encountered a " + MediaStory.Type.class.getName()
                        + " (" + type + ") that hasn't been added as a possible value.");
        }

        return (T) media;
    }

    @Nullable
    public static ArrayList<MediaStory.AbsMedia> readMediaStoryAbsMediaListFromParcel(
            final Parcel source) {
        final int count = source.readInt();

        if (count == 0) {
            return null;
        }

        final ArrayList<MediaStory.AbsMedia> list = new ArrayList<>(count);

        for (int i = 0; i < count; ++i) {
            list.add(readMediaStoryAbsMediaFromParcel(source));
        }

        return list;
    }

    public static void writeMediaStoryAbsMediaToParcel(@Nullable final MediaStory.AbsMedia media,
            final Parcel dest, final int flags) {
        if (media == null) {
            dest.writeParcelable(null, flags);
        } else {
            dest.writeParcelable(media.getType(), flags);
            dest.writeParcelable(media, flags);
        }
    }

    public static void writeMediaStoryAbsMediaListToParcel(
            @Nullable final List<MediaStory.AbsMedia> list, final Parcel dest, final int flags) {
        if (list == null || list.isEmpty()) {
            dest.writeInt(0);
            return;
        }

        dest.writeInt(list.size());

        for (final MediaStory.AbsMedia media : list) {
            writeMediaStoryAbsMediaToParcel(media, dest, flags);
        }
    }

}
