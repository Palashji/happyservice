package com.bluewebspark.happyservice.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bluewebspark.happyservice.R;
import com.bluewebspark.happyservice.adapter.ConversationAdapter;
import com.bluewebspark.happyservice.data.datahelper.UserDataHelper;
import com.bluewebspark.happyservice.model.Messages;
import com.bluewebspark.happyservice.notification.Config;
import com.bluewebspark.happyservice.notification.NotificationUtils;
import com.bluewebspark.happyservice.sohel.ApiClient;
import com.bluewebspark.happyservice.sohel.ApiInterface;
import com.bluewebspark.happyservice.sohel.Helper;
import com.bluewebspark.happyservice.sohel.JSONParser;
import com.bluewebspark.happyservice.sohel.KeyboardUtils;
import com.bluewebspark.happyservice.sohel.S;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.rockerhieu.emojicon.EmojiconEditText;
import io.github.rockerhieu.emojicon.EmojiconGridFragment;
import io.github.rockerhieu.emojicon.EmojiconsFragment;
import io.github.rockerhieu.emojicon.emoji.Emojicon;

/**
 * Created by sohel on 3/31/2017.
 */

public class ConversationActivity extends BaseActivity implements EmojiconGridFragment.OnEmojiconClickedListener, EmojiconsFragment.OnEmojiconBackspaceClickedListener, View.OnClickListener {
    FloatingActionButton sendMessage;
    int channel_id;
    EmojiconEditText editEmojicon;
    @BindView(R.id.tvServiceName)
    TextView tvServiceName;
    @BindView(R.id.btnHire)
    TextView btnHire;
    private View emojiconsFragment;
    LinearLayout inputeTextLayout;
    ImageView emojiIcon;
    private Handler mainThreadHandler;
    private static final int DELAY_SCROLLING_LIST = 300;
    private static final int DELAY_SHOWING_SMILE_PANEL = 200;
    private ArrayList<String> presentUsers = new ArrayList<>();
    private ArrayList<String> usersCurrentlyTyping = new ArrayList<>();
    private boolean activityPaused = false;
    private boolean typingFlag = false;
    private Handler isUserTypingHandler = new Handler();
    private ImageButton mentionBtn;
    private ArrayList<Messages> msgArrayList;
    ConversationAdapter adapter_1;
    private String reveiverId;
    private String type;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private String vendor_id, request_id, vendor_name, service_name, vendor_status;

