package com.charlesmadere.hummingbird.models;

import android.os.Build;

import com.charlesmadere.hummingbird.BuildConfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.M)
public class RatingTest {

    @Test
    public void testCompare() throws Exception {
        assertEquals(Rating.compare(null, null), 0);
        assertEquals(Rating.compare(null, Rating.UNRATED), 0);
        assertEquals(Rating.compare(Rating.UNRATED, null), 0);
        assertEquals(Rating.compare(Rating.UNRATED, Rating.UNRATED), 0);
        assertEquals(Rating.compare(Rating.ZERO, Rating.ZERO), 0);
        assertEquals(Rating.compare(Rating.ZERO_POINT_FIVE, Rating.ZERO_POINT_FIVE), 0);
        assertEquals(Rating.compare(Rating.THREE, Rating.THREE), 0);
        assertEquals(Rating.compare(Rating.FIVE, Rating.FIVE), 0);

        assertNotEquals(Rating.compare(Rating.ZERO, null), 0);
        assertNotEquals(Rating.compare(Rating.ZERO, Rating.UNRATED), 0);
        assertNotEquals(Rating.compare(null, Rating.ZERO), 0);
        assertNotEquals(Rating.compare(Rating.UNRATED, Rating.ZERO), 0);
        assertNotEquals(Rating.compare(Rating.FIVE, null), 0);
        assertNotEquals(Rating.compare(Rating.FIVE, Rating.UNRATED), 0);
        assertNotEquals(Rating.compare(null, Rating.FIVE), 0);
        assertNotEquals(Rating.compare(Rating.UNRATED, Rating.FIVE), 0);

        assertNotEquals(Rating.compare(Rating.ZERO, Rating.ZERO_POINT_FIVE), 0);
        assertNotEquals(Rating.compare(Rating.FOUR, Rating.THREE), 0);
        assertNotEquals(Rating.compare(Rating.TWO_POINT_FIVE, null), 0);
        assertNotEquals(Rating.compare(Rating.THREE, Rating.FOUR_POINT_FIVE), 0);
        assertNotEquals(Rating.compare(Rating.ONE_POINT_FIVE, Rating.ONE), 0);
        assertNotEquals(Rating.compare(Rating.ONE, Rating.ONE_POINT_FIVE), 0);
        assertNotEquals(Rating.compare(Rating.FOUR_POINT_FIVE, Rating.THREE_POINT_FIVE), 0);
    }

    @Test
    public void testEquals() throws Exception {
        assertTrue(Rating.equals(null, null));
        assertTrue(Rating.equals(Rating.ZERO, Rating.ZERO));
        assertTrue(Rating.equals(Rating.ONE, Rating.ONE));
        assertTrue(Rating.equals(Rating.THREE, Rating.THREE));
        assertTrue(Rating.equals(Rating.FOUR_POINT_FIVE, Rating.FOUR_POINT_FIVE));
        assertTrue(Rating.equals(null, Rating.UNRATED));
        assertTrue(Rating.equals(Rating.UNRATED, null));
        assertTrue(Rating.equals(Rating.UNRATED, Rating.UNRATED));

        assertFalse(Rating.equals(null, Rating.ONE));
        assertFalse(Rating.equals(Rating.ONE, null));
        assertFalse(Rating.equals(Rating.UNRATED, Rating.FOUR));
        assertFalse(Rating.equals(Rating.TWO, Rating.UNRATED));
        assertFalse(Rating.equals(Rating.FIVE, Rating.ZERO));
    }

}
