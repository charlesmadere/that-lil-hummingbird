package com.charlesmadere.hummingbird.networking;

import com.charlesmadere.hummingbird.misc.Constants;
import com.charlesmadere.hummingbird.models.AddAnimeLibraryEntryResponse;
import com.charlesmadere.hummingbird.models.AddMangaLibraryEntryResponse;
import com.charlesmadere.hummingbird.models.AnimeDigest;
import com.charlesmadere.hummingbird.models.AnimeWrapper;
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

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface HummingbirdApi {

    String ACCEPT_JSON_MIMETYPE = "Accept: " + Constants.MIMETYPE_JSON;


    /*
     * internal API
     */

    @Headers(ACCEPT_JSON_MIMETYPE)
    @POST("library_entries")
    Call<AddAnimeLibraryEntryResponse> addAnimeLibraryEntry(@Body JsonElement body);

    @Headers(ACCEPT_JSON_MIMETYPE)
    @POST("manga_library_entries")
    Call<AddMangaLibraryEntryResponse> addMangaLibraryEntry(@Body JsonElement body);

    @DELETE("library_entries/{libraryEntryId}")
    Call<Void> deleteAnimeLibraryEntry(@Path("libraryEntryId") String libraryEntryId);

    @DELETE("manga_library_entries/{libraryEntryId}")
    Call<Void> deleteMangaLibraryEntry(@Path("libraryEntryId") String libraryEntryId);

    @DELETE("stories/{storyId}")
    Call<Boolean> deleteStory(@Path("storyId") String storyId);

    @DELETE("substories/{substoryId}")
    Call<Boolean> deleteSubstory(@Path("substoryId") String substoryId);

    @Headers(ACCEPT_JSON_MIMETYPE)
    @GET("anime/{animeId}")
    Call<AnimeWrapper> getAnime(@Path("animeId") String animeId);

    @GET("full_anime/{animeId}")
    Call<AnimeDigest> getAnimeDigest(@Path("animeId") String animeId);

    @Headers(ACCEPT_JSON_MIMETYPE)
    @GET("library_entries")
    Call<Feed> getAnimeLibraryEntries(@Query("user_id") String userId,
            @Query("status") String watchingStatusLibraryUpdateValue);

    @GET("users")
    Call<Feed> getFollowedUsers(@Query("followed_by") String username, @Query("page") Integer page);

    @GET("users")
    Call<Feed> getFollowingUsers(@Query("followers_of") String username,
            @Query("page") Integer page);

    @Headers(ACCEPT_JSON_MIMETYPE)
    @GET("franchises/{franchiseId}")
    Call<Franchise> getFranchise(@Path("franchiseId") String franchiseId);

    @Headers(ACCEPT_JSON_MIMETYPE)
    @GET("groups/{groupId}")
    Call<GroupDigest> getGroup(@Path("groupId") String groupId);

    @GET("group_members")
    Call<Feed> getGroupMembers(@Query("group_id") String groupId, @Query("page") Integer page);

    @GET("stories")
    Call<Feed> getGroupStories(@Query("group_id") String groupId, @Query("page") Integer page);

    @GET("full_manga/{mangaId}")
    Call<MangaDigest> getMangaDigest(@Path("mangaId") String mangaId);

    @Headers(ACCEPT_JSON_MIMETYPE)
    @GET("manga_library_entries")
    Call<Feed> getMangaLibraryEntries(@Query("user_id") String userId,
            @Query("status") String readingStatusLibraryUpdateValue);

    @GET("stories")
    Call<Feed> getNewsFeed(@Query("news_feed") boolean newsFeed, @Query("page") Integer page);

    @Headers(ACCEPT_JSON_MIMETYPE)
    @GET("notifications")
    Call<Feed> getNotifications(@Query("page") Integer page);

    @GET("sign-in")
    Call<ResponseBody> getSignInPage();

    @Headers(ACCEPT_JSON_MIMETYPE)
    @GET("stories/{storyId}")
    Call<Feed> getStory(@Path("storyId") String storyId);

    @Headers(ACCEPT_JSON_MIMETYPE)
    @GET("notifications/{notificationId}")
    Call<Feed> getStoryFromNotification(@Path("notificationId") String notificationId);

    @GET("substories")
    Call<Feed> getSubstories(@Query("story_id") String storyId, @Query("page") Integer page);

    @Headers(ACCEPT_JSON_MIMETYPE)
    @GET("users/{username}")
    Call<User> getUser(@Path("username") String username);

    @GET("user_infos/{username}")
    Call<UserDigest> getUserDigest(@Path("username") String username);

    @Headers(ACCEPT_JSON_MIMETYPE)
    @GET("groups")
    Call<Feed> getUserGroups(@Query("user_id") String userId, @Query("page") Integer page);

    @Headers(ACCEPT_JSON_MIMETYPE)
    @GET("reviews")
    Call<Feed> getUserReviews(@Query("user_id") String userId, @Query("page") Integer page);

    @GET("stories")
    Call<Feed> getUserStories(@Query("user_id") String username, @Query("page") Integer page);

    @POST("group_members")
    Call<Void> joinGroup(@Body JsonElement body);

    @DELETE("group_members/{groupId}")
    Call<Void> leaveGroup(@Path("groupId") String groupId);

    @PUT("quotes/{quoteId}")
    Call<Void> likeQuote(@Path("quoteId") String quoteId, @Body JsonElement body);

    @PUT("stories/{storyId}")
    Call<Void> likeStory(@Path("storyId") String storyId, @Body JsonElement body);

    @POST("substories")
    Call<Void> postComment(@Body JsonElement body);

    @POST("stories")
    Call<Void> postToFeed(@Body JsonElement body);

    @GET("search.json")
    Call<SearchBundle> search(@Query("scope") SearchScope searchScope,
            @Query("depth") SearchDepth depth, @Query("query") String query);

    @FormUrlEncoded
    @POST("sign-in")
    Call<Void> signIn(@Field("email") String username, @Field("password") String password);

    @POST("users/{userId}/follow")
    Call<Void> toggleFollowingOfUser(@Path("userId") String userId);

    @Headers(ACCEPT_JSON_MIMETYPE)
    @PUT("library_entries/{libraryEntryId}")
    Call<Void> updateAnimeLibraryEntry(@Path("libraryEntryId") String libraryEntryId,
            @Body JsonElement body);

    @Headers(ACCEPT_JSON_MIMETYPE)
    @PUT("manga_library_entries/{libraryEntryId}")
    Call<Void> updateMangaLibraryEntry(@Path("libraryEntryId") String libraryEntryId,
            @Body JsonElement body);

}
