package rs.ac.uns.ftn.findaroommate.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User extends Model {

    @Expose
    @Column(name = "entity_id", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    private int entityId;
    /*@Expose
    @Column(name = "ads")
    private List<Ad> ads;*/
    @Expose
    @Column
    private String email;
    @Expose
    @Column
    private String password;
    @Expose
    @Column
    private String firstName;
    @Expose
    @Column
    private String lastName;
    @Expose
    @Column
    private String gender;
    @Expose
    @Column
    private Date birthDay;
    @Expose
    @Column
    private String occupation;
    @Expose
    @Column
    private String studyLevel;
    @Expose
    @Column
    private String workingStatus;
    @Expose
    @Column
    private String about;
    @Expose
    @Column
    private String urlProfile;
    @Expose
    @Column
    private Date activeSince;

    @Expose
    private List<Integer> languageIds;

    @Expose
    private List<Integer> userCharacteristicIds;

    public static List<User> getOneByEmail(String email) {
        return new Select().from(User.class).where("email=?",email).execute();
    }

    public static User getOne(long id) {
        return new Select().from(User.class).where("id=?",id).executeSingle();
    }

    public static User getOneGlobal(int id) {
        return new Select().from(User.class).where("entity_id=?",id).executeSingle();
    }

}
