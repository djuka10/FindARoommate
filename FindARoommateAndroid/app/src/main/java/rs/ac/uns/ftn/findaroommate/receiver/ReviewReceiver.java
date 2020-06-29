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
import rs.ac.uns.ftn.findaroommate.activity.ProfileActivity;

public class ReviewReceiver extends BroadcastReceiver {
    private static int notificationID = 4;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("REZ", "onReceive");

        if(intent.getAction().equals(MainActivity.REVIEW)){

            String notif_review_message = intent.getExtras().getString("notif_review_message", "local");

            String contentAction = context.getString(R.string.notif_profile_action);

            Intent profileIntent = new Intent(context, ProfileActivity.class);
            PendingIntent pProfileIntent = PendingIntent.getActivity(context, 0, profileIntent, 0);

            Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_logo);

            NotificationManager mNotificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "CHANEL_ID")
                    .setSmallIcon(R.drawable.ic_star)
                    .setContentTitle(context.getString(R.string.notif_review_title))
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(notif_review_message
                            ))
                    .addAction(R.drawable.ic_profile, contentAction, pProfileIntent)
                    .setLargeIcon(bm);

            mNotificationManager.notify(notificationID, mBuilder.build());
        }
    }
}
