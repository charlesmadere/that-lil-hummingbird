package com.charlesmadere.hummingbird.models;

import android.os.Build;

import com.charlesmadere.hummingbird.BuildConfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.M)
public class WatchingStatusTest {

    @Test
    public void testEquals() throws Exception {
        assertTrue(WatchingStatus.equals(null, null));
        assertTrue(WatchingStatus.equals(WatchingStatus.COMPLETED, WatchingStatus.COMPLETED));
        assertTrue(WatchingStatus.equals(WatchingStatus.CURRENTLY_WATCHING, WatchingStatus.CURRENTLY_WATCHING));
        assertTrue(WatchingStatus.equals(WatchingStatus.PLAN_TO_WATCH, WatchingStatus.PLAN_TO_WATCH));

        assertFalse(WatchingStatus.equals(null, WatchingStatus.COMPLETED));
        assertFalse(WatchingStatus.equals(WatchingStatus.DROPPED, null));
        assertFalse(WatchingStatus.equals(WatchingStatus.PLAN_TO_WATCH, WatchingStatus.CURRENTLY_WATCHING));
        assertFalse(WatchingStatus.equals(WatchingStatus.ON_HOLD, WatchingStatus.COMPLETED));
    }

}
