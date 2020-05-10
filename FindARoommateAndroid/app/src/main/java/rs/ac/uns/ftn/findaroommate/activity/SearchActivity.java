package rs.ac.uns.ftn.findaroommate.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import rs.ac.uns.ftn.findaroommate.R;
import rs.ac.uns.ftn.findaroommate.model.Ad;

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

                RoomListActivity.adsList = Ad.getAllAds();
                for (Ad ad: RoomListActivity.adsList) {
                    RoomListActivity.adsMap.put(ad.getId().toString(), ad);
                }

                startActivity(intent);
            }
        });
    }

}