package com.bluewebspark.happyservice.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.bluewebspark.happyservice.R;
import com.bluewebspark.happyservice.sohel.S;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by abc on 21-Mar-18.
 */

public class HelpActivity extends BaseActivity {
    @BindView(R.id.tool_bar)
    Toolbar tool_bar;
    @BindView(R.id.tvContactUs)
    TextView tvContactUs;
    @BindView(R.id.tvFaqTerms)
    TextView tvFaqTerms;
    @BindView(R.id.tvPrivacyPolicy)
    TextView tvPrivacyPolicy;
    @BindView(R.id.tvAboutUS)
    TextView tvAboutUS;

    @Override
    protected int getContentResId() {
        return R.layout.activity_help;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        setToolbarWithBackButton("Help");

        tvContactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                S.I(HelpActivity.this, ContactUsActivity.class, null);
            }
        });
    }
}
