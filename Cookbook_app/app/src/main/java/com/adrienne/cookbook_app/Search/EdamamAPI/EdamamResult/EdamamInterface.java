package com.adrienne.cookbook_app.Search.EdamamAPI.EdamamResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Admin on 5/20/17.
 */

public interface EdamamInterface {

    @GET("search")
    Call<EdamamAPI> getHits(@Query("q") String query,
                            @Query("app_id") String app_id,
                            @Query("app_key") String key,
                            @Query("to") String amount);
}
