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
import com.bluewebspark.happyservice.adapter.CompleteAdapter;
import com.bluewebspark.happyservice.data.datahelper.UserDataHelper;
import com.bluewebspark.happyservice.model.CompleteModel;
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
 * Created by abc on 05-Mar-18.
 */

public class CancelledFragment extends Fragment {

    @BindView(R.id.recycleviewHistory)
    RecyclerView recycleviewHistory;
    @BindView(R.id.noData)
    TextView noData;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipe_refresh_layout;

    ArrayList<CompleteModel> arrayListHistory = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cancelled, container, false);
        ButterKnife.bind(this, view);
        /*arrayListHistory.add(new CompleteModel("1", "Service Name", "22/03/2018", "Vijay nagar, Indore", ""));
        arrayListHistory.add(new CompleteModel("1", "Service Name", "22/03/2018", "Vijay nagar, Indore", ""));
        arrayListHistory.add(new CompleteModel("1", "Service Name", "22/03/2018", "Vijay nagar, Indore", ""));
        arrayListHistory.add(new CompleteModel("1", "Service Name", "22/03/2018", "Vijay nagar, Indore", ""));
        arrayListHistory.add(new CompleteModel("1", "Service Name", "22/03/2018", "Vijay nagar, Indore", ""));
        arrayListHistory.add(new CompleteModel("1", "Service Name", "22/03/2018", "Vijay nagar, Indore", ""));
        arrayListHistory.add(new CompleteModel("1", "Service Name", "22/03/2018", "Vijay nagar, Indore", ""));
        arrayListHistory.add(new CompleteModel("1", "Service Name", "22/03/2018", "Vijay nagar, Indore", ""));

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recycleviewHistory.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycleviewHistory.addItemDecoration(new DividerItemDecoration(getActivity(), layoutManager.getOrientation()));
        CompleteAdapter CompleteAdapter = new CompleteAdapter(getActivity(), arrayListHistory);
        recycleviewHistory.setAdapter(CompleteAdapter);*/
        getHistory();
        return view;
    }

    private void getHistory() {
        new JSONParser(getActivity()).parseRetrofitRequest(ApiClient.getClient().create(ApiInterface.class).my_cancelled_service(
                UserDataHelper.getInstance().getUserDataModel().get(0).getUserId()
        ), new Helper() {
            @Override
            public void backResponse(String response) {
                S.E("service cancelled response : " + response);
                if (!response.equals("error")) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getBoolean("status")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("response");
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject json = jsonArray.getJSONObject(i);
                                CompleteModel completeModel = new CompleteModel();
                                completeModel.setRequestID(json.getString("requestID"));
                                completeModel.setServicePrice(json.getString("servicePrice"));
                                completeModel.setServiceName(json.getString("serviceName"));
                                completeModel.setApproximateTime(json.getString("approximateTime"));
                                completeModel.setServiceImage(json.getString("serviceImage"));
                                arrayListHistory.add(completeModel);
                            }
                            if (arrayListHistory.size() > 0) {
                                recycleviewHistory.setVisibility(View.VISIBLE);
                                noData.setVisibility(View.GONE);
                                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                                recycleviewHistory.setLayoutManager(new LinearLayoutManager(getActivity()));
                                recycleviewHistory.addItemDecoration(new DividerItemDecoration(getActivity(), layoutManager.getOrientation()));
                                CompleteAdapter CompleteAdapter = new CompleteAdapter(getActivity(), arrayListHistory);
                                recycleviewHistory.setAdapter(CompleteAdapter);
                            } else {
                                recycleviewHistory.setVisibility(View.GONE);
                                noData.setVisibility(View.VISIBLE);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        recycleviewHistory.setVisibility(View.GONE);
                        noData.setVisibility(View.VISIBLE);
                    }
                } else {
                    recycleviewHistory.setVisibility(View.GONE);
                    noData.setVisibility(View.VISIBLE);
                }
                swipe_refresh_layout.setRefreshing(false);
            }
        });
    }
}