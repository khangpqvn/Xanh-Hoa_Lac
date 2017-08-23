package com.example.deathdevilt_t.googlemaps_testv1.ObjectClass.WeatherApi.model.Service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by chtnnnmtgkyp on 5/20/2017.
 */

public class ServiceFactory  {
    private Retrofit retrofit;

    private  static  ServiceFactory inst = new ServiceFactory();

    public static ServiceFactory getInst(){
        return inst;
    }

    private ServiceFactory() {
        retrofit = new Retrofit.Builder()
                .baseUrl(ApiUtils.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public <ServiceClass>ServiceClass createService(Class<ServiceClass> serviceClass){
        return retrofit.create(serviceClass);
    }
}
