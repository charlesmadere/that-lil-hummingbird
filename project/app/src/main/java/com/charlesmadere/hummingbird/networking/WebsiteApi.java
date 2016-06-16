package com.charlesmadere.hummingbird.networking;

import com.charlesmadere.hummingbird.models.AppNews;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface WebsiteApi {

    @GET("app_news.json")
    Call<ArrayList<AppNews>> getAppNews();

}
