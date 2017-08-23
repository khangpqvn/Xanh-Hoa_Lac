package com.example.deathdevilt_t.googlemaps_testv1.Retrofit.RetrofitNode;

import com.example.deathdevilt_t.googlemaps_testv1.ObjectClass.Log.Model.JSonLogNodeSendModel;
import com.example.deathdevilt_t.googlemaps_testv1.Retrofit.RetrofitNode.Model.NodeInformation;
import com.example.deathdevilt_t.googlemaps_testv1.Retrofit.ServerResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by DeathDevil.T_T on 31-May-17.
 */

public interface Interface_node {
    //This method is used for "POST"
    @FormUrlEncoded

    @POST("/api/node/sync")
    Call<NodeInformation> post(
            @Field("id_token") String id_token,
            @Field("version") String version
    );

    @POST("/api/log/sync")
    @Headers ("Content-Type: application/json")
    Call<ServerResponse> callSync (@Body JSonLogNodeSendModel data);

    //This method is used for "GET"
    @GET("/api.php")
    Call<ServerResponse_Node> get(
            @Query("method") String method,
            @Query("username") String username,
            @Query("password") String password
    );

}
