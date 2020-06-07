package rs.ac.uns.ftn.findaroommate.model;

import android.view.Display;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="ads")
public class Ad  extends Model{
    @Expose
    @Column(name = "entity_id", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    private int entityId;
    //obican korisnik koji trazi oglas
    @Expose
    @Column(name = "user_id")
    private User userId;
    //vlasnik oglasa
    @Expose
    @Column(name = "owner_id")
    private User ownerId;
    @Expose
    @Column(name="title")
    private String title;
    @Expose
    @Column(name = "desc")
    private String description;
    @Expose
    @Column(name = "latitude")
    private float latitude;
    @Expose
    @Column(name = "longitude")
    private float longitude;
    @Expose
    @Column(name = "ad_type")
    private String adType;
    @Expose
    @Column(name = "flat_M2")
    private float flatM2;
    @Expose
    @Column(name = "room_M2")
    private float roomM2;
    @Expose
    @Column(name = "price")
    private float price;
    @Expose
    @Column(name = "const_included")
    private boolean costsIncluded;
    @Expose
    @Column(name = "deposit")
    private float deposit;
    @Expose
    @Column(name = "available_from")
    private Date availableFrom;
    @Expose
    @Column(name = "available_until")
    private Date availableUntil;
    @Expose
    @Column(name = "min_days")
    private String minDays;
    @Expose
    @Column(name = "max_days")
    private String maxDays;
    @Expose
    @Column(name = "max_person")
    private int maxPerson;
    @Expose
    @Column(name = "ladies_num")
    private int ladiesNum;
    @Expose
    @Column(name = "boys_num")
    private int boysNum;

    public static List<Ad> getAllAds() {
        return new Select().from(Ad.class).execute();
    }

    public static Ad getRandom() {
        return new Select().from(Ad.class).orderBy("RANDOM()").executeSingle();
    }

    public static Ad getOne(long id) {
        return new Select().from(Ad.class).where("id=?", id).executeSingle();
    }

    public static List<Ad> getAllAdsByUserId(User user) {
        return new Select().from(Ad.class).where("user_id=?", user.getId()).execute();
    }

}
