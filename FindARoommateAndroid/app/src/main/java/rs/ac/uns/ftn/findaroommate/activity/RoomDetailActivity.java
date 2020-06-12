package rs.ac.uns.ftn.findaroommate.activity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.widget.Toolbar;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.MenuItem;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rs.ac.uns.ftn.findaroommate.R;
import rs.ac.uns.ftn.findaroommate.adapters.ImageAdapter;
import rs.ac.uns.ftn.findaroommate.adapters.ImageAdapterOnline;
import rs.ac.uns.ftn.findaroommate.model.Ad;
import rs.ac.uns.ftn.findaroommate.model.ResourceRegistry;
import rs.ac.uns.ftn.findaroommate.service.api.ServiceUtils;

/**
 * An activity representing a single Room detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link RoomListActivity}.
 */
public class RoomDetailActivity extends AppCompatActivity {

    ViewPager viewPager;
    int images[] = {R.drawable.apartment1, R.drawable.ic_facebook, R.drawable.ic_google, R.drawable.apartment1};
    ImageAdapter imageAdapter;
    ImageAdapterOnline imageAdapterOnline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chatIntent = new Intent(RoomDetailActivity.this, ChatActivity.class);
                startActivity(chatIntent);
            }
        });

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(RoomDetailFragment.ARG_ITEM_ID,
                    getIntent().getStringExtra(RoomDetailFragment.ARG_ITEM_ID));
            RoomDetailFragment fragment = new RoomDetailFragment();
            fragment.setArguments(arguments);

            CollapsingToolbarLayout layout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
            Ad ad = RoomListActivity.adsMap.get(getIntent().getStringExtra(RoomDetailFragment.ARG_ITEM_ID));


            viewPager = (ViewPager) findViewById(R.id.ViewPage);

            setUpAdapter(ad.getEntityId(), fragment);
//            imageAdapter = new ImageAdapter(RoomDetailActivity.this, images);
//            viewPager.setAdapter(imageAdapter);
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.room_detail_container, fragment)
//                    .commit();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpTo(new Intent(this, RoomListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpAdapter(int entityId, Fragment fragment){
        Call<List<ResourceRegistry>> r = ServiceUtils.adServiceApi.getAdImages(Integer.toString(entityId));

        r.enqueue(new Callback<List<ResourceRegistry>>() {
            @Override
            public void onResponse(Call<List<ResourceRegistry>> call, Response<List<ResourceRegistry>> response) {
                List<ResourceRegistry> body = response.body();
                System.out.println("super");
                imageAdapterOnline = new ImageAdapterOnline(RoomDetailActivity.this, body);
                viewPager.setAdapter(imageAdapterOnline);

                getSupportFragmentManager().beginTransaction()
                        .add(R.id.room_detail_container, fragment)
                        .commit();
            }

            @Override
            public void onFailure(Call<List<ResourceRegistry>> call, Throwable t) {
                System.out.println("Error");
            }
        });
    }
}
