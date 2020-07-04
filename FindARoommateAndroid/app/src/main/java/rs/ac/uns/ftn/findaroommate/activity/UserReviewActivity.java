package rs.ac.uns.ftn.findaroommate.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.util.List;

import rs.ac.uns.ftn.findaroommate.R;
import rs.ac.uns.ftn.findaroommate.adapter.UserReviewRecyclerAdapter;
import rs.ac.uns.ftn.findaroommate.adapter.UserStayRecyclerAdapter;
import rs.ac.uns.ftn.findaroommate.model.Review;
import rs.ac.uns.ftn.findaroommate.model.User;
import rs.ac.uns.ftn.findaroommate.utils.AppTools;
import rs.ac.uns.ftn.findaroommate.utils.Mockup;

public class UserReviewActivity extends AppCompatActivity {

    private static String[] tabTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_review);

        Toolbar toolbar = (Toolbar) findViewById(R.id.user_review_toolbar);
        toolbar.setTitle("My reviews");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  // false: ne prikazuje home

        tabTitles = new String[]{"About me", "From me"};

        TabPagerAdapter adapter = new TabPagerAdapter(getSupportFragmentManager());
        ViewPager viewPager = (ViewPager)findViewById(R.id.user_review_viewpager);
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout)findViewById(R.id.user_review_tablayout);
        tabLayout.setupWithViewPager(viewPager);
    }

    public static class OtherMarksFragment extends Fragment {
        private static final String TAB_TITLE = "title";

        public OtherMarksFragment() {

        }

        public static OtherMarksFragment newInstance(int tabPosition) {
            OtherMarksFragment fragment = new OtherMarksFragment();
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

            View v =  inflater.inflate(R.layout.other_marks, container, false);

            TextView revUserName = (TextView) v.findViewById(R.id.review_user_name);
            TextView revUserGrade = (TextView) v.findViewById(R.id.review_user_grade);
            TextView revUserNum = (TextView) v.findViewById(R.id.review_user_num);

            User loggedUser = AppTools.getLoggedUser();
            List<Review> gradesAboutMe = Review.getAboutMe(loggedUser.getEntityId());

            double avgGrade = gradesAboutMe.stream()
                    .mapToDouble(Review::getRating)
                    .average()
                    .orElse(Double.NaN);

            revUserName.setText(loggedUser.getFirstName());
            revUserGrade.setText(String.format("%.2f", avgGrade));
            revUserNum.setText(String.valueOf(gradesAboutMe.size()) + " " + getString(R.string.profile_reviews));

            LinearLayoutManager recyclerViewLayoutManager = new LinearLayoutManager(getActivity());
            DividerItemDecoration divider = new DividerItemDecoration(getActivity(), recyclerViewLayoutManager.getOrientation());
            RecyclerView recyclerView = (RecyclerView)v.findViewById(R.id.other_marks_recyclerview);
            recyclerView.setLayoutManager(recyclerViewLayoutManager);
            recyclerView.addItemDecoration(divider);

            recyclerView.setAdapter(new UserReviewRecyclerAdapter(gradesAboutMe
                    , true));

            return v;
        }
    }

    public static class MyMarksFragment extends Fragment {
        private static final String TAB_TITLE = "title";

        public MyMarksFragment() {

        }

        public static MyMarksFragment newInstance(int tabPosition) {
            MyMarksFragment fragment = new MyMarksFragment();
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

            View v =  inflater.inflate(R.layout.other_marks, container, false);

            TextView revUserName = (TextView) v.findViewById(R.id.review_user_name);
            TextView revUserGrade = (TextView) v.findViewById(R.id.review_user_grade);
            TextView revUserNum = (TextView) v.findViewById(R.id.review_user_num);

            User loggedUser = AppTools.getLoggedUser();
            List<Review> gradesFromMe = Review.getFromMe(loggedUser.getEntityId());

            double avgGrade = gradesFromMe.stream()
                    .mapToDouble(Review::getRating)
                    .average()
                    .orElse(Double.NaN);

            revUserName.setText("My marks");
            revUserGrade.setText(String.format("%.2f", avgGrade));
            revUserNum.setText(String.valueOf(gradesFromMe.size()) + " " + getString(R.string.profile_reviews));

            LinearLayoutManager recyclerViewLayoutManager = new LinearLayoutManager(getActivity());
            DividerItemDecoration divider = new DividerItemDecoration(getActivity(), recyclerViewLayoutManager.getOrientation());
            RecyclerView recyclerView = (RecyclerView)v.findViewById(R.id.other_marks_recyclerview);
            recyclerView.setLayoutManager(recyclerViewLayoutManager);
            recyclerView.addItemDecoration(divider);

            recyclerView.setAdapter(
                    new UserReviewRecyclerAdapter(gradesFromMe, false));

            return v;
        }
    }

    static class TabPagerAdapter extends FragmentStatePagerAdapter {


        public TabPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return OtherMarksFragment.newInstance(position);
                case 1:
                    return MyMarksFragment.newInstance(position);
                default:
                    return null;
            }
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
