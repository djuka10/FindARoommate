package rs.ac.uns.ftn.findaroommate.service;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
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

            String action = remoteMessage.getData().get("fcmAction");
            if(action.equals("chat")){
                chat(remoteMessage);
            }
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Title: " + remoteMessage.getNotification().getTitle());
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());

            String fcmAction = remoteMessage.getData().get("fcmAction");
            if (fcmAction != null){
                switch (fcmAction){
                    case "booking": bookingNotification(remoteMessage); break;
                    case "review": reviewNotification(remoteMessage); break;
                    default:
                        chat(remoteMessage);
                }
            }

        }
    }

    private void chat(RemoteMessage remoteMessage){
        ActivityManager am = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if(preferences.getBoolean("new_message_notif", false)){
            String sender = remoteMessage.getData().get("sender");
            String senderId = remoteMessage.getData().get("senderId");
            String message = remoteMessage.getData().get("message");
            Intent intent = new Intent(MainActivity.CHAT);
            intent.putExtra("sender", sender);
            intent.putExtra("senderId", senderId);
            intent.putExtra("message", message);
            sendBroadcast(intent);
        }
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

        String userIdStr = data.get("userId");
        int userId = 0;
        if(!userIdStr.isEmpty()){
            userId = Integer.parseInt(userIdStr);
        }

        Ad ad = Ad.getOneGlobal(adEntityId);
        ad.setAdStatus(adStatus);
        ad.setUserId(userId);
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
