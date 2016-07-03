package com.charlesmadere.hummingbird.models;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class WatchingStatusTest {

    @Test
    public void testEquals() throws Exception {
        assertTrue(WatchingStatus.equals(null, null));
        assertTrue(WatchingStatus.equals(WatchingStatus.COMPLETED, WatchingStatus.COMPLETED));
        assertTrue(WatchingStatus.equals(WatchingStatus.CURRENTLY_WATCHING, WatchingStatus.CURRENTLY_WATCHING));
        assertTrue(WatchingStatus.equals(WatchingStatus.PLAN_TO_WATCH, WatchingStatus.PLAN_TO_WATCH));

        assertFalse(WatchingStatus.equals(null, WatchingStatus.COMPLETED));
        assertFalse(WatchingStatus.equals(WatchingStatus.DROPPED, null));
        assertFalse(WatchingStatus.equals(WatchingStatus.PLAN_TO_WATCH, WatchingStatus.REMOVE_FROM_LIBRARY));
    }

}
