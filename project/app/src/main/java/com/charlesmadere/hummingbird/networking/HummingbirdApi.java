package com.charlesmadere.hummingbird.networking;

import com.charlesmadere.hummingbird.models.AbsAnime;
import com.charlesmadere.hummingbird.models.AddAnimeLibraryEntryResponse;
import com.charlesmadere.hummingbird.models.AddMangaLibraryEntryResponse;
import com.charlesmadere.hummingbird.models.AnimeDigest;
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
import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface HummingbirdApi {

    String ACCEPT_JSON_MIMETYPE = "Accept: application/json";


    /*
     * v1
     * https://github.com/hummingbird-me/hummingbird/wiki/API-v1-Methods
     */

    @POST("api/v1/users/authenticate")
    Call<String> authenticate(@Body AuthInfo authInfo);


    /*
     * internal API
     */

    @Headers(ACCEPT_JSON_MIMETYPE)
    @POST("library_entries")
    Call<AddAnimeLibraryEntryResponse> addAnimeLibraryEntry(@Header("Cookie") String authToken,
            @Body JsonElement body);

    @Headers(ACCEPT_JSON_MIMETYPE)
    @POST("manga_library_entries")
    Call<AddMangaLibraryEntryResponse> addMangaLibraryEntry(@Header("Cookie") String authToken,
            @Body JsonElement body);

    @DELETE("library_entries/{libraryEntryId}")
    Call<Void> deleteAnimeLibraryEntry(@Header("Cookie") String authToken,
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

    @Headers(ACCEPT_JSON_MIMETYPE)
    @GET("anime/{animeId}")
    Call<AbsAnime> getAnime(@Header("Cookie") String authToken, @Path("animeId") String animeId);

    @GET("full_anime/{animeId}")
    Call<AnimeDigest> getAnimeDigest(@Header("Cookie") String authToken,
            @Path("animeId") String animeId);

    @Headers(ACCEPT_JSON_MIMETYPE)
    @GET("library_entries")
    Call<Feed> getAnimeLibraryEntries(@Header("Cookie") String authToken,
            @Query("user_id") String userId, @Query("status") String watchingStatusLibraryUpdateValue);

    @GET("users")
    Call<Feed> getFollowedUsers(@Header("Cookie") String authToken,
            @Query("followed_by") String username, @Query("page") Integer page);

    @GET("users")
    Call<Feed> getFollowingUsers(@Header("Cookie") String authToken,
            @Query("followers_of") String username, @Query("page") Integer page);

    @Headers(ACCEPT_JSON_MIMETYPE)
    @GET("franchises/{franchiseId}")
    Call<Franchise> getFranchise(@Header("Cookie") String authToken,
            @Path("franchiseId") String franchiseId);

    @Headers(ACCEPT_JSON_MIMETYPE)
    @GET("groups/{groupId}")
    Call<GroupDigest> getGroup(@Header("Cookie") String authToken, @Path("groupId") String groupId);

    @GET("group_members")
    Call<Feed> getGroupMembers(@Header("Cookie") String authToken,
            @Query("group_id") String groupId, @Query("page") Integer page);

    @GET("stories")
    Call<Feed> getGroupStories(@Header("Cookie") String authToken,
            @Query("group_id") String groupId, @Query("page") Integer page);

    @GET("full_manga/{mangaId}")
    Call<MangaDigest> getMangaDigest(@Header("Cookie") String authToken,
            @Path("mangaId") String mangaId);

    @Headers(ACCEPT_JSON_MIMETYPE)
    @GET("manga_library_entries")
    Call<Feed> getMangaLibraryEntries(@Header("Cookie") String authToken,
            @Query("user_id") String userId, @Query("status") String readingStatusLibraryUpdateValue);

    @GET("stories")
    Call<Feed> getNewsFeed(@Header("Cookie") String authToken,
            @Query("news_feed") Boolean newsFeed, @Query("page") Integer page);

    @Headers(ACCEPT_JSON_MIMETYPE)
    @GET("notifications")
    Call<Feed> getNotifications(@Header("Cookie") String authToken, @Query("page") Integer page);

    @GET("substories")
    Call<Feed> getSubstories(@Header("Cookie") String authToken, @Query("story_id") String storyId,
            @Query("page") Integer page);

    @Headers(ACCEPT_JSON_MIMETYPE)
    @GET("users/{username}")
    Call<User> getUser(@Header("Cookie") String authToken, @Path("username") String username);

    @GET("user_infos/{username}")
    Call<UserDigest> getUserDigest(@Header("Cookie") String authToken,
            @Path("username") String username);

    @Headers(ACCEPT_JSON_MIMETYPE)
    @GET("groups")
    Call<Feed> getUserGroups(@Header("Cookie") String authToken, @Query("user_id") String userId,
            @Query("page") Integer page);

    @Headers(ACCEPT_JSON_MIMETYPE)
    @GET("reviews")
    Call<Feed> getUserReviews(@Header("Cookie") String authToken, @Query("user_id") String userId,
            @Query("page") Integer page);

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

    @Headers(ACCEPT_JSON_MIMETYPE)
    @PUT("library_entries/{libraryEntryId}")
    Call<Void> updateAnimeLibraryEntry(@Header("Cookie") String authToken,
            @Path("libraryEntryId") String libraryEntryId, @Body JsonElement body);

    @Headers(ACCEPT_JSON_MIMETYPE)
    @PUT("manga_library_entries/{libraryEntryId}")
    Call<Void> updateMangaLibraryEntry(@Header("Cookie") String authToken,
            @Path("libraryEntryId") String libraryEntryId, @Body JsonElement body);

}
