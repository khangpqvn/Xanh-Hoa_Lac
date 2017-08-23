package com.example.deathdevilt_t.googlemaps_testv1.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.deathdevilt_t.googlemaps_testv1.DBContext.DBContext;
import com.example.deathdevilt_t.googlemaps_testv1.ObjectClass.Log.Model.JSonLogNodeSendModel;
import com.example.deathdevilt_t.googlemaps_testv1.ObjectClass.Log.Model.JSonLogSendModel;
import com.example.deathdevilt_t.googlemaps_testv1.ObjectClass.Log.Model.LogNode;
import com.example.deathdevilt_t.googlemaps_testv1.ObjectClass.MarkerObject;
import com.example.deathdevilt_t.googlemaps_testv1.ObjectClass.NodeObject;
import com.example.deathdevilt_t.googlemaps_testv1.ObjectClass.UserInformationObject;
import com.example.deathdevilt_t.googlemaps_testv1.R;
import com.example.deathdevilt_t.googlemaps_testv1.Retrofit.BusProvider;
import com.example.deathdevilt_t.googlemaps_testv1.Retrofit.Communicator;
import com.example.deathdevilt_t.googlemaps_testv1.Retrofit.ErrorEvent;
import com.example.deathdevilt_t.googlemaps_testv1.Retrofit.NodeLog.BusProvider_Node_Log;
import com.example.deathdevilt_t.googlemaps_testv1.Retrofit.NodeLog.Communicator_Node_Log;
import com.example.deathdevilt_t.googlemaps_testv1.Retrofit.RetrofitNode.BusProvider_Node;
import com.example.deathdevilt_t.googlemaps_testv1.Retrofit.RetrofitNode.Communicator_Node;
import com.example.deathdevilt_t.googlemaps_testv1.Retrofit.RetrofitNode.ErrorEvent_Node;
import com.example.deathdevilt_t.googlemaps_testv1.Retrofit.RetrofitNode.Model.Node;
import com.example.deathdevilt_t.googlemaps_testv1.Retrofit.RetrofitNode.ServerEvent_Node;
import com.example.deathdevilt_t.googlemaps_testv1.Retrofit.ServerEvent;
import com.example.deathdevilt_t.googlemaps_testv1.fragment.FragmentDialogAccessError;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DeathDevil.T_T on 15-May-17.
 */

