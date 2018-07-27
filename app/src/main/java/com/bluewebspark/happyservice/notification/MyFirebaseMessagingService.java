package com.bluewebspark.happyservice.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.bluewebspark.happyservice.R;
import com.bluewebspark.happyservice.activity.MainActivity;
import com.bluewebspark.happyservice.sohel.S;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

/**
 * Created by user on 1/15/2018.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";
    String title, message, imageUrl, timestamp, action, userType, Message_Type;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //Displaying data in log
        Log.e(TAG, "From: " + remoteMessage.getFrom());
        Log.e(TAG, "From Data" + remoteMessage.getData().toString());
        handleDataMessage(remoteMessage.getFrom().toString());
    }

    //only show Chat Notification methode
    private void chatNotification(String messageBody, String imageUrl) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());
    }

    private void handleDataMessage(String json) {
        Log.e(TAG, "push json ttt" + json.toString());
        chatNotification(message, imageUrl);
        ChatSendNotification();
    }

    //Only BroadCast reciver Chat Methode
    private void ChatSendNotification() {
        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            Log.e("check if", "......");
            // app is not in foreground, broadcast the push message
            Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
            pushNotification.putExtra("message", String.valueOf(message));
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

            // play notification sound
            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            notificationUtils.playNotificationSound();

        } else {
            // app is in background, show the notification in notification tray
            Log.e("check if", "......");
            try {
                Log.e("check if", "......its working ");
                JSONObject jsonObject1 = new JSONObject(message);
                String msg = jsonObject1.getString("msg");
                chatNotification(msg, imageUrl);

            } catch (Exception e) {
                S.E("Message");
            }
        }
    }
}