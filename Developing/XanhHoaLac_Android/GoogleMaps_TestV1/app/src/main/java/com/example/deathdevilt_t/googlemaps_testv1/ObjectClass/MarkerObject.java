package com.example.deathdevilt_t.googlemaps_testv1.ObjectClass;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by DeathDevil.T_T on 05-May-17.
 */

public class MarkerObject {

    public MarkerObject() {
    }


    public String getTittle() {
        return tittle;
    }

    public void setTittle(String titrle) {
        this.tittle = titrle;
    }

    public LatLng getPosition() {
        return position;
    }

    public void setPosition(LatLng position) {
        this.position = position;
    }

    private LatLng position;
    private String tittle;

}
