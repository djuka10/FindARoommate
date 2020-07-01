package rs.ac.uns.ftn.findaroommate.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;

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
@Table(name = "reviews")
public class Review extends Model {

    @Expose
    @Column(name = "entity_id", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    private int entityId;

    @Expose
    @Column
    private int rating;
    @Expose
    @Column
    private String comment;

    @Expose
    @Column
    private String title;

    @Expose
    @Column(name = "reviewer_name")
    private String reviewerName;

    @Column
    @Expose
    private int author;

    @Expose
    @Column(name = "rated_user")
    private int ratedUser;
    @Expose
    @Column
    private int ad;

    public static Review getOne(long id) {
        return new Select().from(Review.class).where("id=?",id).executeSingle();
    }

    public static Review getOneGlobal(int id) {
        return new Select().from(Review.class).where("entity_id=?",id).executeSingle();
    }

    public static Review getOne(int adId, int authorId) {
        return new Select().from(Review.class)
                .where("ad=?",adId)
                .where("author=?",authorId)
                .executeSingle();
    }

    public static List<Review> getAllReviews() {
        return new Select().from(Review.class).execute();
    }

    public static List<Review> getReviewsForAd(int ad) {
        return new Select().from(Review.class).where("ad=?", ad).execute();
    }

    public static List<Review> getAboutMe(int ratedUser) {
        return new Select().from(Review.class).where("rated_user=?", ratedUser).execute();
    }

    public static List<Review> getFromMe(int author) {
        return new Select().from(Review.class).where("author=?", author).execute();
    }

    public static List<Review> deleteAll() {
        return new Delete().from(Review.class).execute();
    }
}
