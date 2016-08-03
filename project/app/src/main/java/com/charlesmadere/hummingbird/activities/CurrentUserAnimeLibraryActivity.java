package com.charlesmadere.hummingbird.activities;

import android.content.Context;
import android.content.Intent;

import com.charlesmadere.hummingbird.adapters.AnimeLibraryFragmentAdapter;
import com.charlesmadere.hummingbird.misc.CurrentUser;
import com.charlesmadere.hummingbird.views.NavigationDrawerItemView;

public class CurrentUserAnimeLibraryActivity extends BaseAnimeLibraryActivity {

    private static final String TAG = "CurrentUserAnimeLibraryActivity";


    public static Intent getLaunchIntent(final Context context) {
        return createDrawerActivityIntent(context, CurrentUserAnimeLibraryActivity.class);
    }

    @Override
    protected AnimeLibraryFragmentAdapter createAdapter() {
        return new AnimeLibraryFragmentAdapter(this, CurrentUser.get().getUserId(), true);
    }

    @Override
    public String getActivityName() {
        return TAG;
    }

    @Override
    protected NavigationDrawerItemView.Entry getSelectedNavigationDrawerItemViewEntry() {
        return NavigationDrawerItemView.Entry.ANIME_LIBRARY;
    }

}
