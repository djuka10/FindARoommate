package rs.ac.uns.ftn.findaroommate.FindARoommateServer.dto;

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
public class UserDto {
	
	
    private int entityId;
    
    private String email;
    
    private String password;
    
    private String firstName;
    
    private String lastName;
    
    private String gender;
    
    private String birthDay;
    
    private String occupation;
    
    private String studyLevel;
    
    private String workingStatus;
    
    private String about;
    
    private String urlProfile;
    
    private String activeSince;

}
