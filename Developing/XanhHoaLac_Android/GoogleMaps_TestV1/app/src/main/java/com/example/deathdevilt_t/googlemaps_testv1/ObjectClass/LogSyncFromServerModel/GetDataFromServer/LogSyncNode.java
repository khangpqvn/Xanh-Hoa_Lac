
package com.example.deathdevilt_t.googlemaps_testv1.ObjectClass.LogSyncFromServerModel.GetDataFromServer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LogSyncNode {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;
    @SerializedName("viewmore")
    @Expose
    private Boolean viewmore;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public Boolean getViewmore() {
        return viewmore;
    }

    public void setViewmore(Boolean viewmore) {
        this.viewmore = viewmore;
    }

}
