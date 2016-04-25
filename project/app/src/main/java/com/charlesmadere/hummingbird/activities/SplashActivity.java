package com.charlesmadere.hummingbird.activities;

import android.os.Bundle;

import com.charlesmadere.hummingbird.R;

public class SplashActivity extends BaseActivity {

    private static final String TAG = "SplashActivity";


    @Override
    public String getActivityName() {
        return TAG;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

}
