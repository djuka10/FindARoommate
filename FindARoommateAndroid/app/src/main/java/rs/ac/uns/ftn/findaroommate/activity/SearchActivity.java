package rs.ac.uns.ftn.findaroommate.activity;

import android.os.Bundle;
import android.widget.NumberPicker;

import androidx.appcompat.app.AppCompatActivity;

import rs.ac.uns.ftn.findaroommate.R;

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
    }

}
