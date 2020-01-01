package com.nurul.simpakat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.nurul.simpakat.common.Constanta;
import com.nurul.simpakat.common.util.PreferenceUtils;
import com.nurul.simpakat.view.home.ui.pengajuan.DetailPengajuanActivity;

import java.util.Map;

public class FirebaseNotificationService extends FirebaseMessagingService {

    private static String TAG = "Firebase Nessage";

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Remote Message: " + remoteMessage);

        if(remoteMessage.getNotification() != null) {
//            if(remoteMessage.getData().size() > 0) {
//                handleMessage(remoteMessage);
//            }
            handleMessage(remoteMessage);
        }
    }

    @Override
    public void onNewToken(@NonNull String s) {
        Log.d(TAG, "Firebase Token : " + s);
        PreferenceUtils preferenceUtils = new PreferenceUtils(getApplicationContext(), Constanta.APPLICATION_PREFERENCE);
        preferenceUtils.putString(Constanta.PREF_FCM_TOKEN, s);
//        super.onNewToken(s);
    }

    private void handleMessage(RemoteMessage remoteMessage) {
        String channelId = "com.nurul.simpakat.notif";
        String descriptions = "Simpakat Notif Pengajuan";
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel notificationChannel;
        Notification.Builder builderNotif;

        Intent intent = new Intent(getApplicationContext(), DetailPengajuanActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra("channelId", channelId);
        intent.putExtra("idnotify", 1010);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel(channelId, descriptions, NotificationManager.IMPORTANCE_HIGH);
            // Sets whether notifications posted to this channel should display notification lights
            notificationChannel.enableLights(true);
            // Sets whether notification posted to this channel should vibrate.
            notificationChannel.enableVibration(true);
            // Sets the notification light color for notifications posted to this channel
            notificationChannel.setLightColor(Color.GREEN);
            // Sets whether notifications posted to this channel appear on the lockscreen or not
//            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

            notificationManager.createNotificationChannel(notificationChannel);

            builderNotif = new Notification.Builder(getApplicationContext(), channelId)
                    .setContentTitle(remoteMessage.getNotification().getTitle())
                    .setContentText(remoteMessage.getNotification().getBody())
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setSmallIcon(R.drawable.ic_event_note_notification)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.logo_apps))
                    .setAutoCancel(true)
                    .setOnlyAlertOnce(true)
                    .setColor(getColor(R.color.colorPrimary))
                    .setPriority(Notification.PRIORITY_HIGH)
//                    .addAction(R.drawable.ic_launcher, "Hangup", pendingIntentHangup)
//            .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.ic_launcher))
                    .setContentIntent(pendingIntent);

            notificationManager.notify(Constanta.NOTIFY_SUBMISSION, builderNotif.build());
        } else {
            builderNotif = new Notification.Builder(this)
                    .setContentTitle(remoteMessage.getNotification().getTitle())
                    .setContentText(remoteMessage.getNotification().getBody())
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setSmallIcon(R.drawable.ic_event_note_notification)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.logo_apps))
                    .setAutoCancel(true)
                    .setOnlyAlertOnce(true)
                    .setColor(getApplicationContext().getResources().getColor(R.color.colorPrimary))
                    .setPriority(Notification.PRIORITY_HIGH)
//                    .addAction(R.drawable.ic_launcher, "Hangup", pendingIntentHangup)
//            .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.ic_launcher))
                    .setContentIntent(pendingIntent);

            notificationManager.notify(Constanta.NOTIFY_SUBMISSION, builderNotif.build());
        }
    }
}
