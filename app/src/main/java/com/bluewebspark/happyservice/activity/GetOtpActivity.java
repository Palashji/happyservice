package com.bluewebspark.happyservice.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bluewebspark.happyservice.R;
import com.bluewebspark.happyservice.sohel.ApiClient;
import com.bluewebspark.happyservice.sohel.ApiInterface;
import com.bluewebspark.happyservice.sohel.Helper;
import com.bluewebspark.happyservice.sohel.JSONParser;
import com.bluewebspark.happyservice.sohel.S;
import com.bluewebspark.happyservice.sohel.UserAccount;
import com.facebook.login.widget.LoginButton;
import com.rilixtech.CountryCodePicker;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Sohel Khan on 12-Apr-18.
 */

public class GetOtpActivity extends BaseActivity {

    @BindView(R.id.ccp)
    CountryCodePicker ccp;
    @BindView(R.id.etMobileNumber)
    EditText etMobileNumber;
    @BindView(R.id.btnWithPassword)
    Button btnWithPassword;
    @BindView(R.id.btnWithOtp)
    Button btnWithOtp;
    @BindView(R.id.btnFb)
    LoginButton btnFb;
    @BindView(R.id.tvSkip)
    TextView tvSkip;

    @Override
    protected int getContentResId() {
        return R.layout.activity_get_otp;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        etMobileNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etMobileNumber.getText().toString().length() == 10) {
                    btnWithOtp.setBackground(getResources().getDrawable(R.drawable.btn_shape_active));
                    btnWithPassword.setBackground(getResources().getDrawable(R.drawable.btn_shape_active));
                } else {
                    btnWithOtp.setBackground(getResources().getDrawable(R.drawable.btn_shape_non_active));
                    btnWithPassword.setBackground(getResources().getDrawable(R.drawable.btn_shape_non_active));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        btnWithOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserAccount.isEmpty(etMobileNumber)) {
                    if (UserAccount.isPhoneLengthValid(etMobileNumber)) {
                        /*Bundle bundle = new Bundle();
                        bundle.putString("country_code", ccp.getSelectedCountryCode());
                        bundle.putString("mobile_number", etMobileNumber.getText().toString());
                        S.I(GetOtpActivity.this, SignUpActivity.class, bundle);*/
                        checkUserExist("OTP");
                    }
                }
            }
        });

        btnWithPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UserAccount.isEmpty(etMobileNumber)) {
                    /*Bundle bundle = new Bundle();
                    bundle.putString("country_code", ccp.getSelectedCountryCode());
                    bundle.putString("mobile_number", etMobileNumber.getText().toString());
                    S.I(GetOtpActivity.this, LoginWithPasswordActivity.class, bundle);*/
                    checkUserExist("Password");
                }
            }
        });

        tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                S.I_clear(GetOtpActivity.this, MainActivity.class, null);
            }
        });
    }

    private void checkUserExist(final String loginWith) {
        S.E("prams : " + ccp.getSelectedCountryCode() + etMobileNumber.getText().toString() + " 1 " + " customer");
        new JSONParser(this).parseRetrofitRequest(ApiClient.getClient().create(ApiInterface.class).userLogin(
                "+" + ccp.getSelectedCountryCode() + etMobileNumber.getText().toString(),
                "",
                "customer"
        ), new Helper() {
            @Override
            public void backResponse(String response) {
                S.E("check user exist : " + response);
                try {
                    if (response.equals("error")) {
                        Bundle bundle = new Bundle();
                        bundle.putString("country_code", ccp.getSelectedCountryCode());
                        bundle.putString("mobile_number", etMobileNumber.getText().toString());
                        S.I(GetOtpActivity.this, SignUpActivity.class, bundle);
                    } else {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getBoolean("status")) {
                            if (loginWith.equals("OTP")) {
                                Bundle bundle = new Bundle();
                                bundle.putString("country_code", ccp.getSelectedCountryCode());
                                bundle.putString("mobile_number", etMobileNumber.getText().toString());
                                S.I(GetOtpActivity.this, OtpVerificationActivity.class, bundle);
                            } else {
                                Bundle bundle = new Bundle();
                                bundle.putString("country_code", ccp.getSelectedCountryCode());
                                bundle.putString("mobile_number", etMobileNumber.getText().toString());
                                S.I(GetOtpActivity.this, LoginWithPasswordActivity.class, bundle);
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
