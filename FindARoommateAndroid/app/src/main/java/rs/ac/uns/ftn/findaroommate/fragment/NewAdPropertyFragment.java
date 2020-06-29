package rs.ac.uns.ftn.findaroommate.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.google.android.material.textfield.TextInputEditText;

import rs.ac.uns.ftn.findaroommate.R;
import rs.ac.uns.ftn.findaroommate.activity.NewAdActivity;
import rs.ac.uns.ftn.findaroommate.dto.AdDto;
import rs.ac.uns.ftn.findaroommate.model.Ad;

public class NewAdPropertyFragment extends NewAdFragmentAbstact {

    TextInputEditText propertySizeEditText;
    TextInputEditText maxPersonEditText;
    NumberPicker npMan;
    NumberPicker npWoman;
    RadioGroup propertyType;
    RadioGroup houseRules;


    public NewAdPropertyFragment(AdDto ad) {
        super(ad);
    }

    public static NewAdPropertyFragment newInstance(AdDto ad) {
        NewAdPropertyFragment mpf = new NewAdPropertyFragment(ad);

        return mpf;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_add_property, container, false);

        Bundle bundle = getArguments();
        if(bundle != null){
            title = bundle.getString("title", "New ad");
            dialogNum = bundle.getInt("dialogNum", 1);
        }

        TextView titleText = (TextView) view.findViewById(R.id.dialog_title);
        titleText.setText(title + dialogNamePatern.replace("NAME", getString(R.string.ad_form_property)));

        npMan = view.findViewById(R.id.ad_form_picker_men);

        npMan.setMinValue(0);
        npMan.setMaxValue(10);

        npWoman = view.findViewById(R.id.ad_form_picker_women);

        npWoman.setMinValue(0);
        npWoman.setMaxValue(10);

        propertySizeEditText = (TextInputEditText) view.findViewById(R.id.ad_form_property_size);
        maxPersonEditText = (TextInputEditText) view.findViewById(R.id.ad_form_max_person);

        propertyType = (RadioGroup) view.findViewById(R.id.new_add_property_type);
        houseRules = (RadioGroup) view.findViewById(R.id.new_add_house_rules);


        int index = -1;
        boolean matched = false;
        for( String option: getResources().getStringArray(R.array.property_type_keys)){
            index++;
            if (option.equals(ad.getAd().getAdType())){
                propertyType.check(propertyType.getChildAt(index).getId());
                matched = true;
                break;
            }
        }
        if(!matched){
            propertyType.check(propertyType.getChildAt(0).getId());
        }


        if(ad.getAd().getBoysNum() != 0){
            npMan.setValue(ad.getAd().getBoysNum());
        }

        if(ad.getAd().getLadiesNum() != 0){
            npWoman.setValue(ad.getAd().getLadiesNum());
        }

        if(ad.getAd().getMaxPerson() != 0){
            maxPersonEditText.setText(ad.getAd().getMaxPerson());
        }

        if(ad.getAd().getFlatM2() != 0){
            propertySizeEditText.setText(String.valueOf(ad.getAd().getFlatM2()));
        }

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        NewAdActivity act = (NewAdActivity) getActivity();
        act.getToolbar().setTitle("New ad " + dialogNum + " of 6");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("title", "Gladiator");

    }

    @Override
    public void onDestroyView() {
        String maxPersonText = maxPersonEditText.getText().toString();
        String propertySizeText = propertySizeEditText.getText().toString();

        if(!maxPersonText.isEmpty()){
            ad.getAd().setMaxPerson(Integer.parseInt(maxPersonText));
        }

        if(!propertySizeText.isEmpty()){
            ad.getAd().setFlatM2(Float.parseFloat(propertySizeText));
        }

        ad.getAd().setLadiesNum(npWoman.getValue());
        ad.getAd().setBoysNum(npMan.getValue());

        int checkedPopertyType = propertyType.getCheckedRadioButtonId();
        if(checkedPopertyType != -1){
            RadioButton val = getView().findViewById(checkedPopertyType);
            ad.getAd().setAdType(val.getText().toString());
        } else {
            ad.getAd().setAdType("");
        }

        // TODO: house rules radio button

        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
