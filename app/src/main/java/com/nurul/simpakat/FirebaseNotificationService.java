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
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nurul.simpakat.common.Constanta;
import com.nurul.simpakat.common.util.PreferenceUtils;
import com.nurul.simpakat.view.dekan.HomeDekanActivity;
import com.nurul.simpakat.view.home.ui.pengajuan.DetailPengajuanActivity;
import com.nurul.simpakat.view.warek.HomeWarekActivity;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import static com.nurul.simpakat.common.Constanta.APPLICATION_PATH;
import static com.nurul.simpakat.common.Constanta.APPLICATION_URL;

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
        if(preferenceUtils.getString(Constanta.PREF_ID, null) != null) {
            updateDeviceToken(s);
        }
//        super.onNewToken(s);
    }

    private void handleMessage(RemoteMessage remoteMessage) {
        PreferenceUtils preferenceUtils = new PreferenceUtils(getApplicationContext(), Constanta.APPLICATION_PREFERENCE);
        String channelId = "com.nurul.simpakat.notif";
        String descriptions = "Simpakat Notif Pengajuan";
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel notificationChannel;
        Notification.Builder builderNotif;

        String jabatan = preferenceUtils.getString(Constanta.PREF_JABATAN, "");
        Intent intent = null;
        if (jabatan.equals("Unit Kerja")) {
            intent = new Intent(getApplicationContext(), DetailPengajuanActivity.class);
        } else if (jabatan.equals("Dekan")) {
            intent = new Intent(getApplicationContext(), HomeDekanActivity.class);
        } else if (jabatan.equals("Wakil Rektor 1")) {
            intent = new Intent(getApplicationContext(), HomeWarekActivity.class);
        } else if (jabatan.equals("Wakil Rektor 2")) {
            intent = new Intent(getApplicationContext(), HomeWarekActivity.class);
        }

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
                    .setStyle(new Notification.BigTextStyle().bigText(remoteMessage.getNotification().getBody()))
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
                    .setStyle(new Notification.BigTextStyle().bigText(remoteMessage.getNotification().getBody()))
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

    private void updateDeviceToken(String token) {
        PreferenceUtils preferenceUtils = new PreferenceUtils(getApplicationContext(), Constanta.APPLICATION_PREFERENCE);
        String url = APPLICATION_URL+APPLICATION_PATH+"simpakat_update_user_device_token.php";
        AsyncHttpClient client = new AsyncHttpClient(true,80,443);
        client.setTimeout(60000);
        RequestParams params = new RequestParams();
        params.add("nip", preferenceUtils.getString(Constanta.PREF_ID, ""));
        params.add("device_token", token);
        params.setUseJsonStreamer(true);

        client.get(url,params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                String response = null;
                try {
                    response = new String(responseBody, "UTF-8");
                    Log.d("respond","response update : " + response);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                String response;
                try {
                    response = new String(responseBody, "UTF-8");
                    Log.d("error",response);
                    //Toast.makeText(EditBusinessUnitWizardActivity.this,"Cannot Connect to server",Toast.LENGTH_LONG).show();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
