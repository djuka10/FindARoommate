package rs.ac.uns.ftn.findaroommate.model;

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
public class User {

    private int id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String gender;
    private Date birthDay;
    private String occupation;
    private String studyLevel;
    private String workingStatus;
    private String about;
    private String urlProfile;
    private Date activeSince;
}
