package rs.ac.uns.ftn.findaroommate.FindARoommateServer.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "user_settings")
public class UserSettings {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer entityId;
	
	@Column
	private Integer userId;

    @Column
    private String language;
    
    @Column
    private String distance;
    
    @Column
    private String remindDay;
    
    @Column
    private Boolean shouldRemind;
    
    @Column
    private Boolean stayNotif;
    
    @Column
    private Boolean newMessageNotif;
    
    @Column
    private Boolean newReviewNotif;
    
    @Column
    private Boolean shouldNewAdMail;
    
    @Column
    private Boolean shouldConfirmMail;
    
    @Column
    private Boolean shouldRequestMail;
    
    public void setNewValues(UserSettings settings) {
    	this.distance = settings.getDistance();
    	this.language = settings.getLanguage();
    	this.remindDay = settings.getRemindDay();
    	this.newMessageNotif = settings.getNewMessageNotif();
    	this.newReviewNotif = settings.getNewReviewNotif();
    	this.stayNotif = settings.getStayNotif();
    	this.shouldConfirmMail = settings.getShouldConfirmMail();
    	this.shouldNewAdMail = settings.getShouldNewAdMail();
    	this.shouldRemind = settings.getShouldRemind();
    	this.shouldRequestMail = settings.getShouldRequestMail();
    }

}
