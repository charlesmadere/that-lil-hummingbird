package com.charlesmadere.hummingbird.misc;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.charlesmadere.hummingbird.activities.AnimeActivity;
import com.charlesmadere.hummingbird.activities.AnimeQuotesActivity;
import com.charlesmadere.hummingbird.activities.AnimeReviewActivity;
import com.charlesmadere.hummingbird.activities.AnimeReviewsActivity;
import com.charlesmadere.hummingbird.activities.BaseUserActivity;
import com.charlesmadere.hummingbird.activities.FollowersActivity;
import com.charlesmadere.hummingbird.activities.FollowingActivity;
import com.charlesmadere.hummingbird.activities.GroupActivity;
import com.charlesmadere.hummingbird.activities.GroupMembersActivity;
import com.charlesmadere.hummingbird.activities.MangaActivity;
import com.charlesmadere.hummingbird.activities.MangaLibraryActivity;
import com.charlesmadere.hummingbird.activities.NotificationsActivity;
import com.charlesmadere.hummingbird.activities.StoryActivity;
import com.charlesmadere.hummingbird.activities.UserActivity;
import com.charlesmadere.hummingbird.activities.UserAnimeReviewsActivity;

import java.util.ArrayList;

import static com.charlesmadere.hummingbird.misc.Constants.ANIME;
import static com.charlesmadere.hummingbird.misc.Constants.ANIME_SHORT;
import static com.charlesmadere.hummingbird.misc.Constants.FOLLOWERS;
import static com.charlesmadere.hummingbird.misc.Constants.FOLLOWING;
import static com.charlesmadere.hummingbird.misc.Constants.GROUPS;
import static com.charlesmadere.hummingbird.misc.Constants.GROUPS_SHORT;
import static com.charlesmadere.hummingbird.misc.Constants.HUMMINGBIRD_URL_HTTP;
import static com.charlesmadere.hummingbird.misc.Constants.HUMMINGBIRD_URL_HTTPS;
import static com.charlesmadere.hummingbird.misc.Constants.LIBRARY;
import static com.charlesmadere.hummingbird.misc.Constants.MANGA;
import static com.charlesmadere.hummingbird.misc.Constants.MANGA_SHORT;
import static com.charlesmadere.hummingbird.misc.Constants.MEMBERS;
import static com.charlesmadere.hummingbird.misc.Constants.NOTIFICATIONS;
import static com.charlesmadere.hummingbird.misc.Constants.QUOTES;
import static com.charlesmadere.hummingbird.misc.Constants.REVIEWS;
import static com.charlesmadere.hummingbird.misc.Constants.STORIES;
import static com.charlesmadere.hummingbird.misc.Constants.USERS;
import static com.charlesmadere.hummingbird.misc.Constants.USERS_SHORT;

public final class DeepLinkUtils {

    private static final String TAG = "DeepLinkUtils";


    @Nullable
    public static Intent[] buildActivityStack(final Activity activity) {
        if (activity == null) {
            throw new IllegalArgumentException("activity parameter must not be null");
        }

        return buildActivityStack(activity, activity.getIntent());
    }

    @Nullable
    public static Intent[] buildActivityStack(final Activity activity,
            @Nullable final Intent intent) {
        if (activity == null) {
            throw new IllegalArgumentException("activity parameter must not be null");
        }

        if (intent == null) {
            Timber.d(TAG, "Nothing to deep link to, Intent is null");
            return null;
        } else {
            return buildActivityStack(activity, intent.getData());
        }
    }

    @Nullable
    public static Intent[] buildActivityStack(final Activity activity,
            @Nullable final String uri) {
        if (activity == null) {
            throw new IllegalArgumentException("activity parameter must not be null");
        }

        if (TextUtils.isEmpty(uri) || TextUtils.getTrimmedLength(uri) == 0) {
            Timber.d(TAG, "Nothing to deep link to - Intent's URI is null, empty, or whitespace");
            return null;
        }

        Timber.d(TAG, "Attempting to deep link to URI: \"" + uri + '"');

        final String path;

        if (uri.startsWith(HUMMINGBIRD_URL_HTTP)) {
            path = uri.substring(HUMMINGBIRD_URL_HTTP.length(), uri.length());
        } else if (uri.startsWith(HUMMINGBIRD_URL_HTTPS)) {
            path = uri.substring(HUMMINGBIRD_URL_HTTPS.length(), uri.length());
        } else {
            Timber.w(TAG, "Deep link URI isn't for Hummingbird");
            return null;
        }

        if (TextUtils.isEmpty(path)) {
            Timber.d(TAG, "Deep link URI's path is null or empty");
            return null;
        }

        final String[] paths = path.split("/");

        if (paths.length == 0) {
            Timber.d(TAG, "Deep link URI's path split is empty");
            return null;
        }

        final String pageIdentifier = paths[0];

        if (pageIdentifier.equalsIgnoreCase("dashboard")) {
            Timber.d(TAG, "Deep link URI is to the user's own dashboard");
            return null;
        }

        final ArrayList<Intent> activityStack = new ArrayList<>();

        // https://hummingbird.me/anime/rwby-ii
        if (ANIME.equalsIgnoreCase(pageIdentifier) || ANIME_SHORT.equalsIgnoreCase(pageIdentifier)) {
            buildAnimeActivityStack(activity, paths, activityStack);
        }

        // https://hummingbird.me/groups/sos-brigade
        else if (GROUPS.equalsIgnoreCase(pageIdentifier) || GROUPS_SHORT.equalsIgnoreCase(pageIdentifier)) {
            buildGroupsActivityStack(activity, paths, activityStack);
        }

        // https://hummingbird.me/manga/rwby
        else if (MANGA.equalsIgnoreCase(pageIdentifier) || MANGA_SHORT.equalsIgnoreCase(pageIdentifier)) {
            buildMangaActivityStack(activity, paths, activityStack);
        }

        // https://hummingbird.me/notifications
        else if (NOTIFICATIONS.equalsIgnoreCase(pageIdentifier)) {
            buildNotificationsActivityStack(activity, activityStack);
        }

        // https://hummingbird.me/stories/8032021
        else if (STORIES.equalsIgnoreCase(pageIdentifier)) {
            buildStoriesActivityStack(activity, paths, activityStack);
        }

        // https://hummingbird.me/users/ThatLilChestnut
        else if (USERS.equalsIgnoreCase(pageIdentifier) || USERS_SHORT.equalsIgnoreCase(pageIdentifier)) {
            buildUserActivityStack(activity, paths, activityStack);
        }

        if (activityStack.isEmpty()) {
            return null;
        } else {
            final Intent[] intents = new Intent[activityStack.size()];
            activityStack.toArray(intents);
            return intents;
        }
    }

