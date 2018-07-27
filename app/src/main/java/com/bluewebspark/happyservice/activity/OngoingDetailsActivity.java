package com.bluewebspark.happyservice.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bluewebspark.happyservice.R;
import com.bluewebspark.happyservice.adapter.BookingsVendorAdapter;
import com.bluewebspark.happyservice.data.datahelper.UserDataHelper;
import com.bluewebspark.happyservice.listeners.CustomButtonListener;
import com.bluewebspark.happyservice.model.BookingsVendorModel;
import com.bluewebspark.happyservice.sohel.ApiClient;
import com.bluewebspark.happyservice.sohel.ApiInterface;
import com.bluewebspark.happyservice.sohel.Const;
import com.bluewebspark.happyservice.sohel.Helper;
import com.bluewebspark.happyservice.sohel.JSONParser;
import com.bluewebspark.happyservice.sohel.S;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Sohel Khan on 29-Mar-18.
 */

public class OngoingDetailsActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, CustomButtonListener {
    @BindView(R.id.tool_bar)
    Toolbar tool_bar;
    @BindView(R.id.bookingImage)
    ImageView bookingImage;
    @BindView(R.id.tvServiceName)
    TextView tvServiceName;
    @BindView(R.id.tvLocation)
    TextView tvLocation;
    @BindView(R.id.tvDateTime)
    TextView tvDateTime;
    @BindView(R.id.tvApproximateTime)
    TextView tvApproximateTime;
    @BindView(R.id.tvCancelThisJob)
    TextView tvCancelThisJob;
    @BindView(R.id.recycleviewVendorList)
    RecyclerView recycleviewVendorList;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipe_refresh_layout;
    @BindView(R.id.tvNoData)
    TextView tvNoData;
    @BindView(R.id.btnMarkAsComplete)
    Button btnMarkAsComplete;
    private String request_id;
    ArrayList<BookingsVendorModel> arrayListVendor = new ArrayList<>();

