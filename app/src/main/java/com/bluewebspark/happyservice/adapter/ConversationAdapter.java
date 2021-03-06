package com.bluewebspark.happyservice.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bluewebspark.happyservice.R;
import com.bluewebspark.happyservice.data.datahelper.UserDataHelper;
import com.bluewebspark.happyservice.model.Messages;
import com.bluewebspark.happyservice.sohel.S;

import java.util.List;

import io.github.rockerhieu.emojicon.EmojiconTextView;

/**
 * Created by Sohel-PC on 03/31/2017.
 */
public class ConversationAdapter extends RecyclerView.Adapter<ConversationAdapter.MyViewHolder> {
    private List<Messages> browseList;
    private Context context;
    private String type;
    private static final int TYPE_REQUEST_MESSAGE = 0;
    private static final int TYPE_OWN_MESSAGE = 1;
    private static final int TYPE_OWN_MESSAGE_SECOND = 2;
    private static final int TYPE_OPPONENT_MESSAGE = 3;
    private static final int TYPE_OPPONENT_MESSAGE_SECOND = 4;
    int temp_pos;
    private String userId = "";

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView userName;
        EmojiconTextView messageText;
        TextView actionView;

        public MyViewHolder(View v) {
            super(v);
            messageText = (EmojiconTextView) v.findViewById(R.id.message_text);
            userName = (TextView) v.findViewById(R.id.userName);
            actionView = (TextView) v.findViewById(R.id.action);
        }
    }

    public ConversationAdapter(Context context, List<Messages> moviesList, String type) {
        this.browseList = moviesList;
        this.context = context;
        this.type = type;
    }

    @Override
    public int getItemViewType(int position) {
        temp_pos = position;
        if (browseList.size() > position + 1) {
            return getItemViewType(browseList.get(position + 1), position);
        } else {
            return getItemViewType(browseList.get(position), position);
        }
    }

    public void setFilter(List<Messages> newData) {
        browseList = newData;
        notifyDataSetChanged();
    }

    private int getItemViewType(Messages message, int position) {
        userId = message.getMessageBY();
        S.E("user id : " + message.getMessageBY());
        if (type.equals("1")) {
            if (browseList.get(position).getMessageBY().equals(UserDataHelper.getInstance().getUserDataModel().get(0).getUserId())) {
                S.E(" user if");
                return TYPE_OWN_MESSAGE;
            } else {
                S.E(" user else");
                return TYPE_OPPONENT_MESSAGE;
            }
        } else {
            if (browseList.get(position).getMessageBY().equals(UserDataHelper.getInstance().getUserDataModel().get(0).getUserId())) {
                S.E(" user if");
                return TYPE_OWN_MESSAGE;
            } else {
                return TYPE_OPPONENT_MESSAGE;
            }
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        S.E("view type = " + viewType);
        switch (viewType) {
            case TYPE_OWN_MESSAGE:
                return new ConversationAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_message_own, parent, false));
            case TYPE_OPPONENT_MESSAGE:
                return new ConversationAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_message_opponent, parent, false));
            case TYPE_REQUEST_MESSAGE:
                return new ConversationAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.presence_message, parent, false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Messages messages = browseList.get(position);
        holder.messageText.setText(messages.getChat());
    }

    @Override
    public int getItemCount() {
        return browseList.size();
    }
}