    @Override
    protected int getContentResId() {
        return R.layout.single_chat_screen;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mainThreadHandler = new Handler(Looper.getMainLooper());
        msgArrayList = new ArrayList<>();

        vendor_id = getIntent().getStringExtra("vendor_id");
        request_id = getIntent().getStringExtra("request_id");
        vendor_name = getIntent().getStringExtra("vendor_name");
        service_name = getIntent().getStringExtra("service_name");
        vendor_status = getIntent().getStringExtra("vendor_status");

        setToolbarWithBackButton(vendor_name);

        tvServiceName.setText(service_name);
        if (vendor_status.equals("4")) {
            btnHire.setText("Hired");
        }

        reveiverId = getIntent().getStringExtra("id");
        type = "Customer";

        initRecyclerView();
        initViews();
        initListener();
        hideSmileLayout();
        msgArrayList.clear();
        getMessages();

        btnHire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btnHire.getText().toString().equals("Hire")) {
                    hireVendor();
                }
            }
        });

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                S.E("call get message");
                msgArrayList.clear();
                getMessages();
            }
        };
    }

    private void hireVendor() {
        new JSONParser(this).parseRetrofitRequest(ApiClient.getClient().create(ApiInterface.class).hiring_vendor(
                vendor_id,
                request_id
        ), new Helper() {
            @Override
            public void backResponse(String response) {
                S.E("Hire vendor response : " + response);
                if (!response.equals("error")) {
                    btnHire.setText("Hired");
                }
            }
        });
    }

    private void hideSmileLayout() {
        emojiconsFragment.setVisibility(View.GONE);
        setSmilePanelIcon(R.drawable.happy);
    }

    private void setSmilePanelIcon(int resourceId) {
        emojiIcon.setImageResource(resourceId);
    }

    private void initListener() {
        emojiIcon.setOnClickListener(this);
        sendMessage.setOnClickListener(this);
        mentionBtn.setOnClickListener(this);
        editEmojicon.setOnClickListener(this);
    }

    private void initViews() {
        mLayoutManager.setStackFromEnd(true);
        editEmojicon = (EmojiconEditText) findViewById(R.id.editEmojicon);
        sendMessage = (FloatingActionButton) findViewById(R.id.send);
        inputeTextLayout = (LinearLayout) findViewById(R.id.inpute_TextLayout);
        emojiconsFragment = (View) findViewById(R.id.emojicons);
        emojiIcon = (ImageView) findViewById(R.id.emojiIcon);
        mentionBtn = (ImageButton) findViewById(R.id.mentionBtn);
    }

    protected void initRecyclerView() {
        initViewFlipper();
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        // The number of Columns
        mLayoutManager = new LinearLayoutManager(this);
//        mLayoutManager.setReverseLayout(true);
//        mLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    private void getMessages() {
        setMainData();
        new JSONParser(this).parseRetrofitRequestWithautProgress(ApiClient.getClient().create(ApiInterface.class).chatting_history(
                UserDataHelper.getInstance().getUserDataModel().get(0).getUserId(),
                vendor_id,
                request_id
        ), new Helper() {
            @Override
            public void backResponse(String response) {
                S.E("get chat response : " + response);
                if (!response.equals("error")) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getBoolean("status")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("response");

//                            clear old message list
                            msgArrayList.clear();

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject json = jsonArray.getJSONObject(i);
                                Messages messages = new Messages();
                                messages.setMessageBY(json.getString("messageBY"));
                                messages.setMessageTo(json.getString("messageTo"));
                                messages.setChat(json.getString("chat"));
                                messages.setMessageDate(json.getString("messageDate"));
                                msgArrayList.add(messages);
                            }
                            if (msgArrayList.size() > 0) {
                                //    Collections.reverse(msgArrayList);
                                adapter_1 = new ConversationAdapter(ConversationActivity.this, msgArrayList, "1");
                                mRecyclerView.setAdapter(adapter_1);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void visibleOrHideSmilePanel() {
        if (isSmilesLayoutShowing()) {
            hideSmileLayout();
            KeyboardUtils.showKeyboard(ConversationActivity.this);
        } else {
            KeyboardUtils.hideKeyboard(ConversationActivity.this);
            showSmileLayout();
        }
        editEmojicon.requestFocus();
    }

    private boolean isSmilesLayoutShowing() {
        return emojiconsFragment.getVisibility() == View.VISIBLE;
    }

    private void showSmileLayout() {
        mainThreadHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                emojiconsFragment.setVisibility(View.VISIBLE);
                setSmilePanelIcon(R.drawable.keyboard);
            }
        }, DELAY_SHOWING_SMILE_PANEL);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.send:
                setMessageTo();
                editEmojicon.getText().clear();
                msgArrayList.clear();
                getMessages();
                adapter_1.notifyDataSetChanged();
                break;
            case R.id.emojiIcon:
                visibleOrHideSmilePanel();
                break;
            case R.id.mentionBtn:
                break;
            case R.id.editEmojicon:
                break;
        }
    }

    private void setMessageTo() {
        new JSONParser(this).parseRetrofitRequestWithautProgress(ApiClient.getClient().create(ApiInterface.class).sendChatMsg(
                request_id,
                UserDataHelper.getInstance().getUserDataModel().get(0).getUserId(),
                vendor_id,
                editEmojicon.getText().toString()
        ), new Helper() {
            @Override
            public void backResponse(String response) {
                S.E("send msg reponse : " + response);
            }
        });
    }

    @Override
    public void onEmojiconClicked(Emojicon emojicon) {
        EmojiconsFragment.input(editEmojicon, emojicon);
    }

    @Override
    public void onEmojiconBackspaceClicked(View v) {
        EmojiconsFragment.backspace(editEmojicon);

    }

    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver, new IntentFilter(Config.REGISTRATION_COMPLETE));
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver, new IntentFilter(Config.PUSH_NOTIFICATION));
        NotificationUtils.clearNotifications(this);
    }
}
