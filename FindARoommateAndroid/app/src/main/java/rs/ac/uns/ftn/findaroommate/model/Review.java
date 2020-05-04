package rs.ac.uns.ftn.findaroommate.model;

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
public class Review {

    private int author;
    private int rating;
    private String comment;
    private int ratedUser;
    private String adUrl;
}
