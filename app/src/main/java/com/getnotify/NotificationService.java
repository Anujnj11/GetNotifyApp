package com.getnotify;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class NotificationService extends NotificationListenerService {
    Context context;
    private static String Ticker = "";
    private static Bundle extras = null;
    private static String title = null;
    private static String text = null;
    private static Pattern CallRegex = Pattern.compile("");
    private  static String Username = null;
    private  static String Password = null;
    private  static String AESToken = null;
    private  static String MESSAGING = null;
//    private static final String DIALER = "com.android.dialer";
//    private static String DIALER = null;
    private static ArrayList<String> DIALER = null;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

//        nlservicereciver = new NLServiceReceiver();
//        IntentFilter filter = new IntentFilter();
//        filter.addAction("com.kpbird.nlsexample.NOTIFICATION_LISTENER_SERVICE_EXAMPLE");
//        registerReceiver(nlservicereciver,filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        unregisterReceiver(nlservicereciver);
    }


//    @Override
//    public IBinder onBind(Intent intent) {
//        return super.onBind(intent);
//    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        if(Username !=null && Password !=null && AESToken!=null) {
            int notificationCode = matchNotificationCode(sbn);
            if (notificationCode != InterceptedNotificationCode.OTHER_NOTIFICATIONS_CODE) {
                if (notificationCode == 1)//Call data
                {
                    ExceuteCallBroadCast(sbn);
//                if (text == null) {
//                    // for whatsapp on kitkat second whats app text
//                    // will be null
//                    if (extras.get("android.textLines") != null) {
//                        CharSequence[] charText = (CharSequence[]) extras.get("android.textLines");
//                        if (charText.length > 0) {
//                            text = charText[charText.length - 1].toString();
//                        }
//                    }
//                }

                } else if (notificationCode == 2)//SMS DATA
                {
                    ExceuteSMSBroadCast(sbn);
                }
            }
        }
    }



    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        int notificationCode = matchNotificationCode(sbn);

//        if (notificationCode != InterceptedNotificationCode.OTHER_NOTIFICATIONS_CODE) {
//            StatusBarNotification[] activeNotifications = this.getActiveNotifications();
//            if (activeNotifications != null && activeNotifications.length > 0) {
//                for (int i = 0; i < activeNotifications.length; i++) {
//                    if (notificationCode == matchNotificationCode(activeNotifications[i])) {
//                        Intent intent = new Intent("com.github.chagall.notificationlistenerexample");
//                        intent.putExtra("Notification Code", notificationCode);
//                        sendBroadcast(intent);
//                        break;
//                    }
//                }
//            }
//        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //retrieving data from the received intent
//        if(intent.hasExtra("command")) {
//            Log.i("NotificationService", "Started for command '"+intent.getStringExtra("command"));
//        } else
        if(intent !=null && intent.hasExtra("email")) {
            Username = intent.getStringExtra("email");
            Password = intent.getStringExtra("password");
            AESToken = intent.getStringExtra("AESToken");
            MESSAGING = intent.getStringExtra("MESSAGING");
//            DIALER =  intent.getStringExtra("CALL");
            DIALER =  intent.getStringArrayListExtra("CALL");
        }
        super.onStartCommand(intent, flags, startId);
        // NOTE: We return STICKY to prevent the automatic service termination
        return START_STICKY;
    }



//
//    @Override
//    public void onCreate() {
//
//        super.onCreate();
//        context = getApplicationContext();
//
//    }
//    @Override
//    public void onNotificationPosted(StatusBarNotification sbn) {
//        String pack = sbn.getPackageName();
//        String ticker ="";
//        if(sbn.getNotification().tickerText !=null) {
//            ticker = sbn.getNotification().tickerText.toString();
//        }
//        Bundle extras = sbn.getNotification().extras;
//        String title = extras.getString("android.title");
////        String text = extras.getCharSequence("android.text").toString();
//        String text = null;
//        if (extras.getCharSequence("android.text") != null) {
//            text = extras.getCharSequence("android.text").toString();
//        }
//        if (text == null) {
//        // for whatsapp on kitkat second whats app text
//        // will be null
//            if (extras.get("android.textLines") != null) {
//                CharSequence[] charText = (CharSequence[]) extras.get("android.textLines");
//                if (charText.length > 0) {
//                    text = charText[charText.length - 1].toString();
//                }
//            }
//        }
//        int id1 = extras.getInt(Notification.EXTRA_SMALL_ICON);
//        Bitmap id = sbn.getNotification().largeIcon;
//
//
//        Log.i("Package",pack);
//        Log.i("Ticker",ticker);
//        Log.i("Title",title);
//        Log.i("Text",text);
//
////        Intent msgrcv = new Intent("Msg");
////        msgrcv.putExtra("package", pack);
////        msgrcv.putExtra("ticker", ticker);
////        msgrcv.putExtra("title", title);
////        msgrcv.putExtra("text", text);
////        if(id != null) {
////            ByteArrayOutputStream stream = new ByteArrayOutputStream();
////            id.compress(Bitmap.CompressFormat.PNG, 100, stream);
////            byte[] byteArray = stream.toByteArray();
////            msgrcv.putExtra("icon",byteArray);
////        }
////        LocalBroadcastManager.getInstance(context).sendBroadcast(msgrcv);
//
//
//    }
//
//    @Override
//    public void onNotificationRemoved(StatusBarNotification sbn) {
//        Log.i("Msg","Notification Removed");
//    }

