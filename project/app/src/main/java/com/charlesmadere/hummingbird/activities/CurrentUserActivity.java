package com.charlesmadere.hummingbird.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;

import com.charlesmadere.hummingbird.R;
import com.charlesmadere.hummingbird.misc.CurrentUser;
import com.charlesmadere.hummingbird.models.UserDigest;

public class CurrentUserActivity extends BaseUserActivity {

    private static final String TAG = "CurrentUserActivity";


    public static Intent getLaunchIntent(final Context context) {
        return getLaunchIntent(context, null);
    }

    public static Intent getLaunchIntent(final Context context, @Nullable final Integer initialTab) {
        final Intent intent = createDrawerActivityIntent(context, CurrentUserActivity.class);

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
    public UserDigest getUserDigest() {
        return CurrentUser.get();
    }

    @Override
    public String getUsername() {
        return getUserDigest().getUserId();
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setUserDigest(getUserDigest());
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.activity_current_user, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.miAnimeLibrary:
                startActivity(CurrentUserAnimeLibraryActivity.getLaunchIntent(this));
                return true;

            case R.id.miMangaLibrary:
                startActivity(CurrentUserMangaLibraryActivity.getLaunchIntent(this));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setUserDigest(final UserDigest userDigest) {
        CurrentUser.set(userDigest);
        super.setUserDigest(userDigest);
    }

}
