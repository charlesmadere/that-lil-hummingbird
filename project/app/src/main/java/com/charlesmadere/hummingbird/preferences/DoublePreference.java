package com.charlesmadere.hummingbird.preferences;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class DoublePreference extends Preference<Double> {

    private final StringPreference mStringPreference;


    public DoublePreference(final String name, final String key,
            @Nullable final Double defaultValue) {
        super(name, key, defaultValue);
        mStringPreference = new StringPreference(name, key, defaultValue == null ? null :
                defaultValue.toString());
    }

    @Override
    protected boolean contains() {
        return mStringPreference.contains();
    }

    @Override
    public boolean exists() {
        return mStringPreference.exists();
    }

    @Nullable
    @Override
    public Double get() {
        if (contains()) {
            final String value = mStringPreference.get();

            try {
                return Double.valueOf(value);
            } catch (final NumberFormatException e) {
                // this Exception should never happen
                throw new RuntimeException("Error parsing DoublePreference's (" + getName()
                        + ':' + getKey() + ") value: \"" + value + '"', e);
            }
        } else {
            return getDefaultValue();
        }
    }

    @Override
    protected void performSet(@NonNull final Double newValue) {
        mStringPreference.performSet(newValue.toString());
    }

}
