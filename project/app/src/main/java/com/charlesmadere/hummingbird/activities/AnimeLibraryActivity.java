package com.charlesmadere.hummingbird.activities;

import android.content.Context;
import android.content.Intent;

import com.charlesmadere.hummingbird.adapters.AnimeLibraryFragmentAdapter;
import com.charlesmadere.hummingbird.misc.CurrentUser;

public class AnimeLibraryActivity extends BaseAnimeLibraryActivity {

    private static final String TAG = "AnimeLibraryActivity";


    public static Intent getLaunchIntent(final Context context, final String username) {
        if (username.equalsIgnoreCase(CurrentUser.get().getUserId())) {
            return CurrentUserAnimeLibraryActivity.getLaunchIntent(context);
        } else {
            return new Intent(context, AnimeLibraryActivity.class)
                    .putExtra(EXTRA_USERNAME, username);
        }
    }

    @Override
    protected AnimeLibraryFragmentAdapter createAdapter() {
        return new AnimeLibraryFragmentAdapter(this, mUsername, false);
    }

    @Override
    public String getActivityName() {
        return TAG;
    }

    @Override
    protected boolean isUpNavigationEnabled() {
        return true;
    }

}