public class SigninActivity extends Activity implements View.OnClickListener {
    private GoogleApiClient mGoogleApiClient;
    private GoogleSignInAccount acct;
    private SignInButton btnSignIn;
    private List<NodeObject> listNode;
    private static final int RC_SIGN_IN = 9001;
    private static final String TAG = "SignInActivity";
    public static String email, UriImage;
    public static String IdToken = "";
    private int checkKey = 0;
    private Communicator communicator;
    private Boolean check_Status;
    private DBContext dbContext;
    private Communicator_Node communicatorNode;
    private Communicator_Node_Log communicatorNodeLog;
    private Boolean checkSuccess;
    private MarkerObject markerObject;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_google_account);
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();
        mGoogleApiClient = new GoogleApiClient.Builder(SigninActivity.this).addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions).build();
    }

    private void AddNodeTest() {
        dbContext = DBContext.getInst();


        listNode = new ArrayList<NodeObject>();
        NodeObject nodeObject = new NodeObject();
        if (dbContext.getAllNode() != null) {
            listNode = dbContext.getAllNode();
        }
    }

    private void init() {
        btnSignIn = (SignInButton) findViewById(R.id.btnSignIn);
        btnSignIn.setOnClickListener(this);

    }

    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.btnSignIn:

                    NodeObject nodeObject = new NodeObject();
                    if (listNode.size() == 0 && isNetworkConnected() == true) { //check xem da co node nao chua

                        signIn();
                    } else if (listNode.size() != 0 && isNetworkConnected() == false) {
                        Intent intent = new Intent(SigninActivity.this, NaviMapActivity.class);
                        startActivity(intent);
                    } else if (listNode.size() != 0 && isNetworkConnected() == true) {
                        signOutWithDeleteInformation();
                        signIn();

                    } else if (listNode.size() == 0 && isNetworkConnected() == false) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setMessage("Bạn cần bật mạng cho phiên đăng nhập này")
                                .setCancelable(false)
                                .setPositiveButton("Đăng nhập lại", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                    } else {
                    }

                    break;

                default:
                    break;
            }
        } catch (Exception e) {
            Log.d("fuckExeption", e.getMessage());
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    private void showAccessError() {

        new FragmentDialogAccessError().show(getFragmentManager(), "Fragment Access Error");

    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);

        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            acct = result.getSignInAccount();
            UriImage = acct.getPhotoUrl().toString();

            email = acct.getEmail();
            IdToken = acct.getIdToken();
            dialog = ProgressDialog.show(this, "Đang kết nối", "Vui lòng chờ...", true);

            if (!IdToken.equals("")) {
                Log.d("email: ", acct.getIdToken());
                usePost(acct.getIdToken());
                // usePost(IdToken);

                checkKey = 1;
            } else {
                Log.d("email: ", acct.getEmail());

            }
            syncNode(IdToken, "1");
            dbContext = DBContext.getInst();
            String idToken = "";
            if (dbContext.getAllUserInformation() != null) {
                for (UserInformationObject uio : dbContext.getAllUserInformation()
                        ) {
                    idToken = uio.getIdToken();
                }
            }
            List<JSonLogNodeSendModel> jSonLogNodeSendModelList = new ArrayList<>();
            List<LogNode> logNodeList = dbContext.getAllLogNode();

            for (LogNode ln : logNodeList
                    ) {
                JSonLogNodeSendModel jSonLogNodeSendModel = new JSonLogNodeSendModel(ln.getUserName(), ln.getTargetNode(), ln.getTime(), ln.getAction(), ln.getDescription(), ln.getIdLogNode(), ln.getIsSync());
                jSonLogNodeSendModelList.add(jSonLogNodeSendModel);
            }
            JSonLogSendModel jSonLogSendModel = new JSonLogSendModel(idToken, jSonLogNodeSendModelList);
            sendLog(jSonLogSendModel);
        } else {
            // Signed out, show unauthenticated UI.
            updateUI(false);
        }
    }

    private void updateUI(boolean signedIn) {
        if (signedIn) {
            findViewById(R.id.btnSignIn).setVisibility(View.GONE);
        } else {
            findViewById(R.id.btnSignIn).setVisibility(View.VISIBLE);
        }
    }

    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // [START_EXCLUDE]
                        // updateUI(false);
                        // [END_EXCLUDE]
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 111);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 111);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS}, 111);
        }
        BusProvider.getInstance().register(this);
        BusProvider_Node.getInstance().register(this);
        BusProvider_Node_Log.getInstance().register(this);
        AddNodeTest();
        init();
    }

    @Override
    public void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
        BusProvider_Node.getInstance().unregister(this);
        BusProvider_Node_Log.getInstance().unregister(this);
    }

    private void usePost(String id_token) {

        Log.d("fuck", id_token);
        communicator = new Communicator();
        communicator.loginPost(id_token, "", "");


    }

    private Boolean syncNode(String id_token, String version) {
        communicatorNode = new Communicator_Node();
        checkSuccess = communicatorNode.loginPostNode(id_token, version);

        return checkSuccess;
    }

    private void sendLog(JSonLogSendModel jSonLogSendModel) {
        communicatorNodeLog = new Communicator_Node_Log();
        communicatorNodeLog.NodeLog(jSonLogSendModel);

    }

    @Subscribe
    public void onServerEvent(ServerEvent serverEvent) {
        try {
            Toast.makeText(this, "" + serverEvent.getServerResponse().getMessage(), Toast.LENGTH_SHORT).show();
            Log.d("fuck123", serverEvent.getServerResponse().getStatus().toString());
            check_Status = serverEvent.getServerResponse().getStatus();


            if (check_Status == true) {
                dialog.cancel();
                Intent intent = new Intent(SigninActivity.this, NaviMapActivity.class);
                Bundle bundleUriImage = new Bundle();
                bundleUriImage.putString("UriImage", UriImage);
                bundleUriImage.putString("Name", acct.getDisplayName().toString());
                bundleUriImage.putString("EmailAddress", acct.getEmail().toString());
                bundleUriImage.putString("idToken", acct.getIdToken());
                bundleUriImage.putString("role", serverEvent.getServerResponse().getRole());
                intent.putExtras(bundleUriImage);
                signOut();
                startActivity(intent);
                finish();

            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Bạn không có quyền đăng nhập")
                        .setCancelable(false)
                        .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                signOut();
                                Intent intent = new Intent(SigninActivity.this, SigninActivity.class);
                                startActivity(intent);
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

            }

        } catch (Exception e) {
            // showAccessError();
        }


    }

    private void signOutWithDeleteInformation() {
        dbContext.DeleteNodeObject();
        dbContext.DeleteUserInformation();
        dbContext.DeleteAllNodeSession();
        int sz = dbContext.getAllNode().size();
    }

    @Subscribe
    public void onErrorEvent(ErrorEvent errorEvent) {
        Toast.makeText(this, "" + errorEvent.getErrorMsg(), Toast.LENGTH_SHORT).show();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Bạn không có quyền đăng nhập")
                .setCancelable(false)
                .setPositiveButton("Đăng nhập lại", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        signOut();
                        Intent intent = new Intent(SigninActivity.this, SigninActivity.class);
                        startActivity(intent);
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }

    @Subscribe
    public void onServerEventNode(final ServerEvent_Node serverEvent_node) {

        markerObject = new MarkerObject();
        if (serverEvent_node != null) {
            Log.d("fuckSync", serverEvent_node.getServerResponse().toString());
            if (serverEvent_node.getServerResponse().getStatus() == true) {
                dbContext = DBContext.getInst();
                dbContext.DeleteNodeObject();
                for (Node node : serverEvent_node.getServerResponse().getNodes()
                        ) {
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
            }
        }
    }

    @Subscribe
    public void onErrorEvent(ErrorEvent_Node errorEvent) {

        Toast.makeText(this, "" + errorEvent.getErrorMsg(), Toast.LENGTH_SHORT).show();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Bạn không có quyền đăng nhập")
                .setCancelable(false)
                .setPositiveButton("Đăng nhập lại", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        signOut();
                        Intent intent = new Intent(SigninActivity.this, SigninActivity.class);
                        startActivity(intent);
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

}
