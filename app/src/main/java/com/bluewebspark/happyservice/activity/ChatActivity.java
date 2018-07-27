package com.bluewebspark.happyservice.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.bluewebspark.happyservice.R;
import com.bluewebspark.happyservice.adapter.ChatAdapter;
import com.bluewebspark.happyservice.data.datahelper.UserDataHelper;
import com.bluewebspark.happyservice.model.ChatModel;
import com.bluewebspark.happyservice.model.RecentChatModel;
import com.bluewebspark.happyservice.sohel.ApiClient;
import com.bluewebspark.happyservice.sohel.ApiInterface;
import com.bluewebspark.happyservice.sohel.Helper;
import com.bluewebspark.happyservice.sohel.JSONParser;
import com.bluewebspark.happyservice.sohel.S;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Sohel Khan on 29-Mar-18.
 */

public class ChatActivity extends BaseActivity {
    @BindView(R.id.tool_bar)
    Toolbar tool_bar;
    @BindView(R.id.recycleviewChat)
    RecyclerView recycleviewChat;

    ArrayList<ChatModel> arrayListChat = new ArrayList<>();

    @Override
    protected int getContentResId() {
        return R.layout.activity_chat;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        setToolbarWithBackButton("Previous Chat");

        getRecentChat();
    }

    private void getRecentChat() {
        new JSONParser(this).parseRetrofitRequest(ApiClient.getClient().create(ApiInterface.class).chat_list(
                UserDataHelper.getInstance().getUserDataModel().get(0).getUserId()
        ), new Helper() {
            @Override
            public void backResponse(String response) {
                S.E("recent chat list response : " + response);
                if (!response.equals("error")) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getBoolean("status")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("response");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject json = jsonArray.getJSONObject(i);
                                ChatModel chatModel = new ChatModel();
                                chatModel.setServiceName(json.getString("serviceName"));
                                chatModel.setServiceRequestID(json.getString("serviceRequestID"));
                                chatModel.setUserName(json.getString("userName"));
                                chatModel.setMessageBY(json.getString("messageBY"));
                                chatModel.setUserImage(json.getString("userImage"));
                                chatModel.setRequestStatus(json.getString("requestStatus"));
                                arrayListChat.add(chatModel);
                            }
                            recycleviewChat.setLayoutManager(new LinearLayoutManager(ChatActivity.this));
                            ChatAdapter chatAdapter = new ChatAdapter(ChatActivity.this, arrayListChat);
                            recycleviewChat.setAdapter(chatAdapter);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }
}
