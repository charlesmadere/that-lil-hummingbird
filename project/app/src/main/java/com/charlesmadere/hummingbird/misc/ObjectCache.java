package com.charlesmadere.hummingbird.misc;

import android.support.annotation.Nullable;
import android.support.v4.util.LruCache;
import android.text.TextUtils;

public final class ObjectCache {

    private static final String TAG = "ObjectCache";
    private static final LruCache<String, Object> CACHE;


    static {
        if (MiscUtils.isLowRamDevice()) {
            CACHE = new LruCache<>(8);
        } else {
            CACHE = new LruCache<>(12);
        }
    }

    private static String buildKey(final String... keys) {
        if (keys == null || keys.length == 0) {
            throw new IllegalArgumentException("keys parameter must not be null / empty");
        }

        return TextUtils.join("|", keys);
    }

    public static void clear() {
        final int oldSize;

        synchronized (CACHE) {
            oldSize = CACHE.size();
            CACHE.evictAll();
        }

        Timber.d(TAG, "cleared, size was " + oldSize);
    }

    @Nullable
    public static <T> T get(final KeyProvider keyProvider) {
        if (keyProvider == null) {
            throw new IllegalArgumentException("keyProvider parameter can't be null");
        }

        return get(keyProvider.getObjectCacheKeys());
    }

    @Nullable
    @SuppressWarnings("unchecked")
    public static <T> T get(final String... keys) {
        final String key = buildKey(keys);

        synchronized (CACHE) {
            return (T) CACHE.get(key);
        }
    }

    public static void put(@Nullable final Object object, final KeyProvider keyProvider) {
        if (keyProvider == null) {
            throw new IllegalArgumentException("keyProvider parameter can't be null");
        }

        put(object, keyProvider.getObjectCacheKeys());
    }

    public static void put(@Nullable final Object object, final String... keys) {
        final String key = buildKey(keys);
        final int oldSize, newSize;

        synchronized (CACHE) {
            oldSize = CACHE.size();

            if (object == null) {
                CACHE.remove(key);
            } else {
                CACHE.put(key, object);
            }

            newSize = CACHE.size();
        }

        if (newSize != oldSize) {
            Timber.d(TAG, "new size: " + newSize);
        }
    }

    public static void trim() {
        final int oldSize, newSize;

        synchronized (CACHE) {
            oldSize = CACHE.size();
            CACHE.trimToSize(CACHE.size() / 2);
            newSize = CACHE.size();
        }

        if (newSize != oldSize) {
            Timber.d(TAG, "trimmed from " + oldSize + " to " + newSize);
        }
    }

    public interface KeyProvider {
        String[] getObjectCacheKeys();
    }

}
