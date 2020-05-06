package rs.ac.uns.ftn.findaroommate.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

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
@Table(name="ad_items")
public class AdItem extends Model {
    @Column(name = "name")
    private String name;
    @Column(name = "ad")
    private Ad ad;
}
