package com.bluewebspark.happyservice.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
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
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by abc on 07-Mar-18.
 */

public class SignUpActivity extends BaseActivity {
    @BindView(R.id.tvConutyCode)
    TextView tvConutyCode;
    @BindView(R.id.etMobileNumber)
    EditText etMobileNumber;
    @BindView(R.id.etName)
    EditText etName;
    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.btnNext)
    Button btnNext;
    @BindView(R.id.btnFb)
    LoginButton btnFb;
    @BindView(R.id.tool_bar)
    Toolbar tool_bar;
    @BindView(R.id.etPassword)
    EditText etPassword;

    private FusedLocationProviderClient mFusedLocationClient;
    private double lat;
    private double lng;
    private String address;
    private CallbackManager callbackManager;
    String socialLogin = "0";

    @Override
    protected int getContentResId() {
        return R.layout.activity_signup;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        setToolbarWithBackButton("SignUp");

        etMobileNumber.setText(getIntent().getStringExtra("mobile_number"));
        tvConutyCode.setText("+" + getIntent().getStringExtra("country_code"));

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            S.E("location : " + location.toString());
                            S.E("location lat: " + location.getLatitude());
                            S.E("location lng: " + location.getLongitude());

                            lat = location.getLatitude();
                            lng = location.getLongitude();

                            Geocoder geocoder = new Geocoder(SignUpActivity.this, Locale.getDefault());
                            try {
                                List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
                                address = addresses.get(0).getAddressLine(0);
                                S.E("address : " + addresses.get(0).getAddressLine(0));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserAccount.isEmpty(etMobileNumber, etName, etEmail, etPassword)) {
                    if (UserAccount.isPhoneLengthValid(etMobileNumber)) {
                        if (UserAccount.isEmailValid(etEmail)) {
                            signUp();
                        } else {
                            UserAccount.EditTextPointer.requestFocus();
                            UserAccount.EditTextPointer.setError("Invalid Email Address");
                        }
                    }
                } else {
                    UserAccount.EditTextPointer.requestFocus();
                    UserAccount.EditTextPointer.setError("This can't be empty");
                }
            }
        });

        callbackManager = CallbackManager.Factory.create();
        btnFb.setReadPermissions(Arrays.asList("email", "public_profile"));
        btnFb.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject me, GraphResponse response) {

                                if (response.getError() != null) {
                                    // handle error
                                } else {
                                    S.E("profile : " + me.optString("name"));
                                    S.E("profile : " + me.optString("email"));
                                    socialLogin = "1";
                                }
                            }
                        });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "name,email");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });
    }

    private void signUp() {
        new JSONParser(this).parseRetrofitRequest(ApiClient.getClient().create(ApiInterface.class).userRegistration(
                etName.getText().toString(),
                etEmail.getText().toString(),
                tvConutyCode.getText().toString() + etMobileNumber.getText().toString(),
                etPassword.getText().toString(),
                "customer",
                address,
                String.valueOf(lat),
                String.valueOf(lng),
                socialLogin,
                FirebaseInstanceId.getInstance().getToken()
        ), new Helper() {
            @Override
            public void backResponse(String response) {
                S.E("registration response : " + response);
                if (!response.equals("error")) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getBoolean("status")) {
                            JSONObject json = jsonObject.getJSONObject("response");
                            UserDataModel userDataModel = new UserDataModel();
                            userDataModel.setUserId(json.getString("user_id"));
                            userDataModel.setUserName(json.getString("userName"));
                            userDataModel.setUserEmail(json.getString("userEmail"));
                            userDataModel.setUserPhone(json.getString("userPhone"));
                            userDataModel.setUserAlternatePhone("");
                            userDataModel.setUserType(json.getString("userType"));
                            userDataModel.setUserImage("");
                            userDataModel.setUserAddress(json.getString("userAddress"));
                            userDataModel.setUserCity("");
                            userDataModel.setUserLat(json.getString("userLat"));
                            userDataModel.setUserLong(json.getString("userLong"));
                            userDataModel.setUserBySocial(json.getString("userBySocial"));
                            userDataModel.setFcm_id(json.getString("userfirbaseID"));
                            userDataModel.setUserStatus(json.getString("userStatus"));

                            UserDataHelper.getInstance().insertUserDataModel(userDataModel);
                            S.I_clear(SignUpActivity.this, OtpVerificationActivity.class, null);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    S.T(SignUpActivity.this, "Try Again.");
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}