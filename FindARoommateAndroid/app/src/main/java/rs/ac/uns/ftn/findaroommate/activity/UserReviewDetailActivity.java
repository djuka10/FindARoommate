package rs.ac.uns.ftn.findaroommate.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import rs.ac.uns.ftn.findaroommate.R;
import rs.ac.uns.ftn.findaroommate.model.Ad;
import rs.ac.uns.ftn.findaroommate.model.Review;

public class UserReviewDetailActivity extends AppCompatActivity {

    Review review;
    Ad stay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_review_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.user_review_detail_toolbar);
        toolbar.setTitle("My stays");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int reviewId = getIntent().getExtras().getInt("reviewId");
        review = Review.getOneGlobal(reviewId);
        stay = Ad.getOneGlobal(review.getAd());

        init();
    }

    private void init(){
        ((TextView) findViewById(R.id.room_title_frag)).setText(stay.getTitle());
        ((TextView) findViewById(R.id.room_longitude)).setText(String.valueOf(stay.getLongitude()));
        ((TextView) findViewById(R.id.room_latitude)).setText(String.valueOf(stay.getLatitude()));
        ((TextView) findViewById(R.id.room_price)).setText(String.valueOf(stay.getPrice()));
        if(stay.isCostsIncluded()) {
            ((TextView) findViewById(R.id.room_costs_included)).setText("Yes");
        } else {
            ((TextView) findViewById(R.id.room_costs_included)).setText("No");
        }
        ((TextView) findViewById(R.id.room_deposit)).setText(String.valueOf(stay.getDeposit()));


        String pattern = "MM/dd/yyyy";
        DateFormat df = new SimpleDateFormat(pattern);
        String dateFromStr = df.format(stay.getAvailableFrom());
        String dateUntilStr = df.format(stay.getAvailableUntil());
        ((TextView) findViewById(R.id.room_available_from)).setText(dateFromStr);
        ((TextView) findViewById(R.id.room_available_until)).setText(dateUntilStr);

        ImageView btnMapView = (ImageView) findViewById(R.id.btnMapView);

        btnMapView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserReviewDetailActivity.this, MapActivity.class);

                intent.putExtra("longitude", stay.getLongitude());
                intent.putExtra("latitude", stay.getLatitude());

                startActivity(intent);
            }
        });
    }
}
