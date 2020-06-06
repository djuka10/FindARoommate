package rs.ac.uns.ftn.findaroommate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.activeandroid.ActiveAndroid;
import com.facebook.FacebookSdk;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import rs.ac.uns.ftn.findaroommate.activity.HomepageActivity;
import rs.ac.uns.ftn.findaroommate.activity.ProfileActivity;
import rs.ac.uns.ftn.findaroommate.activity.SignUpHomeActivity;
import rs.ac.uns.ftn.findaroommate.model.Ad;
import rs.ac.uns.ftn.findaroommate.model.Language;
import rs.ac.uns.ftn.findaroommate.model.Message;
import rs.ac.uns.ftn.findaroommate.model.User;
import rs.ac.uns.ftn.findaroommate.service.DemoService;
import rs.ac.uns.ftn.findaroommate.utils.AppTools;

public class MainActivity extends AppCompatActivity {

    private Boolean initDataFlag;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;

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

        initDataFlag = false;
        Boolean initPeraFlag = false;

        if(initPeraFlag)
            initUser();

        if(initDataFlag)
            initData();

        List<Ad> storedItems = Ad.getAllAds();

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
            Intent intent = new Intent(MainActivity.this, ProfileActivity .class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(MainActivity.this, SignUpHomeActivity.class);
            startActivity(intent);
        }

        setUpReceiver();

    }



    private void setUpReceiver(){

        // Retrieve a PendingIntent that will perform a broadcast
        Intent alarmIntent = new Intent(this, DemoService.class);
        startService(alarmIntent);
        //PendingIntent pendingIntent = PendingIntent.getService(this, 0, alarmIntent, 0);
        // manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
    }

/*    @Override
    protected void onStart() {
        super.onStart();

        if(currentUser == null) {
            SendUserLoginActivity();
        } else {
            SendUserHomeActivity();
        }

    }

    private void SendUserHomeActivity() {
        Intent intent = new Intent(MainActivity.this, HomepageActivity.class);
        startActivity(intent);
    }

    private void SendUserLoginActivity() {

        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }*/

    private void initData() {

        Ad ad = new Ad().builder().build();
        ad.setFlatM2(25);
        ad.setAdType("type");
        ad.setAvailableFrom(new Date());
        ad.setAvailableUntil(new Date());
        ad.setBoysNum(3);
        ad.setCostsIncluded(true);
        ad.setDeposit(300);
        ad.setDescription("Brutalna stancuga u centru grada");
        ad.setLadiesNum(2);
        ad.setLatitude(25);
        ad.setLongitude(25);
        ad.setMaxPerson(5);
        ad.setMinDays("1-5 months");
        ad.setPrice(322);
        ad.setRoomM2(20);
        ad.setTitle("Rajski pogled!");
        ad.save();

        Ad ad2 = Ad.getRandom();
        ad2.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.");
        ad2.setTitle("Apartment with positive view");
        String pattern = "MM/dd/yyyy";
        DateFormat df = new SimpleDateFormat(pattern);
        Date dateFrom = new Date();

        ad2.setAvailableFrom(new Date());
        ad2.setAvailableUntil(new Date());
        ad2.save();

        Ad ad3 = Ad.getRandom();
        ad3.setLongitude((float) 48.209217);
        ad3.setLatitude((float) 29.722242);
        ad3.save();

      /*  ResourceRegistry rr = new ResourceRegistry().builder().build();
        rr.setAddId(1);
        rr.setProfilePicture(false);
        rr.setUri(R.drawable.apartment1);
        rr.save();*/

        //Languages
        Language l1 = new Language("SRB","Serbian");
        Language l2 = new Language("ENG","English");
        Language l3 = new Language("ESP","Spanish");
        Language l4 = new Language("PRT","Portuguese");
        Language l5 = new Language("FRA","French");
        Language l6 = new Language("GER","German");
        Language l7 = new Language("HRV","Croatian");
        Language l8 = new Language("BGR","Bulgarian");
        Language l9 = new Language("ITA","Italian");

        l1.save();
        l2.save();
        l3.save();
        l4.save();
        l5.save();
        l6.save();
        l7.save();
        l8.save();
        l9.save();


        //users
        User userLuka = new User().builder()
                .firstName("Luka")
                .lastName("Jovanovic")
                .email("lukajvnv@gmail.com")
                .birthDay(new Date(828054000000l))  //29.3.1996.
                .gender("Male")
                .about("Lorem ipsum")
                .password("password")
                .occupation("SW architect")
                .studyLevel("Bachelor's degree")
                .workingStatus("Study")
                .urlProfile("url")
                .activeSince(new Date())
                .build();

        User userPop = new User().builder()
                .firstName("Srdjan")
                .lastName("Popovic")
                .email("srdjanpopovic14@gmail.com")
                .birthDay(new Date(825634800000l))  //1.3.1996
                .gender("Male")
                .about("Lorem ipsum")
                .password("password")
                .occupation("SW architect")
                .studyLevel("Bachelor's degree")
                .workingStatus("Study")
                .urlProfile("url")
                .activeSince(new Date())
                .build();

        User userDjuka = new User().builder()
                .firstName("Viktor")
                .lastName("Djuka")
                .email("viktordjuka10@gmail.com")
                .birthDay(new Date(823820400000l))  // 9.2.1996
                .gender("Male")
                .about("Lorem ipsum")
                .password("SW architect")
                .occupation("Study")
                .studyLevel("Bachelor's degree")
                .workingStatus("Study")
                .urlProfile("url")
                .activeSince(new Date())
                .build();

        userDjuka.save();
        userLuka.save();
        userPop.save();

    }

    private void initUser() {
        User userPera = new User().builder()
                .firstName("Petar")
                .lastName("Petrovic")
                .email("pera123@gmail.com")
                .birthDay(new Date(823820400000l))  // 9.2.1996
                .gender("Male")
                .about("Lorem ipsum")
                .password("pera123")
                .occupation("SW architect")
                .studyLevel("Bachelor's degree")
                .workingStatus("Study")
                .urlProfile("url")
                .activeSince(new Date())
                .entityId(1)
                .build();

        userPera.save();

    }
}
