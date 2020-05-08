package rs.ac.uns.ftn.findaroommate.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.util.List;

import rs.ac.uns.ftn.findaroommate.R;
import rs.ac.uns.ftn.findaroommate.model.Language;
import rs.ac.uns.ftn.findaroommate.model.User;
import rs.ac.uns.ftn.findaroommate.utils.Mockup;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

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

        List<Language> userLanguages = Mockup.getInstance().getUserLanguages();
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < userLanguages.size(); i++){
            builder.append(userLanguages.get(i).getName());
            if( i < userLanguages.size() - 1)
                builder.append(", ");
        }

        TextView tUserInfo = (TextView) findViewById(R.id.user_name_info);
        TextView tUserActive = (TextView) findViewById(R.id.user_active);
        TextView tLanguages = (TextView) findViewById(R.id.user_languages);
        TextView tOccupation = (TextView) findViewById(R.id.user_occupation);
        TextView tEducation = (TextView) findViewById(R.id.user_education);


        SimpleDateFormat format = new SimpleDateFormat(getString(R.string.date_format));
        String formattedActive = format.format(user.getActiveSince());

        int now = Calendar.getInstance().get(Calendar.YEAR);
        String age = Integer.toString(now - user.getBirthDay().getYear() - 1900);

        tUserInfo.setText(String.format("%s %s, %s", user.getFirstName(), user.getLastName(), age));
        tUserActive.setText("Active since " + formattedActive);
        tOccupation.setText(user.getOccupation());
        tEducation.setText(user.getStudyLevel());
        tLanguages.setText(builder.toString());

        BottomAppBar bottomAppBar = (BottomAppBar) findViewById(R.id.bottom_app_bar);
        bottomAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();

                switch (id){
                    case R.id.menu_profile_ads:
                        Toast.makeText(ProfileActivity.this, "Ads", Toast.LENGTH_LONG).show();
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
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
