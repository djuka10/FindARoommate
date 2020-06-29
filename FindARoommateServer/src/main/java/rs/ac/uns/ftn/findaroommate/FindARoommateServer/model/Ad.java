package rs.ac.uns.ftn.findaroommate.FindARoommateServer.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.ManyToAny;

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
@Table(name="ads")
public class Ad {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer entityId;
	
    @Column(name="title")
    private String title;
    @Column(name = "desc")
    private String description;
    @Column(name = "latitude")
    private float latitude;
    @Column(name = "longitude")
    private float longitude;
    @Column(name = "address")
    private String address;
    @Column(name = "ad_type")
    private String adType;
    @Column(name = "flat_M2")
    private float flatM2;
    @Column(name = "room_M2")
    private float roomM2;
    @Column(name = "price")
    private float price;
    @Column(name = "const_included")
    private boolean costsIncluded;
    @Column(name = "deposit")
    private float deposit;
    @Column(name = "available_from")
    private Date availableFrom;
    @Column(name = "available_until")
    private Date availableUntil;
    @Column(name = "min_days")
    private String minDays;
    @Column(name = "max_days")
    private String maxDays;
    @Column(name = "max_person")
    private int maxPerson;
    @Column(name = "ladies_num")
    private int ladiesNum;
    @Column(name = "boys_num")
    private int boysNum;
    @Column(name = "ad_status")
    private AdStatus adStatus;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;
    
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User ownerId;

    
    @ManyToMany
    private List<UserCharacteristic> characteristics;
    
    @ManyToMany
    private List<AdItem> items;
    
    @Transient
    private List<Integer> adItemsId;
    @Transient
    private List<Integer> roommatePrefsId;
    @Transient
    private Integer adOwnerId;
    
}
