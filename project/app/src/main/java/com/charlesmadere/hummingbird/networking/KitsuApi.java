package com.charlesmadere.hummingbird.networking;

import com.charlesmadere.hummingbird.models.ActionGroup;
import com.charlesmadere.hummingbird.models.ArrayResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface KitsuApi {

    @GET("feeds/global/global")
    Call<ArrayResponse<ActionGroup>> getGlobalFeed();

}
