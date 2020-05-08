package rs.ac.uns.ftn.findaroommate.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

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
@Table(name = "resource_registry")
public class ResourceRegistry extends Model {

    @Column
    private int user;
    @Column
    private String uri;
    @Column
    private boolean profilePicture;
    @Column
    private int addId;

    public static List<ResourceRegistry> getAllResourceByAddId(Long id) {
        return new Select().from(ResourceRegistry.class).where("addId=?",id).execute();
    }

}
