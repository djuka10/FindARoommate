package rs.ac.uns.ftn.findaroommate.activity;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rs.ac.uns.ftn.findaroommate.R;
import rs.ac.uns.ftn.findaroommate.model.Language;
import rs.ac.uns.ftn.findaroommate.utils.Mockup;

public class LanguageChooserActivity extends AppCompatActivity {

    ChipGroup chipGroup;
    List<Language> selectedLanguages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_chooser);

        Toolbar toolbar = (Toolbar) findViewById(R.id.lang_choser_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  // false: ne prikazuje home

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_lang_choser_toolbar);
        collapsingToolbar.setTitle(getString(R.string.profile_form_language));

        Button b = (Button) findViewById(R.id.btn_language_update);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();


                CharSequence[] langArray = new CharSequence[selectedLanguages.size()];

                for(int i = 0; i < selectedLanguages.size(); i++){
                    langArray[i] = selectedLanguages.get(i).getName();
                }

                data.putExtra("selectedLanguages", langArray);
                setResult(RESULT_OK, data);
                finish();
            }
        });

        chipGroup = (ChipGroup) findViewById(R.id.chips);
        createLanguageChips();

        selectedLanguages = new ArrayList<Language>();
    }

    private void createLanguageChips(){
        for (Language lang : Mockup.getInstance().getLanguagesDataSource()){
            Chip c1 = (Chip) this.getLayoutInflater().inflate(R.layout.chip_item, null, false);
            Display display = getWindowManager().getDefaultDisplay();
            int width = display.getWidth();
            double ratio = ((float) (width))/300.0;
            int height = (int)(ratio*50);

            c1.setWidth(width);
            c1.setText(lang.getName());
            c1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Chip c = (Chip) buttonView;
                    if(isChecked){
                        selectedLanguages.add((Language)c.getTag());
                    } else {
                        selectedLanguages.remove((Language)c.getTag());
                    }
                }
            });
            int paddingDp = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 10,
                    getResources().getDisplayMetrics()
            );
            c1.setPadding(paddingDp, 0, paddingDp, 0);
            c1.setTag(lang);

            c1.setOnCloseIconClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean t = true;
                    Chip c = (Chip)v;
                    c.setChecked(false);
                    selectedLanguages.remove((Language)c.getTag());
                }
            });

            chipGroup.addView(c1);
        }
    }
}
