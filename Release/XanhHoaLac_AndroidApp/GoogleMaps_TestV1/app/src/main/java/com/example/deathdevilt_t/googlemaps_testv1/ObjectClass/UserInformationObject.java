package com.example.deathdevilt_t.googlemaps_testv1.ObjectClass;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by DeathDevil.T_T on 19-Jun-17.
 */

public class UserInformationObject extends RealmObject {


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getUriImage() {
        return uriImage;
    }

    public void setUriImage(String uriImage) {
        this.uriImage = uriImage;
    }
    @PrimaryKey
    private String emailAddress;
    private String uriImage;
    private String role;
    private  String idToken;
    private String name;
    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }



    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


}
