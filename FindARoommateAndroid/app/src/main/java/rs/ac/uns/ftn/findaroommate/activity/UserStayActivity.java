package rs.ac.uns.ftn.findaroommate.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rs.ac.uns.ftn.findaroommate.R;
import rs.ac.uns.ftn.findaroommate.adapter.UserStayRecyclerAdapter;
import rs.ac.uns.ftn.findaroommate.dto.StayDto;
import rs.ac.uns.ftn.findaroommate.model.Ad;
import rs.ac.uns.ftn.findaroommate.model.User;
import rs.ac.uns.ftn.findaroommate.utils.AppTools;
import rs.ac.uns.ftn.findaroommate.utils.Mockup;

public class UserStayActivity extends AppCompatActivity {

    private static String[] tabTitles;
    private User loggedUserModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_stay);

        loggedUserModel = AppTools.getLoggedUser();
        if(loggedUserModel == null)
            return;

        Toolbar toolbar = (Toolbar) findViewById(R.id.user_stay_toolbar);
        toolbar.setTitle("My stays");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  // false: ne prikazuje home

        tabTitles = new String[]{"Upcoming", "Past"};

        TabPagerAdapter adapter = new TabPagerAdapter(getSupportFragmentManager());
        ViewPager viewPager = (ViewPager)findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout)findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
    }

    public static class TabFragment extends Fragment {
        private static final String TAB_TITLE = "title";

        private User loggedUserModel;

        public TabFragment() {

        }

        public static TabFragment newInstance(int tabPosition) {
            TabFragment fragment = new TabFragment();
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

            List<Ad> listAds = Ad.getAllAdsByUserId(loggedUserModel);
            List<StayDto> stays = createStays(listAds);
            List<StayDto> pastStays = new ArrayList<>();
            List<StayDto> upcomingStays = new ArrayList<>();
            for (StayDto stay: stays) {
                if(stay.getFrom().before(new Date())) {
                    //ovde cepamo past
                    pastStays.add(stay);
                } else {
                    upcomingStays.add(stay);
                }
            }
            if(tabPosition.contains("Past")) {
                recyclerView.setAdapter(new UserStayRecyclerAdapter(pastStays, true));
            } else {
                recyclerView.setAdapter(new UserStayRecyclerAdapter(upcomingStays));
            }



            //recyclerView.setAdapter(new UserStayRecyclerAdapter(stays));

            return v;
        }

        private List<StayDto> createStays(List<Ad> listAds) {
            List<StayDto> stays = new ArrayList<>();
            for (Ad ad: listAds) {
                User user = User.getOneGlobal(ad.getUserId());
                stays.add(new StayDto(ad.getTitle(), ad.getAddress(), ad.getAvailableFrom(), ad.getAvailableUntil(), ad.getAdStatus(), user, ad.getEntityId(),ad.getId()));
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
            return TabFragment.newInstance(position);
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
