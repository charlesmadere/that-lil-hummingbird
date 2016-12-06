package com.charlesmadere.hummingbird.networking;

import com.charlesmadere.hummingbird.models.ActionGroup;
import com.charlesmadere.hummingbird.models.AnimeV3;
import com.charlesmadere.hummingbird.models.ArrayResponse;
import com.charlesmadere.hummingbird.models.UserV3;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface KitsuApi {

    @GET("anime?filter%5Bslug%5D=new-game&include=genres")
    Call<ArrayResponse<AnimeV3>> getAnime(@Path("anime") String anime);

    @GET("feeds/global/global?include=media,actor,unit,subject,subject.user,subject.target_user,subject.media,subject.follower,subject.followed")
    Call<ArrayResponse<ActionGroup>> getGlobalFeed();

    @GET("users?filter%5Bname%5D={username}&include=userRoles.role%2CpinnedPost")
    Call<ArrayResponse<UserV3>> getUsers(@Path("username") String username);

}
