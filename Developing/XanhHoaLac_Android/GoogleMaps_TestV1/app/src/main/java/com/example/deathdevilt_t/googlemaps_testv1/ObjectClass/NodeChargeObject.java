package com.example.deathdevilt_t.googlemaps_testv1.ObjectClass;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by DeathDevil.T_T on 08-Aug-17.
 */

public class NodeChargeObject extends RealmObject {
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String phoneNumber;
    @PrimaryKey
    private int id;
}
