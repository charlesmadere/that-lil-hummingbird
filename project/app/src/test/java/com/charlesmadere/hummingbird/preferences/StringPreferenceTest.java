package com.charlesmadere.hummingbird.preferences;

import android.os.Build;

import com.charlesmadere.hummingbird.BuildConfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.M)
public class StringPreferenceTest {

    private static final String CNAME = StringPreferenceTest.class.getCanonicalName();


    private StringPreference createEmptyPref() {
        return new StringPreference(CNAME, "emptyPref", "");
    }

    private StringPreference createHelloPref() {
        return new StringPreference(CNAME, "helloPref", "Hello, World!");
    }

    @Test
    public void testExists() throws Exception {
        final StringPreference emptyPref = createEmptyPref();

        assertFalse(emptyPref.exists());
        emptyPref.set((String) null);
        assertFalse(emptyPref.exists());
        emptyPref.set("Blah");
        assertTrue(emptyPref.exists());
        emptyPref.delete();
        assertFalse(emptyPref.exists());

        final StringPreference helloPref = createHelloPref();

        assertTrue(helloPref.exists());
        helloPref.set("");
        assertFalse(helloPref.exists());
        helloPref.set("qrrbrbirlbel");
        assertTrue(helloPref.exists());
        helloPref.delete();
        assertTrue(helloPref.exists());
    }

    @Test
    public void testGet() throws Exception {
        final StringPreference emptyPref = createEmptyPref();

        assertEquals(emptyPref.get(), "");
        emptyPref.set("hi");
        assertEquals(emptyPref.get(), "hi");
        emptyPref.delete();
        assertEquals(emptyPref.get(), "");

        final StringPreference helloPref = createHelloPref();

        assertEquals(helloPref.get(), "Hello, World!");
        helloPref.set((String) null);
        assertEquals(helloPref.get(), "Hello, World!");
        helloPref.delete();
        assertEquals(helloPref.get(), "Hello, World!");
    }

}
