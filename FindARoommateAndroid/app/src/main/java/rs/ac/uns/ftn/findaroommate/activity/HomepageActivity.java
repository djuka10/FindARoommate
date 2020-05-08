package rs.ac.uns.ftn.findaroommate.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import rs.ac.uns.ftn.findaroommate.MainActivity;
import rs.ac.uns.ftn.findaroommate.R;

public class HomepageActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        //actionBar.setIcon(R.mipmap.ic_logo);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_dot);
        actionBar.setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                Toast.makeText(HomepageActivity.this, menuItem.getTitle(), Toast.LENGTH_LONG).show();

                int id = menuItem.getItemId();

                switch (id) {
                    case android.R.id.home:

                        return true;
                    case R.id.search_item:
                        return true;
                    case R.id.profile_item:
                        Intent intent = new Intent(HomepageActivity.this, ProfileActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.settings_item:
                        Intent settingsIntent = new Intent(HomepageActivity.this, SettingsActivity.class);
                        startActivity(settingsIntent);
                        return true;
                    case R.id.sign_out_item:
                        return true;
                }

                return true;
            }
        });

        Button btnSearchAction = (Button)findViewById(R.id.btn_search_action);
        btnSearchAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(HomepageActivity.this, "Search action", Toast.LENGTH_LONG).show();
                Intent settingsIntent = new Intent(HomepageActivity.this, SearchActivity.class);
                startActivity(settingsIntent);
            }
        });

        Button btnNewAddAction = (Button)findViewById(R.id.btn_new_ad_action);
        btnNewAddAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomepageActivity.this, "New ad action", Toast.LENGTH_LONG).show();
            }
        });

        CardView mCardViewTop = (CardView) findViewById(R.id.ca);
        CardView mCardViewBottom = (CardView) findViewById(R.id.cb);

        mCardViewTop.setRadius(50);
        mCardViewBottom.setRadius(50);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:

                return true;
            case R.id.search_item:
                return true;
            case R.id.profile_item:
                return true;
            case R.id.settings_item:
                return true;
            case R.id.sign_out_item:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
