package com.charlesmadere.hummingbird.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.charlesmadere.hummingbird.ThatLilHummingbird;

import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.LinkedList;

public abstract class Preference<T> {

    private final LinkedList<WeakReference<OnPreferenceChangeListener<T>>> mListeners;
    private final String mKey;
    private final String mName;
    private final T mDefaultValue;


    /**
     * constructs a new Preference object
     *
     * @param name
     * the name of the SharedPreferences file to operate on
     *
     * @param key
     * the SharedPreference key or ID
     *
     * @param defaultValue
     * the value that will be used if no stored value exists
     *
     * @throws IllegalArgumentException
     * if name or key are null / empty, then this Exception will be thrown
     */
    public Preference(@NonNull final String name, @NonNull final String key,
            @Nullable final T defaultValue) throws IllegalArgumentException {
        if (TextUtils.isEmpty(name)) {
            throw new IllegalArgumentException("name can't be null or empty");
        } else if (TextUtils.isEmpty(key)) {
            throw new IllegalArgumentException("key can't be null or empty");
        }

        mListeners = new LinkedList<>();
        mDefaultValue = defaultValue;
        mName = name;
        mKey = key;
    }

    /**
     * Adds a listener that will receive callbacks when this Preference is modified. Please be
     * sure to use {@link #removeListener(OnPreferenceChangeListener)} when you're finished.
     *
     * @param listener
     * the {@link OnPreferenceChangeListener} that will receive callbacks when this Preference
     * is modified
     *
     * @throws IllegalArgumentException
     * if listener is null, then this Exception will be thrown
     *
     * @see
     * #removeListener(OnPreferenceChangeListener)
     */
    public final void addListener(final OnPreferenceChangeListener<T> listener) throws
            IllegalArgumentException {
        if (listener == null) {
            throw new IllegalArgumentException("listener can't be null");
        }

        synchronized (mListeners) {
            boolean addListener = true;
            final Iterator<WeakReference<OnPreferenceChangeListener<T>>> iterator =
                    mListeners.iterator();

            // the following loop does two things: (1) it prevents us from adding the same
            // listener more than once, (2) it removes any listeners that have been garbage
            // collected

            while (iterator.hasNext()) {
                final OnPreferenceChangeListener<T> opcl = iterator.next().get();

                if (opcl == null) {
                    // listener has been garbage collected
                    iterator.remove();
                } else if (opcl == listener) {
                    // listener already exists in the list
                    addListener = false;
                }
            }

            if (addListener) {
                mListeners.add(new WeakReference<>(listener));
            }
        }
    }

    protected final boolean contains() {
        return readSharedPreferences().contains(getKey());
    }

    /**
     * removes this Preference from storage and notifies listeners
     */
    public final void delete() {
        delete(true);
    }

    /**
     * removes this Preference from storage
     *
     * @param notifyListeners
     * true if you want listeners to be notified, false if not
     */
    public final void delete(final boolean notifyListeners) {
        writeSharedPreferences().remove(mKey).apply();

        if (notifyListeners) {
            notifyListeners();
        }
    }

    /**
     * @return
     * Returns true if a value exists for this Preference
     */
    public abstract boolean exists();

    /**
     * @return
     * Returns the current value of this Preference (which could be null).
     */
    @Nullable
    public abstract T get();

    public final Context getContext() {
        return ThatLilHummingbird.get();
    }

    /**
     * @return
     * returns this Preference's default value
     */
    @Nullable
    public final T getDefaultValue() {
        return mDefaultValue;
    }

    /**
     * @return
     * returns the key that this Preference's value is stored as
     */
    @NonNull
    public final String getKey() {
        return mKey;
    }

    /**
     * @return
     * returns the name of the preferences file that this Preference is stored in
     */
    @NonNull
    public String getName() {
        return mName;
    }

    private void notifyListeners() {
        synchronized (mListeners) {
            final Iterator<WeakReference<OnPreferenceChangeListener<T>>> iterator =
                    mListeners.iterator();

            while (iterator.hasNext()) {
                final OnPreferenceChangeListener<T> opcl = iterator.next().get();

                if (opcl == null) {
                    iterator.remove();
                } else {
                    opcl.onPreferenceChange(this);
                }
            }
        }
    }

    /**
     * writes the given value to the Android preference storage
     *
     * @param newValue
     * the guaranteed non-null value to be written to storage
     */
    protected abstract void performSet(@NonNull final T newValue);

    protected final SharedPreferences readSharedPreferences() {
        return getContext().getSharedPreferences(mName, Context.MODE_PRIVATE);
    }

    /**
     * removes a listener so that it no longer receives Preference modification callbacks
     *
     * @param listener
     * the {@link OnPreferenceChangeListener} that no longer wants to receive Preference
     * modification callbacks
     */
    public final void removeListener(final OnPreferenceChangeListener<T> listener) {
        synchronized (mListeners) {
            final Iterator<WeakReference<OnPreferenceChangeListener<T>>> iterator =
                    mListeners.iterator();

            while (iterator.hasNext()) {
                final OnPreferenceChangeListener<T> opcl = iterator.next().get();

                if (opcl == null || opcl == listener) {
                    iterator.remove();
                }
            }
        }
    }

    /**
     * writes a new value into storage for this Preference and notifies listeners
     */
    public final void set(@Nullable final T newValue) {
        set(newValue, true);
    }

    /**
     * writes a new value into storage for this Preference
     *
     * @param notifyListeners
     * true if you want listeners to be notified, false if not
     */
    public final void set(@Nullable final T newValue, final boolean notifyListeners) {
        if (newValue == null) {
            delete(notifyListeners);
        } else {
            performSet(newValue);

            if (notifyListeners) {
                notifyListeners();
            }
        }
    }

    /**
     * writes the value of the given Preference into storage for this Preference and notifies
     * listeners
     */
    public final void set(@NonNull final Preference<T> preference) {
        set(preference, true);
    }

    /**
     * writes the value of the given Preference into storage for this Preference
     *
     * @param notifyListeners
     * true if you want listeners to be notified, false if not
     */
    public final void set(@NonNull final Preference<T> preference, final boolean notifyListeners) {
        set(preference.get(), notifyListeners);
    }

    protected final SharedPreferences.Editor writeSharedPreferences() {
        return readSharedPreferences().edit();
    }


    public interface OnPreferenceChangeListener<T> {
        void onPreferenceChange(final Preference<T> preference);
    }

}
