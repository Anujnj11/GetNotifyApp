package com.getnotify;

import android.Manifest;
import android.app.ActivityManager;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.crashlytics.android.Crashlytics;
//import com.google.android.gms.ads.AdListener;
//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.AdView;

import io.fabric.sdk.android.Fabric;

public class HomeActivity extends AppCompatActivity {
    List<ApplicationInfo> installedApps = new ArrayList<ApplicationInfo>();
    private List<ApplicationInfo> appsjjj;
    private GetNotifyBroadcastReceiver ObjGetNotifyBroadcastReceiver;
    Intent objHomeAc = null;
    TextView Objpassword = null, ObjCountPosted = null ,Objusername=null;
    CardView ObjRequiredAccess,ObjHaveAccess;
    int Count = 0;
    Button ObjVisitSetting,ObjBuzzMe;
    private Dialog RequiredAccess;
//    private AdView mAdView;
    private  String AESToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activityhome);

        RequiredAccess = new Dialog(this);
        Objpassword = (TextView)findViewById(R.id.password);
        Objusername = (TextView)findViewById(R.id.username);

        ObjCountPosted = (TextView)findViewById(R.id.CountPosted);
//        ObjRequiredAccess = (CardView)findViewById(R.id.RequiredAccess);
        ObjHaveAccess = (CardView)findViewById(R.id.HaveAccess);

        ObjVisitSetting = (Button)findViewById(R.id.VisitSetting);
        ObjBuzzMe = (Button)findViewById(R.id.BuzzMe);
        objHomeAc = getIntent();
        String Password = objHomeAc.getExtras().getString("Password");
        AESToken = objHomeAc.getExtras().getString("AESToken");
        String Username = objHomeAc.getExtras().getString("Username");
        Objpassword.setText(Password);
        Objusername.setText(Username);
//        if(ContextCompat.checkSelfPermission(HomeActivity.this,
//                Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED){
//
//            if(ActivityCompat.shouldShowRequestPermissionRationale(HomeActivity.this,Manifest.permission.READ_PHONE_STATE)){
//                ActivityCompat.requestPermissions(HomeActivity.this,new String[]{Manifest.permission.READ_PHONE_STATE,Manifest.permission.READ_SMS,Manifest.permission.BIND_NOTIFICATION_LISTENER_SERVICE},1);
//            }
//            else{
//                ActivityCompat.requestPermissions(HomeActivity.this,new String[]{Manifest.permission.READ_PHONE_STATE,Manifest.permission.READ_SMS,Manifest.permission.BIND_NOTIFICATION_LISTENER_SERVICE},1);
//            }
//        }else
//            {
//
//        }
        Log.i("IsServiceRunning", (String.valueOf(isMyServiceRunning(NotificationService.class))));
//        InitiADS();
        if (hasNotificationAccess()) {
            RequiredAccess.dismiss();
        } else {
            ShowRequiredAccess();
            ObjHaveAccess.setVisibility(View.GONE);
//            //service is not enabled try to enabled by calling...
//            getApplicationContext().startActivity(new Intent(
//                    "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
        }
        RegisterService();
    }

