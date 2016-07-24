package com.charlesmadere.hummingbird.activities;

import android.os.Bundle;

import com.charlesmadere.hummingbird.R;

public class MediaStoryActivity extends BaseDrawerActivity {

    private static final String TAG = "MediaStoryActivity";


    @Override
    public String getActivityName() {
        return TAG;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_story);
    }

}
