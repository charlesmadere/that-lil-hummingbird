package com.charlesmadere.hummingbird.networking;

import com.charlesmadere.hummingbird.models.ActionGroup;
import com.charlesmadere.hummingbird.models.ArrayResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface KitsuApi {

    @GET("feeds/global/global?include=media,actor,unit,subject,subject.user,subject.target_user,subject.media,subject.follower,subject.followed")
    Call<ArrayResponse<ActionGroup>> getGlobalFeed();

}
