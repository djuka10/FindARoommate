package rs.ac.uns.ftn.findaroommate.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.util.List;

import rs.ac.uns.ftn.findaroommate.R;
import rs.ac.uns.ftn.findaroommate.model.Language;
import rs.ac.uns.ftn.findaroommate.model.User;
import rs.ac.uns.ftn.findaroommate.utils.AppTools;
import rs.ac.uns.ftn.findaroommate.utils.Mockup;

public class ProfileActivity extends AppCompatActivity {

    private User loggedUserModel;

    private ImageView profileImage;

    private ImageButton imageGenderButton;

    public static String PROFILE_URL = "http://HOST/server/user/profile/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        loggedUserModel = AppTools.getLoggedUser();
        if(loggedUserModel == null){
            Log.e("login error", "No one is logged");
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.profile_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  // false: ne prikazuje home

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_profile_toolbar);
        collapsingToolbar.setTitle(getString(R.string.profile));

        FloatingActionButton fabEditProfile = (FloatingActionButton)findViewById(R.id.fab_profile_edit);
        fabEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, ProfileFormActivity.class);
                startActivity(intent);
            }
        });

        User user = User.builder()
                .firstName("Korisnik")
                .lastName("Korisnikovic")
                .birthDay(new Date(820450800000l))
                .activeSince(new Date(System.currentTimeMillis()))
                .occupation("Software architect")
                .studyLevel("Master studies")
                .workingStatus("Unemployed")
                .build();

        // TODO: get user languages
        List<Language> userLanguages = Mockup.getInstance().getUserLanguages();
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < userLanguages.size(); i++){
            builder.append(userLanguages.get(i).getName());
            if( i < userLanguages.size() - 1)
                builder.append(", ");
        }

        TextView tUserInfo = (TextView) findViewById(R.id.user_name_info);
        TextView tUserActive = (TextView) findViewById(R.id.user_active);
        //TextView tLanguages = (TextView) findViewById(R.id.user_languages);
        TextView tStudyLevel= (TextView) findViewById(R.id.study_level);
        TextView tOccupation = (TextView) findViewById(R.id.user_occupation);
        TextView tEducation = (TextView) findViewById(R.id.user_education);

        profileImage = (ImageView) findViewById(R.id.profileImage);

        imageGenderButton = (ImageButton) findViewById(R.id.gender_image);
        if(loggedUserModel.getGender() != null){
            if(loggedUserModel.getGender().equals("Female")){
                imageGenderButton.setImageResource(R.drawable.ic_girl);

            } else {
                imageGenderButton.setImageResource(R.drawable.ic_boy);
            }
        }



        SimpleDateFormat format = new SimpleDateFormat(getString(R.string.date_format));
        String formattedActive = format.format(loggedUserModel.getActiveSince());

        int now = Calendar.getInstance().get(Calendar.YEAR);
        String age = "";
        if(loggedUserModel.getBirthDay() != null){
             age = ", " + Integer.toString(now - loggedUserModel.getBirthDay().getYear() - 1900);
        }

        tUserInfo.setText(String.format("%s %s%s", loggedUserModel.getFirstName(), loggedUserModel.getLastName(), age));
        //tUserInfo.setText(String.format("%s %s, %s", user.getFirstName(), user.getLastName(), age));
        tUserActive.setText("Active since " + formattedActive);
        tOccupation.setText(loggedUserModel.getOccupation());
        tEducation.setText(loggedUserModel.getStudyLevel());
        //tLanguages.setText(builder.toString());
        tStudyLevel.setText(loggedUserModel.getStudyLevel());

        BottomAppBar bottomAppBar = (BottomAppBar) findViewById(R.id.bottom_app_bar);
        bottomAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();

                switch (id){
                    case R.id.menu_profile_ads:
                        //Toast.makeText(ProfileActivity.this, "Ads", Toast.LENGTH_LONG).show();
                        Intent myAdsIntent = new Intent(ProfileActivity.this, MyAdsActivity.class);
                        startActivity(myAdsIntent);
                        return true;
                    case R.id.menu_profile_history:
                        Intent historyIntent = new Intent(ProfileActivity.this, UserStayActivity.class);
                        startActivity(historyIntent);
                        return true;
                    case R.id.menu_profile_review:
                        Intent reviewIntent = new Intent(ProfileActivity.this, UserReviewActivity.class);
                        startActivity(reviewIntent);
                        return true;
                }

                return false;
            }
        });


        if(loggedUserModel.getUrlProfile() != null){
            Glide.with(getApplicationContext())
                    .load(PROFILE_URL.replace("HOST", getString(R.string.host)) + loggedUserModel.getUrlProfile()).into(profileImage);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                return super.onOptionsItemSelected(item);
        }

        return super.onOptionsItemSelected(item);
    }
}
