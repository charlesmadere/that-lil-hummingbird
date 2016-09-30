package com.charlesmadere.hummingbird.activities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.charlesmadere.hummingbird.adapters.MangaLibraryFragmentAdapter;
import com.charlesmadere.hummingbird.misc.CurrentUser;
import com.charlesmadere.hummingbird.models.UiColorSet;

public class MangaLibraryActivity extends BaseMangaLibraryActivity {

    private static final String TAG = "MangaLibraryActivity";


    public static Intent getLaunchIntent(final Context context, final String userId) {
        return getLaunchIntent(context, userId, null);
    }

    public static Intent getLaunchIntent(final Context context, final String userId,
            @Nullable final UiColorSet uiColorSet) {
        if (userId.equalsIgnoreCase(CurrentUser.get().getUserId())) {
            return CurrentUserMangaLibraryActivity.getLaunchIntent(context);
        } else {
            final Intent intent = new Intent(context, MangaLibraryActivity.class)
                    .putExtra(EXTRA_USERNAME, userId);

            if (uiColorSet != null) {
                intent.putExtra(EXTRA_UI_COLOR_SET, uiColorSet);
            }

            return intent;
        }
    }

    @Override
    protected MangaLibraryFragmentAdapter createAdapter() {
        return new MangaLibraryFragmentAdapter(this);
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
