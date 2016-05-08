package com.charlesmadere.hummingbird.misc;

import android.os.Parcel;
import android.support.annotation.Nullable;

import com.charlesmadere.hummingbird.models.AbsAnime;
import com.charlesmadere.hummingbird.models.AbsStory;
import com.charlesmadere.hummingbird.models.AbsSubstory;
import com.charlesmadere.hummingbird.models.AbsUser;
import com.charlesmadere.hummingbird.models.AnimeV1;
import com.charlesmadere.hummingbird.models.AnimeV2;
import com.charlesmadere.hummingbird.models.AnimeV3;
import com.charlesmadere.hummingbird.models.CommentStory;
import com.charlesmadere.hummingbird.models.FollowedStory;
import com.charlesmadere.hummingbird.models.FollowedSubstory;
import com.charlesmadere.hummingbird.models.MediaStory;
import com.charlesmadere.hummingbird.models.ReplySubstory;
import com.charlesmadere.hummingbird.models.SearchBundle;
import com.charlesmadere.hummingbird.models.UserDigest;
import com.charlesmadere.hummingbird.models.UserV1;
import com.charlesmadere.hummingbird.models.UserV2;
import com.charlesmadere.hummingbird.models.WatchedEpisodeSubstory;
import com.charlesmadere.hummingbird.models.WatchlistStatusUpdateSubstory;

import java.util.ArrayList;
import java.util.List;

public final class ParcelableUtils {

