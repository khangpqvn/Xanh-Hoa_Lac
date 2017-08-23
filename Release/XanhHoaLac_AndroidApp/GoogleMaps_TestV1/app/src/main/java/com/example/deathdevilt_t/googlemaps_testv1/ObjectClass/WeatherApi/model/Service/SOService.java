package com.example.deathdevilt_t.googlemaps_testv1.ObjectClass.WeatherApi.model.Service;

/**
 * Created by chtnnnmtgkyp on 5/20/2017.
 */

import com.example.deathdevilt_t.googlemaps_testv1.ObjectClass.WeatherApi.model.SOAnswersResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface SOService {

    @Headers({ApiUtils.HEADERS})
    @GET(ApiUtils.GET_WEATHER)
    Call<SOAnswersResponse> getAnswers();
}
