package rs.ac.uns.ftn.findaroommate.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rs.ac.uns.ftn.findaroommate.MainActivity;
import rs.ac.uns.ftn.findaroommate.R;
import rs.ac.uns.ftn.findaroommate.dto.UserDto;
import rs.ac.uns.ftn.findaroommate.model.Ad;
import rs.ac.uns.ftn.findaroommate.model.User;
import rs.ac.uns.ftn.findaroommate.service.api.ServiceUtils;
import rs.ac.uns.ftn.findaroommate.utils.AppTools;

public class HomepageActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    private ActionBarDrawerToggle mDrawerToggle;

    public static String HOME_PAGE = "HOME_PAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        //actionBar.setIcon(R.mipmap.ic_logo);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_dot);
        actionBar.setDisplayHomeAsUpEnabled(true);


        /*MenuItem item = (MenuItem) findViewById(R.id.profile_item);
        if(currentUser != null) {
            String displayName = currentUser.getDisplayName();

            // If the above were null, iterate the provider data
            // and set with the first non null data
            for (UserInfo userInfo : currentUser.getProviderData()) {
                if (displayName == null && userInfo.getDisplayName() != null) {
                    displayName = userInfo.getDisplayName();
                }
            }

            item.setTitle(displayName);
        }*/


        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                //Toast.makeText(HomepageActivity.this, menuItem.getTitle(), Toast.LENGTH_LONG).show();

                int id = menuItem.getItemId();

                switch (id) {
                    case android.R.id.home:
                        return true;
                    case R.id.search_item:
                        Intent searchIntent = new Intent(HomepageActivity.this, SearchActivity.class);
                        startActivity(searchIntent);
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
                        removeUserDevice();
                        removePrefs();
                        mAuth.signOut();
                        AppTools.setLoggedUser(null);
                        AppTools.setFirebaseUser(null);
                        Intent signUpIntent = new Intent(HomepageActivity.this, SignUpHomeActivity.class);
                        startActivity(signUpIntent);
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
                Intent newAdIntent = new Intent(HomepageActivity.this, NewAdActivity.class);
                startActivity(newAdIntent);
            }
        });

        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                toolbar,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        // OVOM LINIJOM SE AKTIVIRA LISTENER IZNAD
        mDrawerLayout.setDrawerListener(mDrawerToggle);

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

    // after onStart() and onRestoreInstanceState(). nema bas toliku primenu ima smisla za NavigationDrawer
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.search_item_toolbar:
                Intent searchIntent = new Intent(HomepageActivity.this, SearchActivity.class);
                startActivity(searchIntent);
                return true;
            case android.R.id.home:
                Toast.makeText(this, "TODO: drawer togler or something else for home button", Toast.LENGTH_SHORT).show();
                //return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void removeUserDevice(){
        User loggedUser = AppTools.getLoggedUser();
        UserDto userDto = UserDto.builder().entityId(loggedUser.getEntityId()).deviceId(AppTools.getDeviceId()).build();
        Call<ResponseBody> call = ServiceUtils.userServiceApi.signOut(userDto);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    System.out.println("Meesage recieved");
                    Log.i("fd", "Message received");

                } else {
                    Log.e("editProfileTask", "Error");
                    Intent intent = new Intent(MainActivity.SERVER_ERROR);
                    intent.putExtra("error_message",
                            "Server error while user signout.");
                    sendBroadcast(intent);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("editProfileTask", "Error");
                Intent intent = new Intent(MainActivity.SERVER_ERROR);
                intent.putExtra("error_message",
                        "Server error while user signout.");
                sendBroadcast(intent);
            }
        });
    }

    private void removePrefs(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences.edit().clear().apply();
    }
}
