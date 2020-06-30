package rs.ac.uns.ftn.findaroommate.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import rs.ac.uns.ftn.findaroommate.R;
import rs.ac.uns.ftn.findaroommate.dto.SearchDto;
import rs.ac.uns.ftn.findaroommate.fragment.NewAdRoomFragment;
import rs.ac.uns.ftn.findaroommate.model.Ad;
import rs.ac.uns.ftn.findaroommate.service.ResourceRegistryService;
import rs.ac.uns.ftn.findaroommate.utils.AdStatus;

public class SearchActivity extends AppCompatActivity {

    double longitude;
    double latitude;

    NumberPicker costsMin;
    NumberPicker costsMax;

    TextInputEditText searchFromEditText;
    TextInputEditText searchToEditText;

    RadioGroup durationOfStayRadioGroup;

    DatePickerDialog datePicker;

    SearchDto searchDto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchDto = new SearchDto();

        setupSearchComponents();
        setupPlaceAutocompleteComponent();

        Button adView = (Button) findViewById(R.id.btn_apply);
        adView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(valid()){
                    preparedSearchFields();

                    Toast.makeText(SearchActivity.this, "MasterDetailView action", Toast.LENGTH_LONG).show();

                    RoomListActivity.listOfAvaiable = search();

                    Intent intent = new Intent(SearchActivity.this, RoomListActivity.class);
                    //TODO Ovde treba da se pozove servis i da se sinhronizuju oglasi na osnovu search-a
//                RoomListActivity.adsList = Ad.getAllAds();
//                for (Ad ad: RoomListActivity.adsList) {
//                    RoomListActivity.adsMap.put(ad.getId().toString(), ad);
//                }

                    startActivity(intent);
                }


            }
        });
        //Getovati sa servera celu listu Ad-ova

        //setUpAdReceiver();

        RoomListActivity.adsList = Ad.getAllAds();
        for (Ad ad:RoomListActivity.adsList) {
            if(ad.getAvailableFrom().after(new Date()) && ad.getUserId() == null && ad.getAdStatus().equals(AdStatus.IDLE))
                if(checkIfExist(ad))
                    RoomListActivity.listOfAvaiable.add(ad);
        }

        //ovo bi se moglo pozvati kada se obavi "Search" metoda, za sad se poziva na kraju ove
        setUpReceiver();
    }

    /*private void setUpAdReceiver() {
        Intent adIntent = new Intent(this, AdService.class);
        startService(adIntent);
    }*/

    private void setUpReceiver() {
        Intent resourceIntent = new Intent(this, ResourceRegistryService.class);
        startService(resourceIntent);
    }

<<<<<<< HEAD
    private boolean checkIfExist(Ad ad) {
        for(Ad ad2 : RoomListActivity.listOfAvaiable) {
            if(ad.getEntityId() == ad2.getEntityId()) {
                return false;
            }
        }
        return true;
    }

