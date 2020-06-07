package rs.ac.uns.ftn.findaroommate.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Map;

import rs.ac.uns.ftn.findaroommate.R;
import rs.ac.uns.ftn.findaroommate.model.Ad;
import rs.ac.uns.ftn.findaroommate.model.User;
import rs.ac.uns.ftn.findaroommate.receiver.BookReceiver;
import rs.ac.uns.ftn.findaroommate.service.BookService;
import rs.ac.uns.ftn.findaroommate.service.EditProfileService;
import rs.ac.uns.ftn.findaroommate.utils.AppTools;

/**
 * A fragment representing a single Room detail screen.
 * This fragment is either contained in a {@link RoomListActivity}
 * in two-pane mode (on tablets) or a {@link RoomDetailActivity}
 * on handsets.
 */
public class RoomDetailFragment extends Fragment{
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The Ad content this fragment is presenting.
     */
    private Ad mItem;

    //receiver
    BookReceiver bookReceiver;

    //ad id
    Long adId;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RoomDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adId = Long.parseLong(getArguments().getString(ARG_ITEM_ID));

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = RoomListActivity.adsMap.get(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.getTitle());
            }
        }

        setUpReceiver();
    }

    private void setUpReceiver() {
        bookReceiver = new BookReceiver();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.room_detail, container, false);

        CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) getActivity().findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            appBarLayout.setTitle(mItem.getTitle());
        }

        // Show ad content as text in a TextView.
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.room_title_frag)).setText(mItem.getTitle());
            ((TextView) rootView.findViewById(R.id.room_detail_text)).setText(mItem.getDescription());
            /*((TextView) rootView.findViewById(R.id.room_ad_type)).setText(mItem.getAdType());*/
            ((TextView) rootView.findViewById(R.id.room_longitude)).setText(String.valueOf(mItem.getLongitude()));
            ((TextView) rootView.findViewById(R.id.room_latitude)).setText(String.valueOf(mItem.getLatitude()));
            ((TextView) rootView.findViewById(R.id.room_flat_m2)).setText(String.valueOf(mItem.getFlatM2()));
            ((TextView) rootView.findViewById(R.id.room_room_m2)).setText(String.valueOf(mItem.getRoomM2()));
            ((TextView) rootView.findViewById(R.id.room_price)).setText(String.valueOf(mItem.getPrice()));
            if(mItem.isCostsIncluded()) {
                ((TextView) rootView.findViewById(R.id.room_costs_included)).setText("Yes");
            } else {
                ((TextView) rootView.findViewById(R.id.room_costs_included)).setText("No");
            }
            ((TextView) rootView.findViewById(R.id.room_deposit)).setText(String.valueOf(mItem.getDeposit()));


            String pattern = "MM/dd/yyyy";
            DateFormat df = new SimpleDateFormat(pattern);
            String dateFromStr = df.format(mItem.getAvailableFrom());
            String dateUntilStr = df.format(mItem.getAvailableFrom());
            ((TextView) rootView.findViewById(R.id.room_available_from)).setText(dateFromStr);
            ((TextView) rootView.findViewById(R.id.room_available_until)).setText(dateUntilStr);

            ((TextView) rootView.findViewById(R.id.room_min_days)).setText(String.valueOf(mItem.getMinDays()));
            ((TextView) rootView.findViewById(R.id.room_max_person)).setText(String.valueOf(mItem.getMaxPerson()));
            ((TextView) rootView.findViewById(R.id.room_ladies_num)).setText(String.valueOf(mItem.getLadiesNum()));
            ((TextView) rootView.findViewById(R.id.room_boys_num)).setText(String.valueOf(mItem.getBoysNum()));

            ImageView btnMapView = (ImageView) rootView.findViewById(R.id.btnMapView);

            btnMapView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), MapActivity.class);

                    intent.putExtra("longitude", mItem.getLongitude());
                    intent.putExtra("latitude", mItem.getLatitude());

                    startActivity(intent);
                }
            });

            Button btnRequest = (Button) rootView.findViewById(R.id.btn_request_to_book);

            btnRequest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // send to the server
                    Ad ad = Ad.load(Ad.class,adId);
                    User user = AppTools.getLoggedUser();
                    ad.setUserId(user);
                    ad.save();
                    Intent bookIntent = new Intent(getActivity(), BookService.class);
                    bookIntent.putExtra("adId", mItem.getId());
                    getActivity().startService(bookIntent);
                }
            });
        }

        return rootView;
    }

}
