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
import com.bluewebspark.happyservice.activity.SubCategoryListActivity;
import com.bluewebspark.happyservice.model.CategoryModel;
import com.bluewebspark.happyservice.sohel.Const;
import com.bluewebspark.happyservice.sohel.S;

import java.util.List;

/**
 * Created by androidsys1 on 8/15/2017.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    List<CategoryModel> arrayList;
    Context context;

    public CategoryAdapter(Context context, List<CategoryModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView itemTextServiceName;
        ImageView itemServiceImage;

        public MyViewHolder(View view) {
            super(view);
            itemTextServiceName = (TextView) view.findViewById(R.id.itemTextServiceName);
            itemServiceImage = (ImageView) view.findViewById(R.id.itemServiceImage);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_categories, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final CategoryModel model = arrayList.get(position);
        holder.itemTextServiceName.setText(model.getCatName());
        S.setImageByPicaso(context, Const.URL.CAT_ICON + model.getCatIcon(), holder.itemServiceImage, null);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("cat_name", model.getCatName());
                bundle.putString("cat_id", model.getCatID());
                S.I(context, SubCategoryListActivity.class, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
