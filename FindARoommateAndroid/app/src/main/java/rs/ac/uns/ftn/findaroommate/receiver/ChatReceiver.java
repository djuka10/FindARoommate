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
import rs.ac.uns.ftn.findaroommate.activity.MessageActivity;
import rs.ac.uns.ftn.findaroommate.activity.MessagesActivity;
import rs.ac.uns.ftn.findaroommate.activity.ProfileActivity;

public class ChatReceiver extends BroadcastReceiver {
    private static int notificationID = 5;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("REZ", "onReceive");

        if(intent.getAction().equals(MainActivity.CHAT)){

            String sender = intent.getExtras().getString("sender", "local");
            String senderId = intent.getExtras().getString("senderId", "local");
            String message = intent.getExtras().getString("message", "local");


            String contentAction = context.getString(R.string.messenger);

            Intent chatIntent = new Intent(context, MessageActivity.class);
            chatIntent.putExtra("receiverId", Integer.parseInt(senderId));
            PendingIntent pChatIntent = PendingIntent.getActivity(context, 0, chatIntent, 0);

            Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_logo);

            NotificationManager mNotificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "CHANEL_ID")
                    .setSmallIcon(R.drawable.ic_email)
                    .setContentTitle(sender)
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(message
                            ))
                    .addAction(R.drawable.ic_email, contentAction, pChatIntent)
                    .setLargeIcon(bm);

            mNotificationManager.notify(notificationID, mBuilder.build());
        }
    }
}
