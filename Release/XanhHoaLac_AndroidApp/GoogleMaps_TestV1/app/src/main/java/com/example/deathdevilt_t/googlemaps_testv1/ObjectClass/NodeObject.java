package com.example.deathdevilt_t.googlemaps_testv1.ObjectClass;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by DeathDevil.T_T on 09-May-17.
 */

public class NodeObject extends RealmObject{
   /* public LatLng getPosition() {
        return position;
    }

   public void setPosition(LatLng position) {
        this.position = position;
    }*/

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

   // private LatLng position;

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

    @PrimaryKey
    private String  id;
    private String lat;
    private String lng;
    private String description;
    private String phone;
    private boolean isDelete;
    private String version;
}