=======
    private void setupSearchComponents(){
        costsMin = findViewById(R.id.number_picker_min);

        costsMin.setMinValue(50);
        costsMin.setMaxValue(100);

        costsMax = findViewById(R.id.number_picker_max);

        costsMax.setMinValue(100);
        costsMax.setMaxValue(500);
        costsMax.setValue(250);

        durationOfStayRadioGroup = (RadioGroup) findViewById(R.id.radio_group_duration);

        searchFromEditText = (TextInputEditText) findViewById(R.id.search_date_from);
        searchFromEditText.setInputType(InputType.TYPE_NULL);
        searchFromEditText.setOnFocusChangeListener(new FocusListener("FROM"));

        searchToEditText = (TextInputEditText) findViewById(R.id.search_date_to);
        searchToEditText.setInputType(InputType.TYPE_NULL);
        searchToEditText.setOnFocusChangeListener(new FocusListener("TO"));
    }

    private void setupPlaceAutocompleteComponent(){
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), getString(R.string.google_place_api_key));
        }

        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_search_fragment);
        autocompleteFragment.setTypeFilter(TypeFilter.CITIES);

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

                searchDto.setLatitude((float)latitude);
                searchDto.setLongitude((float)longitude);
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i("fd", "An error occurred: " + status);
            }
        });
    }

    class FocusListener implements View.OnFocusChangeListener{

        String dateType;

        public FocusListener(String dateType){
            this.dateType = dateType;
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if(v == searchFromEditText || v == searchToEditText){
                if(hasFocus){
                    final EditText source = (EditText) v;

                    Date dateToView;
                    if(dateType.equals("FROM")){
                        dateToView = searchDto.getFrom();
                    } else {
                        dateToView = searchDto.getTo();
                    }

                    final Calendar cldr = Calendar.getInstance();

                    if(dateToView != null) {
                        cldr.setTime(dateToView);
                    }

                    int day = cldr.get(Calendar.DAY_OF_MONTH);
                    int month = cldr.get(Calendar.MONTH);
                    int year = cldr.get(Calendar.YEAR);

                    datePicker = new DatePickerDialog(SearchActivity.this, null, year, month, day);
                    datePicker.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            source.setText(dayOfMonth + "/" + (month + 1) + "/" + year);

                            Calendar c = Calendar.getInstance();
                            c.set(year, month, dayOfMonth, 0, 0);

                            Date d = c.getTime();
                            if(dateType.equals("FROM")){
                                searchDto.setFrom(d);
                            } else {
                                searchDto.setTo(d);
                            }
                        }
                    });
                    datePicker.show();
                }
            }
        }
    }

    private boolean valid(){
        Date searchFrom = searchDto.getFrom();
        Date searchTo = searchDto.getTo();

        if(searchFrom == null && searchTo == null){
            return true;
        }

        // one date field is empty, second date field is not
        if( (searchFrom == null && searchTo != null) || (searchFrom != null && searchTo == null) ){
            Toast.makeText(this, getString(R.string.date_required_error_msg), Toast.LENGTH_SHORT).show();
            return false;
        }

        // incorect date order
        if( searchFrom.getTime() > searchTo.getTime()){
            Toast.makeText(this, getString(R.string.date_order_error_msg), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void preparedSearchFields(){

        searchDto.setCostsMin((float)costsMin.getValue());
        searchDto.setCostsMax((float)costsMax.getValue());

        int checkedPopertyType = durationOfStayRadioGroup.getCheckedRadioButtonId();
        if(checkedPopertyType != -1){
            RadioButton val = findViewById(checkedPopertyType);
            searchDto.setDurationOfStay(val.getText().toString());
        } else {
            searchDto.setDurationOfStay("");
        }
        searchDto.getCostsMax();
    }

    private List<Ad> search(){
//        float NsKac = this.getDistanceFromLatLonInKm(searchDto.getLongitude(), searchDto.getLatitude(), 45.303114f, 19.966207f);
//
//        float BgNs = this.getDistanceFromLatLonInKm(45.27f, 19.83f, 44.79f, 20.45f);
//        float NiNs = this.getDistanceFromLatLonInKm(45.27f, 19.83f, 43.32f, 21.89f);
//        float NsRioBrasil = this.getDistanceFromLatLonInKm(45.27f, 19.83f, -22.9f, -43.17f);
        List<Ad> allAds = Ad.getAllAds();

        List<Ad> filtered = Ad.getAllAds()
                .stream()
                .filter(ad -> ad.getUserId() == null)
                .filter(ad -> ad.getAdStatus().equals(AdStatus.IDLE))
                .filter(ad -> searchDto.getCostsMin() <= ad.getPrice() && ad.getPrice() <= searchDto.getCostsMax())
                .collect(Collectors.toList());

        if(!searchDto.getDurationOfStay().isEmpty()){
            filtered = filtered.stream()
                    .filter(ad -> ad.getMinDays().equals(searchDto.getDurationOfStay()))

                    .collect(Collectors.toList());
        }

        if(searchDto.getFrom() != null && searchDto.getTo() != null){
            Ad a = allAds.get(0);
            long searchFrom = searchDto.getFrom().getTime();
            long searchTo = searchDto.getTo().getTime();
            long adFrom = a.getAvailableFrom().getTime();
            long adTo = a.getAvailableUntil().getTime();


            filtered = filtered.stream()
                    .filter(ad -> searchDto.getFrom().getTime() >= ad.getAvailableFrom().getTime()
                        && ad.getAvailableUntil().getTime() >= searchDto.getTo().getTime()
                    )
                    .collect(Collectors.toList());
        }

        if(searchDto.getLatitude() != 0 && searchDto.getLongitude() != 0){
            // radius less than 50km
            filtered = filtered.stream()
                    .filter(ad ->
                            getDistanceFromLatLonInKm(
                                    ad.getLongitude(), ad.getLatitude(),
                                    searchDto.getLongitude(), searchDto.getLatitude()) < 50)
                    .collect(Collectors.toList());
        }

        return filtered;
    }

    private float getDistanceFromLatLonInKm( float lon1 , float lat1, float lon2 , float lat2) {
        double R = 6371; // Radius of the earth in km
        double dLat = this.deg2rad(lat2-lat1);  // deg2rad below
        double dLon = this.deg2rad(lon2-lon1);
        double a =
                Math.sin(dLat/2) * Math.sin(dLat/2) +
                        Math.cos(this.deg2rad(lat1)) * Math.cos(this.deg2rad(lat2)) *
                                Math.sin(dLon/2) * Math.sin(dLon/2)
                ;
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d = R * c; // Distance in km
        String distPattern = "P1(%.6f, %.6f) <--> P2(%.6f, %.6f)  -> distance = %.6f";
        Log.d("Two places distance", String.format(distPattern, lon1, lat1, lon2, lat2, d));
        return (float)d;
    }

    private double deg2rad(float degree) {
        return degree * (Math.PI/180);
    }

>>>>>>> View filtered ad results by search
}
