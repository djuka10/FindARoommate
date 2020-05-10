package rs.ac.uns.ftn.findaroommate.model;

import android.view.Display;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

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
    @Column(name="title")
    private String title;
    @Column(name = "desc")
    private String description;
    @Column(name = "latitude")
    private float latitude;
    @Column(name = "longitude")
    private float longitude;
    @Column(name = "ad_type")
    private String adType;
    @Column(name = "flat_M2")
    private float flatM2;
    @Column(name = "room_M2")
    private float roomM2;
    @Column(name = "price")
    private float price;
    @Column(name = "const_included")
    private boolean costsIncluded;
    @Column(name = "deposit")
    private float deposit;
    @Column(name = "available_from")
    private Date availableFrom;
    @Column(name = "available_until")
    private Date availableUntil;
    @Column(name = "min_days")
    private String minDays;
    @Column(name = "max_days")
    private String maxDays;
    @Column(name = "max_person")
    private int maxPerson;
    @Column(name = "ladies_num")
    private int ladiesNum;
    @Column(name = "boys_num")
    private int boysNum;

    public static List<Ad> getAllAds() {
        return new Select().from(Ad.class).execute();
    }

    public static Ad getRandom() {
        return new Select().from(Ad.class).orderBy("RANDOM()").executeSingle();
    }

}
