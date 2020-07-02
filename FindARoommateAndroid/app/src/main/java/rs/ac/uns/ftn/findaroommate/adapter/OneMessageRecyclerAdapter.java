package rs.ac.uns.ftn.findaroommate.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import rs.ac.uns.ftn.findaroommate.R;
import rs.ac.uns.ftn.findaroommate.activity.MessageActivity;
import rs.ac.uns.ftn.findaroommate.dto.FirebaseMessageDto;
import rs.ac.uns.ftn.findaroommate.model.Message;
import rs.ac.uns.ftn.findaroommate.model.Review;

import static android.view.Gravity.LEFT;
import static android.view.Gravity.RIGHT;

public class OneMessageRecyclerAdapter extends RecyclerView.Adapter<OneMessageRecyclerAdapter.ViewHolder> {

    private List<FirebaseMessageDto> mItems;
    private Context context;
    private static final int LEFT = 1;
    private static final int RIGTH = 2;

    private int loggedUserId;


    public OneMessageRecyclerAdapter(List<FirebaseMessageDto> items, Context context, int loggedUserId) {
        mItems = items;
        this.context = context;
        this.loggedUserId = loggedUserId;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v;
        if(viewType == LEFT){
            v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.message_text_row, viewGroup, false);
        } else {
            v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.message_text_row_right, viewGroup, false);
        }
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        FirebaseMessageDto item = mItems.get(i);

        viewHolder.message.setText(item.getMessage());

    }

    @Override
    public int getItemViewType(int position) {
        FirebaseMessageDto firebaseMessageDto = mItems.get(position);
        if(firebaseMessageDto.getSender() == loggedUserId){
            return RIGTH;
        } else {
            return LEFT;
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView message;

        ViewHolder(View v) {
            super(v);
            message = (TextView)v.findViewById(R.id.message_user_name);

        }
    }

}
