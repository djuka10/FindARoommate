package rs.ac.uns.ftn.findaroommate.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import rs.ac.uns.ftn.findaroommate.R;
import rs.ac.uns.ftn.findaroommate.adapter.MessagesRecyclerAdapter;
import rs.ac.uns.ftn.findaroommate.adapter.UserReviewRecyclerAdapter;
import rs.ac.uns.ftn.findaroommate.dto.FirebaseMessageDto;
import rs.ac.uns.ftn.findaroommate.dto.FirebaseUserDto;
import rs.ac.uns.ftn.findaroommate.model.Review;
import rs.ac.uns.ftn.findaroommate.model.User;
import rs.ac.uns.ftn.findaroommate.utils.AppTools;

public class MessagesActivity extends AppCompatActivity {

    MessagesRecyclerAdapter messagesRecyclerAdapter;
    RecyclerView messagesRecyclerView;

    int loggedUserId;

    List<Integer> userInteractionsId = new ArrayList<Integer>();
    List<User> userInteractions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        Toolbar toolbar = (Toolbar) findViewById(R.id.messages_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        LinearLayoutManager recyclerViewLayoutManager = new LinearLayoutManager(this);
        DividerItemDecoration divider = new DividerItemDecoration(this, recyclerViewLayoutManager.getOrientation());
        messagesRecyclerView = (RecyclerView)findViewById(R.id.messages_recyclerview);
        messagesRecyclerView.setLayoutManager(recyclerViewLayoutManager);
        messagesRecyclerView.setHasFixedSize(true);
        messagesRecyclerView.addItemDecoration(divider);

        User loggedUser = AppTools.getLoggedUser();
        loggedUserId = loggedUser.getEntityId();

        userInteractionsId = new ArrayList<Integer>();
        userInteractions = new ArrayList<>();

        updateUser();
        getUsersWithInteraction();
    }

    private void updateUser(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        FirebaseUserDto user = new FirebaseUserDto(loggedUserId, AppTools.getDeviceId());

        ref.child(String.valueOf(loggedUserId)).setValue(user);
    }

    private void getUsersWithInteraction(){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Messages");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userInteractions.clear();
                userInteractionsId.clear();

                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Log.d("DatabaseValueChanged", snapshot.getKey());
                    FirebaseMessageDto firebaseMessageDto = snapshot.getValue(FirebaseMessageDto.class);
                    //Log.d("DatabaseValueChanged", user.getEntityId());


                    //if message which includes logged user
                    if(firebaseMessageDto.getReceiver() == loggedUserId || firebaseMessageDto.getSender() == loggedUserId){
                        int newUserInteractionsId;

                        if(firebaseMessageDto.getSender() == loggedUserId){
                            newUserInteractionsId = firebaseMessageDto.getReceiver();
                        } else {
                            newUserInteractionsId = firebaseMessageDto.getSender();
                        }
                        if(!userInteractionsId.contains(newUserInteractionsId)){
                            userInteractionsId.add(newUserInteractionsId);
                            User newUserInteractions = User.getOneGlobal(newUserInteractionsId);
                            userInteractions.add(newUserInteractions);
                        }
                    }



                }

                messagesRecyclerAdapter = new MessagesRecyclerAdapter(userInteractions, MessagesActivity.this);
                messagesRecyclerView.setAdapter(messagesRecyclerAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("DatabaseValueChanged", "DatabaseValueChanged");
            }
        });
    }
}
