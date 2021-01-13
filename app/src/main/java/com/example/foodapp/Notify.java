package com.example.foodapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;
import java.util.Random;

public class Notify extends FirebaseMessagingService {

    public static final String NOTIFICATION_ID_1 = "channel1";

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if(remoteMessage.getData().isEmpty())
            showNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());

        else
            showNotification(remoteMessage.getData());
    }

    private void showNotification(Map<String, String> data) {

        String title  = data.get("title").toString();
        String body = data.get("Body").toString();
        NotificationManager manegeNotify = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_ID_1, "Notification", NotificationManager.IMPORTANCE_HIGH);

            channel.setDescription("it's approve");
            channel.setLightColor(Color.RED);
        }

        NotificationCompat.Builder compat = new NotificationCompat.Builder(this, NOTIFICATION_ID_1);
        compat.setAutoCancel(true).setDefaults(Notification.DEFAULT_ALL).setWhen(System.currentTimeMillis()).setSmallIcon(R.drawable.ic_approve)
                .setContentTitle(title).setContentText(body);
        manegeNotify.notify(new Random().nextInt(), compat.build());
    }

    private void showNotification(String title, String body) {
        NotificationManager manegeNotify = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_ID_1, "Notification", NotificationManager.IMPORTANCE_HIGH);

            channel.setDescription("it's approve");
            channel.setLightColor(Color.RED);
        }

        NotificationCompat.Builder compat = new NotificationCompat.Builder(this, NOTIFICATION_ID_1);
        compat.setAutoCancel(true).setDefaults(Notification.DEFAULT_ALL).setWhen(System.currentTimeMillis()).setSmallIcon(R.drawable.ic_approve)
                .setContentTitle(title).setContentText(body);
        manegeNotify.notify(new Random().nextInt(), compat.build());



    }
}
