package rs.ac.uns.ftn.findaroommate.FindARoommateServer.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reviews")
public class Review {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer entityId;

    @Column
    private Integer rating;
    @Column
    private String title;
    @Column
    private String comment;
  
    @ManyToOne
    @JoinColumn(name = "ad_id")
    private Ad ad;
    
    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;
    
    @ManyToOne
    @JoinColumn(name = "rated_user_id")
    private User ratedUser;
}
