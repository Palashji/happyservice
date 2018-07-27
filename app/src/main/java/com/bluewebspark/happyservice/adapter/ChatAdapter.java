package com.bluewebspark.happyservice.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bluewebspark.happyservice.R;
import com.bluewebspark.happyservice.activity.ConversationActivity;
import com.bluewebspark.happyservice.model.ChatModel;
import com.bluewebspark.happyservice.sohel.S;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by androidsys1 on 8/15/2017.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {
    List<ChatModel> arrayList;
    Context context;
    @BindView(R.id.chatUserImage)
    ImageView chatUserImage;
    @BindView(R.id.tvUserName)
    TextView tvUserName;
    @BindView(R.id.tvLastMsg)
    TextView tvLastMsg;
    @BindView(R.id.mainLayout)
    RelativeLayout mainLayout;

    public ChatAdapter(Context context, List<ChatModel> arrayList) {
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
                .inflate(R.layout.item_chat, parent, false);
        ButterKnife.bind(this, itemView);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final ChatModel model = arrayList.get(position);
        tvUserName.setText(model.getUserName());
        tvLastMsg.setText(model.getServiceName());

        if (!model.getUserImage().equals(""))
            S.setImageByPicaso(context, model.getUserImage(), chatUserImage, null);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("vendor_id", model.getMessageBY());
                bundle.putString("request_id", model.getServiceRequestID());
                bundle.putString("service_name", model.getServiceName());
                bundle.putString("vendor_name", model.getUserName());
                bundle.putString("vendor_status", model.getRequestStatus());
                S.I(context, ConversationActivity.class, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
