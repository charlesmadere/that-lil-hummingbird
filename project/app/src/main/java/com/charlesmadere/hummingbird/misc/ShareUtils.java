package com.charlesmadere.hummingbird.misc;

import android.app.Activity;
import android.support.v4.app.ShareCompat;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.models.AnimeDigest;
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
                // TODO
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
                // TODO
                .startChooser();
    }

}
