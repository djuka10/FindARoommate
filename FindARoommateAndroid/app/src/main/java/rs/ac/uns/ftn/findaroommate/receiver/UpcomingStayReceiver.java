package rs.ac.uns.ftn.findaroommate.receiver;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.provider.Settings;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import rs.ac.uns.ftn.findaroommate.MainActivity;
import rs.ac.uns.ftn.findaroommate.R;
import rs.ac.uns.ftn.findaroommate.activity.SettingsActivity;
import rs.ac.uns.ftn.findaroommate.activity.UserStayActivity;
import rs.ac.uns.ftn.findaroommate.model.User;
import rs.ac.uns.ftn.findaroommate.utils.AppTools;

public class UpcomingStayReceiver extends BroadcastReceiver {
    private static int notificationID = 1;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("REZ", "onReceive");

        NotificationManager mNotificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "CHANEL_ID");

        if(intent.getAction().equals(MainActivity.UPCOMING_STAY)){
            String reminder_days = intent.getExtras().getString("reminder_days");

            Bitmap bm = null;

            Intent staysintent = new Intent(context, UserStayActivity.class);
            PendingIntent pStaysintent = PendingIntent.getActivity(context, 0, staysintent, 0);

            Intent settingsIntent = new Intent(context, SettingsActivity.class);
            PendingIntent pIntentSettings = PendingIntent.getActivity(context, 0, settingsIntent, 0);


            bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_logo);
            mBuilder.setSmallIcon(R.drawable.ic_history);
            User user = AppTools.getLoggedUser();
            String contentAction = context.getString(R.string.notif_upcoming_stays_action);
            String contextText = context.getString(R.string.notif_upcoming_stays_text);

            mBuilder.setContentTitle(context.getString(R.string.notif_upcoming_stays_title));
            mBuilder.setStyle(new NotificationCompat.BigTextStyle()
                    .bigText(contextText
                            .replace("%NAME%", user.getFirstName())
                            .replace("%ACTION%", contentAction)
                            .replace("%DAY%", reminder_days)
                            ));
            mBuilder.addAction(R.drawable.ic_history, contentAction, pStaysintent);
            mBuilder.setColor(Color.BLUE);
            mBuilder.addAction(R.drawable.ic_settings, context.getString(R.string.notif_settings_action), pIntentSettings);

            mBuilder.setLargeIcon(bm);
            mNotificationManager.notify(notificationID, mBuilder.build());

        }
    }
}
