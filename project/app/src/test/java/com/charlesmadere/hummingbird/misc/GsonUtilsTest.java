package com.charlesmadere.hummingbird.misc;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class GsonUtilsTest {

    @Test
    public void testGetGson() {
        assertNotNull(GsonUtils.getGson());
    }

}
