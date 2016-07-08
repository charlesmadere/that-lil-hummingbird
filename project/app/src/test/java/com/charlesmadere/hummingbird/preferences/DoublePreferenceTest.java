package com.charlesmadere.hummingbird.preferences;

import android.os.Build;

import com.charlesmadere.hummingbird.BuildConfig;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.M)
public class DoublePreferenceTest {

    private static final String CNAME = DoublePreferenceTest.class.getCanonicalName();

    private DoublePreference mNullPref;
    private DoublePreference mTwoPref;


    @Before
    public void setUp() throws Exception {
        mNullPref = new DoublePreference(CNAME, "nullPref", null);
        mTwoPref = new DoublePreference(CNAME, "twoPref", 2D);
    }

    @Test
    public void testExists() throws Exception {
        assertFalse(mNullPref.exists());
        mNullPref.set(5D);
        assertTrue(mNullPref.exists());
        mNullPref.set((Double) null);
        assertFalse(mNullPref.exists());
        mNullPref.delete();
        assertFalse(mNullPref.exists());

        assertTrue(mTwoPref.exists());
        mTwoPref.set(Double.MIN_VALUE);
        assertTrue(mTwoPref.exists());
        mTwoPref.set(Double.MAX_VALUE);
        assertTrue(mTwoPref.exists());
        mTwoPref.delete();
        assertTrue(mTwoPref.exists());
    }

    @Test
    public void testGet() throws Exception {
        assertNull(mNullPref.get());
        mNullPref.set(50D);
        assertTrue(mNullPref.get() == 50D);
        mNullPref.set(64D);
        assertTrue(mNullPref.get() == 64D);
        mNullPref.delete();
        assertNull(mNullPref.get());

        assertTrue(mTwoPref.get() == 2D);
        mTwoPref.set(Double.MIN_VALUE);
        assertTrue(mTwoPref.get() == Double.MIN_VALUE);
        mTwoPref.set(Double.MAX_VALUE);
        assertTrue(mTwoPref.get() == Double.MAX_VALUE);
        mTwoPref.delete();
        assertTrue(mTwoPref.get() == 2D);
    }

    @Test
    public void testGetDefaultValue() throws Exception {
        assertNull(mNullPref.getDefaultValue());
        assertTrue(mTwoPref.getDefaultValue() == 2D);
    }

}
