package com.charlesmadere.hummingbird;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

import com.charlesmadere.hummingbird.misc.Timber;
import com.facebook.drawee.backends.pipeline.Fresco;

public class ThatLilHummingbird extends Application {

    private static final String TAG = "ThatLilHummingbird";

    private static ThatLilHummingbird sInstance;


    static {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
    }

    public static ThatLilHummingbird get() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        Timber.d(TAG, "Application created (debug: " + BuildConfig.DEBUG + ')');
        Fresco.initialize(this);
    }

    @Override
    public void onTrimMemory(final int level) {
        super.onTrimMemory(level);

        if (level >= TRIM_MEMORY_BACKGROUND) {
            Fresco.getImagePipeline().clearMemoryCaches();
            Timber.clearEntries();
        }

        Timber.d(TAG, "onTrimMemory(): " + level);
    }

}
