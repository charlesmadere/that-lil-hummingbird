package com.charlesmadere.hummingbird.misc;

import com.charlesmadere.hummingbird.ThatLilHummingbird;
import com.charlesmadere.hummingbird.preferences.Preferences;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import java.io.IOException;

import okhttp3.Cache;
import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public final class OkHttpUtils {

    private static final String TAG = "OkHttpUtils";
    private static final long CACHE_MAX_SIZE = 1024L * 1024L * 8L; // 8 megabytes

    private static CookieJar sCookieJar;
    private static HttpLoggingInterceptor sHttpLoggingInterceptor;
    private static Interceptor sCsrfTokenInterceptor;
    private static OkHttpClient sOkHttpClient;


    public static synchronized CookieJar getCookieJar() {
        if (sCookieJar == null) {
            Timber.d(TAG, "creating CookieJar instance");
            sCookieJar = new PersistentCookieJar(new SetCookieCache(),
                    new SharedPrefsCookiePersistor(ThatLilHummingbird.get()));
        }

        return sCookieJar;
    }

    private static synchronized Interceptor getCsrfTokenInterceptor() {
        if (sCsrfTokenInterceptor == null) {
            sCsrfTokenInterceptor = new Interceptor() {
                @Override
                public Response intercept(final Chain chain) throws IOException {
                    final Request request = chain.request();

                    if (Preferences.Account.CsrfToken.exists()) {
                        final Request newRequest = request.newBuilder()
                                .addHeader("X-CSRF-Token", Preferences.Account.CsrfToken.get())
                                .build();

                        return chain.proceed(newRequest);
                    } else {
                        return chain.proceed(request);
                    }
                }
            };
        }

        return sCsrfTokenInterceptor;
    }

    private static synchronized HttpLoggingInterceptor getHttpLoggingInterceptor() {
        if (sHttpLoggingInterceptor == null) {
            sHttpLoggingInterceptor = new HttpLoggingInterceptor();
            sHttpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        }

        return sHttpLoggingInterceptor;
    }

    public static synchronized OkHttpClient getOkHttpClient() {
        if (sOkHttpClient == null) {
            Timber.d(TAG, "creating OkHttpClient instance");

            sOkHttpClient = new OkHttpClient.Builder()
                    .cache(new Cache(ThatLilHummingbird.get().getCacheDir(), CACHE_MAX_SIZE))
                    .cookieJar(getCookieJar())
                    .addInterceptor(getHttpLoggingInterceptor())
                    .addInterceptor(getCsrfTokenInterceptor())
                    .build();
        }

        return sOkHttpClient;
    }

}
