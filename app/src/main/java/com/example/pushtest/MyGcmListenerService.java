package com.example.pushtest;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;

import com.google.android.gms.gcm.GcmListenerService;
import com.kii.cloud.storage.DirectPushMessage;
import com.kii.cloud.storage.KiiUser;
import com.kii.cloud.storage.PushMessageBundleHelper;
import com.kii.cloud.storage.PushToAppMessage;
import com.kii.cloud.storage.PushToUserMessage;
import com.kii.cloud.storage.ReceivedMessage;
import com.kii.cloud.storage.utils.Log;

public class MyGcmListenerService extends GcmListenerService {
    private static final String TAG = "MyGcmListenerService";

    @Override
    public void onMessageReceived(String from, Bundle data) {
        ReceivedMessage message = PushMessageBundleHelper.parse(data);
        KiiUser sender = message.getSender();
        PushMessageBundleHelper.MessageType type = message.pushMessageType();
        switch (type) {
            case PUSH_TO_APP:
                PushToAppMessage appMsg = (PushToAppMessage)message;
                Log.d(TAG, "PUSH_TO_APP Received");
                break;
            case PUSH_TO_USER:
                PushToUserMessage userMsg = (PushToUserMessage)message;
                Log.d(TAG, "PUSH_TO_USER Received");
                break;
            case DIRECT_PUSH:
                DirectPushMessage directMsg = (DirectPushMessage)message;
                Log.d(TAG, "DIRECT_PUSH Received");
                break;
        }
    }

//// Sample code for displaying a message in the status bar
//  private void sendNotification(String message) {
//    Intent intent = new Intent(this, MainActivity.class);
//    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
//        PendingIntent.FLAG_ONE_SHOT);
//
//    Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
//        .setSmallIcon(R.drawable.ic_stat_ic_notification)
//        .setContentTitle("GCM Message")
//        .setContentText(message)
//        .setAutoCancel(true)
//        .setSound(defaultSoundUri)
//        .setContentIntent(pendingIntent);
//
//    NotificationManager notificationManager =
//        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//    notificationManager.notify(0, notificationBuilder.build());
//  }

}
