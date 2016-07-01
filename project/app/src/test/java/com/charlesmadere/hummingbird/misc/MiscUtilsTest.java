package com.charlesmadere.hummingbird.misc;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MiscUtilsTest {

    @Test
    public void testBooleanEquals() throws Exception {
        assertTrue(MiscUtils.booleanEquals(null, null));

        assertTrue(MiscUtils.booleanEquals(Boolean.TRUE, Boolean.TRUE));
        assertTrue(MiscUtils.booleanEquals(Boolean.TRUE, true));
        assertTrue(MiscUtils.booleanEquals(true, Boolean.TRUE));
        assertTrue(MiscUtils.booleanEquals(true, true));

        assertTrue(MiscUtils.booleanEquals(Boolean.FALSE, Boolean.FALSE));
        assertTrue(MiscUtils.booleanEquals(Boolean.FALSE, false));
        assertTrue(MiscUtils.booleanEquals(false, Boolean.FALSE));
        assertTrue(MiscUtils.booleanEquals(false, false));

        assertFalse(MiscUtils.booleanEquals(Boolean.TRUE, Boolean.FALSE));
        assertFalse(MiscUtils.booleanEquals(Boolean.TRUE, false));
        assertFalse(MiscUtils.booleanEquals(Boolean.TRUE, null));
        assertFalse(MiscUtils.booleanEquals(true, Boolean.FALSE));
        assertFalse(MiscUtils.booleanEquals(true, false));
        assertFalse(MiscUtils.booleanEquals(true, null));

        assertFalse(MiscUtils.booleanEquals(Boolean.FALSE, Boolean.TRUE));
        assertFalse(MiscUtils.booleanEquals(Boolean.FALSE, true));
        assertFalse(MiscUtils.booleanEquals(Boolean.FALSE, null));
        assertFalse(MiscUtils.booleanEquals(false, Boolean.TRUE));
        assertFalse(MiscUtils.booleanEquals(false, true));
        assertFalse(MiscUtils.booleanEquals(false, null));

        assertFalse(MiscUtils.booleanEquals(false, true));

    }

    @Test
    public void testExclusiveAdd() throws Exception {

    }

    @Test
    public void testIntegerEquals() throws Exception {
        assertTrue(MiscUtils.integerEquals(null, null));

    }

}
