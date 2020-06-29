package rs.ac.uns.ftn.findaroommate.activity;

import android.app.AlarmManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import java.util.Locale;

import rs.ac.uns.ftn.findaroommate.R;

public class SettingsActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    //private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        } else {
            ViewGroup v = (ViewGroup) findViewById(android.R.id.content);
            getLayoutInflater().inflate(R.layout.settings_toolbar, (ViewGroup)findViewById(android.R.id.content));
            Toolbar toolbar = (Toolbar)findViewById(R.id.settings_toolbar);
            setSupportActionBar(toolbar);
            actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);

            View linearLayout = v.getChildAt(0);
            int horizontalMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics());
            int verticalMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics());
            int topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (int) getResources().getDimension(R.dimen.activity_vertical_margin), getResources().getDisplayMetrics());
            linearLayout.setPadding(horizontalMargin, topMargin, horizontalMargin, verticalMargin);
        }

//        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        //language change
        if (key.equals("language")){
            String lang = sharedPreferences.getString(key, "en");
            Locale locale;
            switch (lang){
                case "serbian":
                    locale = new Locale("sr", "RS");
                    break;
                default:
                    locale = new Locale("en", "US");
            }

            Locale.setDefault(locale);

            Configuration config = new Configuration();
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
            // opt 1
//            finish();
//            startActivity(getIntent());
            //opt 2
//            recreate();

            //opt 3
            finish();
            overridePendingTransition(0, 0);
            startActivity(getIntent());
            overridePendingTransition(0, 0);
        }

        if (key.equals("reminder_day")){
            String reminder_day_str = sharedPreferences.getString(key, "");
            if(!reminder_day_str.isEmpty() && reminder_day_str.matches("\\d*")){

                int reminder_day = Integer.parseInt(reminder_day_str);
                if(reminder_day < 1 || reminder_day > 15){
                    Toast.makeText(this, getString(R.string.reminder_day_error), Toast.LENGTH_SHORT).show();
                    sharedPreferences.edit().remove(key).apply();

                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(getIntent());
                    overridePendingTransition(0, 0);
                }
            } else {
                Toast.makeText(this, getString(R.string.reminder_day_error), Toast.LENGTH_SHORT).show();
                sharedPreferences.edit().remove(key).apply();

                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
            }

        }
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.preferences, rootKey);

        }
    }
}