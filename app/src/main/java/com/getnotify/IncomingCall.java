//package com.getnotify;
//
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.telephony.PhoneStateListener;
//import android.telephony.TelephonyManager;
//import android.util.Log;
//import android.widget.Toast;
//
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//import okhttp3.ResponseBody;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class IncomingCall extends BroadcastReceiver {
//
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
//
//        } catch (Exception e) {
//            Log.e("Phone Receive Error", " " + e);
//        }
//
//    }
//
//    private class MyPhoneStateListener extends PhoneStateListener {
//        private boolean IsDataPushed = false;
//
//        public void onCallStateChanged(int state, String incomingNumber) {
//            if (state == 1) {
//                Log.d("MyPhoneListener", state + "   incoming no:" + incomingNumber);
//                if (!IsDataPushed)
//                    CallAPI(incomingNumber);
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
//        public void CallAPI(String incomingNumber) {
//            UserDetails ObjUserDetails = new UserDetails();
//            ObjUserDetails.setSMS(false);
//            ObjUserDetails.setCallLog(incomingNumber);
//            ObjUserDetails.setMacId("a");
//            ObjUserDetails.setCall(true);
//            ObjUserDetails.setUsername("Anuj");
//            ObjUserDetails.setMessage("");
////                ObjUserDetails.setDate(DateFormat.getDateTimeInstance().format(new Date()));
//            ObjUserDetails.setDate(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new java.util.Date()));
////                new ConsumeAPI(ObjUserDetails).execute();
//            Call<LogDetailResponse> objCallAPI = ConsumeAPI.postLog().postLogDetails(ObjUserDetails);
//            objCallAPI.enqueue(new Callback<LogDetailResponse>() {
//                @Override
//                public void onResponse(Call<LogDetailResponse> call, Response<LogDetailResponse> response) {
//                    LogDetailResponse ObjLogDetailResponse = response.body();
//                    Log.i("Response : ", ObjLogDetailResponse.getSuccess().toString());
//                    IsDataPushed = true;
//                }
//
//                @Override
//                public void onFailure(Call<LogDetailResponse> call, Throwable t) {
//                    Log.i("myApp", "error:" + t);
////                        Toast.makeText(LocationBasedActivity.this,"Error",Toast.LENGTH_LONG).show();
//                }
//            });
//        }
//    }
//}
