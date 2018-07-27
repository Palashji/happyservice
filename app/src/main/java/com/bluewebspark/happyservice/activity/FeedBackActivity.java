package com.bluewebspark.happyservice.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bluewebspark.happyservice.R;
import com.bluewebspark.happyservice.data.datahelper.UserDataHelper;
import com.bluewebspark.happyservice.sohel.ApiClient;
import com.bluewebspark.happyservice.sohel.ApiInterface;
import com.bluewebspark.happyservice.sohel.Helper;
import com.bluewebspark.happyservice.sohel.JSONParser;
import com.bluewebspark.happyservice.sohel.S;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Sohel Khan on 30-Apr-18.
 */

public class FeedBackActivity extends BaseActivity {
    @BindView(R.id.tool_bar)
    Toolbar tool_bar;
    @BindView(R.id.jobImage)
    ImageView jobImage;
    @BindView(R.id.tvServiceStatus)
    TextView tvServiceStatus;
    @BindView(R.id.ratingBar)
    RatingBar ratingBar;
    @BindView(R.id.etComment)
    EditText etComment;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    private String user_id;
    private String request_id;

    @Override
    protected int getContentResId() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setToolbarWithBackButton("Rating");

        request_id = getIntent().getStringExtra("request_id");
        user_id = getIntent().getStringExtra("user_id");

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ratingBar.getRating() > 0) {
                    submitRating();
                }
            }
        });
    }

    private void submitRating() {
        S.E("prams : " + request_id + " , " + UserDataHelper.getInstance().getUserDataModel().get(0).getUserId() + " , " + user_id + " , " + etComment.getText().toString() + " , " + ratingBar.getRating());
        new JSONParser(this).parseRetrofitRequest(ApiClient.getClient().create(ApiInterface.class).submitReview(
                request_id,
                UserDataHelper.getInstance().getUserDataModel().get(0).getUserId(),
                user_id,
                etComment.getText().toString(),
                ratingBar.getRating()
        ), new Helper() {
            @Override
            public void backResponse(String response) {
                S.E("submit rating response : " + response);
                if (!response.equals("error")) {
                    S.T(FeedBackActivity.this, "Feedback submitted successfully.");
                    S.I_clear(FeedBackActivity.this,MainActivity.class,null);
                }
            }
        });
    }
}
