package com.charlesmadere.hummingbird.preferences;

import android.support.annotation.NonNull;

import com.charlesmadere.hummingbird.misc.GsonUtils;
import com.google.gson.Gson;

import java.lang.reflect.Type;

public class GsonPreference<T> extends Preference<T> {

    private final StringPreference mStringPreference;
    private final Type mType;


    public GsonPreference(final String name, final String key, final T defaultValue,
            final Type type) {
        super(name, key, defaultValue);
        mType = type;
        mStringPreference = new StringPreference(name, key, defaultValue == null ? null :
                getGson().toJson(defaultValue, type));
    }

    @Override
    public boolean exists() {
        return mStringPreference.exists();
    }

    @Override
    public T get() {
        if (exists()) {
            return getGson().fromJson(mStringPreference.get(), mType);
        } else {
            return getDefaultValue();
        }
    }

    public final Gson getGson() {
        return GsonUtils.getGson();
    }

    @Override
    protected void performSet(@NonNull final T newValue) {
        mStringPreference.performSet(getGson().toJson(newValue, mType));
    }

}
