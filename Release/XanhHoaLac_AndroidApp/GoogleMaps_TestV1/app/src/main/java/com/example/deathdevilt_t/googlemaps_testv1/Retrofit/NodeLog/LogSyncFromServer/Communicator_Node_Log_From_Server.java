package com.example.deathdevilt_t.googlemaps_testv1.Retrofit.NodeLog.LogSyncFromServer;

import com.example.deathdevilt_t.googlemaps_testv1.ObjectClass.Log.Model.JSonLogNodeSendModel;
import com.example.deathdevilt_t.googlemaps_testv1.ObjectClass.Log.Model.JSonLogSendModel;
import com.example.deathdevilt_t.googlemaps_testv1.ObjectClass.LogSyncFromServerModel.GetDataFromServer.LogSyncNode;
import com.example.deathdevilt_t.googlemaps_testv1.ObjectClass.LogSyncFromServerModel.SendData.SendDataModel;
import com.example.deathdevilt_t.googlemaps_testv1.ObjectClass.UserObject;
import com.example.deathdevilt_t.googlemaps_testv1.Retrofit.NodeLog.ErrorEvent_Node_Log;
import com.example.deathdevilt_t.googlemaps_testv1.Retrofit.NodeLog.ServerEvent_Node_Log;
import com.example.deathdevilt_t.googlemaps_testv1.Retrofit.NodeLog.ServerResponse_Node_Log;
import com.squareup.otto.Produce;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by DeathDevil.T_T on 31-May-17.
 */

public class Communicator_Node_Log_From_Server {
    private static  final String TAG = "Communicator_Node_Log_From_Server";
    private static final String SERVER_URL = "http://capstoneweb.herokuapp.com/";
    //https://caps-web-khanghn2.c9users.io/
    private UserObject userObject;
    private JSonLogNodeSendModel jSonLogNodeSendModel;
    private JSonLogSendModel jSonLogSendModel;
    public Boolean isSuccess = false;

    public Communicator_Node_Log_From_Server() {
    }

    public Boolean NodeLog(JSonLogSendModel jSonLogSendModel){
        //Here a logging interceptor is created
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
       logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        //The logging interceptor will be added to the http client
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        //The Retrofit builder will have the client attached, in order to get connection logs
        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(SERVER_URL)
                .build(); Interface_Node_Log_From_Server service = retrofit.create(Interface_Node_Log_From_Server.class);

        Call<ServerResponse_Node_Log> call = service.callSync(jSonLogSendModel);
            try {
                call.enqueue(new Callback<ServerResponse_Node_Log>() {
                    @Override
                    public void onResponse(Call<ServerResponse_Node_Log> call, Response<ServerResponse_Node_Log> response) {
                        if (response.isSuccessful()) {
                            isSuccess = true;
                            if (response.body().getStatus() != null) {
                            }
                        }else {
                        }

                    }

                    @Override
                    public void onFailure(Call<ServerResponse_Node_Log> call, Throwable t) {
                    }
                });
            }catch (Exception e){
            }
            return isSuccess;
    }
    public Boolean NodeLogFromServer(SendDataModel jSonLogSendModel){
        //Here a logging interceptor is created
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        //The logging interceptor will be added to the http client
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        //The Retrofit builder will have the client attached, in order to get connection logs
        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(SERVER_URL)
                .build(); Interface_Node_Log_From_Server service = retrofit.create(Interface_Node_Log_From_Server.class);

        Call<LogSyncNode> call = service.callSyncLogFromServer(jSonLogSendModel);
        try {
            call.enqueue(new Callback<LogSyncNode>() {
                @Override
                public void onResponse(Call<LogSyncNode> call, Response<LogSyncNode> response) {
                    if (response.isSuccessful()) {
                        isSuccess = true;
                        if (response.body().getStatus() != null) {
                            BusProvider_Node_Log_From_Server.getInstance().post(new ServerEvent_Node_Log_From_Server(response.body()));

                            LogSyncNode serverResponseNodeLog = response.body();
                        }
                    }else {
                    }

                }

                @Override
                public void onFailure(Call<LogSyncNode> call, Throwable t) {

                }

            });
        }catch (Exception e){
        }
        return isSuccess;
    }
    @Produce
    public ServerEvent_Node_Log produceServerEvent(ServerResponse_Node_Log serverResponse) {
        return new ServerEvent_Node_Log(serverResponse);
    }

    @Produce
    public ErrorEvent_Node_Log produceErrorEvent(int errorCode, String errorMsg) {
        return new ErrorEvent_Node_Log(errorCode, errorMsg);
    }
    @Produce
    public ServerEvent_Node_Log_From_Server produceServerEventLogNodeFromServer(LogSyncNode serverResponse) {
        return new ServerEvent_Node_Log_From_Server(serverResponse);
    }

    @Produce
    public ErrorEvent_Node_Log_From_Server produceErrorEventLogNodeFromServer(int errorCode, String errorMsg) {
        return new ErrorEvent_Node_Log_From_Server(errorCode, errorMsg);
    }



}
