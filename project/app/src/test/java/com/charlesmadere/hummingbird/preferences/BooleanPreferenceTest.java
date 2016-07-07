package com.charlesmadere.hummingbird.preferences;

import android.os.Build;

import com.charlesmadere.hummingbird.BuildConfig;
import com.charlesmadere.hummingbird.preferences.Preference.OnPreferenceChangeListener;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.M)
public class BooleanPreferenceTest {

    private static final String CNAME = BooleanPreferenceTest.class.getCanonicalName();

    private BooleanPreference mNullPref;
    private BooleanPreference mFalsePref;
    private BooleanPreference mTruePref;


    @Before
    public void setUp() throws Exception {
        mNullPref = new BooleanPreference(CNAME, "nullPref", null);
        mFalsePref = new BooleanPreference(CNAME, "falsePref", Boolean.FALSE);
        mTruePref = new BooleanPreference(CNAME, "truePref", Boolean.TRUE);
    }

    @Test
    public void testAddListener() throws Exception {
        final Boolean[] booleans = { Boolean.FALSE };

        mNullPref.addListener(new OnPreferenceChangeListener<Boolean>() {
            @Override
            public void onPreferenceChange(final Preference<Boolean> preference) {
                booleans[0] = preference.get();
            }
        });

        assertFalse(booleans[0]);
        mNullPref.set(Boolean.TRUE);
        assertTrue(booleans[0]);
        mNullPref.set(Boolean.FALSE);
        assertFalse(booleans[0]);

        mNullPref.delete();
    }

    @Test
    public void testDelete() throws Exception {
        mNullPref.set(Boolean.TRUE);
        assertTrue(mNullPref.get());
        mNullPref.delete();
        assertFalse(mNullPref.exists());
        assertNull(mNullPref.get());

        mFalsePref.delete();
        assertFalse(mFalsePref.get());
        mFalsePref.set(Boolean.TRUE);
        assertTrue(mFalsePref.get());
        mFalsePref.delete();
        assertTrue(mFalsePref.exists());
        assertFalse(mFalsePref.get());

        mTruePref.delete();
        assertTrue(mTruePref.get());
        mTruePref.set(Boolean.FALSE);
        assertFalse(mTruePref.get());
        mTruePref.delete();
        assertTrue(mTruePref.exists());
        assertTrue(mTruePref.get());

        mNullPref.delete();
        mFalsePref.delete();
        mTruePref.delete();
    }

    @Test
    public void testExists() throws Exception {
        assertFalse(mNullPref.exists());
        mNullPref.set(Boolean.TRUE);
        assertTrue(mNullPref.exists());
        mNullPref.delete();
        assertFalse(mNullPref.exists());

        assertTrue(mFalsePref.exists());
        mFalsePref.set(Boolean.TRUE);
        assertTrue(mFalsePref.exists());
        mFalsePref.delete();
        assertTrue(mFalsePref.exists());

        assertTrue(mTruePref.exists());
        mTruePref.set(Boolean.FALSE);
        assertTrue(mTruePref.exists());
        mTruePref.delete();
        assertTrue(mTruePref.exists());

        mNullPref.delete();
        mFalsePref.delete();
        mTruePref.delete();
    }

    @Test
    public void testGet() throws Exception {
        assertNull(mNullPref.get());
        mNullPref.set(Boolean.TRUE);
        assertTrue(mNullPref.get());

        assertFalse(mFalsePref.get());
        mFalsePref.set(Boolean.FALSE);
        assertFalse(mFalsePref.get());

        assertTrue(mTruePref.get());
        mTruePref.set(Boolean.FALSE);
        assertFalse(mTruePref.get());
        mTruePref.set((Boolean) null);
        assertTrue(mTruePref.get());

        mNullPref.delete();
        mFalsePref.delete();
        mTruePref.delete();
    }

    @Test
    public void testGetDefaultValue() throws Exception {
        assertNull(mNullPref.getDefaultValue());
        mNullPref.set(Boolean.FALSE);
        assertNull(mNullPref.getDefaultValue());

        assertFalse(mFalsePref.getDefaultValue());
        mFalsePref.set(Boolean.TRUE);
        assertFalse(mFalsePref.getDefaultValue());

        assertTrue(mTruePref.getDefaultValue());
        mTruePref.set(Boolean.FALSE);
        assertTrue(mTruePref.getDefaultValue());

        mNullPref.delete();
        mFalsePref.delete();
        mTruePref.delete();
    }

    @Test
    public void testGetKey() {
        assertEquals(mNullPref.getKey(), "nullPref");
        assertEquals(mFalsePref.getKey(), "falsePref");
        assertEquals(mTruePref.getKey(), "truePref");
    }

    @Test
    public void testGetName() {
        assertEquals(mNullPref.getName(), CNAME);
        assertEquals(mFalsePref.getName(), CNAME);
        assertEquals(mTruePref.getName(), CNAME);
    }

    @Test
    public void testRemoveListener() {
        final Boolean[] booleans = { Boolean.FALSE };

        final OnPreferenceChangeListener<Boolean> opcl = new OnPreferenceChangeListener<Boolean>() {
            @Override
            public void onPreferenceChange(final Preference<Boolean> preference) {
                booleans[0] = preference.get();
            }
        };

        mNullPref.addListener(opcl);

        assertFalse(booleans[0]);
        mNullPref.set(Boolean.TRUE);
        assertTrue(booleans[0]);
        mNullPref.set(Boolean.FALSE);
        assertFalse(booleans[0]);

        mNullPref.removeListener(opcl);
        mNullPref.set(Boolean.TRUE);
        assertFalse(booleans[0]);

        mNullPref.delete();
    }

    @Test
    public void testSet() throws Exception {
        assertNull(mNullPref.get());
        mNullPref.set(Boolean.TRUE);
        assertTrue(mNullPref.get());

        assertFalse(mFalsePref.get());
        mFalsePref.set(Boolean.FALSE);
        assertFalse(mFalsePref.get());
        mFalsePref.set(Boolean.TRUE);
        assertTrue(mFalsePref.get());

        assertTrue(mTruePref.get());
        mTruePref.set(Boolean.TRUE);
        assertTrue(mTruePref.get());
        mTruePref.set(Boolean.FALSE);
        assertFalse(mTruePref.get());

        mNullPref.delete();
        mFalsePref.delete();
        mTruePref.delete();
    }

    @Test
    public void testToggle() throws Exception {
        mNullPref.toggle();
        assertNull(mNullPref.get());

        mFalsePref.toggle();
        assertTrue(mFalsePref.get());
        mFalsePref.toggle();
        assertFalse(mFalsePref.get());

        mTruePref.toggle();
        assertFalse(mTruePref.get());
        mTruePref.toggle();
        assertTrue(mTruePref.get());

        mNullPref.delete();
        mFalsePref.delete();
        mTruePref.delete();
    }

}
