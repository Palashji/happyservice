package com.bluewebspark.happyservice.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bluewebspark.happyservice.R;
import com.bluewebspark.happyservice.listeners.CustomButtonListener;
import com.bluewebspark.happyservice.model.ServiceVariationModel;
import com.bluewebspark.happyservice.sohel.S;

import java.util.List;

/**
 * Created by androidsys1 on 8/15/2017.
 */

public class ServiceVariationAdapter extends RecyclerView.Adapter<ServiceVariationAdapter.MyViewHolder> {
    List<ServiceVariationModel> arrayList;
    Context context;
    private CustomButtonListener customButtonListener = null;
    private int lastCheckedPosition = 0;

    public ServiceVariationAdapter(Context context, List<ServiceVariationModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    public void setCustomeButton(CustomButtonListener customButtonListener) {
        this.customButtonListener = customButtonListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        Button tvVariation;

        public MyViewHolder(View view) {
            super(view);
            tvVariation = (Button) view.findViewById(R.id.tvVariation);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_service_variation, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        ServiceVariationModel model = arrayList.get(position);
        holder.tvVariation.setText(model.getVariationName());
        S.E("model.getVariationName() : " + model.getVariationName());
        if (position == lastCheckedPosition) {
            holder.tvVariation.setBackground(context.getResources().getDrawable(R.drawable.service_variation_shape));
            holder.tvVariation.setTextColor(context.getResources().getColor(R.color.white));
        } else {
            holder.tvVariation.setBackground(context.getResources().getDrawable(R.drawable.service_variation_shape_non_active));
            holder.tvVariation.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        }
        holder.tvVariation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastCheckedPosition = holder.getAdapterPosition();
                notifyDataSetChanged();
                S.E("click 1 ");
                if (customButtonListener != null) {
                    customButtonListener.onButtonClick(position, "btn_click");
                    S.E("click");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}