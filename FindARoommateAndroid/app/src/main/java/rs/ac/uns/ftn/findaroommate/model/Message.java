package rs.ac.uns.ftn.findaroommate.model;

import android.graphics.ColorSpace;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "message")
public class Message extends Model {

    @Column(name = "sender")
    private User sender;
    @Column(name = "receiver")
    private User receiver;
    @Column(name = "title")
    private String title;
    @Column(name = "message")
    private String message;

    public static List<Message> getAllMessages() {
        return new Select().from(Message.class).execute();
    }

}
