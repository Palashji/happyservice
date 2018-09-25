package com.bluewebspark.happyservice.activity;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bluewebspark.happyservice.R;
import com.bluewebspark.happyservice.sohel.S;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by abc on 07-Mar-18.
 */

public class MyProfileActivity extends BaseActivity {
    @BindView(R.id.tool_bar)
    Toolbar tool_bar;
    @BindView(R.id.userImage)
    CircleImageView userImage;
    @BindView(R.id.textUserName)
    TextView textUserName;
    @BindView(R.id.textUserEmail)
    TextView textUserEmail;
    @BindView(R.id.btnNextProfile)
    ImageView btnNextProfile;
    @BindView(R.id.cardMySubscription)
    CardView cardMySubscription;
    @BindView(R.id.cardMyTransactionHistory)
    CardView cardMyTransactionHistory;
    @BindView(R.id.cardHelpCenter)
    CardView cardHelpCenter;
    @BindView(R.id.cardProfile)
    CardView cardProfile;
    @BindView(R.id.btnLogout)
    Button btnLogout;

    @Override
    protected int getContentResId() {
        return R.layout.activity_my_profile;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        setToolbarWithBackButton("Profile");

        cardProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                S.I(MyProfileActivity.this, UpdateProfileActivity.class, null);
            }
        });
    }
}
