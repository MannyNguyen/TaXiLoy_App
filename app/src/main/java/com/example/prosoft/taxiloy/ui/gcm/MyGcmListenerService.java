package com.example.prosoft.taxiloy.ui.gcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.example.prosoft.taxiloy.R;
import com.example.prosoft.taxiloy.api.object_response_api.PushNotificationReponse;
import com.example.prosoft.taxiloy.ui.Model;
import com.example.prosoft.taxiloy.ui.activity.LoginScreenActivity;
import com.example.prosoft.taxiloy.ui.activity.MainScreenActivity;
import com.example.prosoft.taxiloy.ui.activity.MapScreenDriverActivity;
import com.example.prosoft.taxiloy.ui.activity.RatingScreenActivity;
import com.example.prosoft.taxiloy.ui.utils.SystemUtils;
import com.google.android.gms.gcm.GcmListenerService;
import com.google.gson.Gson;

/**
 * Created by prosoft on 2/15/16.
 */
public class MyGcmListenerService extends GcmListenerService {

    private static final String TAG = "MyGcmListenerService";
    private static int idPassenger;
    private String mMessage;
    private int pushtype;
    private String phonePassenger;
    private int idMatchLog;

    /**
     * Called when message is received.
     *
     * @param from SenderID of the sender.
     * @param data Data bundle containing message data as key/value pairs.
     *             For Set of keys use data.keySet().
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(String from, Bundle data) {

        String message = data.getString("message");
        Log.d(TAG, "From: " + from);
        Log.d(TAG, "Message: " + message);

        Gson gson = new Gson();
        PushNotificationReponse response = gson.fromJson(message, PushNotificationReponse.class);

        idMatchLog = response.idmatchlog;
        mMessage = response.body;
        pushtype = response.pushtype;
        phonePassenger = response.phone;


        Model.getInstance().typePush = pushtype;
        switch (pushtype) {
            case 1:
                if (Model.getInstance().isDriverVacant()) {
                    Model.getInstance().setIsDriverVacant(false);
                    if (idMatchLog != 0) {
                        SystemUtils.saveIDMatchLog(idMatchLog, this);
                    }
                    idPassenger = response.idpassenger;

                    if (Model.getInstance().isInApp()) {
                        Intent i = new Intent(QuickstartPreferences.A_NEW_RIDE_COMES);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtra(QuickstartPreferences.PHONE_NUMBER_OF_PASSENGER, phonePassenger);
                        i.putExtra(QuickstartPreferences.ID_PASSENGER, idPassenger);
                        actionBroadcast(i);
                        //start and looping the horntone
                        Model.getInstance().getMediaPlayer(this).start();
                        Model.getInstance().getMediaPlayer(this).setLooping(true);
                    } else {
                        Intent intent = new Intent(this, MapScreenDriverActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra(QuickstartPreferences.PHONE_NUMBER_OF_PASSENGER, phonePassenger);
                        intent.putExtra(QuickstartPreferences.ID_PASSENGER, idPassenger);
                        sendNotification(mMessage, intent);
                    }
                }
                break;
            case 2:
                if (Model.getInstance().isInApp()) {
                    Intent i = new Intent(QuickstartPreferences.THE_RIDE_CANCELED);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    actionBroadcast(i);
                } else {
                    Intent intent = new Intent(this, RatingScreenActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    sendNotification(mMessage, intent);
                }
                break;
            case 3:
                Intent intent1 = new Intent(this, LoginScreenActivity.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent1.putExtra(QuickstartPreferences.TYPE_PUSH_NOTIFICATION, pushtype);
                sendNotification(mMessage, intent1);
                break;
            case 4:
                if (Model.getInstance().isInApp()) {
                    Intent i = new Intent(QuickstartPreferences.THE_RIDE_CANCELED);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    actionBroadcast(i);
                } else {
                    Intent intent = new Intent(this, MainScreenActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    sendNotification(mMessage, intent);
                }
                break;
            case 5:
                Model.getInstance().setIsDriverVacant(true);
                if (idPassenger == response.idpassenger) {
                    Model.getInstance().getMediaPlayer(this).pause();
                    if (Model.getInstance().isInApp()) {
                        Intent i = new Intent(QuickstartPreferences.THE_RIDE_CANCELED);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        actionBroadcast(i);
                    } else {
                        Intent intent = new Intent(this, MapScreenDriverActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        sendNotification(mMessage, intent);
                    }
                }
                break;
        }


    }


    private void actionBroadcast(Intent intent) {
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    /**
     * Create and show a simple notification containing the received GCM message.
     *
     * @param message GCM message received.
     */
    private void sendNotification(String message, Intent intent) {

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.icon_logo)
                .setContentTitle("GCM Message")
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}