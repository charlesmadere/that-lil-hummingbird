package com.charlesmadere.hummingbird.misc;

import com.charlesmadere.hummingbird.networking.HummingbirdApi;
import com.charlesmadere.hummingbird.networking.WebsiteApi;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class RetrofitUtils {

    private static final String TAG = "RetrofitUtils";

    private static HummingbirdApi sHummingbirdApi;
    private static Retrofit sHummingbirdRetrofit;
    private static Retrofit sWebsiteRetrofit;
    private static WebsiteApi sWebsiteApi;


    public static synchronized HummingbirdApi getHummingbirdApi() {
        if (sHummingbirdApi == null) {
            Timber.d(TAG, "creating Hummingbird Api instance");
            final Retrofit retrofit = getHummingbirdRetrofit();
            sHummingbirdApi = retrofit.create(HummingbirdApi.class);
        }

        return sHummingbirdApi;
    }

    public static synchronized Retrofit getHummingbirdRetrofit() {
        if (sHummingbirdRetrofit == null) {
            Timber.d(TAG, "creating Hummingbird Retrofit instance");
            sHummingbirdRetrofit = new Retrofit.Builder()
                    .client(OkHttpUtils.getOkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create(GsonUtils.getGson()))
                    .addConverterFactory(new EnumConverterFactory())
                    .baseUrl(Constants.HUMMINGBIRD_URL)
                    .build();
        }

        return sHummingbirdRetrofit;
    }

    private static <E extends Enum<E>> String getSerializedName(final E e) {
        try {
            return e.getClass().getField(e.name()).getAnnotation(SerializedName.class).value();
        } catch (final NoSuchFieldException e1) {
            return e.name();
        }
    }

    public static synchronized WebsiteApi getWebsiteApi() {
        if (sWebsiteApi == null) {
            Timber.d(TAG, "creating Website Api instance");
            final Retrofit retrofit = getWebsiteRetrofit();
            sWebsiteApi = retrofit.create(WebsiteApi.class);
        }

        return sWebsiteApi;
    }

    public static synchronized Retrofit getWebsiteRetrofit() {
        if (sWebsiteRetrofit == null) {
            Timber.d(TAG, "creating Website Retrofit instance");
            sWebsiteRetrofit = new Retrofit.Builder()
                    .client(OkHttpUtils.getOkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create(GsonUtils.getGson()))
                    .addConverterFactory(new EnumConverterFactory())
                    .baseUrl(Constants.WEBSITE_URL)
                    .build();
        }

        return sWebsiteRetrofit;
    }

    private static final class EnumConverterFactory extends Converter.Factory {
        @Override
        public Converter<?, String> stringConverter(final Type type, final Annotation[] annotations,
                final Retrofit retrofit) {
            Converter<?, String> converter = null;

            if (type instanceof Class && ((Class<?>)type).isEnum()) {
                converter = new Converter<Object, String>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public String convert(final Object value) throws IOException {
                        return getSerializedName((Enum) value);
                    }
                };
            }

            return converter;
        }
    }

}
