package com.charlesmadere.hummingbird;

import android.app.Activity;
import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

import com.charlesmadere.hummingbird.misc.ActivityRegister;
import com.charlesmadere.hummingbird.misc.Timber;
import com.charlesmadere.hummingbird.models.NightMode;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.ArrayList;

public class ThatLilHummingbird extends Application {

    private static final String TAG = "ThatLilHummingbird";

    private static ThatLilHummingbird sInstance;


    static {
        // noinspection WrongConstant
        AppCompatDelegate.setDefaultNightMode(NightMode.getDefault().getThemeValue());
    }

    public static ThatLilHummingbird get() {
        return sInstance;
    }

    public static void restart() {
        final ArrayList<Activity> activities = ActivityRegister.get();

        if (activities == null || activities.isEmpty()) {
            return;
        }

        for (final Activity activity : activities) {
            activity.finish();
        }

        activities.clear();
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