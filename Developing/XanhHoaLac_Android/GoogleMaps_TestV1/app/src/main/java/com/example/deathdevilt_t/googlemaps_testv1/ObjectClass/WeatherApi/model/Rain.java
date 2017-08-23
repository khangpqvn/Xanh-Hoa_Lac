package com.example.deathdevilt_t.googlemaps_testv1.ObjectClass.WeatherApi.model;

/**
 * Created by chtnnnmtgkyp on 5/20/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rain {

    @SerializedName("3h")
    @Expose
    private Double _3h;

    public Double get3h() {
        return _3h;
    }

    public void set3h(Double _3h) {
        this._3h = _3h;
    }

}
