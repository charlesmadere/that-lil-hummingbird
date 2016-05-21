package com.charlesmadere.hummingbird.misc;

import com.charlesmadere.hummingbird.ThatLilHummingbird;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

public final class OkHttpUtils {

    private static final String TAG = "OkHttpUtils";
    private static final long CACHE_MAX_SIZE = 1024L * 1024L; // 1 Megabyte

    private static OkHttpClient sOkHttpClient;


    public static synchronized OkHttpClient getOkHttpClient() {
        if (sOkHttpClient == null) {
            Timber.d(TAG, "Creating OkHttpClient instance");
            sOkHttpClient = new OkHttpClient.Builder()
                    .cache(new Cache(ThatLilHummingbird.get().getCacheDir(), CACHE_MAX_SIZE))
                    .build();
        }

        return sOkHttpClient;
    }

}
