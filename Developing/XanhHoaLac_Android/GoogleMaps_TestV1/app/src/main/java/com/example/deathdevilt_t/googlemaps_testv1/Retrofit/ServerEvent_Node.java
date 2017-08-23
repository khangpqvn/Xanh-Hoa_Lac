package com.example.deathdevilt_t.googlemaps_testv1.Retrofit;

import com.example.deathdevilt_t.googlemaps_testv1.Retrofit.RetrofitNode.Model.NodeInformation;

/**
 * Created by DeathDevil.T_T on 31-May-17.
 */

public class ServerEvent_Node {
    private NodeInformation serverResponse;

    public ServerEvent_Node(NodeInformation serverResponse) {
        this.serverResponse = serverResponse;
    }

    public NodeInformation getServerResponse() {
        return serverResponse;
    }

    public void setServerResponse(NodeInformation serverResponse) {
        this.serverResponse = serverResponse;
    }
}
