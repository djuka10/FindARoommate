package rs.ac.uns.ftn.findaroommate.activity;

import android.content.Context;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rs.ac.uns.ftn.findaroommate.R;
import rs.ac.uns.ftn.findaroommate.adapter.MyActiveAdsRecyclerAdapter;
import rs.ac.uns.ftn.findaroommate.adapter.MyAdsRecyclerAdapter;
import rs.ac.uns.ftn.findaroommate.adapter.UserStayRecyclerAdapter;
import rs.ac.uns.ftn.findaroommate.dto.StayDto;
import rs.ac.uns.ftn.findaroommate.model.Ad;
import rs.ac.uns.ftn.findaroommate.model.User;
import rs.ac.uns.ftn.findaroommate.utils.AppTools;

public class MyAdsActivity extends AppCompatActivity {

    private static String[] tabTitles;
    private User loggedUserModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ads);
        Toolbar toolbar = findViewById(R.id.my_ads_toolbar);
        toolbar.setTitle("My ads");
        setSupportActionBar(toolbar);

        tabTitles = new String[]{"Active", "Past"};

        MyAdsActivity.TabPagerAdapter adapter = new MyAdsActivity.TabPagerAdapter(getSupportFragmentManager());
        ViewPager viewPager = (ViewPager)findViewById(R.id.my_ads_viewpager);
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout)findViewById(R.id.my_ads_tablayout);
        tabLayout.setupWithViewPager(viewPager);



    }

    public static class TabFragment extends Fragment {
        private static final String TAB_TITLE = "title";

        private User loggedUserModel;

        public TabFragment() {

        }

        public static MyAdsActivity.TabFragment newInstance(int tabPosition) {
            MyAdsActivity.TabFragment fragment = new MyAdsActivity.TabFragment();
            Bundle args = new Bundle();
            args.putString(TAB_TITLE, tabTitles[tabPosition]);
            fragment.setArguments(args);
            return fragment;
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            Bundle args = getArguments();
            String tabPosition = args.getString(TAB_TITLE);

            loggedUserModel = AppTools.getLoggedUser();

            View v =  inflater.inflate(R.layout.fragment_list_view, container, false);
            RecyclerView recyclerView = (RecyclerView)v.findViewById(R.id.recyclerview);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

            //List<Ad> listAds = Ad.getAllAdsByUserId(loggedUserModel);
            List<Ad> listAds = Ad.getAllAdsByOwnerId(loggedUserModel);
            List<StayDto> stays = createStays(listAds);
            List<StayDto> pastStays = new ArrayList<>();
            List<StayDto> upcomingStays = new ArrayList<>();
            for (StayDto stay: stays) {
                if(stay.getFrom().before(new Date())) {
                    pastStays.add(stay);
                } else {
                    upcomingStays.add(stay);
                }
            }
            if(tabPosition.contains("Past")) {
                recyclerView.setAdapter(new MyAdsRecyclerAdapter(pastStays,getActivity()));
            } else {
                recyclerView.setAdapter(new MyActiveAdsRecyclerAdapter(upcomingStays,getActivity()));
            }

            //TODO
            //Ubaciti nekako da imamo status Oglasa, tipa PENDING, DENIED, APROVE, IDLE
            //Napraviti enum za to i dodati u oglas
            //Ovde prikazivati samo Ad-ove koji imaju owner_id isto sto i getLogged user id
            //To be continue sutra

            //recyclerView.setAdapter(new MyAdsRecyclerAdapter(stays));

            return v;
        }

        private List<StayDto> createStays(List<Ad> listAds) {
            List<StayDto> stays = new ArrayList<>();
            for (Ad ad: listAds) {
                if(ad.getOwnerId().getEntityId() == AppTools.getLoggedUser().getEntityId())
                    stays.add(new StayDto(ad.getTitle(), "Novi Sad", ad.getAvailableFrom(), ad.getAvailableUntil(),ad.getAdStatus(), ad.getUserId(), ad.getEntityId(), ad.getId()));
            }
            return stays;
        }
    }

    static class TabPagerAdapter extends FragmentStatePagerAdapter {

        public TabPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return MyAdsActivity.TabFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }
    }
}