//    private static  class ApplicationPackageNames {
//        public static final String DIALER = "com.android.dialer";
//            MESSAGING = "com.google.android.apps.messaging";
////        public static final String WHATSAPP_PACK_NAME = "com.whatsapp";
//    }

    /*
        These are the return codes we use in the method which intercepts
        the notifications, to decide whether we should do something or not
     */
    public static final class InterceptedNotificationCode {
        public static final int DIALER = 1;
        public static final int MESSAGING = 2;
        //        public static final int MESSAGING = 3;
        public static final int OTHER_NOTIFICATIONS_CODE = 3; // We ignore all notification with code == 4
    }


    private int matchNotificationCode(StatusBarNotification sbn) {
        String packageName = sbn.getPackageName();
//        if (packageName.equals(DIALER)
//                || packageName.equals(DIALER)) {
//            return (InterceptedNotificationCode.DIALER);
        if (DIALER !=null && DIALER.contains(packageName)) {
            return (InterceptedNotificationCode.DIALER);
        } else if (MESSAGING !=null && packageName.equals(MESSAGING)) {
            return (InterceptedNotificationCode.MESSAGING);
        }
//        else if(packageName.equals(ApplicationPackageNames.WHATSAPP_PACK_NAME)){
//            return(InterceptedNotificationCode.WHATSAPP_CODE);
//        }
        else {
            return (InterceptedNotificationCode.OTHER_NOTIFICATIONS_CODE);
        }
    }

    private void ExceuteCallBroadCast(StatusBarNotification sbn){
        UserDetails ObjUserDetails = new UserDetails();
        if (sbn.getNotification().tickerText != null) {
            Ticker = sbn.getNotification().tickerText.toString();
        }
        extras = sbn.getNotification().extras;
        title= extras.getString("android.title").replaceAll("\\s+","");
        if (extras.getCharSequence("android.text") != null) {
            text = extras.getCharSequence("android.text").toString();
        }
//        if( title!=null && ValidateMobile(title) && text !=null && text.equalsIgnoreCase("Incoming call") && !text.equalsIgnoreCase("Dialing")){
        if(text !=null && text.contains("Incoming") && title !=null){
            ObjUserDetails.setSMS(false);
            ObjUserDetails.setCallLog(title);
            ObjUserDetails.setCall(true);
//
//            ObjUserDetails.setUsername("Anuj");
//            ObjUserDetails.setMacId("a");

            ObjUserDetails.setAESToken(AESToken);
            ObjUserDetails.setMessage(text);
            Intent intent = new Intent("com.getnotify");
            intent.putExtra("GetnotifyData", ObjUserDetails);
            sendBroadcast(intent);
        }
    }

    private void ExceuteSMSBroadCast(StatusBarNotification sbn){
        UserDetails ObjUserDetails = new UserDetails();
        if (sbn.getNotification().tickerText != null) {
            Ticker = sbn.getNotification().tickerText.toString();
        }
        extras = sbn.getNotification().extras;
        title= extras.getString("android.title").replaceAll("\\s+","");
        if (extras.getCharSequence("android.text") != null) {
            text = extras.getCharSequence("android.text").toString();
        }

        if(text !=null && title !=null){
            ObjUserDetails.setSMS(true);
            ObjUserDetails.setCallLog(title);
            ObjUserDetails.setCall(false);

//            ObjUserDetails.setUsername("Anuj");
//            ObjUserDetails.setMacId("a");

            ObjUserDetails.setAESToken(AESToken);
            ObjUserDetails.setMessage(text);
            Intent intent = new Intent("com.getnotify");
            intent.putExtra("GetnotifyData", ObjUserDetails);
            sendBroadcast(intent);
        }
    }

    public  boolean ValidateMobile(String Number) {
        return Pattern.compile("^(?:(?:\\+|0{0,2})91(\\s*[\\ -]\\s*)?|[0]?)?[789]\\d{9}|(\\d[ -]?){10}\\d$").matcher(Number).find();
    }
}
