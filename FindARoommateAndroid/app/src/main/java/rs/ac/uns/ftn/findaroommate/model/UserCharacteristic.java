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
@Table(name = "user_characteristics")
public class UserCharacteristic extends Model {

    @Expose
    @Column
    private CharacteristicType type;

    @Expose
    @Column
    private String value;

    // remote id
    @Expose
    @Column(name = "entity_id", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    private int entityId;

    public UserCharacteristic(CharacteristicType type, String value){
        this.type = type;
        this.value = value;
    }
}
