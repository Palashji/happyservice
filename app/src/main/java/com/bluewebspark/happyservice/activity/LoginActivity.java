package com.bluewebspark.happyservice.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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

public class LoginActivity extends BaseActivity {
    @BindView(R.id.editMobile)
    EditText editMobile;
    @BindView(R.id.editPassword)
    EditText editPassword;
    @BindView(R.id.textForgotPassword)
    TextView textForgotPassword;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.textLoginWithOTP)
    TextView textLoginWithOTP;
    @BindView(R.id.btnRegister)
    FloatingActionButton btnRegister;

    @Override
    protected int getContentResId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        textLoginWithOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                S.I(LoginActivity.this, LoginWithOtpActivity.class, null);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                S.I_clear(LoginActivity.this, MainActivity.class, null);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                S.I(LoginActivity.this, SignUpActivity.class, null);
            }
        });
    }
}
