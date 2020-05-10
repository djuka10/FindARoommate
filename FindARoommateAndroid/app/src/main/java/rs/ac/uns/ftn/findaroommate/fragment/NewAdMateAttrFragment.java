package rs.ac.uns.ftn.findaroommate.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

import rs.ac.uns.ftn.findaroommate.R;
import rs.ac.uns.ftn.findaroommate.activity.NewAdActivity;
import rs.ac.uns.ftn.findaroommate.dto.AdDto;
import rs.ac.uns.ftn.findaroommate.model.Ad;
import rs.ac.uns.ftn.findaroommate.model.AdItem;
import rs.ac.uns.ftn.findaroommate.model.CharacteristicType;
import rs.ac.uns.ftn.findaroommate.model.UserCharacteristic;
import rs.ac.uns.ftn.findaroommate.utils.Mockup;

public class NewAdMateAttrFragment extends NewAdFragmentAbstact{

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

    public NewAdMateAttrFragment(AdDto ad) {
        super(ad);
    }


    public static NewAdMateAttrFragment newInstance(AdDto ad) {
        NewAdMateAttrFragment mpf = new NewAdMateAttrFragment(ad);

        return mpf;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_add_mate_attr, container, false);

        Bundle bundle = getArguments();
        if(bundle != null){
            title = bundle.getString("title", "New ad");
            dialogNum = bundle.getInt("dialogNum", 1);
        }

        TextView titleText = (TextView) view.findViewById(R.id.dialog_title);
        titleText.setText(title + " (Mate prefs)");

        if(savedInstanceState != null){

        }

        selectedPersonalityAttrs = ad.getPrefsPersonality();
        selectedLifestyleAttrs = ad.getPrefsLifestyle();
        selectedMusicAttrs = ad.getPrefsMusic();
        selectedFilmAttrs = ad.getPrefsFilm();
        selectedSportAttrs = ad.getPrefsSport();

        personalityChipGroup = (ChipGroup) view.findViewById(R.id.personality_selector_chips);
        lifestyleChipGroup = (ChipGroup) view.findViewById(R.id.lifestyle_selector_chips);
        musicChipGroup = (ChipGroup) view.findViewById(R.id.music_selector_chips);
        filmChipGroup = (ChipGroup) view.findViewById(R.id.film_selector_chips);
        sportChipGroup = (ChipGroup) view.findViewById(R.id.sport_selector_chips);


        createChips(personalityChipGroup, selectedPersonalityAttrs, CharacteristicType.PERSONALITY);
        createChips(lifestyleChipGroup, selectedLifestyleAttrs, CharacteristicType.LIFESTYLE);
        createChips(musicChipGroup, selectedMusicAttrs, CharacteristicType.MUSIC);
        createChips(filmChipGroup, selectedFilmAttrs, CharacteristicType.FILM);
        createChips(sportChipGroup, selectedSportAttrs, CharacteristicType.SPORT);

        return view;
    }

    private void createChips(ChipGroup chips, final List<UserCharacteristic> selectedAttrs, final CharacteristicType type){
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
            final Chip chip = (Chip) this.getLayoutInflater().inflate(R.layout.chip_item, null, false);

            chip.setText(characteristic.getValue());
            chip.setTag(characteristic);

            if(alreadyAddedSelected(chip, type)){
                chip.setChecked(true);
            }

            chip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Chip c = (Chip) buttonView;
                    if(isChecked){
                        if(!alreadyAddedSelected(c, type)){
                            selectedAttrs.add((UserCharacteristic) c.getTag());
                        }
                    } else {
                        selectedAttrs.remove((UserCharacteristic)c.getTag());
                    }
                }
            });

            chip.setOnCloseIconClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean t = true;
                    Chip c = (Chip)v;
                    c.setChecked(false);
                    selectedAttrs.remove((UserCharacteristic)c.getTag());
                }
            });

            chips.addView(chip);
        }
    }

    private boolean alreadyAddedSelected(Chip chip, CharacteristicType characteristicType){
        UserCharacteristic item = (UserCharacteristic)chip.getTag();
        List<UserCharacteristic> selectedAttrs;

        switch (characteristicType){
            case FILM:
                selectedAttrs = selectedFilmAttrs;
                break;
            case MUSIC:
                selectedAttrs = selectedMusicAttrs;
                break;
            case LIFESTYLE:
                selectedAttrs = selectedLifestyleAttrs;
                break;
            case PERSONALITY:
                selectedAttrs = selectedPersonalityAttrs;
                break;

            case SPORT:
                selectedAttrs = selectedSportAttrs;
                break;

                default:
                    selectedAttrs = new ArrayList<>();
        }

        for(UserCharacteristic u : selectedAttrs){
            if(u == item){
                return true;
            }
        }

        return false;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        NewAdActivity act = (NewAdActivity) getActivity();
        act.getToolbar().setTitle("New ad " + dialogNum + " of 6");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        //ovde loviti pod !!!!!!!!!!!!!
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
