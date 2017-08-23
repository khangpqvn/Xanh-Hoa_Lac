package com.example.deathdevilt_t.googlemaps_testv1.Retrofit.NodeLog;

/**
 * Created by DeathDevil.T_T on 31-May-17.
 */

public class ErrorEvent_Node_Log {
    private int errorCode;
    private String errorMsg;

    public ErrorEvent_Node_Log(int errorCode, String errorMsg) {
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
