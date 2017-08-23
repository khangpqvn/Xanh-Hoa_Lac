package com.example.deathdevilt_t.googlemaps_testv1.FragmentSetting;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

/**
 * Created by DeathDevil.T_T on 07-Aug-17.
 */

public class SMSBroadcastReceiver extends BroadcastReceiver {
    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    public  SmsMessage[] messages = null;
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("fuckBroadCase :",intent.getAction());
        if (intent.getAction() == SMS_RECEIVED){
            Bundle bundle = intent.getExtras();
            if(bundle!=null){
                Object[] pdus = (Object[])bundle.get("pdus");
                messages = new SmsMessage[pdus.length];
                for(int i =0; i<pdus.length;i++){
                    messages[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                    if(messages.length>-1){
                        Log.d("fuckBroadcast :",messages[0].getMessageBody());
                    }
                }
            }

        }
    }
}
