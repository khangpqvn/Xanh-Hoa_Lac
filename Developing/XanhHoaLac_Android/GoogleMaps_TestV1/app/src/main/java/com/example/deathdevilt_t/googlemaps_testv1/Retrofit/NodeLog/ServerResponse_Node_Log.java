package com.example.deathdevilt_t.googlemaps_testv1.Retrofit.NodeLog;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by DeathDevil.T_T on 31-May-17.
 */


    public class ServerResponse_Node_Log implements Serializable {



    @SerializedName("status")
    @Expose
    private Boolean status;
//    @SerializedName("message")
//    @Expose
//    private String message;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
//    public ServerResponse_Node_Log_From_Server() {
//    }
}

