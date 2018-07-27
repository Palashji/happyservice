package com.bluewebspark.happyservice.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bluewebspark.happyservice.R;
import com.bluewebspark.happyservice.sohel.S;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by abc on 07-Mar-18.
 */

public class SearchLocationActivity extends BaseActivity {
    @BindView(R.id.editSearch)
    EditText editSearch;
    @BindView(R.id.tool_bar)
    Toolbar tool_bar;
    @BindView(R.id.locationImage)
    ImageView locationImage;
    @BindView(R.id.layoutMyLocation)
    RelativeLayout layoutMyLocation;

    @Override
    protected int getContentResId() {
        return R.layout.activity_search_location;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setToolbarWithBackButton("");

        layoutMyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                S.T(SearchLocationActivity.this, "Coming soon...");
            }
        });
    }
}