    @Override
    protected int getContentResId() {
        return R.layout.activity_order_details;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setToolbarWithBackButton("Order Details");

        request_id = getIntent().getStringExtra("request_id");

        tvCancelThisJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmationPopupForCancelRequest();
            }
        });

        btnMarkAsComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                completeService();
            }
        });
    }

    private void completeService() {
        new JSONParser(this).parseRetrofitRequest(ApiClient.getClient().create(ApiInterface.class).change_request_status(
                request_id,
                "3" // 3= complete this job
        ), new Helper() {
            @Override
            public void backResponse(String response) {
                S.E("complete request response : " + response);
                if (!response.equals("error")) {
                    S.T(OngoingDetailsActivity.this, "Your Service Request Completed.");
//                    finish();
                    String vendor_id = "";
                    for (int i = 0; i < arrayListVendor.size(); i++) {
                        if (arrayListVendor.get(i).getVendorStatus().equals("4")) {
                            vendor_id = arrayListVendor.get(i).getVendorID();
                            S.E("vendor _id  1  : : : " + arrayListVendor.get(i).getVendorID());
                        }
                        S.E("vendor _id  2  : : : " + arrayListVendor.get(i).getVendorID());
                    }
                    S.E("vendor _id  : " + vendor_id);
                    Bundle bundle = new Bundle();
                    bundle.putString("request_id", request_id);
                    bundle.putString("user_id", vendor_id);
                    S.I(OngoingDetailsActivity.this, FeedBackActivity.class, bundle);
                }
            }
        });
    }

    private void confirmationPopupForCancelRequest() {
        AlertDialog.Builder builder = new AlertDialog.Builder(OngoingDetailsActivity.this);
        builder.setTitle("Confirmation");
        builder.setMessage("Are you sure, You want to cancel Service Request?");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                cancelJob();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void cancelJob() {
        new JSONParser(this).parseRetrofitRequest(ApiClient.getClient().create(ApiInterface.class).change_request_status(
                request_id,
                "0" // 0= cancel this job
        ), new Helper() {
            @Override
            public void backResponse(String response) {
                S.E("cancel request response : " + response);
                if (!response.equals("error")) {
                    S.T(OngoingDetailsActivity.this, "Your Service Request will be Cancel");
                    finish();
                }
            }
        });
    }

    private void getRequestDetails() {
        swipe_refresh_layout.setRefreshing(true);
        new JSONParser(this).parseRetrofitRequestWithautProgress(ApiClient.getClient().create(ApiInterface.class).requeted_service_details(
                request_id
        ), new Helper() {
            @Override
            public void backResponse(String response) {
                S.E("request details response : " + response);
                if (!response.equals("error")) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getBoolean("status")) {
                            JSONObject json = jsonObject.getJSONObject("response");
                            tvServiceName.setText(json.getString("serviceName"));
                            tvLocation.setText(json.getString("serviceReqAddress") + " " + json.getString("serviceReqCity"));
                            tvDateTime.setText("Request Date & Time: " + json.getString("serviceReqDate") + " | " + S.changeHourFormat(json.getString("serviceReqTime")));
                            tvApproximateTime.setText("Approximate time:  " + json.getString("serviveTime"));
                            S.setImageByPicaso(OngoingDetailsActivity.this, Const.URL.SERVICES_IMAGES + json.getString("serviceImage"), bookingImage, null);
                            JSONArray vendor = json.getJSONArray("vendor");
                            for (int i = 0; i < vendor.length(); i++) {
                                JSONObject jsonVendor = vendor.getJSONObject(i);
                                BookingsVendorModel bookingsVendorModel = new BookingsVendorModel();
                                bookingsVendorModel.setVendorID(jsonVendor.getString("vendorID"));
                                bookingsVendorModel.setVendorName(jsonVendor.getString("vendorName"));
                                bookingsVendorModel.setVendorImage(jsonVendor.getString("vendorImage"));
                                bookingsVendorModel.setVendorStatus(jsonVendor.getString("vendorStatus"));
                                arrayListVendor.add(bookingsVendorModel);
                            }
                            if (arrayListVendor.size() > 0) {
                                recycleviewVendorList.setVisibility(View.VISIBLE);
                                tvNoData.setVisibility(View.GONE);
                                recycleviewVendorList.setLayoutManager(new LinearLayoutManager(OngoingDetailsActivity.this));
                                BookingsVendorAdapter bookingsVendorAdapter = new BookingsVendorAdapter(OngoingDetailsActivity.this, arrayListVendor);
                                recycleviewVendorList.setAdapter(bookingsVendorAdapter);
                                bookingsVendorAdapter.setCustomButtonListener(OngoingDetailsActivity.this);

                                for (int i = 0; i < arrayListVendor.size(); i++) {
                                    if (arrayListVendor.get(i).getVendorStatus().equals("4")) {
                                        if (json.getString("customerStatus").equals("3")) {
                                            btnMarkAsComplete.setVisibility(View.GONE);
                                        } else {
                                            btnMarkAsComplete.setVisibility(View.VISIBLE);
                                        }
                                        break;
                                    }
                                }
                            } else {
                                recycleviewVendorList.setVisibility(View.GONE);
                                tvNoData.setVisibility(View.VISIBLE);
                                btnMarkAsComplete.setVisibility(View.GONE);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                swipe_refresh_layout.setRefreshing(false);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        swipe_refresh_layout.setOnRefreshListener(this);

        swipe_refresh_layout.
                post(new Runnable() {
                         @Override
                         public void run() {
                             swipe_refresh_layout.setRefreshing(true);
                             arrayListVendor.clear();
                             getRequestDetails();
                         }
                     }
                );
    }

    @Override
    public void onRefresh() {
        arrayListVendor.clear();
        getRequestDetails();
    }

    @Override
    public void onButtonClick(int position, String buttonText) {
        if (buttonText.equals("chat_btn_click")) {
            if (arrayListVendor.get(position).getVendorStatus().equals("3") || arrayListVendor.get(position).getVendorStatus().equals("4")) {
                Bundle bundle = new Bundle();
                bundle.putString("vendor_id", arrayListVendor.get(position).getVendorID());
                bundle.putString("request_id", request_id);
                bundle.putString("service_name", tvServiceName.getText().toString());
                bundle.putString("vendor_name", arrayListVendor.get(position).getVendorName());
                bundle.putString("vendor_status", arrayListVendor.get(position).getVendorStatus());
                S.I(OngoingDetailsActivity.this, ConversationActivity.class, bundle);
            } else {
                startChatWithVendor(position);
            }
        }
    }

    private void startChatWithVendor(final int position) {
        new JSONParser(this).parseRetrofitRequest(ApiClient.getClient().create(ApiInterface.class).chat_initiate(
                UserDataHelper.getInstance().getUserDataModel().get(0).getUserId(),
                arrayListVendor.get(position).getVendorID(),
                request_id,
                "3"
        ), new Helper() {
            @Override
            public void backResponse(String response) {
                S.E("start chat response : " + response);
                if (!response.equals("error")) {
                    /*S.T(OngoingDetailsActivity.this, "Start Chat");
                    arrayListVendor.clear();
                    getRequestDetails();*/
                    Bundle bundle = new Bundle();
                    bundle.putString("vendor_id", arrayListVendor.get(position).getVendorID());
                    bundle.putString("request_id", request_id);
                    bundle.putString("service_name", tvServiceName.getText().toString());
                    bundle.putString("vendor_name", arrayListVendor.get(position).getVendorName());
                    bundle.putString("vendor_status", arrayListVendor.get(position).getVendorStatus());
                    S.I(OngoingDetailsActivity.this, ConversationActivity.class, bundle);
                }
            }
        });
    }
}