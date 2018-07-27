package com.bluewebspark.happyservice.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bluewebspark.happyservice.R;
import com.bluewebspark.happyservice.activity.ServiceDetailsActivity;
import com.bluewebspark.happyservice.model.ServicesModel;
import com.bluewebspark.happyservice.sohel.S;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by androidsys1 on 8/15/2017.
 */

public class TopServicesAdapter extends RecyclerView.Adapter<TopServicesAdapter.MyViewHolder> {
    List<ServicesModel> arrayList;
    Context context;
    @BindView(R.id.serviceImage)
    ImageView serviceImage;
    @BindView(R.id.tvServiceName)
    TextView tvServiceName;
    @BindView(R.id.mainLayout)
    LinearLayout mainLayout;

    public TopServicesAdapter(Context context, List<ServicesModel> arrayList) {
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
                .inflate(R.layout.item_top_services, parent, false);
        ButterKnife.bind(this, itemView);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final ServicesModel model = arrayList.get(position);
        /*tvServiceName.setText(model.getService_name());
        serviceImage.setImageResource(model.getService_Image());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("service_name", model.getService_name());
                bundle.putString("service_desc", model.getService_desc());
                bundle.putString("service_price", model.getService_price());
                bundle.putInt("service_image", model.getService_Image());
                S.I(context, ServiceDetailsActivity.class, bundle);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}