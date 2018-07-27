package com.bluewebspark.happyservice.activity;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bluewebspark.happyservice.R;
import com.bluewebspark.happyservice.adapter.ReviewAdapter;
import com.bluewebspark.happyservice.model.ReviewModel;
import com.bluewebspark.happyservice.sohel.ApiClient;
import com.bluewebspark.happyservice.sohel.ApiInterface;
import com.bluewebspark.happyservice.sohel.Helper;
import com.bluewebspark.happyservice.sohel.JSONParser;
import com.bluewebspark.happyservice.sohel.S;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Sohel Khan on 03-May-18.
 */

public class UserDetailsActivity extends BaseActivity {
    @BindView(R.id.tool_bar)
    Toolbar tool_bar;
    @BindView(R.id.userImage)
    CircleImageView userImage;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvEmail)
    TextView tvEmail;
    @BindView(R.id.tvAddress)
    TextView tvAddress;
    @BindView(R.id.mainLayout)
    CardView mainLayout;
    @BindView(R.id.tvReview)
    TextView tvReview;
    @BindView(R.id.tvRating)
    TextView tvRating;
    @BindView(R.id.layoutRating)
    RelativeLayout layoutRating;
    @BindView(R.id.recyclerViewReview)
    RecyclerView recyclerViewReview;

    private String user_id;
    ArrayList<ReviewModel> arrayListReview = new ArrayList<>();

    @Override
    protected int getContentResId() {
        return R.layout.activity_user_details;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        setToolbarWithBackButton("HS Partner Details");

        user_id = getIntent().getStringExtra("user_id");

        getUserDetails();
    }

    private void getUserDetails() {
        new JSONParser(this).parseRetrofitRequest(ApiClient.getClient().create(ApiInterface.class).getUseretails(
                user_id
        ), new Helper() {
            @Override
            public void backResponse(String response) {
                S.E("user details response : " + response);
                if (!response.equals("error")) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONObject data = jsonObject.getJSONObject("response");
                        tvName.setText(data.getString("userName"));
                        tvEmail.setText(data.getString("userEmail"));
                        tvAddress.setText("Address: " + data.getString("userAddress"));
                        tvRating.setText("(" + data.getString("averageRating") + ")");
                        S.setImageByPicaso(UserDetailsActivity.this, data.getString("userImage"), userImage, null);
                        JSONArray review = data.getJSONArray("review");
                        for (int i = 0; i < review.length(); i++) {
                            JSONObject json = review.getJSONObject(i);
                            ReviewModel reviewModel = new ReviewModel();
                            reviewModel.setReviewBy(json.getString("reviewBy"));
                            reviewModel.setRatingNumber(json.getString("ratingNumber"));
                            reviewModel.setReviewComment(json.getString("reviewComment"));
                            reviewModel.setVendorName(json.getString("vendorName"));
                            reviewModel.setVendorImage(json.getString("vendorImage"));
                            arrayListReview.add(reviewModel);
                        }
                        LinearLayoutManager layoutManager = new LinearLayoutManager(UserDetailsActivity.this);
                        recyclerViewReview.setLayoutManager(layoutManager);
                        recyclerViewReview.addItemDecoration(new DividerItemDecoration(UserDetailsActivity.this, layoutManager.getOrientation()));
                        ReviewAdapter reviewAdapter = new ReviewAdapter(UserDetailsActivity.this, arrayListReview);
                        recyclerViewReview.setAdapter(reviewAdapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
