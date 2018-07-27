package com.bluewebspark.happyservice.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bluewebspark.happyservice.R;
import com.bluewebspark.happyservice.sohel.S;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by abc on 07-Mar-18.
 */

public class LoginWithOtpActivity extends BaseActivity {
    @BindView(R.id.editMobile)
    EditText editMobile;
    @BindView(R.id.editOtp)
    EditText editOtp;
    @BindView(R.id.inputLayoutMobile)
    TextInputLayout inputLayoutMobile;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.textLoginWithPassword)
    TextView textLoginWithPassword;
    @BindView(R.id.btnRegister)
    FloatingActionButton btnRegister;

    @Override
    protected int getContentResId() {
        return R.layout.activity_login_with_otp;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnLogin.getText().toString().equals("GET OTP")) {
                    btnLogin.setText("VERIFY");
                    editOtp.setVisibility(View.VISIBLE);
                } else if (btnLogin.getText().toString().equals("VERIFY")) {
                    S.I_clear(LoginWithOtpActivity.this, MainActivity.class, null);
                }
            }
        });

        textLoginWithPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                S.I_clear(LoginWithOtpActivity.this, LoginActivity.class, null);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                S.I(LoginWithOtpActivity.this, SignUpActivity.class, null);
            }
        });
    }
}