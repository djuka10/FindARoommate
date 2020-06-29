package rs.ac.uns.ftn.findaroommate.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.textfield.TextInputEditText;


import java.util.Arrays;

import rs.ac.uns.ftn.findaroommate.R;
import rs.ac.uns.ftn.findaroommate.activity.NewAdActivity;
import rs.ac.uns.ftn.findaroommate.dto.AdDto;
import rs.ac.uns.ftn.findaroommate.model.Ad;

public class NewAdLocationFragment extends NewAdFragmentAbstact {

    TextInputEditText latitudeEditText;
    TextInputEditText longitudeEditText;

    double latitude;
    double longitude;
    String address = "" +
            "";


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
        titleText.setText(title + dialogNamePatern.replace("NAME", getString(R.string.ad_form_location)));

        if(savedInstanceState != null){
            String val = savedInstanceState.getString("title", "nema");
            int r;
            r= 5;
        }

        if (!Places.isInitialized()) {
            Places.initialize(getContext().getApplicationContext(), getString(R.string.google_place_api_key));
        }

        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
               // getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
                //getActivity().getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
                this.getChildFragmentManager().findFragmentById(R.id.autocomplete_fragment);

//        autocompleteFragment.setTypeFilter(TypeFilter.ADDRESS);
//        autocompleteFragment.setTypeFilter(TypeFilter.CITIES);

        autocompleteFragment.setLocationBias(RectangularBounds.newInstance(
                new LatLng(-33.880490, 151.184363),
                new LatLng(-33.858754, 151.229596)));

        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS));


        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i("fd", "Place: " + place.getName() + ", " + place.getId());
                LatLng latLng = place.getLatLng();
                latitude = latLng.longitude;
                longitude = latLng.latitude;
                address = place.getAddress();

            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i("fd", "An error occurred: " + status);
            }
        });

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

        if(latitude != 0){
            ad.getAd().setLatitude((float)latitude);
        }

        if(longitude != 0){
            ad.getAd().setLongitude((float)longitude);
        }
        ad.getAd().setAddress(address);

        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
