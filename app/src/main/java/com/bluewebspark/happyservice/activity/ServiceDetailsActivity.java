package com.bluewebspark.happyservice.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bluewebspark.happyservice.R;
import com.bluewebspark.happyservice.adapter.ServiceInclutionExclutionAdapter;
import com.bluewebspark.happyservice.adapter.ServiceProcedureAdapter;
import com.bluewebspark.happyservice.adapter.ServiceVariationAdapter;
import com.bluewebspark.happyservice.data.datahelper.UserDataHelper;
import com.bluewebspark.happyservice.listeners.CustomButtonListener;
import com.bluewebspark.happyservice.model.ServiceVariationModel;
import com.bluewebspark.happyservice.sohel.ApiClient;
import com.bluewebspark.happyservice.sohel.ApiInterface;
import com.bluewebspark.happyservice.sohel.Const;
import com.bluewebspark.happyservice.sohel.Helper;
import com.bluewebspark.happyservice.sohel.JSONParser;
import com.bluewebspark.happyservice.sohel.S;
import com.bluewebspark.happyservice.sohel.SavedData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by abc on 21-Mar-18.
 */

public class ServiceDetailsActivity extends BaseActivity implements CustomButtonListener {
    @BindView(R.id.tool_bar)
    Toolbar tool_bar;
    @BindView(R.id.serviceImage)
    ImageView serviceImage;
    @BindView(R.id.tvHeading)
    TextView tvHeading;
    @BindView(R.id.tvServicePrice)
    TextView tvServicePrice;
    @BindView(R.id.tvSubHeading)
    TextView tvSubHeading;
    @BindView(R.id.tvServiceDesc)
    TextView tvServiceDesc;
    @BindView(R.id.tvApproximateTime)
    TextView tvApproximateTime;
    @BindView(R.id.recycleViewInclusionsExclusios)
    RecyclerView recycleViewInclusionsExclusios;
    @BindView(R.id.recycleViewProcedure)
    RecyclerView recycleViewProcedure;
    @BindView(R.id.btnBook)
    Button btnBook;
    @BindView(R.id.recycleViewVariation)
    RecyclerView recycleViewVariation;
    @BindView(R.id.tvVariationType)
    TextView tvVariationType;
    @BindView(R.id.tvVariationPrice)
    TextView tvVariationPrice;
    @BindView(R.id.layoutVariation)
    LinearLayout layoutVariation;
    //    variable declaration
    String service_name;
    String service_desc;
    String service_price;
    String service_image;
    ArrayList<String> arrayListInclutionsExclution = new ArrayList<>();
    ArrayList<String> arrayListProcedure = new ArrayList<>();
    ArrayList<ServiceVariationModel> arrayListServiceVariation = new ArrayList<>();

    private String service_id;
    private String serviceID;
    private String priceToSend;

