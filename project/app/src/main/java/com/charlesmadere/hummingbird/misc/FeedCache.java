package com.charlesmadere.hummingbird.misc;

import android.support.annotation.Nullable;
import android.support.v4.util.LruCache;

import com.charlesmadere.hummingbird.models.Feed;

public final class FeedCache {

    private static final LruCache<String, Feed> CACHE;


    static {
        if (MiscUtils.isLowRamDevice()) {
            CACHE = new LruCache<>(11);
        } else {
            CACHE = new LruCache<>(16);
        }
    }

    private static String buildKey(final String... keys) {
        if (keys == null || keys.length == 0) {
            throw new IllegalArgumentException("keys parameter can't be null / empty");
        }

        final StringBuilder builder = new StringBuilder();

        for (final String key : keys) {
            builder.append('|');
            builder.append(key);
        }

        return builder.toString();
    }

    public static synchronized void clear() {
        CACHE.evictAll();
    }

    @Nullable
    public static Feed get(final String... keys) {
        final String key = buildKey(keys);

        synchronized (CACHE) {
            return CACHE.get(key);
        }
    }

    public static void put(final Feed feed, final String... keys) {
        final String key = buildKey(keys);

        synchronized (CACHE) {
            CACHE.put(key, feed);
        }
    }

}
