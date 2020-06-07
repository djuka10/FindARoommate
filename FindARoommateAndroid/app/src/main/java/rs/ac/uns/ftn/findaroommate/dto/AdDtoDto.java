package rs.ac.uns.ftn.findaroommate.dto;

import com.google.gson.annotations.Expose;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.uns.ftn.findaroommate.model.Ad;
import rs.ac.uns.ftn.findaroommate.model.User;
import rs.ac.uns.ftn.findaroommate.utils.AppTools;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdDtoDto {

    @Expose
    private int entityId;
    @Expose
    private UserDto userId;
    @Expose
    private UserDto ownerId;
    @Expose
    private String title;
    @Expose
    private String description;
    @Expose
    private float latitude;
    @Expose
    private float longitude;
    @Expose
    private String adType;
    @Expose
    private float flatM2;
    @Expose
    private float roomM2;
    @Expose
    private float price;
    @Expose
    private boolean costsIncluded;
    @Expose
    private float deposit;
    @Expose
    private String availableFrom;
    @Expose
    private String availableUntil;
    @Expose
    private String minDays;
    @Expose
    private String maxDays;
    @Expose
    private int maxPerson;
    @Expose
    private int ladiesNum;
    @Expose
    private int boysNum;

    public void convert(Ad ad){
        this.entityId = ad.getEntityId();
        if(ad.getAvailableFrom() != null){
            this.availableFrom = AppTools.getSimpleDateFormat().format(ad.getAvailableFrom());
        }
        if(ad.getAvailableUntil() != null){
            this.availableUntil = AppTools.getSimpleDateFormat().format(ad.getAvailableUntil());
        }
        this.title = ad.getTitle();
        this.adType = ad.getAdType();
        this.boysNum = ad.getBoysNum();
        this.costsIncluded = ad.isCostsIncluded();
        this.deposit = ad.getDeposit();
        this.description = ad.getDescription();
        this.flatM2 = ad.getFlatM2();
        this.roomM2 = ad.getRoomM2();
        this.ladiesNum = ad.getLadiesNum();
        this.latitude = ad.getLatitude();
        this.longitude = ad.getLongitude();
        this.maxDays = ad.getMaxDays();
        this.minDays = ad.getMinDays();
        this.maxPerson = ad.getMaxPerson();
        this.price = ad.getPrice();
        UserDto userDto = new UserDto();
        userDto.convert(ad.getUserId());
        this.userId = userDto;
        UserDto ownerDto = new UserDto();
        ownerDto.convert(ad.getOwnerId());
        this.ownerId = ownerDto;
    }
}
