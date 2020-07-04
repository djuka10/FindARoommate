package rs.ac.uns.ftn.findaroommate.activity;

import android.app.AlarmManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
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

import java.io.IOException;
import java.util.Locale;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rs.ac.uns.ftn.findaroommate.R;
import rs.ac.uns.ftn.findaroommate.dto.UserSettings;
import rs.ac.uns.ftn.findaroommate.service.api.ServiceUtils;
import rs.ac.uns.ftn.findaroommate.utils.AppTools;

public class SettingsActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

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
    public void onBackPressed() {
        super.onBackPressed();
        updateSettings();
    }

    private void updateSettings(){
        UserSettings u = prepareSettingsToServer();
        Call<ResponseBody> call = ServiceUtils.userServiceApi.updateSettings(u);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){

                } else {
                    System.out.println("Error!");
                    Log.e("error", "Error");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println("Error!");
                Log.e("error", t.getMessage());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        updateSettings();


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

    private UserSettings prepareSettingsToServer(){
        UserSettings userSettings = new UserSettings();

        SharedPreferences s = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        boolean prefs_should_remind = s.getBoolean(getString(R.string.prefs_should_remind), false);

        boolean prefs_should_confirm_mail = s.getBoolean(getString(R.string.prefs_should_confirm_mail), false);
        boolean prefs_should_request_mail = s.getBoolean(getString(R.string.prefs_should_request_mail), false);
        boolean prefs_should_new_ad_mail = s.getBoolean(getString(R.string.prefs_should_new_ad_mail), false);

        boolean prefs_stay_notif = s.getBoolean(getString(R.string.prefs_stay_notif), false);
        boolean prefs_new_review_notif = s.getBoolean(getString(R.string.prefs_new_review_notif), false);
        boolean prefs_new_message_notif = s.getBoolean(getString(R.string.prefs_new_message_notif), false);

        String prefs_remind_day = s.getString(getString(R.string.prefs_remind_day), "5");
        String prefs_unit = s.getString(getString(R.string.prefs_unit), "km");
        String prefs_lang = s.getString(getString(R.string.prefs_lang), "english");

        userSettings.setNewValues(
                prefs_unit,
                prefs_lang,
                prefs_remind_day,
                prefs_new_message_notif,
                prefs_new_review_notif,
                prefs_stay_notif, prefs_should_confirm_mail,
                prefs_should_new_ad_mail,
                prefs_should_request_mail,
                prefs_should_remind,
                AppTools.getLoggedUser().getEntityId()
        );

        return userSettings;
    }
}