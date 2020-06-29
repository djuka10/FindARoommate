package rs.ac.uns.ftn.findaroommate.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;

import rs.ac.uns.ftn.findaroommate.R;
import rs.ac.uns.ftn.findaroommate.activity.UserStayDetailActivity;
import rs.ac.uns.ftn.findaroommate.dto.StayDto;
import rs.ac.uns.ftn.findaroommate.model.Review;
import rs.ac.uns.ftn.findaroommate.model.User;
import rs.ac.uns.ftn.findaroommate.utils.AppTools;

public class UserStayRecyclerAdapter extends RecyclerView.Adapter<UserStayRecyclerAdapter.ViewHolder> {

    private List<StayDto> mItems;
    SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy");

    private boolean pastStays;

    public UserStayRecyclerAdapter(List<StayDto> items) {
        mItems = items;
    }

    public UserStayRecyclerAdapter(List<StayDto> items, boolean pastStays) {
        mItems = items;
        this.pastStays = pastStays;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.stay_row, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        StayDto item = mItems.get(i);
        viewHolder.mTextLocation.setText(item.getLocation());
        viewHolder.mTextTitle.setText(item.getTitle());
        viewHolder.mTextFrom.setText(dateFormat.format(item.getFrom()));
        viewHolder.mTextTo.setText(dateFormat.format(item.getTo()));


        viewHolder.detailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                if(pastStays){
                    if(!alreadyAddedReview(item.getEntity_id())){
                        Intent intent = new Intent(context, UserStayDetailActivity.class);
                        intent.putExtra("stayId", item.getEntity_id());
                        context.startActivity(intent);
                    } else {
                        Log.i("User review action", "For this stay user already added review.");
                    }

                } else {
                    Toast.makeText(context, "AD STATUS: " + item.getAdStatus().name(), Toast.LENGTH_SHORT).show();
                }

            }
        });
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
        private final Button detailButton;

        ViewHolder(View v) {
            super(v);
            mTextLocation = (TextView)v.findViewById(R.id.list_location);
            mTextFrom = (TextView)v.findViewById(R.id.list_from);
            mTextTo = (TextView)v.findViewById(R.id.list_to);
            mTextTitle = (TextView)v.findViewById(R.id.list_title);
            detailButton = (Button) v.findViewById(R.id.list_btn);
        }
    }

    private boolean alreadyAddedReview(int adId){
        User user = AppTools.getLoggedUser();
        Review addedReview = Review.getOne(adId, user.getEntityId());
        if (addedReview != null){
            return true;
        }

        return false;
    }

}
