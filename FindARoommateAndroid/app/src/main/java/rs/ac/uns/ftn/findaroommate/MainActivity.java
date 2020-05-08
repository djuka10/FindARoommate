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
import rs.ac.uns.ftn.findaroommate.activity.SignUpActivity;
import rs.ac.uns.ftn.findaroommate.activity.SignUpHomeActivity;
import rs.ac.uns.ftn.findaroommate.model.Ad;
import rs.ac.uns.ftn.findaroommate.model.Message;
import rs.ac.uns.ftn.findaroommate.model.ResourceRegistry;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActiveAndroid.initialize(this);

       /* Ad ad = new Ad().builder().build();
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
        ad.setMinDays(34);
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
        ad2.save();*/

/*        ResourceRegistry rr = new ResourceRegistry().builder().build();
        rr.setAddId(1);
        rr.setProfilePicture(false);
        rr.setUri(R.drawable.apartment1);
        rr.save();*/

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
            Intent intent = new Intent(MainActivity.this, HomepageActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(MainActivity.this, SignUpHomeActivity.class);
            startActivity(intent);
        }

    }
}
