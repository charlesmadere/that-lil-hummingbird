package com.charlesmadere.hummingbird.preferences;

import android.os.Build;

import com.charlesmadere.hummingbird.BuildConfig;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.M)
public class StringPreferenceTest {

    private static final String CNAME = StringPreferenceTest.class.getCanonicalName();

    private StringPreference mNullPref;
    private StringPreference mEmptyPref;
    private StringPreference mHelloPref;


    @Before
    public void setUp() throws Exception {
        mNullPref = new StringPreference(CNAME, "nullPref", null);
        mEmptyPref = new StringPreference(CNAME, "emptyPref", "");
        mHelloPref = new StringPreference(CNAME, "helloPref", "Hello, World!");
    }

    @Test
    public void testExists() throws Exception {
        assertFalse(mNullPref.exists());
        mNullPref.set("");
        assertFalse(mNullPref.exists());
        mNullPref.set((String) null);
        assertFalse(mNullPref.exists());
        mNullPref.set("qwerty");
        assertTrue(mNullPref.exists());
        mNullPref.delete();
        assertFalse(mNullPref.exists());

        assertFalse(mEmptyPref.exists());
        mEmptyPref.set((String) null);
        assertFalse(mEmptyPref.exists());
        mEmptyPref.set("Blah");
        assertTrue(mEmptyPref.exists());
        mEmptyPref.delete();
        assertFalse(mEmptyPref.exists());

        assertTrue(mHelloPref.exists());
        mHelloPref.set("");
        assertFalse(mHelloPref.exists());
        mHelloPref.set("qrrbrbirlbel");
        assertTrue(mHelloPref.exists());
        mHelloPref.delete();
        assertTrue(mHelloPref.exists());
    }

    @Test
    public void testGet() throws Exception {
        assertNull(mNullPref.get());
        mNullPref.set((String) null);
        assertNull(mNullPref.get());
        mNullPref.delete();
        assertNull(mNullPref.get());
        mNullPref.set("Android");
        assertEquals(mNullPref.get(), "Android");
        mNullPref.delete();
        assertNull(mNullPref.get());

        assertEquals(mEmptyPref.get(), "");
        mEmptyPref.set("hi");
        assertEquals(mEmptyPref.get(), "hi");
        mEmptyPref.delete();
        assertEquals(mEmptyPref.get(), "");

        assertEquals(mHelloPref.get(), "Hello, World!");
        mHelloPref.set((String) null);
        assertEquals(mHelloPref.get(), "Hello, World!");
        mHelloPref.delete();
        assertEquals(mHelloPref.get(), "Hello, World!");
    }

}
