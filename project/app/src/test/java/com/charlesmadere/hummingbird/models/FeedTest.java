package com.charlesmadere.hummingbird.models;

import android.os.Build;

import com.charlesmadere.hummingbird.BuildConfig;
import com.charlesmadere.hummingbird.misc.GsonUtils;
import com.google.gson.Gson;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.M)
public class FeedTest {

    private static final String STORY_PAGE = "{\n" +
            "    \"users\": [\n" +
            "        {\n" +
            "            \"id\": \"maliz\",\n" +
            "            \"cover_image_url\": \"https://static.hummingbird.me/users/cover_images/000/052/786/thumb/data.jpg?1459470362\",\n" +
            "            \"avatar_template\": \"https://static.hummingbird.me/users/avatars/000/052/786/{size}/tumblr_o1spa00CHV1unau8ao1_500.gif?1473492700\",\n" +
            "            \"rating_type\": \"advanced\",\n" +
            "            \"bio\": \"Why does everyone think I'm an alcoholic? I'm too poor.\",\n" +
            "            \"about\": \"http://img02.deviantart.net/a5ca/i/2016/023/0/d/osomatsu_in_wonderland___by_sweetschemer-d9p0ppw.png\\nhttp://i.imgur.com/C8bLF8P.gif\\nElizabeth: just your average American college girl (Asian studies!) English and Français ok; learning 中文 and 日本語. I love :sushi: :pizza: :tea: :beer: :cocktail: :tv: :books: :video_game: and @jam :kissing_heart: (baka bf)\\noh and @silber made this thingy and it's my \\\"aesthetic\\\"\\nhttp://i.imgur.com/xvhn0EO.jpg\\nhttps://hummingbird.me/groups/look-i-m-watching-it-okay\",\n" +
            "            \"is_followed\": false,\n" +
            "            \"location\": \"VA but currently in 台北\",\n" +
            "            \"website\": \"http://maliz.flavors.me/\",\n" +
            "            \"waifu\": \"Takashi Natsume\",\n" +
            "            \"waifu_or_husbando\": \"Husbando\",\n" +
            "            \"waifu_slug\": \"natsume-yuujinchou\",\n" +
            "            \"waifu_char_id\": \"25596\",\n" +
            "            \"last_sign_in_at\": null,\n" +
            "            \"current_sign_in_at\": null,\n" +
            "            \"is_admin\": false,\n" +
            "            \"following_count\": 221,\n" +
            "            \"follower_count\": 209,\n" +
            "            \"is_pro\": null,\n" +
            "            \"about_formatted\": \"<a href=\\\"http://img02.deviantart.net/a5ca/i/2016/023/0/d/osomatsu_in_wonderland___by_sweetschemer-d9p0ppw.png\\\" target=\\\"_blank\\\"><img src=\\\"http://img02.deviantart.net/a5ca/i/2016/023/0/d/osomatsu_in_wonderland___by_sweetschemer-d9p0ppw.png\\\"></a><br><a href=\\\"http://i.imgur.com/C8bLF8P.gif\\\" target=\\\"_blank\\\"><img src=\\\"http://i.imgur.com/C8bLF8P.gif\\\"></a><br>Elizabeth: just your average American college girl (Asian studies!) English and Français ok; learning 中文 and 日本語. I love <img class='emoji' draggable='false' title=':sushi:' alt='\uD83C\uDF63' src='https://twemoji.maxcdn.com/36x36/1f363.png'> <img class='emoji' draggable='false' title=':pizza:' alt='\uD83C\uDF55' src='https://twemoji.maxcdn.com/36x36/1f355.png'> <img class='emoji' draggable='false' title=':tea:' alt='\uD83C\uDF75' src='https://twemoji.maxcdn.com/36x36/1f375.png'> <img class='emoji' draggable='false' title=':beer:' alt='\uD83C\uDF7A' src='https://twemoji.maxcdn.com/36x36/1f37a.png'> <img class='emoji' draggable='false' title=':cocktail:' alt='\uD83C\uDF78' src='https://twemoji.maxcdn.com/36x36/1f378.png'> <img class='emoji' draggable='false' title=':tv:' alt='\uD83D\uDCFA' src='https://twemoji.maxcdn.com/36x36/1f4fa.png'> <img class='emoji' draggable='false' title=':books:' alt='\uD83D\uDCDA' src='https://twemoji.maxcdn.com/36x36/1f4da.png'> <img class='emoji' draggable='false' title=':video_game:' alt='\uD83C\uDFAE' src='https://twemoji.maxcdn.com/36x36/1f3ae.png'> and <a href=\\\"//hummingbird.me/users/jam\\\" target=\\\"_blank\\\" data-user-name=\\\"jam\\\" class=\\\"name\\\">@jam</a> <img class='emoji' draggable='false' title=':kissing_heart:' alt='\uD83D\uDE18' src='https://twemoji.maxcdn.com/36x36/1f618.png'> (baka bf)<br>oh and <a href=\\\"//hummingbird.me/users/silber\\\" target=\\\"_blank\\\" data-user-name=\\\"silber\\\" class=\\\"name\\\">@silber</a> made this thingy and it's my \\\"aesthetic\\\"<br><a href=\\\"http://i.imgur.com/xvhn0EO.jpg\\\" target=\\\"_blank\\\"><img src=\\\"http://i.imgur.com/xvhn0EO.jpg\\\"></a><br><a href=\\\"https://hummingbird.me/groups/look-i-m-watching-it-okay\\\" target=\\\"_blank\\\">https://hummingbird.me/groups/look-i-m-watching-it-okay</a>\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"Prunes\",\n" +
            "            \"cover_image_url\": \"https://static.hummingbird.me/users/cover_images/000/064/078/thumb/data.jpg?1474552018\",\n" +
            "            \"avatar_template\": \"https://static.hummingbird.me/users/avatars/000/064/078/{size}/Yotsuba2.jpg?1474552520\",\n" +
            "            \"rating_type\": \"advanced\",\n" +
            "            \"bio\": \"This ain't no side hustle.\\n\\n\",\n" +
            "            \"about\": \"WHO WOULD WIN A T-REX OF A PLASTIC  BAG YOUR MOM\\n\\n\\n\\n\",\n" +
            "            \"is_followed\": false,\n" +
            "            \"location\": \"Singapore\",\n" +
            "            \"website\": \"http://prunes.flavors.me/\",\n" +
            "            \"waifu\": \"\",\n" +
            "            \"waifu_or_husbando\": \"Waifu\",\n" +
            "            \"waifu_slug\": \"lost-universe\",\n" +
            "            \"waifu_char_id\": \"13303\",\n" +
            "            \"last_sign_in_at\": null,\n" +
            "            \"current_sign_in_at\": null,\n" +
            "            \"is_admin\": false,\n" +
            "            \"following_count\": 167,\n" +
            "            \"follower_count\": 262,\n" +
            "            \"is_pro\": false,\n" +
            "            \"about_formatted\": \"WHO WOULD WIN A T-REX OF A PLASTIC  BAG YOUR MOM<br><br>\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"FreyKeehl\",\n" +
            "            \"cover_image_url\": \"https://static.hummingbird.me/users/cover_images/000/093/768/thumb/data.jpg?1467546736\",\n" +
            "            \"avatar_template\": \"https://static.hummingbird.me/users/avatars/000/093/768/{size}/profile.jpg?1467366506\",\n" +
            "            \"rating_type\": \"advanced\",\n" +
            "            \"bio\": \"nope.\",\n" +
            "            \"about\": \"Lily of the Valley; We are Born when We Die\\n\\n(Knowing the mouse might one day watch as much anime as it has read manga... It fills you with determination.)\",\n" +
            "            \"is_followed\": false,\n" +
            "            \"location\": null,\n" +
            "            \"website\": null,\n" +
            "            \"waifu\": null,\n" +
            "            \"waifu_or_husbando\": \"Waifu\",\n" +
            "            \"waifu_slug\": \"#\",\n" +
            "            \"waifu_char_id\": \"0000\",\n" +
            "            \"last_sign_in_at\": null,\n" +
            "            \"current_sign_in_at\": null,\n" +
            "            \"is_admin\": false,\n" +
            "            \"following_count\": 26,\n" +
            "            \"follower_count\": 19,\n" +
            "            \"is_pro\": null,\n" +
            "            \"about_formatted\": \"Lily of the Valley; We are Born when We Die<br><br>(Knowing the mouse might one day watch as much anime as it has read manga... It fills you with determination.)\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"ony\",\n" +
            "            \"cover_image_url\": \"https://static.hummingbird.me/users/cover_images/000/008/929/thumb/data.jpg?1459857772\",\n" +
            "            \"avatar_template\": \"https://static.hummingbird.me/users/avatars/000/008/929/{size}/Q_rmYlXr-glitched-5.4.2016-11-29-14.png?1459864163\",\n" +
            "            \"rating_type\": \"advanced\",\n" +
            "            \"bio\": \"♥ ♥ y u n g o n y\\n♪♫♬ - ☆*✲ﾟ*｡(((´♡‿♡`+)))｡*ﾟ✲*☆ - ♪♫♬\",\n" +
            "            \"about\": \"https://49.media.tumblr.com/5c88cdb5fe7b4ab53830b4e999d38967/tumblr_o03ww8WCix1qehrvso1_500.gif\\nhttps://www.youtube.com/watch?v=dSX60458WGw\\n♪♫♬ - ☆*✲ﾟ*｡(((´♡‿♡`+)))｡*ﾟ✲*☆ - ♪♫♬\",\n" +
            "            \"is_followed\": false,\n" +
            "            \"location\": \"Rosenheim, Germany\",\n" +
            "            \"website\": \"http://steamcommunity.com/id/yungony/\",\n" +
            "            \"waifu\": \"Chino Kafuu\",\n" +
            "            \"waifu_or_husbando\": \"Waifu\",\n" +
            "            \"waifu_slug\": \"gochuumon-wa-usagi-desu-ka-2\",\n" +
            "            \"waifu_char_id\": \"40940\",\n" +
            "            \"last_sign_in_at\": \"2013-12-04T20:32:46.000Z\",\n" +
            "            \"current_sign_in_at\": \"2013-12-04T20:32:47.746Z\",\n" +
            "            \"is_admin\": false,\n" +
            "            \"following_count\": 246,\n" +
            "            \"follower_count\": 242,\n" +
            "            \"is_pro\": false,\n" +
            "            \"about_formatted\": \"<a href=\\\"https://49.media.tumblr.com/5c88cdb5fe7b4ab53830b4e999d38967/tumblr_o03ww8WCix1qehrvso1_500.gif\\\" target=\\\"_blank\\\"><img src=\\\"https://49.media.tumblr.com/5c88cdb5fe7b4ab53830b4e999d38967/tumblr_o03ww8WCix1qehrvso1_500.gif\\\"></a><br><iframe width=\\\"480\\\" height=\\\"270\\\" src=\\\"https://www.youtube.com/embed/dSX60458WGw?feature=oembed&amp;wmode=opaque\\\" frameborder=\\\"0\\\" allowfullscreen></iframe><br>♪♫♬ - ☆*✲ﾟ*｡(((´♡‿♡`+)))｡*ﾟ✲*☆ - ♪♫♬\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"Eline\",\n" +
            "            \"cover_image_url\": \"https://static.hummingbird.me/users/cover_images/000/052/264/thumb/data.jpg?1473159684\",\n" +
            "            \"avatar_template\": \"https://static.hummingbird.me/users/avatars/000/052/264/{size}/avy_5.9.PNG?1471189909\",\n" +
            "            \"rating_type\": \"advanced\",\n" +
            "            \"bio\": \"I am the accumulated experience of my years spent alive on planet earth. I am me, inexorably.\",\n" +
            "            \"about\": \"25 | She/Her | Pansexual | Polyamorous | Autistic | Cooking queen | Supreme nerd | Trash compactor\\nhttps://s22.postimg.org/3yobuamsx/tumblr_o33o2o_BLsu1u77u56o1_540.gif\\nIf there is anything I've learned in life, it is that I have much yet to learn and that one could always stand to be more understanding, helpful and diplomatic.\\n\\nI'm a stupid nerd that loves pizza, Ghost in The Shell, Undertale, taking trips on sunny days, marathons of media and being trash.\\n\\nHit me up, I don't bite.\",\n" +
            "            \"is_followed\": false,\n" +
            "            \"location\": \"Small town Norway\",\n" +
            "            \"website\": \"https://twitter.com/ElineSchulze\",\n" +
            "            \"waifu\": \"Shizuru Kuwabara\",\n" +
            "            \"waifu_or_husbando\": \"Waifu\",\n" +
            "            \"waifu_slug\": \"yu-yu-hakusho\",\n" +
            "            \"waifu_char_id\": \"6963\",\n" +
            "            \"last_sign_in_at\": null,\n" +
            "            \"current_sign_in_at\": null,\n" +
            "            \"is_admin\": false,\n" +
            "            \"following_count\": 41,\n" +
            "            \"follower_count\": 82,\n" +
            "            \"is_pro\": true,\n" +
            "            \"about_formatted\": \"25 | She/Her | Pansexual | Polyamorous | Autistic | Cooking queen | Supreme nerd | Trash compactor<br><a href=\\\"https://s22.postimg.org/3yobuamsx/tumblr_o33o2o_BLsu1u77u56o1_540.gif\\\" target=\\\"_blank\\\"><img src=\\\"https://s22.postimg.org/3yobuamsx/tumblr_o33o2o_BLsu1u77u56o1_540.gif\\\"></a><br>If there is anything I've learned in life, it is that I have much yet to learn and that one could always stand to be more understanding, helpful and diplomatic.<br><br>I'm a stupid nerd that loves pizza, Ghost in The Shell, Undertale, taking trips on sunny days, marathons of media and being trash.<br><br>Hit me up, I don't bite.\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"substories\": [\n" +
            "        {\n" +
            "            \"id\": 27707761,\n" +
            "            \"type\": \"reply\",\n" +
            "            \"created_at\": \"2016-10-21T10:40:30.148Z\",\n" +
            "            \"reply\": \"ahahahahha well... you've got five months until it comes out\",\n" +
            "            \"story_id\": 8488383,\n" +
            "            \"user_id\": \"maliz\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 27706831,\n" +
            "            \"type\": \"reply\",\n" +
            "            \"created_at\": \"2016-10-21T10:18:14.341Z\",\n" +
            "            \"reply\": \"This isn't funny. I just ordered a NEW 3DS XL.....\",\n" +
            "            \"story_id\": 8488383,\n" +
            "            \"user_id\": \"Prunes\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 27705507,\n" +
            "            \"type\": \"reply\",\n" +
            "            \"created_at\": \"2016-10-21T07:42:31.777Z\",\n" +
            "            \"reply\": \"I've never owned anything Nintendo but I'm thinking of nabbing a 3DS if I find one cheap, but I will certainly be looking this out for the reviews when this one comes out. \",\n" +
            "            \"story_id\": 8488383,\n" +
            "            \"user_id\": \"FreyKeehl\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 27694759,\n" +
            "            \"type\": \"reply\",\n" +
            "            \"created_at\": \"2016-10-20T15:06:43.275Z\",\n" +
            "            \"reply\": \"<blockquote class=\\\"wp-embedded-content\\\"><a href=\\\"https://blogs.nvidia.com/blog/2016/10/20/nintendo-switch/\\\">NVIDIA Technology Powers New Home Gaming System, Nintendo Switch</a></blockquote>\\n<script type=\\\"text/javascript\\\">\\n<!--//--><![CDATA[//><!--\\n\\t\\t!function(a,b){\\\"use strict\\\";function c(){if(!e){e=!0;var a,c,d,f,g=-1!==navigator.appVersion.indexOf(\\\"MSIE 10\\\"),h=!!navigator.userAgent.match(/Trident.*rv:11\\\\./),i=b.querySelectorAll(\\\"iframe.wp-embedded-content\\\");for(c=0;c<i.length;c++)if(d=i[c],!d.getAttribute(\\\"data-secret\\\")){if(f=Math.random().toString(36).substr(2,10),d.src+=\\\"#?secret=\\\"+f,d.setAttribute(\\\"data-secret\\\",f),g||h)a=d.cloneNode(!0),a.removeAttribute(\\\"security\\\"),d.parentNode.replaceChild(a,d)}else;}}var d=!1,e=!1;if(b.querySelector)if(a.addEventListener)d=!0;if(a.wp=a.wp||{},!a.wp.receiveEmbedMessage)if(a.wp.receiveEmbedMessage=function(c){var d=c.data;if(d.secret||d.message||d.value)if(!/[^a-zA-Z0-9]/.test(d.secret)){var e,f,g,h,i,j=b.querySelectorAll('iframe[data-secret=\\\"'+d.secret+'\\\"]'),k=b.querySelectorAll('blockquote[data-secret=\\\"'+d.secret+'\\\"]');for(e=0;e<k.length;e++)k[e].style.display=\\\"none\\\";for(e=0;e<j.length;e++)if(f=j[e],c.source===f.contentWindow){if(f.removeAttribute(\\\"style\\\"),\\\"height\\\"===d.message){if(g=parseInt(d.value,10),g>1e3)g=1e3;else if(~~g<200)g=200;f.height=g}if(\\\"link\\\"===d.message)if(h=b.createElement(\\\"a\\\"),i=b.createElement(\\\"a\\\"),h.href=f.getAttribute(\\\"src\\\"),i.href=d.value,i.host===h.host)if(b.activeElement===f)a.top.location.href=d.value}else;}},d)a.addEventListener(\\\"message\\\",a.wp.receiveEmbedMessage,!1),b.addEventListener(\\\"DOMContentLoaded\\\",c,!1),a.addEventListener(\\\"load\\\",c,!1)}(window,document);\\n//--><!]]>\\n</script><iframe sandbox=\\\"allow-scripts\\\" security=\\\"restricted\\\" src=\\\"https://blogs.nvidia.com/blog/2016/10/20/nintendo-switch/embed/\\\" width=\\\"500\\\" height=\\\"281\\\" title=\\\"“NVIDIA Technology Powers New Home Gaming System, Nintendo Switch” — The Official NVIDIA Blog\\\" frameborder=\\\"0\\\" marginwidth=\\\"0\\\" marginheight=\\\"0\\\" scrolling=\\\"no\\\" class=\\\"wp-embedded-content\\\"></iframe>\",\n" +
            "            \"story_id\": 8488383,\n" +
            "            \"user_id\": \"maliz\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 27694732,\n" +
            "            \"type\": \"reply\",\n" +
            "            \"created_at\": \"2016-10-20T15:05:12.412Z\",\n" +
            "            \"reply\": \"<a href=\\\"http://www.nintendo.com/switch\\\" target=\\\"_blank\\\">http://www.nintendo.com/switch</a>\",\n" +
            "            \"story_id\": 8488383,\n" +
            "            \"user_id\": \"maliz\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 27694561,\n" +
            "            \"type\": \"reply\",\n" +
            "            \"created_at\": \"2016-10-20T14:46:48.132Z\",\n" +
            "            \"reply\": \"i want this now\",\n" +
            "            \"story_id\": 8488383,\n" +
            "            \"user_id\": \"ony\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 27694441,\n" +
            "            \"type\": \"reply\",\n" +
            "            \"created_at\": \"2016-10-20T14:35:42.168Z\",\n" +
            "            \"reply\": \"Holy shit. <img class='emoji' draggable='false' title=':ok_hand:' alt='\uD83D\uDC4C' src='https://twemoji.maxcdn.com/36x36/1f44c.png'> <img class='emoji' draggable='false' title=':ok_hand:' alt='\uD83D\uDC4C' src='https://twemoji.maxcdn.com/36x36/1f44c.png'> <img class='emoji' draggable='false' title=':ok_hand:' alt='\uD83D\uDC4C' src='https://twemoji.maxcdn.com/36x36/1f44c.png'> \",\n" +
            "            \"story_id\": 8488383,\n" +
            "            \"user_id\": \"Eline\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 27694354,\n" +
            "            \"type\": \"reply\",\n" +
            "            \"created_at\": \"2016-10-20T14:30:05.149Z\",\n" +
            "            \"reply\": \"<a href=\\\"https://www.nintendo.co.jp/corporate/release/2016/161020.html\\\" target=\\\"_blank\\\">https://www.nintendo.co.jp/corporate/release/2016/161020.html</a>\",\n" +
            "            \"story_id\": 8488383,\n" +
            "            \"user_id\": \"maliz\"\n" +
            "        }\n" +
            "    ]\n" +
            "}\n";


    @Test
    public void testHasSubstories() {
        final Gson gson = GsonUtils.getGson();
        final Feed feed = gson.fromJson(STORY_PAGE, Feed.class);
        assertTrue(feed.hasSubstories());
    }

    @Test
    public void testHasUsers() {
        final Gson gson = GsonUtils.getGson();
        final Feed feed = gson.fromJson(STORY_PAGE, Feed.class);
        assertTrue(feed.hasUsers());
    }

    @Test
    public void testHydrate() {
        final Gson gson = GsonUtils.getGson();
        final Feed feed = gson.fromJson(STORY_PAGE, Feed.class);
        feed.hydrate();

        final ArrayList<AbsSubstory> substories = feed.getSubstories();

        // noinspection ConstantConditions
        for (final AbsSubstory substory : substories) {
            final ReplySubstory reply = (ReplySubstory) substory;
            assertNotNull(reply.getUser());
        }
    }

}
