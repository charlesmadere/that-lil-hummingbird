package com.charlesmadere.hummingbird.activities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.charlesmadere.hummingbird.adapters.AnimeLibraryFragmentAdapter;
import com.charlesmadere.hummingbird.misc.CurrentUser;
import com.charlesmadere.hummingbird.models.UiColorSet;

public class AnimeLibraryActivity extends BaseAnimeLibraryActivity {

    private static final String TAG = "AnimeLibraryActivity";


    public static Intent getLaunchIntent(final Context context, final String username) {
        return getLaunchIntent(context, username, null);
    }

    public static Intent getLaunchIntent(final Context context, final String username,
            @Nullable final UiColorSet uiColorSet) {
        if (username.equalsIgnoreCase(CurrentUser.get().getUserId())) {
            return CurrentUserAnimeLibraryActivity.getLaunchIntent(context);
        } else {
            final Intent intent = new Intent(context, AnimeLibraryActivity.class)
                    .putExtra(EXTRA_USERNAME, username);

            if (uiColorSet != null) {
                intent.putExtra(EXTRA_UI_COLOR_SET, uiColorSet);
            }

            return intent;
        }
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
    public boolean isEditableLibrary() {
        return false;
    }

    @Override
    protected boolean isUpNavigationEnabled() {
        return true;
    }

}
