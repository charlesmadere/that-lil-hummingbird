package com.charlesmadere.hummingbird.misc;

import android.support.annotation.Nullable;
import android.support.v4.util.LruCache;
import android.text.TextUtils;

import com.charlesmadere.hummingbird.models.Feed;

public final class FeedCache {

    private static final LruCache<String, Feed> CACHE;


    static {
        if (MiscUtils.isLowRamDevice()) {
            CACHE = new LruCache<>(10);
        } else {
            CACHE = new LruCache<>(16);
        }
    }

    private static String buildKey(final String... keys) {
        return TextUtils.join("|", keys);
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
