package rs.ac.uns.ftn.findaroommate.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;

import rs.ac.uns.ftn.findaroommate.R;
import rs.ac.uns.ftn.findaroommate.activity.UserStayDetailActivity;
import rs.ac.uns.ftn.findaroommate.dto.StayDto;

public class MyAdsRecyclerAdapter extends RecyclerView.Adapter<MyAdsRecyclerAdapter.ViewHolder> {

    private List<StayDto> mItems;
    SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy");
    Context context;


    public MyAdsRecyclerAdapter(List<StayDto> items, Context context) {
        mItems = items;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_ads_row, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        StayDto item = mItems.get(i);
        viewHolder.mTextLocation.setText(item.getLocation());
        viewHolder.mTextTitle.setText(item.getTitle());
        viewHolder.mTextFrom.setText(dateFormat.format(item.getFrom()));
        viewHolder.mTextTo.setText(dateFormat.format(item.getTo()));
        viewHolder.detailButton.setText(item.getAdStatus().name());
        viewHolder.mUser.setText(item.getUser().getFirstName() + " " + item.getUser().getLastName());

    }

    private void showPopup(View view) {
        PopupMenu popup = new PopupMenu(context,view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.popup_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(context, "Usao u popup", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        popup.show();
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
        private final TextView detailButton;
        private final TextView mUser;

        ViewHolder(View v) {
            super(v);
            mTextLocation = (TextView)v.findViewById(R.id.list_location);
            mTextFrom = (TextView)v.findViewById(R.id.list_from);
            mTextTo = (TextView)v.findViewById(R.id.list_to);
            mTextTitle = (TextView)v.findViewById(R.id.list_title);
            detailButton = (TextView) v.findViewById(R.id.list_btn);
            mUser = (TextView) v.findViewById(R.id.my_ads_user_id);
        }
    }

}
