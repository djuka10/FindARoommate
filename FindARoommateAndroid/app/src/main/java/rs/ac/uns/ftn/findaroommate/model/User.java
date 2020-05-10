package rs.ac.uns.ftn.findaroommate.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.Date;

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

    @Column
    private int userId;
    @Column
    private String email;
    @Column
    private String password;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String gender;
    @Column
    private Date birthDay;
    @Column
    private String occupation;
    @Column
    private String studyLevel;
    @Column
    private String workingStatus;
    @Column
    private String about;
    @Column
    private String urlProfile;
    @Column
    private Date activeSince;
}
