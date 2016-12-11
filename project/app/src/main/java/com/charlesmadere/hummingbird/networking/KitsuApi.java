package com.charlesmadere.hummingbird.networking;

import com.charlesmadere.hummingbird.models.ActionGroup;
import com.charlesmadere.hummingbird.models.AnimeV3;
import com.charlesmadere.hummingbird.models.ArrayResponse;
import com.charlesmadere.hummingbird.models.Follow;
import com.charlesmadere.hummingbird.models.UserV3;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface KitsuApi {

    @GET("anime?filter%5Bslug%5D={slug}&include=genres")
    Call<ArrayResponse<AnimeV3>> getAnime(@Path("slug") String slug);

    @GET("follows?filter%5Bfollowed%5D={userId}&include=follower&sort=-created_at")
    Call<ArrayResponse<Follow>> getFollowers(@Path("userId") String userId);

    @GET("feeds/global/global?media,actor,unit,subject,target,target.user.user_roles.role,target.target_user.user_roles.role,target.spoiled_unit,target.media,subject.user.user_roles.role,subject.target_user.user_roles.role,subject.spoiled_unit,subject.media,subject.media,subject.follower,subject.followed,subject.library_entry&page[limit]=10")
    Call<ArrayResponse<ActionGroup>> getGlobalFeed();

    @GET("users?filter%5Bname%5D={username}&include=userRoles.role%2CpinnedPost")
    Call<ArrayResponse<UserV3>> getUsers(@Path("username") String username);

}
