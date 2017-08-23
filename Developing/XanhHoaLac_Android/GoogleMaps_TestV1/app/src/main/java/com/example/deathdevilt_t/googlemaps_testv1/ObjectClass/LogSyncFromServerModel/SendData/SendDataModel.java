package com.example.deathdevilt_t.googlemaps_testv1.ObjectClass.LogSyncFromServerModel.SendData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by DeathDevil.T_T on 01-Aug-17.
 */

public class SendDataModel {
    public SendDataModel(String idToken, String limit, String target) {
        this.idToken = idToken;
        this.limit = limit;
        this.target = target;
    }

    @SerializedName("id_token")
    @Expose
    private String idToken;
    @SerializedName("limit")
    @Expose
    private String limit;
    @SerializedName("target")
    @Expose
    private String target;

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
