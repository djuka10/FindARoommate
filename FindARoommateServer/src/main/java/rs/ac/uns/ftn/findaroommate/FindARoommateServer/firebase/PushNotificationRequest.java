package rs.ac.uns.ftn.findaroommate.FindARoommateServer.firebase;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PushNotificationRequest {
	
	String title;
	String topic;
	String message;
	String token;
	
	Integer userId;
	Integer adId;
	Integer reviewId;


}
