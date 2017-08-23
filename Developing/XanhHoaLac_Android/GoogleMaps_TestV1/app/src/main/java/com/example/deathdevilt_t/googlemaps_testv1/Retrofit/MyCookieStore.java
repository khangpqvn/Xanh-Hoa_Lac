package com.example.deathdevilt_t.googlemaps_testv1.Retrofit;

import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URI;
import java.util.List;

/**
 * Created by DeathDevil.T_T on 06-Jun-17.
 */

public class MyCookieStore implements CookieStore {
    @Override
    public void add(URI uri, HttpCookie cookie) {

    }

    @Override
    public List<HttpCookie> get(URI uri) {
        return null;
    }

    @Override
    public List<HttpCookie> getCookies() {
        return null;
    }

    @Override
    public List<URI> getURIs() {
        return null;
    }

    @Override
    public boolean remove(URI uri, HttpCookie cookie) {
        return false;
    }

    @Override
    public boolean removeAll() {
        return false;
    }
}
