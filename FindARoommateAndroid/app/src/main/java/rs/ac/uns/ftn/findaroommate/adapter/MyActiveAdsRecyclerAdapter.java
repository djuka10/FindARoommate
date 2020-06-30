package rs.ac.uns.ftn.findaroommate.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.ViewAnimator;

import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;

import rs.ac.uns.ftn.findaroommate.R;
import rs.ac.uns.ftn.findaroommate.activity.HomepageActivity;
import rs.ac.uns.ftn.findaroommate.dto.StayDto;
import rs.ac.uns.ftn.findaroommate.model.Ad;
import rs.ac.uns.ftn.findaroommate.service.BookService;
import rs.ac.uns.ftn.findaroommate.utils.AdStatus;

public class MyActiveAdsRecyclerAdapter extends RecyclerView.Adapter<MyActiveAdsRecyclerAdapter.ViewHolder> {

    private List<StayDto> mItems;
    SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy");
    Context context;


    public MyActiveAdsRecyclerAdapter(List<StayDto> items, Context context) {
        mItems = items;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_active_ads_row, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        StayDto item = mItems.get(i);
        viewHolder.mTextLocation.setText(item.getLocation());
        viewHolder.mTextTitle.setText(item.getTitle());
        viewHolder.mTextFrom.setText(dateFormat.format(item.getFrom()));
        viewHolder.mTextTo.setText(dateFormat.format(item.getTo()));
        if(item.getUser() != null)
            viewHolder.mUser.setText(item.getUser().getFirstName() + " " + item.getUser().getLastName());


        viewHolder.mAnimator.setText(item.getAdStatus().name());
        if(item.getAdStatus().equals(AdStatus.PENDING)) {
            viewHolder.mAnimator.setBackgroundColor(Color.YELLOW);
        } else if(item.getAdStatus().equals(AdStatus.APPROVE)) {
            viewHolder.mAnimator.setBackgroundColor(Color.GREEN);
        } else if(item.getAdStatus().equals(AdStatus.DENIED)) {
            viewHolder.mAnimator.setBackgroundColor(Color.RED);
        }
        viewHolder.mAnimator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!item.getAdStatus().equals(0)) {
                    //ukoliko je idle nije moguce menjati nista
                    showPopup(view,i);
                }

            }
        });

    }

    private void showPopup(View view,int i) {
        PopupMenu popup = new PopupMenu(context,view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.popup_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.accept:
                        changeAdStatus(true,i);
                        return true;
                    case R.id.reject:
                        changeAdStatus(false,i);
                        return true;
                    default:
                        return false;
                }
            }
        });
        popup.show();
    }

    private void changeAdStatus(boolean b, int i) {
        StayDto item = mItems.get(i);
        Ad ad = Ad.load(Ad.class,item.getId());
        if(b) {
            ad.setAdStatus(AdStatus.APPROVE);
        } else {
            ad.setAdStatus(AdStatus.IDLE);
            ad.setUserId(null);
        }
        ad.save();
        Intent bookIntent = new Intent(context, BookService.class);
        bookIntent.putExtra("adId", ad.getId());
        context.startService(bookIntent);

        Intent homepageIntent = new Intent(context.getApplicationContext(), HomepageActivity.class);
        context.startActivity(homepageIntent);

    }


    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView mTextLocation;
        private final TextView mTextFrom;
        private final TextView mTextTo;
        private final TextView mTextTitle;
        private final TextView mUser;
        private final Button mAnimator;


        ViewHolder(View v) {
            super(v);
            mTextLocation = (TextView)v.findViewById(R.id.list_location);
            mTextFrom = (TextView)v.findViewById(R.id.list_from);
            mTextTo = (TextView)v.findViewById(R.id.list_to);
            mTextTitle = (TextView)v.findViewById(R.id.list_title);
            mAnimator = (Button) v.findViewById(R.id.view_ad_status);
            mUser = (TextView) v.findViewById(R.id.my_ads_user_id);
        }
    }

}
