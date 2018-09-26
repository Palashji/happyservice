package com.bluewebspark.happyservice.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bluewebspark.happyservice.R;
import com.bluewebspark.happyservice.activity.HistoryDetailsActivity;
import com.bluewebspark.happyservice.model.CompleteModel;
import com.bluewebspark.happyservice.sohel.S;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by androidsys1 on 8/15/2017.
 */

public class CompleteAdapter extends RecyclerView.Adapter<CompleteAdapter.MyViewHolder> {
    List<CompleteModel> arrayList;
    Context context;
    @BindView(R.id.tvServiceName)
    TextView tvServiceName;
    @BindView(R.id.tvTime)
    TextView tvTime;
    @BindView(R.id.mainLayout)
    RelativeLayout mainLayout;

    public CompleteAdapter(Context context, List<CompleteModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(View view) {
            super(view);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_complete, parent, false);
        ButterKnife.bind(this, itemView);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final CompleteModel model = arrayList.get(position);
        tvServiceName.setText(model.getServiceName());
        tvTime.setText(model.getApproximateTime());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("request_id", model.getRequestID());
                S.I(context, HistoryDetailsActivity.class, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

}