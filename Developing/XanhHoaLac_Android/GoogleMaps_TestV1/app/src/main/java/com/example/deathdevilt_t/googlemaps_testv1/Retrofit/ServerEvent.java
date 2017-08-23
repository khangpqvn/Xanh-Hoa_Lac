package com.example.deathdevilt_t.googlemaps_testv1.Retrofit;

import com.example.deathdevilt_t.googlemaps_testv1.Retrofit.RetrofitNode.Model.NodeInformation;

/**
 * Created by DeathDevil.T_T on 31-May-17.
 */

public class ServerEvent {
    private ServerResponse serverResponse;
    private NodeInformation serverResponse2;

    public ServerEvent(ServerResponse serverResponse) {
        this.serverResponse = serverResponse;
    }

    public ServerResponse getServerResponse() {
        return serverResponse;
    }

    public void setServerResponse(ServerResponse serverResponse) {
        this.serverResponse = serverResponse;
    }
//
//    public NodeInformation getServerRespons2() {
//        return serverResponse2;
//    }
//
//    public void setServerResponse2(NodeInformation serverResponse2) {
//        this.serverResponse2 = serverResponse2;
//    }
}