//    private  void InitiADS(){
//        mAdView = (AdView) findViewById(R.id.adView);
////        mAdView.setAdSize(AdSize.BANNER);
////        mAdView.setAdUnitId(getString(R.string.banner_home_footer));
//
//        AdRequest adRequest = new AdRequest.Builder()
////                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
////                // Check the LogCat to get your test device ID
////                .addTestDevice("97C729DB642AD3645847E56DFEFBC238")
//                .build();
//        mAdView.loadAd(adRequest);
//        mAdView.setAdListener(new AdListener() {
//            @Override
//            public void onAdLoaded() {
////                Toast.makeText(getApplicationContext(), "Ad is loaded!", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onAdClosed() {
////                Toast.makeText(getApplicationContext(), "Ad is closed!", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onAdFailedToLoad(int errorCode) {
//                Log.i("onAdFailedToLoad", (String.valueOf(errorCode)));
////                Toast.makeText(getApplicationContext(), "Ad failed to load! error code: " + errorCode, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onAdLeftApplication() {
////                Toast.makeText(getApplicationContext(), "Ad left application!", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onAdOpened() {
//                super.onAdOpened();
//            }
//        });
//
//    }

    @Override
    public void onPause() {
//        if (mAdView != null) {
//            mAdView.pause();
//        }
        super.onPause();
    }
    @Override
    protected void onResume() {
//        if (mAdView != null) {
//            mAdView.resume();
//        }
        if(hasNotificationAccess()){
//            ObjRequiredAccess.setVisibility(View.INVISIBLE);
//            ObjHaveAccess.setVisibility(View.VISIBLE);
//            ObjRequiredAccess.setVisibility(View.GONE);
            RequiredAccess.dismiss();
            ObjHaveAccess.setVisibility(View.VISIBLE);
        }
        else {
//            ObjHaveAccess.setVisibility(View.INVISIBLE);
//            ObjRequiredAccess.setVisibility(View.VISIBLE);
            ShowRequiredAccess();
            ObjHaveAccess.setVisibility(View.GONE);
//            ObjRequiredAccess.setVisibility(View.VISIBLE);
        }
        super.onResume();
    }

    @Override
    public void onDestroy() {
//        if (mAdView != null) {
//            mAdView.destroy();
//        }
        super.onDestroy();
    }



    public void VisitSetting(View v){
        if (Build.VERSION.SDK_INT >= 22) {
            startActivity(new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS));

        } else {
            startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            if(ObjGetNotifyBroadcastReceiver !=null)
                unregisterReceiver(ObjGetNotifyBroadcastReceiver);
        }
        return super.onKeyDown(keyCode, event);
    }


    public void BuzzMe(View v){
        UserDetails ObjUserDetails = new UserDetails();
        ObjUserDetails.setSMS(false);
        ObjUserDetails.setCallLog("You are awesome!!");
        ObjUserDetails.setCall(true);
        ObjUserDetails.setAESToken(AESToken);
        ObjUserDetails.setMessage("");
        CallAPI(ObjUserDetails);
        sendNotification();
        ShowSnackBar();
    }

    public  void ShowRequiredAccess(){
        RequiredAccess.setContentView(R.layout.requiredaccesspartial);
        RequiredAccess.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        RequiredAccess.show();
        RequiredAccess.setCancelable(false);
    }


    public  void RegisterService() {
        try {
//            Log.i("BeforeServiceStop", (String.valueOf(isMyServiceRunning(NotificationService.class))));
//            stopService(new Intent(this, NotificationService.class));
//            Log.i("AfterServiceStop", (String.valueOf(isMyServiceRunning(NotificationService.class))));
//            startService(new Intent(this, NotificationService.class));
//            Log.i("AfterServicestart", (String.valueOf(isMyServiceRunning(NotificationService.class))));
            // Finally we register a receiver to tell the MainActivity when a notification has been received
            ObjGetNotifyBroadcastReceiver = new GetNotifyBroadcastReceiver();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("com.getnotify");
            registerReceiver(ObjGetNotifyBroadcastReceiver, intentFilter);
        }
        catch (Exception e){
//            stopService(new Intent(this, NotificationService.class));
//            startService(new Intent(this, NotificationService.class));
//            if(ObjGetNotifyBroadcastReceiver !=null)
            unregisterReceiver(ObjGetNotifyBroadcastReceiver);
            ObjGetNotifyBroadcastReceiver = new GetNotifyBroadcastReceiver();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("com.getnotify");
            registerReceiver(ObjGetNotifyBroadcastReceiver, intentFilter);
        }
    }

    public List<ApplicationInfo> getInstalledApps(List<ApplicationInfo> apps) {
        for (ApplicationInfo app : apps) {
            //checks for flags; if flagged, check if updated system app
            if ((app.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
                installedApps.add(app);
                //it's a system app, not interested
            } else if ((app.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
                //Discard this one
                //in this case, it should be a user-installed app
            } else {
                installedApps.add(app);
            }
        }
        return installedApps;
    }

    boolean hasNotificationAccess() {
        ContentResolver contentResolver = this.getContentResolver();
        String enabledNotificationListeners = Settings.Secure.getString(contentResolver, "enabled_notification_listeners");
        String packageName = this.getPackageName();
        // check to see if the enabledNotificationListeners String contains our package name
        return !(enabledNotificationListeners == null || !enabledNotificationListeners.contains(packageName));
    }

    public class GetNotifyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            UserDetails ObjUserDetails= (UserDetails) intent.getSerializableExtra("GetnotifyData");
            Log.i("UserDetails: ", ObjUserDetails.getCallLog());
            CallAPI(ObjUserDetails);
//            int receivedNotificationCode = intent.getIntExtra("Notification Code", -1);
//            Log.i("RC: ", String.valueOf(receivedNotificationCode));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) ==
                            PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_LONG).show();
                }
                return;
        }
    }


    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public void CallAPI(UserDetails ObjUserDetails) {
        ObjUserDetails.setDate(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new java.util.Date()));
//        Call<LogDetailResponse> objCallAPI = ConsumeAPI.postLog().postLogDetails(ObjUserDetails);
        Call<LogDetailResponse> objCallAPI = ConsumeAPI.postLog().postAESLogDetails(ObjUserDetails);
        objCallAPI.enqueue(new Callback<LogDetailResponse>() {
            @Override
            public void onResponse(Call<LogDetailResponse> call, Response<LogDetailResponse> response) {
                LogDetailResponse ObjLogDetailResponse = response.body();
                if (ObjLogDetailResponse.getSuccess()) {
                    Count++;
                    ObjCountPosted.setText(String.valueOf(Count));
                }
            }

            @Override
            public void onFailure(Call<LogDetailResponse> call, Throwable t) {
                Log.i("myApp", "error:" + t);
//                        Toast.makeText(LocationBasedActivity.this,"Error",Toast.LENGTH_LONG).show();
            }
        });
    }

    public  void ShowSnackBar(){
        Snackbar snack = Snackbar.make(findViewById(android.R.id.content), "You are awesome!!", Snackbar.LENGTH_LONG);
        View view = snack.getView();
        TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(ContextCompat.getColor(HomeActivity.this, R.color.white));
        view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        } else {
            tv.setGravity(Gravity.CENTER_HORIZONTAL);
        }
        snack.show();
    }

    public void sendNotification() {

        //Get an instance of NotificationManager//

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle("Get notify")
                        .setContentText("You are awesome!!");


        // Gets an instance of the NotificationManager service//

        NotificationManager mNotificationManager =

                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // When you issue multiple notifications about the same type of event,
        // it’s best practice for your app to try to update an existing notification
        // with this new information, rather than immediately creating a new notification.
        // If you want to update this notification at a later date, you need to assign it an ID.
        // You can then use this ID whenever you issue a subsequent notification.
        // If the previous notification is still visible, the system will update this existing notification,
        // rather than create a new one. In this example, the notification’s ID is 001//
        mNotificationManager.notify(001, mBuilder.build());
    }
}
