package rs.ac.uns.ftn.findaroommate.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import rs.ac.uns.ftn.findaroommate.R;
import rs.ac.uns.ftn.findaroommate.adapter.MessageAdapter;
import rs.ac.uns.ftn.findaroommate.model.Message;
import rs.ac.uns.ftn.findaroommate.model.User;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MessageAdapter messageAdapter;
    private List<Message> messages = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        recyclerView = findViewById(R.id.rv_chat);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        User user1 = new User();
        user1.setEntityId(1);
        user1.setFirstName("Djuro");
        user1.setLastName(" Stevic");
        user1.setUrlProfile("https://upload.wikimedia.org/wikipedia/en/thumb/8/8f/Boston_Celtics.svg/300px-Boston_Celtics.svg.png");

        User user2 = new User();
        user2.setEntityId(2);
        user2.setFirstName("Pera");
        user2.setLastName(" Peric");
        user2.setUrlProfile("https://upload.wikimedia.org/wikipedia/commons/thumb/2/2c/Jayson_Tatum_%282018%29.jpg/330px-Jayson_Tatum_%282018%29.jpg");

        Message message1 = new Message();
        message1.setSender(user1);
        message1.setMessage("Trazim cimera");

        Message message2 = new Message();
        message2.setSender(user2);
        message2.setMessage("I ja trazim cimera");

        Message message3 = new Message();
        message3.setSender(user1);
        message3.setMessage("Hoces da budemo cimeri?");

        messages.add(message1);
        messages.add(message2);
        messages.add(message3);

        messageAdapter = new MessageAdapter(messages, this);
        recyclerView.setAdapter(messageAdapter);
    }

}
