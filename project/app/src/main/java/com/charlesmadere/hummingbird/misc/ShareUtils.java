package com.charlesmadere.hummingbird.misc;

import android.app.Activity;
import android.support.v4.app.ShareCompat;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.models.AnimeDigest;
import com.charlesmadere.hummingbird.models.GroupDigest;
import com.charlesmadere.hummingbird.models.MangaDigest;

public final class ShareUtils {

    public static void shareAnime(final Activity activity, final AnimeDigest anime) {
        if (activity == null) {
            throw new IllegalArgumentException("activity parameter can't be null");
        } else if (anime == null) {
            throw new IllegalArgumentException("anime parameter can't be null");
        }

        ShareCompat.IntentBuilder.from(activity)
                .setChooserTitle(activity.getString(R.string.share_x, anime.getTitle()))
                .setText(Constants.HUMMINGBIRD_ANIME_URL + anime.getId())
                .setType(Constants.MIMETYPE_TEXT_PLAIN)
                .startChooser();
    }

    public static void shareGroup(final Activity activity, final GroupDigest group) {
        if (activity == null) {
            throw new IllegalArgumentException("activity parameter can't be null");
        } else if (group == null) {
            throw new IllegalArgumentException("group parameter can't be null");
        }

        ShareCompat.IntentBuilder.from(activity)
                .setChooserTitle(activity.getString(R.string.share_x, group.getName()))
                .setText(Constants.HUMMINGBIRD_GROUP_URL + group.getId())
                .setType(Constants.MIMETYPE_TEXT_PLAIN)
                .startChooser();
    }

    public static void shareManga(final Activity activity, final MangaDigest manga) {
        if (activity == null) {
            throw new IllegalArgumentException("activity parameter can't be null");
        } else if (manga == null) {
            throw new IllegalArgumentException("manga parameter can't be null");
        }

        ShareCompat.IntentBuilder.from(activity)
                .setChooserTitle(activity.getString(R.string.share_x, manga.getTitle()))
                .setText(Constants.HUMMINGBIRD_MANGA_URL + manga.getId())
                .setType(Constants.MIMETYPE_TEXT_PLAIN)
                .startChooser();
    }

}
