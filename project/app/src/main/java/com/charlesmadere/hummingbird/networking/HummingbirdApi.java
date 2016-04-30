package com.charlesmadere.hummingbird.networking;

import com.charlesmadere.hummingbird.models.AbsAnime;
import com.charlesmadere.hummingbird.models.AnimeV2;
import com.charlesmadere.hummingbird.models.AuthInfo;
import com.charlesmadere.hummingbird.models.LibraryEntry;
import com.charlesmadere.hummingbird.models.LibraryUpdate;
import com.charlesmadere.hummingbird.models.NewsFeed;
import com.charlesmadere.hummingbird.models.Story;
import com.charlesmadere.hummingbird.models.User;
import com.charlesmadere.hummingbird.models.UserStories;
import com.charlesmadere.hummingbird.models.WatchingStatus;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface HummingbirdApi {

    /*
     * v1
     * https://github.com/hummingbird-me/hummingbird/wiki/API-v1-Methods
     */

    @POST("api/v1/libaries/{id}")
    Call<LibraryEntry> addOrUpdateLibraryEntry(@Path("id") String id,
            @Body LibraryUpdate libraryUpdate);

    @POST("api/v1/users/authenticate")
    Call<String> authenticate(@Body AuthInfo authInfo);

    @GET("api/v1/users/{username}/feed")
    Call<ArrayList<Story>> getActivityFeed(@Path("username") String username);

    @GET("api/v1/users/{username}/favorite_anime")
    Call<ArrayList<AbsAnime>> getFavoriteAnime(@Path("username") String username);

    @GET("api/v1/users/{username}/library")
    Call<ArrayList<LibraryEntry>> getLibraryEntries(@Path("username") String username,
            @Query("status") WatchingStatus watchingStatus);

    @GET("api/v1/users/{username}")
    Call<User> getUser(@Path("username") String username);

    @POST("api/v1/libraries/{id}/remove")
    Call<Boolean> removeLibraryEntry(@Header("auth_token") String authToken,
            @Path("id") String id);

    @GET("api/v1/search/anime")
    Call<ArrayList<AbsAnime>> searchAnimeByTitle(@Query("query") String query);


    /*
     * v2
     * https://github.com/hummingbird-me/hummingbird/wiki/API-v2-Methods
     */

    @GET("api/v2/anime/{id}")
    Call<AnimeV2> getAnimeById(@Header("X-Client-Id") String apiKey, @Path("id") String id);

    @GET("api/v2/anime/myanimelist:{malid}")
    Call<AnimeV2> getAnimeByMyAnimeListId(@Header("X-Client-Id") String apiKey,
            @Path("malid") String malid);


    /*
     * internal API
     */

    @GET("stories")
    Call<NewsFeed> getNewsFeed(@Header("cookie") String authToken,
            @Query("news_feed") Boolean newsFeed, @Query("page") Integer page);

    @GET("stories")
    Call<UserStories> getUserStories(@Header("cookie") String authToken,
            @Path("user_id") String userId);

}
