package com.charlesmadere.hummingbird.networking;

import com.charlesmadere.hummingbird.models.AbsAnime;
import com.charlesmadere.hummingbird.models.AnimeDigest;
import com.charlesmadere.hummingbird.models.AnimeLibraryEntry;
import com.charlesmadere.hummingbird.models.AuthInfo;
import com.charlesmadere.hummingbird.models.Feed;
import com.charlesmadere.hummingbird.models.Franchise;
import com.charlesmadere.hummingbird.models.GroupDigest;
import com.charlesmadere.hummingbird.models.MangaDigest;
import com.charlesmadere.hummingbird.models.SearchBundle;
import com.charlesmadere.hummingbird.models.SearchDepth;
import com.charlesmadere.hummingbird.models.SearchScope;
import com.charlesmadere.hummingbird.models.User;
import com.charlesmadere.hummingbird.models.UserDigest;
import com.charlesmadere.hummingbird.models.WatchingStatus;
import com.google.gson.JsonElement;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface HummingbirdApi {

    /*
     * v1
     * https://github.com/hummingbird-me/hummingbird/wiki/API-v1-Methods
     */

    @POST("api/v1/users/authenticate")
    Call<String> authenticate(@Body AuthInfo authInfo);

    @GET("api/v1/users/{username}/library")
    Call<ArrayList<AnimeLibraryEntry>> getLibraryEntries(@Path("username") String username,
            @Query("status") WatchingStatus watchingStatus);


    /*
     * internal API
     */

    @POST("library_entries")
    Call<Void> addLibraryEntry(@Header("Cookie") String authToken, @Header("Accept") String json,
            @Body JsonElement body);

    @POST("manga_library_entries")
    Call<Void> addMangaLibraryEntry(@Header("Cookie") String authToken,
            @Header("Accept") String json, @Body JsonElement body);

    @DELETE("library_entries/{libraryEntryId}")
    Call<Void> deleteLibraryEntry(@Header("Cookie") String authToken,
            @Path("libraryEntryId") String libraryEntryId);

    @DELETE("manga_library_entries/{libraryEntryId}")
    Call<Void> deleteMangaLibraryEntry(@Header("Cookie") String authToken,
            @Path("libraryEntryId") String libraryEntryId);

    @DELETE("stories/{storyId}")
    Call<Boolean> deleteStory(@Header("Cookie") String authToken, @Path("storyId") String storyId);

    @DELETE("substories/{substoryId}")
    Call<Boolean> deleteSubstory(@Header("Cookie") String authToken,
            @Path("substoryId") String substoryId);

    @PUT("quotes/{quoteId}")
    Call<Void> favoriteQuote(@Header("Cookie") String authToken, @Path("quoteId") String quoteId,
            @Body JsonElement body);

    @GET("anime/{animeId}")
    Call<AbsAnime> getAnime(@Header("Cookie") String authToken, @Header("Accept") String json,
            @Path("animeId") String animeId);

    @GET("full_anime/{animeId}")
    Call<AnimeDigest> getAnimeDigest(@Header("Cookie") String authToken,
            @Path("animeId") String animeId);

    @GET("users")
    Call<Feed> getFollowedUsers(@Header("Cookie") String authToken,
            @Query("followed_by") String username, @Query("page") Integer page);

    @GET("users")
    Call<Feed> getFollowingUsers(@Header("Cookie") String authToken,
            @Query("followers_of") String username, @Query("page") Integer page);

    @GET("franchises/{franchiseId}")
    Call<Franchise> getFranchise(@Header("Cookie") String authToken, @Header("Accept") String json,
            @Path("franchiseId") String franchiseId);

    @GET("groups/{groupId}")
    Call<GroupDigest> getGroup(@Header("Cookie") String authToken, @Header("Accept") String json,
            @Path("groupId") String groupId);

    @GET("group_members")
    Call<Feed> getGroupMembers(@Header("Cookie") String authToken,
            @Query("group_id") String groupId, @Query("page") Integer page);

    @GET("stories")
    Call<Feed> getGroupStories(@Header("Cookie") String authToken,
            @Query("group_id") String groupId, @Query("page") Integer page);

    @GET("full_manga/{mangaId}")
    Call<MangaDigest> getMangaDigest(@Header("Cookie") String authToken,
            @Path("mangaId") String mangaId);

    @GET("stories")
    Call<Feed> getNewsFeed(@Header("Cookie") String authToken,
            @Query("news_feed") Boolean newsFeed, @Query("page") Integer page);

    @GET("notifications")
    Call<Feed> getNotifications(@Header("Cookie") String authToken, @Header("Accept") String json);

    @GET("substories")
    Call<Feed> getSubstories(@Header("Cookie") String authToken, @Query("story_id") String storyId,
            @Query("page") Integer page);

    @GET("users/{username}")
    Call<User> getUser(@Header("Cookie") String authToken, @Header("Accept") String json,
            @Path("username") String username);

    @GET("user_infos/{username}")
    Call<UserDigest> getUserDigest(@Header("Cookie") String authToken,
            @Path("username") String username);

    @GET("groups")
    Call<Feed> getUserGroups(@Header("Cookie") String authToken, @Header("Accept") String json,
            @Query("user_id") String userId, @Query("page") Integer page);

    @GET("reviews")
    Call<Feed> getUserReviews(@Header("Cookie") String authToken, @Header("Accept") String json,
            @Query("user_id") String userId, @Query("page") Integer page);

    @GET("stories")
    Call<Feed> getUserStories(@Header("Cookie") String authToken,
            @Query("user_id") String username, @Query("page") Integer page);

    @PUT("stories/{storyId}")
    Call<Void> likeStory(@Header("Cookie") String authToken, @Path("storyId") String storyId,
            @Body JsonElement body);

    @POST("substories")
    Call<Void> postComment(@Header("Cookie") String authToken, @Body JsonElement body);

    @POST("stories")
    Call<Void> postToFeed(@Header("Cookie") String authToken, @Body JsonElement body);

    @GET("search.json")
    Call<SearchBundle> search(@Query("scope") SearchScope searchScope,
            @Query("depth") SearchDepth depth, @Query("query") String query);

    @POST("users/{userId}/follow")
    Call<Void> toggleFollowingOfUser(@Header("Cookie") String authToken,
            @Path("userId") String userId);

    @PUT("library_entries/{libraryEntryId}")
    Call<Void> updateLibraryEntry(@Header("Cookie") String authToken, @Header("Accept") String json,
            @Path("libraryEntryId") String libraryEntryId, @Body JsonElement body);

    @PUT("manga_library_entries/{libraryEntryId}")
    Call<Void> updateMangaLibraryEntry(@Header("Cookie") String authToken,
            @Header("Accept") String json, @Path("libraryEntryId") String libraryEntryId,
            @Body JsonElement body);

}
