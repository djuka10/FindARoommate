package rs.ac.uns.ftn.findaroommate.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rs.ac.uns.ftn.findaroommate.R;
import rs.ac.uns.ftn.findaroommate.adapter.MessagesRecyclerAdapter;
import rs.ac.uns.ftn.findaroommate.adapter.OneMessageRecyclerAdapter;
import rs.ac.uns.ftn.findaroommate.dto.FCMData;
import rs.ac.uns.ftn.findaroommate.dto.FCMRequest;
import rs.ac.uns.ftn.findaroommate.dto.FCMResponse;
import rs.ac.uns.ftn.findaroommate.dto.FirebaseMessageDto;
import rs.ac.uns.ftn.findaroommate.dto.FirebaseUserDto;
import rs.ac.uns.ftn.findaroommate.model.Review;
import rs.ac.uns.ftn.findaroommate.model.User;
import rs.ac.uns.ftn.findaroommate.service.api.ServiceUtils;
import rs.ac.uns.ftn.findaroommate.utils.AppTools;

public class MessageActivity extends AppCompatActivity {

    EditText newMessageEditText;
    int receiverId;
    //sender
    int loggedUserId;
    User loggedUser;

    int adId;

    OneMessageRecyclerAdapter messagesRecyclerAdapter;
    RecyclerView messagesRecyclerView;
    List<FirebaseMessageDto> messages;

    User receiver;

    boolean notify = true;

    public static String PROFILE_URL = "http://HOST/server/user/profile/";

    ImageView message_profile_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        Toolbar toolbar = (Toolbar) findViewById(R.id.message_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        messages = new ArrayList<FirebaseMessageDto>();

        loggedUser = AppTools.getLoggedUser();
        loggedUserId = loggedUser.getEntityId();

        if(getIntent().getExtras() != null){
            receiverId = getIntent().getExtras().getInt("receiverId", -1);
            adId = getIntent().getExtras().getInt("adId", -1);

            receiver = User.getOneGlobal(receiverId);

            User sender = AppTools.getLoggedUser();
            loggedUserId = sender.getEntityId();

            TextView displayUser = (TextView) findViewById(R.id.user_name_textview);
            displayUser.setText(receiver.getFirstName());

            LinearLayoutManager recyclerViewLayoutManager = new LinearLayoutManager(this);
            DividerItemDecoration divider = new DividerItemDecoration(this, recyclerViewLayoutManager.getOrientation());

            messagesRecyclerView = (RecyclerView)findViewById(R.id.message_detail_recyclerview);
            messagesRecyclerView.setHasFixedSize(true);
            messagesRecyclerView.setLayoutManager(recyclerViewLayoutManager);

            displayMessages();

            newMessageEditText = (EditText)findViewById(R.id.new_message_text);

            ImageButton sendButton = (ImageButton)findViewById(R.id.new_message_send_btn);
            sendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String messageText = newMessageEditText.getText().toString();
                    newMessageEditText.getText().clear();
                    Log.i("new msg text", messageText);
                    pushNewMessage(messageText);
                }
            });

            message_profile_img = (ImageView)findViewById(R.id.message_profile_img);

            if(receiver.getUrlProfile() != null) {
                if (receiver.getUrlProfile().startsWith("http")) { // fotografija je sa google naloga
                    Glide.with(this).load(receiver.getUrlProfile()).into(message_profile_img);
                } else {

                    Glide.with(getApplicationContext())
                            .load(PROFILE_URL.replace("HOST", getString(R.string.host)) + receiver.getUrlProfile())
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
                            .into(message_profile_img);
                }
            }
        }


    }

    private void displayMessages(){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Messages");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                messages.clear();

                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Log.d("DatabaseValueChanged", snapshot.getKey());
                    FirebaseMessageDto firebaseMessageDto = snapshot.getValue(FirebaseMessageDto.class);

                    //if message which includes logged user and opened receiver
                    if((firebaseMessageDto.getReceiver() == loggedUserId && firebaseMessageDto.getSender() == receiverId) ||
                                    (firebaseMessageDto.getReceiver() == receiverId && firebaseMessageDto.getSender() == loggedUserId)
                    ){
                        messages.add(firebaseMessageDto);
                    }
                }

                messagesRecyclerAdapter = new OneMessageRecyclerAdapter(messages, MessageActivity.this, loggedUserId);
                messagesRecyclerView.setAdapter(messagesRecyclerAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("DatabaseValueChanged", "DatabaseValueChanged");
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        handleBack();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                handleBack();
        }

        return super.onOptionsItemSelected(item);
    }

    private void pushNewMessage(String message){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        FirebaseMessageDto messageDto = new FirebaseMessageDto(loggedUserId, receiverId, message);

        ref.child("Messages").push().setValue(messageDto);

        if (notify) {
            sendNotifiaction(messageDto.getMessage());
        }
        notify = false;

    }

    private void sendNotifiaction(String message){
        DatabaseReference users = FirebaseDatabase.getInstance().getReference("Users");
        Query query = users.orderByKey().equalTo(String.valueOf(receiverId));

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Log.d("DatabaseValueChanged", snapshot.getKey());
                    FirebaseUserDto user = snapshot.getValue(FirebaseUserDto.class);
                    //Log.d("DatabaseValueChanged", user.getEntityId());
                    Log.d("DatabaseValueChanged", Integer.toString(user.getEntityId()));

                    FCMData data = new FCMData("chat", message, loggedUser.getFirstName(), String.valueOf(loggedUserId));
                    FCMRequest request = new FCMRequest(data, user.getDeviceId());

                    Call<FCMResponse> fcmCall = ServiceUtils.fcmServiceApi.sendNotification(request);
                    fcmCall.enqueue(new Callback<FCMResponse>() {
                        @Override
                        public void onResponse(Call<FCMResponse> call, Response<FCMResponse> response) {
                            if(response.isSuccessful()){
                                Log.i("fcm", "success");
                            } else {
                                Log.i("fcm", "error");
                            }
                        }

                        @Override
                        public void onFailure(Call<FCMResponse> call, Throwable t) {
                            Log.i("fcm", "error");
                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("DatabaseValueChanged", "DatabaseValueChanged");
            }
        });
    }

    private void handleBack(){
        if(adId != -1){
            Intent backIntent = new Intent(MessageActivity.this, RoomDetailActivity.class);
            backIntent.putExtra(RoomDetailFragment.ARG_ITEM_ID, adId);
            startActivity(backIntent);
        }
    }
}
