package com.example.deathdevilt_t.googlemaps_testv1.ObjectClass.Log.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by DeathDevil.T_T on 26-Jul-17.
 */

public class JSonLogSendModel{
    @SerializedName("id_token")
    @Expose
    private String idToken;

    @SerializedName("logs")
    @Expose
    private List<JSonLogNodeSendModel> jSonLogNodeSendModelList;

    public JSonLogSendModel(String idToken, List<JSonLogNodeSendModel> jSonLogNodeSendModelList) {
        this.idToken = idToken;
        this.jSonLogNodeSendModelList = jSonLogNodeSendModelList;
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public List<JSonLogNodeSendModel> getjSonLogNodeSendModelList() {
        return jSonLogNodeSendModelList;
    }

    public void setjSonLogNodeSendModelList(List<JSonLogNodeSendModel> jSonLogNodeSendModelList) {
        this.jSonLogNodeSendModelList = jSonLogNodeSendModelList;
    }
}
