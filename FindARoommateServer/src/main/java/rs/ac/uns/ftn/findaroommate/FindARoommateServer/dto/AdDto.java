package rs.ac.uns.ftn.findaroommate.FindARoommateServer.dto;

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
public class AdDto {
	
	 	private int entityId;
	    private Integer userId;
	    private Integer ownerId;
	    private String title;
	    private String description;
	    private float latitude;
	    private float longitude;
	    private String adType;
	    private float flatM2;
	    private float roomM2;
	    private float price;
	    private boolean costsIncluded;
	    private float deposit;
	    private Date availableFrom;
	    private Date availableUntil;
	    private String minDays;
	    private String maxDays;
	    private int maxPerson;
	    private int ladiesNum;
	    private int boysNum;

}
