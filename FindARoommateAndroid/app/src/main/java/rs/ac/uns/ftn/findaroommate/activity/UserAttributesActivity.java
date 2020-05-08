package rs.ac.uns.ftn.findaroommate.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

import rs.ac.uns.ftn.findaroommate.R;
import rs.ac.uns.ftn.findaroommate.model.CharacteristicType;
import rs.ac.uns.ftn.findaroommate.model.Language;
import rs.ac.uns.ftn.findaroommate.model.UserCharacteristic;
import rs.ac.uns.ftn.findaroommate.utils.Mockup;

public class UserAttributesActivity extends AppCompatActivity {

    ChipGroup personalityChipGroup;
    ChipGroup lifestyleChipGroup;
    ChipGroup musicChipGroup;
    ChipGroup filmChipGroup;
    ChipGroup sportChipGroup;

    List<UserCharacteristic> selectedPersonalityAttrs;
    List<UserCharacteristic> selectedLifestyleAttrs;
    List<UserCharacteristic> selectedMusicAttrs;
    List<UserCharacteristic> selectedFilmAttrs;
    List<UserCharacteristic> selectedSportAttrs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_attributes);

        Toolbar toolbar = (Toolbar) findViewById(R.id.user_attributes_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_user_attributes_toolbar);
        collapsingToolbar.setTitle(getString(R.string.profile_form_atrs_about));

        selectedPersonalityAttrs = new ArrayList<UserCharacteristic>();
        selectedLifestyleAttrs = new ArrayList<UserCharacteristic>();
        selectedMusicAttrs = new ArrayList<UserCharacteristic>();
        selectedFilmAttrs = new ArrayList<UserCharacteristic>();
        selectedSportAttrs = new ArrayList<UserCharacteristic>();


        Button updateUserCharacteristics = (Button) findViewById(R.id.btn_user_attributes);
        updateUserCharacteristics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                CharSequence[] personalitiesArray = new CharSequence[selectedPersonalityAttrs.size()];
                CharSequence[] lifestyleArray = new CharSequence[selectedLifestyleAttrs.size()];
                CharSequence[] musicArray = new CharSequence[selectedMusicAttrs.size()];
                CharSequence[] filmArray = new CharSequence[selectedFilmAttrs.size()];
                CharSequence[] sportArray = new CharSequence[selectedSportAttrs.size()];

                for(int i = 0; i < selectedPersonalityAttrs.size(); i++){
                    personalitiesArray[i] = selectedPersonalityAttrs.get(i).getValue();
                }
                for(int i = 0; i < selectedLifestyleAttrs.size(); i++){
                    lifestyleArray[i] = selectedLifestyleAttrs.get(i).getValue();
                }
                for(int i = 0; i < selectedMusicAttrs.size(); i++){
                    musicArray[i] = selectedMusicAttrs.get(i).getValue();
                }
                for(int i = 0; i < selectedFilmAttrs.size(); i++){
                    filmArray[i] = selectedFilmAttrs.get(i).getValue();
                }
                for(int i = 0; i < selectedSportAttrs.size(); i++){
                    sportArray[i] = selectedSportAttrs.get(i).getValue();
                }

                data.putExtra("selectedPersonalities", personalitiesArray);
                data.putExtra("selectedLifestyles", lifestyleArray);
                data.putExtra("selectedMusics", musicArray);
                data.putExtra("selectedFilms", filmArray);
                data.putExtra("selectedSport", sportArray);

                setResult(RESULT_OK, data);
                finish();
            }
        });

        personalityChipGroup = (ChipGroup) findViewById(R.id.personality_selector_chips);
        lifestyleChipGroup = (ChipGroup) findViewById(R.id.lifestyle_selector_chips);
        musicChipGroup = (ChipGroup) findViewById(R.id.music_selector_chips);
        filmChipGroup = (ChipGroup) findViewById(R.id.film_selector_chips);
        sportChipGroup = (ChipGroup) findViewById(R.id.sport_selector_chips);


        createChips(personalityChipGroup, selectedPersonalityAttrs, CharacteristicType.PERSONALITY);
        createChips(lifestyleChipGroup, selectedLifestyleAttrs, CharacteristicType.LIFESTYLE);
        createChips(musicChipGroup, selectedMusicAttrs, CharacteristicType.MUSIC);
        createChips(filmChipGroup, selectedFilmAttrs, CharacteristicType.FILM);
        createChips(sportChipGroup, selectedSportAttrs, CharacteristicType.SPORT);
    }

    private void createChips(ChipGroup chips, final List<UserCharacteristic> selectedAttrs, CharacteristicType type){
        List<UserCharacteristic> datasource = new ArrayList<UserCharacteristic>();
        switch(type){
            case PERSONALITY:
                datasource = Mockup.getInstance().getAvailablePersonalities();
                break;
            case LIFESTYLE:
                datasource = Mockup.getInstance().getAvailableLifestyles();
                break;
            case MUSIC:
                datasource = Mockup.getInstance().getAvailableMusics();
                break;
            case SPORT:
                datasource = Mockup.getInstance().getAvailableSports();
                break;
            case FILM:
                datasource = Mockup.getInstance().getAvailableFilms();
                break;
        }

        for (UserCharacteristic characteristic : datasource){
            Chip c1 = (Chip) this.getLayoutInflater().inflate(R.layout.chip_item, null, false);

            c1.setText(characteristic.getValue());
            c1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Chip c = (Chip) buttonView;
                    if(isChecked){
                        selectedAttrs.add((UserCharacteristic) c.getTag());
                    } else {
                        selectedAttrs.remove((UserCharacteristic)c.getTag());
                    }
                }
            });
            c1.setTag(characteristic);

            c1.setOnCloseIconClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean t = true;
                    Chip c = (Chip)v;
                    c.setChecked(false);
                    selectedAttrs.remove((UserCharacteristic)c.getTag());
                }
            });

            chips.addView(c1);
        }
    }
}
