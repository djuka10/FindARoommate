package rs.ac.uns.ftn.findaroommate.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Rating;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rs.ac.uns.ftn.findaroommate.MainActivity;
import rs.ac.uns.ftn.findaroommate.R;
import rs.ac.uns.ftn.findaroommate.dto.PushNotificationRequest;
import rs.ac.uns.ftn.findaroommate.dto.UserDto;
import rs.ac.uns.ftn.findaroommate.model.Ad;
import rs.ac.uns.ftn.findaroommate.model.Review;
import rs.ac.uns.ftn.findaroommate.model.User;
import rs.ac.uns.ftn.findaroommate.service.api.ServiceUtils;
import rs.ac.uns.ftn.findaroommate.utils.AppTools;

public class UserStayDetailActivity extends AppCompatActivity {

    private Ad stayDetail;

    TextInputEditText reviewTitleEditText;
    TextInputEditText reviewMessageEditText;

    RatingBar gradeRatingBar;

    TextView adTitleText;
    TextView periodText;
    TextView ownerText;
    TextView locationText;

    Button submitButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_stay_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.user_stay_detail_toolbar);
        toolbar.setTitle("My stays");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  // false: ne prikazuje home

        int stayId = getIntent().getExtras().getInt("stayId");

        stayDetail = Ad.getOneGlobal(stayId);

        reviewTitleEditText = (TextInputEditText) findViewById(R.id.review_form_text_title);
        reviewMessageEditText = (TextInputEditText) findViewById(R.id.review_form_text_comment);
        gradeRatingBar = (RatingBar) findViewById(R.id.review_form_rating_grade);

        periodText = (TextView) findViewById(R.id.review_form_text_period);
        adTitleText = (TextView) findViewById(R.id.review_form_text_ad_title);
        ownerText = (TextView) findViewById(R.id.review_form_text_owner);
        locationText = (TextView) findViewById(R.id.review_form_text_location);

        submitButton = (Button)findViewById(R.id.review_form_submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitReview();

                Intent intent = new Intent(UserStayDetailActivity.this, UserStayActivity.class);
                startActivity(intent);
            }
        });

       setValues();

    }


    private void setValues(){
        User owner = User.getOneGlobal(stayDetail.getOwnerId());

        String firstName = owner.getFirstName();
        String lastName = owner.getLastName();

        String fromStr = "";
        String  toStr = "";
        Date from = stayDetail.getAvailableFrom();
        Date to = stayDetail.getAvailableUntil();

        SimpleDateFormat dateFormat = new SimpleDateFormat(getString(R.string.date_format));

        if(from != null){
            fromStr = dateFormat.format(from);
        }

        if(to != null){
            toStr = dateFormat.format(to);
        }

        ownerText.setText(String.format("%s %s", firstName, lastName));
        adTitleText.setText(stayDetail.getTitle());
        periodText.setText(String.format("%s - %s", fromStr, toStr));
    }

    private void submitReview(){
        String reviewTitle = reviewTitleEditText.getText().toString();
        int reviewGrade = (int)gradeRatingBar.getRating();

        //validation
        if(reviewTitle.isEmpty() || reviewGrade == 0){
            Toast.makeText(this, getString(R.string.review_form_submit_error), Toast.LENGTH_SHORT).show();
        }

        User user = AppTools.getLoggedUser();

        String reviewComment = reviewMessageEditText.getText().toString();
        String reviewerName = user.getFirstName();
        int author = user.getEntityId();
        int ratedUser = stayDetail.getOwnerId();
        int ad = stayDetail.getEntityId();

        Review newReview = Review.builder()
                .ad(ad)
                .author(author)
                .reviewerName(reviewerName)
                .title(reviewTitle)
                .comment(reviewComment)
                .rating(reviewGrade)
                .ratedUser(ratedUser)
                .build();

        newReview.save();
        sendToServer(newReview);
    }

    private void sendToServer(Review review){
        Call<Review> call = ServiceUtils.reviewServiceApi.addReview(review);
        call.enqueue(new Callback<Review>() {
            @Override
            public void onResponse(Call<Review> call, Response<Review> response) {
                if (response.isSuccessful()) {
                    System.out.println("Meesage recieved");
                    Log.i("fd", "Message received");

                    Review body = response.body();

                    Review dbReview = Review.getOne(review.getId());
                    dbReview.setEntityId(body.getEntityId());
                    dbReview.save();

                    sendNotification(review, body.getEntityId());

                } else {
                    Log.e("editProfileTask", "Error");
                    serverErrorHandling("sending review");

                }
            }

            @Override
            public void onFailure(Call<Review> call, Throwable t) {
                System.out.println("Error!");
                Log.e("error", t.getMessage());

                serverErrorHandling("sending review");

            }
        });
    }

    private void serverErrorHandling(String action){
        String serverErrorPattenr = "Server error while %ACTION%. Please try again.";

        Intent intent = new Intent(MainActivity.SERVER_ERROR);
        intent.putExtra("error_message",
                serverErrorPattenr.replace("%ACTION%", action));
        sendBroadcast(intent);
    }

    private void sendNotification(Review review, int reviewRemoteId){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Ad ad = Ad.getOneGlobal(review.getAd());
        String adTitle = ad.getTitle();

        if(preferences.getBoolean("new_review_notif", false)){
            Intent intent = new Intent(MainActivity.REVIEW);
            String reviewEditMessage = getString(R.string.notif_review_edit_message);



            intent.putExtra("notif_review_message",
                    reviewEditMessage.replace("%AD%", adTitle)
                    );

            sendBroadcast(intent);
        }

        sendNotificationRemote(ad, reviewRemoteId);

    }

    private void sendNotificationRemote(Ad ad, int reviewRemoteId){

        String notif_topic = getString(R.string.notif_review_topic);
        String notif_title = getString(R.string.notif_review_title);
        String notif_text = getString(R.string.notif_review_push_message);

        String message = notif_text.replace("%AD%", ad.getTitle());

        PushNotificationRequest pushNotificationRequest = PushNotificationRequest.builder()
                .topic(notif_topic)
                .title(notif_title)
                .message(message)
                .adId(ad.getEntityId())
                .userId(ad.getOwnerId())
                .reviewId(reviewRemoteId)
                .build();

        Call<ResponseBody> call = ServiceUtils.notificationServiceApi.pushRemoteReviewNotification(pushNotificationRequest);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    System.out.println("Meesage recieved");
                    Log.i("fd", "Message received");

                } else {
                    Log.e("editBookTask", "Error");

                    serverErrorHandling("sending remote notification");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                serverErrorHandling("sending remote notification");
            }
        });

    }
}
