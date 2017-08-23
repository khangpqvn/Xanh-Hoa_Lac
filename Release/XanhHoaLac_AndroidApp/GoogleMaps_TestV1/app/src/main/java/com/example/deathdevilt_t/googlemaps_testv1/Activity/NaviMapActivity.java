package com.example.deathdevilt_t.googlemaps_testv1.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.deathdevilt_t.googlemaps_testv1.DBContext.DBContext;
import com.example.deathdevilt_t.googlemaps_testv1.ObjectClass.Log.Model.JSonLogNodeSendModel;
import com.example.deathdevilt_t.googlemaps_testv1.ObjectClass.Log.Model.JSonLogSendModel;
import com.example.deathdevilt_t.googlemaps_testv1.ObjectClass.Log.Model.LogNode;
import com.example.deathdevilt_t.googlemaps_testv1.ObjectClass.MarkerObject;
import com.example.deathdevilt_t.googlemaps_testv1.ObjectClass.NodeObject;
import com.example.deathdevilt_t.googlemaps_testv1.ObjectClass.NodeSessionObject;
import com.example.deathdevilt_t.googlemaps_testv1.ObjectClass.UserInformationObject;
import com.example.deathdevilt_t.googlemaps_testv1.R;
import com.example.deathdevilt_t.googlemaps_testv1.Retrofit.NodeLog.BusProvider_Node_Log;
import com.example.deathdevilt_t.googlemaps_testv1.Retrofit.NodeLog.Communicator_Node_Log;
import com.example.deathdevilt_t.googlemaps_testv1.Retrofit.NodeLog.ErrorEvent_Node_Log;
import com.example.deathdevilt_t.googlemaps_testv1.Retrofit.NodeLog.ServerEvent_Node_Log;
import com.example.deathdevilt_t.googlemaps_testv1.Retrofit.RetrofitNode.BusProvider_Node;
import com.example.deathdevilt_t.googlemaps_testv1.Retrofit.RetrofitNode.Communicator_Node;
import com.example.deathdevilt_t.googlemaps_testv1.Retrofit.RetrofitNode.ErrorEvent_Node;
import com.example.deathdevilt_t.googlemaps_testv1.Retrofit.RetrofitNode.Model.Node;
import com.example.deathdevilt_t.googlemaps_testv1.Retrofit.RetrofitNode.ServerEvent_Node;
import com.example.deathdevilt_t.googlemaps_testv1.fragment.FragmentDialogHistory;
import com.example.deathdevilt_t.googlemaps_testv1.fragment.FragmentDialogInformation;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class NaviMapActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private MarkerObject markerObject;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    public static Context contextNavi;
    private String UriImage;
    private Communicator_Node communicator;
    private Communicator_Node_Log communicatorNodeLog;
    private DBContext dbContext;
    private Boolean exit = false;
    private Boolean signInByAccount = false;
    private boolean onClickSyncSuccess = false;
    private Boolean checkStatusServer = false;
    private Boolean checkSuccess;
    private Boolean checkSyncNode = true;
    private ProgressDialog dialog;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            //usePost("1");
            // ATTENTION: This was auto-generated to implement the App Indexing API.
            // See https://g.co/AppIndexing/AndroidStudio for more information.
            client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        } catch (Exception e) {

        }
    }

    public void onResume() {
        super.onResume();
        // init();
        try {
            initRealm();
            initGoogleSignOut();
            setContentView(R.layout.activity_navi_map);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();
            init();
            contextNavi = getBaseContext();
            BusProvider_Node.getInstance().register(this);
            BusProvider_Node_Log.getInstance().register(this);
            this.registerReceiver(broadcastReceiver, new IntentFilter("android.provider.Telephony.SMS_RECEIVED"));
        } catch (Exception e) {

        }

    }


    @Override
    public void onPause() {
        super.onPause();
        BusProvider_Node.getInstance().unregister(this);
        BusProvider_Node_Log.getInstance().unregister(this);
        this.unregisterReceiver(broadcastReceiver);
    }

    private void initGoogleSignOut() {
//        mGoogleApiClient = new GoogleApiClient.Builder(this)
//                .addConnectionCallbacks(this)
//                .addOnConnectionFailedListener(this).addApi(Plus.API)
//                .addScope(Plus.SCOPE_PLUS_LOGIN).build();
    }

    private void initRealm() {
        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
        dbContext = DBContext.getInst();
    }

    private void init() {
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        transaction.add(R.id.frame_layout, new MapFragment());
        transaction.commit();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View hView = navigationView.getHeaderView(0);
        ImageView imageView = (ImageView) hView.findViewById(R.id.imageView_Icon);
        ImageView ImgView_Weather = (ImageView) hView.findViewById(R.id.ImgView_Weather);
        TextView tView_Name = (TextView) hView.findViewById(R.id.tView_Name);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            dbContext = DBContext.getInst();
            UriImage = bundle.getString("UriImage");
            String Name = bundle.getString("Name");
            String EmailAddress = bundle.getString("EmailAddress");
            String idToken = bundle.getString("idToken");
            String role = bundle.getString("role");
            UserInformationObject userInformationObject = new UserInformationObject();

            userInformationObject.setName(Name);
            userInformationObject.setEmailAddress(EmailAddress);
            userInformationObject.setUriImage(UriImage);
            userInformationObject.setIdToken(idToken);
            userInformationObject.setRole(role);
            dbContext.addUserInformation(userInformationObject);
            TextView tView_emailAddress = (TextView) hView.findViewById(R.id.tView_emailAddress);
            Picasso.with(this).load(UriImage).placeholder(R.drawable.icon_avatar_default).resize(150, 150).error(R.drawable.icon_avatar_default).into(imageView);
            tView_Name.setText(Name);
            tView_emailAddress.setText(EmailAddress);
            signInByAccount = true;
            Bitmap imageAva = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
            imageDownload(this, UriImage);

        } else if (bundle == null) {
            dbContext = DBContext.getInst();
            List<UserInformationObject> listInfor = dbContext.getAllUserInformation();
            for (UserInformationObject userInformationObject : listInfor
                    ) {

                TextView tView_emailAddress = (TextView) hView.findViewById(R.id.tView_emailAddress);
                if (isNetworkConnected()) {
                    Picasso.with(this).load(userInformationObject.getUriImage()).placeholder(R.drawable.icon_avatar_default).resize(150, 150).error(R.drawable.icon_avatar_default).into(imageView);
                } else {
                    imageView.setImageResource(R.drawable.icon_avatar_default);
                }
                tView_Name.setText(userInformationObject.getName());
                tView_emailAddress.setText(userInformationObject.getEmailAddress());
            }
            signInByAccount = true;
        }
        if (onClickSyncSuccess == true) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Đã đồng bộ xong, các vị trí Node đã được cập nhập")
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


    }


    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            dbContext = DBContext.getInst();

            String phoneNumber = "";
            final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
            SmsMessage[] messages = null;
            Log.d("fuckBroadCastNavi", intent.getAction());
            if (intent.getAction() == SMS_RECEIVED) {
                Bundle bundle = intent.getExtras();
                if (bundle != null) {
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    messages = new SmsMessage[pdus.length];
                    for (int i = 0; i < pdus.length; i++) {
                        messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                        if (messages.length > -1) {
                            String phoneNumberProcess = messages[0].getOriginatingAddress().toString();
                            NodeSessionObject nodeSessionObject = dbContext.getNodeSessionByPhoneNumber(phoneNumberProcess);
                            List<Integer> listIdNodes;
                            if (nodeSessionObject != null) {
                                int idNodeLog = nodeSessionObject.getIdLogNode();
                                phoneNumber = nodeSessionObject.getPhoneNumber();
                                if (messages[0].getMessageBody().substring(0, 2).toString().equalsIgnoreCase("01") && nodeSessionObject.getPhoneNumber().equalsIgnoreCase(messages[0].getOriginatingAddress())) {
                                    //send Log to server
                                    if (isNetworkConnected() == true) {
                                        dbContext = DBContext.getInst();
                                        String idToken = "";
                                        if (dbContext.getAllUserInformation() != null) {
                                            for (UserInformationObject uio : dbContext.getAllUserInformation()
                                                    ) {
                                                idToken = uio.getIdToken();
                                            }
                                        }
                                        List<JSonLogNodeSendModel> jSonLogNodeSendModelList = new ArrayList<>();
                                        List<LogNode> logNodeList = dbContext.getAllLogNodeByNotSync(phoneNumber.substring(1));
                                        if (logNodeList.size() > 0) {
                                            listIdNodes = new ArrayList<>();
                                            for (LogNode ln : logNodeList
                                                    ) {
                                                if (ln.getIdLogNode() == idNodeLog) {
                                                    JSonLogNodeSendModel jSonLogNodeSendModel = new JSonLogNodeSendModel(ln.getUserName(), ln.getTargetNode(), ln.getTime(), ln.getAction(), ln.getDescription(), ln.getIdLogNode(), ln.getIsSync());
                                                    jSonLogNodeSendModelList.add(jSonLogNodeSendModel);
                                                    listIdNodes.add(ln.getIdLogNode());
                                                }

                                            }
                                            JSonLogSendModel jSonLogSendModel = new JSonLogSendModel(idToken, jSonLogNodeSendModelList);
                                            sendLog(jSonLogSendModel);
                                            dbContext.updateSyncOkLogNodeByIdNode(idNodeLog, phoneNumber);
                                        }
                                        dbContext.DeleteAllLogNodeSyncOk();
                                        dbContext.deleteNodeSessionByPhoneNumberAndIdLog(messages[0].getOriginatingAddress().toString(), idNodeLog);
                                    } else {
                                        dbContext.updateIsOnlineLogNodeByIdNode(idNodeLog, phoneNumber);
                                    }


                                } else if (messages[0].getMessageBody().equalsIgnoreCase("Ses00") && nodeSessionObject.getPhoneNumber().equalsIgnoreCase(messages[0].getOriginatingAddress())) {

                                }
                            }

                        }
                    }
                }
            }
        }
    };

    public static void imageDownload(Context ctx, String url) {
        Log.d("fuck1234", url);
        Picasso.with(ctx)
                .load("http://blog.concretesolutions.com.br/wp-content/uploads/2015/04/Android1.png")
                .into(getTarget(url));
    }

    //target to save
    private static Target getTarget(final String url) {
        Log.d("fuck1234", url);
        Target target = new Target() {

            @Override
            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                new Thread(new Runnable() {

                    @Override
                    public void run() {

                        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + url);
                        try {
                            Log.d("fuckImg", bitmap.toString());
                            Log.d("fuckImg", Environment.getExternalStorageDirectory().getAbsolutePath());
                            file.createNewFile();
                            FileOutputStream ostream = new FileOutputStream(file);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, ostream);
                            ostream.flush();
                            ostream.close();
                        } catch (IOException e) {
                            Log.e("IOException", e.getLocalizedMessage());
                        }
                    }
                }).start();

            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };
        return target;
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Bạn có muốn thoát ứng dụng này không ?")
                .setCancelable(false)
                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navi_map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_reloadNode) {
            getNewNodeFromServer();
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_logout) {
            signOut();
        } else if (id == R.id.nav_infor) {
            showInformationDialog();
        } else if (id == R.id.nav_weather) {
            Intent intent = new Intent(NaviMapActivity.this, WeatherActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_sync) {
            getNewNodeFromServer();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void getNewNodeFromServer() {
        if (isNetworkConnected() == true) {

            dbContext = DBContext.getInst();
            signInByAccount = false;
            String currentVersion = "";
            String idTokenCurrent = "";
            if (dbContext.getAllNode() != null) {
                List<NodeObject> listNode = dbContext.getAllNode();
                for (NodeObject no : listNode
                        ) {
                    currentVersion = no.getVersion();
                }
                List<UserInformationObject> listInfor = dbContext.getAllUserInformation();
                for (UserInformationObject userInformationObject : listInfor
                        ) {
                    idTokenCurrent = userInformationObject.getIdToken();

                }
                Boolean checkAuthor = usePost(idTokenCurrent, currentVersion);
            }

        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
    }

    private void showInformationDialog() {

        new FragmentDialogInformation().show(getFragmentManager(), "Fagment information");

    }

    private void showHistoryDialog() {

        new FragmentDialogHistory().show(getFragmentManager(), "Fragment history");

    }


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("NaviMap Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());

    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
    // Send post request

    private void signOut() {
        dbContext.DeleteNodeObject();
        dbContext.DeleteUserInformation();
        dbContext.DeleteAllNodeSession();
        int sz = dbContext.getAllNode().size();
        Log.d("fuckSizeDB", "" + sz);
        Intent intent = new Intent(NaviMapActivity.this, SigninActivity.class);
        startActivity(intent);
        finish();
    }

    private Boolean usePost(String id_token, String version) {
        communicator = new Communicator_Node();
        checkSuccess = communicator.loginPostNode(id_token, version);
        dialog = ProgressDialog.show(this, "Đang đồng bộ",
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


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                });
            }
        }).start();


        return checkSuccess;
    }

    private void sendLog(JSonLogSendModel jSonLogSendModel) {
        communicatorNodeLog = new Communicator_Node_Log();
        communicatorNodeLog.NodeLog(jSonLogSendModel);
    }


    @Subscribe
    public void onServerEventNode(final ServerEvent_Node serverEvent_node) {

        markerObject = new MarkerObject();
        if (serverEvent_node != null) {
            checkStatusServer = true;
            if (serverEvent_node.getServerResponse().getStatus() == true && signInByAccount == false) {
                checkSyncNode = false;
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Đã có bản cập nhật các Node, click 'Đồng Ý' để tiến hành cập nhật!")
                        .setCancelable(false)
                        .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dbContext = DBContext.getInst();
                                dbContext.DeleteNodeObject();
                                for (Node node : serverEvent_node.getServerResponse().getNodes()
                                        ) {
                                    NodeObject nodeObject = new NodeObject();
                                    nodeObject.setPhone(node.getPhoneNumber());
                                    // nodeObject.setPosition(pointOnce);
                                    nodeObject.setVersion(serverEvent_node.getServerResponse().getCurrentVersion().toString());
                                    nodeObject.setId(node.getId());
                                    nodeObject.setDescription(node.getDescription());
                                    nodeObject.setDelete(node.getIsDelete());
                                    nodeObject.setLat(node.getLat());
                                    nodeObject.setLng(node.getLng());
                                    dbContext.addNodeObject(nodeObject);
                                }
                                dialog.dismiss();
                                Intent intent = new Intent(NaviMapActivity.this, NaviMapActivity.class);
                                startActivity(intent);
                            }
                        }).setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();

            } else if (serverEvent_node.getServerResponse().getStatus() == false) {
                dialog.dismiss();
                checkSyncNode = false;
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Đã đồng bộ xong ,không có thay đổi về các vị trí đặt Node")
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
        }
    }

    @Subscribe
    public void onErrorEvent(ErrorEvent_Node errorEvent) {

    }

    @Subscribe
    public void onServerEventNode(ServerEvent_Node_Log serverEvent_node) {
        if (serverEvent_node.getServerResponse() != null) {

            dbContext = DBContext.getInst();
        }
    }

    @Subscribe
    public void onErrorEvent(ErrorEvent_Node_Log errorEvent) {


    }

}
