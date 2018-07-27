package com.bluewebspark.happyservice.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bluewebspark.happyservice.R;
import com.bluewebspark.happyservice.activity.ServicesActivity;
import com.bluewebspark.happyservice.model.SubCategoriesModel;
import com.bluewebspark.happyservice.sohel.S;

import java.util.List;

/**
 * Created by androidsys1 on 8/15/2017.
 */

public class SubCategoriesAdapter extends RecyclerView.Adapter<SubCategoriesAdapter.MyViewHolder> {
    List<SubCategoriesModel> arrayList;
    Context context;

    public SubCategoriesAdapter(Context context, List<SubCategoriesModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textCatName;

        public MyViewHolder(View view) {
            super(view);
            textCatName = (TextView) view.findViewById(R.id.textCatName);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_sub_categories, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final SubCategoriesModel model = arrayList.get(position);
        holder.textCatName.setText(model.getSubCatName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("sub_cat_id", model.getSubCatID());
                bundle.putString("sub_cat_name", model.getSubCatName());
                S.I(context, ServicesActivity.class, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
