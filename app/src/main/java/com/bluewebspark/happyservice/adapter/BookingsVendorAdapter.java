package com.bluewebspark.happyservice.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bluewebspark.happyservice.R;
import com.bluewebspark.happyservice.activity.UserDetailsActivity;
import com.bluewebspark.happyservice.data.datahelper.UserDataHelper;
import com.bluewebspark.happyservice.listeners.CustomButtonListener;
import com.bluewebspark.happyservice.model.BookingsVendorModel;
import com.bluewebspark.happyservice.sohel.ApiClient;
import com.bluewebspark.happyservice.sohel.ApiInterface;
import com.bluewebspark.happyservice.sohel.Helper;
import com.bluewebspark.happyservice.sohel.JSONParser;
import com.bluewebspark.happyservice.sohel.S;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by androidsys1 on 8/15/2017.
 */

public class BookingsVendorAdapter extends RecyclerView.Adapter<BookingsVendorAdapter.MyViewHolder> {
    @BindView(R.id.vendorImage)
    CircleImageView vendorImage;
    @BindView(R.id.tvVendorName)
    TextView tvVendorName;
    @BindView(R.id.tvVendorService)
    TextView tvVendorService;
    @BindView(R.id.btnChat)
    Button btnChat;

    List<BookingsVendorModel> arrayList;
    Context context;
    private CustomButtonListener customButtonListener = null;

    public BookingsVendorAdapter(Context context, List<BookingsVendorModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    public void setCustomButtonListener(CustomButtonListener customButtonListener) {
        this.customButtonListener = customButtonListener;
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
                .inflate(R.layout.item_bookings_vendor, parent, false);
        ButterKnife.bind(this, itemView);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final BookingsVendorModel model = arrayList.get(position);
        tvVendorName.setText(model.getVendorName());

        if (model.getVendorStatus().equals("3") || model.getVendorStatus().equals("4")) {
            btnChat.setBackground(context.getResources().getDrawable(R.drawable.btn_shape_active));
        } else {
            btnChat.setBackground(context.getResources().getDrawable(R.drawable.btn_shape_non_active));
        }
        S.setImageByPicaso(context, model.getVendorImage(), vendorImage, null);
//        tvVendorService.setText(model.getVendorService());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("user_id", model.getVendorID());
                S.I(context, UserDetailsActivity.class, bundle);
            }
        });

        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (customButtonListener != null)
                    customButtonListener.onButtonClick(holder.getAdapterPosition(), "chat_btn_click");
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
