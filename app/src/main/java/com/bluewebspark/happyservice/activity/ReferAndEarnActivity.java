package com.bluewebspark.happyservice.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.bluewebspark.happyservice.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by abc on 21-Mar-18.
 */

public class ReferAndEarnActivity extends BaseActivity {
    @BindView(R.id.tool_bar)
    Toolbar tool_bar;
    @BindView(R.id.tvReferCode)
    TextView tvReferCode;
    @BindView(R.id.tvShareCode)
    TextView tvShareCode;
    @BindView(R.id.tvHowItWorks)
    TextView tvHowItWorks;

    @Override
    protected int getContentResId() {
        return R.layout.activity_refer_earn;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        setToolbarWithBackButton("Invite & Earn");
    }
}