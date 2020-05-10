package rs.ac.uns.ftn.findaroommate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.activeandroid.ActiveAndroid;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import rs.ac.uns.ftn.findaroommate.activity.HomepageActivity;
import rs.ac.uns.ftn.findaroommate.activity.LoginActivity;
import rs.ac.uns.ftn.findaroommate.activity.NewAdActivity;
import rs.ac.uns.ftn.findaroommate.activity.ProfileActivity;
import rs.ac.uns.ftn.findaroommate.activity.ProfileFormActivity;
import rs.ac.uns.ftn.findaroommate.activity.SignUpActivity;
import rs.ac.uns.ftn.findaroommate.activity.SignUpHomeActivity;
import rs.ac.uns.ftn.findaroommate.model.Ad;
import rs.ac.uns.ftn.findaroommate.model.Language;
import rs.ac.uns.ftn.findaroommate.model.Message;
import rs.ac.uns.ftn.findaroommate.model.ResourceRegistry;
import rs.ac.uns.ftn.findaroommate.model.User;

public class MainActivity extends AppCompatActivity {

    private Boolean initDataFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActiveAndroid.initialize(this);

        initDataFlag = false;

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
        if(logged){
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(MainActivity.this, SignUpHomeActivity.class);
            startActivity(intent);
        }

    }

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
                .birthDay(new Date(1996,3,29))
                .gender("male")
                .about("Lorem ipsum")
                .password("password")
                .occupation("student")
                .studyLevel("master")
                .workingStatus("unemployed")
                .urlProfile("url")
                .activeSince(new Date())
                .build();

        User userPop = new User().builder()
                .firstName("Srdjan")
                .lastName("Popovic")
                .email("srdjanpopovic14@gmail.com")
                .birthDay(new Date(1996,3,1))
                .gender("male")
                .about("Lorem ipsum")
                .password("password")
                .occupation("student")
                .studyLevel("4 year")
                .workingStatus("unemployed")
                .urlProfile("url")
                .activeSince(new Date())
                .build();

        User userDjuka = new User().builder()
                .firstName("Viktor")
                .lastName("Djuka")
                .email("viktordjuka10@gmail.com")
                .birthDay(new Date(1996,2,9))
                .gender("male")
                .about("Lorem ipsum")
                .password("password")
                .occupation("student")
                .studyLevel("master")
                .workingStatus("unemployed")
                .urlProfile("url")
                .activeSince(new Date())
                .build();

        userDjuka.save();
        userLuka.save();
        userPop.save();

    }

}
