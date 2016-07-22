package com.charlesmadere.hummingbird.models;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RatingTest {

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
