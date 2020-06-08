package rs.ac.uns.ftn.findaroommate.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputEditText;

import rs.ac.uns.ftn.findaroommate.R;
import rs.ac.uns.ftn.findaroommate.activity.NewAdActivity;
import rs.ac.uns.ftn.findaroommate.dto.AdDto;
import rs.ac.uns.ftn.findaroommate.model.Ad;

public class NewAdLocationFragment extends NewAdFragmentAbstact {

    TextInputEditText latitudeEditText;
    TextInputEditText longitudeEditText;


    public NewAdLocationFragment(AdDto ad) {
        super(ad);
    }

    public static NewAdLocationFragment newInstance(AdDto ad) {
        NewAdLocationFragment mpf = new NewAdLocationFragment(ad);

        return mpf;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_add_location, container, false);

        Bundle bundle = getArguments();
        if(bundle != null){
            title = bundle.getString("title", "New ad");
            dialogNum = bundle.getInt("dialogNum", 1);
        }

        TextView titleText = (TextView) view.findViewById(R.id.dialog_title);
        titleText.setText(title + " (Location)");

        if(savedInstanceState != null){
            String val = savedInstanceState.getString("title", "nema");
            int r;
            r= 5;
        }

        longitudeEditText = (TextInputEditText) view.findViewById(R.id.ad_form_longitude);
        latitudeEditText = (TextInputEditText) view.findViewById(R.id.ad_form_latitude);

        //temp
//        String adType = ad.getAd().getAdType();
//        String adTitle = ad.getAd().getTitle();
//
//        if(adType != null){
//            longitudeEditText.setText(ad.getAd().getAdType());
//        }
//
//        if(adTitle != null){
//            latitudeEditText.setText(ad.getAd().getTitle());
//        }
        float longitude = ad.getAd().getLongitude();
        float latitude = ad.getAd().getLatitude();

        if(longitude != 0){
            longitudeEditText.setText(ad.getAd().getAdType());
        }

        if(latitude != 0){
            latitudeEditText.setText(ad.getAd().getTitle());
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
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("title", "Gladiator");

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

        String longitudeText = longitudeEditText.getText().toString();
        String latitudeText = latitudeEditText.getText().toString();

        if(!longitudeText.isEmpty()){
            ad.getAd().setLongitude(Float.parseFloat(longitudeText));
        }

        if(!latitudeText.isEmpty()){
            ad.getAd().setLatitude(Float.parseFloat(latitudeText));
        }

        //privremeno mapiranje
//        ad.getAd().setAdType(locationEditText.getText().toString());
//        ad.getAd().setTitle(streetEditText.getText().toString());
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
