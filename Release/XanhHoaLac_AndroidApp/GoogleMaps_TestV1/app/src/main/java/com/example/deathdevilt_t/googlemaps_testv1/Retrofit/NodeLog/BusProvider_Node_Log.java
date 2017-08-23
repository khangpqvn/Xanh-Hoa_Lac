package com.example.deathdevilt_t.googlemaps_testv1.Retrofit.NodeLog;

import com.squareup.otto.Bus;

/**
 * Created by DeathDevil.T_T on 31-May-17.
 */

public class BusProvider_Node_Log {
    private static final Bus BUS = new Bus();

    public static Bus getInstance(){
        return BUS;
    }

    public BusProvider_Node_Log(){}
}
