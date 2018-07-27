package com.bluewebspark.happyservice.activity;

import android.os.Bundle;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Sohel Khan on 23-Apr-18.
 */

public class LoginWithPasswordActivity extends BaseActivity {
    @BindView(R.id.tool_bar)
    Toolbar tool_bar;
    @BindView(R.id.tvConutyCode)
    TextView tvConutyCode;
    @BindView(R.id.etMobileNumber)
    EditText etMobileNumber;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.btnLogin)
    Button btnLogin;

    private String mobileNumber;

    @Override
    protected int getContentResId() {
        return R.layout.activity_login_with_password;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setToolbarWithBackButton("Login With Password");

        tvConutyCode.setText("+" + getIntent().getStringExtra("country_code"));
        etMobileNumber.setText(getIntent().getStringExtra("mobile_number"));

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UserAccount.isEmpty(etMobileNumber, etPassword))
                    loginWithPassword();
            }
        });
    }

    private void loginWithPassword() {
        new JSONParser(this).parseRetrofitRequest(ApiClient.getClient().create(ApiInterface.class).userLogin(
                tvConutyCode.getText().toString() + etMobileNumber.getText().toString(),
                etPassword.getText().toString(),
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
                            S.I_clear(LoginWithPasswordActivity.this, MainActivity.class, null);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    S.T(LoginWithPasswordActivity.this, "Try after some time.");
                }
            }
        });
    }
}
