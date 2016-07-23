package com.charlesmadere.hummingbird.misc;

import com.charlesmadere.hummingbird.ThatLilHummingbird;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public final class OkHttpUtils {

    private static final String TAG = "OkHttpUtils";
    private static final long CACHE_MAX_SIZE = 1024L * 1024L * 8L; // 8 megabytes

    private static OkHttpClient sOkHttpClient;
    private static PersistentCookieJar sPersistentCookieJar;


    public static synchronized OkHttpClient getOkHttpClient() {
        if (sOkHttpClient == null) {
            Timber.d(TAG, "creating OkHttpClient instance");

            final HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            sOkHttpClient = new OkHttpClient.Builder()
                    .cache(new Cache(ThatLilHummingbird.get().getCacheDir(), CACHE_MAX_SIZE))
                    .cookieJar(getPersistentCookieJar())
                    .addInterceptor(interceptor)
                    .build();
        }

        return sOkHttpClient;
    }

    public static synchronized PersistentCookieJar getPersistentCookieJar() {
        if (sPersistentCookieJar == null) {
            Timber.d(TAG, "creating PersistentCookieJar instance");
            sPersistentCookieJar = new PersistentCookieJar(new SetCookieCache(),
                    new SharedPrefsCookiePersistor(ThatLilHummingbird.get()));
        }

        return sPersistentCookieJar;
    }

}
