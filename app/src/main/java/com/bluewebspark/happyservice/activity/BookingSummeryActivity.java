package com.bluewebspark.happyservice.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bluewebspark.happyservice.R;
import com.bluewebspark.happyservice.data.datahelper.UserDataHelper;
import com.bluewebspark.happyservice.sohel.ApiClient;
import com.bluewebspark.happyservice.sohel.ApiInterface;
import com.bluewebspark.happyservice.sohel.Const;
import com.bluewebspark.happyservice.sohel.Helper;
import com.bluewebspark.happyservice.sohel.JSONParser;
import com.bluewebspark.happyservice.sohel.S;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Sohel Khan on 31-Mar-18.
 */

public class BookingSummeryActivity extends BaseActivity {
    @BindView(R.id.tool_bar)
    Toolbar tool_bar;
    @BindView(R.id.serviceImage)
    ImageView serviceImage;
    @BindView(R.id.tvServiceName)
    TextView tvServiceName;
    @BindView(R.id.tvServicePrice)
    TextView tvServicePrice;
    @BindView(R.id.tvDate)
    TextView tvDate;
    @BindView(R.id.tvTime)
    TextView tvTime;
    @BindView(R.id.tvLocation)
    TextView tvLocation;
    @BindView(R.id.tvAddress)
    TextView tvAddress;
    @BindView(R.id.btnConfirm)
    Button btnConfirm;

    String service_id;
    String price;
    String address;
    String landmark;
    String city;
    String date;
    String time;
    String lat;
    String lng;
    private String service_image;
    private String service_name;
    private String building_detail;

    @Override
    protected int getContentResId() {
        return R.layout.activity_booking_summery;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        setToolbarWithBackButton("Booking Summery");

        service_id = getIntent().getStringExtra("service_id");
        price = getIntent().getStringExtra("price");
        building_detail = getIntent().getStringExtra("building_detail");
        landmark = getIntent().getStringExtra("landmark");
        city = getIntent().getStringExtra("city");
        address = getIntent().getStringExtra("address");
        date = getIntent().getStringExtra("date");
        time = getIntent().getStringExtra("time");
        lat = getIntent().getStringExtra("lat");
        lng = getIntent().getStringExtra("lng");
        service_image = getIntent().getStringExtra("service_image");
        service_name = getIntent().getStringExtra("service_name");

        tvServiceName.setText(service_name);
        tvServicePrice.setText(price);
        tvDate.setText(date);
        tvTime.setText(time);
        tvAddress.setText(building_detail + "\n" + address);
        S.setImageByPicaso(BookingSummeryActivity.this, Const.URL.SERVICES_IMAGES + service_image, serviceImage, null);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserDataHelper.getInstance().getUserDataModel().size() > 0)
                    bookService();
                else
                    S.I(BookingSummeryActivity.this, GetOtpActivity.class, null);
            }
        });
    }

    private void bookService() {
        new JSONParser(this).parseRetrofitRequest(ApiClient.getClient().create(ApiInterface.class).serviceBooking(
                service_id,
                UserDataHelper.getInstance().getUserDataModel().get(0).getUserId(),
                price,
                building_detail + " , " + address,
                landmark,
                city,
                date,
                time,
                String.valueOf(lat),
                String.valueOf(lng),
                "1"
        ), new Helper() {
            @Override
            public void backResponse(String response) {
                S.E("service booking response : " + response);
                if (!response.equals("error")) {
                    S.I_clear(BookingSummeryActivity.this, MainActivity.class, null);
                    S.T(BookingSummeryActivity.this, "Booking Successful, Please wait for response.");
                } else {
                    S.T(BookingSummeryActivity.this, "Try after some time.");
                }
            }
        });
    }
}