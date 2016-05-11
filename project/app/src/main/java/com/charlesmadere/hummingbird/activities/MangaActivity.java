package com.charlesmadere.hummingbird.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.charlesmadere.hummingbird.R;

public class MangaActivity extends BaseDrawerActivity {

    private static final String TAG = "MangaActivity";
    private static final String CNAME = MangaActivity.class.getCanonicalName();
    private static final String EXTRA_MANGA_ID = CNAME + ".MangaId";
    private static final String EXTRA_MANGA_NAME = CNAME + ".MangaName";

    private String mMangaId;


    public static Intent getLaunchIntent(final Context context, final String mangaId,
            final String mangaName) {
        return new Intent(context, MangaActivity.class)
                .putExtra(EXTRA_MANGA_ID, mangaId)
                .putExtra(EXTRA_MANGA_NAME, mangaName);
    }

    @Override
    public String getActivityName() {
        return TAG;
    }

    @Override
    protected boolean isUpNavigationEnabled() {
        return true;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manga);

        final Intent intent = getIntent();
        setTitle(intent.getStringExtra(EXTRA_MANGA_NAME));
        mMangaId = intent.getStringExtra(EXTRA_MANGA_ID);
    }

}
