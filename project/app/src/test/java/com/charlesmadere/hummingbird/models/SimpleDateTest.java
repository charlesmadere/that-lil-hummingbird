package com.charlesmadere.hummingbird.models;

import android.os.Build;

import com.charlesmadere.hummingbird.BuildConfig;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.M)
public class SimpleDateTest {

    private static final String EXTENDED_DATE_0 = "2013-02-20T16:00:15.623Z";
    private static final String EXTENDED_DATE_1 = "2015-08-06T18:46:42.723Z";
    private static final String SHORT_DATE_0 = "2013-07-18";
    private static final String SHORT_DATE_1 = "2016-03-04";

    private Constructor<SimpleDate> mConstructor;
    private Method mFixTimeZone;
    private SimpleDateFormat mExtendedFormat;
    private SimpleDateFormat mShortFormat;


    @Before
    public void setUp() throws Exception {
        mConstructor = SimpleDate.class.getDeclaredConstructor(Date.class);
        mConstructor.setAccessible(true);

        mFixTimeZone = SimpleDate.class.getDeclaredMethod("fixTimeZone", String.class);
        mFixTimeZone.setAccessible(true);

        final Field field = SimpleDate.class.getDeclaredField("FORMATS");
        field.setAccessible(true);
        final SimpleDateFormat[] formats = (SimpleDateFormat[]) field.get(null);
        mExtendedFormat = formats[0];
        mShortFormat = formats[1];
    }

    @Test
    public void testExtendedDateConstruction() throws Exception {
        String dateString = (String) mFixTimeZone.invoke(null, EXTENDED_DATE_0);
        SimpleDate sd = mConstructor.newInstance(mExtendedFormat.parse(dateString));
        assertNotNull(sd);

        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(sd.getDate());
        assertTrue(calendar.get(Calendar.YEAR) == 2013);
        assertTrue(calendar.get(Calendar.MONTH) == Calendar.FEBRUARY);
        assertTrue(calendar.get(Calendar.DAY_OF_MONTH) == 20);

        dateString = (String) mFixTimeZone.invoke(null, EXTENDED_DATE_1);
        sd = mConstructor.newInstance(mExtendedFormat.parse(dateString));
        assertNotNull(sd);

        calendar.setTime(sd.getDate());
        assertTrue(calendar.get(Calendar.YEAR) == 2015);
        assertTrue(calendar.get(Calendar.MONTH) == Calendar.AUGUST);
        assertTrue(calendar.get(Calendar.DAY_OF_MONTH) == 6);
    }

    @Test
    public void testFixTimeZone() throws Exception {
        String string = (String) mFixTimeZone.invoke(null, EXTENDED_DATE_0);
        assertTrue(string.endsWith("+0000"));

        string = (String) mFixTimeZone.invoke(null, EXTENDED_DATE_1);
        assertTrue(string.endsWith("+0000"));

        string = (String) mFixTimeZone.invoke(null, SHORT_DATE_0);
        assertTrue(string.equals(SHORT_DATE_0));

        string = (String) mFixTimeZone.invoke(null, SHORT_DATE_1);
        assertTrue(string.equals(SHORT_DATE_1));
    }

    @Test
    public void testShortDateConstruction() throws Exception {
        SimpleDate sd = mConstructor.newInstance(mShortFormat.parse(SHORT_DATE_0));
        assertNotNull(sd);

        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(sd.getDate());
        assertTrue(calendar.get(Calendar.YEAR) == 2013);
        assertTrue(calendar.get(Calendar.MONTH) == Calendar.JULY);
        assertTrue(calendar.get(Calendar.DAY_OF_MONTH) == 18);

        sd = mConstructor.newInstance(mShortFormat.parse(SHORT_DATE_1));
        assertNotNull(sd);

        calendar.setTime(sd.getDate());
        assertTrue(calendar.get(Calendar.YEAR) == 2016);
        assertTrue(calendar.get(Calendar.MONTH) == Calendar.MARCH);
        assertTrue(calendar.get(Calendar.DAY_OF_MONTH) == 4);
    }

}
