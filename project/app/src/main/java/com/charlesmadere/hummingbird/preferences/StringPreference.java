package com.charlesmadere.hummingbird.preferences;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

public class StringPreference extends Preference<String> {

    public StringPreference(final String name, final String key, @Nullable final String defaultValue) {
        super(name, key, defaultValue);
    }

    @Override
    public boolean exists() {
        return !TextUtils.isEmpty(get());
    }

    @Nullable
    @Override
    public String get() {
        return readSharedPreferences().getString(getKey(), getDefaultValue());
    }

    @Override
    protected void performSet(@NonNull final String newValue) {
        writeSharedPreferences().putString(getKey(), newValue).apply();
    }

}
