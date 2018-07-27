package com.bluewebspark.happyservice.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bluewebspark.happyservice.R;
import com.bluewebspark.happyservice.sohel.S;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChooseLocationActivity extends BaseActivity {
    @BindView(R.id.layoutCurrentLocation)
    LinearLayout layoutCurrentLocation;
    @BindView(R.id.tvSelectYourCity)
    TextView tvSelectYourCity;

    @Override
    protected int getContentResId() {
        return R.layout.activity_choose_location;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        layoutCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                S.I_clear(ChooseLocationActivity.this, MainActivity.class, null);
            }
        });

        tvSelectYourCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                S.I(ChooseLocationActivity.this, SelectCityActivity.class, null);
            }
        });
    }
}