    @Nullable
    public static Intent[] buildActivityStack(final Activity activity,
            @Nullable final Uri data) {
        if (activity == null) {
            throw new IllegalArgumentException("activity parameter must not be null");
        }

        if (data == null) {
            Timber.d(TAG, "Nothing to deep link to, Intent's data is null");
            return null;
        } else {
            return buildActivityStack(activity, data.toString());
        }
    }

    private static void buildAnimeActivityStack(final Activity activity, final String[] paths,
            final ArrayList<Intent> activityStack) {
        activityStack.add(AnimeActivity.getLaunchIntent(activity, paths[1]));

        if (paths.length == 2) {
            return;
        }

        // https://hummingbird.me/anime/rwby-ii/quotes
        // https://hummingbird.me/anime/rwby-ii/reviews
        // https://hummingbird.me/anime/rwby-ii/reviews/10090

        if (QUOTES.equalsIgnoreCase(paths[2])) {
            activityStack.add(AnimeQuotesActivity.getLaunchIntent(activity, paths[1]));
        } else if (REVIEWS.equalsIgnoreCase(paths[2])) {
            activityStack.add(AnimeReviewsActivity.getLaunchIntent(activity, paths[1]));
            // https://hummingbird.me/anime/rwby-ii/reviews/10090
            if (paths.length >= 4) {
                activityStack.add(AnimeReviewActivity.getLaunchIntent(activity, paths[1],
                        paths[3]));
            }
        }
    }

    private static void buildGroupsActivityStack(final Activity activity, final String[] paths,
            final ArrayList<Intent> activityStack) {
        activityStack.add(GroupActivity.getLaunchIntent(activity, paths[1]));

        if (paths.length == 2) {
            return;
        }

        // https://hummingbird.me/groups/sos-brigade/members

        if (MEMBERS.equalsIgnoreCase(paths[2])) {
            activityStack.add(GroupMembersActivity.getLaunchIntent(activity, paths[1]));
        }
    }

    private static void buildMangaActivityStack(final Activity activity, final String[] paths,
            final ArrayList<Intent> activityStack) {
        activityStack.add(MangaActivity.getLaunchIntent(activity, paths[1]));
    }

    private static void buildNotificationsActivityStack(final Activity activity,
            final ArrayList<Intent> activityStack) {
        activityStack.add(NotificationsActivity.getLaunchIntent(activity));
    }

    private static void buildStoriesActivityStack(final Activity activity, final String[] paths,
            final ArrayList<Intent> activityStack) {
        activityStack.add(StoryActivity.getStoryIdLaunchIntent(activity, paths[1]));
    }

    private static void buildUserActivityStack(final Activity activity, final String[] paths,
            final ArrayList<Intent> activityStack) {
        if (paths.length == 2) {
            activityStack.add(UserActivity.getLaunchIntent(activity, paths[1]));
            return;
        }

        // https://hummingbird.me/users/ThatLilChestnut/groups
        if (GROUPS.equalsIgnoreCase(paths[2])) {
            activityStack.add(UserActivity.getLaunchIntent(activity, paths[2],
                    BaseUserActivity.TAB_FEED));
            return;
        }

        activityStack.add(UserActivity.getLaunchIntent(activity, paths[1]));

        // https://hummingbird.me/users/ThatLilChestnut/followers
        // https://hummingbird.me/users/ThatLilChestnut/following
        // https://hummingbird.me/users/ThatLilChestnut/library
        // https://hummingbird.me/users/ThatLilChestnut/reviews

        if (FOLLOWERS.equalsIgnoreCase(paths[2])) {
            activityStack.add(FollowersActivity.getLaunchIntent(activity, paths[1]));
        } else if (FOLLOWING.equalsIgnoreCase(paths[2])) {
            activityStack.add(FollowingActivity.getLaunchIntent(activity, paths[1]));
        } else if (LIBRARY.equalsIgnoreCase(paths[2])) {
            // https://hummingbird.me/users/ThatLilChestnut/library/manga
            if (paths.length >= 4 && MANGA.equalsIgnoreCase(paths[3])) {
                activityStack.add(MangaLibraryActivity.getLaunchIntent(activity, paths[1]));
            }
        } else if (REVIEWS.equalsIgnoreCase(paths[2])) {
            activityStack.add(UserAnimeReviewsActivity.getLaunchIntent(activity, paths[1]));
        }
    }

}
