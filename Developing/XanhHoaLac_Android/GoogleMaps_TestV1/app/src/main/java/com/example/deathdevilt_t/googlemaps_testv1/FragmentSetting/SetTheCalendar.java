package com.example.deathdevilt_t.googlemaps_testv1.FragmentSetting;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.deathdevilt_t.googlemaps_testv1.DBContext.DBContext;
import com.example.deathdevilt_t.googlemaps_testv1.ObjectClass.Log.Model.JSonLogSendModel;
import com.example.deathdevilt_t.googlemaps_testv1.ObjectClass.Log.Model.LogNode;
import com.example.deathdevilt_t.googlemaps_testv1.ObjectClass.NodeObject;
import com.example.deathdevilt_t.googlemaps_testv1.ObjectClass.NodeSessionObject;
import com.example.deathdevilt_t.googlemaps_testv1.ObjectClass.UserInformationObject;
import com.example.deathdevilt_t.googlemaps_testv1.R;
import com.example.deathdevilt_t.googlemaps_testv1.Retrofit.NodeLog.Communicator_Node_Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by chtnnnmtgkyp on 6/26/2017.
 */

public class SetTheCalendar extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, AdapterView.OnItemSelectedListener {
    private String phoneNumber = "";
    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    public SmsMessage[] messages = null;
    private Communicator_Node_Log communicatorNodeLog;

    private ProgressDialog dialogProgress;
    String arr[] = {
            "Không thời hạn",
            "15",
            "20",
            "30"};

    String arr2[] = {
            "15",
            "20",
            "30"};

    String arrRemoting[] = {
            "Tắt Node",
            "Bật Node"
    };

    String arrSchedule[] = {
            "Một ngày",
            "Hằng ngày",
            "Hằng tuần"};

    String arrSynchronous[] = {
            "None"
    };

    String txt_sms;
    String durationtime, status;
    private RadioGroup radioGroup;
    private RadioButton rd_remoting, rd_schedule, rd_synchronous;
    private String mode_cb;

    private Calendar calendar;
    private int year, month, day;
    private DateFormat format = DateFormat.getDateInstance();

    private RelativeLayout rlt_content;
    private TextView txt_duration;
    private TextView txt_state;
    private Spinner spinner, spinner_status;
    private Button btn_send;
    private Button btn_chooseDate;
    private ArrayAdapter<String> adapter;
    private ArrayAdapter<String> adapter_status;
    private TimePicker simpleTimePicker;
    private String hours;
    private String minutes;
    private CheckBox mo, tu, we, th, fr, sa, su;
    private String checkbox1, checkbox2, checkbox3, checkbox4, checkbox5, checkbox6, checkbox7;
    private DBContext dbContext;
    private Context context;
    private String descriptionLog;
    private String strAction;

