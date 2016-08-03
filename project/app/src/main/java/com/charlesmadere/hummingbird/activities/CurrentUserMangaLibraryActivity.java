package com.charlesmadere.hummingbird.activities;

import android.content.Context;
import android.content.Intent;

import com.charlesmadere.hummingbird.adapters.MangaLibraryFragmentAdapter;
import com.charlesmadere.hummingbird.misc.CurrentUser;
import com.charlesmadere.hummingbird.views.NavigationDrawerItemView;

public class CurrentUserMangaLibraryActivity extends BaseMangaLibraryActivity {

    private static final String TAG = "CurrentUserMangaLibraryActivity";


    public static Intent getLaunchIntent(final Context context) {
        return createDrawerActivityIntent(context, CurrentUserMangaLibraryActivity.class);
    }

    @Override
    protected MangaLibraryFragmentAdapter createAdapter() {
        return new MangaLibraryFragmentAdapter(this, CurrentUser.get().getUserId(), true);
    }

    @Override
    public String getActivityName() {
        return TAG;
    }

    @Override
    protected NavigationDrawerItemView.Entry getSelectedNavigationDrawerItemViewEntry() {
        return NavigationDrawerItemView.Entry.MANGA_LIBRARY;
    }

}
