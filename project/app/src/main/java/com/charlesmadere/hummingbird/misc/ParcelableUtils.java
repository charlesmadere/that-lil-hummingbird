package com.charlesmadere.hummingbird.misc;

import android.os.Parcel;
import android.support.annotation.Nullable;

import com.charlesmadere.hummingbird.models.AbsNotification;
import com.charlesmadere.hummingbird.models.AbsStory;
import com.charlesmadere.hummingbird.models.AbsSubstory;
import com.charlesmadere.hummingbird.models.CommentStory;
import com.charlesmadere.hummingbird.models.FollowedStory;
import com.charlesmadere.hummingbird.models.FollowedSubstory;
import com.charlesmadere.hummingbird.models.MediaStory;
import com.charlesmadere.hummingbird.models.ReplySubstory;
import com.charlesmadere.hummingbird.models.SearchBundle;
import com.charlesmadere.hummingbird.models.UserDigest;
import com.charlesmadere.hummingbird.models.WatchedEpisodeSubstory;
import com.charlesmadere.hummingbird.models.WatchlistStatusUpdateSubstory;

import java.util.ArrayList;
import java.util.List;

public final class ParcelableUtils {

    @Nullable
    public static <T extends AbsNotification.AbsSource> T readAbsNotificationAbsSource(
            final Parcel source) {
        final AbsNotification.AbsSource.Type type = source.readParcelable(
                AbsNotification.AbsSource.Type.class.getClassLoader());

        if (type == null) {
            return null;
        }

        final T absSource;

        switch (type) {
            case STORY:
                absSource = source.readParcelable(AbsNotification.StorySource.class.getClassLoader());
                break;

            case SUBSTORY:
                absSource = source.readParcelable(AbsNotification.SubstorySource.class.getClassLoader());
                break;

            default:
                throw new RuntimeException("encountered unknown " +
                        AbsNotification.AbsSource.Type.class.getName() + ": \"" + type + '"');
        }

        return absSource;
    }

    public static void writeAbsNotificationAbsSource(
            @Nullable final AbsNotification.AbsSource source, final Parcel dest, final int flags) {
        if (source == null) {
            dest.writeParcelable(null, flags);
        } else {
            dest.writeParcelable(source.getType(), flags);
            dest.writeParcelable(source, flags);
        }
    }

    @Nullable
    public static <T extends AbsStory> T readAbsStory(final Parcel source) {
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

    public static void writeAbsStory(@Nullable final AbsStory story, final Parcel dest,
            final int flags) {
        if (story == null) {
            dest.writeParcelable(null, flags);
        } else {
            dest.writeParcelable(story.getType(), flags);
            dest.writeParcelable(story, flags);
        }
    }

    @Nullable
    public static <T extends AbsSubstory> T readAbsSubstory(final Parcel source) {
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
                substory = source.readParcelable(WatchlistStatusUpdateSubstory.class.getClassLoader());
                break;

            default:
                throw new RuntimeException("encountered unknown " +
                        AbsSubstory.Type.class.getName() + ": \"" + type + '"');
        }

        return substory;
    }

    @Nullable
    public static ArrayList<AbsSubstory> readAbsSubstoryList(final Parcel source) {
        final int count = source.readInt();

        if (count == 0) {
            return null;
        }

        final ArrayList<AbsSubstory> list = new ArrayList<>(count);

        for (int i = 0; i < count; ++i) {
            list.add(readAbsSubstory(source));
        }

        return list;
    }

    public static void writeAbsSubstory(@Nullable final AbsSubstory substory, final Parcel dest,
            final int flags) {
        if (substory == null) {
            dest.writeParcelable(null, flags);
        } else {
            dest.writeParcelable(substory.getType(), flags);
            dest.writeParcelable(substory, flags);
        }
    }

    public static void writeAbsSubstoryList(@Nullable final List<AbsSubstory> list,
            final Parcel dest, final int flags) {
        if (list == null || list.isEmpty()) {
            dest.writeInt(0);
            return;
        }

        dest.writeInt(list.size());

        for (final AbsSubstory substory : list) {
            writeAbsSubstory(substory, dest, flags);
        }
    }

    @Nullable
    public static ArrayList<Integer> readIntegerArrayList(final Parcel source) {
        final int count = source.readInt();

        if (count == 0) {
            return null;
        }

        final ArrayList<Integer> list = new ArrayList<>(count);

        for (int i = 0; i < count; ++i) {
            list.add((Integer) source.readValue(Integer.class.getClassLoader()));
        }

        return list;
    }

    public static void writeIntegerArrayList(@Nullable final ArrayList<Integer> list,
            final Parcel dest) {
        if (list == null || list.isEmpty()) {
            dest.writeInt(0);
            return;
        }

        dest.writeInt(list.size());

        for (final Integer integer : list) {
            dest.writeValue(integer);
        }
    }

    @Nullable
    public static <T extends MediaStory.AbsMedia> T readMediaStoryAbsMedia(final Parcel source) {
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

    public static void writeMediaStoryAbsMedia(@Nullable final MediaStory.AbsMedia media,
            final Parcel dest, final int flags) {
        if (media == null) {
            dest.writeParcelable(null, flags);
        } else {
            dest.writeParcelable(media.getType(), flags);
            dest.writeParcelable(media, flags);
        }
    }

    @Nullable
    public static <T extends SearchBundle.AbsResult> T readSearchBundleAbsResult(
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
    public static ArrayList<SearchBundle.AbsResult> readSearchBundleAbsResultList(
            final Parcel source) {
        final int count = source.readInt();

        if (count == 0) {
            return null;
        }

        final ArrayList<SearchBundle.AbsResult> list = new ArrayList<>(count);

        for (int i = 0; i < count; ++i) {
            list.add(readSearchBundleAbsResult(source));
        }

        return list;
    }

    public static void writeSearchBundleAbsResult(@Nullable final SearchBundle.AbsResult result,
            final Parcel dest, final int flags) {
        if (result == null) {
            dest.writeParcelable(null, flags);
        } else {
            dest.writeParcelable(result.getType(), flags);
            dest.writeParcelable(result, flags);
        }
    }

    public static void writeSearchBundleAbsResultList(
            @Nullable final List<SearchBundle.AbsResult> list, final Parcel dest, final int flags) {
        if (list == null || list.isEmpty()) {
            dest.writeInt(0);
            return;
        }

        dest.writeInt(list.size());

        for (final SearchBundle.AbsResult result : list) {
            writeSearchBundleAbsResult(result, dest, flags);
        }
    }

    @Nullable
    public static <T extends UserDigest.Favorite.AbsItem> T readUserDigestFavoriteAbsItem(
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

            case MANGA:
                item = source.readParcelable(UserDigest.Favorite.MangaItem.class.getClassLoader());
                break;

            default:
                throw new RuntimeException("encountered unknown " +
                        UserDigest.Favorite.AbsItem.Type.class.getName() + ": \"" + type + '"');
        }

        return item;
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

}
