//package com.getnotify;
//
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.telephony.SmsManager;
//import android.telephony.SmsMessage;
//import android.util.Log;
//import android.widget.Toast;
//
//import java.text.SimpleDateFormat;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class IncomingSms extends BroadcastReceiver {
//    // Get the object of SmsManager
//    final SmsManager sms = SmsManager.getDefault();
//
//    public void onReceive(Context context, Intent intent) {
//
//        // Retrieves a map of extended data from the intent.
//        final Bundle bundle = intent.getExtras();
//        try {
//
//            if (bundle != null) {
//
//                final Object[] pdusObj = (Object[]) bundle.get("pdus");
//
//                for (int i = 0; i < pdusObj.length; i++) {
//                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
//                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();
//                    String message = currentMessage.getDisplayMessageBody();
//                    Log.i("SmsReceiver", "senderNum: " + phoneNumber + "; message: " + message);
//
//                    CallAPI(phoneNumber, message);
//                    // Show Alert
////                    int duration = Toast.LENGTH_LONG;
////                    Toast toast = Toast.makeText(context,
////                            "senderNum: "+ senderNum + ", message: " + message, duration);
////                    toast.show();
//                } // end for loop
//            } // bundle is null
//        } catch (Exception e) {
//            Log.e("SmsReceiver", "Exception smsReceiver" + e);
//        }
//    }
//
//    public void CallAPI(String incomingNumber, String Message) {
//        UserDetails ObjUserDetails = new UserDetails();
//        ObjUserDetails.setSMS(true);
//        ObjUserDetails.setCallLog(incomingNumber);
//        ObjUserDetails.setMacId("a");
//        ObjUserDetails.setCall(false);
//        ObjUserDetails.setUsername("Anuj");
//        ObjUserDetails.setMessage(Message);
////                ObjUserDetails.setDate(DateFormat.getDateTimeInstance().format(new Date()));
//        ObjUserDetails.setDate(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new java.util.Date()));
////                new ConsumeAPI(ObjUserDetails).execute();
//        Call<LogDetailResponse> objCallAPI = ConsumeAPI.postLog().postLogDetails(ObjUserDetails);
//        objCallAPI.enqueue(new Callback<LogDetailResponse>() {
//            @Override
//            public void onResponse(Call<LogDetailResponse> call, Response<LogDetailResponse> response) {
//                LogDetailResponse ObjLogDetailResponse = response.body();
//                Log.i("Response : ", ObjLogDetailResponse.getSuccess().toString());
////                IsDataPushed = true;
//            }
//
//            @Override
//            public void onFailure(Call<LogDetailResponse> call, Throwable t) {
//                Log.i("myApp", "error:" + t);
////                        Toast.makeText(LocationBasedActivity.this,"Error",Toast.LENGTH_LONG).show();
//            }
//        });
//    }
//}
