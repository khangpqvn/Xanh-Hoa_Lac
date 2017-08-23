package com.example.deathdevilt_t.googlemaps_testv1.Retrofit.NodeLog;

import com.example.deathdevilt_t.googlemaps_testv1.ObjectClass.Log.Model.JSonLogSendModel;
import com.example.deathdevilt_t.googlemaps_testv1.ObjectClass.LogSyncFromServerModel.GetDataFromServer.LogSyncNode;
import com.example.deathdevilt_t.googlemaps_testv1.ObjectClass.LogSyncFromServerModel.SendData.SendDataModel;
import com.example.deathdevilt_t.googlemaps_testv1.Retrofit.RetrofitNode.Model.NodeInformation;

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

public interface Interface_Node_Log {
    //This method is used for "POST"
    @FormUrlEncoded

    @POST("/api/node/sync")
    Call<NodeInformation> post(
            @Field("id_token") String id_token,
            @Field("version") String version
    );

    @POST("/api/log/sync")
    @Headers ("Content-Type: application/json")
    Call<ServerResponse_Node_Log> callSync(@Body JSonLogSendModel data);

    @POST("/api/log/mobileViewLog")
    @Headers ("Content-Type: application/json")
    Call<LogSyncNode> callSyncLogFromServer(@Body SendDataModel data);

    //This method is used for "GET"
    @GET("/api.php")
    Call<ServerResponse_Node_Log> get(
            @Query("method") String method,
            @Query("username") String username,
            @Query("password") String password
    );

}
