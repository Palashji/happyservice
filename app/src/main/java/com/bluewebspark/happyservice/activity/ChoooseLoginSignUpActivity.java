package com.bluewebspark.happyservice.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bluewebspark.happyservice.R;
import com.bluewebspark.happyservice.sohel.S;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by abc on 21-Mar-18.
 */

public class ChoooseLoginSignUpActivity extends BaseActivity {
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.btnSignUp)
    Button btnSignUp;

    @Override
    protected int getContentResId() {
        return R.layout.activity_choose_login_signup;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                S.I(ChoooseLoginSignUpActivity.this, LoginActivity.class, null);
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                S.I(ChoooseLoginSignUpActivity.this, SignUpActivity.class, null);
            }
        });
    }
}
