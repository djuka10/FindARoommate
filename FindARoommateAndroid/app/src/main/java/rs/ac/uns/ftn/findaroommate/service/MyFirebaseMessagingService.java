package rs.ac.uns.ftn.findaroommate.service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceManager;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import rs.ac.uns.ftn.findaroommate.MainActivity;
import rs.ac.uns.ftn.findaroommate.model.Ad;
import rs.ac.uns.ftn.findaroommate.model.Review;
import rs.ac.uns.ftn.findaroommate.utils.AdStatus;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "FirebaseMessagingService";

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        //super.onMessageReceived(remoteMessage);

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
                scheduleJob();
            } else {
                // Handle message within 10 seconds
                handleNow();
            }
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Title: " + remoteMessage.getNotification().getTitle());
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());

            String fcmAction = remoteMessage.getData().get("fcmAction");
            switch (fcmAction){
                case "booking": bookingNotification(remoteMessage); break;
                case "review": reviewNotification(remoteMessage); break;
                default: break;
            }
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    private void bookingNotification(RemoteMessage remoteMessage){
        updateAd(remoteMessage.getData());

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if(preferences.getBoolean("stay_notif", false)){
            Intent intent = new Intent(MainActivity.BOOKING);
            intent.putExtra("remote_message", remoteMessage.getNotification().getBody());
            intent.putExtra("notif_type", "server");
            sendBroadcast(intent);
        }
    }

    private void reviewNotification(RemoteMessage remoteMessage){
        updateReview(remoteMessage.getData());

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if(preferences.getBoolean("new_review_notif", false)){
            Intent intent = new Intent(MainActivity.REVIEW);
            intent.putExtra("notif_review_message", remoteMessage.getNotification().getBody());
            sendBroadcast(intent);
        }
    }

    private void updateReview(Map<String, String> data){
        String title = data.get("title");
        String comment = data.get("comment");
        String reviewerName = data.get("reviewerName");

        String entityIdStr = data.get("entityId");
        String ratingStr = data.get("rating");
        String authorStr = data.get("author");
        String ratedUserStr = data.get("ratedUser");
        String adStr = data.get("ad");

        int entityId = Integer.parseInt(entityIdStr);
        int rating = Integer.parseInt(ratingStr);
        int author = Integer.parseInt(authorStr);
        int ratedUser = Integer.parseInt(ratedUserStr);
        int ad = Integer.parseInt(adStr);

        Review newReview = new Review(entityId, rating, comment, title, reviewerName, author, ratedUser, ad);
        newReview.save();

    }


    private void updateAd(Map<String, String> data){
        Log.d(TAG, "Message Notification key-value: " + data.get("adEntityId"));
        int adEntityId = Integer.parseInt(data.get("adEntityId"));
        AdStatus adStatus = AdStatus.valueOf(data.get("adStatus"));

        Ad ad = Ad.getOneGlobal(1);
        ad.setAdStatus(adStatus);
        ad.save();
    }

    @Override
    public void onNewToken(@NonNull String s) {
        //super.onNewToken(s);

        Log.d(TAG, "Refreshed token: " + s);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        //sendRegistrationToServer(s);
    }

    private void scheduleJob(){

    }

    private void handleNow(){

    }

}
