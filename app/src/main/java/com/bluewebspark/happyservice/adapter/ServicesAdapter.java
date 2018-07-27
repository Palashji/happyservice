package com.bluewebspark.happyservice.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bluewebspark.happyservice.R;
import com.bluewebspark.happyservice.activity.ServiceDetailsActivity;
import com.bluewebspark.happyservice.activity.ServicesActivity;
import com.bluewebspark.happyservice.model.ServicesModel;
import com.bluewebspark.happyservice.model.SubCategoriesModel;
import com.bluewebspark.happyservice.sohel.Const;
import com.bluewebspark.happyservice.sohel.S;

import java.util.List;

/**
 * Created by androidsys1 on 8/15/2017.
 */

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.MyViewHolder> {
    List<ServicesModel> arrayList;
    Context context;

    public ServicesAdapter(Context context, List<ServicesModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvServiceName, tvServiceDesc, tvServicePrice;
        ImageView serviceImage;

        public MyViewHolder(View view) {
            super(view);
            tvServiceName = (TextView) view.findViewById(R.id.tvServiceName);
            serviceImage = (ImageView) view.findViewById(R.id.serviceImage);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_services, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final ServicesModel model = arrayList.get(position);
        holder.tvServiceName.setText(model.getServiceName());
        S.setImageByPicaso(context, Const.URL.SERVICES_IMAGES + model.getServiceImage(), holder.serviceImage, null);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("service_name", model.getServiceName());
                bundle.putString("service_image", model.getServiceImage());
                bundle.putString("service_id", model.getServiceID());
                S.I(context, ServiceDetailsActivity.class, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
