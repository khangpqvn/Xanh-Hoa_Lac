package com.example.deathdevilt_t.googlemaps_testv1.Retrofit.RetrofitNode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by DeathDevil.T_T on 31-May-17.
 */


    public class ServerResponse_Node implements Serializable {


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
    }

    @SerializedName("_id")
    @Expose
    private String _id;
    @SerializedName("phoneNumber")
    @Expose
    private String phoneNumber;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lng")
    @Expose
    private String lng;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("isDelete")
    @Expose
    private Boolean isDelete;


    public ServerResponse_Node() {
    }
}

