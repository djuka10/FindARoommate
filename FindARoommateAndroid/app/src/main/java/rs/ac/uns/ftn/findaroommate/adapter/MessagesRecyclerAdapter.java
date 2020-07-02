package rs.ac.uns.ftn.findaroommate.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import rs.ac.uns.ftn.findaroommate.R;
import rs.ac.uns.ftn.findaroommate.activity.MessageActivity;
import rs.ac.uns.ftn.findaroommate.activity.ProfileActivity;
import rs.ac.uns.ftn.findaroommate.activity.UserReviewDetailActivity;
import rs.ac.uns.ftn.findaroommate.model.Review;
import rs.ac.uns.ftn.findaroommate.model.User;

public class MessagesRecyclerAdapter extends RecyclerView.Adapter<MessagesRecyclerAdapter.ViewHolder> {

    private List<User> mItems;
    private Context context;

    public static String PROFILE_URL = "http://HOST/server/user/profile/";

    public MessagesRecyclerAdapter(List<User> items, Context context) {
        mItems = items;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.message_row, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        User item = mItems.get(i);
        viewHolder.userNameText.setText(item.getFirstName());
        viewHolder.userLastMsg.setText(item.getOccupation());

        if(item.getUrlProfile() != null) {
            if (item.getUrlProfile().startsWith("http")) { // fotografija je sa google naloga
                Glide.with(context).load(item.getUrlProfile()).into(viewHolder.userProfileImage);
            } else {

                Glide.with(context.getApplicationContext())
                        .load(PROFILE_URL.replace("HOST", context.getString(R.string.host)) + item.getUrlProfile())
                        .listener(new RequestListener<Drawable>() {
                                      @Override
                                      public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                          return false;
                                      }

                                      @Override
                                      public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                          return false;
                                      }
                                  }
                        )
                        .into(viewHolder.userProfileImage);
            }
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent messageIntent = new Intent(context, MessageActivity.class);
                messageIntent.putExtra("receiverId", item.getEntityId());
                context.startActivity(messageIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView userNameText;
        private final TextView userLastMsg;
        private final ImageView userProfileImage;


        ViewHolder(View v) {
            super(v);
            userNameText = (TextView)v.findViewById(R.id.message_user_name);
            userLastMsg = (TextView)v.findViewById(R.id.message_user_last_msg);
            userProfileImage = (ImageView) v.findViewById(R.id.message_profile_img);
        }
    }

}
