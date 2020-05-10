package rs.ac.uns.ftn.findaroommate.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Calendar;

import rs.ac.uns.ftn.findaroommate.R;
import rs.ac.uns.ftn.findaroommate.dto.AdDto;
import rs.ac.uns.ftn.findaroommate.fragment.FragmentTransition;
import rs.ac.uns.ftn.findaroommate.fragment.NewAdFragmentFactory;
import rs.ac.uns.ftn.findaroommate.fragment.NewAdFragmentAbstact;
import rs.ac.uns.ftn.findaroommate.model.Ad;
import rs.ac.uns.ftn.findaroommate.model.AdItem;
import rs.ac.uns.ftn.findaroommate.model.ResourceRegistry;
import rs.ac.uns.ftn.findaroommate.model.UserCharacteristic;

public class NewAdActivity extends AppCompatActivity {

    public Toolbar getToolbar() {
        return toolbar;
    }

    Toolbar toolbar;
    ActionBar actionBar;

    AdDto ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_ad);

        toolbar = (Toolbar) findViewById(R.id.ad_form_toolbar);
        toolbar.setTitle("New Add");
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Button button = (Button) findViewById(R.id.ad_save_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewAdFragmentAbstact activeFragment = (NewAdFragmentAbstact) FragmentTransition.findActiveFragment(NewAdActivity.this);
                int dialogNum = activeFragment.getDialogNum();

                if(dialogNum == 6){
                    //save
                    Toast.makeText(NewAdActivity.this, getString(R.string.ad_form_success), Toast.LENGTH_LONG).show();
                    Intent homepageIntent = new Intent(NewAdActivity.this, HomepageActivity.class);
                    startActivity(homepageIntent);
                    finish();
                } else {
                    //Fragment f = FragmentTransition.getCurrentFragment(NewAdActivity.this);
                    int nextDialogNum = ++dialogNum;
                    NewAdFragmentAbstact nextFragment = NewAdFragmentFactory.getNewAdFragment(nextDialogNum, ad);
                    Bundle bundle = new Bundle();
                    bundle.putString("title", "New ad ");
                    bundle.putInt("dialogNum", nextDialogNum);
                    nextFragment.setArguments(bundle);
                    FragmentTransition.to(nextFragment, NewAdActivity.this, true, "Dialog" + nextDialogNum);

                }
            }
        });

        ad = AdDto.builder()
                .ad(new Ad())
                .adItems(new ArrayList<AdItem>())
                .images(new ArrayList<ResourceRegistry>())
                .prefsFilm(new ArrayList<UserCharacteristic>())
                .prefsLifestyle(new ArrayList<UserCharacteristic>())
                .prefsMusic(new ArrayList<UserCharacteristic>())
                .prefsPersonality(new ArrayList<UserCharacteristic>())
                .prefsSport(new ArrayList<UserCharacteristic>())
                .build();

        if(savedInstanceState == null){
            NewAdFragmentAbstact nextFragment = NewAdFragmentFactory.getNewAdFragment(1, ad);
            Bundle bundle = new Bundle();
            bundle.putString("title", "New ad");
            bundle.putInt("dialogNum", 1);
            nextFragment.setArguments(bundle);
            FragmentTransition.to(nextFragment, NewAdActivity.this, false, "Dialog1");
        }
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == android.R.id.home){
            NewAdFragmentAbstact activeFragment = (NewAdFragmentAbstact) FragmentTransition.findActiveFragment(NewAdActivity.this);
            int dialogNum = activeFragment.getDialogNum();

            if(dialogNum == 1){
                //back from dialog
                //finish();
                return super.onOptionsItemSelected(item);
            }

            int nextDialogNum = --dialogNum;
            NewAdFragmentAbstact nextFragment = NewAdFragmentFactory.getNewAdFragment(nextDialogNum, ad);
            Bundle bundle = new Bundle();
            bundle.putString("title", "New ad");
            bundle.putInt("dialogNum", nextDialogNum);
            nextFragment.setArguments(bundle);
            FragmentTransition.to(nextFragment, NewAdActivity.this, false);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
