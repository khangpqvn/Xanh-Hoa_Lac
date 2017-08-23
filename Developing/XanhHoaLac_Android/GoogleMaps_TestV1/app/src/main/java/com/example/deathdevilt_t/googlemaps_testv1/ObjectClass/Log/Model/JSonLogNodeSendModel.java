package com.example.deathdevilt_t.googlemaps_testv1.ObjectClass.Log.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by DeathDevil.T_T on 26-Jul-17.
 */

public class JSonLogNodeSendModel {

    @SerializedName("username")
    @Expose
    private String userName;

    @SerializedName("targetNode")
    @Expose
    private String targetNode;

    @SerializedName("time")
    @Expose
    private long time;

    @SerializedName("action")
    @Expose
    private String action;

    @SerializedName("description")
    @Expose
    private String description;

    public JSonLogNodeSendModel(String userName, String targetNode, long time, String action, String description,int idLogNode,String isSync) {
        this.userName = userName;
        this.targetNode = targetNode;
        this.time = time;
        this.action = action;
        this.description = description;
        this.idLogNode = idLogNode;
        this.isSync = isSync;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTargetNode() {
        return targetNode;
    }

    public void setTargetNode(String targetNode) {
        this.targetNode = targetNode;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public int getIdLogNode() {
        return idLogNode;
    }

    public void setIdLogNode(int idLogNode) {
        this.idLogNode = idLogNode;
    }
    public String getIsSync() {
        return isSync;
    }

    public void setIsSync(String isSync) {
        this.isSync = isSync;
    }

    private int idLogNode;
    private String isSync;
}
