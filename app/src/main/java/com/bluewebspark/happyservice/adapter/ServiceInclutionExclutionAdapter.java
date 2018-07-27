package com.bluewebspark.happyservice.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bluewebspark.happyservice.R;
import com.bluewebspark.happyservice.model.ServiceInclusionsExclutionsModel;
import com.bluewebspark.happyservice.model.ServiceProcedureModel;

import java.util.List;

/**
 * Created by androidsys1 on 8/15/2017.
 */

public class ServiceInclutionExclutionAdapter extends RecyclerView.Adapter<ServiceInclutionExclutionAdapter.MyViewHolder> {
    List<String> arrayList;
    Context context;
    int arraySize;

    public ServiceInclutionExclutionAdapter(Context context, List<String> arrayList, int arraySize) {
        this.context = context;
        this.arrayList = arrayList;
        this.arraySize = arraySize;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvProcedureTitle;
        ImageView imageProcedureCorrect, imageProcedureWrong;

        public MyViewHolder(View view) {
            super(view);
            tvProcedureTitle = (TextView) view.findViewById(R.id.tvInclutionsExclutionTitle);
            imageProcedureWrong = (ImageView) view.findViewById(R.id.imageInclutionsExclutionWrong);
            imageProcedureCorrect = (ImageView) view.findViewById(R.id.imageInclutionsExclutionCorrect);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_service_inclutions_exclutions, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final String model = arrayList.get(position);
        if (arraySize <= position) {
            holder.imageProcedureCorrect.setVisibility(View.GONE);
            holder.imageProcedureWrong.setVisibility(View.VISIBLE);
        } else {
            holder.imageProcedureCorrect.setVisibility(View.VISIBLE);
            holder.imageProcedureWrong.setVisibility(View.GONE);
        }
        holder.tvProcedureTitle.setText(model);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
