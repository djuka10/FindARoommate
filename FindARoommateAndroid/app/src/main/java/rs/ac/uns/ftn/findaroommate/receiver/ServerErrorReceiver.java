package rs.ac.uns.ftn.findaroommate.receiver;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import rs.ac.uns.ftn.findaroommate.MainActivity;
import rs.ac.uns.ftn.findaroommate.R;
import rs.ac.uns.ftn.findaroommate.activity.SettingsActivity;
import rs.ac.uns.ftn.findaroommate.activity.UserStayActivity;
import rs.ac.uns.ftn.findaroommate.model.User;
import rs.ac.uns.ftn.findaroommate.utils.AppTools;

public class ServerErrorReceiver extends BroadcastReceiver {

    private static int notificationID = 2;
    String GROUP_KEY_ServerError = "ServerError";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("REZ", "onReceive");

        if(intent.getAction().equals(MainActivity.SERVER_ERROR)){
            String error_message = intent.getExtras().getString("error_message", "Unknown server error");

            Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_logo);

            NotificationManager mNotificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "CHANEL_ID")
                    .setSmallIcon(R.drawable.ic_error)
                    .setContentTitle(context.getString(R.string.notif_server_error_title))
                    .setStyle(new NotificationCompat.BigTextStyle()
                    .bigText(error_message))
                    .setGroup(GROUP_KEY_ServerError)
                    .setLargeIcon(bm);

            mNotificationManager.notify(notificationID, mBuilder.build());
        }
    }
}
