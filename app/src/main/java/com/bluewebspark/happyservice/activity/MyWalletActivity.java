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

public class MyWalletActivity extends BaseActivity {
    @BindView(R.id.tool_bar)
    Toolbar tool_bar;
    @BindView(R.id.tvAmount)
    TextView tvAmount;

    @Override
    protected int getContentResId() {
        return R.layout.activity_my_wallet;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setToolbarWithBackButton("My Wallet");
    }
}