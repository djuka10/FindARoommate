package rs.ac.uns.ftn.findaroommate.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;

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
@Table(name="languages")
public class Language extends Model {

    @Expose
    @Column
    private String isoCode;

    @Expose
    @Column
    private String name;

    @Expose
    private int entityId;

    public Language(String isoCode, String name) {
        this.isoCode = isoCode;
        this.name = name;
    }

}
