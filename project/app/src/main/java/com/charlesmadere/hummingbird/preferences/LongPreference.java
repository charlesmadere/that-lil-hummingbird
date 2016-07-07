package com.charlesmadere.hummingbird.preferences;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class LongPreference extends Preference<Long> {

    public LongPreference(final String name, final String key, @Nullable final Long defaultValue) {
        super(name, key, defaultValue);
    }

    @Override
    public boolean exists() {
        return readSharedPreferences().contains(getKey()) || getDefaultValue() != null;
    }

    @Nullable
    @Override
    public Long get() {
        if (exists()) {
            // at this point, returning the fallback value is impossible
            return readSharedPreferences().getLong(getKey(), 0L);
        } else {
            return getDefaultValue();
        }
    }

    @Override
    protected void performSet(@NonNull final Long newValue) {
        writeSharedPreferences().putLong(getKey(), newValue).apply();
    }

}
