package com.getnotify;

import android.Manifest;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
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

public class HomeActivity extends AppCompatActivity {
    List<ApplicationInfo> installedApps = new ArrayList<ApplicationInfo>();
    private List<ApplicationInfo> appsjjj;
    private GetNotifyBroadcastReceiver ObjGetNotifyBroadcastReceiver;
    Intent objHomeAc = null;
    TextView Objpassword = null;
    CardView ObjRequiredAccess,ObjHaveAccess;
    Button ObjVisitSetting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityhome);

        Objpassword = (TextView)findViewById(R.id.password);
        ObjRequiredAccess = (CardView)findViewById(R.id.RequiredAccess);
        ObjHaveAccess = (CardView)findViewById(R.id.HaveAccess);

        ObjVisitSetting = (Button)findViewById(R.id.VisitSetting);
        objHomeAc = getIntent();
        String Password = objHomeAc.getExtras().getString("Password");
        Objpassword.setText(Password);
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

        if (hasNotificationAccess()) {
//            if(Build.VERSION.SDK_INT >= 22 ){
//                startActivity(new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS));
//
//            }
//            else{
//                startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
//            }
//            PackageManager pm = getPackageManager();
//            List<ApplicationInfo> apps = pm.getInstalledApplications(0);
//            List<ApplicationInfo> appsjjj=   getInstalledApps(apps);
//            Log.i("ApplicationInfo","jeee");
            //service is enabled do something



//            RegisterService();
            ObjRequiredAccess.setVisibility(View.GONE);

        } else {
//            ObjHaveAccess.setVisibility(View.INVISIBLE);

            ObjHaveAccess.setVisibility(View.GONE);
//            //service is not enabled try to enabled by calling...
//            getApplicationContext().startActivity(new Intent(
//                    "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
        }
        RegisterService();
    }


    @Override
    protected void onResume() {
        if(hasNotificationAccess()){
//            ObjRequiredAccess.setVisibility(View.INVISIBLE);
//            ObjHaveAccess.setVisibility(View.VISIBLE);
            ObjRequiredAccess.setVisibility(View.GONE);
            ObjHaveAccess.setVisibility(View.VISIBLE);
        }
        else {
//            ObjHaveAccess.setVisibility(View.INVISIBLE);
//            ObjRequiredAccess.setVisibility(View.VISIBLE);

            ObjHaveAccess.setVisibility(View.GONE);
            ObjRequiredAccess.setVisibility(View.VISIBLE);
        }
        super.onResume();
    }

    public void VisitSetting(View v){
        if (Build.VERSION.SDK_INT >= 22) {
            startActivity(new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS));

        } else {
            startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
        }
    }


    public  void RegisterService(){
        // Finally we register a receiver to tell the MainActivity when a notification has been received
        ObjGetNotifyBroadcastReceiver = new GetNotifyBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.getnotify");
        registerReceiver(ObjGetNotifyBroadcastReceiver, intentFilter);
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
                Log.i("Response : ", ObjLogDetailResponse.getSuccess().toString());
            }

            @Override
            public void onFailure(Call<LogDetailResponse> call, Throwable t) {
                Log.i("myApp", "error:" + t);
//                        Toast.makeText(LocationBasedActivity.this,"Error",Toast.LENGTH_LONG).show();
            }
        });
    }
}
