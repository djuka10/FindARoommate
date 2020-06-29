package rs.ac.uns.ftn.findaroommate.receiver;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import rs.ac.uns.ftn.findaroommate.MainActivity;
import rs.ac.uns.ftn.findaroommate.R;
import rs.ac.uns.ftn.findaroommate.activity.ProfileActivity;
import rs.ac.uns.ftn.findaroommate.activity.SettingsActivity;
import rs.ac.uns.ftn.findaroommate.activity.UserStayActivity;
import rs.ac.uns.ftn.findaroommate.model.User;
import rs.ac.uns.ftn.findaroommate.utils.AppTools;

public class BookReceiver extends BroadcastReceiver {
    private static int notificationID = 3;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("REZ", "onReceive");

        if(intent.getAction().equals(MainActivity.BOOKING)){
            String adOwner = intent.getExtras().getString("adOwner", "");
            String adStatus = intent.getExtras().getString("adStatus", "IDLE");
            String adTitle = intent.getExtras().getString("adTitle", "AD_TITLE");

            String notif_type = intent.getExtras().getString("notif_type", "local");



            String contentAction = context.getString(R.string.notif_profile_action);
            String bookingMessagePattern = context.getString(R.string.notif_booking_text);
            String bookingMessage = "";
            if(notif_type.equals("local")){
                bookingMessage = bookingMessagePattern
                        .replace("%AD%", adOwner)
                        .replace("%STATUS%", adStatus)
                        .replace("%TITLE%", adTitle);
            } else {
                bookingMessage = intent.getExtras().getString("remote_message", "");
            }

            Intent profileIntent = new Intent(context, ProfileActivity.class);
            PendingIntent pProfileIntent = PendingIntent.getActivity(context, 0, profileIntent, 0);

            Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_logo);

            NotificationManager mNotificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "CHANEL_ID")
                    .setSmallIcon(R.drawable.ic_bed)
                    .setContentTitle(context.getString(R.string.notif_booking_title))
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(bookingMessage
                            ))
                    .addAction(R.drawable.ic_profile, contentAction, pProfileIntent)
                    .setLargeIcon(bm);

            mNotificationManager.notify(notificationID, mBuilder.build());
        }
    }
}
