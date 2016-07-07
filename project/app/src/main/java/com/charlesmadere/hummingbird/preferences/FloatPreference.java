package com.charlesmadere.hummingbird.preferences;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class FloatPreference extends Preference<Float> {

    public FloatPreference(final String name, final String key,
            @Nullable final Float defaultValue) {
        super(name, key, defaultValue);
    }

    @Override
    public boolean exists() {
        return contains() || getDefaultValue() != null;
    }

    @Nullable
    @Override
    public Float get() {
        if (contains()) {
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