    private Button btnCancel;
    private OnABCClick onABCClick;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.calendar_fragment, container, false);
        init(view);
        elementListener();
        return view;

    }


    public SetTheCalendar(String phoneNumber, OnABCClick onABCClick) {
        this.onABCClick = onABCClick;
        this.phoneNumber = phoneNumber;
    }

    private void init(View view) {

        context = this.getContext();

        txt_duration = (TextView) view.findViewById(R.id.txt_thoihantuoi);
        txt_state = (TextView) view.findViewById(R.id.txt_status);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH)+1;
        day = calendar.get(Calendar.DAY_OF_MONTH);

        btn_send = (Button) view.findViewById(R.id.btn_send);
        btn_chooseDate = (Button) view.findViewById(R.id.btn_selectDate);
        btn_send.setOnClickListener(this);
        btn_chooseDate.setOnClickListener(this);

        btnCancel = (Button) view.findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(this);

        btn_chooseDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(calendar.getTime()));

        rlt_content = (RelativeLayout) view.findViewById(R.id.rlt_content);
        spinner = (Spinner) view.findViewById(R.id.sprin_select);
        adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, arr);
        adapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        spinner_status = (Spinner) view.findViewById(R.id.sprin_status);


        radioGroup = (RadioGroup) view.findViewById(R.id.rd_group);
        rd_remoting = (RadioButton) view.findViewById(R.id.btnRD_remoting);
        rd_schedule = (RadioButton) view.findViewById(R.id.btnRD_schedule);
        rd_synchronous = (RadioButton) view.findViewById(R.id.btnRD_synchronous);

        simpleTimePicker = (TimePicker) view.findViewById(R.id.simpleTimePicker);
        simpleTimePicker.setIs24HourView(true);


        checkTime();


        mo = (CheckBox) view.findViewById(R.id.check_ngay2);
        tu = (CheckBox) view.findViewById(R.id.check_ngay3);
        we = (CheckBox) view.findViewById(R.id.check_ngay4);
        th = (CheckBox) view.findViewById(R.id.check_ngay5);
        fr = (CheckBox) view.findViewById(R.id.check_ngay6);
        sa = (CheckBox) view.findViewById(R.id.check_ngay7);
        su = (CheckBox) view.findViewById(R.id.check_ngaycn);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == Activity.RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    String result = bundle.getString("selectedSchedule", "error");
                    String result2 = bundle.getString("nameSchedule", "error");
                    arrSynchronous[0] = result2;
                    mode_cb = "6";
                    adapter_status = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, arrSynchronous);
                    spinner_status.setAdapter(adapter_status);
                    txt_sms =  result;
                    descriptionLog ="Hủy " + result2;
                } else if (requestCode == Activity.RESULT_FIRST_USER) {
                    Bundle bundle = data.getExtras();
                    String result = bundle.getString("seletecedBack", "error");
                    rd_remoting.setChecked(true);
                }
        }
    }

    private void disableRelative() {
        for (int i = 0; i < rlt_content.getChildCount(); i++) {
            View child = rlt_content.getChildAt(i);
            child.setEnabled(false);
        }
    }

    private void enableRelative() {
        for (int i = 0; i < rlt_content.getChildCount(); i++) {
            View child = rlt_content.getChildAt(i);
            child.setEnabled(true);
        }
    }


    private void elementListener() {

        int selectedId = radioGroup.getCheckedRadioButtonId();
        if (selectedId == rd_remoting.getId()) {
            mode_cb = "1";
            disableRelative();
        } else if (selectedId == rd_schedule.getId()) {
            mode_cb = "2";
            enableRelative();
        } else if (selectedId == rd_synchronous.getId()) {
            mode_cb = "3";
            enableRelative();
        }


        if (mode_cb == "1") {
            setSpin(arrRemoting);
            setSpin2(arr);
        } else if (mode_cb == "2") {
            setSpin(arrSchedule);
            setSpin2(arr);
        } else if (mode_cb == "3") {
            setSpin(arrSynchronous);
        }
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.btnRD_remoting) {
                    mode_cb = "1";
                    setSpin(arrRemoting);
                    setSpin2(arr);
                    status = "0";
                    disableRelative();
                }
                if (checkedId == R.id.btnRD_schedule) {
                    mode_cb = "2";
                    setSpin(arrSchedule);
                    setSpin2(arr2);
                    status = "1";
                    enableRelative();
                }
                if (checkedId == R.id.btnRD_synchronous) {
                    mode_cb = "3";
                    setSpin(arrSynchronous);
                    status = "1";
                    disableRelative();
                    Log.d("xxx0we", "we are");
                }
            }
        });


        //
        simpleTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                checkTime();
            }
        });

        //
        spinner.setOnItemSelectedListener(this);
        spinner_status.setOnItemSelectedListener(this);
        //
        mo.setOnCheckedChangeListener(this);
        tu.setOnCheckedChangeListener(this);
        we.setOnCheckedChangeListener(this);
        th.setOnCheckedChangeListener(this);
        fr.setOnCheckedChangeListener(this);
        sa.setOnCheckedChangeListener(this);
        su.setOnCheckedChangeListener(this);


        checkCheckBox();
    }

    private void checkTime() {
        if (Build.VERSION.SDK_INT >= 23) {
            hours = String.valueOf(simpleTimePicker.getHour());
            minutes = String.valueOf(simpleTimePicker.getMinute());
        } else {
            hours = String.valueOf(simpleTimePicker.getCurrentHour());
            minutes = String.valueOf(simpleTimePicker.getCurrentMinute());
        }
    }

    private void checkCheckBox() {
        if (mo.isChecked()) {
            checkbox1 = "1";
        } else {
            checkbox1 = "0";
        }
        if (tu.isChecked()) {
            checkbox2 = "1";
        } else {
            checkbox2 = "0";
        }
        if (we.isChecked()) {
            checkbox3 = "1";
        } else {
            checkbox3 = "0";
        }
        if (th.isChecked()) {
            checkbox4 = "1";
        } else {
            checkbox4 = "0";
        }
        if (fr.isChecked()) {
            checkbox5 = "1";
        } else {
            checkbox5 = "0";
        }
        if (sa.isChecked()) {
            checkbox6 = "1";
        } else {
            checkbox6 = "0";
        }
        if (su.isChecked()) {
            checkbox7 = "1";
        } else {
            checkbox7 = "0";
        }
    }

    private void setSpin(String arr[]) {
        adapter_status = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, arr);
        adapter_status.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        spinner_status.setAdapter(adapter_status);
    }

    private void setSpin2(String arr[]) {
        adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, arr);
        adapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {
        int change = v.getId();
        switch (change) {
            case R.id.btn_send:
                checkCheckBox();
                checkTime();
                if (mode_cb == "1") {
                    Log.d("durationTime","000");
                    if (status == "0") {
                        durationtime = "000";
                        minutes = "0";
                        hours = "0";
                        checkbox7 = "0";
                        checkbox6 = "0";
                        checkbox5 = "0";
                        checkbox4 = "0";
                        checkbox3 = "0";
                        checkbox2 = "0";
                        checkbox1 = "0";
                        descriptionLog = "Tắt node";
                        smssending();
                    }
                    if (status == "1") {
                        minutes = "0";
                        hours = "0";
                        checkbox7 = "0";
                        checkbox6 = "0";
                        checkbox5 = "0";
                        checkbox4 = "0";
                        checkbox3 = "0";
                        checkbox2 = "0";
                        checkbox1 = "0";
                        descriptionLog = "Bật node trong " + durationtime + " phút";
                        smssending();
                    }
                } else if (mode_cb == "2") {
                    Log.d("durationTime","015");
                    if (status == "1") {
                        if (hours.length() == 1) {
                            hours = "0" + hours;
                        }
                        if (minutes.length() == 1) {
                            minutes = "0" + minutes;
                        }
                        if (year >= 2000) {
                            year = year - 2000;
                        }
                        String Y = String.valueOf(year);
                        String M = String.valueOf(month);
                        String D = String.valueOf(day);
                        if (Y.length() == 1) {
                            Y = "0" + Y;
                        }
                        if (M.length() == 1) {
                            M = "0" + M;
                        }
                        if (D.length() == 1) {
                            D = "0" + D;
                        }
                        txt_sms = durationtime
                                + minutes + hours
                                + "0" + Y + M + D
                                + status
                                + mode_cb;
                        if (!durationtime.equalsIgnoreCase("000")) {
                            descriptionLog = "Đặt lịch 1 ngày trong " + durationtime + " phút , lúc " + hours + ":" + minutes + "- ngày:" + D + "//" + M + "//" + Y;
                        }
                    } else if (status == "2") {
                        checkbox7 = "0";
                        checkbox6 = "0";
                        checkbox5 = "0";
                        checkbox4 = "0";

                        checkbox2 = "0";
                        checkbox3 = "0";
                        checkbox1 = "0";
                        smssending();
                        descriptionLog = "Đặt lịch hằng ngày trong " + durationtime + " phút , lúc " + hours + ":" + minutes;
                    } else if (status == "3") {
                        smssending();
                        descriptionLog = "Đặt lịch hằng tuần";
                    }
                } else if (mode_cb == "3") {

                }


                if (status == "3" && checkbox1 == "0" && checkbox2 == "0" && checkbox3 == "0" && checkbox4 == "0" && checkbox5 == "0" && checkbox6 == "0" && checkbox7 == "0") {
                    Toast.makeText(context, "hãy chọn thứ để đặt lịch", Toast.LENGTH_SHORT).show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Bạn muốn thực hiện thao tác này ngay chứ ?")
                            .setCancelable(false)
                            .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    try {
                                        SmsManager sendSms = SmsManager.getDefault();
                                        NodeObject nodeObject = new NodeObject();
                                        sendSms.sendTextMessage("+" + phoneNumber, null, txt_sms, null, null);
                                        Log.d("xxx", txt_sms);
                                        Log.d("xxx", descriptionLog);
                                        dbContext = DBContext.getInst();
                                        List<UserInformationObject> userInformationObjects = dbContext.getAllUserInformation();
                                        String userName = "";
                                        String phone = nodeObject.getPhone();
                                        String action = "";
                                        int idLogNode = 1;
                                        long time = System.currentTimeMillis();
                                        if (mode_cb == "1") {
                                            if (status == "0") {
                                                action = "Off";
                                            } else if (status == "1") {
                                                action = "On";
                                            }
                                        } else if (mode_cb == "2") {
                                            action = "Schedule";
                                        } else{
                                            action = "Schedule";
                                        }

                                        for (UserInformationObject us : userInformationObjects
                                                ) {
                                            userName = us.getEmailAddress();

                                        }
                                        List<LogNode> logNodes = dbContext.getAllLogNode();
                                        if (logNodes.size() != 0) {
                                            idLogNode = logNodes.get(logNodes.size() - 1).getIdLogNode() + 1;
                                        }
                                        LogNode logNode = new LogNode();
                                        logNode.setDescription(descriptionLog);
                                        logNode.setTargetNode(phoneNumber);
                                        logNode.setUserName(userName);
                                        logNode.setIdLogNode(idLogNode);
                                        logNode.setAction(action);
                                        logNode.setIsSync("0");
                                        logNode.setIsOnline(1);
                                        logNode.setTime(time);
                                        dbContext.addLogNode(logNode);
                                        //add Node Session
                                        List<NodeSessionObject> nodeSessionObjects = dbContext.getAllNodeSession();
                                        int idNodeSession =1;
                                        if(nodeSessionObjects.size()!=0){
                                            idNodeSession = nodeSessionObjects.get(nodeSessionObjects.size()-1).getId()+1;
                                        }
                                        NodeSessionObject nodeSessionObject = new NodeSessionObject();
                                        nodeSessionObject.setPhoneNumber("+"+phoneNumber);
                                        nodeSessionObject.setId(idNodeSession);
                                        nodeSessionObject.setIdLogNode(idLogNode);

                                        dbContext.addNodeSession(nodeSessionObject);
                                    } catch (Exception e) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                        builder.setMessage("Số điện thoại không hợp lệ !" + e.getMessage())
                                                .setCancelable(false)

                                                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        dialog.cancel();
                                                    }
                                                });
                                        AlertDialog alert = builder.create();
                                        alert.show();

                                    }
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
                break;

            case R.id.btn_selectDate:
                updateDate();
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

    private void smssending() {
        if (hours.length() == 1) {
            hours = "0" + hours;
        }
        if (minutes.length() == 1) {
            minutes = "0" + minutes;
        }

        dbContext = DBContext.getInst();

        List<UserInformationObject> listUser = dbContext.getAllUserInformation();
        String role ="";
        for (UserInformationObject oj:listUser
                ) {
            role = oj.getRole();
        }
        if (role.equalsIgnoreCase("admin") && mode_cb == "1") {
            txt_sms = durationtime
                    + minutes + hours
                    + checkbox7 + checkbox1 + checkbox2 + checkbox3 + checkbox4 + checkbox5 + checkbox6
                    + status
                    + 3;
        }
        else{
            txt_sms = durationtime
                    + minutes + hours
                    + checkbox7 + checkbox1 + checkbox2 + checkbox3 + checkbox4 + checkbox5 + checkbox6
                    + status
                    + mode_cb;
        }

    }

    private void sendLog(JSonLogSendModel jSonLogSendModel) {
        communicatorNodeLog = new Communicator_Node_Log();
        communicatorNodeLog.NodeLog(jSonLogSendModel);


    }

    public void onResume() {
        super.onResume();

        initRealm();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void initRealm() {
        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
        dbContext = DBContext.getInst();
    }

    private void updateDate() {
        new DatePickerDialog(context, d, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int yearx, int monthx, int dayOfMonth) {
            calendar.set(Calendar.YEAR, yearx);
            calendar.set(Calendar.MONTH, monthx);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            year = yearx - 2000;
            month = monthx + 1;
            day = dayOfMonth;
            btn_chooseDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(calendar.getTime()));
        }
    };

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int id = buttonView.getId();
        if (id == R.id.check_ngay2) {
            if (mo.isChecked()) {
                checkbox1 = "1";
            } else {
                checkbox1 = "0";
            }
        } else if (id == R.id.check_ngay3) {
            if (tu.isChecked()) {
                checkbox2 = "1";
            } else {
                checkbox2 = "0";
            }
        } else if (id == R.id.check_ngay4) {
            if (we.isChecked()) {
                checkbox3 = "1";
            } else {
                checkbox3 = "0";
            }
        } else if (id == R.id.check_ngay5) {
            if (th.isChecked()) {
                checkbox4 = "1";
            } else {
                checkbox4 = "0";
            }
        } else if (id == R.id.check_ngay6) {
            if (fr.isChecked()) {
                checkbox5 = "1";
            } else {
                checkbox5 = "0";
            }
        } else if (id == R.id.check_ngay7) {
            if (sa.isChecked()) {
                checkbox6 = "1";
            } else {
                checkbox6 = "0";
            }
        } else if (id == R.id.check_ngaycn) {
            if (su.isChecked()) {
                checkbox7 = "1";
            } else {
                checkbox7 = "0";
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinnerx = (Spinner) parent;
        if (spinnerx.getId() == R.id.sprin_select) {
            if (spinnerx.getItemAtPosition(0) == "Không thời hạn") {
                if (arr[position].length() == 1) {
                    durationtime = "00" + arr[position];
                } else if (arr[position].length() == 2) {
                    durationtime = "0" + arr[position];
                } else if (arr[position].length() == 3) {
                    durationtime = arr[position];
                }
                if (arr[position].equals("Không thời hạn")) {
                    durationtime = "000";
                }
            } else {
                if (arr[position].length() == 1) {
                    durationtime = "00" + arr2[position];
                } else if (arr2[position].length() == 2) {
                    durationtime = "0" + arr2[position];
                } else if (arr2[position].length() == 3) {
                    durationtime = arr2[position];
                }
                if (arr2[position].equals("Không thời hạn")) {
                    durationtime = "000";
                }
            }
        } else if (spinnerx.getId() == R.id.sprin_status) {
            spinner_status.setEnabled(true);
            if (mode_cb == "1") {
                if (arrRemoting[position].equals("Tắt Node")) {
                    status = "0";
                    txt_duration.setEnabled(false);
                    spinner.setEnabled(false);
                } else if (arrRemoting[position].equals("Bật Node")) {
                    status = "1";
                    txt_duration.setEnabled(true);
                    spinner.setEnabled(true);
                }
            } else if (mode_cb == "2") {
                spinner_status.setEnabled(true);
                if (arrSchedule[position].equals("Một ngày")) {
                    status = "1";
                    mo.setEnabled(false);
                    tu.setEnabled(false);
                    we.setEnabled(false);
                    th.setEnabled(false);
                    fr.setEnabled(false);
                    sa.setEnabled(false);
                    su.setEnabled(false);

                    btn_chooseDate.setEnabled(true);
                } else if (arrSchedule[position].equals("Hằng ngày")) {
                    status = "2";
                    mo.setEnabled(false);
                    tu.setEnabled(false);
                    we.setEnabled(false);
                    th.setEnabled(false);
                    fr.setEnabled(false);
                    sa.setEnabled(false);
                    su.setEnabled(false);
                    btn_chooseDate.setEnabled(false);

                    mo.setChecked(true);
                    tu.setChecked(true);
                    we.setChecked(true);
                    th.setChecked(true);
                    fr.setChecked(true);
                    sa.setChecked(true);
                    su.setChecked(true);

                } else if (arrSchedule[position].equals("Hằng tuần")) {
                    status = "3";
                    mo.setEnabled(true);
                    tu.setEnabled(true);
                    we.setEnabled(true);
                    th.setEnabled(true);
                    fr.setEnabled(true);
                    sa.setEnabled(true);
                    su.setEnabled(true);
                    btn_chooseDate.setEnabled(false);

                    mo.setChecked(true);
                    tu.setChecked(false);
                    we.setChecked(false);
                    th.setChecked(false);
                    fr.setChecked(false);
                    sa.setChecked(false);
                    su.setChecked(false);
                }
            } else if (mode_cb == "3") {
                spinner_status.setEnabled(false);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Bạn có muốn hủy đặt lịch không?")
                        .setCancelable(false).setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        rd_remoting.setChecked(true);
                        dialog.dismiss();
                    }
                })
                        .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DialogFragment picker = new ConflictDialog();
                                Bundle bundle = new Bundle();
                                bundle.putString("phoneNumber",phoneNumber);
                                picker.setArguments(bundle);
                                picker.setTargetFragment(SetTheCalendar.this, 1);
                                picker.show(getFragmentManager().beginTransaction(), "Date Picker");
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}
