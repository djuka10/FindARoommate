package rs.ac.uns.ftn.findaroommate.dto;

import com.activeandroid.annotation.Column;
import com.google.gson.annotations.Expose;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.uns.ftn.findaroommate.model.Ad;
import rs.ac.uns.ftn.findaroommate.model.AdItem;
import rs.ac.uns.ftn.findaroommate.model.ResourceRegistry;
import rs.ac.uns.ftn.findaroommate.model.UserCharacteristic;
import rs.ac.uns.ftn.findaroommate.utils.AdStatus;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdFormDto {
    @Expose
    private int entityId;
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
    @Expose
    List<Integer> adItemsId;
    @Expose
    List<Integer> roommatePrefsId;

    @Expose
    private AdStatus adStatus;

    @Expose
    Integer adOwnerId;

    // private List<ResourceRegistry> images;
}
