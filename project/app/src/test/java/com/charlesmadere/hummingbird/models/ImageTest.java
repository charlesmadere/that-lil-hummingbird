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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.M)
public class ImageTest {

    private static final String COVER = "{\"small\":\"https://kitsu-media.s3.amazonaws.com/anime/cover_images/7882/small.jpg?1425430694\",\"large\":\"https://kitsu-media.s3.amazonaws.com/anime/cover_images/7882/large.jpg?1425430694\",\"original\":\"https://kitsu-media.s3.amazonaws.com/anime/cover_images/7882/original.jpg?1425430694\"}";
    private static final String JPEG = "\"https://kitsu-media.s3.amazonaws.com/users/avatars/67839/original.jpeg?1416241240\"";
    private static final String NULL = null;
    private static final String PNG = "\"https://kitsu-media.s3.amazonaws.com/users/avatars/67891/original.png?1433440827\"";
    private static final String POSTER = "{\"tiny\":\"https://kitsu-media.s3.amazonaws.com/anime/poster_images/7882/tiny.jpg?1435979093\",\"small\":\"https://kitsu-media.s3.amazonaws.com/anime/poster_images/7882/small.jpg?1435979093\",\"medium\":\"https://kitsu-media.s3.amazonaws.com/anime/poster_images/7882/medium.jpg?1435979093\",\"large\":\"https://kitsu-media.s3.amazonaws.com/anime/poster_images/7882/large.jpg?1435979093\",\"original\":\"https://kitsu-media.s3.amazonaws.com/anime/poster_images/7882/original.jpg?1435979093\"}";

    private Image mCover;
    private Image mJpeg;
    private Image mNull;
    private Image mPng;
    private Image mPoster;


    @Before
    public void setUp() throws Exception {
        final Gson gson = GsonUtils.getGson();
        mCover = gson.fromJson(COVER, Image.class);
        mJpeg = gson.fromJson(JPEG, Image.class);
        mNull = gson.fromJson(NULL, Image.class);
        mPng = gson.fromJson(PNG, Image.class);
        mPoster = gson.fromJson(POSTER, Image.class);
    }

    @Test
    public void testHasLarge() throws Exception {
        assertTrue(mCover.hasLarge());
        assertFalse(mJpeg.hasLarge());
        assertFalse(mPng.hasLarge());
        assertTrue(mPoster.hasLarge());
    }

    @Test
    public void testHasMedium() throws Exception {
        assertFalse(mCover.hasMedium());
        assertFalse(mJpeg.hasMedium());
        assertFalse(mPng.hasMedium());
        assertTrue(mPoster.hasMedium());
    }

    @Test
    public void testHasOriginal() throws Exception {
        assertTrue(mCover.hasOriginal());
        assertTrue(mJpeg.hasOriginal());
        assertTrue(mPng.hasOriginal());
        assertTrue(mPoster.hasOriginal());
    }

    @Test
    public void testHasSmall() throws Exception {
        assertTrue(mCover.hasSmall());
        assertFalse(mJpeg.hasSmall());
        assertFalse(mPng.hasSmall());
        assertTrue(mPoster.hasSmall());
    }

    @Test
    public void testHasTiny() throws Exception {
        assertFalse(mCover.hasTiny());
        assertFalse(mJpeg.hasTiny());
        assertFalse(mPng.hasTiny());
        assertTrue(mPoster.hasTiny());
    }

    @Test
    public void testNull() throws Exception {
        assertNull(mNull);
    }

}
