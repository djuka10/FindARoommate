package rs.ac.uns.ftn.findaroommate.dto;

import java.util.Date;

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
public class StayDto {

    private String title;
    private String location;
    private Date from;
    private Date to;
}
