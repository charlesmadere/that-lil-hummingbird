package com.charlesmadere.hummingbird.activities;

import android.content.Context;
import android.content.Intent;

import com.charlesmadere.hummingbird.adapters.AnimeLibraryFragmentAdapter;
import com.charlesmadere.hummingbird.misc.CurrentUser;
import com.charlesmadere.hummingbird.models.LaunchScreen;
import com.charlesmadere.hummingbird.preferences.Preferences;
import com.charlesmadere.hummingbird.views.NavigationDrawerItemView;

public class CurrentUserAnimeLibraryActivity extends BaseAnimeLibraryActivity {

    private static final String TAG = "CurrentUserAnimeLibraryActivity";


    public static Intent getLaunchIntent(final Context context) {
        final Intent intent = createDrawerActivityIntent(context, CurrentUserAnimeLibraryActivity.class);

        if (Preferences.General.DefaultLaunchScreen.get() == LaunchScreen.ANIME_LIBRARY) {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }

        intent.putExtra(EXTRA_USERNAME, CurrentUser.get().getUserId());
        return intent;
    }

    @Override
    protected AnimeLibraryFragmentAdapter createAdapter() {
        return new AnimeLibraryFragmentAdapter(this);
    }

    @Override
    public String getActivityName() {
        return TAG;
    }

    @Override
    protected NavigationDrawerItemView.Entry getSelectedNavigationDrawerItemViewEntry() {
        return NavigationDrawerItemView.Entry.ANIME_LIBRARY;
    }

    @Override
    public boolean isEditableLibrary() {
        return true;
    }

}
