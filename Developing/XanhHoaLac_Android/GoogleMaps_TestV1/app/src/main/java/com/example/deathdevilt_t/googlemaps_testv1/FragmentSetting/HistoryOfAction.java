package com.example.deathdevilt_t.googlemaps_testv1.FragmentSetting;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.deathdevilt_t.googlemaps_testv1.Activity.SigninActivity;
import com.example.deathdevilt_t.googlemaps_testv1.DBContext.DBContext;
import com.example.deathdevilt_t.googlemaps_testv1.ObjectClass.Log.Model.JSonLogNodeSendModel;
import com.example.deathdevilt_t.googlemaps_testv1.ObjectClass.Log.Model.JSonLogSendModel;
import com.example.deathdevilt_t.googlemaps_testv1.ObjectClass.Log.Model.LogNode;
import com.example.deathdevilt_t.googlemaps_testv1.ObjectClass.LogSyncFromServerModel.GetDataFromServer.Datum;
import com.example.deathdevilt_t.googlemaps_testv1.ObjectClass.LogSyncFromServerModel.SendData.SendDataModel;
import com.example.deathdevilt_t.googlemaps_testv1.ObjectClass.NodeObject;
import com.example.deathdevilt_t.googlemaps_testv1.ObjectClass.NodeSessionObject;
import com.example.deathdevilt_t.googlemaps_testv1.ObjectClass.UserInformationObject;
import com.example.deathdevilt_t.googlemaps_testv1.R;
import com.example.deathdevilt_t.googlemaps_testv1.Retrofit.NodeLog.BusProvider_Node_Log;
import com.example.deathdevilt_t.googlemaps_testv1.Retrofit.NodeLog.Communicator_Node_Log;
import com.example.deathdevilt_t.googlemaps_testv1.Retrofit.NodeLog.ErrorEvent_Node_Log;
import com.example.deathdevilt_t.googlemaps_testv1.Retrofit.NodeLog.LogSyncFromServer.BusProvider_Node_Log_From_Server;
import com.example.deathdevilt_t.googlemaps_testv1.Retrofit.NodeLog.LogSyncFromServer.Communicator_Node_Log_From_Server;
import com.example.deathdevilt_t.googlemaps_testv1.Retrofit.NodeLog.LogSyncFromServer.ErrorEvent_Node_Log_From_Server;
import com.example.deathdevilt_t.googlemaps_testv1.Retrofit.NodeLog.LogSyncFromServer.ServerEvent_Node_Log_From_Server;
import com.example.deathdevilt_t.googlemaps_testv1.Retrofit.NodeLog.ServerEvent_Node_Log;
import com.squareup.otto.Subscribe;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by chtnnnmtgkyp on 6/26/2017.
 */

public class HistoryOfAction extends Fragment implements View.OnClickListener {

    private Button cancel;
    private String phoneNumber;
    private Context context;
    private Communicator_Node_Log communicatorNodeLog;
    private Communicator_Node_Log_From_Server communicatorNodeLogFromServer;
    private DBContext dbContext;
    private List<Integer> listIdNodes;
    private Boolean checkStatus = false;
    private Boolean checkStatusServer = false;
    private int limit = 5;
    private ProgressDialog dialog;
    private List<Datum> datalist;
    private RecyclerView recyclerView;
    private HistoryAdapter adapter;
    private List<String> userName;

    private List<String> description;
    private List<String> time;
    private int countLog = 0;

    private OnABCClick onABCClick;


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public HistoryOfAction(String phoneNumber, OnABCClick onABCClick) {
        this.phoneNumber = phoneNumber;
        this.onABCClick = onABCClick;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.history_action, container, false);
        context = this.getContext();
        init(view);

