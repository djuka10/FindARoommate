package rs.ac.uns.ftn.findaroommate.FindARoommateServer.dto;

import org.springframework.web.multipart.MultipartFile;

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
public class ProfileImageDto {

		//@NotNull
		private MultipartFile image;
	    
	    //@NotNull
	    private Integer user;
	    
	    private boolean profilePicture;
	    
}
