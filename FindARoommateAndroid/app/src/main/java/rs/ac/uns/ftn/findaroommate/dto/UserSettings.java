package rs.ac.uns.ftn.findaroommate.dto;

import com.activeandroid.annotation.Column;
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
public class UserSettings {

    @Expose
    private Integer entityId;

    @Expose
    private Integer userId;

    @Expose
    private String language;

    @Expose
    private String distance;

    @Expose
    private String remindDay;

    @Expose
    private Boolean shouldRemind;

    @Expose
    private Boolean stayNotif;

    @Expose
    private Boolean newMessageNotif;

    @Expose
    private Boolean newReviewNotif;

    @Expose
    private Boolean shouldNewAdMail;

    @Expose
    private Boolean shouldConfirmMail;

    @Expose
    private Boolean shouldRequestMail;

    public void setNewValues(String distance, String language, String remindDay,
                             Boolean newMessageNotif, Boolean newReviewNotif, Boolean stayNotif,
                             Boolean shouldConfirmMail, Boolean shouldNewAdMail, Boolean shouldRequestMail, Boolean shouldRemind,
                             Integer userId) {
        this.distance = distance;
        this.language = language;
        this.remindDay = remindDay;
        this.newMessageNotif = newMessageNotif;
        this.newReviewNotif = newReviewNotif;
        this.stayNotif = stayNotif;
        this.shouldConfirmMail = shouldConfirmMail;
        this.shouldNewAdMail = shouldNewAdMail;
        this.shouldRemind = shouldRemind;
        this.shouldRequestMail = shouldRequestMail;
        this.userId = userId;
    }

}

