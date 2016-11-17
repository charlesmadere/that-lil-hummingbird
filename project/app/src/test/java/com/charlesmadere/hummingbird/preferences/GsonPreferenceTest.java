package com.charlesmadere.hummingbird.preferences;

import android.os.Build;

import com.charlesmadere.hummingbird.BuildConfig;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.M)
public class GsonPreferenceTest {

    private static final String CNAME = GsonPreferenceTest.class.getCanonicalName();
    private static final int SHEIK_ID = 10;
    private static final String SHEIK_NAME = "Zelda";

    private GsonPreference<Employee> mNullPref;
    private GsonPreference<Employee> mSheikPref;


    @Before
    public void setUp() throws Exception {
        mNullPref = new GsonPreference<>(CNAME, "nullPref", Employee.class, null);

        final Employee sheik = new Employee();
        sheik.mId = SHEIK_ID;
        sheik.mName = SHEIK_NAME;
        mSheikPref = new GsonPreference<>(CNAME, "sheikPref", Employee.class, sheik);
    }

    @Test
    public void testExists() throws Exception {
        assertFalse(mNullPref.exists());
        assertTrue(mSheikPref.exists());
    }

    @Test
    public void testGet() throws Exception {
        assertNull(mNullPref.get());

        Employee sheik = mSheikPref.get();
        assertNotNull(sheik);
        assertEquals(sheik.mId, SHEIK_ID);
        assertEquals(sheik.mName, SHEIK_NAME);
    }

    @Test
    public void testGetDefaultValue() throws Exception {
        assertNull(mNullPref.getDefaultValue());
        assertNotNull(mSheikPref.getDefaultValue());

        mSheikPref.delete();
        assertNotNull(mSheikPref.getDefaultValue());
    }

    @Test
    public void testGetContext() throws Exception {
        assertNotNull(mNullPref.getContext());
        assertNotNull(mSheikPref.getContext());
    }

    @Test
    public void testSet() throws Exception {
        Employee sheik = new Employee();
        sheik.mId = 20;
        sheik.mName = "Link";
        mSheikPref.set(sheik);

        sheik = mSheikPref.get();
        assertNotNull(sheik);
        assertEquals(sheik.mId, 20);
        assertEquals(sheik.mName, "Link");
    }


    /**
     * just a super simple class to use to test GsonPreference
     */
    private class Employee {
        private int mId;
        private String mName;

        @Override
        public boolean equals(final Object o) {
            return o instanceof Employee && mId == ((Employee) o).mId &&
                    mName.equals(((Employee) o).mName);
        }
    }

}
