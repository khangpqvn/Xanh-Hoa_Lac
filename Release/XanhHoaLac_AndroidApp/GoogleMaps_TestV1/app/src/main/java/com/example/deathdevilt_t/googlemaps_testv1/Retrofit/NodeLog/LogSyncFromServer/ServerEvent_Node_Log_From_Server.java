package com.example.deathdevilt_t.googlemaps_testv1.Retrofit.NodeLog.LogSyncFromServer;

import com.example.deathdevilt_t.googlemaps_testv1.ObjectClass.LogSyncFromServerModel.GetDataFromServer.LogSyncNode;

/**
 * Created by DeathDevil.T_T on 31-May-17.
 */

public class ServerEvent_Node_Log_From_Server {
    private LogSyncNode serverResponse;

    public ServerEvent_Node_Log_From_Server(LogSyncNode serverResponse) {
        this.serverResponse = serverResponse;
    }

    public LogSyncNode getServerResponse() {
        return serverResponse;
    }

    public void setServerResponse(LogSyncNode serverResponse) {
        this.serverResponse = serverResponse;
    }
}
