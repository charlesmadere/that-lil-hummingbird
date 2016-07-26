package com.charlesmadere.hummingbird.preferences;

import android.os.Build;

import com.charlesmadere.hummingbird.BuildConfig;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.M)
public class IntegerPreferenceTest {

    private static final String CNAME = IntegerPreferenceTest.class.getCanonicalName();

    private IntegerPreference mNullPref;
    private IntegerPreference mTenPref;


    @Before
    public void setUp() throws Exception {
        mNullPref = new IntegerPreference(CNAME, "nullPref", null);
        mTenPref = new IntegerPreference(CNAME, "tenPref", 10);
    }

    @Test
    public void testExists() throws Exception {
        assertFalse(mNullPref.exists());
        assertTrue(mTenPref.exists());
    }

}
