package com.example.deathdevilt_t.googlemaps_testv1.ObjectClass.WeatherApi.model;

/**
 * Created by chtnnnmtgkyp on 5/20/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sys {

    @SerializedName("pod")
    @Expose
    private String pod;

    public String getPod() {
        return pod;
    }

    public void setPod(String pod) {
        this.pod = pod;
    }

}
