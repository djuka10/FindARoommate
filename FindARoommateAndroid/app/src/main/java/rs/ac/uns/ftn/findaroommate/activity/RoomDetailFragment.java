package rs.ac.uns.ftn.findaroommate.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.text.style.BackgroundColorSpan;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rs.ac.uns.ftn.findaroommate.R;
import rs.ac.uns.ftn.findaroommate.adapter.ChipAdapter;
import rs.ac.uns.ftn.findaroommate.model.Ad;
import rs.ac.uns.ftn.findaroommate.model.AdItem;
import rs.ac.uns.ftn.findaroommate.model.Language;
import rs.ac.uns.ftn.findaroommate.model.User;
import rs.ac.uns.ftn.findaroommate.model.UserCharacteristic;
import rs.ac.uns.ftn.findaroommate.receiver.BookReceiver;
import rs.ac.uns.ftn.findaroommate.service.BookService;
import rs.ac.uns.ftn.findaroommate.service.EditProfileService;
import rs.ac.uns.ftn.findaroommate.utils.AdStatus;
import rs.ac.uns.ftn.findaroommate.utils.AppTools;
import rs.ac.uns.ftn.findaroommate.utils.Mockup;

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
    public static List<AdItem> listAdItems = new ArrayList<>();
    public static List<UserCharacteristic> listUserCharacteristic = new ArrayList<>();

    /**
     * The Ad content this fragment is presenting.
     */
    private Ad mItem;

    //receiver
    BookReceiver bookReceiver;

    //ad id
    Long adId;

    Dialog myDialog;

    PopupWindow popupAnimates;
    ChipGroup chipAnimatesGroup;
    List<AdItem> chips = new ArrayList<>();

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RoomDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        chipAnimatesGroup = new ChipGroup(getContext());

        //adId = Long.parseLong(getArguments().getString(ARG_ITEM_ID));

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            //mItem = RoomListActivity.adsMap.get(getArguments().getString(ARG_ITEM_ID));
            mItem = Ad.getOneGlobal(getArguments().getInt(ARG_ITEM_ID));

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
            String dateUntilStr = df.format(mItem.getAvailableUntil());
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

            Button btnAdItems = (Button) rootView.findViewById(R.id.btn_ad_items);

            btnAdItems.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(!listAdItems.isEmpty()) {
                        popupAnimates = new PopupWindow(inflater.inflate(R.layout.ad_item_popup, null, false),800,1000, true);
                        ListView listView = popupAnimates.getContentView().findViewById(R.id.list_of_animates);
                        TextView textView = popupAnimates.getContentView().findViewById(R.id.popup_title);

                        textView.setText("Amenites");
                        List<String> strings = new ArrayList<>();

                        for(AdItem adItem : listAdItems) {
                            strings.add(adItem.getName());
                        }

                        ArrayAdapter<String> itemsAdapter =
                                new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, strings);
                        listView.setAdapter(itemsAdapter);

                        //popupAnimates.setContentView(chipAnimatesGroup);
                        popupAnimates.showAtLocation(container, Gravity.CENTER, 0, 0);
                    } else {
                        Toast.makeText(getContext(), "There is no amenites for this Ad", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            Button btnUserCharacteristic = (Button) rootView.findViewById(R.id.btn_user_characteristic);

            btnUserCharacteristic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(!listUserCharacteristic.isEmpty()) {
                        popupAnimates = new PopupWindow(inflater.inflate(R.layout.ad_item_popup, null, false),800,1000, true);
                        ListView listView = popupAnimates.getContentView().findViewById(R.id.list_of_animates);
                        List<String> strings = new ArrayList<>();
                        TextView textView = popupAnimates.getContentView().findViewById(R.id.popup_title);

                        textView.setText("Characteristic");

                        for(UserCharacteristic uc : listUserCharacteristic) {
                            strings.add(uc.getValue());
                        }

                        ArrayAdapter<String> itemsAdapter =
                                new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, strings);
                        listView.setAdapter(itemsAdapter);

                        //popupAnimates.setContentView(chipAnimatesGroup);
                        popupAnimates.showAtLocation(container, Gravity.CENTER, 0, 0);
                    }else {
                        Toast.makeText(getContext(), "There is no characteristics for this Ad", Toast.LENGTH_SHORT).show();
                    }

                }
            });

            Button btnRequest = (Button) rootView.findViewById(R.id.btn_request_to_book);

            btnRequest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // send to the server
                    //Ad ad = Ad.load(Ad.class,adId);
                    Ad ad = mItem;
                    User user = AppTools.getLoggedUser();
                    ad.setUserId(user);
                    ad.setAdStatus(AdStatus.PENDING);
                    ad.save();
                    Intent bookIntent = new Intent(getActivity(), BookService.class);
                    bookIntent.putExtra("adId", mItem.getId());
                    getActivity().startService(bookIntent);

                    Intent homepageIntent = new Intent(getActivity(), HomepageActivity.class);
                    startActivity(homepageIntent);
                }
            });
        }

        return rootView;
    }

}
