package com.example.deathdevilt_t.googlemaps_testv1.Retrofit.RetrofitNode;

/**
 * Created by DeathDevil.T_T on 31-May-17.
 */

public class ErrorEvent_Node {
    private int errorCode;
    private String errorMsg;

    public ErrorEvent_Node(int errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
