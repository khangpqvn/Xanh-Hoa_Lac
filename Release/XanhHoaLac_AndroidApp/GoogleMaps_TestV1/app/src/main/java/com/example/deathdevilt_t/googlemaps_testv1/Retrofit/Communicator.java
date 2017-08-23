package com.example.deathdevilt_t.googlemaps_testv1.Retrofit;

import android.content.Context;
import android.util.Log;

import com.example.deathdevilt_t.googlemaps_testv1.ObjectClass.UserObject;
import com.example.deathdevilt_t.googlemaps_testv1.Retrofit.RetrofitNode.Model.Node;
import com.squareup.otto.Produce;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.internal.JavaNetCookieJar;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by DeathDevil.T_T on 31-May-17.
 */

public class Communicator {
    private static  final String TAG = "Communicator_Node_Log_From_Server";
    private static final String SERVER_URL = "http://capstoneweb.herokuapp.com/";
    //https://caps-web-khanghn2.c9users.io/
    private UserObject userObject;
    private OkHttpClient httpClient;
    private Context ctx;
    public ArrayList<Node> nodeArrayList;

    public Communicator() {
    }

    public void loginPost(final String id_token, String status, String message){
        Log.d("fuck", id_token);

       CookieHandler cookieManager = new CookieManager();
       // CookieHandler.setDefault(cookieManager);

        //Here a logging interceptor is created
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);

        //The logging interceptor will be added to the http client
        httpClient = new OkHttpClient.Builder().addNetworkInterceptor(logging).cookieJar(new JavaNetCookieJar(cookieManager))
//                .connectTimeout(10, TimeUnit.SECONDS)
//                .writeTimeout(10, TimeUnit.SECONDS)
//                .readTimeout(30, TimeUnit.SECONDS)
                .build();



        //The Retrofit builder will have the client attached, in order to get connection logs
        Retrofit.Builder retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(SERVER_URL);
        final Interface service = retrofit.build().create(Interface.class);
        try {
            Call<ServerResponse> call = service.post(id_token, status, message);

            call.enqueue(new Callback<ServerResponse>() {
                @Override
                public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                    BusProvider.getInstance().post(new ServerEvent(response.body()));

                    ServerResponse serverResponse;
                    serverResponse = response.body();
                    Log.e("LOgFromServer", "Success");
                }

                @Override
                public void onFailure(Call<ServerResponse> call, Throwable t) {
                }
            });
        }catch (Exception e){
            Log.d("exception", e.toString());
        }
    }
    @Produce
    public ServerEvent produceServerEvent(ServerResponse serverResponse) {
        return new ServerEvent(serverResponse);
    }

    @Produce
    public ErrorEvent produceErrorEvent(int errorCode, String errorMsg) {
        return new ErrorEvent(errorCode, errorMsg);
    }

}
