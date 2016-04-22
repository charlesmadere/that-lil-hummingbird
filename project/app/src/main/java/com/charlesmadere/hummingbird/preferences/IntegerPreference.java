package com.charlesmadere.hummingbird.preferences;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class IntegerPreference extends Preference<Integer> {

    public IntegerPreference(final String name, final String key, @Nullable final Integer defaultValue) {
        super(name, key, defaultValue);
    }

    @Override
    public boolean exists() {
        return readSharedPreferences().contains(getKey());
    }

    @Nullable
    @Override
    public Integer get() {
        if (exists()) {
            // at this point, returning the fallback value is impossible
            return readSharedPreferences().getInt(getKey(), 0);
        } else {
            return getDefaultValue();
        }
    }

    @Override
    protected void performSet(@NonNull final Integer newValue) {
        writeSharedPreferences().putInt(getKey(), newValue).apply();
    }

}
