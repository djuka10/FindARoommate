package rs.ac.uns.ftn.findaroommate.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;

import rs.ac.uns.ftn.findaroommate.R;
import rs.ac.uns.ftn.findaroommate.model.Ad;
import rs.ac.uns.ftn.findaroommate.service.ResourceRegistryService;
import rs.ac.uns.ftn.findaroommate.utils.AdStatus;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        NumberPicker npMin = findViewById(R.id.number_picker_min);

        npMin.setMinValue(10);
        npMin.setMaxValue(100);

        NumberPicker npMax = findViewById(R.id.number_picker_max);

        npMax.setMinValue(100);
        npMax.setMaxValue(500);

        Button adView = (Button) findViewById(R.id.btn_apply);
        adView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SearchActivity.this, "MasterDetailView action", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(SearchActivity.this, RoomListActivity.class);
                //TODO Ovde treba da se pozove servis i da se sinhronizuju oglasi na osnovu search-a


                RoomListActivity.adsList = Ad.getAllAds();
                for (Ad ad: RoomListActivity.adsList) { //ovde bi trebalo available list
                    RoomListActivity.adsMap.put(ad.getId().toString(), ad);
                }

                startActivity(intent);
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

    private boolean checkIfExist(Ad ad) {
        for(Ad ad2 : RoomListActivity.listOfAvaiable) {
            if(ad.getEntityId() == ad2.getEntityId()) {
                return false;
            }
        }
        return true;
    }

}
