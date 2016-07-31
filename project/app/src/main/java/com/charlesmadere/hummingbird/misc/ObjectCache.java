package com.charlesmadere.hummingbird.misc;

import android.support.annotation.Nullable;
import android.support.v4.util.LruCache;
import android.text.TextUtils;

public final class ObjectCache<T> {

    public static final ObjectCache<Object> INSTANCE;

    private final LruCache<String, T> mCache;
    private final String mTag;


    static {
        if (MiscUtils.isLowRamDevice()) {
            INSTANCE = new ObjectCache<>(6, "ObjectCache");
        } else {
            INSTANCE = new ObjectCache<>(10, "ObjectCache");
        }
    }

    public ObjectCache(final int maxSize, final String tag) {
        mCache = new LruCache<>(maxSize);
        mTag = tag;
    }

    private static String buildKey(final String... keys) {
        return TextUtils.join("|", keys);
    }

    public void clear() {
        final int oldSize;

        synchronized (mCache) {
            oldSize = mCache.size();
            mCache.evictAll();
        }

        Timber.d(mTag, "cleared, size was " + oldSize);
    }

    @Nullable
    public T get(final KeyProvider keyProvider) {
        if (keyProvider == null) {
            throw new IllegalArgumentException("keyProvider parameter can't be null");
        }

        return get(keyProvider.getObjectCacheKeys());
    }

    @Nullable
    public T get(final String... keys) {
        final String key = buildKey(keys);

        synchronized (mCache) {
            return mCache.get(key);
        }
    }

    public void put(final T object, final KeyProvider keyProvider) {
        if (keyProvider == null) {
            throw new IllegalArgumentException("keyProvider parameter can't be null");
        }

        put(object, keyProvider.getObjectCacheKeys());
    }

    public void put(final T object, final String... keys) {
        final String key = buildKey(keys);
        final int oldSize, newSize;

        synchronized (mCache) {
            oldSize = mCache.size();

            if (object == null) {
                mCache.remove(key);
            } else {
                mCache.put(key, object);
            }

            newSize = mCache.size();
        }

        if (newSize != oldSize) {
            Timber.d(mTag, "new size: " + mCache.size());
        }
    }

    public void trim() {
        final int oldSize, newSize;

        synchronized (mCache) {
            oldSize = mCache.size();
            mCache.trimToSize(mCache.maxSize() / 2);
            newSize = mCache.size();
        }

        if (newSize != oldSize) {
            Timber.d(mTag, "trimmed from " + oldSize + " to " + newSize);
        }
    }

    public interface KeyProvider {
        String[] getObjectCacheKeys();
    }

}
