package com.bluewebspark.happyservice.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bluewebspark.happyservice.R;
import com.bluewebspark.happyservice.adapter.OpenAdapter;
import com.bluewebspark.happyservice.data.datahelper.UserDataHelper;
import com.bluewebspark.happyservice.model.OpenModel;
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
 * Created by abc on 05-1-18.
 */

public class OpenFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.recycleviewOpen)
    RecyclerView recycleviewOpen;
    @BindView(R.id.noData)
    TextView noData;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipe_refresh_layout;

    ArrayList<OpenModel> arrayListOpen = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_open, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    private void getOpenData() {
        swipe_refresh_layout.setRefreshing(true);
        new JSONParser(getActivity()).parseRetrofitRequestWithautProgress(ApiClient.getClient().create(ApiInterface.class).getOnGoing(
                UserDataHelper.getInstance().getUserDataModel().get(0).getUserId()
        ), new Helper() {
            @Override
            public void backResponse(String response) {
                S.E("ongoing response : " + response);
                if (!response.equals("error")) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getBoolean("status")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("response");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject json = jsonArray.getJSONObject(i);
                                OpenModel openModel = new OpenModel();
                                openModel.setRequestID(json.getString("requestID"));
                                openModel.setServicePrice(json.getString("servicePrice"));
                                openModel.setServiceName(json.getString("serviceName"));
                                openModel.setApproximateTime(json.getString("approximateTime"));
                                openModel.setServiceImage(json.getString("serviceImage"));
                                arrayListOpen.add(openModel);
                            }
                            if (arrayListOpen.size() > 0) {
                                recycleviewOpen.setVisibility(View.VISIBLE);
                                noData.setVisibility(View.GONE);
                                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                                recycleviewOpen.setLayoutManager(layoutManager);
                                recycleviewOpen.addItemDecoration(new DividerItemDecoration(getActivity(), layoutManager.getOrientation()));
                                OpenAdapter openAdapter = new OpenAdapter(getActivity(), arrayListOpen);
                                recycleviewOpen.setAdapter(openAdapter);
                            } else {
                                recycleviewOpen.setVisibility(View.GONE);
                                noData.setVisibility(View.VISIBLE);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        recycleviewOpen.setVisibility(View.GONE);
                        noData.setVisibility(View.VISIBLE);
                    }
                } else {
                    recycleviewOpen.setVisibility(View.GONE);
                    noData.setVisibility(View.VISIBLE);
                }
                swipe_refresh_layout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        swipe_refresh_layout.setOnRefreshListener(this);

        swipe_refresh_layout.
                post(new Runnable() {
                         @Override
                         public void run() {
                             swipe_refresh_layout.setRefreshing(true);
                             arrayListOpen.clear();
                             getOpenData();
                         }
                     }
                );
    }

    @Override
    public void onRefresh() {
        arrayListOpen.clear();
        getOpenData();
    }
}