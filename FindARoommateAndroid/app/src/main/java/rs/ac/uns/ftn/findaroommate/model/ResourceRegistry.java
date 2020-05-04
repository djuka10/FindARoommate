package rs.ac.uns.ftn.findaroommate.model;

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
public class ResourceRegistry {

    private int user;
    private String uri;
    private boolean profilePicture;
    private int addId;
}
