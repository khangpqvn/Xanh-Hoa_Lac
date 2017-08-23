package com.example.deathdevilt_t.googlemaps_testv1.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.deathdevilt_t.googlemaps_testv1.DBContext.DBContext;
import com.example.deathdevilt_t.googlemaps_testv1.FragmentSetting.DFragment;
import com.example.deathdevilt_t.googlemaps_testv1.FragmentSetting.OnABCClick;
import com.example.deathdevilt_t.googlemaps_testv1.ObjectClass.MarkerObject;
import com.example.deathdevilt_t.googlemaps_testv1.ObjectClass.NodeObject;
import com.example.deathdevilt_t.googlemaps_testv1.R;
import com.example.deathdevilt_t.googlemaps_testv1.Retrofit.ErrorEvent;
import com.example.deathdevilt_t.googlemaps_testv1.Retrofit.RetrofitNode.BusProvider_Node;
import com.example.deathdevilt_t.googlemaps_testv1.Retrofit.RetrofitNode.Communicator_Node;
import com.example.deathdevilt_t.googlemaps_testv1.Retrofit.RetrofitNode.Model.Node;
import com.example.deathdevilt_t.googlemaps_testv1.Retrofit.RetrofitNode.ServerEvent_Node;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;


public class MapFragment extends Fragment implements OnMapReadyCallback, OnABCClick {

    //    private MapView mapView;
    private Context context;
    private View view;
    private GoogleMap mMap;
    private LatLng specialPoint;
    private List<MarkerObject> markerName;
    private MarkerObject markerObject;
    private FragmentTransaction fragmentTransaction;
    private SupportMapFragment supportMapFragment;
    private Communicator_Node communicator;
    private String id_token;
    private SigninActivity signinActivity;
    private DBContext dbContext;
    private List<NodeObject> listNode;
    private DFragment newFragment;

    public MapFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     */
    // TODO: Rename and change types and number of parameters
    public static MapFragment newInstance(String param1, String param2) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        try {
            view = inflater.inflate(R.layout.fragment_map, container, false);
            init(view);
        } catch (Exception e) {
        }

        return view;
    }

    private void init(View view) {
        context = view.getContext();
        supportMapFragment = SupportMapFragment.newInstance();
        supportMapFragment.getMapAsync(this);
        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.google_map, supportMapFragment);
        fragmentTransaction.commit();
        dbContext = DBContext.getInst();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        markerName = new ArrayList<>();
        markerObject = new MarkerObject();
        usePost("1");
        initGoogleMap();
        initNode();
        mMap = googleMap;

        mMap.moveCamera(CameraUpdateFactory.newLatLng(specialPoint));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(17));
        for (MarkerObject mk : markerName) {
            MarkerOptions userMarker = new MarkerOptions().position(mk.getPosition()).title(mk.getTittle()).icon(BitmapDescriptorFactory.fromResource(R.drawable.water_pump_icon2));
            mMap.addMarker(userMarker);

        }

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(final Marker arg0) {
                for (MarkerObject mk : markerName
                        ) {
                    if (arg0.getTitle().equals("Near1!")) { // if marker source is clicked
                        Toast.makeText(context, arg0.getTitle(), Toast.LENGTH_SHORT).show();// display toast
                    } else if (arg0.getTitle().equals("Near2!")) {
                        Toast.makeText(context, arg0.getTitle(), Toast.LENGTH_SHORT).show();// display toast
                    } else if (arg0.getTitle().equals(mk.getTittle())) {
                        Toast.makeText(context, "Thiết bị : + " + arg0.getTitle(), Toast.LENGTH_SHORT).show();// display toast
                        showTheDialog(arg0.getTitle());

                    }
                }
                return true;
            }

        });
    }

    private void initNode() {

        if (dbContext.getAllNode() != null) {
            listNode = dbContext.getAllNode();
        }
        for (NodeObject node : listNode) {
            MarkerObject markerObject = new MarkerObject();
            LatLng pointOnce = new LatLng(Double.parseDouble(node.getLat().toString()), Double.parseDouble(node.getLng().toString()));
            markerObject.setPosition(pointOnce);
            markerObject.setTittle(node.getPhone());
            markerName.add(markerObject);
        }
    }

    private void initGoogleMap() {
        specialPoint = new LatLng(21.013342, 105.525930);
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            BusProvider_Node.getInstance().register(this);

            NaviMapActivity naviMapActivity = new NaviMapActivity();
        } catch (Exception e) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Bạn vừa clear RAM vui lòng đăng nhập lại app " + e.getMessage())
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }

        // initNode();

    }

    @Override
    public void onPause() {
        super.onPause();
        BusProvider_Node.getInstance().unregister(this);

    }

    private void usePost(String version) {
        id_token = signinActivity.IdToken;
        communicator = new Communicator_Node();
        communicator.loginPostNode(id_token, version);

    }

    @Subscribe
    public void onServerEventNode(ServerEvent_Node serverEvent_node) {
        markerObject = new MarkerObject();
        if (dbContext.getAllNode() != null) {
            listNode = dbContext.getAllNode();
        }
        if (listNode.size() == 0) {
            try {
                for (Node node : serverEvent_node.getServerResponse().getNodes()
                        ) {
                    MarkerObject markerObject = new MarkerObject();
                    LatLng pointOnce = new LatLng(Double.parseDouble(node.getLat().toString()), Double.parseDouble(node.getLng().toString()));
                    markerObject.setPosition(pointOnce);
                    markerObject.setTittle(node.getPhoneNumber());
                    markerName.add(markerObject);
                    NodeObject nodeObject = new NodeObject();
                    nodeObject.setPhone(node.getPhoneNumber());
                    nodeObject.setVersion(serverEvent_node.getServerResponse().getCurrentVersion().toString());
                    nodeObject.setId(node.getId());
                    nodeObject.setDescription(node.getDescription());
                    nodeObject.setDelete(node.getIsDelete());
                    nodeObject.setLat(node.getLat());
                    nodeObject.setLng(node.getLng());
                    dbContext.addNodeObject(nodeObject);
                }
                for (final MarkerObject mk : markerName
                        ) {
                    MarkerOptions userMarker = new MarkerOptions().position(mk.getPosition()).title(mk.getTittle()).icon(BitmapDescriptorFactory.fromResource(R.drawable.water_pump_icon2));
                    mMap.addMarker(userMarker);

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @Subscribe
    public void onErrorEvent(ErrorEvent errorEvent) {
        Toast.makeText(getContext(), "" + errorEvent.getErrorMsg(), Toast.LENGTH_SHORT).show();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Kiểm tra lại kết lối mạng!")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }

    private void showTheDialog(String phoneNumber) {
        newFragment = DFragment.newInstance(this, phoneNumber);
        newFragment.show(getFragmentManager(), "dialog");
    }

    @Override
    public void OnABCClick() {
        newFragment.dismiss();
    }
}
