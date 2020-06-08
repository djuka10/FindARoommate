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
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rs.ac.uns.ftn.findaroommate.R;
import rs.ac.uns.ftn.findaroommate.model.CharacteristicType;
import rs.ac.uns.ftn.findaroommate.model.Language;
import rs.ac.uns.ftn.findaroommate.model.UserCharacteristic;
import rs.ac.uns.ftn.findaroommate.service.api.ServiceUtils;
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

    List<Integer> selectedUserCharacteristicsId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_attributes);

        selectedUserCharacteristicsId = getIntent().getIntegerArrayListExtra("userCharacteristics");

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

                //data.putExtra("selectedUserCharacteristicId", selectedUserCharacteristicsId.toArray());
                data.putIntegerArrayListExtra("selectedUserCharacteristicId", (ArrayList<Integer>)selectedUserCharacteristicsId);

                setResult(RESULT_OK, data);
                finish();
            }
        });

        personalityChipGroup = (ChipGroup) findViewById(R.id.personality_selector_chips);
        lifestyleChipGroup = (ChipGroup) findViewById(R.id.lifestyle_selector_chips);
        musicChipGroup = (ChipGroup) findViewById(R.id.music_selector_chips);
        filmChipGroup = (ChipGroup) findViewById(R.id.film_selector_chips);
        sportChipGroup = (ChipGroup) findViewById(R.id.sport_selector_chips);

        setupChips();


//        createChips(personalityChipGroup, selectedPersonalityAttrs, CharacteristicType.PERSONALITY);
//        createChips(lifestyleChipGroup, selectedLifestyleAttrs, CharacteristicType.LIFESTYLE);
//        createChips(musicChipGroup, selectedMusicAttrs, CharacteristicType.MUSIC);
//        createChips(filmChipGroup, selectedFilmAttrs, CharacteristicType.FILM);
//        createChips(sportChipGroup, selectedSportAttrs, CharacteristicType.SPORT);
    }

    private void setupChips(){
        Call<List<UserCharacteristic>> userCharacteristics = ServiceUtils.userCharacteristicServiceApi.getAll();
        userCharacteristics.enqueue(new Callback<List<UserCharacteristic>>() {
            @Override
            public void onResponse(Call<List<UserCharacteristic>> call, Response<List<UserCharacteristic>> response) {
                if(response.isSuccessful()){
                    List<UserCharacteristic> userCharacteristicsDatasource= response.body();

                    createChips(personalityChipGroup, selectedPersonalityAttrs,
                            userCharacteristicsDatasource.stream().filter(c -> c.getType().equals(CharacteristicType.PERSONALITY)).collect(Collectors.toList()));
                    createChips(lifestyleChipGroup, selectedLifestyleAttrs,
                            userCharacteristicsDatasource.stream().filter(c -> c.getType().equals(CharacteristicType.LIFESTYLE)).collect(Collectors.toList()));
                    createChips(musicChipGroup, selectedMusicAttrs,
                            userCharacteristicsDatasource.stream().filter(c -> c.getType().equals(CharacteristicType.MUSIC)).collect(Collectors.toList()));
                    createChips(filmChipGroup, selectedFilmAttrs,
                            userCharacteristicsDatasource.stream().filter(c -> c.getType().equals(CharacteristicType.FILM)).collect(Collectors.toList()));
                    createChips(sportChipGroup, selectedSportAttrs,
                            userCharacteristicsDatasource.stream().filter(c -> c.getType().equals(CharacteristicType.SPORT)).collect(Collectors.toList()));
                }
            }

            @Override
            public void onFailure(Call<List<UserCharacteristic>> call, Throwable t) {

            }
        });
    }

    private void createChips(ChipGroup chips, final List<UserCharacteristic> selectedAttrs, List<UserCharacteristic> datasource){
        // List<UserCharacteristic> datasource = new ArrayList<UserCharacteristic>();
//        switch(type){
//            case PERSONALITY:
//                datasource = Mockup.getInstance().getAvailablePersonalities();
//                break;
//            case LIFESTYLE:
//                datasource = Mockup.getInstance().getAvailableLifestyles();
//                break;
//            case MUSIC:
//                datasource = Mockup.getInstance().getAvailableMusics();
//                break;
//            case SPORT:
//                datasource = Mockup.getInstance().getAvailableSports();
//                break;
//            case FILM:
//                datasource = Mockup.getInstance().getAvailableFilms();
//                break;
//        }

        for (UserCharacteristic characteristic : datasource){
            Chip c1 = (Chip) this.getLayoutInflater().inflate(R.layout.chip_item, null, false);

            c1.setText(characteristic.getValue());
            c1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Chip c = (Chip) buttonView;
                    if(isChecked){
                        if(!alreadyAddedSelected(c, characteristic)){
                            selectedUserCharacteristicsId.add(((UserCharacteristic)c.getTag()).getEntityId());
                            selectedAttrs.add((UserCharacteristic)c.getTag());
                        }
                    } else {
                        UserCharacteristic u = (UserCharacteristic)c.getTag();
                        selectedAttrs.remove(u);
                        selectedUserCharacteristicsId.remove(Integer.valueOf(u.getEntityId()));
                    }
                }
            });
            c1.setTag(characteristic);

            if(alreadyAddedSelected(c1, characteristic)){
                c1.setChecked(true);
                selectedAttrs.add(characteristic);
            }

            c1.setOnCloseIconClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean t = true;
                    Chip c = (Chip)v;
                    c.setChecked(false);
                    UserCharacteristic u = (UserCharacteristic)c.getTag();
                    selectedAttrs.remove(u);
                    selectedUserCharacteristicsId.remove(Integer.valueOf(u.getEntityId()));
                }
            });

            chips.addView(c1);
        }
    }

    private boolean alreadyAddedSelected(Chip chip, UserCharacteristic characteristic){
        if(selectedUserCharacteristicsId.contains(characteristic.getEntityId())){
            return true;
        }

        return false;
    }
}
