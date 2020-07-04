package rs.ac.uns.ftn.findaroommate.dto;


import com.google.gson.annotations.Expose;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PushNotificationRequest {

	@Expose
	String title;
	@Expose
	String topic;
	@Expose
	String message;
	@Expose
	String token;
	@Expose
	Integer userId;
	@Expose
	Integer adId;
	@Expose
	Integer reviewId;

}
