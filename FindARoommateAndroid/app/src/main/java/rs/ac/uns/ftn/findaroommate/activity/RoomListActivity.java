package rs.ac.uns.ftn.findaroommate.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import rs.ac.uns.ftn.findaroommate.R;

import rs.ac.uns.ftn.findaroommate.adapters.ImageAdapterOnline;
import rs.ac.uns.ftn.findaroommate.model.Ad;
import rs.ac.uns.ftn.findaroommate.model.ResourceRegistry;
import rs.ac.uns.ftn.findaroommate.model.User;
import rs.ac.uns.ftn.findaroommate.receiver.ResourceRegistryReceiver;
import rs.ac.uns.ftn.findaroommate.service.ResourceRegistryService;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An activity representing a list of Rooms. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link RoomDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class RoomListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    public static List<Ad> adsList = new ArrayList<>();
    public static List<Ad> listOfAvaiable = new ArrayList<>();
    public static Map<String, Ad> adsMap = new HashMap<>();
    public static List<ResourceRegistry> listOfAvaiablePictures = new ArrayList<>();

    public static String PICTURE_URL = "http://HOST/server/user/profile/";

    public static String HOST = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        //updateAdsList();
        //listOfAvaiable = new ArrayList<>();

        /*for (Ad ad:adsList) {
            if(ad.getAvailableFrom().after(new Date()) && ad.getUserId() == null)
                listOfAvaiable.add(ad);
        }*/

       // setUpReceiver();


      /*  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chatIntent = new Intent(RoomListActivity.this, ChatActivity.class);
                startActivity(chatIntent);
            }
        });*/

        if (findViewById(R.id.room_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        View recyclerView = findViewById(R.id.room_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        HOST = getString(R.string.host);


    }

    @Override
    protected void onResume() {
        super.onResume();
        /*View recyclerView = findViewById(R.id.room_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);*/
    }

    /*private void setUpReceiver() {
        Intent resourceIntent = new Intent(this, ResourceRegistryService.class);
        startService(resourceIntent);
    }*/


    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, listOfAvaiable, mTwoPane));
    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final RoomListActivity mParentActivity;
        private final List<Ad> mValues;
        private final boolean mTwoPane;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ad item = (Ad) view.getTag();
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putString(RoomDetailFragment.ARG_ITEM_ID, item.getId().toString());
                    RoomDetailFragment fragment = new RoomDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.room_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, RoomDetailActivity.class);
                    intent.putExtra(RoomDetailFragment.ARG_ITEM_ID, item.getId().toString());

                    context.startActivity(intent);
                }
            }
        };

        SimpleItemRecyclerViewAdapter(RoomListActivity parent,
                                      List<Ad> items,
                                      boolean twoPane) {
            mValues = items;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.room_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mContentView.setText(mValues.get(position).getTitle());
            String strPrice = Float.toString(mValues.get(position).getPrice());

           // Glide.with(mParentActivity).load(R.drawable.apartment1).into(holder.mImage);

            String fileName = null;
            for (ResourceRegistry rr : listOfAvaiablePictures) {
                if(rr.getAddId() == mValues.get(position).getEntityId()) {
                    fileName = rr.getUri();
                    break;
                }
            }

            if(fileName != null) {

                Glide.with(mParentActivity)
                        .load(PICTURE_URL.replace("HOST", mParentActivity.getString(R.string.host)) + fileName)
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                //u pitanju je faild load!!
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                return false;
                            }
                        })
                        .into(holder.mImage);
            } else {
                Glide.with(mParentActivity).load(R.drawable.apartment1).into(holder.mImage);
            }

            holder.mPriceLabel.setText("Price in €:");
            holder.mPrice.setText(strPrice);

            holder.mCostsIncludedLabel.setText("Costs included:");
            if(mValues.get(position).isCostsIncluded()) {
                holder.mCostsIncluded.setText("Yes");
            } else {
                holder.mCostsIncluded.setText("No");
            }


            User user = User.getOneGlobal(mValues.get(position).getOwnerId().getEntityId());

            int now = Calendar.getInstance().get(Calendar.YEAR);
            String age = "";
            if(user.getBirthDay() != null){
                age = ", " + Integer.toString(now - user.getBirthDay().getYear() - 1900);
            }

            holder.mOwner.setText(user.getFirstName() + age);

            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mContentView;
            final TextView mPrice;
            final ImageView mImage;
            final TextView mPriceLabel;
            final TextView mCostsIncludedLabel;
            final TextView mCostsIncluded;
            final TextView mOwner;

            ViewHolder(View view) {
                super(view);
                mContentView = (TextView) view.findViewById(R.id.content);
                mPrice = (TextView) view.findViewById(R.id.price);
                mImage = (ImageView) view.findViewById(R.id.ad_picture);
                mPriceLabel = (TextView) view.findViewById(R.id.price_label);
                mCostsIncludedLabel = (TextView) view.findViewById(R.id.costs_included_label);
                mCostsIncluded = (TextView) view.findViewById(R.id.costs_included);
                mOwner = (TextView) view.findViewById(R.id.owner);
            }
        }
    }

}
