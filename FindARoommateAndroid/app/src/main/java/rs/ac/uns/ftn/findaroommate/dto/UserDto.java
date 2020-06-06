package rs.ac.uns.ftn.findaroommate.dto;

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
import rs.ac.uns.ftn.findaroommate.model.User;
import rs.ac.uns.ftn.findaroommate.utils.AppTools;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto{

    @Expose
    private int entityId;
    @Expose
    private String email;
    @Expose
    private String password;
    @Expose
    private String firstName;
    @Expose
    private String lastName;
    @Expose
    private String gender;
    @Expose
    private String birthDay;
    @Expose
    private String occupation;
    @Expose
    private String studyLevel;
    @Expose
    private String workingStatus;
    @Expose
    private String about;
    @Expose
    private String urlProfile;
    @Expose
    private String activeSince;

    public void convert(User user){
        this.entityId = user.getEntityId();
        if(user.getBirthDay() != null){
            this.birthDay = AppTools.getSimpleDateFormat().format(user.getBirthDay());
        }
        this.activeSince = AppTools.getSimpleDateFormat().format(user.getActiveSince());
        this.email = user.getEmail();
        this.about = user.getAbout();
        this.occupation = user.getOccupation();
        this.studyLevel = user.getStudyLevel();
        this.workingStatus = user.getWorkingStatus();
        this.urlProfile = user.getUrlProfile();
        this.gender = user.getGender();
        this.password = user.getPassword();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
    }

}
