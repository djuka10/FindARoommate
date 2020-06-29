package rs.ac.uns.ftn.findaroommate.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;

import rs.ac.uns.ftn.findaroommate.R;
import rs.ac.uns.ftn.findaroommate.activity.UserReviewDetailActivity;
import rs.ac.uns.ftn.findaroommate.activity.UserStayDetailActivity;
import rs.ac.uns.ftn.findaroommate.dto.ReviewDto;
import rs.ac.uns.ftn.findaroommate.dto.StayDto;
import rs.ac.uns.ftn.findaroommate.model.Review;

public class UserReviewRecyclerAdapter extends RecyclerView.Adapter<UserReviewRecyclerAdapter.ViewHolder> {

    private List<Review> mItems;
    private boolean otherGrades;

    public UserReviewRecyclerAdapter(List<Review> items) {
        mItems = items;
    }

    public UserReviewRecyclerAdapter(List<Review> items, boolean otherGrades) {
        this(items);
        this.otherGrades = otherGrades;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.review_row, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Review item = mItems.get(i);
        viewHolder.mRevName.setText(item.getReviewerName());
        viewHolder.mRevText.setText(item.getComment());
        viewHolder.mRevTitle.setText(item.getTitle());
        viewHolder.mRevGrade.setRating((float)item.getRating());

        viewHolder.detailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, UserReviewDetailActivity.class);
                intent.putExtra("revId", 1l);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView mRevName;
        private final RatingBar mRevGrade;
        private final TextView mRevText;
        private final TextView mRevTitle;
        private final ImageButton detailButton;

        ViewHolder(View v) {
            super(v);
            mRevName = (TextView)v.findViewById(R.id.review_row_name);
            mRevGrade = (RatingBar) v.findViewById(R.id.review_row_grade);
            mRevText = (TextView)v.findViewById(R.id.review_row_text);
            mRevTitle = (TextView)v.findViewById(R.id.review_row_title);
            detailButton = (ImageButton) v.findViewById(R.id.review_row_detail);
        }
    }

}
