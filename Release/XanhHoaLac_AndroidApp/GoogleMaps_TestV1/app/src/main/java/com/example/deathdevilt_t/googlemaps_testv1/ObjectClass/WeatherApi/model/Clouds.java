package com.example.deathdevilt_t.googlemaps_testv1.ObjectClass.WeatherApi.model;

/**
 * Created by chtnnnmtgkyp on 5/20/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Clouds {

    @SerializedName("all")
    @Expose
    private Integer all;

    public Integer getAll() {
        return all;
    }

    public void setAll(Integer all) {
        this.all = all;
    }

}
