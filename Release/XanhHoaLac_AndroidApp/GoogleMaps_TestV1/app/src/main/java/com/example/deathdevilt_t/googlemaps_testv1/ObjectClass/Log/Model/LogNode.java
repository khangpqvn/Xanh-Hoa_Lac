package com.example.deathdevilt_t.googlemaps_testv1.ObjectClass.Log.Model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by DeathDevil.T_T on 24-Jul-17.
 */

public class LogNode extends RealmObject {
    @PrimaryKey
    private int idLogNode;
    private String isSync;
    private String userName;
    private String targetNode;
    private long time;
    private String action;
    private String description;

    public int getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(int isOnline) {
        this.isOnline = isOnline;
    }

    private int isOnline;

    public int getIdLogNode() {
        return idLogNode;
    }

    public void setIdLogNode(int idLogNode) {
        this.idLogNode = idLogNode;
    }



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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



    public String getIsSync() {
        return isSync;
    }

    public void setIsSync(String isSync) {
        this.isSync = isSync;
    }


}
