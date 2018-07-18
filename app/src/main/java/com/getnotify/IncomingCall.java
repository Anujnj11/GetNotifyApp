package com.getnotify;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;
import android.util.Log;

public class IncomingCall extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        try {
            if (intent != null && intent.getAction().equals("android.intent.action.NEW_OUTGOING_CALL")) {
                //Toast.makeText(context, "Outgoign call", 1000).show();
                String number = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
            } else {
                //get the phone state
                String newPhoneState = intent.hasExtra(TelephonyManager.EXTRA_STATE) ? intent.getStringExtra(TelephonyManager.EXTRA_STATE) : null;
                Bundle bundle = intent.getExtras();

                if (newPhoneState != null && newPhoneState.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                    UserDetails ObjUserDetails = new UserDetails();
                    //read the incoming call number
                    String phoneNumber = bundle.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
                    ObjUserDetails.setMobileNumber(phoneNumber);
                    String Name = Utility.GetContactName(context,phoneNumber);
                    if(!Name.isEmpty())
                        phoneNumber += " ( " + Name + " ) ";
                    ObjUserDetails.setSMS(false);
                    ObjUserDetails.setCallLog(phoneNumber);
                    ObjUserDetails.setCall(true);
                    ObjUserDetails.setMessage("Incoming call");
                    Bundle extras = intent.getExtras();
                    Intent i = new Intent("NotifyBroadCast");
                    // Data you need to pass to activity
                    i.putExtra("GetnotifyData", ObjUserDetails);
                    context.sendBroadcast(i);

                }

//                else if (newPhoneState != null && newPhoneState.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
//                    //Once the call ends, phone will become idle
//                    Log.i("PHONE RECEIVER", "Telephone is now idle");
//                } else if (newPhoneState != null && newPhoneState.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
////Once you receive call, phone is busy
//                    Log.i("PHONE RECEIVER", "Telephone is now busy");
//                }
            }

        } catch (Exception ee) {
            Log.i("Telephony receiver", ee.getMessage());
        }


//    public void onReceive(Context context, Intent intent) {
//
//        try {
//            // TELEPHONY MANAGER class object to register one listner
//            TelephonyManager tmgr = (TelephonyManager) context
//                    .getSystemService(Context.TELEPHONY_SERVICE);
//
//            //Create Listner
//            MyPhoneStateListener PhoneListener = new MyPhoneStateListener();
//
//            // Register listener for LISTEN_CALL_STATE
//            tmgr.listen(PhoneListener, PhoneStateListener.LISTEN_CALL_STATE);
//        } catch (Exception e) {
//            Log.e("Phone Receive Error", " " + e);
//        }
//
//    }
//
//    private class MyPhoneStateListener extends PhoneStateListener {
//
//        public void onCallStateChanged(int state, String incomingNumber) {
//            if (state == 1) {
//                Log.d("MyPhoneListener", state + "   incoming no:" + incomingNumber);
////                if (!IsDataPushed)
////                    CallAPI(incomingNumber);
////                Call<ResponseBody> objCallAPI = ConsumeAPI.postLog().postLogDetails(ObjUserDetails);
////                objCallAPI.enqueue(new Callback<ResponseBody>() {
////                    @Override
////                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response){
////                        ResponseBody ObjLogDetailResponse = response.body();
////                        Log.i("Response : ", ObjLogDetailResponse.toString());
////                    }
////                    @Override
////                    public void onFailure(Call<ResponseBody> call, Throwable t){
////                        Log.i("myApp", "error:"+t);
//////                        Toast.makeText(LocationBasedActivity.this,"Error",Toast.LENGTH_LONG).show();
////                    }
////                });
//            }
//        }
//
////        public void CallAPI(String incomingNumber) {
////            UserDetails ObjUserDetails = new UserDetails();
////            ObjUserDetails.setSMS(false);
////            ObjUserDetails.setCallLog(incomingNumber);
////            ObjUserDetails.setMacId("a");
////            ObjUserDetails.setCall(true);
////            ObjUserDetails.setUsername("Anuj");
////            ObjUserDetails.setMessage("");
//////                ObjUserDetails.setDate(DateFormat.getDateTimeInstance().format(new Date()));
////            ObjUserDetails.setDate(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new java.util.Date()));
//////                new ConsumeAPI(ObjUserDetails).execute();
////            Call<LogDetailResponse> objCallAPI = ConsumeAPI.postLog().postLogDetails(ObjUserDetails);
////            objCallAPI.enqueue(new Callback<LogDetailResponse>() {
////                @Override
////                public void onResponse(Call<LogDetailResponse> call, Response<LogDetailResponse> response) {
////                    LogDetailResponse ObjLogDetailResponse = response.body();
////                    Log.i("Response : ", ObjLogDetailResponse.getSuccess().toString());
////                    IsDataPushed = true;
////                }
////
////                @Override
////                public void onFailure(Call<LogDetailResponse> call, Throwable t) {
////                    Log.i("myApp", "error:" + t);
//////                        Toast.makeText(LocationBasedActivity.this,"Error",Toast.LENGTH_LONG).show();
////                }
////            });
////        }
//    }
    }

}
