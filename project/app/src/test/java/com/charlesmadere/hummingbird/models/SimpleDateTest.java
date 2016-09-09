package com.charlesmadere.hummingbird.models;

import android.os.Build;

import com.charlesmadere.hummingbird.BuildConfig;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.M)
public class SimpleDateTest {

    private static final String AM_PM_DATE_0 = "Feb 2, 1998 4:34:20 AM";
    private static final String AM_PM_DATE_1 = "Jun 08, 2000 08:10:59 PM";
    private static final String AM_PM_DATE_2 = "Dec 28, 2012 12:00:00 AM";
    private static final String ARMY_DATE_0 = "Sep 26, 2015 00:00:00";
    private static final String ARMY_DATE_1 = "Nov 28, 1989 13:41:26";
    private static final String EXTENDED_DATE_0 = "2013-02-20T16:00:15.623Z";
    private static final String EXTENDED_DATE_1 = "2015-08-06T18:46:42.723Z";
    private static final String SHORT_DATE_0 = "2013-07-18";
    private static final String SHORT_DATE_1 = "2016-03-04";

    private Constructor<SimpleDate> mConstructor;
    private Method mFixTimeZone;
    private SimpleDateFormat mAmPmFormat;
    private SimpleDateFormat mArmyFormat;
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
        mAmPmFormat = formats[1];
        mArmyFormat = formats[2];
        mShortFormat = formats[3];
    }

    @Test
    public void testAmPmDateConstruction() throws Exception {
        SimpleDate sd = mConstructor.newInstance(mAmPmFormat.parse(AM_PM_DATE_0));
        assertNotNull(sd);

        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(sd.getDate());
        assertEquals(calendar.get(Calendar.YEAR), 1998);
        assertEquals(calendar.get(Calendar.MONTH), Calendar.FEBRUARY);
        assertEquals(calendar.get(Calendar.DAY_OF_MONTH), 2);
        assertEquals(calendar.get(Calendar.HOUR), 4);
        assertEquals(calendar.get(Calendar.MINUTE), 34);
        assertEquals(calendar.get(Calendar.SECOND), 20);
        assertEquals(calendar.get(Calendar.AM_PM), Calendar.AM);

        sd = mConstructor.newInstance(mAmPmFormat.parse(AM_PM_DATE_1));
        assertNotNull(sd);

        calendar.setTime(sd.getDate());
        assertEquals(calendar.get(Calendar.YEAR), 2000);
        assertEquals(calendar.get(Calendar.MONTH), Calendar.JUNE);
        assertEquals(calendar.get(Calendar.DAY_OF_MONTH), 8);
        assertEquals(calendar.get(Calendar.HOUR), 8);
        assertEquals(calendar.get(Calendar.MINUTE), 10);
        assertEquals(calendar.get(Calendar.SECOND), 59);
        assertEquals(calendar.get(Calendar.AM_PM), Calendar.PM);

        sd = mConstructor.newInstance(mAmPmFormat.parse(AM_PM_DATE_2));
        assertNotNull(sd);

        calendar.setTime(sd.getDate());
        assertEquals(calendar.get(Calendar.YEAR), 2012);
        assertEquals(calendar.get(Calendar.MONTH), Calendar.DECEMBER);
        assertEquals(calendar.get(Calendar.DAY_OF_MONTH), 28);
        assertEquals(calendar.get(Calendar.HOUR), 0);
        assertEquals(calendar.get(Calendar.MINUTE), 0);
        assertEquals(calendar.get(Calendar.SECOND), 0);
        assertEquals(calendar.get(Calendar.AM_PM), Calendar.AM);
    }

    @Test
    public void testArmyDateConstruction() throws Exception {
        SimpleDate sd = mConstructor.newInstance(mArmyFormat.parse(ARMY_DATE_0));
        assertNotNull(sd);

        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(sd.getDate());
        assertEquals(calendar.get(Calendar.YEAR), 2015);
        assertEquals(calendar.get(Calendar.MONTH), Calendar.SEPTEMBER);
        assertEquals(calendar.get(Calendar.DAY_OF_MONTH), 26);
        assertEquals(calendar.get(Calendar.HOUR_OF_DAY), 0);
        assertEquals(calendar.get(Calendar.MINUTE), 0);
        assertEquals(calendar.get(Calendar.SECOND), 0);

        sd = mConstructor.newInstance(mArmyFormat.parse(ARMY_DATE_1));
        assertNotNull(sd);

        calendar.setTime(sd.getDate());
        assertEquals(calendar.get(Calendar.YEAR), 1989);
        assertEquals(calendar.get(Calendar.MONTH), Calendar.NOVEMBER);
        assertEquals(calendar.get(Calendar.DAY_OF_MONTH), 28);
        assertEquals(calendar.get(Calendar.HOUR_OF_DAY), 13);
        assertEquals(calendar.get(Calendar.MINUTE), 41);
        assertEquals(calendar.get(Calendar.SECOND), 26);
    }

    @Test
    public void testExtendedDateConstruction() throws Exception {
        String dateString = (String) mFixTimeZone.invoke(null, EXTENDED_DATE_0);
        SimpleDate sd = mConstructor.newInstance(mExtendedFormat.parse(dateString));
        assertNotNull(sd);

        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(sd.getDate());
        assertEquals(calendar.get(Calendar.YEAR), 2013);
        assertEquals(calendar.get(Calendar.MONTH), Calendar.FEBRUARY);
        assertEquals(calendar.get(Calendar.DAY_OF_MONTH), 20);

        dateString = (String) mFixTimeZone.invoke(null, EXTENDED_DATE_1);
        sd = mConstructor.newInstance(mExtendedFormat.parse(dateString));
        assertNotNull(sd);

        calendar.setTime(sd.getDate());
        assertEquals(calendar.get(Calendar.YEAR), 2015);
        assertEquals(calendar.get(Calendar.MONTH), Calendar.AUGUST);
        assertEquals(calendar.get(Calendar.DAY_OF_MONTH), 6);
    }

    @Test
    public void testFixTimeZone() throws Exception {
        String string = (String) mFixTimeZone.invoke(null, AM_PM_DATE_0);
        assertEquals(string, AM_PM_DATE_0);

        string = (String) mFixTimeZone.invoke(null, AM_PM_DATE_1);
        assertEquals(string, AM_PM_DATE_1);

        string = (String) mFixTimeZone.invoke(null, AM_PM_DATE_2);
        assertEquals(string, AM_PM_DATE_2);

        string = (String) mFixTimeZone.invoke(null, EXTENDED_DATE_0);
        assertTrue(string.endsWith("+0000"));

        string = (String) mFixTimeZone.invoke(null, EXTENDED_DATE_1);
        assertTrue(string.endsWith("+0000"));

        string = (String) mFixTimeZone.invoke(null, SHORT_DATE_0);
        assertEquals(string, SHORT_DATE_0);

        string = (String) mFixTimeZone.invoke(null, SHORT_DATE_1);
        assertEquals(string, SHORT_DATE_1);
    }

    @Test
    public void testShortDateConstruction() throws Exception {
        SimpleDate sd = mConstructor.newInstance(mShortFormat.parse(SHORT_DATE_0));
        assertNotNull(sd);

        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(sd.getDate());
        assertEquals(calendar.get(Calendar.YEAR), 2013);
        assertEquals(calendar.get(Calendar.MONTH), Calendar.JULY);
        assertEquals(calendar.get(Calendar.DAY_OF_MONTH), 18);

        sd = mConstructor.newInstance(mShortFormat.parse(SHORT_DATE_1));
        assertNotNull(sd);

        calendar.setTime(sd.getDate());
        assertEquals(calendar.get(Calendar.YEAR), 2016);
        assertEquals(calendar.get(Calendar.MONTH), Calendar.MARCH);
        assertEquals(calendar.get(Calendar.DAY_OF_MONTH), 4);
    }

}
