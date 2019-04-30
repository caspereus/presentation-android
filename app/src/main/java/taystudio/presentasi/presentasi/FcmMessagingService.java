package taystudio.presentasi.presentasi;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

import java.util.Map;

public class FcmMessagingService extends FirebaseMessagingService{
    String type = "",message,title,id;
    public void onMessageReceived(RemoteMessage remoteMessage)
    {
        if (remoteMessage.getData() != null)
        {
            type = "json";
            sendNotification(remoteMessage);
        }
    }

    private void sendNotification(RemoteMessage messageBody) {
        if (type.equals("json")){
            try {
                Map<String,String> data = messageBody.getData();
                title = data.get("title");
                message = data.get("message");
            } catch (Exception ex) {
                Log.e("JSON", ex.getMessage());
            }
        }

        Intent intent = new Intent(this,HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            @SuppressLint("WrongConstant") NotificationChannel notificationChannel = new NotificationChannel("PERSONAL_APP","PERSONAL Notifications",NotificationManager.IMPORTANCE_MAX);

            notificationChannel.setDescription("Tested");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setVibrationPattern(new long[]{0,1000,500,1000});
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder notifcompat =  new NotificationCompat.Builder(this,"PERSONAL_APP");
        notifcompat.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentTitle(title)
                .setContentText(message)
                .setContentInfo("info");
        notifcompat.setContentIntent(pendingIntent);
        notificationManager.notify(1,notifcompat.build());

    }
}
