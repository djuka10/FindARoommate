package rs.ac.uns.ftn.findaroommate.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.List;

import rs.ac.uns.ftn.findaroommate.R;
import rs.ac.uns.ftn.findaroommate.activity.UserReviewDetailActivity;
import rs.ac.uns.ftn.findaroommate.activity.UserStayDetailActivity;
import rs.ac.uns.ftn.findaroommate.dto.ReviewDto;
import rs.ac.uns.ftn.findaroommate.dto.StayDto;
import rs.ac.uns.ftn.findaroommate.model.Message;

import static android.view.Gravity.END;
import static android.view.Gravity.LEFT;
import static android.view.Gravity.RIGHT;
import static android.view.Gravity.START;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private List<Message> messages;
    private Context context;

    public MessageAdapter(List<Message> messages, Context context) {
       this.messages = messages;
       this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.message, viewGroup, false);

        return new ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Message item = messages.get(i);
        LinearLayout viewMessage = viewHolder.ll_1;
        if(item.getSender().getUserId() == 1) {
            //ako smo mi ulogovani i mi smo sender, poruka treba da se prikaze na desnoj strani ekrana
//            viewMessage.setGravity(END);
            viewMessage.setHorizontalGravity(RIGHT);
        } else if(item.getSender().getUserId() == 2) {
            //ako smo receiver, poruka treba da se prikaze na levoj strani ekrana
//            viewMessage.setGravity(START);
            viewMessage.setHorizontalGravity(LEFT);
        }
        Glide.with(context).load(item.getSender().getUrlProfile()).into(viewHolder.iv_profile_picture);
        viewHolder.tv_first_name_last_name.setText(item.getSender().getFirstName() + item.getSender().getLastName());
        viewHolder.tv_message_content.setText(item.getMessage());
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView iv_profile_picture;
        private final TextView tv_first_name_last_name;
        private final TextView tv_message_content;
        private final LinearLayout ll_1;

        ViewHolder(View v) {
            super(v);
            iv_profile_picture = v.findViewById(R.id.iv_profile_picture);
            tv_first_name_last_name = v.findViewById(R.id.tv_first_name_last_name);
            tv_message_content = v.findViewById(R.id.tv_message_content);
            ll_1 = v.findViewById(R.id.ll_1);
        }
    }

}
