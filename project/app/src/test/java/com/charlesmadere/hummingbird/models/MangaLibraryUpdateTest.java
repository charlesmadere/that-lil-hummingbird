package com.charlesmadere.hummingbird.models;

import android.os.Build;

import com.charlesmadere.hummingbird.BuildConfig;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertFalse;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.M)
public class MangaLibraryUpdateTest {

    private MangaLibraryUpdate mLibraryUpdate;


    @Before
    public void setUp() throws Exception {
        mLibraryUpdate = new MangaLibraryUpdate(MangaLibraryEntryTest.get());
    }

    @Test
    public void testSetChaptersRead() throws Exception {
        assertFalse(mLibraryUpdate.containsModifications());

        // TODO
    }

    @Test
    public void testSetVolumesRead() throws Exception {
        assertFalse(mLibraryUpdate.containsModifications());

        // TODO
    }

}
