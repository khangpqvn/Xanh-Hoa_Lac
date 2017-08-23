package com.example.deathdevilt_t.googlemaps_testv1.ObjectClass;

/**
 * Created by DeathDevil.T_T on 29-May-17.
 */

public class UserObject  {


    public UserObject() {
    }

    public void setIsLogin(Boolean isLogin) {
        this.isLogin = isLogin;
    }

    public Boolean getIsLogin() {
        return isLogin;
    }

    private Boolean isLogin;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
