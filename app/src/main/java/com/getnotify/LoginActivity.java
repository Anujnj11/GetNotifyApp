package com.getnotify;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private EditText _UserText,_passwordText;
    private  TextView _loginButton,_signupLink;
    private String Password=null,email=null,AESToken = null;
    private UserAESTokenReq ObjUserAESTokenReq = null;
    private Dialog myDialog;
    private  static String PKGMESSAGING = null,PKGCall=null;
    FloatingActionButton fab;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        myDialog = new Dialog(this);
        GetDefaultSMS();
        GetDefaultDialer(this);
        fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDiag();
            }
        });
//        //If UserIs Logged in Navigate to home
//        if(Password != null && email !=null)
//        {
//            NavigatetoHome();
//        }
//    else {
//            _loginButton = (Button) findViewById(R.id.btn_login);
//            _UserText = (EditText) findViewById(R.id.input_email);
//            _loginButton = (Button) findViewById(R.id.btn_login);
//            _loginButton.setOnClickListener(new View.OnClickListener() {
//
//                @Override
//                public void onClick(View v) {
//                    login();
//                }
//            });
//        }
    }


    private void showDiag() {
        final View dialogView = View.inflate(this,R.layout.loginform,null);
        final Dialog dialog = new Dialog(this,R.style.MyAlertDialogStyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogView);
            _loginButton = (Button) dialog.findViewById(R.id.btn_login);
            _UserText = (EditText) dialog.findViewById(R.id.input_email);
            _loginButton = (Button) dialog.findViewById(R.id.btn_login);
            _loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    login();
                }
            });
        ImageView imageView = (ImageView)dialog.findViewById(R.id.closeDialogImg);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                revealShow(dialogView, false, dialog);
            }
        });

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                revealShow(dialogView, true, null);
            }
        });

        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_BACK){

                    revealShow(dialogView, false, dialog);
                    return true;
                }

                return false;
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog.show();
    }

    private void revealShow(View dialogView, boolean b, final Dialog dialog) {
        final View view = dialogView.findViewById(R.id.dialog);
        int w = view.getWidth();
        int h = view.getHeight();
        int endRadius = (int) Math.hypot(w, h);
        int cx = (int) (fab.getX() + (fab.getWidth()/2));
        int cy = (int) (fab.getY())+ fab.getHeight() + 56;
        if(b)
        {
            Animator revealAnimator = ViewAnimationUtils.createCircularReveal(view, cx,cy, 0, endRadius);
            view.setVisibility(View.VISIBLE);
            revealAnimator.setDuration(700);
            revealAnimator.start();

        } else {

            Animator anim =
                    ViewAnimationUtils.createCircularReveal(view, cx, cy, endRadius, 0);

            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    dialog.dismiss();
                    view.setVisibility(View.INVISIBLE);

                }
            });
            anim.setDuration(700);
            anim.start();
        }

    }

    public void login()
    {
        Log.d(TAG, "Login");
        if (!validate()) {
            onLoginFailed();
            return;
        }
        _loginButton.setEnabled(false);
            ShowLoader();
         email = _UserText.getText().toString().trim();
        try
        {
            ObjUserAESTokenReq = new UserAESTokenReq();
            byte[] data = email.getBytes("UTF-8");
//            ObjUserAESTokenReq.setUsername(Base64.encodeToString(data, Base64.DEFAULT));
            ObjUserAESTokenReq.setUsername(email);
            onLoginSuccess();
//            Toast.makeText(getBaseContext(), email, Toast.LENGTH_LONG).show();
            ObjUserAESTokenReq.setPassword(GeneratePassword());
//            Toast.makeText(getBaseContext(), Password,Toast.LENGTH_LONG).show();
            GetAESToken(ObjUserAESTokenReq);

        }
        catch (Exception e){
            onLoginFailed();
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        _loginButton.setEnabled(true);
    }

    private String GeneratePassword(){
        String RandomPassword = "";
        try
        {
            int passwordSize = 6;
            char[] chars = "0123456789".toCharArray();
            StringBuilder sb = new StringBuilder();
            Random random = new Random();
            for (int i = 0; i < passwordSize; i++) {
                char c = chars[random.nextInt(chars.length)];
                sb.append(c);
            }
            RandomPassword = sb.toString();
        }
        catch (Exception e)
        {

        }
        return RandomPassword;
    }

    public void GetAESToken(UserAESTokenReq ObjUserAESTokenReq)
    {
        try
        {
            Password=ObjUserAESTokenReq.getPassword();
            email=ObjUserAESTokenReq.getUsername();
            Call<GetEncrptokenRes> objCallAPI = ConsumeAPI.postLog().GetAESToken(ObjUserAESTokenReq);
            objCallAPI.enqueue(new Callback<GetEncrptokenRes>() {
                @Override
                public void onResponse(Call<GetEncrptokenRes> call, Response<GetEncrptokenRes> response) {
                    GetEncrptokenRes ObjLogDetailResponse = response.body();
                    Log.i("AESToken : ", ObjLogDetailResponse.getAESToken());
                    AESToken = ObjLogDetailResponse.getAESToken();
                    Intent intent = new Intent(getApplicationContext(), NotificationService.class);
                    intent.putExtra("email", email);
                    intent.putExtra("password",Password);
                    intent.putExtra("AESToken",AESToken);
                    intent.putExtra("MESSAGING",PKGMESSAGING);
                    intent.putExtra("CALL",PKGCall);
                    //starting service
                    startService(intent);
//                    progressDialog.dismiss();
                    myDialog.dismiss();
                    NavigatetoHome();
                }
                @Override
                public void onFailure(Call<GetEncrptokenRes> call, Throwable t) {
                    Log.i("myApp", "error:" + t);
//                        Toast.makeText(LocationBasedActivity.this,"Error",Toast.LENGTH_LONG).show();
                }
            });
        }
        catch (Exception e)
        {

        }
    }


    public boolean validate()
    {
        boolean valid = true;
        String email = _UserText.getText().toString();
        if (email.isEmpty()) {
            _UserText.setError("enter a valid username");
            valid = false;
        } else {
            _UserText.setError(null);
        }
        return valid;
    }

    public  void ShowLoader(){
        myDialog.setContentView(R.layout.avloadingindicatorview);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

    public void NavigatetoHome()
    {
        //Navigate to home with password.
        Intent objHomeAc = new Intent(getApplicationContext(),HomeActivity.class);
        objHomeAc.putExtra("Password",Password);
        startActivity(objHomeAc);
    }

    public void GetDefaultSMS(){
        String defApp = Settings.Secure.getString(getContentResolver(), "sms_default_application");
        PackageManager pm = getApplicationContext().getPackageManager();
        Intent iIntent = pm.getLaunchIntentForPackage(defApp);
        ResolveInfo mInfo = pm.resolveActivity(iIntent,0);
        PKGMESSAGING = mInfo.activityInfo.packageName;
        Log.i(TAG, "apk: " + PKGMESSAGING);

    }

    public void GetDefaultDialer(Context context){
        try{
        List<String> packageNames = new ArrayList<>();
        // Declare action which target application listen to initiate phone call
        final Intent intent = new Intent();
        intent.setAction(Intent.ACTION_DIAL);
        // Query for all those applications
        List<ResolveInfo> resolveInfos = context.getPackageManager().queryIntentActivities(intent, 0);
        // Read package name of all those applications
        for(ResolveInfo resolveInfo : resolveInfos){
            ActivityInfo activityInfo = resolveInfo.activityInfo;
            packageNames.add(activityInfo.applicationInfo.packageName);
        }
        PKGCall = packageNames.size() > 0 ? packageNames.get(0) : null;
    }
    catch (Exception e){
    }
    }
}
