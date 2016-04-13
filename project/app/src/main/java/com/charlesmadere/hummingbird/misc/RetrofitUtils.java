package com.charlesmadere.hummingbird.misc;

import com.charlesmadere.hummingbird.networking.HummingbirdApi;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class RetrofitUtils {

    private static final String BASE_URL = "https://hummingbird.me/api/";
    private static final String TAG = "RetrofitUtils";

    private static HummingbirdApi sHummingbirdApi;
    private static Retrofit sRetrofit;


    public static synchronized HummingbirdApi getHummingbirdApi() {
        if (sHummingbirdApi == null) {
            Timber.d(TAG, "creating HummingbirdApi instance");
            final Retrofit retrofit = getRetrofit();
            sHummingbirdApi = retrofit.create(HummingbirdApi.class);
        }

        return sHummingbirdApi;
    }

    public static synchronized Retrofit getRetrofit() {
        if (sRetrofit == null) {
            Timber.d(TAG, "creating Retrofit instance");
            final Gson gson = GsonUtils.getGson();
            sRetrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addConverterFactory(new EnumConverterFactory())
                    .baseUrl(BASE_URL)
                    .build();
        }

        return sRetrofit;
    }

    private static <E extends Enum<E>> String getSerializedName(final E e) {
        try {
            return e.getClass().getField(e.name()).getAnnotation(SerializedName.class).value();
        } catch (final NoSuchFieldException e1) {
            return e.name();
        }
    }

    private static final class EnumConverterFactory extends Converter.Factory {
        @Override
        public Converter<?, String> stringConverter(final Type type, final Annotation[] annotations,
                final Retrofit retrofit) {
            Converter<?, String> converter = null;

            if (type instanceof Class && ((Class<?>)type).isEnum()) {
                converter = new Converter<Object, String>() {
                    @Override
                    public String convert(final Object value) throws IOException {
                        return getSerializedName((Enum) value);
                    }
                };
            }

            return converter;
        }
    }

}
