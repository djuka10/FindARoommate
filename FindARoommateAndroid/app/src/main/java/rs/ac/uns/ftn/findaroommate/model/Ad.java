package rs.ac.uns.ftn.findaroommate.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Ad {

    private Long id;
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
    private int minDays;
    private int maxPerson;
    private int ladiesNum;
    private int boysNum;


}
