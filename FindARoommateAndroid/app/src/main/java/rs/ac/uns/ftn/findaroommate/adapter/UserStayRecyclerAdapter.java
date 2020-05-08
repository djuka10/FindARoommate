package rs.ac.uns.ftn.findaroommate.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;

import rs.ac.uns.ftn.findaroommate.R;
import rs.ac.uns.ftn.findaroommate.activity.UserStayDetailActivity;
import rs.ac.uns.ftn.findaroommate.dto.StayDto;

public class UserStayRecyclerAdapter extends RecyclerView.Adapter<UserStayRecyclerAdapter.ViewHolder> {

    private List<StayDto> mItems;
    SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy");


    public UserStayRecyclerAdapter(List<StayDto> items) {
        mItems = items;
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
                Intent intent = new Intent(context, UserStayDetailActivity.class);
                intent.putExtra("stayId", 1l);
                context.startActivity(intent);
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

}
