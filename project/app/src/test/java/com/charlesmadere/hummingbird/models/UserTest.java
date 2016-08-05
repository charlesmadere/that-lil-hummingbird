package com.charlesmadere.hummingbird.models;

import android.os.Build;

import com.charlesmadere.hummingbird.BuildConfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.M)
public class UserTest {

    private static final String URL0 = "https://static.hummingbird.me/users/avatars/000/042/603/thumb_small/wopian_2x.jpg?1466028564";
    private static final String URL1 = "https://static.hummingbird.me/users/avatars/000/077/547/thumb/ryuuko_square3.jpg?1470293290";
    private static final String URL2 = "https://static.hummingbird.me/users/avatars/000/090/096/thumb/Yoko_La_Yoko.jpg?1441097436";
    private static final String URL3 = "https://static.hummingbird.me/users/avatars/000/077/547/medium/ryuuko_square3.jpg?1470293290";
    private static final String URL4 = "https://static.hummingbird.me/users/avatars/000/067/566/thumb_small/Darth_Vader_riding_Charizard_colored.jpg?1416027708";
    private static final String URL5 = "https://static.hummingbird.me/users/avatars/000/079/485/small/sdpu3.jpg?1456852979";
    private static final String URL6 = "https://static.hummingbird.me/users/avatars/000/042/603.jpg";
    private static final String URL7 = "https://static.hummingbird.me/users/avatars/000/042/medium.jpg";
    private static final String URL8 = "https://static.hummingbird.me/users/avatars/000/042/medium-128.jpg";


    @Test
    public void testGetAvatars() throws Exception {
        assertNull(User.getAvatars(null));
        assertNull(User.getAvatars(""));
        assertNull(User.getAvatars("Hello, World!"));
        assertNull(User.getAvatars("https://www.google.com/"));
        assertNull(User.getAvatars(URL6));
        assertNull(User.getAvatars(URL7));
        assertNull(User.getAvatars(URL8));

        assertNotNull(User.getAvatars(URL0));
        assertNotNull(User.getAvatars(URL1));
        assertNotNull(User.getAvatars(URL2));
        assertNotNull(User.getAvatars(URL3));
        assertNotNull(User.getAvatars(URL4));
        assertNotNull(User.getAvatars(URL5));
    }

}
