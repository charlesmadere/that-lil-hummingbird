package com.charlesmadere.hummingbird.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.adapters.NewsFeedFragmentAdapter;
import com.charlesmadere.hummingbird.fragments.FeedPostFragment;
import com.charlesmadere.hummingbird.misc.CurrentUser;
import com.charlesmadere.hummingbird.models.FeedPost;
import com.charlesmadere.hummingbird.models.LaunchScreen;
import com.charlesmadere.hummingbird.models.UserDigest;
import com.charlesmadere.hummingbird.networking.Api;
import com.charlesmadere.hummingbird.preferences.Preferences;
import com.charlesmadere.hummingbird.views.NavigationDrawerItemView;

public class ActivityFeedActivity extends BaseUserActivity {

    private static final String TAG = "ActivityFeedActivity";


    public static Intent getLaunchIntent(final Context context) {
        return getLaunchIntent(context, null);
    }

    public static Intent getLaunchIntent(final Context context, @Nullable final Integer initialTab) {
        final Intent intent = createDrawerActivityIntent(context, ActivityFeedActivity.class);

        if (Preferences.General.DefaultLaunchScreen.get() == LaunchScreen.ACTIVITY_FEED) {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }

        if (initialTab != null) {
            intent.putExtra(EXTRA_INITIAL_TAB, initialTab);
        }

        return intent;
    }

    @Override
    public String getActivityName() {
        return TAG;
    }

    @Override
    protected NavigationDrawerItemView.Entry getSelectedNavigationDrawerItemViewEntry() {
        return NavigationDrawerItemView.Entry.ACTIVITY_FEED;
    }

    @Override
    public UserDigest getUserDigest() {
        return CurrentUser.get();
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);
        setAdapter(new NewsFeedFragmentAdapter(this));
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.activity_news_feed, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onFeedPostSubmit() {
        final FeedPostFragment fragment = (FeedPostFragment) getSupportFragmentManager()
                .findFragmentByTag(FeedPostFragment.TAG);
        final FeedPost post = fragment.getFeedPost(CurrentUser.get().getUserId());

        if (post == null) {
            return;
        }

        Api.postToFeed(post, new FeedPostListener(this));
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.miAnimeLibrary:
                startActivity(CurrentUserAnimeLibraryActivity.getLaunchIntent(this));
                break;

            case R.id.miMangaLibrary:
                startActivity(CurrentUserMangaLibraryActivity.getLaunchIntent(this));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setUserDigest(final UserDigest userDigest) {
        CurrentUser.set(userDigest);
    }

}
