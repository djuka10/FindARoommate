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
public class ReviewDto{

    private int entityId;
    private int rating; 
    private String comment;
    private String title;
    private String reviewerName;
    private int author;
    private int ratedUser;
    private int ad;
}
