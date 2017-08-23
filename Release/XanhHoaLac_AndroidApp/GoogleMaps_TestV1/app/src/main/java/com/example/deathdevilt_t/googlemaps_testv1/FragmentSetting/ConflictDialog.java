package com.example.deathdevilt_t.googlemaps_testv1.FragmentSetting;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.deathdevilt_t.googlemaps_testv1.R;

import java.util.ArrayList;

/**
 * Created by DeathDevil.T_T on 14-Aug-17.
 */

public class ConflictDialog extends DialogFragment implements View.OnClickListener {
    private TextView txt_Onetime;
    private Button btn_huy;
    private ListView listView;
    private ArrayList<String> array;
    private ProgressDialog dialog;
    private String phoneNumber;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_cancel_schedule, container);
        init(view);
        setListener();
        return view;
    }

    public void onResume() {
        super.onResume();

        getActivity().registerReceiver(broadcastReceiver, new IntentFilter("android.provider.Telephony.SMS_RECEIVED"));
        dialog = ProgressDialog.show(getActivity(), "Đang đợi phản hồi", "Vui lòng chờ...", true);
        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if ((keyCode == android.view.KeyEvent.KEYCODE_BACK)) {
                    Intent i = new Intent();
                    i.putExtra("seletecedBack", "Model");
                    getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_CANCELED, i);
                    getDialog().dismiss();
                    return true;
                } else return false;
            }
        });


    }


    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(broadcastReceiver);
    }

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
            SmsMessage[] messages = null;
            if (intent.getAction() == SMS_RECEIVED) {
                Bundle bundle = intent.getExtras();
                if (bundle != null) {
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    messages = new SmsMessage[pdus.length];
                    for (int i = 0; i < pdus.length; i++) {
                        messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                        if (messages.length > -1) {
                            String test[] = messages[0].getMessageBody().split("\\|");
                            for (int a = 0; a < 5; a++) {
                                if (a == 0) {
                                    if (test[0].equalsIgnoreCase("0000000000000")) {
                                        txt_Onetime.setEnabled(false);
                                    } else {

                                        String text_oneTime = "Lịch đặt vào: ";
                                        for (int x = 0; x < test[0].length() - 3; x++) {
                                            if (x % 2 == 0 && x < 6) {
                                                if (x < 4) {
                                                    text_oneTime += test[0].substring(x, x + 2) + "-";
                                                }
                                                if (x == 4) {
                                                    text_oneTime += test[0].substring(x, x + 2) + " ";
                                                }
                                            } else if (x % 2 == 0 && x >= 6) {
                                                if (x == 6) {
                                                    text_oneTime += test[0].substring(x, x + 2) + ":";
                                                } else if (x == 8) {
                                                    text_oneTime += test[0].substring(x, x + 2);
                                                }
                                            }
                                        }
                                        txt_Onetime.setText(text_oneTime);
                                    }
                                } else {
                                    if (!test[a].equalsIgnoreCase("00000000000000")) {
                                        Log.d("xxx", test[a]);
                                        String multiple = "Lịch được đặt vào thứ: ";

                                        for (int x = 0; x < 7; x++) {
                                            if (x == 0 && test[a].substring(0, 1).equalsIgnoreCase("1")) {
                                                multiple += "2 ";
                                            } else if (x == 1 && test[a].substring(1, 2).equalsIgnoreCase("1")) {
                                                multiple += "3 ";
                                            } else if (x == 2 && test[a].substring(2, 3).equalsIgnoreCase("1")) {
                                                multiple += "4 ";
                                            } else if (x == 3 && test[a].substring(3, 4).equalsIgnoreCase("1")) {
                                                multiple += "5 ";
                                            } else if (x == 4 && test[a].substring(4, 5).equalsIgnoreCase("1")) {
                                                multiple += "6 ";
                                            } else if (x == 5 && test[a].substring(5, 6).equalsIgnoreCase("1")) {
                                                multiple += "7 ";
                                            } else if (x == 6 && test[a].substring(6, 7).equalsIgnoreCase("1")) {
                                                multiple += "CN ";
                                            }
                                        }
                                        for (int x = 7; x <= 9; x++) {
                                            if (x % 2 != 0 && x == 7) {
                                                multiple += test[a].substring(x, x + 2) + ":";
                                            } else if (x % 2 != 0 && x == 9) {
                                                multiple += test[a].substring(x, x + 2);
                                            }
                                        }
                                        array.add(multiple);
                                    }
                                }
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, array);
                            listView.setAdapter(adapter);
                            dialog.dismiss();
                        }
                    }
                }
            }
        }
    };


    private void init(View view) {
        btn_huy = (Button) view.findViewById(R.id.btn_close);
        btn_huy.setOnClickListener(this);
        txt_Onetime = (TextView) view.findViewById(R.id.txt_onetime);
        txt_Onetime.setOnClickListener(this);
        listView = (ListView) view.findViewById(R.id.listview_schedule);
        array = new ArrayList<>();
        Bundle bundle = getArguments();
        phoneNumber = bundle.getString("phoneNumber");
        Log.d("xxa",phoneNumber);
        SmsManager sendSms = SmsManager.getDefault();
        sendSms.sendTextMessage("+"+phoneNumber, null, "0000000000000002", null, null);
    }

    private void setListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent();
                i.putExtra("selectedSchedule", initSMS(position));
                i.putExtra("nameSchedule", array.get(position).toString());
                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, i);
                getDialog().dismiss();
            }
        });
    }

    private String initSMS(int pos) {
        String smsText = "000000000000" + pos + "202";
        return smsText;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_close:

                Intent i = new Intent();
                i.putExtra("seletecedBack", "Model");
                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_FIRST_USER, i);
                getDialog().dismiss();
                break;

            case R.id.txt_onetime:
                Intent s = new Intent();
                s.putExtra("selectedSchedule", "0000000000000102");
                s.putExtra("nameSchedule", txt_Onetime.getText().toString());
                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, s);
                getDialog().dismiss();
        }
    }


}
