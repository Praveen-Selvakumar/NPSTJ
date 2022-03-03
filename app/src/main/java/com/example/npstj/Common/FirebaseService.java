package com.example.npstj.Common;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.npstj.R;
import com.example.npstj.mainframe.Home_Page;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FirebaseService extends FirebaseMessagingService{

    private static final String TAG = "MyFirebaseMsgService";
        public FirebaseService() {
            super();
        }

        @Override
        public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
            super.onMessageReceived(remoteMessage);

            if (remoteMessage.getNotification() != null) {
                sendNotification(remoteMessage.getNotification().getBody());

                //content_type  :""circular test



           /* try{
                String title;
                String body;
                JSONObject jsonObject = new JSONObject(remoteMessage.getNotification().getBody());
                title = jsonObject.getString("title");
                body = jsonObject.getString("body");
                sendNotification(remoteMessage.getNotification().getBody());
                Toast.makeText(getApplicationContext(), title, Toast.LENGTH_SHORT).show();
            }catch (Exception e){
            }*/

            }
        }

        @Override
        public void onNewToken(@NonNull String s) {
            super.onNewToken(s);
        }

        private void sendNotification(String message) {

        /*String title;
        String body;*/

            try{
            /*JSONObject jsonObject = new JSONObject(messageBody);
            title = jsonObject.getString("title");
            body = jsonObject.getString("body");*/
                Log.d("test_msg", message);
                Intent intent = new Intent(this, Home_Page.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                        PendingIntent.FLAG_ONE_SHOT);

                Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.carmel_notification_icon);
                String channelId = getString(R.string.default_notification_channel_id);
                Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                NotificationCompat.Builder notificationBuilder =
                        new NotificationCompat.Builder(this, channelId)
                                .setLargeIcon(largeIcon)
                                .setSmallIcon(R.drawable.carmel_notification_icon)
                                .setColor(getResources().getColor(android.R.color.white))
                                .setContentTitle(getResources().getString(R.string.notification_name))
                                .setContentText(message)
                                .setAutoCancel(true)
                                .setSound(defaultSoundUri)
                                .setContentIntent(pendingIntent);

                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                // Since android Oreo notification channel is needed.
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    String id = getString(R.string.default_notification_channel_id);
                    CharSequence name = getString(R.string.app_name);
                    String description = message;
                    int importance = NotificationManager.IMPORTANCE_LOW;
                    NotificationChannel mChannel = new NotificationChannel(id, name, importance);
                    mChannel.setDescription(description);
                    mChannel.setShowBadge(true);
                    NotificationChannel channel = new NotificationChannel(channelId,
                            "Channel human readable title",
                            NotificationManager.IMPORTANCE_DEFAULT);
                    notificationManager.createNotificationChannel(channel);

                }

                notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
            }catch (Exception e){

            }
        }

}