    @Override
    protected int getContentResId() {
        return R.layout.activity_service_details;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        service_name = getIntent().getStringExtra("service_name");
        service_image = getIntent().getStringExtra("service_image");
        service_id = getIntent().getStringExtra("service_id");

        setToolbarWithBackButton(service_name);

        S.setImageByPicaso(ServiceDetailsActivity.this, Const.URL.SERVICES_IMAGES + service_image, serviceImage, null);

        getServiceDetails();

        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserDataHelper.getInstance().getUserDataModel().size() > 0) {
                    if (SavedData.getCityStatus()) {
                        Bundle bundle = new Bundle();
                        bundle.putString("price", priceToSend);
                        bundle.putString("service_id", service_id);
                        bundle.putString("service_image", service_image);
                        bundle.putString("service_name", service_name);
                        S.I(ServiceDetailsActivity.this, BookingActivity.class, bundle);
                    } else {
                        S.T(ServiceDetailsActivity.this, "Service not Available in your City.");
                    }
                } else {
                    S.I(ServiceDetailsActivity.this, GetOtpActivity.class, null);
                }
            }
        });
    }

    private void getServiceDetails() {
        new JSONParser(this).parseRetrofitRequest(ApiClient.getClient().create(ApiInterface.class).getServiceDetails(service_id), new Helper() {
            @Override
            public void backResponse(String response) {
                S.E("service details response : " + response);
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean("status")) {
                        JSONArray data = jsonObject.getJSONArray("response");
                        JSONObject json = data.getJSONObject(0);
                        serviceID = json.getString("serviceID");
                        tvHeading.setText(json.getString("serviceHeading"));
                        tvSubHeading.setText(json.getString("serviceSubHeading"));
                        tvServiceDesc.setText(json.getString("serviceDescription"));
                        tvApproximateTime.setText(json.getString("serviveTime"));

//                        add Inclution and Exclution data arrayList and show on RecycleView
                        JSONArray serviceInclusion = json.getJSONArray("serviceInclusion");
                        JSONArray serviceExclusion = json.getJSONArray("serviceExclusion");

                        for (int i = 0; i < serviceInclusion.length(); i++) {
                            arrayListInclutionsExclution.add(serviceInclusion.getString(i));
                        }

                        int arraySize = arrayListInclutionsExclution.size();

                        for (int i = 0; i < serviceExclusion.length(); i++) {
                            arrayListInclutionsExclution.add(serviceExclusion.getString(i));
                        }

                        recycleViewInclusionsExclusios.setLayoutManager(new LinearLayoutManager(ServiceDetailsActivity.this));
                        ServiceInclutionExclutionAdapter serviceInclutionExclutionAdapter = new ServiceInclutionExclutionAdapter(ServiceDetailsActivity.this, arrayListInclutionsExclution, arraySize);
                        recycleViewInclusionsExclusios.setAdapter(serviceInclutionExclutionAdapter);

//                        add Procedure data in arraylist and show it on recycleview
                        JSONArray service_Procedure = json.getJSONArray("service_Procedure");
                        for (int i = 0; i < service_Procedure.length(); i++) {
                            arrayListProcedure.add(service_Procedure.getString(i));
                        }

                        recycleViewProcedure.setLayoutManager(new LinearLayoutManager(ServiceDetailsActivity.this));
                        ServiceProcedureAdapter serviceProcedureAdapter = new ServiceProcedureAdapter(ServiceDetailsActivity.this, arrayListProcedure);
                        recycleViewProcedure.setAdapter(serviceProcedureAdapter);

                        if (json.getString("serviceType").equals("variable_data")) {

                            String nextKey = json.getJSONArray("variationAttribute").getString(0).replace("[", "").replace("]", "");
                            JSONObject variationAttributeValues = json.getJSONObject("variationAttributeValues");
                            JSONArray variationData = variationAttributeValues.getJSONArray(nextKey);
                            for (int j = 0; j < variationData.length(); j++) {
                                JSONObject variationObj = variationData.getJSONObject(j);
                                ServiceVariationModel serviceVariationModel = new ServiceVariationModel();
                                serviceVariationModel.setVariationName(variationObj.getString("variationName"));
                                serviceVariationModel.setPriceType(variationObj.getString("priceType"));
                                serviceVariationModel.setVariationPrice(variationObj.getString("variationPrice"));
                                arrayListServiceVariation.add(serviceVariationModel);
                            }

                            tvVariationType.setText(arrayListServiceVariation.get(0).getPriceType());
                            tvVariationPrice.setText("Rs. "+arrayListServiceVariation.get(0).getVariationPrice());
                            tvServicePrice.setVisibility(View.GONE);
                            priceToSend = tvVariationPrice.getText().toString();
                            recycleViewVariation.setLayoutManager(new LinearLayoutManager(ServiceDetailsActivity.this, LinearLayoutManager.HORIZONTAL, false));
                            ServiceVariationAdapter serviceVariationAdapter = new ServiceVariationAdapter(ServiceDetailsActivity.this, arrayListServiceVariation);
                            recycleViewVariation.setAdapter(serviceVariationAdapter);
                            serviceVariationAdapter.setCustomeButton(ServiceDetailsActivity.this);
                        } else if (json.getString("serviceType").equals("gerenal_data")) {
                            tvServicePrice.setVisibility(View.VISIBLE);
                            layoutVariation.setVisibility(View.GONE);
                            tvServicePrice.setText(json.getString("servicePrice"));
                            priceToSend = tvServicePrice.getText().toString();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onButtonClick(int position, String buttonText) {
        if (buttonText.equals("btn_click")) {
            tvVariationType.setText(arrayListServiceVariation.get(position).getPriceType());
            tvVariationPrice.setText("Rs. "+arrayListServiceVariation.get(position).getVariationPrice());
            priceToSend = tvVariationPrice.getText().toString();
        }
    }
}