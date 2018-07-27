package com.bluewebspark.happyservice.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bluewebspark.happyservice.R;
import com.bluewebspark.happyservice.model.ServiceInclusionsExclutionsModel;
import com.bluewebspark.happyservice.model.ServiceProcedureModel;

import java.util.List;

/**
 * Created by androidsys1 on 8/15/2017.
 */

public class ServiceProcedureAdapter extends RecyclerView.Adapter<ServiceProcedureAdapter.MyViewHolder> {
    List<String> arrayList;
    Context context;

    public ServiceProcedureAdapter(Context context, List<String> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvInclutionsExclutionsTitle;

        public MyViewHolder(View view) {
            super(view);
            tvInclutionsExclutionsTitle = (TextView) view.findViewById(R.id.tvProcedureTitle);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_service_procedures, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final String model = arrayList.get(position);
        holder.tvInclutionsExclutionsTitle.setText(model);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
