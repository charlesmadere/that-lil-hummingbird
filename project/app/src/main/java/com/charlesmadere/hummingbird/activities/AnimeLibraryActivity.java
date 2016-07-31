package com.charlesmadere.hummingbird.activities;

import android.os.Bundle;

import com.charlesmadere.hummingbird.R;

public class AnimeLibraryActivity extends BaseDrawerActivity {

    private static final String TAG = "AnimeLibraryActivity";


    @Override
    public String getActivityName() {
        return TAG;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_library);
    }

}
