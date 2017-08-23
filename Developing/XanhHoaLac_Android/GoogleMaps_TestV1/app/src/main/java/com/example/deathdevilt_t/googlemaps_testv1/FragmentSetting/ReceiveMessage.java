package com.example.deathdevilt_t.googlemaps_testv1.FragmentSetting;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;

/**
 * Created by chtnnnmtgkyp on 6/22/2017.
 */

public class ReceiveMessage extends BroadcastReceiver {

    final SmsManager mysms = SmsManager.getDefault();
    static int id = 1;
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle myBundle = intent.getExtras();
        try {
            if(myBundle != null){
                final Object[] messageContent = {(Object[])myBundle.get("pdus")};
                SmsMessage mynewsms;
                for (int i = 0;i<messageContent.length; i++){
                    if (Build.VERSION.SDK_INT >= 19) { //KITKAT
                        SmsMessage[] msgs = Telephony.Sms.Intents.getMessagesFromIntent(intent);
                        mynewsms = msgs[0];
                    } else {
                        mynewsms = SmsMessage.createFromPdu((byte[])messageContent[i]);
                    }
                    NewMessageNotification nome = new NewMessageNotification();
                    nome.notify(context,mynewsms.getDisplayOriginatingAddress(),mynewsms.getDisplayMessageBody(),id);
                    id++;
                }
            }
        }catch (Exception ex){

        }
    }
}
