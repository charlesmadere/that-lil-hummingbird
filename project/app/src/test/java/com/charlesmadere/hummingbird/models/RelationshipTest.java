package com.charlesmadere.hummingbird.models;

import android.os.Build;

import com.charlesmadere.hummingbird.BuildConfig;
import com.charlesmadere.hummingbird.misc.GsonUtils;
import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.M)
public class RelationshipTest {

    private static final String ARRAY = "{\"data\":[{\"type\":\"activities\",\"id\":\"954c9a80-b21d-11e6-8080-800034b663af\"}]}";
    private static final String LINKS = "{\"links\":{\"self\":\"https://staging.kitsu.io/api/edge/episodes/185121/relationships/media\",\"related\":\"https://staging.kitsu.io/api/edge/episodes/185121/media\"}}";
    private static final String NULL = "{\"data\":null}";
    private static final String OBJECT = "{\"data\":{\"type\":\"activities\",\"id\":\"954c9a80-b21d-11e6-8080-800034b663af\"}}";

    private Relationship mArray;
    private Relationship mLinks;
    private Relationship mNull;
    private Relationship mObject;


    @Before
    public void setUp() throws Exception {
        final Gson gson = GsonUtils.getGson();
        mArray = gson.fromJson(ARRAY, Relationship.class);
        mLinks = gson.fromJson(LINKS, Relationship.class);
        mNull = gson.fromJson(NULL, Relationship.class);
        mObject = gson.fromJson(OBJECT, Relationship.class);
    }

    @Test
    public void testGetArray() throws Exception {
        assertNotNull(mArray.getArray());
        assertNull(mLinks.getArray());
        assertNull(mObject.getArray());
    }

    @Test
    public void testGetLinks() throws Exception {
        assertNull(mArray.getLinks());
        assertNotNull(mLinks.getLinks());
        assertNull(mObject.getLinks());
    }

    @Test
    public void testGetObject() throws Exception {
        assertNull(mArray.getObject());
        assertNull(mLinks.getObject());
        assertNotNull(mObject.getObject());
    }

    @Test
    public void testHasArray() throws Exception {
        assertTrue(mArray.hasArray());
        assertFalse(mLinks.hasArray());
        assertFalse(mObject.hasArray());
    }

    @Test
    public void testHasLinks() throws Exception {
        assertFalse(mArray.hasLinks());
        assertTrue(mLinks.hasLinks());
        assertFalse(mObject.hasLinks());
    }

    @Test
    public void testHasObject() throws Exception {
        assertFalse(mArray.hasObject());
        assertFalse(mLinks.hasObject());
        assertTrue(mObject.hasObject());
    }

    @Test
    public void testNull() throws Exception {
        assertNull(mNull);
    }

}