        return view;
    }


    public void onResume() {
        super.onResume();

        initRealm();
        BusProvider_Node_Log.getInstance().register(this);
        BusProvider_Node_Log_From_Server.getInstance().register(this);

    }

    @Override
    public void onPause() {
        super.onPause();
        BusProvider_Node_Log.getInstance().unregister(this);
        BusProvider_Node_Log_From_Server.getInstance().unregister(this);

    }

    private void initRealm() {
        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
        dbContext = DBContext.getInst();
    }

    private void init(View view) {
        cancel = (Button) view.findViewById(R.id.btn_cancel);
        cancel.setOnClickListener(this);
        Button btn_LogSync = (Button) view.findViewById(R.id.btn_LogSync);
        Button btn_LoadLogFromServer = (Button) view.findViewById(R.id.btn_LoadLogFromServer);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        btn_LogSync.setOnClickListener(this);
        btn_LoadLogFromServer.setOnClickListener(this);
        userName = new ArrayList<>();
        description = new ArrayList<>();
        time = new ArrayList<>();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_LogSync:
                if (isNetworkConnected() == true) {
                    dbContext = DBContext.getInst();
                    NodeSessionObject nodeSessionObject = dbContext.getNodeSessionByPhoneNumber("+" + phoneNumber);

                    String idToken = "";
                    if (dbContext.getAllUserInformation() != null) {
                        for (UserInformationObject uio : dbContext.getAllUserInformation()
                                ) {
                            idToken = uio.getIdToken();
                        }
                    }
                    List<JSonLogNodeSendModel> jSonLogNodeSendModelList = new ArrayList<>();
                    List<LogNode> logNodeList = dbContext.getAllLogNodeByNotSync(phoneNumber);
                    if (logNodeList.size() > 0) {
                        int i = 0;
                        listIdNodes = new ArrayList<>();
                        for (LogNode ln : logNodeList
                                ) {
                            if (ln.getIsOnline() == 0) {
                                JSonLogNodeSendModel jSonLogNodeSendModel = new JSonLogNodeSendModel(ln.getUserName(), ln.getTargetNode(), ln.getTime(), ln.getAction(), ln.getDescription(), ln.getIdLogNode(), ln.getIsSync());
                                jSonLogNodeSendModelList.add(jSonLogNodeSendModel);
                                listIdNodes.add(ln.getIdLogNode());

                                dbContext.updateSyncOkLogNodeByIdNode(ln.getIdLogNode(), phoneNumber);
                            }
                        }
                        JSonLogSendModel jSonLogSendModel = new JSonLogSendModel(idToken, jSonLogNodeSendModelList);
                        sendLog(jSonLogSendModel);
                        dbContext.DeleteAllLogNodeSyncOk();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("Log Đã được đồng bộ hết!")
                                .setCancelable(false)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                        checkStatusServer = true;
                    }
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Bạn cần truy cập mạng để sử dụng tính năng này!")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }

                break;
            case R.id.btn_LoadLogFromServer:
                if (isNetworkConnected() == true) {
                    userName.clear();
                    description.clear();
                    time.clear();
                    dbContext = DBContext.getInst();
                    String idToken = "";
                    String target = "";

                    if (dbContext.getAllUserInformation() != null) {
                        for (UserInformationObject uio : dbContext.getAllUserInformation()
                                ) {
                            idToken = uio.getIdToken();
                        }
                    }
                    NodeObject nodeObject = new NodeObject();

                    if (dbContext.getNodeObjectByPhoneNumber(phoneNumber) != null) {
                        nodeObject = dbContext.getNodeObjectByPhoneNumber(phoneNumber);
                        target = nodeObject.getId();
                    }
                    SendDataModel sendDataModel = new SendDataModel(idToken, "" + limit, target);
                    getLogFromServer(sendDataModel);

                    limit = limit + 5;
                } else {
                    checkStatus = true;
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Bạn cần truy cập mạng để sử dụng tính năng này!")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }

                break;

            case R.id.btn_cancel:
                onABCClick.OnABCClick();
                break;
        }


    }

    private boolean isNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    private void sendLog(JSonLogSendModel jSonLogSendModel) {
        communicatorNodeLog = new Communicator_Node_Log();
        checkStatus = communicatorNodeLog.NodeLog(jSonLogSendModel);
        dialog = ProgressDialog.show(getActivity(), "Đang đồng bộ",
                "Xin vui lòng đợi ....", true);

        new Thread(new Runnable() {
            @Override
            public void run() {
                // do the thing that takes a long time
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                });
            }
        }).start();

    }

    private void getLogFromServer(SendDataModel model) {
        communicatorNodeLog = new Communicator_Node_Log();
        communicatorNodeLog.NodeLogFromServer(model);
        dialog = ProgressDialog.show(getActivity(), "Đang đồng bộ",
                "Xin vui lòng đợi ....", true);

        new Thread(new Runnable() {
            @Override
            public void run() {
                // do the thing that takes a long time
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                });
            }
        }).start();
    }

    private void signOut() {
        dbContext.DeleteNodeObject();
        dbContext.DeleteUserInformation();
        int sz = dbContext.getAllNode().size();
        Intent intent = new Intent(getActivity(), SigninActivity.class);
        startActivity(intent);
    }

    @Subscribe
    public void onServerEventNode(ServerEvent_Node_Log serverEvent_node) {
        if (serverEvent_node.getServerResponse() != null) {

            dbContext = DBContext.getInst();
            if (serverEvent_node.getServerResponse().getStatus() == true) {
                for (int k : listIdNodes
                        ) {
                    dbContext.updateSyncOkLogNodeByIdNode(k, phoneNumber);
                }
                checkStatusServer = true;
            } else {

            }

        }
    }

    @Subscribe
    public void onErrorEvent(ErrorEvent_Node_Log errorEvent) {
    }


    @Subscribe
    public void onServerEventNodeLogFromServer(ServerEvent_Node_Log_From_Server serverEvent_node) throws ParseException {
        checkStatus = true;
        if (serverEvent_node.getServerResponse() != null) {

            dbContext = DBContext.getInst();
            if (serverEvent_node.getServerResponse().getStatus() == true) {
                datalist = serverEvent_node.getServerResponse().getData();
                for (Datum data : datalist
                        ) {
                    userName.add(data.getUsername().getUsername());
                    description.add(data.getDescription());
                    SimpleDateFormat formatted = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                    formatted.setTimeZone(TimeZone.getTimeZone("GMT"));
                    String timeAfterParse = String.valueOf(formatted.parse(data.getTime()));
                    time.add(timeAfterParse);
                    countLog++;

                }

                adapter = new HistoryAdapter(userName, description, time);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
            } else if (serverEvent_node.getServerResponse().getStatus().equals(false)) {
                userName.clear();
                description.clear();
                time.clear();
                adapter = new HistoryAdapter(userName, description, time);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
            } else {
                userName.clear();
                description.clear();
                time.clear();
                adapter = new HistoryAdapter(userName, description, time);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
            }
        }
    }


    @Subscribe
    public void onErrorEventLoadLogNode(ErrorEvent_Node_Log_From_Server errorEvent) {
        adapter = new HistoryAdapter(null, null, null);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}
