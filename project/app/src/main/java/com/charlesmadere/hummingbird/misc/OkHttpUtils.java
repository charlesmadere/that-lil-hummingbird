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
    private static PersistentCookieJar sCookieJar;


    public static synchronized PersistentCookieJar getCookieJar() {
        if (sCookieJar == null) {
            Timber.d(TAG, "creating PersistentCookieJar instance");
            sCookieJar = new PersistentCookieJar(new SetCookieCache(),
                    new SharedPrefsCookiePersistor(ThatLilHummingbird.get()));
        }

        return sCookieJar;
    }

    public static synchronized OkHttpClient getOkHttpClient() {
        if (sOkHttpClient == null) {
            Timber.d(TAG, "creating OkHttpClient instance");

            final HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            sOkHttpClient = new OkHttpClient.Builder()
                    .cache(new Cache(ThatLilHummingbird.get().getCacheDir(), CACHE_MAX_SIZE))
                    .cookieJar(getCookieJar())
                    .addInterceptor(interceptor)
                    .build();
        }

        return sOkHttpClient;
    }

}
