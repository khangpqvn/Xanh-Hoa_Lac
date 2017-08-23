package com.example.deathdevilt_t.googlemaps_testv1.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.deathdevilt_t.googlemaps_testv1.ObjectClass.MarkerObject;
import com.example.deathdevilt_t.googlemaps_testv1.R;
import com.example.deathdevilt_t.googlemaps_testv1.fragment.fragmentMapsButton;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LatLng near1,near2,specialPoint,nearTest;
    private ArrayList<MarkerObject> markerName;
    private MarkerObject markerObject,markerObject1;
    private FrameLayout mFrameLayout;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    TranslateAnimation animation1;
    TranslateAnimation animation2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    protected void onResume() {
        initFragment();
        super.onResume();

    }

    private void initGoogleMap(){
        near1 = new LatLng(21.013377, 105.526684);
        near2 = new LatLng(21.013362, 105.526676);
        nearTest = new LatLng(21.013390, 105.526680);
        specialPoint = new LatLng(21.013342, 105.525930);
        String tittleTest = "NodeTEst";
        String Node1="Node 1";
        String Node2="Node 2";
        markerObject = new MarkerObject();
        markerObject.setPosition(specialPoint);
        markerObject.setTittle(tittleTest);
        markerName = new ArrayList<MarkerObject>();
        markerName.add(markerObject);

        markerObject1 = new MarkerObject();
        markerObject1.setPosition(near1);
        markerObject1.setTittle(Node1);
        markerName.add(markerObject1);
    }
     /*
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        initGoogleMap();
        mMap = googleMap;
        for (final MarkerObject mk:markerName
                ) {
            MarkerOptions userMarker = new MarkerOptions().position(mk.getPosition()).title(mk.getTittle()).icon(BitmapDescriptorFactory.fromResource(R.drawable.water_pump_icon2));
            mMap.addMarker(userMarker);

        }
        mMap.moveCamera(CameraUpdateFactory.newLatLng(specialPoint));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(17));
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener()
        {
            @Override
            public boolean onMarkerClick(Marker arg0) {
                for (MarkerObject mk:markerName
                     ) {
                    if (arg0.getTitle().equals("Near1!")) { // if marker source is clicked
                        Toast.makeText(MapsActivity.this, arg0.getTitle(), Toast.LENGTH_SHORT).show();// display toast
                    } else if (arg0.getTitle().equals("Near2!")) {
                        Toast.makeText(MapsActivity.this, arg0.getTitle(), Toast.LENGTH_SHORT).show();// display toast
                    } else if (arg0.getTitle().equals(mk.getTittle())) {
                        Toast.makeText(MapsActivity.this, "test " + arg0.getTitle(), Toast.LENGTH_SHORT).show();// display toast
                        appear(arg0.getTitle());
                    }

                }

                return true;
            }

        });
    }


    /*fragment button maps control author:TrisBM */
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        mFrameLayout.setY(mFrameLayout.getHeight());

        animation1 = new TranslateAnimation(0.0f, 0.0f,0.0f,-mFrameLayout.getHeight());
        animation1.setDuration(500);

        animation2 = new TranslateAnimation(0.0f,0.0f,0.0f,mFrameLayout.getHeight());
        animation2.setDuration(500);

    }

    public void hide(){
        animation2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mFrameLayout.layout(mFrameLayout.getLeft(),mFrameLayout.getTop()+mFrameLayout.getHeight(),mFrameLayout.getRight(),mFrameLayout.getTop()+mFrameLayout.getHeight()+mFrameLayout.getMeasuredHeight());
                removeFragment();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mFrameLayout.startAnimation(animation2);
    }

    public void appear(String tittleMarked){
        Bundle bundle = new Bundle();
        bundle.putString("edttext", tittleMarked+"");
        fragmentMapsButton fragobj = new fragmentMapsButton();
        fragobj.setArguments(bundle);

        callFragment(fragobj);

//        Log.d("xxx",mFrameLayout.getHeight()+"5");
        animation1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mFrameLayout.layout(mFrameLayout.getLeft(),mFrameLayout.getTop()-mFrameLayout.getHeight(),mFrameLayout.getRight(),mFrameLayout.getTop()-mFrameLayout.getHeight()+mFrameLayout.getMeasuredHeight());
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mFrameLayout.startAnimation(animation1);
    }
    public void initFragment(){

        mFrameLayout = (FrameLayout) findViewById(R.id.fmContent);

    }

    public void showText(String id,String topImageText, String bottomImageText) {

    }
    public void callFragment(Fragment fragment) {
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        //Khi được goi, fragment truyền vào sẽ thay thế vào vị trí FrameLayout trong Activity chính
        transaction.replace(R.id.fmContent, fragment);
        transaction.commit();
    }

    public void removeFragment(){
        getSupportFragmentManager().beginTransaction().
                remove(getSupportFragmentManager().findFragmentById(R.id.fmContent)).commit();
    }

}
