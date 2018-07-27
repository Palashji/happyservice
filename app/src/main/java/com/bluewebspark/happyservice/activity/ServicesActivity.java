package com.bluewebspark.happyservice.activity;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;

import com.bluewebspark.happyservice.R;
import com.bluewebspark.happyservice.adapter.ServicesAdapter;
import com.bluewebspark.happyservice.model.ServicesModel;
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

/**
 * Created by abc on 21-Mar-18.
 */

public class ServicesActivity extends BaseActivity {
    @BindView(R.id.tool_bar)
    Toolbar tool_bar;
    @BindView(R.id.recycleViewServices)
    RecyclerView recycleViewServices;
    @BindView(R.id.layoutEmpty)
    RelativeLayout layoutEmpty;
    private String sub_cat_id;
    private String sub_cat_name;

    ArrayList<ServicesModel> arrayListServices = new ArrayList<>();

    @Override
    protected int getContentResId() {
        return R.layout.activity_services;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        sub_cat_id = getIntent().getStringExtra("sub_cat_id");
        sub_cat_name = getIntent().getStringExtra("sub_cat_name");
        setToolbarWithBackButton(sub_cat_name);
        getServices();
    }

    private void getServices() {
        new JSONParser(this).parseRetrofitRequest(ApiClient.getClient().create(ApiInterface.class).getServices(sub_cat_id), new Helper() {
            @Override
            public void backResponse(String response) {
                S.E("services response : " + response);
                if (!response.equals("error")) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getBoolean("status")) {
                            JSONArray data = jsonObject.getJSONArray("response");
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject json = data.getJSONObject(i);
                                ServicesModel servicesModel = new ServicesModel();
                                servicesModel.setServiceID(json.getString("serviceID"));
                                servicesModel.setServiceName(json.getString("serviceName"));
                                servicesModel.setServiceImage(json.getString("serviceImage"));
                                servicesModel.setServiceCategoryID(json.getString("serviceCategoryID"));
                                arrayListServices.add(servicesModel);
                            }
                            if (arrayListServices.size() > 0) {
                                recycleViewServices.setVisibility(View.VISIBLE);
                                layoutEmpty.setVisibility(View.GONE);
                                LinearLayoutManager layoutManager = new LinearLayoutManager(ServicesActivity.this);
                                recycleViewServices.setLayoutManager(layoutManager);
                                recycleViewServices.addItemDecoration(new DividerItemDecoration(ServicesActivity.this, layoutManager.getOrientation()));
                                ServicesAdapter servicesAdapter = new ServicesAdapter(ServicesActivity.this, arrayListServices);
                                recycleViewServices.setAdapter(servicesAdapter);
                            } else {
                                recycleViewServices.setVisibility(View.GONE);
                                layoutEmpty.setVisibility(View.VISIBLE);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        recycleViewServices.setVisibility(View.GONE);
                        layoutEmpty.setVisibility(View.VISIBLE);
                    }
                }else {
                    recycleViewServices.setVisibility(View.GONE);
                    layoutEmpty.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}
