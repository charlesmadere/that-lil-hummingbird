package com.charlesmadere.hummingbird.preferences;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class BooleanPreference extends Preference<Boolean> {

    public BooleanPreference(final String name, final String key, @Nullable final Boolean defaultValue) {
        super(name, key, defaultValue);
    }

    @Override
    public boolean exists() {
        return readSharedPreferences().contains(getKey());
    }

    @Nullable
    @Override
    public Boolean get() {
        if (exists()) {
            // at this point, returning the fallback value is impossible
            return readSharedPreferences().getBoolean(getKey(), false);
        } else {
            return getDefaultValue();
        }
    }

    @Override
    protected void performSet(@NonNull final Boolean newValue) {
        writeSharedPreferences().putBoolean(getKey(), newValue).apply();
    }

    public void toggle() {
        final Boolean value = get();

        if (Boolean.TRUE.equals(value)) {
            set(Boolean.FALSE);
        } else if (Boolean.FALSE.equals(value)) {
            set(Boolean.TRUE);
        }
    }

}
