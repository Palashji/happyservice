package com.bluewebspark.happyservice.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bluewebspark.happyservice.R;
import com.bluewebspark.happyservice.activity.OngoingDetailsActivity;
import com.bluewebspark.happyservice.model.OnGoingModel;
import com.bluewebspark.happyservice.sohel.ApiClient;
import com.bluewebspark.happyservice.sohel.ApiInterface;
import com.bluewebspark.happyservice.sohel.Helper;
import com.bluewebspark.happyservice.sohel.JSONParser;
import com.bluewebspark.happyservice.sohel.S;

import java.util.List;

/**
 * Created by androidsys1 on 8/15/2017.
 */

public class OngoingAdapter extends RecyclerView.Adapter<OngoingAdapter.MyViewHolder> {
    List<OnGoingModel> arrayList;
    Context context;

    public OngoingAdapter(Context context, List<OnGoingModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvServiceName, tvTime, tvCancel;

        public MyViewHolder(View view) {
            super(view);
            tvServiceName = (TextView) view.findViewById(R.id.tvServiceName);
            tvTime = (TextView) view.findViewById(R.id.tvTime);
            tvCancel = (TextView) view.findViewById(R.id.tvCancel);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_booking, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final OnGoingModel model = arrayList.get(position);
        holder.tvServiceName.setText(model.getServiceName());
        holder.tvTime.setText(model.getApproximateTime());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("request_id", model.getRequestID());
                S.I(context, OngoingDetailsActivity.class, bundle);
            }
        });

        holder.tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmationPopupForCancelRequest(model.getRequestID());
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    private void confirmationPopupForCancelRequest(final String requestID) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Confirmation");
        builder.setMessage("Are you sure, You want to cancel Service Request?");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                cancelJob(requestID);
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

    private void cancelJob(String requestID) {
        new JSONParser(context).parseRetrofitRequest(ApiClient.getClient().create(ApiInterface.class).change_request_status(
                requestID,
                "0" // 0= cancel this job
        ), new Helper() {
            @Override
            public void backResponse(String response) {
                S.E("cancel request response : " + response);
                if (!response.equals("error")) {
                    S.T(context, "Your Service Request will be Cancel");
                    notifyDataSetChanged();
                }
            }
        });
    }
}
