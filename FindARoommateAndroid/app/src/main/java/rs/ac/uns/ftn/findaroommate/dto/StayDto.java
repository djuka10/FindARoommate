package rs.ac.uns.ftn.findaroommate.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.uns.ftn.findaroommate.model.Ad;
import rs.ac.uns.ftn.findaroommate.model.User;
import rs.ac.uns.ftn.findaroommate.utils.AdStatus;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StayDto {

    private String title;
    private String location;
    private Date from;
    private Date to;
    private AdStatus adStatus;
    private User user;
    private int entity_id;
    private Long id;
}
