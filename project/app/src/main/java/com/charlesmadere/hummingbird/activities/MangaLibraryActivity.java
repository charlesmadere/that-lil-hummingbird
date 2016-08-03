package com.charlesmadere.hummingbird.activities;

import android.content.Context;
import android.content.Intent;

import com.charlesmadere.hummingbird.adapters.MangaLibraryFragmentAdapter;
import com.charlesmadere.hummingbird.misc.CurrentUser;

public class MangaLibraryActivity extends BaseMangaLibraryActivity {

    private static final String TAG = "MangaLibraryActivity";


    public static Intent getLaunchIntent(final Context context, final String username) {
        if (username.equalsIgnoreCase(CurrentUser.get().getUserId())) {
            return CurrentUserMangaLibraryActivity.getLaunchIntent(context);
        } else {
            return new Intent(context, MangaLibraryActivity.class)
                    .putExtra(EXTRA_USERNAME, username);
        }
    }

    @Override
    protected MangaLibraryFragmentAdapter createAdapter() {
        return new MangaLibraryFragmentAdapter(this, mUsername, false);
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
