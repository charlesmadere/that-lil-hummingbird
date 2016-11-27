package com.charlesmadere.hummingbird.models;

import android.os.Build;

import com.charlesmadere.hummingbird.BuildConfig;
import com.charlesmadere.hummingbird.misc.GsonUtils;
import com.google.gson.reflect.TypeToken;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.M)
public class AppNewsTest {

    private static final String JSON = "[{\"important\":true,\"epoch\":1480279982,\"body\":\"Soon, Hummingbird v3 will be released. This will render the app completely unusable. Sorry about this, but I am working hard to update the app's functionality to support v3.\\n\\nImprint for Hummingbird (another Hummingbird Android app) will encounter this very same problem, but it's possible that it will gain v3 support earlier than this app.\\n\\nSo again, sorry about all this, but I appreciate your patience very much!\",\"head\":\"Hummingbird v3 Soon\",\"id\":\"11\"},{\"important\":false,\"epoch\":1476798297,\"body\":\"Finally we've arrived at version 1.0 of the app! Thank you to everyone that helped me test or improve the app in any way.\\n\\nThis app is open source! If you're interested in helping, please fork the code and do as you please! I'd really appreciate it!\",\"head\":\"That Li'l Hummingbird v1.0!\",\"id\":\"10\",\"links\":[{\"title\":\"GitHub / That Lil Hummingbird\",\"url\":\"https://github.com/charlesmadere/that-lil-hummingbird\"},{\"title\":\"Hummingbird / ThatLilChestnut\",\"url\":\"https://hummingbird.me/users/ThatLilChestnut\"}]}]";

    private ArrayList<AppNews> mAppNews;


    @Before
    public void setUp() throws Exception {
        mAppNews = GsonUtils.getGson().fromJson(JSON, new TypeToken<ArrayList<AppNews>>(){}.getType());
    }

    @Test
    public void testHasLinks() throws Exception {
        assertFalse(mAppNews.get(0).hasLinks());
        assertTrue(mAppNews.get(1).hasLinks());
    }

}
