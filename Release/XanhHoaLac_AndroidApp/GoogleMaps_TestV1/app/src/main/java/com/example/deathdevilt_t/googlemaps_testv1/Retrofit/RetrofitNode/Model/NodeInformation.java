package com.example.deathdevilt_t.googlemaps_testv1.Retrofit.RetrofitNode.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by DeathDevil.T_T on 05-Jun-17.
 */

public class NodeInformation implements Serializable{
    public NodeInformation() {
    }

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("currentVersion")
    @Expose
    private String currentVersion;
    @SerializedName("nodes")
    @Expose
    private List<Node> nodes = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getCurrentVersion() {
        return currentVersion;
    }

    public void setCurrentVersion(String currentVersion) {
        this.currentVersion = currentVersion;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }
}
