package com.bluewebspark.happyservice.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bluewebspark.happyservice.R;
import com.bluewebspark.happyservice.model.ReviewModel;
import com.bluewebspark.happyservice.sohel.S;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by androidsys1 on 8/15/2017.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.MyViewHolder> {

    List<ReviewModel> arrayList;
    Context context;
    @BindView(R.id.userImage)
    ImageView userImage;
    @BindView(R.id.tvCustomerName)
    TextView tvCustomerName;
    @BindView(R.id.rating)
    RatingBar rating;
    @BindView(R.id.tvReview)
    TextView tvReview;

    public ReviewAdapter(Context context, List<ReviewModel> arrayList) {
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
                .inflate(R.layout.item_customer_review, parent, false);
        ButterKnife.bind(this, itemView);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        ReviewModel model = arrayList.get(position);
        tvCustomerName.setText(model.getVendorName());
        rating.setRating(Float.parseFloat(model.getRatingNumber()));

        S.setImageByPicaso(context,model.getVendorImage(),userImage,null);

        if (model.getReviewComment().equals("")) {
            tvReview.setVisibility(View.GONE);
        } else {
            tvReview.setVisibility(View.VISIBLE);
            tvReview.setText(model.getReviewComment());
        }

        /*holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, RequestDetailActivity.class));
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
