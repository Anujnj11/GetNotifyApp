package com.getnotify;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;


public class IncomingSms extends BroadcastReceiver {
    // Get the object of SmsManager
    final SmsManager sms = SmsManager.getDefault();

    public void onReceive(Context context, Intent intent) {

        // Retrieves a map of extended data from the intent.
        final Bundle bundle = intent.getExtras();
        try {
            if (bundle != null) {
                final Object[] pdusObj = (Object[]) bundle.get("pdus");
                String phoneNumber = "";
                String message ="";
                for (int i = 0; i < pdusObj.length; i++)
                {
                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    phoneNumber = currentMessage.getDisplayOriginatingAddress();
                    message += currentMessage.getDisplayMessageBody();
//                    Log.i("SmsReceiver", "senderNum: " + phoneNumber + "; message: " + message);
                }
                String Name = Utility.GetContactName(context,phoneNumber);
                if(!Name.isEmpty())
                    phoneNumber += " ( " + Name + " ) ";
                UserDetails ObjUserDetails = new UserDetails();
                ObjUserDetails.setSMS(true);
                ObjUserDetails.setCallLog(phoneNumber);
                ObjUserDetails.setCall(false);
                ObjUserDetails.setMessage(message);
                Bundle extras = intent.getExtras();
                Intent inte = new Intent("NotifyBroadCast");
                // Data you need to pass to activity
                inte.putExtra("GetnotifyData", ObjUserDetails);
                context.sendBroadcast(inte);
            }
        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" + e);
        }
    }
}
