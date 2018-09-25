package com.bluewebspark.happyservice.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.bluewebspark.happyservice.R;
import com.bluewebspark.happyservice.data.datahelper.UserDataHelper;
import com.bluewebspark.happyservice.sohel.S;
import com.google.firebase.iid.FirebaseInstanceId;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * Created by abc on 07-Mar-18.
 */
/*public class SplashActivity extends BaseActivity {
    @Override
    protected int getContentResId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        PrintHashKey();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (UserDataHelper.getInstance().getUserDataModel().size() > 0) {
                    S.I(SplashActivity.this, MainActivity.class, null);
                    finish();
                } else {
                    S.I(SplashActivity.this, GetOtpActivity.class, null);
                    finish();
                }
            }
        }, 1000);
    }

    private void PrintHashKey() {

        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(),
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }
}*/

/**
 * Created by abc on 07-Mar-18.
 */

public class SplashActivity extends AppCompatActivity {

    private static final long SPLASH_TIME_OUT = 2000;
    private String messageBY;
    private String requestID;
    private String serviceStatus;
    private String serviceName;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        PrintHashKey();
        CheakPermissions();
        Log.e("Firebase", "token " + FirebaseInstanceId.getInstance().getToken());
    }

    private void CheakPermissions() {
        if (checkPermission()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (UserDataHelper.getInstance().getUserDataModel().size() > 0) {
                        if (getIntent().getExtras() != null) {
                            S.E("intent called " + getIntent().getStringExtra("message"));
//                            for (String key : getIntent().getExtras().keySet()) {

                            String action_destination = getIntent().getStringExtra("action_destination");
/*
                            switch (action_destination) {
                                case "chatMessage":
                                    messageBY = getIntent().getStringExtra("messageBY");
                                    requestID = getIntent().getStringExtra("requestID");
                                    serviceStatus = getIntent().getStringExtra("serviceStatus");
                                    serviceName = getIntent().getStringExtra("serviceName");
                                    userName = getIntent().getStringExtra("userName");
                                    Bundle bundle = new Bundle();
                                    bundle.putString("vendor_id", String.valueOf(messageBY));
                                    bundle.putString("request_id", String.valueOf(requestID));
                                    bundle.putString("service_name", String.valueOf(serviceName));
                                    bundle.putString("vendor_name", String.valueOf(userName));
                                    bundle.putString("vendor_status", String.valueOf(serviceStatus));
                                    S.I(SplashActivity.this, ConversationActivity.class, bundle);
                                    finish();
                                    break;
                                case "acceptBooking":
                                    requestID = getIntent().getStringExtra("requestID");
                                    serviceName = getIntent().getStringExtra("serviceName");
                                    userName = getIntent().getStringExtra("userName");
                                    Bundle bundle1 = new Bundle();
                                    bundle1.putString("request_id", requestID);
                                    S.I(SplashActivity.this, OngoingDetailsActivity.class, bundle1);
                                    break;
                                case "CompletedByVendor":
                                    requestID = getIntent().getStringExtra("requestID");
                                    serviceName = getIntent().getStringExtra("serviceName");
                                    userName = getIntent().getStringExtra("userName");
                                    Bundle bundle2 = new Bundle();
                                    bundle2.putString("request_id", requestID);
                                    S.I(SplashActivity.this, OngoingDetailsActivity.class, bundle2);
                                    break;
                                case "reviewLeft":
                                    S.I(SplashActivity.this, MainActivity.class, null);
                                    break;
                            }
*/
                            S.I(SplashActivity.this, MainActivity.class, null);
                                /*if (key.equals("action_destination")) {
                                    if (value.equals("chatMessage")) {

                                        S.I(SplashActivity.this, ChatActivity.class, null);
                                    }
                                }
                                Log.e("MainActivity: ", "Key: " + key + " Value: " + value);*/
//                            }
                        } else {
                            S.I(SplashActivity.this, MainActivity.class, null);
                            finish();
                        }
                    } else {
                        S.I(SplashActivity.this, GetOtpActivity.class, null);
                        finish();
                    }

                }
            }, SPLASH_TIME_OUT);
        } else {
            requestPermission();
        }
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(SplashActivity.this, new String[]
                {
                        ACCESS_FINE_LOCATION,
                        WRITE_EXTERNAL_STORAGE,
                        CAMERA

                }, 1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    boolean ACCESSFINELOCATION = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean ACCESSSTORAGE = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean ACCESSCAMERA = grantResults[2] == PackageManager.PERMISSION_GRANTED;

                    if (ACCESSFINELOCATION && ACCESSSTORAGE && ACCESSCAMERA) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (UserDataHelper.getInstance().getUserDataModel().size() > 0) {
                                    S.I(SplashActivity.this, MainActivity.class, null);
                                    finish();
                                } else {
                                    S.I(SplashActivity.this, GetOtpActivity.class, null);
                                    finish();
                                }
                            }
                        }, SPLASH_TIME_OUT);
                    } else {
                        Toast.makeText(SplashActivity.this, "Permission Denied", Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }

    public boolean checkPermission() {
        int FirstPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION);
        int ThirdPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int FourthPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
        return FirstPermissionResult == PackageManager.PERMISSION_GRANTED &&
                ThirdPermissionResult == PackageManager.PERMISSION_GRANTED &&
                FourthPermissionResult == PackageManager.PERMISSION_GRANTED;
    }

    private void PrintHashKey() {

        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(),
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}