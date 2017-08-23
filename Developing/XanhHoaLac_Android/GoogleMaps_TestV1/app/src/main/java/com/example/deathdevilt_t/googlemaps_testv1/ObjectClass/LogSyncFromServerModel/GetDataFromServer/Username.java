
package com.example.deathdevilt_t.googlemaps_testv1.ObjectClass.LogSyncFromServerModel.GetDataFromServer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Username {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("__v")
    @Expose
    private Integer v;
    @SerializedName("isBlock")
    @Expose
    private Boolean isBlock;
    @SerializedName("role")
    @Expose
    private String role;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public Boolean getIsBlock() {
        return isBlock;
    }

    public void setIsBlock(Boolean isBlock) {
        this.isBlock = isBlock;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
