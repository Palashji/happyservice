package com.bluewebspark.happyservice.notification;

import android.content.Context;
import android.util.Log;

import com.bluewebspark.happyservice.data.datahelper.UserDataHelper;
import com.bluewebspark.happyservice.sohel.ApiClient;
import com.bluewebspark.happyservice.sohel.ApiInterface;
import com.bluewebspark.happyservice.sohel.Helper;
import com.bluewebspark.happyservice.sohel.JSONParser;
import com.bluewebspark.happyservice.sohel.S;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by user on 1/15/2018.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = "MyFirebaseIIDService";
    protected Context context;
    String refreshedToken;

    @Override
    public void onTokenRefresh() {
        refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.e(TAG, "Refreshed token:" + refreshedToken);
//        SavedData.savefcm_tokan_id(refreshedToken);
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String token) {
        Log.e("tokan", "sendRegistrationToServer" + token);
        UpdateFCM_ID();
    }

    private void UpdateFCM_ID() {
        if (UserDataHelper.getInstance().getUserDataModel().size() > 0) {
            new JSONParser(this).parseRetrofitRequest(ApiClient.getClient().create(ApiInterface.class).updateFcmID(
                    UserDataHelper.getInstance().getUserDataModel().get(0).getUserId(),
                    FirebaseInstanceId.getInstance().getToken()
            ), new Helper() {
                @Override
                public void backResponse(String response) {
                    S.E("update FCM ID response : " + response);
                }
            });
        }
    }
}
