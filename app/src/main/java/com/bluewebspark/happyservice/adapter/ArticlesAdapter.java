package com.bluewebspark.happyservice.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bluewebspark.happyservice.R;
import com.bluewebspark.happyservice.model.ArticlesModel;

import java.util.List;

/**
 * Created by androidsys1 on 8/15/2017.
 */

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.MyViewHolder> {
    List<ArticlesModel> arrayList;
    Context context;
    ArticlesModel model;

    public ArticlesAdapter(Context context, List<ArticlesModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textCatName, textCatDesc;
        ImageView articlesImage;

        public MyViewHolder(View view) {
            super(view);

            articlesImage = (ImageView) view.findViewById(R.id.articlesImage);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_articles, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        model = arrayList.get(position);
        holder.articlesImage.setImageResource(model.getArticleImage());
        /*holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("cat_name", serviceObject.getCat_name());
                S.I(context, ProductListActivity.class, bundle);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
