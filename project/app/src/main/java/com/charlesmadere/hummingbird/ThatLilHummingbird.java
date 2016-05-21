package com.charlesmadere.hummingbird;

import android.app.Activity;
import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

import com.charlesmadere.hummingbird.activities.HomeActivity;
import com.charlesmadere.hummingbird.misc.ActivityRegister;
import com.charlesmadere.hummingbird.misc.OkHttpUtils;
import com.charlesmadere.hummingbird.misc.Timber;
import com.charlesmadere.hummingbird.models.NightMode;
import com.crashlytics.android.Crashlytics;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

import java.util.ArrayList;

import io.fabric.sdk.android.Fabric;

public class ThatLilHummingbird extends Application {

    private static final String TAG = "ThatLilHummingbird";

    private static ThatLilHummingbird sInstance;


    static {
        // noinspection WrongConstant
        AppCompatDelegate.setDefaultNightMode(NightMode.getDefault().getThemeValue());
    }

    public static void clearCaches() {
        Fresco.getImagePipeline().clearCaches();
    }

    public static ThatLilHummingbird get() {
        return sInstance;
    }

    public static void restart() {
        Timber.d(TAG, "Application is restarting");
        final ArrayList<Activity> activities = ActivityRegister.get();

        if (activities == null || activities.isEmpty()) {
            return;
        }

        final Activity activity = activities.get(0);
        activity.startActivity(HomeActivity.getNewTaskLaunchIntent(activity));

        for (final Activity a : activities) {
            a.finish();
        }

        activities.clear();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        Timber.d(TAG, "Application created (debug: " + BuildConfig.DEBUG + ')');

        Fabric.with(this, new Crashlytics());

        final ImagePipelineConfig config = OkHttpImagePipelineConfigFactory
                .newBuilder(this, OkHttpUtils.getOkHttpClient())
                .build();

        Fresco.initialize(this, config);
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
