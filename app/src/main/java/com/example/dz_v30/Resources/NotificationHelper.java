package com.example.dz_v30.Resources;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.dz_v30.Activities.HomeActivity;
import com.example.dz_v30.R;

public class NotificationHelper extends ContextWrapper {
    public static final String channelID = "0";
    public static final String channelName = "Alert for Medicines";
    private NotificationManager mManager;
    public NotificationHelper(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
        }
    }
    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);
        getManager().createNotificationChannel(channel);
    }
    public NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }
    public NotificationCompat.Builder getChannelNotification() {
//        Intent repeat_intent =new Intent(getApplicationContext(),HomeActivity.class);
//         repeat_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent pendingIntent=PendingIntent.getActivity(getApplicationContext(),1,repeat_intent,PendingIntent.FLAG_UPDATE_CURRENT);

        Intent fullScreenIntent = new Intent(this, HomeActivity.class);
        PendingIntent fullScreenPendingIntent = PendingIntent.getActivity(this, 0, fullScreenIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        return new NotificationCompat.Builder(this, channelID)
                .setContentIntent(fullScreenPendingIntent)
                .setContentTitle("Medicines List")
                .setContentText("It is time for your medicines.")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSmallIcon(R.drawable.ic_android)
                .setAutoCancel(true);
    }
}
