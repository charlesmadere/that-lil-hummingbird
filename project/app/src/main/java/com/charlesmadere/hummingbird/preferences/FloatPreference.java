package com.charlesmadere.hummingbird.preferences;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class FloatPreference extends Preference<Float> {

    public FloatPreference(final String name, final String key, @Nullable final Float defaultValue) {
        super(name, key, defaultValue);
    }

    @Override
    public boolean exists() {
        return readSharedPreferences().contains(getKey());
    }

    @Nullable
    @Override
    public Float get() {
        if (exists()) {
            // at this point, returning the fallback value is impossible
            return readSharedPreferences().getFloat(getKey(), 0f);
        } else {
            return getDefaultValue();
        }
    }

    @Override
    protected void performSet(@NonNull final Float newValue) {
        writeSharedPreferences().putFloat(getKey(), newValue).apply();
    }

}
