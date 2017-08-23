package com.example.deathdevilt_t.googlemaps_testv1.Retrofit;

import com.example.deathdevilt_t.googlemaps_testv1.Retrofit.RetrofitNode.Model.NodeInformation;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by DeathDevil.T_T on 31-May-17.
 */

public interface Interface {
    //This method is used for "POST"
    @FormUrlEncoded
    @POST("/api/auth/idtoken")
    Call<ServerResponse> post(
            @Field("id_token") String id_token,
            @Field("status") String status,
             @Field("message") String message

    );
    @FormUrlEncoded
    @POST("/api/node/sync")
    Call<NodeInformation> postNode(
            @Field("version") String version,
            @Field("id_token") String id_token
    );

    //This method is used for "GET"
    @GET("/api.php")
    Call<ServerResponse> get(
            @Query("method") String method,
            @Query("username") String username,
            @Query("password") String password
    );

}
