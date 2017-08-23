package com.example.deathdevilt_t.googlemaps_testv1.Retrofit.NodeLog;

/**
 * Created by DeathDevil.T_T on 31-May-17.
 */

public class ServerEvent_Node_Log {
    private ServerResponse_Node_Log serverResponse;

    public ServerEvent_Node_Log(ServerResponse_Node_Log serverResponse) {
        this.serverResponse = serverResponse;
    }

    public ServerResponse_Node_Log getServerResponse() {
        return serverResponse;
    }

    public void setServerResponse(ServerResponse_Node_Log serverResponse) {
        this.serverResponse = serverResponse;
    }
}
