package com.example.deathdevilt_t.googlemaps_testv1.Retrofit;

import com.squareup.otto.Bus;

/**
 * Created by DeathDevil.T_T on 31-May-17.
 */

public class BusProvider {
    private static final Bus BUS = new Bus();
    private static final Bus BUS2 = new Bus();

    public static Bus getInstance(){
        return BUS;
    }    public static Bus getInstance2(){
        return BUS2;
    }

    public BusProvider(){}
}