    @Nullable
    public static <T extends AbsAnime> T readAbsAnimeFromParcel(final Parcel source) {
        final AbsAnime.Version version = source.readParcelable(
                AbsAnime.Version.class.getClassLoader());

        if (version == null) {
            return null;
        }

        final T anime;

        switch (version) {
            case V1:
                anime = source.readParcelable(AnimeV1.class.getClassLoader());
                break;

            case V2:
                anime = source.readParcelable(AnimeV2.class.getClassLoader());
                break;

            case V3:
                anime = source.readParcelable(AnimeV3.class.getClassLoader());
                break;

            default:
                throw new RuntimeException("encountered unknown " +
                        AbsAnime.Version.class.getName() + ": \"" + version + '"');
        }

        return anime;
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
    public static <T extends AbsStory> T readAbsStoryFromParcel(final Parcel source) {
        final AbsStory.Type type = source.readParcelable(AbsStory.Type.class.getClassLoader());

        if (type == null) {
            return null;
        }

        final T story;

        switch (type) {
            case COMMENT:
                story = source.readParcelable(CommentStory.class.getClassLoader());
                break;

            case FOLLOWED:
                story = source.readParcelable(FollowedStory.class.getClassLoader());
                break;

            case MEDIA_STORY:
                story = source.readParcelable(MediaStory.class.getClassLoader());
                break;

            default:
                throw new RuntimeException("encountered unknown " + AbsStory.Type.class.getName()
                        + ": \"" + type + '"');
        }

        return story;
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
    public static <T extends AbsSubstory> T readAbsSubstoryFromParcel(final Parcel source) {
        final AbsSubstory.Type type = source.readParcelable(AbsSubstory.Type.class.getClassLoader());

        if (type == null) {
            return null;
        }

        final T substory;

        switch (type) {
            case FOLLOWED:
                substory = source.readParcelable(FollowedSubstory.class.getClassLoader());
                break;

            case REPLY:
                substory = source.readParcelable(ReplySubstory.class.getClassLoader());
                break;

            case WATCHED_EPISODE:
                substory = source.readParcelable(WatchedEpisodeSubstory.class.getClassLoader());
                break;

            case WATCHLIST_STATUS_UPDATE:
                substory = source.readParcelable(
                        WatchlistStatusUpdateSubstory.class.getClassLoader());
                break;

            default:
                throw new RuntimeException("encountered unknown " +
                        AbsSubstory.Type.class.getName() + ": \"" + type + '"');
        }

        return substory;
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
    public static <T extends AbsUser> T readAbsUserFromParcel(final Parcel source) {
        final AbsUser.Version version = source.readParcelable(
                AbsUser.Version.class.getClassLoader());

        if (version == null) {
            return null;
        }

        final T user;

        switch (version) {
            case V1:
                user = source.readParcelable(UserV1.class.getClassLoader());
                break;

            case V2:
                user = source.readParcelable(UserV2.class.getClassLoader());
                break;

            default:
                throw new RuntimeException("encountered unknown " +
                        AbsUser.Version.class.getName() + ": \"" + version + '"');
        }

        return user;
    }

    @Nullable
    public static ArrayList<AbsUser> readAbsUserListFromParcel(final Parcel source) {
        final int count = source.readInt();

        if (count == 0) {
            return null;
        }

        final ArrayList<AbsUser> list = new ArrayList<>(count);

        for (int i = 0; i < count; ++i) {
            list.add(readAbsUserFromParcel(source));
        }

        return list;
    }

    public static void writeAbsUserToParcel(@Nullable final AbsUser user, final Parcel dest,
            final int flags) {
        if (user == null) {
            dest.writeParcelable(null, flags);
        } else {
            dest.writeParcelable(user.getVersion(), flags);
            dest.writeParcelable(user, flags);
        }
    }

    public static void writeAbsUserListToParcel(@Nullable final List<AbsUser> list,
            final Parcel dest, final int flags) {
        if (list == null || list.isEmpty()) {
            dest.writeInt(0);
            return;
        }

        dest.writeInt(list.size());

        for (final AbsUser user : list) {
            writeAbsUserToParcel(user, dest, flags);
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
    public static <T extends MediaStory.AbsMedia> T readMediaStoryAbsMediaFromParcel(
            final Parcel source) {
        final MediaStory.AbsMedia.Type type = source.readParcelable(
                MediaStory.AbsMedia.Type.class.getClassLoader());

        if (type == null) {
            return null;
        }

        final T media;

        switch (type) {
            case ANIME:
                media = source.readParcelable(MediaStory.AnimeMedia.class.getClassLoader());
                break;

            case MANGA:
                media = source.readParcelable(MediaStory.MangaMedia.class.getClassLoader());
                break;

            default:
                throw new RuntimeException("encountered unknown " + MediaStory.Type.class.getName()
                        + ": \"" + type + '"');
        }

        return media;
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

    @Nullable
    public static <T extends SearchBundle.AbsResult> T readSearchBundleAbsResultFromParcel(
            final Parcel source) {
        final SearchBundle.AbsResult.Type type = source.readParcelable(
                SearchBundle.AbsResult.Type.class.getClassLoader());

        if (type == null) {
            return null;
        }

        final T result;

        switch (type) {
            case ANIME:
                result = source.readParcelable(SearchBundle.AnimeResult.class.getClassLoader());
                break;

            case GROUP:
                result = source.readParcelable(SearchBundle.GroupResult.class.getClassLoader());
                break;

            case MANGA:
                result = source.readParcelable(SearchBundle.MangaResult.class.getClassLoader());
                break;

            case USER:
                result = source.readParcelable(SearchBundle.UserResult.class.getClassLoader());
                break;

            default:
                throw new RuntimeException("encountered unknown " +
                        SearchBundle.AbsResult.Type.class.getName() + ": \"" + type + '"');
        }

        return result;
    }

    @Nullable
    public static ArrayList<SearchBundle.AbsResult> readSearchBundleAbsResultListFromParcel(
            final Parcel source) {
        final int count = source.readInt();

        if (count == 0) {
            return null;
        }

        final ArrayList<SearchBundle.AbsResult> list = new ArrayList<>(count);

        for (int i = 0; i < count; ++i) {
            list.add(readSearchBundleAbsResultFromParcel(source));
        }

        return list;
    }

    public static void writeSearchBundleAbsResultToParcel(
            @Nullable final SearchBundle.AbsResult result, final Parcel dest, final int flags) {
        if (result == null) {
            dest.writeParcelable(null, flags);
        } else {
            dest.writeParcelable(result.getType(), flags);
            dest.writeParcelable(result, flags);
        }
    }

    public static void writeSearchBundleAbsResultListToParcel(
            @Nullable final List<SearchBundle.AbsResult> list, final Parcel dest, final int flags) {
        if (list == null || list.isEmpty()) {
            dest.writeInt(0);
            return;
        }

        dest.writeInt(list.size());

        for (final SearchBundle.AbsResult result : list) {
            writeSearchBundleAbsResultToParcel(result, dest, flags);
        }
    }

    @Nullable
    public static <T extends UserDigest.Favorite.AbsItem> T readUserDigestFavoriteAbsItemFromParcel(
            final Parcel source) {
        final UserDigest.Favorite.AbsItem.Type type = source.readParcelable(
                UserDigest.Favorite.AbsItem.Type.class.getClassLoader());

        if (type == null) {
            return null;
        }

        final T item;

        switch (type) {
            case ANIME:
                item = source.readParcelable(UserDigest.Favorite.AnimeItem.class.getClassLoader());
                break;

            default:
                throw new RuntimeException("encountered unknown " +
                        UserDigest.Favorite.AbsItem.Type.class.getName() + ": \"" + type + '"');
        }

        return item;
    }

    @Nullable
    public static ArrayList<UserDigest.Favorite.AbsItem> readUserDigestFavoriteAbsItemListFromParcel(
            final Parcel source) {
        final int count = source.readInt();

        if (count == 0) {
            return null;
        }

        final ArrayList<UserDigest.Favorite.AbsItem> list = new ArrayList<>(count);

        for (int i = 0; i < count; ++i) {
            list.add(readUserDigestFavoriteAbsItemFromParcel(source));
        }

        return list;
    }

    public static void writeUserDigestFavoriteAbsItemToParcel(
            @Nullable final UserDigest.Favorite.AbsItem item, final Parcel dest, final int flags) {
        if (item == null) {
            dest.writeParcelable(null, flags);
        } else {
            dest.writeParcelable(item.getType(), flags);
            dest.writeParcelable(item, flags);
        }
    }

    public static void writeUserDigestFavoriteAbsItemListToParcel(
            @Nullable final List<UserDigest.Favorite.AbsItem> list, final Parcel dest,
            final int flags) {
        if (list == null || list.isEmpty()) {
            dest.writeInt(0);
            return;
        }

        dest.writeInt(list.size());

        for (final UserDigest.Favorite.AbsItem item : list) {
            writeUserDigestFavoriteAbsItemToParcel(item, dest, flags);
        }
    }

}
