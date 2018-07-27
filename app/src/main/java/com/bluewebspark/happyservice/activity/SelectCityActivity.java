package com.bluewebspark.happyservice.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bluewebspark.happyservice.R;
import com.bluewebspark.happyservice.sohel.S;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectCityActivity extends BaseActivity {
    @BindView(R.id.layoutCurrentLocation)
    LinearLayout layoutCurrentLocation;
    @BindView(R.id.tvIndore)
    TextView tvIndore;

    @Override
    protected int getContentResId() {
        return R.layout.activity_select_city;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        layoutCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                S.I_clear(SelectCityActivity.this, MainActivity.class, null);
            }
        });

        tvIndore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("city", "Indore");
                S.I_clear(SelectCityActivity.this, MainActivity.class, bundle);
            }
        });
    }
}
