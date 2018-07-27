package com.bluewebspark.happyservice.activity;

import android.app.Dialog;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bluewebspark.happyservice.R;
import com.bluewebspark.happyservice.data.datahelper.UserDataHelper;
import com.bluewebspark.happyservice.data.model.UserDataModel;
import com.bluewebspark.happyservice.sohel.ApiClient;
import com.bluewebspark.happyservice.sohel.ApiInterface;
import com.bluewebspark.happyservice.sohel.Helper;
import com.bluewebspark.happyservice.sohel.JSONParser;
import com.bluewebspark.happyservice.sohel.S;
import com.bluewebspark.happyservice.sohel.UserAccount;
import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Sohel Khan on 12-Apr-18.
 */

public class OtpVerificationActivity extends BaseActivity {
    @BindView(R.id.tool_bar)
    Toolbar tool_bar;
    @BindView(R.id.pinView)
    PinView pinView;
    @BindView(R.id.tvTimer)
    TextView tvTimer;
    @BindView(R.id.tvGetCodeOnCall)
    TextView tvGetCodeOnCall;
    @BindView(R.id.btnLogin)
    Button btnLogin;


    private Dialog progressDialog;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks verificationCallbacks;
    private PhoneAuthProvider.ForceResendingToken resendingToken;
    private String phoneVerificationId;
    private FirebaseAuth fbAuth;
    private String phoneNumber = "";

    @Override
    protected int getContentResId() {
        return R.layout.activity_otp_verification;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        fbAuth = FirebaseAuth.getInstance();

        if (getIntent().getExtras() != null)
            phoneNumber = "+" + getIntent().getStringExtra("country_code") + getIntent().getStringExtra("mobile_number");

        setToolbarWithBackButton("Enter Verification Code");

        startTimer();

        pinView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (pinView.getText().toString().length() == 6) {
                    btnLogin.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                } else {
                    btnLogin.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UserAccount.isEmpty(pinView)) {
                    if (pinView.getText().toString().length() == 6)
                        verefiyCode(pinView.getText().toString());
                }
            }
        });

        tvGetCodeOnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                S.T(OtpVerificationActivity.this, "Wait for Call");
            }
        });

//        progressDialog = S.initProgressDialog(OtpVerificationActivity.this);
        FirebaseAuth.getInstance().signOut();
        sendCode();
    }

    public void sendCode() {
        if (phoneNumber.equals("")) {
            phoneNumber = UserDataHelper.getInstance().getUserDataModel().get(0).getUserPhone();
        }
        S.E("phone number : " + phoneNumber);
        setUpVerificationCallbacks();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                60,
                TimeUnit.SECONDS,
                this,
                verificationCallbacks
        );
    }

    private void setUpVerificationCallbacks() {
        S.E("call");
        verificationCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                S.E("onVerificationCompleted : " + credential);
//                S.T(OtpVerificationActivity.this, "Already Exist.");
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                S.E("onVerificationFailed : " + e);
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                S.E("onCodeSent : " + verificationId);
                resendingToken = token;
                phoneVerificationId = verificationId;

//                progressDialog.dismiss();
            }
        };
    }

    public void verefiyCode(String code) {
        S.E("code : " + code);
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(phoneVerificationId, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.e("", "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
//                            do something when verify
//                            S.I_clear(OtpVerificationActivity.this, MainActivity.class, null);
                            loginWithPassword();
                        } else {
                            Log.e("", "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            }
                        }
                    }
                });
    }

    private void loginWithPassword() {
        new JSONParser(this).parseRetrofitRequest(ApiClient.getClient().create(ApiInterface.class).userLogin(
                phoneNumber,
                "",
                "customer"
        ), new Helper() {
            @Override
            public void backResponse(String response) {
                S.E("login response : " + response);
                if (!response.equals("error")) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getBoolean("status")) {
                            JSONArray data = jsonObject.getJSONArray("response");
                            JSONObject json = data.getJSONObject(0);
                            UserDataModel userDataModel = new UserDataModel();
                            userDataModel.setUserId(json.getString("ID"));
                            userDataModel.setUserName(json.getString("userName"));
                            userDataModel.setUserEmail(json.getString("userEmail"));
                            userDataModel.setUserPhone(json.getString("userPhone"));
                            userDataModel.setUserAlternatePhone(json.getString("userAlternatePhone"));
                            userDataModel.setUserType(json.getString("userType"));
                            userDataModel.setUserImage(json.getString("userImage"));
                            userDataModel.setUserAddress(json.getString("userAddress"));
                            userDataModel.setUserCity(json.getString("userCity"));
                            userDataModel.setUserLat(json.getString("userLat"));
                            userDataModel.setUserLong(json.getString("userLong"));
                            userDataModel.setUserBySocial(json.getString("userBySocial"));
                            userDataModel.setFcm_id(json.getString("userfirbaseID"));
                            userDataModel.setUserStatus(json.getString("userStatus"));

                            UserDataHelper.getInstance().insertUserDataModel(userDataModel);
                            S.I_clear(OtpVerificationActivity.this, ChooseLocationActivity.class, null);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    S.T(OtpVerificationActivity.this, "Try after some time.");
                }
            }
        });
    }

    private void startTimer() {
        CountDownTimer cT = new CountDownTimer(TimeUnit.SECONDS.toMillis(60), 1000) {
            public void onTick(long millisUntilFinished) {
                String v = String.format("%02d", millisUntilFinished / 60000);
                int va = (int) ((millisUntilFinished % 60000) / 1000);
                tvTimer.setText(v + ":" + String.format("%02d", va));
            }

            public void onFinish() {
                tvTimer.setText("00:00");
                tvGetCodeOnCall.setEnabled(true);
            }
        };
        cT.start();
    }
}