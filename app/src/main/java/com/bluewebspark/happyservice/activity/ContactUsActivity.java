package com.bluewebspark.happyservice.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;

import com.bluewebspark.happyservice.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by abc on 21-Mar-18.
 */

public class ContactUsActivity extends BaseActivity {
    @BindView(R.id.tool_bar)
    Toolbar tool_bar;
    @BindView(R.id.btnCall)
    Button btnCall;
    @BindView(R.id.etName)
    EditText etName;
    @BindView(R.id.etPhoneNumber)
    EditText etPhoneNumber;
    @BindView(R.id.etEmailAddress)
    EditText etEmailAddress;
    @BindView(R.id.etQuery)
    EditText etQuery;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;

    @Override
    protected int getContentResId() {
        return R.layout.activity_contact_us;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setToolbarWithBackButton("Contact Us");
    }
}
