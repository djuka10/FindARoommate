package rs.ac.uns.ftn.findaroommate.dto;

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

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdDto {

    private Ad ad;
    private List<ResourceRegistry> images;
    private List<AdItem> adItems;

    private List<UserCharacteristic> prefsPersonality;
    private List<UserCharacteristic> prefsLifestyle;
    private List<UserCharacteristic> prefsFilm;
    private List<UserCharacteristic> prefsMusic;
    private List<UserCharacteristic> prefsSport;


}
