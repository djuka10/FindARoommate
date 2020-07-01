package rs.ac.uns.ftn.findaroommate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.ListPreference;
import androidx.preference.PreferenceManager;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.activeandroid.ActiveAndroid;
import com.facebook.FacebookSdk;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import rs.ac.uns.ftn.findaroommate.activity.HomepageActivity;
import rs.ac.uns.ftn.findaroommate.activity.NewAdActivity;
import rs.ac.uns.ftn.findaroommate.activity.ProfileActivity;
import rs.ac.uns.ftn.findaroommate.activity.SettingsActivity;
import rs.ac.uns.ftn.findaroommate.activity.SignUpHomeActivity;
import rs.ac.uns.ftn.findaroommate.activity.UserReviewActivity;
import rs.ac.uns.ftn.findaroommate.activity.UserStayActivity;
import rs.ac.uns.ftn.findaroommate.model.Ad;
import rs.ac.uns.ftn.findaroommate.model.Language;
import rs.ac.uns.ftn.findaroommate.model.Message;
import rs.ac.uns.ftn.findaroommate.model.User;
import rs.ac.uns.ftn.findaroommate.receiver.BookReceiver;
import rs.ac.uns.ftn.findaroommate.receiver.ReviewReceiver;
import rs.ac.uns.ftn.findaroommate.receiver.ServerErrorReceiver;
import rs.ac.uns.ftn.findaroommate.receiver.UpcomingStayReceiver;
import rs.ac.uns.ftn.findaroommate.service.DemoService;
import rs.ac.uns.ftn.findaroommate.service.SyncService;
import rs.ac.uns.ftn.findaroommate.service.UpcomingStayService;
import rs.ac.uns.ftn.findaroommate.utils.AppTools;

public class MainActivity extends AppCompatActivity {

    private Boolean initDataFlag;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;

    //Sync stuff
    private PendingIntent pendingIntent;
    private AlarmManager alarmManager;

    private UpcomingStayReceiver upcomingStayReceiver;
    public static String UPCOMING_STAY = "UPCOMING_STAY";

    private ServerErrorReceiver serverErrorReceiver;
    public static String SERVER_ERROR = "SERVER_ERROR";

    private BookReceiver bookReceiver;
    public static String BOOKING = "BOOKING";

    private ReviewReceiver reviewReceiver;
    public static String REVIEW = "REVIEW";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FacebookSdk.sdkInitialize(getApplicationContext());
        //AppEventsLogger.activateApp(this);

        User loggedUserModel = AppTools.getLoggedUser();
        if(loggedUserModel == null){
            Log.e("login error", "No one is logged");
        }

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        ActiveAndroid.initialize(this);

        Message m = Message.builder().title("title").message("message").build();
//        Message m = new Message(1, 1, "My title", "My message");
        TextView textView = (TextView)findViewById(R.id.textView);
        textView.setText(m.getTitle() + m.getMessage());

        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HomepageActivity.class);
                startActivity(intent);
            }
        });


        boolean logged = false;

        if(currentUser == null) {
            logged = false;
        } else {
            logged = true;
        }

        if(logged){
            Intent intent = new Intent(MainActivity.this, HomepageActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(MainActivity.this, SignUpHomeActivity.class);
            startActivity(intent);
        }

        setUpLanguage();
        sync();
        setUpReceiver();
        createNotificationChannel();

    }

    @Override
    protected void onPause() {
////        if (alarmManager != null) {
////            alarmManager.cancel(pendingIntent);
////        }
//
//        //osloboditi resurse
//        if(upcomingStayReceiver != null){
//            unregisterReceiver(upcomingStayReceiver);
//        }

        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (alarmManager == null) {
            setUpReceiver();
        }

        Calendar calendar = Calendar.getInstance();
        //calendar.setTimeInMillis(System.currentTimeMillis());
        //calendar.add(Calendar.SECOND, 30);
        calendar.add(Calendar.MINUTE, 3);


        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),
                AppTools.calculateTimeTillNextSync(3), pendingIntent);

//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
//                AlarmManager.INTERVAL_DAY, pendingIntent);

        IntentFilter upcomingStayIntentFilter = new IntentFilter();
        upcomingStayIntentFilter.addAction(UPCOMING_STAY);
        registerReceiver(upcomingStayReceiver, upcomingStayIntentFilter);

        IntentFilter serverErrorIntent = new IntentFilter();
        serverErrorIntent.addAction(SERVER_ERROR);
        registerReceiver(serverErrorReceiver, serverErrorIntent);

        IntentFilter bookingIntent = new IntentFilter();
        bookingIntent.addAction(BOOKING);
        registerReceiver(bookReceiver, bookingIntent);

        IntentFilter reviewIntent = new IntentFilter();
        reviewIntent.addAction(REVIEW);
        registerReceiver(reviewReceiver, reviewIntent);
    }

    private void sync(){
        Intent alarmIntent = new Intent(this, SyncService.class);
        startService(alarmIntent);
    }

    private void setUpLanguage(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String lang = preferences.getString("language", "en");
        if (lang.equals("serbian")){
            Locale serbian = new Locale("sr", "RS");

            Locale.setDefault(serbian);

            Configuration config = new Configuration();
            config.locale = serbian;
            getBaseContext().getResources().updateConfiguration(config,
                    getBaseContext().getResources().getDisplayMetrics());
        }
    }

    private void setUpReceiver() {
        upcomingStayReceiver = new UpcomingStayReceiver();
        serverErrorReceiver = new ServerErrorReceiver();
        bookReceiver = new BookReceiver();
        reviewReceiver = new ReviewReceiver();

        Intent upcomingStayIntent = new Intent(this, UpcomingStayService.class);
        pendingIntent = PendingIntent.getService(this, 0, upcomingStayIntent, 0);

        // privremeno se koristi
        try {
            pendingIntent.send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }

        alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("CHANEL_ID", "CHANEL NAME", importance);
            channel.setDescription("Channel desc");
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}
