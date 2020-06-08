package rs.ac.uns.ftn.findaroommate.FindARoommateServer.model;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Table(name = "users")
public class User {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer entityId;

    @Column
    private String email;
    @Column
    private String password;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String gender;
    @Column
    private Date birthDay;
    @Column
    private String occupation;
    @Column
    private String studyLevel;
    @Column
    private String workingStatus;
    @Column
    private String about;
    @Column
    private String urlProfile;
    @Column
    private Date activeSince;
    
    @Transient
    private List<Integer> languageIds;
    
    @ManyToMany
    private List<Language> languages;
    
    @Transient
    private List<Integer> userCharacteristicIds;
    
    @ManyToMany
    private List<UserCharacteristic> characteristics;
        
}
