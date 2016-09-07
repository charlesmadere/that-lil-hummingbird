package com.charlesmadere.hummingbird;

import android.app.Activity;
import android.app.Application;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDelegate;

import com.charlesmadere.hummingbird.misc.ActivityRegister;
import com.charlesmadere.hummingbird.misc.Constants;
import com.charlesmadere.hummingbird.misc.MiscUtils;
import com.charlesmadere.hummingbird.misc.ObjectCache;
import com.charlesmadere.hummingbird.misc.OkHttpUtils;
import com.charlesmadere.hummingbird.misc.Timber;
import com.charlesmadere.hummingbird.models.LaunchScreen;
import com.charlesmadere.hummingbird.models.NightMode;
import com.charlesmadere.hummingbird.preferences.Preferences;
import com.crashlytics.android.Crashlytics;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

import java.io.IOException;
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

        try {
            OkHttpUtils.getOkHttpClient().cache().evictAll();
        } catch (final IOException e) {
            Timber.w(TAG, "Exception when clearing OkHttp cache", e);
        }
    }

    public static ThatLilHummingbird get() {
        return sInstance;
    }

    public static void restart() {
        Timber.d(TAG, "Application is restarting");
        final ArrayList<Activity> activities = ActivityRegister.get();

        if (activities == null || activities.isEmpty()) {
            Timber.w(TAG, "No Activity available to restart with");
            return;
        }

        final Activity activity = activities.get(0);
        final LaunchScreen launchScreen = Preferences.General.DefaultLaunchScreen.get();
        // noinspection ConstantConditions
        activity.startActivity(launchScreen.getRestartAppLaunchIntent(activity));

        for (final Activity a : activities) {
            a.finish();
        }

        activities.clear();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;

        Fabric.with(this, new Crashlytics());

        Timber.d(TAG, "Application created");
        Crashlytics.setBool(Constants.DEBUG, BuildConfig.DEBUG);
        Crashlytics.setBool(Constants.LOW_RAM_DEVICE, MiscUtils.isLowRamDevice());

        final ImagePipelineConfig config = OkHttpImagePipelineConfigFactory
                .newBuilder(this, OkHttpUtils.getOkHttpClient())
                .setBitmapsConfig(Bitmap.Config.RGB_565)
                .build();

        Fresco.initialize(this, config);

        final Integer previousLaunchVersion = Preferences.General.PreviousLaunchVersion.get();
        if (previousLaunchVersion == null || previousLaunchVersion < BuildConfig.VERSION_CODE) {
            onUpgrade(previousLaunchVersion);
        }
        Preferences.General.PreviousLaunchVersion.set(BuildConfig.VERSION_CODE);
    }

    @Override
    public void onTrimMemory(final int level) {
        super.onTrimMemory(level);

        if (level >= TRIM_MEMORY_BACKGROUND) {
            Timber.clearEntries();
            Fresco.getImagePipeline().clearMemoryCaches();
            ObjectCache.clear();
        } else if (level >= TRIM_MEMORY_UI_HIDDEN) {
            ObjectCache.trim();
        }

        Timber.d(TAG, "onTrimMemory(): " + level);
    }

    public void onUpgrade(@Nullable final Integer previousVersion) {
        Timber.d(TAG, "Upgrading from app version " + previousVersion + " to " +
                BuildConfig.VERSION_CODE);

        if (previousVersion == null || previousVersion < 7) {
            Preferences.eraseAll();
        }

        if (previousVersion == null || previousVersion < 29) {
            Preferences.General.DefaultLaunchScreen.delete();
        }
    }

}
